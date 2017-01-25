using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;

namespace WebAI.Controllers
{
    public class SiteController : Controller
    {
        IPersonService _dataservice = null;

        public SiteController()
        {

        }
        public SiteController(IPersonService dataservice)
        {
            _dataservice = dataservice;
        }
        public ActionResult Index()
        {
            return View(new List<Site> {
                new Site { Id = 1, Name = "lenta.ru", Url = "lenta.ru" }
            });
        }
        [ActionName ("Add")] 
        public ActionResult Add()

        {
            return View("Edit");
        }


        public ActionResult Edit()

        {
            return View("Edit");
        }
    }
}