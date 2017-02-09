using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Autofac;
using Autofac.Integration.Mvc;
using DataAccess.IoC;
using System.Web.Mvc;
using BusinessLogic.IoC;
using WebAI.Infrastructure;
using AutoMapper;

namespace WebAI.IoC
{
    public class AutofacConfig
    {
        public static void ConfigureContainer()
        {
            var builder = new ContainerBuilder();

            builder.RegisterControllers(typeof(MvcApplication).Assembly);

            builder.Register(ctn => AutoMapperConfig.GetConfiguration()).SingleInstance();
            builder.Register(ctn => ctn.Resolve<MapperConfiguration>().CreateMapper()).SingleInstance();

            builder.RegisterModule<DataAccessDependencyModule>();
            builder.RegisterModule<BusinessLogicDependencyModule>();

            var container = builder.Build();
            DependencyResolver.SetResolver(new AutofacDependencyResolver(container));
        }
    }
}