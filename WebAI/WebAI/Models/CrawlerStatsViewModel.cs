using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebAI.Models
{
    public class CrawlerStatsViewModel
        : Base.BaseModel
    {
        public string Site { get; set; }
        public int SiteId { get; set; }
        public int CountAllLinks { get; set; }
        public int CountVisitedLinks { get; set; }
        public int CountNotVisitedLinks { get; set; }

    }
}