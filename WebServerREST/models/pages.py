from db import db


class PageModel(db.Model):
    __tablename__ = 'Pages'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Url = db.Column(db.String(2048))
    FoundDateTime = db.Column(db.DateTime)
    LastScanDate = db.Column(db.DateTime)

    SiteID = db.Column(db.Integer, db.ForeignKey('Sites.ID'))
    Site = db.relationship('SiteModel')


