using DataAccess.Context;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace DataAccess.Repositories.Base
{
    public class BaseRepository
    {

        protected WebAIDbContext _context;

        public BaseRepository(WebAIDbContext context)
        {
            _context = context;
        }

        protected int GetCurrentUserId()
        {
            string name = GetCurrentUserName();
            return _context.Users.FirstOrDefault(x => x.Login.Trim().ToLower() == name.Trim().ToLower()).Id;
        }
        protected string GetCurrentUserName()
        {
            string name = Thread.CurrentPrincipal.Identity?.Name;
            return name != null ? name : string.Empty;
        }
    }
}
