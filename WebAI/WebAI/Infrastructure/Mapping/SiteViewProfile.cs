using AutoMapper;
using BusinessLogic.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebAI.Models;

namespace WebAI.Infrastructure.Mapping
{
    public class SiteViewProfile
        : Profile
    {
        public SiteViewProfile()
        {
            CreateMap<SiteDTO, SiteViewModel>();

            CreateMap<SiteViewModel, SiteDTO>();
        }
    }
}