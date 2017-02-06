from db import db
from sqlalchemy.orm import synonym
from models.site import SiteModel
from models.rank import RankModel


class PageModel(db.Model):
    __tablename__ = 'Pages'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Url = db.Column(db.String(2048))
    FoundDateTime = db.Column(db.DateTime)
    LastScanDate = db.Column(db.DateTime)
    SiteID = db.Column(db.Integer, db.ForeignKey('Sites.ID'))

    # Site = db.relationship('SiteModel')
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

    def json(self):
        return {
            'id': self.site_id,
            'site': SiteModel.query.filter_by(
                id=self.site_id
                ).first().name,
            'total_count': PageModel.query.filter_by(
                site_id=self.site_id
                ).count(),
            'total_count_not_round': PageModel.query.filter_by(
                site_id=self.site_id,
                scan=None
                ).count(),
            'total_count_round': PageModel.query.filter(
                PageModel.site_id == self.site_id,
                PageModel.scan is not None
                ).count()
            }

    @classmethod
    def find_by_id(cls, ID):
        return cls.query.filter_by(SiteID=ID).first()

    @classmethod
    def find_by_name(cls, Name):
        siteID = SiteModel.query.filter_by(Name=Name).first()
        if siteID:
            return cls.query.filter_by(SiteID=siteID.ID).first()
