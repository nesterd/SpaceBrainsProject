from db import db
from sqlalchemy.orm import synonym


class UserModel(db.Model):
    __tablename__ = 'Users'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    Name = db.Column(db.String(75))
    Login = db.Column(db.String(15))
    Password = db.Column(db.String(255))
    Is_Admin = db.Column(db.Boolean)
    AdminID = db.Column(db.Integer, db.ForeignKey('Users.ID'))
    id = synonym('ID')

    def __init__(self, Login, Password):
        self.Login = Login
        self.Password = Password

    def save_to_db(self):
        db.session.add(self)
        db.session.commit()

    @classmethod
    def find_by_username(cls, Login):
        return cls.query.filter_by(Login=Login).first()

    @classmethod
    def find_by_id(cls, ID):
        return cls.query.filter_by(id=ID).first()
