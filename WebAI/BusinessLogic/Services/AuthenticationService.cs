using BusinessLogic.Services.Base;
using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.Services
{
    public class AuthenticationService
        : IAuthenticationService
    {
        IAuthenticationRepository authenticationService;

        public AuthenticationService(IAuthenticationRepository authenticationService)
        {
            this.authenticationService = authenticationService;
        }

        public bool CheckUser(string login, string password)
        {
            var user = authenticationService.GetUserByLogin(login);
            if (user == null)
                return false;

            return user.Password == password;
        }

        public bool IsAdmin(string login)
        {
            return authenticationService.GetUserByLogin(login).IsAdmin;
        }
    }
}
