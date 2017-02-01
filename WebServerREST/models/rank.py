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

    def json(self):
        return {"PersonsID": self.PersonsID, "Rank": self.Rank}

    @classmethod
    def find_by_person(cls, PersonsID):
        return cls.query.filter_by(PersonsID=PersonsID).first()
