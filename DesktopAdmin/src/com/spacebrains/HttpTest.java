package com.spacebrains;

import com.spacebrains.core.dao.UserRepository;
import com.spacebrains.core.http.HttpProvider;
import com.spacebrains.model.*;

import java.util.Scanner;

/**
 * @author oleg.chizhov
 *
 */
public class HttpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		HttpProvider httpProvider = new HttpProvider();
//		httpProvider.setJSONString("{\"username\":\"root\", \"password\":\"password\"}");
//		int status = 0;
//		try {
//			status = httpProvider.doPostMethod("/login");
//		} catch (RuntimeException e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println(status);
//		System.out.println(httpProvider.getStatusLine());

		UserRepository userRepo = new UserRepository();
		Scanner sc = new Scanner(System.in);
		System.out.print("Введите логин: ");
		String login = sc.nextLine();
		System.out.print("Введите пароль: ");
		String password = sc.nextLine();
		User currentUSer = userRepo.login(login, password);
		System.out.println(userRepo.getToken());

		userRepo.get(currentUSer.getRole());
	}

}
