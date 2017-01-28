using BusinessLogic.DTO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.Services.Base
{
    public interface ISiteService
    {
        IEnumerable<SiteDTO> GetSites();
        SiteDTO GetSiteById(int id);
        
        void DeleteSiteById(int id);
        
        void AddSite(SiteDTO siteDTO);
        void ChangeSite(SiteDTO siteDTO);
    }
}
