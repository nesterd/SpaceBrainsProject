using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.Context
{
    public class WebAIDbContext
        : DbContext
    {
        public WebAIDbContext()
            : base("NameOfDb")
        {

        }

        public virtual DbSet<Person> Persons { get; set; }
        public virtual DbSet<KeyWord> KeyWords { get; set; }
        public virtual DbSet<Site> Sites { get; set; }
        public virtual DbSet<Page> Pages { get; set; }
        public virtual DbSet<PersonRangeRank> PersonRangeRanks { get; set; }
    }
}
