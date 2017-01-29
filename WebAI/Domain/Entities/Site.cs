using Domain.Entities.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Entities
{
    [Table("Sites")]
    public class Site
        : Base.BaseDbEntity
    {
        public string Name { get; set; }
        public int AdminId { get; set; }

        [ForeignKey("AdminId")]
        public virtual User Admin { get; set; }

        public virtual ICollection<Page> Pages { get; set; }
    }
}
