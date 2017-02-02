from flask_restful import Resource, reqparse
from models.pages import PageModel
from models.site import SiteModel
# from requests import PageModel # for debug
# from requests import SiteModel # for debug


class Pages(Resource):
    # parser = reqparse.RequestParser()
    # parser.add_argument('SiteID',
    #                     type=int,
    #                     required=True,
    #                     help="For specific statistics, you must specify the SiteID!"
    #                     )
    # @classmethod #for debug
    def get(self, ID=None, Name=None):
        if ID:
            stat = PageModel.find_by_id(ID)
        else:
            stat = PageModel.find_by_name(Name)
        if stat:
            return stat.json()
        return {'message': 'Stat not found'}, 404

class StatList(Resource):
    # @classmethod #for debug
    def get(self):
        #pass
        return {'base statistic': [PageModel.find_by_id(el.ID).json() for el in SiteModel.query.all() if
                                   PageModel.find_by_id(el.ID)]}

    


