package com.example.timeout.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

	@GetMapping("/")
	public ResponseEntity<User> index(){
		User user = new User(1, "John");
		ResponseEntity<User> res = new ResponseEntity<User>(user, HttpStatus.valueOf(200));

		try {
			//呼び出し元でタイムアウトさせるために敢えて5秒遅くする
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			//e.printStackTrace();
			return res;
		}

		return res;
	}


}
