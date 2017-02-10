using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.DTO
{
    public class CrawlerStatsDTO
       
    {
        public string Site { get; set; }
        public int SiteId { get; set; }
        public int CountAllLinks { get; set; }
        public int CountVisitedLinks { get; set; }
        public int CountNotVisitedLinks { get; set; }

    }
}
