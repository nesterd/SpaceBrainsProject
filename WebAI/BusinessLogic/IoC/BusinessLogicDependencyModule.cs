using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Autofac;
using BusinessLogic.Services;
using BusinessLogic.Services.Base;

namespace BusinessLogic.IoC
{
    public class BusinessLogicDependencyModule
        : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<PersonService>().As<IPersonService>()
                .InstancePerRequest();
            builder.RegisterType<SiteService>().As<ISiteService>()
                .InstancePerRequest();
        }
    }
}
