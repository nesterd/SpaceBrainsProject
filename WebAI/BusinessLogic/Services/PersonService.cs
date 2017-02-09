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
        IPersonRepository _personRepository;
        IMapper _mapper = null;

        public PersonService(IPersonRepository personRepository, IMapper mapper)
        {
            _personRepository = personRepository;
            _mapper = mapper;
        }
        
        public IEnumerable<PersonDTO> GetPersons()
        {
            var persons = _personRepository.GetPersons();
            
            return _mapper.Map<IEnumerable<Person>, IEnumerable<PersonDTO>>(persons);
        }

        public IEnumerable<KeyWordDTO> GetKeyWordsForPerson(int personId)
        {
            var keyWords = _personRepository.GetKeyWords(personId);

            
            return _mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(keyWords);
        }
       
        public IEnumerable<KeyWordDTO> GetKeyWords()
        {
            
            return _mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(_personRepository.GetKeyWords());
        }

        public PersonDTO GetPersonById(int id)
        {
            
            return _mapper.Map<Person, PersonDTO>(_personRepository.GetPerson(id));
        }

        public KeyWordDTO GetKeyWordById(int id)
        {
            
            return _mapper.Map<KeyWord, KeyWordDTO>(_personRepository.GetKeyWord(id));
        }

        public void DeletePersonById(int id)
        {
            _personRepository.DeletePersonById(id);
        }

        public void DeleteKeyWordById(int id)
        {
            _personRepository.DeleteKeyWordById(id);
        }

        public void AddPerson(PersonDTO personDTO)
        {
            var person = _mapper.Map<PersonDTO, Person>(personDTO);
            person.AdminId = AdminIdRemember.Id;
            _personRepository.AddPerson(person);
        }

        public void ChangePerson(PersonDTO personDTO)
        {

            _personRepository.ChangePerson(_mapper.Map<PersonDTO, Person>(personDTO));
        }

        public void AddKeyWord(KeyWordDTO keyWordDTO)
        {

            _personRepository.AddKeyWord(_mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        public void ChangeKeyWord(KeyWordDTO keyWordDTO)
        {

            _personRepository.ChangeKeyWord(_mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        
    }
}
