from db import db
from sqlalchemy.orm import synonym


class RoleModel(db.Model):
    __tablename__ = 'Roles'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(80))

    id = synonym('ID')
    name = synonym('Name')
    users = db.relationship('UserModel', lazy='dynamic')

    def __init__(self, id, name):
        self.id = id
        self.name = name
