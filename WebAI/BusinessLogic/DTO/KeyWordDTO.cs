using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.DTO
{
    public class KeyWordDTO
        : Base.BaseDTO
    {
        public string Name { get; set; }
        public int PersonId { get; set; }
    }
}
