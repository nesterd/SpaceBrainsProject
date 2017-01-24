using DataAccess.Repositories;
using DataAccess.Repositories.Base;
using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess;

namespace BusinessLogic.Services
{
    public class DataService
        : Base.IDataService
    {
        IDataRepository dataRepository;

        public DataService(IDataRepository dataRepository)
        {
            this.dataRepository = dataRepository;
        }

        /*public IEnumerable<WebAI.Models.Person> GetPersons()
        {
            return dataRepository.GetPersons().Select
                (x => new Person
                {
                    Id = x.Id,
                    Name = x.Name,
                    Ranks = x.Ranks,
                    KeyWords = x.KeyWords
                }).ToList();
        }*/

    }
}
