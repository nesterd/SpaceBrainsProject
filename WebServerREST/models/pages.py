from db import db


class PageModel(db.Model):
    __tablename__ = 'Pages'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    SiteID = db.Column(db.Integer, db.ForeignKey('Sites.ID'))
    Url = db.Column(db.String(2048))
    FoundDateTime = db.Column(db.DateTime)
    LastScanDate = db.Column(db.DateTime)
    Site = db.relationship('SiteModel')
