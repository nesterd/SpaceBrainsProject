from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://root:passwd@localhost/mydatabase'
db = SQLAlchemy(app)


class Sites(db.Model):
    __tablename__ = 'Sites'
    ID = db.Column('id', db.Integer, primary_key=True)
    Name = db.Column('name', db.String)

    def __init__(self, id, name):
        self.id = ID
        self.name = Name


class CRUD:
    def __init__(self, db):
        self.db = db

    def get(self, method, tabel_model, id=None):
        if method == 'GET':
            if not id:
                result = tabel_model.query.all()
            else:
                result = tabel_model.query.filter_by(id).first()
            return result
        else:
            return False, 'unsupported this method' + method

    def put(self, method, data_for_add_table):
        if method == 'PUT':
            # insert
            self.db.session.add(data_for_add_table)
            db.session.commit()
            return True
        else:
            return False, 'unsupported this method' + method

    def update(self, method, tabel_model, id):
        if method == 'PUT':
            update_this = tabel_model.query.filter_by(id).first()
            update_this.data = 'New data!'
            db.session.commit()
            return True
        else:
            return False, 'unsupported this method' + method

    def delete(self, method, tabel_model, id):
        if method == 'DEL':
            delete_this = tabel_model.query.filter_by(id).first()
            self.db.session.delete(delete_this)
            db.session.commit()
            return True
        else:
            return False, 'unsupported this method' + method

tabel_model = Sites
id_for_table_of_sites = 7
name_for_table_of_sites = 'ya.ru'
data_for_add_table = Sites(id_for_table_of_sites, name_for_table_of_sites)

# GET
obj = CRUD(db)
obj.get('GET', tabel_model)
obj.get('GET', tabel_model, 7)
# PUT
obj = CRUD(db)
obj.put('PUT', data_for_add_table)
# UPDATE
obj = CRUD(db)
obj.update('PUT', data_for_add_table, 7)
# DELETE
obj = CRUD(db)
obj.delete('DEL', tabel_model, 7)
