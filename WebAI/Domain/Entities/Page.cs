using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Entities
{
    [Table("Pages")]
    public class Page
        : Base.BaseDbEntity
    {
        [Column(TypeName = "varchar")]
        [StringLength(2048)]
        public string Url { get; set; }

        public int SiteId { get; set; }
        public DateTime FoundDateTime { get; set; }
        public DateTime? LastScanDate { get; set; }

        [ForeignKey("SiteId")]
        public virtual Site Site { get; set; }

        public virtual ICollection<PersonPageRank> Ranks { get; set; }
    }
}
