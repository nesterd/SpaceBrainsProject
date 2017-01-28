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

namespace DataAccess.Repositories
{
    public class PersonRepository
        : IPersonRepository
    {
        WebAIDbContext context;

        public PersonRepository(WebAIDbContext context)
        {
            this.context = context;
        }

        public void AddPerson(Person person)
        {
            if(person != null)
            {
                context.Persons.Add(person);
                context.SaveChanges();
            }
            
        }

        public void AddKeyWord(KeyWord keyWord)
        {
            if (keyWord != null)
            {
                context.KeyWords.Add(keyWord);
                context.SaveChanges();
            }

        }

        //public void AddSite(Site site, string url)
        //{
        //    if (site != null)
        //    {
        //        context.Sites.Add(site);
        //        context.SaveChanges();

        //        context.Pages.Add(new Page { Url = url, SiteId = site.Id, FoundDateTime = DateTime.Now });
        //        context.SaveChanges();
        //    }
        //}

        public  void ChangePerson(Person person)
        {
            if (person == null)
                return;
            context.Entry(person).State = System.Data.Entity.EntityState.Modified;
            context.SaveChanges();
        }

        public void ChangeKeyWord(KeyWord keyword)
        {
            if (keyword == null)
                return;
            context.Entry(keyword).State = System.Data.Entity.EntityState.Modified;
            context.SaveChanges();
        }

        //public void ChangeSite(Site site)
        //{
        //    if (site == null)
        //        return;
        //    context.Entry(site).State = System.Data.Entity.EntityState.Modified;
        //    context.SaveChanges();
        //}

        public void DeleteKeyWordById(int id)
        {
            DeleteKeyWord(context.KeyWords.Find(id));
        }
        private void DeleteKeyWord(KeyWord keyword)
        {
            if (keyword == null)
                return;
            context.KeyWords.Remove(keyword);
            context.SaveChanges();
        }

        public void DeletePersonById(int id)
        {
            Person person = context.Persons.Include(x => x.KeyWords).Include(x => x.Ranks).FirstOrDefault(x => x.Id == id);
            if (person == null)
                return;
            context.Persons.Remove(person);
            context.SaveChanges();
        }

        //public void DeleteSiteById(int id)
        //{
        //    Site site = context.Sites.Include(x => x.Pages).FirstOrDefault(x => x.Id == id);
        //    if (site == null)
        //        return;
        //    context.Sites.Remove(site);
        //    context.SaveChanges();
        //}

        public IEnumerable<Person> GetPersons()
        {
            return context.Persons.ToArray();
        }

        public IEnumerable<KeyWord> GetKeyWords()
        {
            return context.KeyWords.ToArray();
        }

        public IEnumerable<KeyWord> GetKeyWords(Person person)
        {
            if (person == null)
                return GetKeyWords();
            return context.KeyWords.Where(x => x.PersonId == person.Id).ToArray();
        }

        public IEnumerable<KeyWord> GetKeyWords(string personName)
        {
            return GetKeyWords(context.Persons.FirstOrDefault(x => x.Name.Trim().ToLower() == personName.Trim().ToLower()));
        }

        public IEnumerable<KeyWord> GetKeyWords(int personId)
        {
            return GetKeyWords(context.Persons.FirstOrDefault(x => x.Id == personId));
        }

        public Person GetPerson(int id)
        {
            return context.Persons.FirstOrDefault(x => x.Id == id);
        }

        public KeyWord GetKeyWord(int id)
        {
            return context.KeyWords.FirstOrDefault(x => x.Id == id);
        }

       
    }
}
