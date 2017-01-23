using Autofac;
using DataAccess.Context;
using DataAccess.Repositories;
using DataAccess.Repositories.Base;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.IoC
{
    public class DataAccessDependencyModule
        : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            //builder.RegisterType<WebAIDbContext>()
            //    .Named<DbContext>("DataContext")

            builder.RegisterType<DataRepository>().As<IDataRepository>()
                .WithParameter("context", new WebAIDbContext());
        }
    }
}
