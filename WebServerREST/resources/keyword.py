from flask_restful import Resource, reqparse
from flask_jwt import jwt_required
from models.keyword import KeywordModel


class Keyword(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument('PersonID',
                        type=int,
                        required=True,
                        help="Every keyword needs a person id.")

#    @jwt_required()
    def get(self, ID=None, Name=None):
        if ID:
            keyword = KeywordModel.find_by_id(ID)
        else:
            keyword = KeywordModel.find_by_name(Name)

        if keyword:
            return keyword.json()
        return {'message': 'Item not found'}, 404

    def post(self, Name):
        if KeywordModel.find_by_name(Name):
            return {'message': "An keyword with name '{}' already exists.".format(Name)}, 400

        data = Keyword.parser.parse_args()

        keyword = KeywordModel(Name, **data)

        try:
            keyword.save_to_db()
        except:
            return {"message": "An error occurred inserting the item."}, 500

        return keyword.json(), 201

    def delete(self, ID=None, Name=None):
        if ID:
            keyword = KeywordModel.find_by_id(ID)
        else:
            keyword = KeywordModel.find_by_name(Name)

        if keyword:
            keyword.delete_from_db()

        return {'message': 'Keyword deleted'}

    def put(self, ID, PersonID=None):
        data = Keyword.parser.parse_args()

        keyword = KeywordModel.find_by_id(ID)

        if keyword:
            keyword.Name = data['name']
        else:
            keyword = KeywordModel(ID, data['name'])

        keyword.save_to_db()

        return keyword.json()


class KeywordList(Resource):
    def get(self):
        return {'keywords': list(map(lambda x: x.json(), KeywordModel.query.all()))}