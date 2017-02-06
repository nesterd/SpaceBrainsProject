from db import db
from sqlalchemy.orm import synonym


class KeywordModel(db.Model):
    __tablename__ = 'Keywords'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Name = db.Column(db.String(80))
    PersonID = db.Column(db.Integer, db.ForeignKey('Persons.ID'))
    # Person = db.relationship('PersonModel')

    id = synonym('ID')
    name = synonym('Name')
    person_id = synonym('PersonID')

    def __init__(self, name, person_id):
        self.name = name
        self.person_id = person_id

    def json(self):
        return {
            'id': self.id,
            'name': self.name,
            'person_id': self.person_id
        }

    @classmethod
    def find_by_name(cls, name):
        return cls.query.filter_by(name=name).first()

    @classmethod
    def find_by_id(cls, id):
        return cls.query.filter_by(id=id).first()

    def save_to_db(self):
        db.session.add(self)
        db.session.commit()

    def delete_from_db(self):
        db.session.delete(self)
        db.session.commit()
