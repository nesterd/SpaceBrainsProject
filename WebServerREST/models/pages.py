from db import db
from sqlalchemy.orm import synonym
from models.site import SiteModel


class PageModel(db.Model):
    __tablename__ = 'Pages'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Url = db.Column(db.String(2048))
    FoundDateTime = db.Column(db.DateTime)
    LastScanDate = db.Column(db.DateTime)
    SiteID = db.Column(db.Integer, db.ForeignKey('Sites.ID'))

    id = synonym('ID')
    url = synonym('Url')
    found = synonym('FoundDateTime')
    scan = synonym('LastScanDate')
    site_id = synonym('SiteID')
    persons = db.relationship('RankModel', lazy='dynamic')

    def __init__(self, url, found, scan, site_id):
        self.url = url
        self.found = found
        self.scan = scan
        self.site_id = site_id

    def json(self, permission):
        def _query(self):
            query = db.session.query(PageModel, SiteModel)
            query = query.join(SiteModel, PageModel.site_id == SiteModel.id)
            return query.filter(
                SiteModel.id == PageModel.site_id,
                SiteModel.admin == permission
            )
        return {
            'id': self.site_id,
            'site': SiteModel.query.filter_by(id=self.site_id).first().name,
            'total_count': _query(self).filter(
                PageModel.site_id == self.site_id
            ).count(),
            'total_count_not_round': _query(self).filter(
                PageModel.site_id == self.site_id,
                PageModel.scan is None
            ).count(),
            'total_count_round': _query(self).filter(
                PageModel.site_id == self.site_id,
                PageModel.scan is not None
            ).count()
        }

    @classmethod
    def find_by_id(cls, id):
        return cls.query.filter_by(site_id=id).first()

    @classmethod
    def find_by_name(cls, name):
        siteid = SiteModel.query.filter_by(name=name).first()
        if siteid:
            return cls.query.filter_by(site_id=siteid.id).first()


class SiteModel_for_json(SiteModel):
    def json(self):
        return {
            'id': self.id,
            'site': SiteModel.query.filter_by(id=self.id).first().name,
            'total_count': 0,
            'total_count_not_round': 0,
            'total_count_round': 0
        }
