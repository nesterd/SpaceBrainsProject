using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebAI.Models
{
    public class KeyWordViewModel
        : Base.BaseModel
    {
        [DisplayName("Ключевое слово")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "поле обязательно для заполнения")]
        [StringLength(1024, ErrorMessage = "Слово слишком длинное(макс 1024 символа)")]
        public string Name { get; set; }

        [HiddenInput(DisplayValue = false)]
        public int PersonId { get; set; }
    }
}