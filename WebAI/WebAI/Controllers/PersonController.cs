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
        IPersonService _personService = null;
        IMapper _mapper = null;
        
        
        public PersonController(IPersonService personService, IMapper mapper)
        {
            _personService = personService;
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
                ViewBag.Person = _personService.GetPersonById(defaultPersonId).Name;

            ViewBag.DefaultPerson = GetPersonById(PersonIdRemember.Id);

            return View();
        }

        public ActionResult FIlteredKeyWord(PersonViewModel person)
        {
            ViewBag.Person = _personService.GetPersonById(person.Id).Name;
            ViewBag.SelectedPersonId = person.Id;
            PersonIdRemember.Id = person.Id;
            return PartialView("_KeyWordList", GetKeyWords(person.Id));
        }

        IEnumerable<PersonViewModel> GetPersons()
        {
            var person = _personService.GetPersons();
            return _mapper.Map<IEnumerable<PersonDTO>, IEnumerable<PersonViewModel>>(person);
        }

        PersonViewModel GetPersonById(int id)
        {
            var person = _personService.GetPersonById(id);
            return _mapper.Map<PersonDTO, PersonViewModel>(person);
        }

        IEnumerable<KeyWordViewModel> GetKeyWords()
        {
            var keyWord = _personService.GetKeyWords();
            return _mapper.Map<IEnumerable<KeyWordDTO>, IEnumerable<KeyWordViewModel>>(keyWord);
        }

        IEnumerable<KeyWordViewModel> GetKeyWords(int id)
        {
            var keyWord = _personService.GetKeyWordsForPerson(id);
            return _mapper.Map<IEnumerable<KeyWordDTO>, IEnumerable<KeyWordViewModel>>(keyWord);
        }

        [HttpGet]
        public ActionResult AddPerson()
        {
            return View();
        }

        [HttpPost]
        public ActionResult AddPerson(PersonViewModel newPerson)
        {
            _personService.AddPerson(_mapper.Map<PersonViewModel, PersonDTO>(newPerson));
            return RedirectToAction("PersonList");
        }
        

        [HttpGet]
        public ActionResult ChangePerson(int id)
        {
            var personDTO = _personService.GetPersonById(id);
            return View(_mapper.Map<PersonDTO, PersonViewModel>(personDTO));
        }


        [HttpPost]
        public ActionResult ChangePerson(PersonViewModel personToChange)
        {
            var personDTO = _mapper.Map<PersonViewModel, PersonDTO>(personToChange);
            _personService.ChangePerson(personDTO);
            return RedirectToAction("PersonList");
        }

        public ActionResult DeletePerson(int id)
        {
            _personService.DeletePersonById(id);
            return RedirectToAction("PersonList");
        }

        [HttpGet]
        public ActionResult AddKeyWord(int id)
        {
            ViewBag.PersonId = id;
            ViewBag.Person = _personService.GetPersonById(id).Name;
            return View();
        }

        [HttpPost]
        public ActionResult AddKeyWord(KeyWordViewModel newKeyWord)
        {
            PersonIdRemember.Id = newKeyWord.PersonId;
            _personService.AddKeyWord(_mapper.Map<KeyWordViewModel, KeyWordDTO>(newKeyWord));
            return RedirectToAction("KeyWordList");
        }

        [HttpGet]
        public ActionResult ChangeKeyWord(int id)
        {
            var keyWordDTO = _personService.GetKeyWordById(id);
            return View(_mapper.Map<KeyWordDTO, KeyWordViewModel>(keyWordDTO));
        }


        [HttpPost]
        public ActionResult ChangeKeyWord(KeyWordViewModel keyWordToChange)
        {
            PersonIdRemember.Id = keyWordToChange.PersonId;
            var keyWordDTO = _mapper.Map<KeyWordViewModel, KeyWordDTO>(keyWordToChange);
            _personService.ChangeKeyWord(keyWordDTO);
            return RedirectToAction("KeyWordList");
        }

        public ActionResult DeleteKeyWord(int id)
        {
            PersonIdRemember.Id = _personService.GetKeyWordById(id).PersonId;
            _personService.DeleteKeyWordById(id);
            return RedirectToAction("KeyWordList");
        }
    }
}