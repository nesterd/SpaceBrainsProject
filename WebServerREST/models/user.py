from db import db
from sqlalchemy.orm import synonym
from models.roles import RoleModel


class UserModel(db.Model):
    __tablename__ = 'Users'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Name = db.Column(db.String(75))
    Login = db.Column(db.String(15))
    Password = db.Column(db.String(255))
    Email = db.Column(db.String(255))
    RoleID = db.Column(db.Integer, db.ForeignKey('Roles.ID'))
    AdminID = db.Column(db.Integer, db.ForeignKey('Users.AdminID'))

    id = synonym('ID')
    name = synonym('Name')
    username = synonym('Login')
    password = synonym('Password')
    email = synonym('Email')
    role = synonym('RoleID')
    admin = synonym('AdminID')
    sites = db.relationship('SiteModel')
    persons = db.relationship('PersonModel')

    def __init__(self, username, password, name, email, role, admin, id=None):
        self.id = id
        self.username = username
        self.password = password
        self.name = name
        self.email = email
        self.role = role
        self.admin = admin

    def json():
        return {
            'id': self.id,
            'name': self.name,
            'role': self.role,
            'admin': self.admin
        }

    def save_to_db(self):
        db.session.add(self)
        db.session.commit()

    @classmethod
    def find_by_username(cls, username):
        return cls.query.filter_by(username=username).first()

    @classmethod
    def find_by_id(cls, id):
        return cls.query.filter_by(id=id).first()
