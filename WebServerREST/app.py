from flask import Flask
from flask_restful import Api

from resources.site import Site, SiteList
from resources.person import Person, PersonList
# from resources.pages import Page
from models.pages import PageModel
from resources.keyword import Keyword, KeywordList

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///data.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.secret_key = ''
api = Api(app)


@app.before_first_request
def create_tables():
    db.create_all()


api.add_resource(Site, '/site/<int:ID>', '/site/<string:Name>')
api.add_resource(SiteList, '/sites')
api.add_resource(Person, '/person/<string:Name>', '/person/<int:ID>')
api.add_resource(PersonList, '/persons')
api.add_resource(Keyword, '/keyword/<string:Name>', '/keyword/<int:ID>')
api.add_resource(KeywordList, '/keywords')

if __name__ == '__main__':
    from db import db
    db.init_app(app)
    app.run(port=5000, debug=True)
