using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BusinessLogic.DTO;
using AutoMapper;
using Domain.Entities;
using Utility.Helpers;

namespace BusinessLogic.Services
{
    public class SiteService
        : Base.ISiteService
    {
        ISiteRepository _siteRepository;
        IMapper _mapper = null;

        public SiteService(ISiteRepository siteReposytory, IMapper mapper)
        {
            _siteRepository = siteReposytory;
            _mapper = mapper;
        }

        public void AddSite(SiteDTO siteDTO)
        {
            var site = _mapper.Map<SiteDTO, Site>(siteDTO);
            site.AdminId = AdminIdRemember.Id;
            _siteRepository.AddSite(site);
        }

        public void ChangeSite(SiteDTO siteDTO)
        {
            _siteRepository.ChangeSite(_mapper.Map<SiteDTO, Site>(siteDTO));
        }

        public void DeleteSiteById(int id)
        {
            _siteRepository.DeleteSiteById(id);
        }

        public SiteDTO GetSiteById(int id)
        {
            return _mapper.Map<Site, SiteDTO>(_siteRepository.GetSite(id));
        }

        public IEnumerable<SiteDTO> GetSites()
        {
            var sites = _siteRepository.GetSites();
            return _mapper.Map<IEnumerable<Site>, IEnumerable<SiteDTO>>(sites);
        }
    }
}
