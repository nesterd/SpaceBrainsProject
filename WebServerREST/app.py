import logging
from logging.handlers import RotatingFileHandler

from flask import Flask
from flask_restful import Api
from flask_jwt import JWT
from datetime import timedelta

from security import authenticate, identity, Login
from resources.user import UserRegister, UserListView, User,\
    UserRestorePassword, UserChangePassword, UserStatus
from resources.site import Site, SiteList
from resources.person import Person, PersonList
from resources.stats import Pages as Stats, StatList, Rank, RankList,\
    RankDay, RankDayList, RankTime, RankTimeList
from models.pages import PageModel
from resources.keyword import Keyword, KeywordList

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = (
    'mysql+pymysql://user:pass@host:3306/database')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['JWT_SECRET_KEY'] = 'password'
app.config['JWT_AUTH_URL_RULE'] = '/auth_old'
app.config['JWT_EXPIRATION_DELTA'] = timedelta(seconds=7200)
app.secret_key = ''
api = Api(app)


@app.before_first_request
def create_tables():
    db.create_all()

jwt = JWT(app, authenticate, identity)  # /auth_old

# Users urls
api.add_resource(Login, '/auth')
api.add_resource(UserStatus, '/status')
api.add_resource(UserRegister, '/register')
api.add_resource(UserListView, '/users')
api.add_resource(User, '/user/<int:id>')
api.add_resource(UserRestorePassword, '/user/restore')
api.add_resource(UserChangePassword, '/user/changepass')

# Reference book urls
api.add_resource(Site, '/site/<int:id>', '/site/<string:name>')
api.add_resource(SiteList, '/sites')
api.add_resource(Person, '/person/<string:name>', '/person/<int:id>')
api.add_resource(PersonList, '/persons')
api.add_resource(Keyword, '/keyword/<string:name>', '/keyword/<int:id>')
api.add_resource(KeywordList, '/keywords')

# Statistic urls
api.add_resource(StatList, '/base_statistic')
api.add_resource(
    Stats, '/base_statistic/<int:id>', '/base_statistic/<string:name>')
api.add_resource(
    Rank, '/rank_statistic/<int:id>', '/rank_statistic/<string:name>')
api.add_resource(RankList, '/rank_statistic')
api.add_resource(
    RankDay,
    '/day_statistic/<int:id>/<string:date>',
    '/day_statistic/<string:name>/<string:date>'
)
api.add_resource(RankDayList, '/day_statistic/base/<string:date>')
api.add_resource(
    RankTime,
    '/time_statistic/<int:id>/<string:date1>/<string:date2>',
    '/time_statistic/<string:name>/<string:date1>/<string:date2>'
)
api.add_resource(
    RankTimeList, '/time_statistic/base/<string:date1>/<string:date2>')

if __name__ == '__main__':
    log = logging.getLogger(__name__)
    log.setLevel(logging.DEBUG)
    # add a file handler
    fh = logging.handlers.TimedRotatingFileHandler(
        "log/app_log.log",
        when='M',
        interval=86400,
        backupCount=0
        )
    fh.setLevel(logging.DEBUG)
    # create a formatter and set the formatter for the handler.
    frmt = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    fh.setFormatter(frmt)
    # add the Handler to the logger
    log.addHandler(fh)

    logging.getLogger('werkzeug').addHandler(fh)

    from db import db
    db.init_app(app)
    # app.run(port=5000, debug=True)
    app.run(host='93.174.131.56', debug=True)
