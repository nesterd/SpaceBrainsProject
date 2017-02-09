using AutoMapper;
using BusinessLogic.DTO.Account;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebAI.Models.Account;

namespace WebAI.Infrastructure.Mapping.Account
{
    public class UserRegistrationProfile
        : Profile
    {
        public UserRegistrationProfile()
        {
            CreateMap<UserRegistrationViewModel, UserDTO>()
                .ForMember(dest => dest.RoleId, opt => opt.Ignore())
                .ForMember(dest => dest.AdminId, opt => opt.Ignore());
        }
    }
}