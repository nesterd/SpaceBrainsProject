using BusinessLogic.Services.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebAI.Models;
using AutoMapper;
using BusinessLogic.DTO;

namespace WebAI.Controllers
{
    public class PersonController : Controller
    {
        IPersonService personservice = null;
        
        public PersonController(IPersonService personservice)
        {
            this.personservice = personservice;
            //Mapper.Initialize(cfg => cfg.CreateMap<PersonViewModel, PersonDTO>());
            //Mapper.Initialize(cfg => cfg.CreateMap<KeyWordViewModel, KeyWordDTO>());
        }
  
        public ActionResult PersonList()
        {
            return View(GetPersons());
        }

        public ActionResult KeyWordList()
        {
            ViewBag.Persons = GetPersons();
            ViewBag.KeyWords = GetKeyWords(1);

            return View();
        }

        public ActionResult FIlteredKeyWord(PersonViewModel person)
        {

            return PartialView("_KeyWordList", GetKeyWords(person.Id));
        }

        IEnumerable<PersonViewModel> GetPersons()
        {
            
            var per = personservice.GetPersons();
            Mapper.Initialize(cfg => cfg.CreateMap<PersonDTO, PersonViewModel>());
            return Mapper.Map<IEnumerable<PersonDTO>, IEnumerable<PersonViewModel>>(per);
        }

        IEnumerable<KeyWordViewModel> GetKeyWords()
        {
            var kw = personservice.GetKeyWords();
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWordViewModel>());
            return Mapper.Map<IEnumerable<KeyWordDTO>, IEnumerable<KeyWordViewModel>>(kw);
        }

        IEnumerable<KeyWordViewModel> GetKeyWords(int id)
        {
            var kw = personservice.GetKeyWordsForPerson(id);
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWordDTO, KeyWordViewModel>());
            return Mapper.Map<IEnumerable<KeyWordDTO>, IEnumerable<KeyWordViewModel>>(kw);
        }

        [HttpGet]
        public ActionResult AddKeyWord(int id)
        {
            ViewBag.PersonId = id;
            return View();
        }

        [HttpPost]
        public ActionResult AddKeyWord(KeyWordViewModel newkeyWord)
        {
            Mapper.Initialize(cfg => cfg.CreateMap<KeyWordViewModel, KeyWordDTO>());
            personservice.AddKeyWord(Mapper.Map<KeyWordViewModel, KeyWordDTO>(newkeyWord));
            return RedirectToAction("KeyWordList");
        }
    }
}