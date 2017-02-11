from db import db
from sqlalchemy.orm import synonym
# from models.rank import RankModel


class PersonModel(db.Model):
    __tablename__ = 'Persons'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Name = db.Column(db.String(80))
    AdminID = db.Column(db.Integer, db.ForeignKey('Users.AdminID'))

    id = synonym('ID')
    name = synonym('Name')
    admin = synonym('AdminID')
    keywords = db.relationship('KeywordModel', lazy='dynamic')
    pages = db.relationship('RankModel', lazy='dynamic')

    def __init__(self, name, admin):
        self.name = name
        self.admin = admin

    def json(self):
        return {
            'id': self.id,
            'name': self.name,
            'keywords': [keyword.json() for keyword in self.keywords.all()],
            'admin_id': self.admin
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
