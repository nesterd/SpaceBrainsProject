from flask_restful import Resource, reqparse
from flask_jwt import jwt_required
from models.user import UserModel
from flask_jwt import current_identity


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
    def delete(self, id=None, name=None):
        if id:
            user = UserModel.find_by_id(id)
        else:
            user = UserModel.find_by_name(name)
        if user:
            user.delete_from_db()
        return {
            'message': 'User deleted'
        }, 200

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
            else:
                user = UserModel(admin=current_user, role=newuser_role, **data)
            user.save_to_db()
            return user.json()

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
                'admins': list(map(
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
