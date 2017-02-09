using BusinessLogic.Services.Base;
using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BusinessLogic.DTO.Account;
using AutoMapper;
using Domain.Entities.Users;
using Utility.enums;
using System.Web.Security;

namespace BusinessLogic.Services
{
    public class AuthenticationService
        : IAuthenticationService
    {
        IAuthenticationRepository authenticationRepository;
        IMapper _mapper = null;

        public AuthenticationService(IAuthenticationRepository authenticationRepository, IMapper mapper)
        {
            this.authenticationRepository = authenticationRepository;
            _mapper = mapper;
        }

        public bool CheckUser(string login, string password)
        {
            var user = authenticationRepository.GetUserByLogin(login);
            if (user == null)
                return false;

            return user.Password == password && IsAdmin(login);
        }

        public bool CheckLogin(string loginToCheck)
        {
            return authenticationRepository.CheckLogin(loginToCheck);
        }

        public bool IsAdmin(string login)
        {
            string[] adminRolesList = { "root", "admin" };
            return adminRolesList.Contains(authenticationRepository.GetUserByLogin(login).Role.Name);
        }

        public bool IsSuperAdmin(string login)
        {
            
            if (string.IsNullOrEmpty(login))
                return false;
            return authenticationRepository.GetUserByLogin(login).Role.Name == "root";
        }

        public void AdminRegistration(UserDTO userRegistrationData)
        {
            userRegistrationData.RoleId = (int)RolesEnum.Admin;
            userRegistrationData.AdminId = (int)RolesEnum.Root;
            AddUser(userRegistrationData);

        }

        public void UserRegistration(UserDTO userRegistrationData, int adminId)
        {
            userRegistrationData.RoleId = (int)RolesEnum.User;
            userRegistrationData.AdminId = adminId;
            AddUser(userRegistrationData);

        }

        void AddUser(UserDTO userRegistrationData)
        {
            var userToAdd = _mapper.Map<UserDTO, User>(userRegistrationData);
            authenticationRepository.AddUser(userToAdd);
        }

        public int GetAdminIdByLogin(string adminLogin)
        {
            return authenticationRepository.GetUserIdByLogin(adminLogin);
        }

        public void ChangePassword(string login, string newPassword)
        {
            authenticationRepository.ChangePassword(login, newPassword);
        }
    }
}
