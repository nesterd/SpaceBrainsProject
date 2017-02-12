using AutoMapper;
using BusinessLogic.DTO.Account;
using Domain.Entities.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.Mapping
{
    public class UserProfile
        : Profile
    {
        public UserProfile()
        {
            CreateMap<User, UserDTO>();

            CreateMap<UserDTO, User>()
                .ForMember(dest => dest.Admin, opt => opt.Ignore())
                .ForMember(dest => dest.Role, opt => opt.Ignore())
                .ForMember(dest => dest.Persons, opt => opt.Ignore())
                .ForMember(dest => dest.Sites, opt => opt.Ignore());
        }
    }
}
