/**
 * 
 */
package com.spacebrains;

import com.spacebrains.core.dao.KeywordRepository;
import com.spacebrains.core.dao.PersonRepository;
import com.spacebrains.model.*;

import java.util.Arrays;

/**
 * @author oleg.chizhov
 *
 */
public class HttpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PersonRepository personRepository = new PersonRepository();
		System.out.println(Arrays.asList(personRepository.get()));
//
//		System.out.println("А теперь исправим в базе 4 запись");
//		System.out.println(personRepository.put(new Person(4, "Мишаня")));
//		System.out.println(Arrays.asList(personRepository.get()));
//
//		System.out.println("А теперь добавим в базу Навального");
//		System.out.println(personRepository.put(new Person(5, "Навальный")));
//		System.out.println(Arrays.asList(personRepository.get()));

		KeywordRepository keyRepo = new KeywordRepository();
		System.out.println(keyRepo.getByObject(new Person(1,"Путин")));

//		SiteRepository siteRepository = new SiteRepository();
//		System.out.println(Arrays.asList(siteRepository.get()));
//		System.out.println("Изменим сайт с id=3:");
//		System.out.println(siteRepository.put(new Site(3,"ненормальный.рф")));
//		System.out.println(Arrays.asList(siteRepository.get()));
	}

}
