from db import db


class KeywordModel(db.Model):
    __tablename__ = 'Keywords'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(80))
    PersonID = db.Column(db.Integer, db.ForeignKey('Persons.ID'))
    person = db.relationship('PersonModel')
