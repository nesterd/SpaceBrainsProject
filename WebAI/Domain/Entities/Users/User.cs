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

        [Column(TypeName = "varchar")]
        [StringLength(75)]
        public string Name { get; set; }

        [Column(TypeName = "varchar")]
        [StringLength(15)]
        public string Login { get; set; }

        [Column(TypeName = "varchar")]
        [StringLength(255)]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        [Column(TypeName = "varchar")]
        [StringLength(255)]
        [DataType(DataType.EmailAddress)]
        public string Email { get; set; }

        //public bool IsAdmin { get; set; }

        public int AdminId { get; set; }
        public int RoleId { get; set; }

        [ForeignKey("AdminId")]
        public virtual User Admin { get; set; }

        [ForeignKey("RoleId")]
        public virtual Role Role { get; set; }

        //public virtual ICollection<User> Users { get; set; }
        public virtual ICollection<Person> Persons { get; set; }
        public virtual ICollection<Site> Sites { get; set; }
    }
}
