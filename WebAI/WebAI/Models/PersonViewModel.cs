using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WebAI.Models
{
    public class PersonViewModel
        :Base.BaseModel
    {
        [DisplayName("ФИО")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "поле обязательно для заполнения")]
        public string Name { get; set; }

        public override string ToString()
        {
            return Name;
        }
    }
}

