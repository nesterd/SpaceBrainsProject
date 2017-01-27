from db import db


class KeywordModel(db.Model):
    __tablename__ = 'Keywords'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(80))
    PersonID = db.Column(db.Integer, db.ForeignKey('Persons.ID'))
    person = db.relationship('PersonModel')

    def __init__(self, Name, PersonID):
        self.Name = Name
        self.PersonID = PersonID

    def json(self):
        return {'id': self.ID, 'name': self.Name, 'person_id': self.PersonID}

    @classmethod
    def find_by_name(cls, Name):
        return cls.query.filter_by(Name=Name).first()

    @classmethod
    def find_by_id(cls, ID):
        return cls.query.filter_by(ID=ID).first()  # TODO?

    def save_to_db(self):
        db.session.add(self)
        db.session.commit()

    def delete_from_db(self):
        db.session.delete(self)
        db.session.commit()
