using DataAccess.Context;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Domain.Entities;
using Domain.Entities.Users;

namespace DataAccess.Initializer
{
    public class DbContextInitializer
        : DropCreateDatabaseAlways<WebAIDbContext>
    {
        protected override void Seed(WebAIDbContext context)
        {
            context.Users.AddRange(new List<User>()
            {
                new User { Login = "nester", Password = "12345", IsAdmin = true, Name = "Dan" }
            });
            context.SaveChanges();


            context.Persons.Add(new Person { Name = "Путин В.В.", AdminId = 1 });
            context.Persons.Add(new Person { Name = "Медведев Д.А.", AdminId = 1 });
            context.SaveChanges();

            context.KeyWords.AddRange(new List<KeyWord>
            {
                new KeyWord { Name = "Путина", PersonId = 1 },
                new KeyWord { Name = "Путину", PersonId = 1 },
                new KeyWord { Name = "Путиным", PersonId = 1 },
                new KeyWord { Name = "Медведева", PersonId = 2 },
                new KeyWord { Name = "Медведеву", PersonId = 2 }
            });
            context.SaveChanges();

            context.Sites.AddRange(new List<Site>
            {
                new Site { Name = "lenta.ru", AdminId = 1  },
                new Site { Name = "mail.ru", AdminId = 1  }
            });
            context.SaveChanges();

            context.Pages.AddRange(new List<Page>
            {
                new Page { Url = "lenta.ru", FoundDateTime = DateTime.Now, SiteId = 1 },
                new Page { Url = "lenta.ru/1", FoundDateTime = DateTime.Now, SiteId = 1 },
                new Page { Url = "lenta.ru/2", FoundDateTime = DateTime.Now, SiteId = 1 },
                new Page { Url = "mail.ru", FoundDateTime = DateTime.Now, SiteId = 2 },
                new Page { Url = "mail.ru/1", FoundDateTime = DateTime.Now, SiteId = 2 },
                new Page { Url = "mail.ru/2", FoundDateTime = DateTime.Now, SiteId = 2 }
            });
            context.SaveChanges();

            context.PersonPageRanks.AddRange(new List<PersonPageRank>()
            {
                new PersonPageRank { PageId = 2, PersonId =1, Rank = 10 },
                new PersonPageRank { PageId = 3, PersonId = 1, Rank = 12 },
                new PersonPageRank { PageId = 5, PersonId = 1, Rank = 7 },
                new PersonPageRank { PageId = 2, PersonId = 2, Rank = 19 }
            });

            

        }
    }
}
