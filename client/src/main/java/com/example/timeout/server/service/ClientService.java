package com.example.timeout.server.service;

import com.example.timeout.server.config.AsyncConfig;
import com.example.timeout.server.model.User;
import com.example.timeout.server.task.MyTask;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.RejectedExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientService {

  public static final String URL = "http://localhost:8081";
  @Autowired
  AsyncConfig asyncConfig;

  public ResponseEntity<User> getUser(String syncName) {
    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    RestTemplate restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(3))
        .setReadTimeout(Duration.ofSeconds(3))
        .build();

    User user = new User();
    Random random = new Random();

    try {
      user = restTemplate.getForObject(URL, User.class);

      user.setName(user.getName() + "クライアントを通ったよ");
      return new ResponseEntity<User>(user, HttpStatus.valueOf(200));
    } catch (ResourceAccessException ex) {

      for (int i = 0; i < 10; i++) {
        System.out.printf("* Submit [%d]\n", i);
        try {
          asyncConfig.taskExecutor(syncName).submit(new MyTask(i, random.nextInt(5000)));
          //キューが0なので、プールサイズから溢れる量のスレッドを動かそうとするとダメになる
        } catch (RejectedExecutionException e) {
          System.out.println("タスク実行できませんでした" + e.getMessage());
        }
      }
      //タスクが失敗しようとしなかろうと、タイムアウトしたら"id":0,"name":null}が帰ってくる
      return new ResponseEntity<User>(user, HttpStatus.valueOf(200));

    }
  }

}
