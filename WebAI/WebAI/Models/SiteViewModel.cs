using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WebAI.Models
{
    public class SiteViewModel
        : Base.BaseModel
    {
        [DisplayName("Название сайта")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "поле обязательно для заполнения")]
        public string Name { get; set; }

        [DisplayName("Url начальной страницы")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "поле обязательно для заполнения")]
        public string Url { get; set; }
    }
}