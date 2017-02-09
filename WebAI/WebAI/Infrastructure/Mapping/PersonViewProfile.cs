using AutoMapper;
using BusinessLogic.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebAI.Models;

namespace WebAI.Infrastructure.Mapping
{
    public class PersonViewProfile
        : Profile
    {
        public PersonViewProfile()
        {
            CreateMap<PersonDTO, PersonViewModel>();

            CreateMap<PersonViewModel, PersonDTO>();
        }
    }
}