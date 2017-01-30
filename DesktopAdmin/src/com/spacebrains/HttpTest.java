/**
 * 
 */
package com.spacebrains;

import com.spacebrains.core.http.HttpProvider;
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

		System.out.println("А теперь исправим в базе 4 запись");
		System.out.println(personRepository.put(new Person(4, "Мишаня")));
//		System.out.println("А теперь добавим в базу Навального");
//		System.out.println(personRepository.put(new Person(5, "Навальный")));
		System.out.println(Arrays.asList(personRepository.get()));


//      на данный момент в REST уже включена авторизация на данный тип запроса
		SiteRepository siteRepository = new SiteRepository();
		System.out.println(Arrays.asList(siteRepository.get()));
	}

}
