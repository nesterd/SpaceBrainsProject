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
        IPersonService _dataservice = null;
        public PersonController()
        {

        }
        public PersonController(IPersonService dataservice)
        {
                _dataservice = dataservice;
        }
  
        public ActionResult Index()
        {
            return View(new List<Person> {
            new Person { Id = 1, Name = "Путин В.В." },
            new Person { Id = 2, Name = "МедведевД.А." }
        });

        }
    }
}