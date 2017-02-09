using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.Web.Mvc;

namespace WebAI.Models.Account
{
    public class AdminRegistrationViewModel
    {
        [DisplayName("Логин")]
        [Remote("CheckLogin", "Account", ErrorMessage = "Такой логин уже существует")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "Логин обязателен для заполнения")]
        public string Login { get; set; }

        [DisplayName("Пароль")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "Пароль обязателен для заполнения")]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        [DisplayName("Подтвердите пароль")]
        [Required(AllowEmptyStrings = false, ErrorMessage = "Пароль обязателен для заполнения")]
        [DataType(DataType.Password)]
        public string PasswordComfirm { get; set; }

        [DisplayName("Имя")]
        public string Name { get; set; }
    }
}