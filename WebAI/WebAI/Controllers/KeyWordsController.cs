using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;

namespace WebAI.Controllers
{
    public class KeyWordsController : Controller
    {
        IPersonService _dataservice = null;
        public KeyWordsController()
        {

        }
        public KeyWordsController(IPersonService dataservice)
        {
            _dataservice = dataservice;
        }
        public ActionResult Index()
        {
            return View(new List<KeyWord> {
                new KeyWord { Id = 1, Name = "Путина", PersonId = 1 },
                new KeyWord { Id = 2, Name = "Путину", PersonId = 1 }
            });
        }
    }
}