from flask_restful import Resource, reqparse
from flask_jwt import jwt_required
from models.user import UserModel
from security import identity
from flask_jwt import current_identity


class UserRegister(Resource):

    parser = reqparse.RequestParser()
    parser.add_argument(
        'username',
        type=str,
        required=True,
        help="Username cannot be blank."
    )
    parser.add_argument(
        'password',
        type=str,
        required=True,
        help="Password cannot be blank."
    )
    parser.add_argument(
        'role',
        type=int,
        required=True,
        help="Choose user role, please."
    )
    parser.add_argument(
        'email',
        type=str,
        required=True,
        help="Any box for letters?"
    )
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help="Name cannot be blank."
    )

    @jwt_required()
    def post(self):
        data = UserRegister.parser.parse_args()
        admin = current_identity.id

        if UserModel.find_by_username(data['username']):
            return {"message": "A user with that username already exists"}, 400

        user = UserModel(admin=admin, **data)
        user.save_to_db()

        return {"message": "User created successfully."}, 201
