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
        IAuthenticationRepository _authenticationRepository;
        IMapper _mapper = null;

        public AuthenticationService(IAuthenticationRepository authenticationRepository, IMapper mapper)
        {
            _authenticationRepository = authenticationRepository;
            _mapper = mapper;
        }

        public bool CheckUser(string login, string password)
        {
            var user = _authenticationRepository.GetUserByLogin(login);
            if (user == null)
                return false;

            return user.Password == password && IsAdmin(login);
        }

        public bool CheckLogin(string loginToCheck)
        {
            return _authenticationRepository.CheckLogin(loginToCheck);
        }

        public bool IsAdmin(string login)
        {
            string[] adminRolesList = { RolesEnum.Root.ToString().ToLower(), RolesEnum.Admin.ToString().ToLower() };
            return adminRolesList.Contains(_authenticationRepository.GetUserByLogin(login).Role.Name);
        }

        public bool IsSuperAdmin(string login)
        {
            
            if (string.IsNullOrEmpty(login))
                return false;
            return _authenticationRepository.GetUserByLogin(login).Role.Name == RolesEnum.Root.ToString().ToLower();
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
            _authenticationRepository.AddUser(userToAdd);
        }

        public int GetAdminIdByLogin(string adminLogin)
        {
            return _authenticationRepository.GetUserIdByLogin(adminLogin);
        }

        public void ChangePassword(string login, string newPassword)
        {
            _authenticationRepository.ChangePassword(login, newPassword);
        }
    }
}
