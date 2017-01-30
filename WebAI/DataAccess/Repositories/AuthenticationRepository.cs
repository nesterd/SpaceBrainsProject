using DataAccess.Context;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Entities.Users;

namespace DataAccess.Repositories
{
    public class AuthenticationRepository
        : Base.IAuthenticationRepository
    {
        WebAIDbContext context;

        public AuthenticationRepository(WebAIDbContext context)
        {
            this.context = context;
        }

        public User GetUserByLogin(string login)
        {
            return context.Users.FirstOrDefault(x => x.Login == login);
        }
    }
}
