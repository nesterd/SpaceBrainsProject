from db import db
from models.site import SiteModel


class PageModel(db.Model):
    __tablename__ = 'Pages'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Url = db.Column(db.String(2048))
    FoundDateTime = db.Column(db.DateTime)
    LastScanDate = db.Column(db.DateTime)

    SiteID = db.Column(db.Integer, db.ForeignKey('Sites.ID'))
    Site = db.relationship('SiteModel')

    def json(self):
        return {'ID':                       self.SiteID,
                'site':                     SiteModel.query.filter_by(ID=self.SiteID).first().Name,                  #self.SiteID,
                'total_count':              PageModel.query.filter_by(SiteID=self.SiteID).count(),
                'total_count_not_round':    PageModel.query.filter_by(SiteID=self.SiteID, LastScanDate=None).count(),
                'total_count_round':        PageModel.query.filter(PageModel.SiteID==self.SiteID, PageModel.LastScanDate!=None).count()}

    @classmethod
    def find_by_id(cls, ID):
        return cls.query.filter_by(SiteID=ID).first()

    @classmethod
    def find_by_name(cls, Name):
        siteID = SiteModel.query.filter_by(Name=Name).first()
        if siteID:
            return cls.query.filter_by(SiteID=siteID.ID).first()
