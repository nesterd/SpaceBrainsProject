using BusinessLogic.Services;
using BusinessLogic.Services.Base;
using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using Utility.enums;
using Utility.Helpers;
using WebAI.Models;

namespace WebAI.Controllers
{
    [Authorize]
    //[Authorize(Roles = "SuperAdmin")]
    public class HomeController : Controller
        
    {
        IAuthenticationService _authenticationService;
        public HomeController(IAuthenticationService authenticationService)
        {
            
            _authenticationService = authenticationService;
        }

        public ActionResult Index()
        {
            //FormsAuthentication.SignOut();
            return View(_authenticationService.IsSuperAdmin(CurrentAdminData.Name));
        }

 
        //public ActionResult Statistics()
        //{
        //    return View(new List<StatisticsViewModel> {
        //        new StatisticsViewModel { Id = 1,
        //        Site = new SiteViewModel { Id = 1, Name = "lenta.ru", Url = "lenta.ru" },
        //        CountAllLinks = 100,
        //        CountNotVisitedLinks = 20,
        //        CountVisitedLinks =90                
        //        }
        //    });
        //}

    }
}