using Domain.Entities.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Entities
{
    [Table("Persons")]
    public class Person
        : Base.BaseDbEntity
    {
        public string Name { get; set; }
        public int AdminId { get; set; }

        [ForeignKey ("AdminId")]
        public virtual User Admin { get; set; }  
        public virtual ICollection<KeyWord> KeyWords { get; set; }
        public virtual ICollection<PersonPageRank> Ranks { get; set; }
    }
}
