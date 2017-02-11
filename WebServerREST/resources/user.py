from flask_restful import Resource, reqparse
from flask_jwt import jwt_required
from models.user import UserModel
from flask_jwt import current_identity
from random import sample
from mailer import send_mail


class UserRegister(Resource):
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
    parser.add_argument(
        'email',
        type=str,
        required=True,
        help='Any box for letters?'
    )
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help='Name cannot be blank.'
    )

    @jwt_required()
    def post(self):
        data = UserRegister.parser.parse_args()
        current_user = current_identity.id
        if current_identity.role == 1:
            newuser_role = 2
        elif current_identity.role == 2:
            newuser_role = 3
        else:
            return {'message': 'You cannot create users.'}, 403

        if UserModel.find_by_username(data['username']):
            return {'message': 'A user with that username already exists'}, 400
        if UserModel.find_by_email(data['email']):
            return {'message': 'A user with that email already exists'}, 400

        user = UserModel(admin=current_user, role=newuser_role, **data)
        user.save_to_db()

        return {'message': 'User created successfully.'}, 201


class User(Resource):
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
    parser.add_argument(
        'email',
        type=str,
        required=True,
        help='Any box for letters?'
    )
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help='Name cannot be blank.'
    )

    @jwt_required()
    def get(self, id):
        current_user = current_identity.id
        user = UserModel.find_by_id(id)
        if user and user.admin == current_user:
            return user.json()
        return {'message': 'User not found'}, 404

    @jwt_required()
    def delete(self, id):
        current_user = current_identity.id
        if id:
            user = UserModel.find_by_id(id)
            if user and user.admin == current_user:
                user.admin = user.admin
                user.role = user.role
                user.delete_user()
            return {'message': 'User deleted'}, 200
        return {'message': 'No such user'}, 404

    @jwt_required()
    def put(self, id):
        current_user = current_identity.id

        def create_or_modyfy():
            data = User.parser.parse_args()
            user = UserModel.find_by_id(id)
            if user and user.admin == current_user:
                user.name = (
                    data['name'] if data['name'] else user.name
                )
                user.email = (
                    data['email'] if data['email'] else user.email
                )
                user.username = (
                    data['username'] if data['username'] else user.username
                )
                user.password = (
                    data['password'] if data['password'] else user.password
                )
                user.save_to_db()
                return user.json(), 200
            else:
                user = UserModel(admin=current_user, role=newuser_role, **data)
                user.save_to_db()
                return user.json(), 201

        if current_identity.role == 1:
            newuser_role = 2
            return create_or_modyfy()
        elif current_identity.role == 2:
            newuser_role = 3
            return create_or_modyfy()
        else:
            return {
                'message': 'You cannot modify users.'
            }, 403


class UserListView(Resource):
    @jwt_required()
    def get(self):
        current_user = current_identity.id

        if current_identity.role == 2:
            return {
                'users': list(map(
                    lambda x: x.json(),
                    UserModel.query.filter_by(admin=current_user)
                    )
                ),  # what is this mystical 'x'? Who or what is it?
            }, 200
        elif current_identity.role == 1:
            return {
                'users': list(map(
                    lambda x: x.json(),
                    UserModel.query.filter_by(
                        role=2,
                        admin=current_user
                        )
                    )
                ),
            }, 200
        else:
            return {
                'message': 'You have no rights for this!',
            }, 403


class UserChangePassword(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'password',
        type=str,
        required=True,
        help='Password cannot be blank.'
    )
    parser.add_argument(
        'new_password',
        type=str,
        required=True,
        help='Password cannot be blank.'
    )

    @jwt_required()
    def put(self):
        data = UserChangePassword.parser.parse_args()
        user = UserModel.find_by_id(current_identity.id)
        old_pass = data['password']
        new_pass = data['new_password']

        if old_pass and old_pass == user.password:
            if new_pass:
                user.password = new_pass
                user.save_to_db()
                return {'message': 'Password changed!'}, 200
            else:
                return {'message': 'A new password cannot be blank!'}, 400
        else:
            return {'message': 'Wrong password'}, 400


class UserRestorePassword(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'email',
        type=str,
        required=True,
        help='Any box for letters?'
    )

    def post(self):
        data = UserRestorePassword.parser.parse_args()

        if data['email']:
            user = UserModel.find_by_email(email=data['email'])
            if user:
                user.password = password_gen()
                user.save_to_db()
                send_mail(
                    user.email,
                    user.name,
                    user.username,
                    user.password
                )
                return {'message': 'We send an email to you.'}, 200
            else:
                return {'message': 'There is no user with such email.'}, 404
        else:
            return {'message': 'We need email.'}, 400


class UserStatus(Resource):
    @jwt_required()
    def get(self):
        return {
            'id': current_identity.id,
            'name': current_identity.name,
            'email': current_identity.email,
            'role': current_identity.role
        }


def password_gen():
    symbols = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUV\
        WXYZ01234567890?!@#$%^&*()'
    password = ''.join(sample(symbols, 8))
    return password
