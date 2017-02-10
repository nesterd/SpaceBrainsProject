using AutoMapper;
using BusinessLogic.DTO;
using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Xml;
using WebAI.Models;

namespace WebAI.Controllers
{
    public class CrawlerStatsController : Controller
    {

        ICrawlerStatsService crawlerStatsService = null;
        public CrawlerStatsController(ICrawlerStatsService crawlerStatsService)
        {
            this.crawlerStatsService = crawlerStatsService;
        }
        
        public IEnumerable<CrawlerStatsViewModel> GetCrawlerStats()
        {

            var stat = crawlerStatsService.GetCrawlerStats();
            Mapper.Initialize(cfg => cfg.CreateMap<CrawlerStatsDTO, CrawlerStatsViewModel>());
            return Mapper.Map<IEnumerable<CrawlerStatsDTO>, IEnumerable<CrawlerStatsViewModel>>(stat);
        }


        public ActionResult Index()
        {
            return View(GetCrawlerStats());
        }

        public ActionResult PartialCrawlerStats()
        {
             return PartialView("_crawlerStats", GetCrawlerStats());
        }

        public void ExportToCSV()
        {

        StringWriter sw = new StringWriter();

        sw.WriteLine("\"Sate name\";\"All pages in base\";\"Checked crawlers\";\"Not checked crawlers\"");

        Response.ClearContent();
        Response.AddHeader("content-disposition", "attachment;filename=Exported_CrawlerStats.csv");
            Response.ContentType = "text/csv";

            foreach (var line in GetCrawlerStats())
            {
                sw.WriteLine(string.Format("\"{0}\";\"{1}\";\"{2}\";\"{3}\"",
                                           line.Site,
                                           line.CountAllLinks,
                                           line.CountVisitedLinks,
                                           line.CountNotVisitedLinks));
            }

            Response.Write(sw.ToString());

            Response.End();
        }

    }

}