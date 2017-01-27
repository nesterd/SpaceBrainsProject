from flask_restful import Resource, reqparse
from models.person import PersonModel


class Person(Resource):
    def get(self, Name=None, ID=None):
        if ID:
            person = PersonModel.find_by_id(ID)
        else:
            person = PersonModel.find_by_name(Name)
        if person:
            return person.json()
        return {'message': 'Person not found'}, 404

    def post(self, Name):
        if PersonModel.find_by_name(Name):
            return {'message': "A person with name '{}' already exists.".format(Name)}, 400

        person = PersonModel(Name)
        try:
            person.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return person.json(), 201

    def delete(self, Name):
        person = PersonModel.find_by_name(Name)
        if person:
            person.delete_from_db()

        return {'message': 'Site deleted'}

    def put(self, ID):
        data = Person.parser.parse_args()

        person = PersonModel.find_by_id(ID)

        if person:
            person.Name = data['name']
        else:
            person = PersonModel(id, data['name'])

        person.save_to_db()

        return person.json()


class PersonList(Resource):
    def get(self):
        return {'persons': list(map(lambda x: x.json(), PersonModel.query.all()))}