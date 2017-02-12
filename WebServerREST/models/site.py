from db import db
from sqlalchemy.orm import synonym


class SiteModel(db.Model):
    __tablename__ = 'Sites'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Name = db.Column(db.String(80))
    AdminID = db.Column(db.Integer, db.ForeignKey('Users.AdminID'))

    id = synonym('ID')
    name = synonym('Name')
    admin = synonym('AdminID')
    pages = db.relationship('PageModel', lazy='dynamic')

    def __init__(self, admin, name=None):
        self.name = name
        self.admin = admin

    def json(self):
        return {
            'id': self.id,
            'name': self.name,
            'admin_id': self.admin
        }
        '''return {
            'id': self.id,
            'name': self.name,
            'pages': [page.json() for page in self.pages.all()]
        }'''

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
