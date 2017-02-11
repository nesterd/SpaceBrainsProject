import jwt
import time
from flask import current_app
from datetime import datetime, timedelta
from werkzeug.security import safe_str_cmp
from flask_restful import Resource, reqparse
from models.user import UserModel


class Login(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'username',
        type=str,
        required=True,
        help='Username cannot be blank.'
    )
    parser.add_argument(
        'password',
        type=str,
        required=True,
        help='Password cannot be blank.'
    )

    def post(self):
        data = Login.parser.parse_args()
        user_name = data['username']
        user_pass = data['password']
        user = authenticate(user_name, user_pass)
        if user:
            jwt_secret = current_app.config.get('JWT_SECRET_KEY')
            jwt_algorithm = current_app.config.get('JWT_ALGORITHM')
            payload = payload_handler(user)
            jwt_token = jwt.encode(payload, jwt_secret, jwt_algorithm)
            return {
                'id': user.id,
                'email': user.email,
                'name': user.name,
                'role': user.role,
                'token': jwt_token.decode('utf-8'),
                'token_exp': time.strftime(
                    '%Y-%m-%d %H:%M:%S',
                    time.gmtime(payload['exp'])
                ) + ' GMT'
            }, 200
        return {'message': 'Invalid credentials'}, 400


def authenticate(username, password):
    user = UserModel.find_by_username(username)
    if user and safe_str_cmp(user.password, password):
        return user


def identity(payload):
    user_id = payload['identity']
    return UserModel.find_by_id(user_id)


def payload_handler(identity):
    iat = datetime.utcnow()
    exp = iat + current_app.config.get('JWT_EXPIRATION_DELTA')
    nbf = iat + current_app.config.get('JWT_NOT_BEFORE_DELTA')
    identity = getattr(identity, 'id') or identity['id']
    return {'exp': exp, 'iat': iat, 'nbf': nbf, 'identity': identity}
