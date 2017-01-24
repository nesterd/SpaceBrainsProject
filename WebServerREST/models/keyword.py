from db import db


class KeywordModel(db.Model):
    __tablename__ = 'keywords'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(80))
    person_id = db.Column(db.Integer, db.ForeignKey('persons.id'))
    person = db.relationship('PersonModel')