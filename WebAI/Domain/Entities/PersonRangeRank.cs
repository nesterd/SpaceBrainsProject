using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Entities
{
    [Table("PersonRangeRank")]
    public class PersonRangeRank
    {
        public int PersonId { get; set; }
        public int PageId { get; set; }

        public int Rank { get; set; }

        [ForeignKey("PersonId")]
        public virtual Person Person { get; set; }
        [ForeignKey("PageId")]
        public virtual Page Page { get; set; }
    }
}
