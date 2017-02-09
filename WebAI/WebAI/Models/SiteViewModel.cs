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
        [StringLength(1024, ErrorMessage = "Название сайта слишком длинное(макс 1024 символа)")]
        public string Name { get; set; }

        [DisplayName("Url начальной страницы")]
        [UIHint("Url")]
        [DataType(DataType.Url)]
        [Required(AllowEmptyStrings = false, ErrorMessage = "поле обязательно для заполнения")]
        [RegularExpression(@"[A-Za-z0-9._%+-]+\.[A-Za-z]{2,4}", ErrorMessage = "Некорректный адрес")]
        [StringLength(2040, ErrorMessage = "Url слишком длинный")]
        public string Url { get; set; }
    }
}