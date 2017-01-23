using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Entities
{
    [Table("KeyWords")]
    public class KeyWord
        : Base.BaseDbEntity
    {
        public string Name { get; set; }
        public int PersonId { get; set; }

        [ForeignKey("PersonId")]
        public virtual Person Person { get; set; }
    }
}
