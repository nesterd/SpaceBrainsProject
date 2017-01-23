from flask import Flask
from flask_restful import Api

from resources.site import Site, SiteList

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///data.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.secret_key = ''
api = Api(app)

@app.before_first_request
def create_tables():
    db.create_all()


api.add_resource(Site, '/site/<int:id>', '/site/<string:name>')
api.add_resource(SiteList, '/sites')

if __name__ == '__main__':
    from db import db
    db.init_app(app)
    app.run(port=5000, debug=True)