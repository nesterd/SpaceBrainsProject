using BusinessLogic.Services;
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
        ISiteService siteService = null;

        public SiteController()
        {

        }
        public SiteController(ISiteService siteService)
        {
            this.siteService = siteService;
        }
        public ActionResult Index()
        {
            return View();
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