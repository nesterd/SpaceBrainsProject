/**
 * 
 */
package com.spacebrains;

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

//		HttpProvider provider = new HttpProvider();
//		//provider.doGetRequest("/id/1/name/Путин");
//		provider.putJSONString("{\n \"id\": \"1\",\n \"name\": \"%D0%9F%D1%83%D1%82%D0%B8%D0%BD\"\n}\n");
//		provider.doPutRequest("/persons/1");
//		System.out.println(provider.getJSONString());

		KeywordDto key = new KeywordDto(1, "Путину", 1);
		System.out.println(key.toJSONString());

//		Storage<PersonDto> storage = new Storage<>();
		KeywordRepository storage = new KeywordRepository();
		PersonDto personDto = new PersonDto(1, "Медведев");
		System.out.println(personDto);
		System.out.println(Arrays.asList(storage.getByObject(personDto)));
		
		System.out.println(Arrays.asList(storage.get()));
	}

}
