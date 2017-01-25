using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogic.Services
{
    public class SiteService
    {
        ISiteRepository siteReposytory;

        public SiteService(ISiteRepository siteReposytory)
        {
            this.siteReposytory = siteReposytory;
        }


    }
}
