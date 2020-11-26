package com.github.mymarquee.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

  @GetMapping("/hello-world")
  public String sayHello() {
    return "Hello World";
  }

}