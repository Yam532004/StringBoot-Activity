package homework12._2.Am.homework12._2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/greeting")
    public String greeting(@RequestParam (defaultValue = "") String name) {
        return String.format("Hello %s!!!",name);
    }
}
