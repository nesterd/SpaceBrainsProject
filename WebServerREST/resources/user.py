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


class User(Resource):
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
    def get(self, id):
        admin = current_identity.id
        user = UserModel.find_by_id(id)
        if user and user.admin == admin:   # проверяем принадлежит ли запрашиваемый user к этому админу
            return user.json()
        return {'message': 'User not found'}, 404

    @jwt_required()
    def delete(self, id=None, name=None):
        if id:
            user = UserModel.find_by_id(id)
        else:
            user = UserModel.find_by_name(name)
        if user:
            user.delete_from_db()

        return {'message': 'User deleted'}

    @jwt_required()
    def put(self, id):
        data = User.parser.parse_args()
        user = UserModel.find_by_id(id)

        admin = current_identity.id

        if user:
            user.name = data['name']
            user.email = data['email']
            user.username = data['username']
            user.password = data['password']
            user.admin = admin
        else:
            user = UserModel(admin=admin, **data)

        user.save_to_db()
        return user.json()


class UserListView(Resource):
    @jwt_required()
    def get(self):
        admin = current_identity.id
        return {
            'users': list(map(lambda x: x.json(), UserModel.query.filter_by(admin=admin))),
        }


class AdminView(Resource):
    @jwt_required()
    def get(self):
        admin = current_identity.id
        return {
            'admins': list(map(lambda x: x.json(), UserModel.query.filter_by(role=2, admin=admin))),
        }