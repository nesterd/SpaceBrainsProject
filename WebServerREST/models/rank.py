from db import db
from models.site import SiteModel
from models.person import PersonModel
from models.pages import PageModel
from sqlalchemy.sql.expression import func


class SiteModel_for_json(SiteModel):
    def _query(self, ID, siteID, date, date1, date2):
        query = db.session.query(func.sum(RankModel.Rank))
        query = query.join(PageModel, RankModel.PageID == PageModel.ID)
        query = query.join(SiteModel, PageModel.SiteID == SiteModel.ID)
        query = query.join(PersonModel, RankModel.PersonID == PersonModel.ID)
        if date1 and date2:
            return query.filter(RankModel.PersonID == ID, SiteModel.ID == siteID, PageModel.LastScanDate >= date1,
                                PageModel.LastScanDate <= date2)
        if date:
            return query.filter(RankModel.PersonID == ID, SiteModel.ID == siteID, PageModel.LastScanDate == date)
        return query.filter(RankModel.PersonID == ID, SiteModel.ID == siteID)

    def _get_rank_for_person(self, ID, siteID, date=None, date1=None, date2=None):
        query = self._query(ID, siteID, date, date1, date2)
        query = query.one()
        if query[0] or str(query[0]) == '0':
            return str(query[0])
        return 'not found statistic for this person'

    def json(self):
        return {'ID': self.ID,
                'Name': self.Name,
                'Persons': [{'ID': el.ID,
                             'Name': el.Name,
                             'Rank': self._get_rank_for_person(el.ID, self.ID)}
                            for el in PersonModel.query.all()]}

    def json_day(self, date):
        if PageModel.query.filter(PageModel.LastScanDate == date).first():
            return {'ID': self.ID,
                    'Name': self.Name,
                    'Persons': [{'ID': el.ID,
                                 'Name': el.Name,
                                 'Rank': self._get_rank_for_person(el.ID, self.ID, date)}
                                for el in PersonModel.query.all()]}
        return None

    def json_time(self, date1, date2):
        if PageModel.query.filter(PageModel.LastScanDate >= date1, PageModel.LastScanDate <= date2).first():
            return {'ID': self.ID,
                    'Name': self.Name,
                    'Persons': [{'ID': el.ID,
                                 'Name': el.Name,
                                 'Rank': self._get_rank_for_person(el.ID, self.ID, None, date1, date2)}
                                for el in PersonModel.query.all()]}
        return None


class RankModel(db.Model):
    __tablename__ = 'PersonPageRank'

    PersonID = db.Column(
        db.Integer,
        db.ForeignKey('Persons.ID'),
        primary_key=True
    )
    PageID = db.Column(
        db.Integer,
        db.ForeignKey('Pages.ID'),
        primary_key=True
    )
    Person = db.relationship('PersonModel')
    Page = db.relationship('PageModel')
    Rank = db.Column(db.Integer)

    def json(self):
        return {"PersonID": self.PersonID, "Rank": self.Rank}

    @classmethod
    def find_by_id(cls, ID):
        site = SiteModel_for_json.query.filter_by(ID=ID).first()
        if site:
            return site

    @classmethod
    def find_by_name(cls, Name):
        site = SiteModel_for_json.query.filter_by(Name=Name).first()
        if site:
            return site

    @classmethod
    def find_by_id_day(cls, ID, date):
        site = SiteModel_for_json.query.filter_by(ID=ID).first()
        date = PageModel.query.filter(PageModel.LastScanDate == date).first()
        if site and date:
            return site

    @classmethod
    def find_by_name_day(cls, Name, date):
        site = SiteModel_for_json.query.filter_by(Name=Name).first()
        date = PageModel.query.filter(PageModel.LastScanDate == date).first()
        if site and date:
            return site

    @classmethod
    def find_by_id_time(cls, ID, date1, date2):
        site = SiteModel_for_json.query.filter_by(ID=ID).first()
        date = PageModel.query.filter(PageModel.LastScanDate >= date1, PageModel.LastScanDate <= date2).first()
        if site and date:
            return site

    @classmethod
    def find_by_name_time(cls, Name, date1, date2):
        site = SiteModel_for_json.query.filter_by(Name=Name).first()
        date = PageModel.query.filter(PageModel.LastScanDate >= date1, PageModel.LastScanDate <= date2).first()
        if site and date:
            return site

    @classmethod
    def find_by_person(cls, PersonID):
        return cls.query.filter_by(PersonID=PersonID).first()

