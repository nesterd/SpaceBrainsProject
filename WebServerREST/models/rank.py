from db import db
from sqlalchemy.orm import synonym


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
    Rank = db.Column(db.Integer)

    page = synonym('PageID')
    person = synonym('PersonID')
    rank = synonym('Rank')

    def __init__(self, person, page, rank):
        self.page = page
        self.person = person
        self.rank = rank

    def json(self):
        return {
            'person_id': self.person,
            'rank': self.rank
        }

    @classmethod
    def find_by_person(cls, person):
        return cls.query.filter_by(person=person).first()
