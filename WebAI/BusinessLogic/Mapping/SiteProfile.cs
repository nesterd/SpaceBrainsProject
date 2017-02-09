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
    public class SiteProfile
        : Profile
    {
        public SiteProfile()
        {
            CreateMap<Site, SiteDTO>()
                .ForMember(dest => dest.Url, opt => opt.Ignore());

            CreateMap<SiteDTO, Site>()
                .ForMember(dest => dest.Admin, opt => opt.Ignore())
                .ForMember(dest => dest.AdminId, opt => opt.Ignore())
                .ForMember(dest => dest.Pages, opt => opt.Ignore());
        }
    }
}
