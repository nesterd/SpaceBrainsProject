from flask_restful import Resource, reqparse
from models.person import PersonModel


class Person(Resource):
    def get(self, name):
        person = PersonModel.find_by_name(name)
        if person:
            return person.json()
        return {'message': 'Person not found'}, 404

    def post(self, name):
        if PersonModel.find_by_name(name):
            return {'message': "A person with name '{}' already exists.".format(name)}, 400

        person = PersonModel(name)
        try:
            person.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return person.json(), 201

    def delete(self, name):
        person = PersonModel.find_by_name(name)
        if person:
            person.delete_from_db()

        return {'message': 'Site deleted'}


class PersonList(Resource):
    def get(self):
        return {'persons': list(map(lambda x: x.json(), PersonModel.query.all()))}