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
        }
        
        public IEnumerable<PersonDTO> GetPersons()
        {
            var persons = personRepository.GetPersons();

            Mapper.Initialize(cfg => cfg.CreateMap<Person, PersonDTO>());
            return Mapper.Map<IEnumerable<Person>, IEnumerable<PersonDTO>>(persons);
        }

        public IEnumerable<KeyWordDTO> GetKeyWordsForPerson(int personId)
        {
            var keyWords = personRepository.GetKeyWords(personId);

            Mapper.Initialize(cfg => cfg.CreateMap<KeyWord, KeyWordDTO>());
            return Mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(keyWords);
        }
       
        public IEnumerable<KeyWordDTO> GetKeyWords()
        {
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWord, KeyWordDTO>());
            return Mapper.Map<IEnumerable<KeyWord>, IEnumerable<KeyWordDTO>>(personRepository.GetKeyWords());
        }

        public PersonDTO GetPersonById(int id)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<Person, PersonDTO>());
            return Mapper.Map<Person, PersonDTO>(personRepository.GetPerson(id));
        }

        public KeyWordDTO GetKeyWordById(int id)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWord, KeyWordDTO>());
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
            Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, Person>()
                  .ForMember(dest => dest.KeyWords, opt => opt.Ignore())
                  /*.ForMember(dest => dest.Ranks, opt => opt.Ignore())*/);
            personRepository.AddPerson(Mapper.Map<PersonDTO, Person>(personDTO));
        }

        public void ChangePerson(PersonDTO personDTO)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, Person>()
                  .ForMember(dest => dest.KeyWords, opt => opt.Ignore())
                  /*.ForMember(dest => dest.Ranks, opt => opt.Ignore())*/);
            personRepository.ChangePerson(Mapper.Map<PersonDTO, Person>(personDTO));
        }

        public void AddKeyWord(KeyWordDTO keyWordDTO)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWord>()
                  .ForMember(dest => dest.Person, opt => opt.Ignore()));
            personRepository.AddKeyWord(Mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        public void ChangeKeyWord(KeyWordDTO keyWordDTO)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWord>()
                  .ForMember(dest => dest.Person, opt => opt.Ignore()));
            personRepository.ChangeKeyWord(Mapper.Map<KeyWordDTO, KeyWord>(keyWordDTO));
        }

        
    }
}
