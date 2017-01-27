# Версия 1.0
#Для каждого сайта в отдельности, и для всех сразу, отображать следующую информацию:
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import func

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:passwd@localhost:3306/ratepersons'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

class PageModel(db.Model):
    __tablename__ = 'Pages'

    ID = db.Column(db.Integer, primary_key=True, autoincrement=True)
    SiteID = db.Column(db.Integer, db.ForeignKey('Sites.ID'))
    Url = db.Column(db.String(2048))
    FoundDateTime = db.Column(db.DateTime)
    LastScanDate = db.Column(db.DateTime)
    Site = db.relationship('SiteModel')

class SiteModel(db.Model):
    __tablename__ = 'Sites'

    ID = db.Column(db.Integer, primary_key=True)
    Name = db.Column(db.String(80))
#1.	Сколько ссылок с этого сайта всего находится в БД;

# session.query(func.count(Pages.id)).scalar()
PageModel.query.filter(PageModel.ID).count()

#2.	Сколько ссылок с этого сайта не обходилось никогда, но они уже хранятся в БД;

PageModel.query.filter(PageModel.LastScanDate == None).count()

#3.	Сколько ссылок с этого сайта уже обходилось когда-либо

PageModel.query.filter(PageModel.LastScanDate != None).count()

"""

Таблицы Pages

Имя столбца	         Описание	                    Тип	            Пример
ID	                 Идентификатор страницы сайта	Int	1
Url	                 Полный URL адрес страницы	    nvarchar(2048)	«http://lenta.ru/rubrics/world/»
SiteID	             Идентификатор сайта (ресурса),
                     который предоставлен
                     администратором для анализа.
                     Является внешним ключом к
                     таблице Sites	                int	1
FoundDateTime	     Дата и время обнаружения
                     страницы системой	            datetime	    “10-10-2015 15:23”
LastScanDate	     Дата и время последней
                     проверки на упоминания	        datetime	    “03-04-2015 15:23”

"""