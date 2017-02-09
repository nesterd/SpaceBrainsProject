using DataAccess.Context;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Entities.Users;
using DataAccess.Repositories.Base;

namespace DataAccess.Repositories
{
    public class AuthenticationRepository
        : BaseRepository, IAuthenticationRepository
    {
        

        public AuthenticationRepository(WebAIDbContext context)
            : base (context)
        {
            
        }

        public User GetUserByLogin(string login)
        {
            return _context.Users.FirstOrDefault(x => x.Login.Trim().ToLower() == login.Trim().ToLower());
        }

        public bool CheckLogin(string loginToCheck)
        {
            return !_context.Users.Any(x => x.Login.Trim().ToLower() == loginToCheck.Trim().ToLower());
        }

        public void AddUser(User userToAdd)
        {
            if(userToAdd != null)
            {
                _context.Users.Add(userToAdd);
                _context.SaveChanges();
            }
            
        }

        public int GetUserIdByLogin(string userLogin)
        {
            var user = GetUserByLogin(userLogin);
            return user.Id;
        }

        public void ChangePassword(string login, string newPassword)
        {
            var user = GetUserByLogin(login);
            user.Password = newPassword;
            _context.Entry(user).State = System.Data.Entity.EntityState.Modified;
            _context.SaveChanges();
        }
    }
}
