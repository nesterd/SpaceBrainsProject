from db import db


class PersonModel(db.Model):
    __tablename__ = 'Persons'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(80))

    def __init__(self, name):
        self.name = Name

    def json(self):
        return {'id': self.ID, 'name': self.name}

    @classmethod
    def find_by_name(cls, name):
        return cls.query.filter_by(name=name).first()

    @classmethod
    def find_by_id(cls, _id):
        return cls.query.filter_by(id=_id).first()  # TODO?

    def save_to_db(self):
        db.session.add(self)
        db.session.commit()

    def delete_from_db(self):
        db.session.delete(self)
        db.session.commit()
