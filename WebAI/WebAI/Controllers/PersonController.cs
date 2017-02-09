using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;
using AutoMapper;
using BusinessLogic.DTO;
using Utility.Helpers;

namespace WebAI.Controllers
{
    [Authorize]
    public class PersonController : Controller
    {
        IPersonService personService = null;
        IMapper _mapper = null;
        
        
        public PersonController(IPersonService personService, IMapper mapper)
        {
            this.personService = personService;
            _mapper = mapper;
        }
  
        public ActionResult PersonList()
        {
            return View(GetPersons());
        }

        public ActionResult KeyWordList()
        {
            int defaultPersonId = 0;
            var persons = GetPersons();
            if (persons.Count() != 0)
                defaultPersonId = PersonIdRemember.Id != 0 ? PersonIdRemember.Id : persons.FirstOrDefault().Id;
            ViewBag.Persons = persons;
            ViewBag.DefaultPersonId = defaultPersonId;
            ViewBag.KeyWords = GetKeyWords(defaultPersonId);
            if (defaultPersonId != 0)
                ViewBag.Person = personService.GetPersonById(defaultPersonId).Name;
            ViewBag.DefaultPerson = GetPersonById(PersonIdRemember.Id);
            return View();
        }

        public ActionResult FIlteredKeyWord(PersonViewModel person)
        {
            ViewBag.Person = personService.GetPersonById(person.Id).Name;
            ViewBag.SelectedPersonId = person.Id;
            PersonIdRemember.Id = person.Id;
            return PartialView("_KeyWordList", GetKeyWords(person.Id));
        }

        IEnumerable<PersonViewModel> GetPersons()
        {
            
            var per = personService.GetPersons();
            //Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, PersonViewModel>());
            return _mapper.Map<IEnumerable<PersonDTO>, IEnumerable<PersonViewModel>>(per);
        }

        PersonViewModel GetPersonById(int id)
        {

            var per = personService.GetPersonById(id);
            //Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, PersonViewModel>());
            return _mapper.Map<PersonDTO, PersonViewModel>(per);
        }

        IEnumerable<KeyWordViewModel> GetKeyWords()
        {
            var kw = personService.GetKeyWords();
            //Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWordViewModel>());
            return _mapper.Map<IEnumerable<KeyWordDTO>, IEnumerable<KeyWordViewModel>>(kw);
        }

        IEnumerable<KeyWordViewModel> GetKeyWords(int id)
        {
            var kw = personService.GetKeyWordsForPerson(id);
            //Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWordViewModel>());
            return _mapper.Map<IEnumerable<KeyWordDTO>, IEnumerable<KeyWordViewModel>>(kw);
        }

        [HttpGet]
        public ActionResult AddPerson()
        {
            return View();
        }

        [HttpPost]
        public ActionResult AddPerson(PersonViewModel newPerson)
        {
            //Mapper.Initialize(cfg => cfg.CreateMap<PersonViewModel, PersonDTO>());
            personService.AddPerson(_mapper.Map<PersonViewModel, PersonDTO>(newPerson));
            return RedirectToAction("PersonList");
        }
        

        [HttpGet]
        public ActionResult ChangePerson(int id)
        {
            var personDTO = personService.GetPersonById(id);
            //Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, PersonViewModel>());
            return View(_mapper.Map<PersonDTO, PersonViewModel>(personDTO));
        }


        [HttpPost]
        public ActionResult ChangePerson(PersonViewModel personToChange)
        {
            //Mapper.Initialize(cfg => cfg.CreateMap<PersonViewModel, PersonDTO>());
            var personDTO = _mapper.Map<PersonViewModel, PersonDTO>(personToChange);
            personService.ChangePerson(personDTO);
            return RedirectToAction("PersonList");
        }

        public ActionResult DeletePerson(int id)
        {
            personService.DeletePersonById(id);
            return RedirectToAction("PersonList");
        }

        [HttpGet]
        public ActionResult AddKeyWord(int id)
        {
            //ViewBag.PersonId = PersonIdRemember.Id;
            ViewBag.PersonId = id;
            ViewBag.Person = personService.GetPersonById(id).Name;
            return View();
        }

        [HttpPost]
        public ActionResult AddKeyWord(KeyWordViewModel newKeyWord)
        {
            PersonIdRemember.Id = newKeyWord.PersonId;
            //Mapper.Initialize(cfg => cfg.CreateMap<KeyWordViewModel, KeyWordDTO>());
            personService.AddKeyWord(_mapper.Map<KeyWordViewModel, KeyWordDTO>(newKeyWord));
            //return PartialView("_KeyWordList", GetKeyWords(newKeyWord.PersonId));
            return RedirectToAction("KeyWordList");
        }

        [HttpGet]
        public ActionResult ChangeKeyWord(int id)
        {
            var keyWordDTO = personService.GetKeyWordById(id);
            //Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWordViewModel>());
            return View(_mapper.Map<KeyWordDTO, KeyWordViewModel>(keyWordDTO));
        }


        [HttpPost]
        public ActionResult ChangeKeyWord(KeyWordViewModel keyWordToChange)
        {
            PersonIdRemember.Id = keyWordToChange.PersonId;
            //Mapper.Initialize(cfg => cfg.CreateMap<KeyWordViewModel, KeyWordDTO>());
            var keyWordDTO = _mapper.Map<KeyWordViewModel, KeyWordDTO>(keyWordToChange);
            personService.ChangeKeyWord(keyWordDTO);
            //return PartialView("_KeyWordList", GetKeyWords(keyWordToChange.PersonId));
            return RedirectToAction("KeyWordList");
        }

        public ActionResult DeleteKeyWord(int id)
        {
            PersonIdRemember.Id = personService.GetKeyWordById(id).PersonId;
            personService.DeleteKeyWordById(id);
            //return PartialView("_KeyWordList", GetKeyWords(personId));
            return RedirectToAction("KeyWordList");
        }
    }
}