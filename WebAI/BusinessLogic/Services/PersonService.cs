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

namespace BusinessLogic.Services
{
    public class PersonService
        : Base.IPersonService
    {
        IPersonRepository personRepository;

        public PersonService(IPersonRepository personRepository)
        {
            this.personRepository = personRepository;
            Mapper.Initialize(cfg => cfg.CreateMap<Person, PersonDTO>());
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWord, KeyWordDTO>());
            Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, Person>()
               .ForMember(dest => dest.KeyWords, opt => opt.Ignore())
               .ForMember(dest => dest.Ranks, opt => opt.Ignore()));
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWord>()
               .ForMember(dest => dest.Person, opt => opt.Ignore()));
        }

        public IEnumerable<PersonDTO> GetPersons()
        {
            var persons = personRepository.GetPersons();
            
            return Mapper.Map<IEnumerable<Person>, IEnumerable<PersonDTO>>(persons);
        }

        public IEnumerable<KeyWordDTO> GetKeyWordsForPerson(int personId)
        {
            var keyWords = personRepository.GetKeyWords(personId);

            return Mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(keyWords);
        }

        public PersonDTO GetPersonById(int id)
        {
            return Mapper.Map<Person, PersonDTO>(personRepository.GetPerson(id));
        }

        public KeyWordDTO GetKeyWordById(int id)
        {
            return Mapper.Map<KeyWord, KeyWordDTO>(personRepository.GetKeyWord(id));
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
            personRepository.AddPerson(Mapper.Map<PersonDTO, Person>(personDTO));
        }

        public void ChangePerson(PersonDTO personDTO)
        {
            personRepository.ChangePerson(Mapper.Map<PersonDTO, Person>(personDTO));
        }

        public void AddKeyWord(KeyWordDTO keyWordDTO)
        {
            personRepository.AddKeyWord(Mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        public void ChangeKeyWord(KeyWordDTO keyWordDTO)
        {
            personRepository.ChangeKeyWord(Mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }
    }
}
