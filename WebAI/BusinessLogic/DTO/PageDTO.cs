using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.DTO
{
    public class PageDTO
        : Base.BaseDTO
    {
        public string Url { get; set; }
        public int SiteId { get; set; }
        public DateTime FoundDateTime { get; set; }
        public DateTime? LastScanDate { get; set; }

    }
}
