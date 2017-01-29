using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Domain.Entities.Users
{
    [Table("Users")]
    public class User
        : Base.BaseDbEntity
    {
        public string Name { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public bool IsAdmin { get; set; }
        public int? AdminId { get; set; }

        [ForeignKey("AdminId")]
        public virtual User Admin { get; set; }

        //public virtual ICollection<User> Users { get; set; }
        public virtual ICollection<Person> Persons { get; set; }
        public virtual ICollection<Site> Sites { get; set; }
    }
}
