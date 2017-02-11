from db import db
from sqlalchemy.orm import synonym
from models.site import SiteModel
from models.pages import PageModel
from models.person import PersonModel
from sqlalchemy.sql.expression import func
from datetime import datetime


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

    page = synonym('PageID')
    person = synonym('PersonID')
    rank = synonym('Rank')

    def __init__(self, person, page, rank):
        self.page = page
        self.person = person
        self.rank = rank

    def json(self):
        return {'person_id': self.person, 'rank': self.rank}

    @classmethod
    def find_by_id(cls, id):
        site = SiteModel_for_json.query.filter_by(id=id).first()
        if site:
            return site

    @classmethod
    def find_by_name(cls, name):
        site = SiteModel_for_json.query.filter_by(name=name).first()
        if site:
            return site

    @classmethod
    def find_by_id_day(cls, id, date):
        site = SiteModel_for_json.query.filter_by(id=id).first()
        date = PageModel.query.filter(PageModel.scan == date).first()
        if site and date:
            return site

    @classmethod
    def find_by_name_day(cls, name, date):
        site = SiteModel_for_json.query.filter_by(name=name).first()
        date = PageModel.query.filter(PageModel.scan == date).first()
        if site and date:
            return site

    @classmethod
    def find_by_id_time(cls, id, date1, date2):
        site = SiteModel_for_json.query.filter_by(id=id).first()
        date = PageModel.query.filter(
            PageModel.scan >= date1,
            PageModel.scan <= date2
        ).first()
        if site and date:
            return site

    @classmethod
    def find_by_name_time(cls, name, date1, date2):
        site = SiteModel_for_json.query.filter_by(name=name).first()
        date = PageModel.query.filter(
            PageModel.scan >= date1,
            PageModel.scan <= date2
        ).first()
        if site and date:
            return site

    @classmethod
    def find_by_person(cls, person):
        return cls.query.filter_by(person=person).first()


class SiteModel_for_json(SiteModel):
    def _query(self, id, site_id, date, date1, date2):
        query = db.session.query(func.sum(RankModel.rank))
        query = query.join(PageModel, RankModel.page == PageModel.id)
        query = query.join(SiteModel, PageModel.site_id == SiteModel.id)
        query = query.join(PersonModel, RankModel.person == PersonModel.id)
        if date1 and date2:
            return query.filter(
                RankModel.person == id,
                SiteModel.id == site_id,
                func.DATE(PageModel.scan) >= date1,
                func.DATE(PageModel.scan) <= date2
            )
        if date:
            return query.filter(
                RankModel.person == id,
                SiteModel.id == site_id,
                func.DATE(PageModel.scan) == date
            )
        return query.filter(
            RankModel.person == id,
            SiteModel.id == site_id
        )

    def _get_rank_for_person(
            self, id, site_id, date=None, date1=None, date2=None):
        query = self._query(id, site_id, date, date1, date2)
        query = query.one()
        if query[0] or str(query[0]) == '0':
            return str(query[0])
        return 'not found statistic for this person'  # Realy? String?

    def json(self):
        return {
            'id': self.id,
            'name': self.name,
            'persons': [{
                'id': el.id,
                'name': el.name,
                'rank': self._get_rank_for_person(el.id, self.id)
                } for el in PersonModel.query.all()]
            }

    def json_day(self, date):
        if PageModel.query.filter(PageModel.scan == date).first():
            return {
                'id': self.id,
                'name': self.name,
                'persons': [{
                    'id': el.id,
                    'name': el.name,
                    'rank': self._get_rank_for_person(el.id, self.id, date)
                    } for el in PersonModel.query.all()]
                }
        return None  # Realy?

    def json_time(self, date1, date2):
        if PageModel.query.filter(
                PageModel.scan >= date1,
                PageModel.scan <= date2
                ).first():
            return {
                'id': self.id,
                'name': self.name,
                'persons': [{
                    'id': el.id,
                    'name': el.name,
                    'rank': self._get_rank_for_person(
                        el.id, self.id, None, date1, date2)
                    } for el in PersonModel.query.all()]
                }
        return None  # None? Realy?
