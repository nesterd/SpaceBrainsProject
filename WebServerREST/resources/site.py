from flask_restful import Resource, reqparse
from models.site import SiteModel


class Site(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument('name',
                        type=str,
                        required=True,
                        help="This field cannot be left blank!"
                        )

    def get(self, id=None, name=None):
        if id:
            site = SiteModel.find_by_id(id)
        else:
            site = SiteModel.find_by_name(name)
        if site:
            return site.json()
        return {'message': 'Site not found'}, 404

    def post(self, name):
        if SiteModel.find_by_name(name):
            return {'message': "A site with name '{}' already exists.".format(name)}, 400

        site = SiteModel(name)
        try:
            site.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return site.json(), 201

    # Сделать удаление и по id, сейчас работает только по имени
    def delete(self, id=None, name=None):
        site = SiteModel.find_by_name(name)
        if site:
            site.delete_from_db()

        return {'message': 'Site deleted'}

    def put(self, id):
        data = Site.parser.parse_args()

        site = SiteModel.find_by_id(id)

        if site:
            site.name = data['name']
        else:
            site = SiteModel(id, data['name'])

        site.save_to_db()

        return site.json()


class SiteList(Resource):
    def get(self):
        return {'sites': list(map(lambda x: x.json(), SiteModel.query.all()))}