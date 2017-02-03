from flask_restful import Resource, reqparse
from flask_jwt import jwt_required
from models.site import SiteModel
from models.pages import PageModel


class Site(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument('name',
                        type=str,
                        required=True,
                        help="This field cannot be left blank!"
                        )

    # @jwt_required()
    def get(self, ID=None, Name=None):
        if ID:
            site = SiteModel.find_by_id(ID)
        else:
            site = SiteModel.find_by_name(Name)
        if site:
            return site.json()
        return {'message': 'Site not found'}, 404

    # @jwt_required()
    def post(self, Name):
        if SiteModel.find_by_name(Name):
            return {'message': "A site with name '{}' already exists.".format(Name)}, 400

        site = SiteModel(Name)
        try:
            site.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return site.json(), 201

    # @jwt_required()
    def delete(self, ID=None, Name=None):
        if ID:
            site = SiteModel.find_by_id(ID)
        else:
            site = SiteModel.find_by_name(Name)
        if site:
            site.delete_from_db()

        return {'message': 'Site deleted'}

    # @jwt_required()
    def put(self, ID):
        data = Site.parser.parse_args()

        site = SiteModel.find_by_id(ID)

        if site:
            site.Name = data['name']
        else:
            site = SiteModel(data['name'])

        site.save_to_db()

        return site.json()


class SiteList(Resource):
    # @jwt_required()
    def get(self):
        return {'sites': list(map(lambda x: x.json(), SiteModel.query.all()))}


class PagesList(Resource):
#    @jwt_required()
    def get(self, ID):
        # пройти каждую страничку и вывести их Rank из RankModel
        # pages = PageModel.query.filter_by(SiteID=ID)
        #for rank in RankModel.query.filter_by(SiteID=ID):
        return {'pages': PageModel.query.filter_by(SiteID=ID).count()}


class CreateSite(Resource):
    parser2 = reqparse.RequestParser()
    parser2.add_argument('name',
                        type=str,
                        required=True,
                        help="This field cannot be left blank!"
                        )

    # @jwt_required()
    def post(self):
        data = CreateSite.parser2.parse_args()
        site = SiteModel(data['name'])

        if SiteModel.find_by_name(data['name']):
            return {'message': "A site with name '{}' already exists.".format(data['name'])}, 400

        site.save_to_db()
        return site.json(), 201
