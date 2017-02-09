using DataAccess.Repositories;
using DataAccess.Repositories.Base;
using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess;
using AutoMapper;
using BusinessLogic.DTO;
using Utility.Helpers;

namespace BusinessLogic.Services
{
    public class PersonService
        : Base.IPersonService
    {
        IPersonRepository personRepository;
        IMapper _mapper = null;

        public PersonService(IPersonRepository personRepository, IMapper mapper)
        {
            this.personRepository = personRepository;
            _mapper = mapper;
        }
        
        public IEnumerable<PersonDTO> GetPersons()
        {
            var persons = personRepository.GetPersons();
            
            return _mapper.Map<IEnumerable<Person>, IEnumerable<PersonDTO>>(persons);
        }

        public IEnumerable<KeyWordDTO> GetKeyWordsForPerson(int personId)
        {
            var keyWords = personRepository.GetKeyWords(personId);

            
            return _mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(keyWords);
        }
       
        public IEnumerable<KeyWordDTO> GetKeyWords()
        {
            
            return _mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(personRepository.GetKeyWords());
        }

        public PersonDTO GetPersonById(int id)
        {
            
            return _mapper.Map<Person, PersonDTO>(personRepository.GetPerson(id));
        }

        public KeyWordDTO GetKeyWordById(int id)
        {
            
            return _mapper.Map<KeyWord, KeyWordDTO>(personRepository.GetKeyWord(id));
        }

        public void DeletePersonById(int id)
        {
            personRepository.DeletePersonById(id);
        }

        public void DeleteKeyWordById(int id)
        {
            personRepository.DeleteKeyWordById(id);
        }

        public void AddPerson(PersonDTO personDTO)
        {
            var person = _mapper.Map<PersonDTO, Person>(personDTO);
            person.AdminId = AdminIdRemember.Id;
            personRepository.AddPerson(person);
        }

        public void ChangePerson(PersonDTO personDTO)
        {
            
            personRepository.ChangePerson(_mapper.Map<PersonDTO, Person>(personDTO));
        }

        public void AddKeyWord(KeyWordDTO keyWordDTO)
        {
            
            personRepository.AddKeyWord(_mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        public void ChangeKeyWord(KeyWordDTO keyWordDTO)
        {
            
            personRepository.ChangeKeyWord(_mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        
    }
}
