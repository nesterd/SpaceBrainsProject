from flask_restful import Resource, reqparse
from flask_jwt import jwt_required, current_identity
from models.person import PersonModel


class Person(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )

    @jwt_required()
    def get(self, name=None, id=None):
        if id:
            person = PersonModel.find_by_id(id)
        else:
            person = PersonModel.find_by_name(name)
        if person:
            return person.json(), 200
        return {'message': 'Person not found'}, 404

    @jwt_required()
    def post(self, name):
        if PersonModel.find_by_name(name):
            return {
                'message': "A person with name '{}' already exists.".format(
                    name)
            }, 400

        current_user = current_identity.id
        person = PersonModel(name=name, admin=current_user)
        try:
            person.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return person.json(), 201

    @jwt_required()
    def delete(self, name=None, id=None):
        if id:
            person = PersonModel.find_by_id(id)
        else:
            person = PersonModel.find_by_name(name)
        if person:
            person.delete_from_db()
            return {'message': 'Person deleted'}, 200
        else:
            return {'message': 'Person not found'}, 400

    @jwt_required()
    def put(self, id):
        data = Person.parser.parse_args()
        person = PersonModel.find_by_id(id)
        current_user = current_identity.id

        if person:
            person.name = data['name']
            person.admin = current_user
        else:
            person = PersonModel(name=data['name'], admin=current_user)

        person.save_to_db()
        return person.json(), 200


class PersonList(Resource):
    @jwt_required()
    def get(self):
        return {
            'persons': list(map(
                lambda x: x.json(), PersonModel.query.all()
            ))
        }, 200


class CreatePerson(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )

    @jwt_required()
    def post(self):
        current_user = current_identity.id
        data = CreatePerson.parser.parse_args()
        person = PersonModel(name=data['name'], admin=current_user)

        if PersonModel.find_by_name(data['name']):
            return {
                'message': "A person with name '{}' already exists.".format(
                    data['name']
                )
            }, 400

        person.save_to_db()
        return person.json(), 201
