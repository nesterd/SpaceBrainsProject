using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess;
using BusinessLogic.DTO;

namespace BusinessLogic.Services.Base
{
    public interface IPersonService
    {
         IEnumerable<PersonDTO> GetPersons();
         IEnumerable<KeyWordDTO> GetKeyWords();
         IEnumerable<KeyWordDTO> GetKeyWordsForPerson(int personId);
         


         PersonDTO GetPersonById(int id);
         KeyWordDTO GetKeyWordById(int id);

         void DeletePersonById(int id);
         void DeleteKeyWordById(int id);

         void AddPerson(PersonDTO personDTO);
         void ChangePerson(PersonDTO personDTO);

         void AddKeyWord(KeyWordDTO keyWordDTO);
         void ChangeKeyWord(KeyWordDTO keyWordDTO);
    }
}
