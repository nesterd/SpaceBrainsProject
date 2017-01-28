using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BusinessLogic.DTO;
using AutoMapper;
using Domain.Entities;

namespace BusinessLogic.Services
{
    public class SiteService
        : Base.ISiteService
    {
        ISiteRepository siteReposytory;

        public SiteService(ISiteRepository siteReposytory)
        {
            this.siteReposytory = siteReposytory;
            
           
        }

        public void AddSite(SiteDTO siteDTO)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<SiteDTO, Site>()
               .ForMember(dest => dest.Pages, opt => opt.Ignore()));
            siteReposytory.AddSite(Mapper.Map<SiteDTO, Site>(siteDTO), siteDTO.Url);
        }

        public void ChangeSite(SiteDTO siteDTO)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<SiteDTO, Site>()
               .ForMember(dest => dest.Pages, opt => opt.Ignore()));

            siteReposytory.ChangeSite(Mapper.Map<SiteDTO, Site>(siteDTO));

        }

        public void DeleteSiteById(int id)
        {
            siteReposytory.DeleteSiteById(id);
        }

        public SiteDTO GetSiteById(int id)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<Site, SiteDTO>()
               .ForMember(dest => dest.Url, opt => opt.Ignore()));
            return Mapper.Map<Site, SiteDTO>(siteReposytory.GetSite(id));
        }

        public IEnumerable<SiteDTO> GetSites()
        {
            var sites = siteReposytory.GetSites();

            Mapper.Initialize(cfg => cfg.CreateMap<Site, SiteDTO>()
               .ForMember(dest => dest.Url, opt => opt.Ignore()));
            return Mapper.Map<IEnumerable<Site>, IEnumerable<SiteDTO>>(sites);
        }
    }
}
