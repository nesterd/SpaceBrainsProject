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
    [Authorize]
    public class SiteController : Controller
    {
        ISiteService _siteService = null;
        IMapper _mapper = null;
        
        public SiteController(ISiteService siteService, IMapper mapper)
        {
            _siteService = siteService;
            _mapper = mapper;
        }
        
        public ActionResult Index()
        {
            return View(GetSites());
        }

        IEnumerable<SiteViewModel> GetSites()
        {
            var site = _siteService.GetSites();
            return _mapper.Map<IEnumerable<SiteDTO>, IEnumerable<SiteViewModel>>(site);
        }
        
        [HttpGet]
        public ActionResult Add()

        {
            return View();
        }

        [HttpPost]
        public ActionResult Add(SiteViewModel newSite)
        {
            _siteService.AddSite(_mapper.Map<SiteViewModel, SiteDTO>(newSite));
            return RedirectToAction("Index");
        }
        
        [HttpGet]
        public ActionResult Edit(int id)
        {
            var siteDTO = _siteService.GetSiteById(id);
            return View(_mapper.Map<SiteDTO,SiteViewModel>(siteDTO));
        }

        [HttpPost]
        public ActionResult Edit (SiteViewModel siteToChange)
        {
            var siteDTO = _mapper.Map<SiteViewModel, SiteDTO>(siteToChange);
            _siteService.ChangeSite(siteDTO);
            return RedirectToAction("Index");
        }

        public ActionResult Delete(int id)
        {
            _siteService.DeleteSiteById(id);
            return RedirectToAction("Index");
        }
    }
}