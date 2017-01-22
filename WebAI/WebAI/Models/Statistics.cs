using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebAI.Models
{
    public class Statistics
        : Base.BaseModel
    {
        public Site Site { get; set; }
        public int CountAllLinks { get; set; }
        public int CountVisitedLinks { get; set; }
        public int CountNotVisitedLinks { get; set; }

    }
}