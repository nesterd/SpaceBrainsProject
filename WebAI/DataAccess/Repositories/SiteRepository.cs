using DataAccess.Context;
using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;
using System.Data.Entity;
using DataAccess.Repositories.Base;
using Utility.Helpers;

namespace DataAccess.Repositories
{
    public class SiteRepository
        : BaseRepository, ISiteRepository
    {
        //WebAIDbContext context;

        public SiteRepository(WebAIDbContext context)
            : base(context)
        {
            
        }

        

        public void AddSite(Site site, string url)
        {
            
            if (site != null)
            {
                site.AdminId = GetCurrentUserId();
                _context.Sites.Add(site);
                _context.SaveChanges();

                _context.Pages.Add(new Page { Url = url, SiteId = site.Id, FoundDateTime = DateTime.Now });
                _context.SaveChanges();
            }
        }

        

        public void ChangeSite(Site site)
        {
            if (site == null)
                return;
            _context.Entry(site).State = System.Data.Entity.EntityState.Modified;
            _context.SaveChanges();
        }

        
       

        public void DeleteSiteById(int id)
        {
            //????? каскадное удаление
            Site site = _context.Sites
                .Include(x => x.Pages)
                .Include(x => x.Pages.Select(y => y.Ranks))
                .FirstOrDefault(x => x.Id == id);
            if (site == null)
                return;
            _context.Sites.Remove(site);
            _context.SaveChanges();
        }

        

        public IEnumerable<Site> GetSites()
        {
            return GetSitesByAdminId().ToArray();
        }

        

        public Site GetSite(int id)
        {
            return GetSitesByAdminId().FirstOrDefault(x => x.Id == id);
        }

        IEnumerable<Site> GetSitesByAdminId()
        {
            string name = GetCurrentUserName();
            return _context.Sites.Where(x => x.Admin.Login == name).ToArray();
        }
    }
}
