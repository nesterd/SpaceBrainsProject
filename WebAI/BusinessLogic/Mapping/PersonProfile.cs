using AutoMapper;
using BusinessLogic.DTO;
using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.Mapping
{
    public class PersonProfile
        : Profile
    {
        public PersonProfile()
        {
            CreateMap<Person, PersonDTO>();

            CreateMap<PersonDTO, Person>()
                .ForMember(dest => dest.KeyWords, opt => opt.Ignore())
                .ForMember(dest => dest.Ranks, opt => opt.Ignore())
                .ForMember(dest => dest.Admin, opt => opt.Ignore())
                .ForMember(dest => dest.AdminId, opt => opt.Ignore());

        }
    }
}
