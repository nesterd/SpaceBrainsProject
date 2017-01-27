from db import db


class PersonModel(db.Model):
    __tablename__ = 'Persons'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(80))

    def __init__(self, Name):
        self.Name = Name

    def json(self):
        return {'id': self.ID, 'name': self.Name}

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
