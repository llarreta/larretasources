package ar.com.larreta.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/helloworld")
public class HelloWorldController extends ar.com.larreta.rest.controllers.ParentController {
}
