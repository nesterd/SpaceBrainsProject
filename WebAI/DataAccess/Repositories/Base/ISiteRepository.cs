using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Entities;




namespace DataAccess.Repositories.Base
{
    public interface ISiteRepository
    {
        
        void AddSite(Site site, string url);

        
        void ChangeSite(Site site);

        
        void DeleteSiteById(int id);

        
        IEnumerable<Site> GetSites();

        
        Site GetSite(int id);
    }
}
