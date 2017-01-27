from flask_restful import Resource, reqparse
from models.site import SiteModel


class Site(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument('name',
                        type=str,
                        required=True,
                        help="This field cannot be left blank!"
                        )

    def get(self, ID=None, Name=None):
        if ID:
            site = SiteModel.find_by_id(ID)
        else:
            site = SiteModel.find_by_name(Name)
        if site:
            return site.json()
        return {'message': 'Site not found'}, 404

    def post(self, Name):
        if SiteModel.find_by_name(Name):
            return {'message': "A site with name '{}' already exists.".format(Name)}, 400

        site = SiteModel(Name)
        try:
            site.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return site.json(), 201

    # Сделать удаление и по id, сейчас работает только по имени
    def delete(self, ID=None, Name=None):
        site = SiteModel.find_by_name(Name)
        if site:
            site.delete_from_db()

        return {'message': 'Site deleted'}

    def put(self, ID):
        data = Site.parser.parse_args()

        site = SiteModel.find_by_id(ID)

        if site:
            site.Name = data['name']
        else:
            site = SiteModel(id, data['name'])

        site.save_to_db()

        return site.json()


class SiteList(Resource):
    def get(self):
        return {'sites': list(map(lambda x: x.json(), SiteModel.query.all()))}