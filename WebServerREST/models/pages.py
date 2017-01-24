from db import db


class PageModel(db.Model):
    __tablename__ = 'pages'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    site_id = db.Column(db.Integer, db.ForeignKey('sites.id'))
    url = db.Column(db.String(2048))
    found_date = db.Column(db.DateTime)
    scan_date = db.Column(db.DateTime)
    site = db.relationship('SiteModel')