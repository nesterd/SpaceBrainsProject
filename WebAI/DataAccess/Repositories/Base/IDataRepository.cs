using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.Repositories.Base
{
    interface IDataRepository
    {
        void AddPerson(Person person);
        void AddKeyWord(KeyWord keyWord);
        void AddSite(Site site, string url);

        void ChangePerson(Person person);
        void ChangeKeyWord(KeyWord keyword);
        void ChangeSite(Site site);

        void DeleteKeyWord(KeyWord keyword);
        void DeletePersonById(int id);
        void DeleteSiteById(int id);

        IEnumerable<Person> GetPersons();
        IEnumerable<KeyWord> GetKeyWords();
        IEnumerable<KeyWord> GetKeyWords(Person person);
        IEnumerable<Site> GetSites();
    }
}
