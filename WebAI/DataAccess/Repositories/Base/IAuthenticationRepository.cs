using Domain.Entities.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.Repositories.Base
{
    public interface IAuthenticationRepository
    {
        User GetUserByLogin(string login);
        bool CheckLogin(string loginToCheck);
        void AddUser(User userToAdd);
        int GetUserIdByLogin(string userLogin);
        void ChangePassword(string login, string newPassword);
    }
}
