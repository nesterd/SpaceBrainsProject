from flask_restful import Resource, reqparse
from flask_jwt import jwt_required, current_identity
from models.site import SiteModel
from models.pages import PageModel


class Site(Resource):
    parser = reqparse.RequestParser()
    parser.add_argument(
        'name',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )

    @jwt_required()
    def get(self, id=None, name=None):
        if id:
            site = SiteModel.find_by_id(id)
        else:
            site = SiteModel.find_by_name(name)
        if site:
            return site.json()
        return {'message': 'Site not found'}, 404

    @jwt_required()
    def post(self, name):
        if SiteModel.find_by_name(name):
            return {
                'message': "A site with name '{}' already exists.".format(name)
            }, 400

        current_user = current_identity.id
        site = SiteModel(name=name, admin=current_user)
        try:
            site.save_to_db()
        except:
            return {"message": "An error occurred creating the site."}, 500

        return site.json(), 201

    @jwt_required()
    def delete(self, id=None, name=None):
        if id:
            site = SiteModel.find_by_id(id)
        else:
            site = SiteModel.find_by_name(name)
        if site:
            site.delete_from_db()
            return {'message': 'Site deleted.'}, 200
        return {'message': 'Site not found.'}, 404

    @jwt_required()
    def put(self, id):
        data = Site.parser.parse_args()
        site = SiteModel.find_by_id(id)
        current_user = current_identity.id

        if site:
            site.name = data['name']
            site.admin = current_user
            site.save_to_db()
            return site.json(), 200
        else:
            site = SiteModel(name=data['name'], admin=current_user)
            site.save_to_db()
            return site.json(), 201


class SiteList(Resource):
    @jwt_required()
    def get(self):
        return {
            'sites': list(map(lambda x: x.json(), SiteModel.query.all())),
        }, 200


class PagesList(Resource):
    @jwt_required()
    def get(self, id):
        # пройти каждую страничку и вывести их Rank из RankModel
        # pages = PageModel.query.filter_by(Siteid=id)
        # for rank in RankModel.query.filter_by(Siteid=id):
        return {'pages': PageModel.query.filter_by(site_id=id).count()}, 200


class CreateSite(Resource):
    parser2 = reqparse.RequestParser()
    parser2.add_argument(
        'name',
        type=str,
        required=True,
        help="This field cannot be left blank!"
    )

    @jwt_required()
    def post(self):
        data = CreateSite.parser2.parse_args()
        current_user = current_identity.id
        site = SiteModel(name=data['name'], admin=current_user)

        if SiteModel.find_by_name(data['name']):
            return {
                'message': "A site with name '{}' already exists.".format(
                    data['name'])
            }, 400

        site.save_to_db()
        return site.json(), 201
