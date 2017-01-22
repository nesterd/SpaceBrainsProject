using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;

namespace WebAI.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Persons()
        {
            return View( new List<Person> {
                new Person { Id = 1, Name = "Путин В.В." },
                new Person { Id = 2, Name = "Медведев Д.А." }
            });
        }

        public ActionResult KeyWords()
        {
         
            return View( new List<KeyWord> {
                new KeyWord { Id = 1, Name = "Путина", PersonId = 1 },
                new KeyWord { Id = 2, Name = "Путину", PersonId = 1 }
            });
        }

        public ActionResult Sites()
        {
            return View(new List<Site> {
                new Site { Id = 1, Name = "lenta.ru", Url = "lenta.ru" }
            });
        }
        public ActionResult Statistics()
        {
            return View(new List<Statistics> {
                new Statistics { Id = 1,
                Site = new Site { Id = 1, Name = "lenta.ru", Url = "lenta.ru" },
                CountAllLinks = 100,
                CountNotVisitedLinks = 20,
                CountVisitedLinks =90                
                }
            });
        }

    }
}