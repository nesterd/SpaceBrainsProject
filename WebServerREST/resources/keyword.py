from flask_restful import Resource, reqparse
from flask_jwt import jwt_required
from models.keyword import KeywordModel


class Keyword(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'person_id',
        type=int,
        required=True,
        help="Every keyword needs a person id."
    )
    parser1 = reqparse.RequestParser()
    parser1.add_argument(
        'name',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )
    parser1.add_argument(
        'person_id',
        type=int,
        required=True,
        help="Every keyword needs a person id."
    )

    @jwt_required()
    def get(self, id=None, name=None):
        if id:
            keyword = KeywordModel.find_by_id(id)
        else:
            keyword = KeywordModel.find_by_name(name)

        if keyword:
            return keyword.json()
        return {'message': 'Item not found'}, 404

    @jwt_required()
    def post(self, name):
        if KeywordModel.find_by_name(name):
            return {
                'message': "An keyword with name '{}' already exists.".format(
                    name)
            }, 400

        data = Keyword.parser.parse_args()
        keyword = KeywordModel(name=name, **data)

        try:
            keyword.save_to_db()
        except:
            return {"message": "An error occurred inserting the item."}, 500

        return keyword.json(), 201

    @jwt_required()
    def delete(self, id=None, name=None):
        if id:
            keyword = KeywordModel.find_by_id(id)
        else:
            keyword = KeywordModel.find_by_name(name)

        if keyword:
            keyword.delete_from_db()
            return {'message': 'Keyword deleted'}, 200
        return {'message': 'Keyword not found'}, 404

    @jwt_required()
    def put(self, id):
        data = Keyword.parser1.parse_args()
        keyword = KeywordModel.find_by_id(id)

        if keyword:
            keyword.name = data['name']
        else:
            keyword = KeywordModel(
                name=data['name'],
                person_id=data['person_id']
            )

        keyword.save_to_db()
        return keyword.json(), 200


class KeywordList(Resource):
    @jwt_required()
    def get(self):
        return {
            'keywords': list(map(
                lambda x: x.json(), KeywordModel.query.all())
            )
        }, 200


class CreateKeyword(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'person_id',
        type=int,
        required=True,
        help="Every keyword needs a person id."
    )
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )

    @jwt_required()
    def post(self):
        data = CreateKeyword.parser.parse_args()
        keyword = KeywordModel(
            name=data['name'],
            person_id=data['person_id']
        )

        if KeywordModel.find_by_name(data['name']):
            return {
                'message': "A keyword with name '{}' already exists.".format(
                    data['name'])
            }, 400

        keyword.save_to_db()
        return keyword.json(), 201
