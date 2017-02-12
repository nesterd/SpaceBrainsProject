using DataAccess.Context;
using Domain.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq.Expressions;
using System.Data.Entity;
using DataAccess.Repositories.Base;
using Utility.Helpers;

namespace DataAccess.Repositories
{
    public class PersonRepository
        : BaseRepository, IPersonRepository
    {
        public PersonRepository(WebAIDbContext context)
            : base(context)
        {

        }
        
        public void AddPerson(Person person)
        {
            if(person != null)
            {
                person.AdminId = GetCurrentUserId();
                _context.Persons.Add(person);
                _context.SaveChanges();
            }
        }

        public void AddKeyWord(KeyWord keyWord)
        {
            if (keyWord != null)
            {
                _context.KeyWords.Add(keyWord);
                _context.SaveChanges();
            }
        }
        
        public  void ChangePerson(Person person)
        {
            if (person == null)
                return;
            _context.Entry(person).State = System.Data.Entity.EntityState.Modified;
            _context.SaveChanges();
        }

        public void ChangeKeyWord(KeyWord keyword)
        {
            if (keyword == null)
                return;
            _context.Entry(keyword).State = System.Data.Entity.EntityState.Modified;
            _context.SaveChanges();
        }
        
        public void DeleteKeyWordById(int id)
        {
            DeleteKeyWord(_context.KeyWords.Find(id));
        }
        private void DeleteKeyWord(KeyWord keyword)
        {
            if (keyword == null)
                return;
            _context.KeyWords.Remove(keyword);
            _context.SaveChanges();
        }

        public void DeletePersonById(int id)
        {
            Person person = _context.Persons./*Include(x => x.KeyWords).Include(x => x.Ranks).*/Find(id);
            if (person == null)
                return;
            _context.Persons.Remove(person);
            _context.SaveChanges();
        }
        
        public IEnumerable<Person> GetPersons()
        {
            return GetPersonsByAdminId().ToArray();
        }

        public IEnumerable<KeyWord> GetKeyWords()
        {
            return _context.KeyWords.ToArray();
        }

        public IEnumerable<KeyWord> GetKeyWords(Person person)
        {
            if (person == null)
                return GetKeyWords();
            return _context.KeyWords.Where(x => x.PersonId == person.Id).ToArray();
        }

        public IEnumerable<KeyWord> GetKeyWords(string personName)
        {
            return GetKeyWords(_context.Persons.FirstOrDefault(x => x.Name.Trim().ToLower() == personName.Trim().ToLower()));
        }

        public IEnumerable<KeyWord> GetKeyWords(int personId)
        {
            return GetKeyWords(_context.Persons.FirstOrDefault(x => x.Id == personId));
        }
        
        public KeyWord GetKeyWord(int id)
        {
            return _context.KeyWords.FirstOrDefault(x => x.Id == id);
        }

        public Person GetPerson(int id)
        {
            return GetPersonsByAdminId().FirstOrDefault(x => x.Id == id);
        }

        IEnumerable<Person> GetPersonsByAdminId()
        {
            string name = GetCurrentUserName();
            return _context.Persons.Where(x => x.Admin.Login == name).ToArray();
        }
    }
}
