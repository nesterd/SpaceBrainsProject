using BusinessLogic.Services;
using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;
using AutoMapper;
using BusinessLogic.DTO;

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
            return View(GetSites());
        }

        IEnumerable<SiteViewModel> GetSites()
        {
            var site = siteService.GetSites();
            Mapper.Initialize(cfg => cfg.CreateMap<SiteDTO, SiteViewModel>());
            return Mapper.Map<IEnumerable<SiteDTO>, IEnumerable<SiteViewModel>>(site);
        }



        [HttpGet]
        public ActionResult Add()

        {
            return View();
        }

        [HttpPost]
        public ActionResult Add(SiteViewModel newSite)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<SiteViewModel, SiteDTO>());
            siteService.AddSite(Mapper.Map<SiteViewModel, SiteDTO>(newSite));
            return RedirectToAction("Index");
        }




        [HttpGet]
        public ActionResult Edit(int id)
        {
            var siteDTO = siteService.GetSiteById(id);
            Mapper.Initialize(cfg => cfg.CreateMap<SiteDTO, SiteViewModel>());
            return View(Mapper.Map<SiteDTO,SiteViewModel>(siteDTO));
        }

        [HttpPost]
        public ActionResult Edit (SiteViewModel siteToChange)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<SiteViewModel, SiteDTO>());
            var siteDTO = Mapper.Map<SiteViewModel, SiteDTO>(siteToChange);
            siteService.ChangeSite(siteDTO);
            return RedirectToAction("Index");
        }

        public ActionResult Delete(int id)
        {
            siteService.DeleteSiteById(id);
            return RedirectToAction("Index");
        }
    }
}