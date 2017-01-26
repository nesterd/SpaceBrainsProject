from db import db


class RankModel(db.Model):
    __tablename__ = 'PersonPageRank'

    PersonsID = db.Column(
        db.Integer,
        db.ForeignKey('Persons.ID'),
        primary_key=True
    )
    PagesID = db.Column(
        db.Integer,
        db.ForeignKey('Pages.ID'),
        primary_key=True
    )
    Persons = db.relationship('PersonModel')
    Pages = db.relationship('PageModel')
    Rank = db.Column(db.Integer)
