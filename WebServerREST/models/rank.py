from db import db


class RankModel(db.Model):
    __tablename__ = 'PersonPageRank'

    persons_id = db.Column(db.Integer, db.ForeignKey('persons.id'), primary_key=True)
    pages_id = db.Column(db.Integer, db.ForeignKey('pages.id'), primary_key=True)
    persons = db.relationship('PersonModel')
    pages = db.relationship('PageModel')
    rank = db.Column(db.Integer)

