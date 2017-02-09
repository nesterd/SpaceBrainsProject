using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utility.enums;

namespace Domain.Entities.Users
{
    [Table("Roles")]
    public class Role
        : Base.BaseDbEntity
    {
        [Column(TypeName = "varchar")]
        [StringLength(50)]
        public string Name { get; set; }

        public virtual ICollection<User> Users { get; set; }
    }
}
