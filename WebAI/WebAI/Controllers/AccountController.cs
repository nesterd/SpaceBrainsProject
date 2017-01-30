using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models.Account;
using System.Web.Security;
using System.Threading;

namespace WebAI.Controllers
{
    [AllowAnonymous]
    public class AccountController : Controller
    {
        
        IAuthenticationService authenticationService;

        public AccountController(IAuthenticationService authenticationService)
        {
            this.authenticationService = authenticationService;
        }


        [HttpGet]
        public ActionResult LogIn()
        {
            return View();
        }

        [HttpPost]
        public ActionResult LogIn(LogInViewModel logInModel)
        {
            if (!ModelState.IsValid)
                return View(logInModel);
            if (authenticationService.CheckUser(logInModel.Login, logInModel.Password))
            {
                FormsAuthentication.SetAuthCookie(logInModel.Login, true);
                return Redirect(FormsAuthentication.DefaultUrl);
            }
            return View();
        }

        public ActionResult LogOut()
        {
            FormsAuthentication.SignOut();

            return Redirect(FormsAuthentication.LoginUrl);
        }

        [HttpGet]
        public ActionResult CurrentUser()
        {
            var UserName = Thread.CurrentPrincipal.Identity?.Name;

            return PartialView("_CurrentUser", UserName);
        }
    }
}