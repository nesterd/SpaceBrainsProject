using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.Repositories.Base
{
    public interface IPersonRepository
    {
        void AddPerson(Person person);
        void AddKeyWord(KeyWord keyWord);
        

        void ChangePerson(Person person);
        void ChangeKeyWord(KeyWord keyword);
        

        void DeleteKeyWordById(int id);
        void DeletePersonById(int id);
        

        IEnumerable<Person> GetPersons();
        IEnumerable<KeyWord> GetKeyWords();
        IEnumerable<KeyWord> GetKeyWords(Person person);
        IEnumerable<KeyWord> GetKeyWords(string personName);
        IEnumerable<KeyWord> GetKeyWords(int personId);


        Person GetPerson(int id);
        KeyWord GetKeyWord(int id);
        
    }
}
