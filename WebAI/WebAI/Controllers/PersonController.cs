using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;

namespace WebAI.Controllers
{
    public class PersonController : Controller
    {
        IPersonService personservice = null;
        
        public PersonController(IPersonService personservice)
        {
            this.personservice = personservice;
        }
  
        public ActionResult Index()
        {
            return View(personservice.);

        }
    }
}