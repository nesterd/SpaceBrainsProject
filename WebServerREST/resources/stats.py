from flask_restful import Resource, reqparse
from flask_jwt import jwt_required, current_identity
from models.pages import PageModel
from models.site import SiteModel
from models.rank import RankModel
from datetime import datetime


class Pages(Resource):
    @jwt_required()
    def get(self, id=None, name=None):
        if id:
            stat = PageModel.find_by_id(id)
        else:
            stat = PageModel.find_by_name(name)
        if stat:
            return stat.json(current_identity.admin), 200
        return {'message': 'not found pages in this site'}, 404


class StatList(Resource):
    @jwt_required()
    def get(self):
        result = SiteModel.query.all()
        if result:
            return {
                'base statistic': [
                    PageModel.find_by_id(el.id).json(
                        current_identity.admin
                    ) for el in result if PageModel.find_by_id(el.id)]
            }, 200
        return {'message': 'not found base statistic'}, 404


class Rank(Resource):
    @jwt_required()
    def get(self, id=None, name=None):
        if id:
            stat = RankModel.find_by_id(id)
        else:
            stat = RankModel.find_by_name(name)
        if stat:
            return stat.json(), 200
        return {'message': 'not found rank in this site'}, 404


class RankList(Resource):
    @jwt_required()
    def get(self):
        result = SiteModel.query.all()
        if result:
            return {
                'base_rank statistic': [
                    RankModel.find_by_id(
                        el.id
                    ).json() for el in result if RankModel.find_by_id(el.id)
                ]
            }, 200
        return {'message': 'not found base_rank statistic'}, 404


class RankDay(Resource):
    # @jwt_required()
    def _pars_date(self, date):
        # pattern = r'\d{2}-\d{2}-\d{4}'
        # if re.match(pattern, date) is not None:
        #    return True
        # return False
        try:
            # date_str = "30-10-2016 16:18"
            # format_str = "%d-%m-%Y %H:%M"
            format_str = "%Y-%m-%d"
            return datetime.strptime(date, format_str)
        except ValueError:
            return False

    @jwt_required()
    def get(self, id=None, name=None, date=None):
        date = self._pars_date(date)
        if not date:
            return {'message': 'format of date is false ' + str(id)}, 404
        stat = 'None'
        if id and date:
            stat = RankModel.find_by_id_day(id, date)
        elif name and date:
            stat = RankModel.find_by_name_day(name, date)
        if stat:
            return stat.json_day(date), 200
        return {'message': 'not found pages in this site, day'}, 404


class RankDayList(Resource):
    @jwt_required()
    def get(self, date=None):
        result = PageModel.query.filter(PageModel.scan == date).first()
        if result:
            return {  # bad choise, key shouldn't change!
                'base_rank_day statistic - ' + date: [
                    RankModel.find_by_id_day(
                        el.id, date
                    ).json_day(date) for el in SiteModel.query.all(
                        ) if RankModel.find_by_id_day(el.id, date)
                ]
            }, 200
        return {
            'message': 'not found base_rank_day statistic for this day'
        }, 404


class RankTime(Resource):
    def _pars_date(self, date):
        try:
            format_str = "%Y-%m-%d"
            return datetime.strptime(date, format_str)
        except ValueError:
            return False

    @jwt_required()
    def get(self, id=None, name=None, date1=None, date2=None):
        date1 = self._pars_date(date1)
        date2 = self._pars_date(date2)
        if not date1 or not date2:
            return {'message': 'format of date is false'}, 404
        stat = 'None'
        if id and date1 and date2:
            stat = RankModel.find_by_id_time(id, date1, date2)
        elif name and date1 and date2:
            stat = RankModel.find_by_name_time(name, date1, date2)
        if stat:
            return stat.json_time(date1, date2), 200
        return {'message': 'not found pages in this site, time'}, 404


class RankTimeList(Resource):
    @jwt_required()
    def get(self, date1=None, date2=None):
        result = PageModel.query.filter(
            PageModel.scan >= date1,
            PageModel.scan <= date2
            ).first()
        if result:
            return {
                'base_rank_time statistic - ' + date1 + '/' + date2: [
                    RankModel.find_by_id_time(
                        el.id, date1, date2
                    ).json_time(
                        date1, date2
                    ) for el in SiteModel.query.all(
                    ) if RankModel.find_by_id_time(el.id, date1, date2)
                ]
            }, 200
        return {
            'message': 'not found base_rank_time statistic for this time'
        }, 404
