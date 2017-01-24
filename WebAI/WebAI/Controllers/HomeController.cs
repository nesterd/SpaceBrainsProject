using BusinessLogic.Services.Base;
using DataAccess.Repositories.Base;
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
        public HomeController()
        {

        }

        public ActionResult Index()
        {
            return View();
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