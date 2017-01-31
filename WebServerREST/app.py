from flask import Flask
from flask_restful import Api
from flask_jwt import JWT

from security import authenticate, identity
from resources.user import UserRegister

from resources.site import Site, SiteList
from resources.person import Person, PersonList
from resources.stats import Pages as Stats, StatList
from models.pages import PageModel
from resources.keyword import Keyword, KeywordList

import logging
from logging.handlers import RotatingFileHandler


app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://oldfox:StrongPassword111@93.174.131.56:3306/ratepersons'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.secret_key = ''
api = Api(app)

@app.before_first_request
def create_tables():
    db.create_all()

jwt = JWT(app, authenticate, identity)  # /auth

api.add_resource(Site, '/site/<int:ID>', '/site/<string:Name>')
api.add_resource(SiteList, '/sites')
api.add_resource(Person, '/person/<string:Name>', '/person/<int:ID>')
api.add_resource(PersonList, '/persons')
api.add_resource(Keyword, '/keyword/<string:Name>', '/keyword/<int:ID>')
api.add_resource(KeywordList, '/keywords')
api.add_resource(Stats, '/base_statistic/<int:ID>', '/base_statistic/<string:Name>') #Stats

api.add_resource(UserRegister, '/register')

if __name__ == '__main__':

    log = logging.getLogger(__name__)
    log.setLevel(logging.DEBUG)
    # add a file handler
    fh = logging.handlers.TimedRotatingFileHandler("log/app_log.log",when='M',interval=86400,backupCount=0)
    fh.setLevel(logging.DEBUG)
    # create a formatter and set the formatter for the handler.
    frmt = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    fh.setFormatter(frmt)
    # add the Handler to the logger
    log.addHandler(fh)

    logging.getLogger('werkzeug').addHandler(fh)
    
    from db import db
    db.init_app(app)
    #app.run(port=5000, debug=True)
    app.run(host='93.174.131.56', debug=True)
