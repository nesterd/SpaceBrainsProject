using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace WebAI.Models.Account
{
    public class LogInViewModel
    {
        [DisplayName("Логин")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "Логин обязателен для заполнения")]
        public string Login { get; set; }

        [DisplayName("Пароль")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "Пароль обязателен для заполнения")]
        [DataType(DataType.Password)]
        public string Password { get; set; }
    }
}