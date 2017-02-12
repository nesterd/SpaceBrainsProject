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
    public class KeyWordProfile
        : Profile
    {
        public KeyWordProfile()
        {
            CreateMap<KeyWord, KeyWordDTO>();

            CreateMap<KeyWordDTO, KeyWord>()
                .ForMember(dest => dest.Person, opt => opt.Ignore());
        }


    }
}
