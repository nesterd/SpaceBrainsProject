using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.Repositories.Base
{

    public interface ICrawlerRepository
    {

        IEnumerable<Site> GetSites();
        IEnumerable<Page> GetAllPagesForSite(int id);
        int GetQuantityPagesForSite(int id);
        int GetQuantityNotVisitedPagesForSite(int id);
        int GetQuantityVisitedPagesForSite(int id);

    }
}
