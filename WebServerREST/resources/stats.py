from flask_restful import Resource, reqparse
from models.pages import PageModel
from models.site import SiteModel
from models.rank import RankModel
from datetime import datetime


class Pages(Resource):
    def get(self, ID=None, Name=None):
        if ID:
            stat = PageModel.find_by_id(ID)
        else:
            stat = PageModel.find_by_name(Name)
        if stat:
            return stat.json()
        return {'message': 'not found pages in this site'}, 404


class StatList(Resource):
    def get(self):
        result = SiteModel.query.all()
        if result:
            return {
                'base statistic': [PageModel.find_by_id(el.ID).json() for el in result if PageModel.find_by_id(el.ID)]}
        return {'message': 'not found base statistic'}, 404


class Rank(Resource):
    def get(self, ID=None, Name=None):
        if ID:
            stat = RankModel.find_by_id(ID)
        else:
            stat = RankModel.find_by_name(Name)
        if stat:
            return stat.json()
        return {'message': 'not found rank in this site'}, 404


class RankList(Resource):
    def get(self):
        result = SiteModel.query.all()
        if result:
            return {'base_rank statistic': [RankModel.find_by_id(el.ID).json() for el in result if
                                            RankModel.find_by_id(el.ID)]}
        return {'message': 'not found base_rank statistic'}, 404


class RankDay(Resource):
    def _pars_date(self, date):
        # pattern = r'\d{2}-\d{2}-\d{4}'
        # if re.match(pattern, date) is not None:
        #   return True
        # return False
        try:
            # date_str = "30-10-2016 16:18"
            # format_str = "%d-%m-%Y %H:%M"
            format_str = "%Y-%m-%d"
            return datetime.strptime(date, format_str)
        except ValueError:
            return False

    def get(self, ID=None, Name=None, Date=None):
        date = self._pars_date(Date)
        if not date:
            return {'message': 'format of date is false'}, 404
        stat = 'None'
        if ID and date:
            stat = RankModel.find_by_id_day(ID, date)
        elif Name and date:
            stat = RankModel.find_by_name_day(Name, date)
        if stat:
            return stat.json_day(date)
        return {'message': 'not found pages in this site, day'}, 404


class RankDayList(Resource):
    def get(self, Date=None):
        result = PageModel.query.filter(PageModel.LastScanDate == Date).first()
        if result:
            return {'base_rank_day statistic - ' + Date: [RankModel.find_by_id_day(el.ID, Date).json_day(Date) for el in
                                                          SiteModel.query.all() if
                                                          RankModel.find_by_id_day(el.ID, Date)]}
        return {'message': 'not found base_rank_day statistic for this day'}, 404


class RankTime(Resource):
    def _pars_date(self, date):
        try:
            format_str = "%Y-%m-%d"
            return datetime.strptime(date, format_str)
        except ValueError:
            return False

    def get(self, ID=None, Name=None, Date1=None, Date2=None):
        date1 = self._pars_date(Date1)
        date2 = self._pars_date(Date2)
        if not date1 or not date2:
            return {'message': 'format of date is false'}, 404
        stat = 'None'
        if ID and date1 and date2:
            stat = RankModel.find_by_id_time(ID, date1, date2)
        elif Name and date1 and date2:
            stat = RankModel.find_by_name_time(Name, date1, date2)
        if stat:
            return stat.json_time(date1, date2)
        return {'message': 'not found pages in this site, time'}, 404


class RankTimeList(Resource):
    def get(self, Date1=None, Date2=None):
        result = PageModel.query.filter(PageModel.LastScanDate >= Date1, PageModel.LastScanDate <= Date2).first()
        if result:
            return {'base_rank_time statistic - ' + Date1 + '/' + Date2: [
                RankModel.find_by_id_time(el.ID, Date1, Date2).json_time(Date1, Date2) for el in SiteModel.query.all()
                if RankModel.find_by_id_time(el.ID, Date1, Date2)]}
        return {'message': 'not found base_rank_time statistic for this time'}, 404