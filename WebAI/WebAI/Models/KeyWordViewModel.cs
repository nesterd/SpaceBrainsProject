using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebAI.Models
{
    public class KeyWordViewModel
        : Base.BaseModel
    {
        public string Name { get; set; }
        public int PersonId { get; set; }
    }
}