package homework12._2.Am.homework12._2.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class DictionaryController {
    private Map<String, String> dictionary;
    public DictionaryController() {
        dictionary = new HashMap<>();
        dictionary.put("hello", "xin chào");
        dictionary.put("goodbye", "tạm biệt");
        dictionary.put("please", "làm ơn");
        dictionary.put("thank you", "cảm ơn");
        dictionary.put("yes", "có");
        dictionary.put("no", "không");
        dictionary.put("friend", "bạn");
        dictionary.put("love", "yêu");
    }

    @GetMapping("/dictionary")
   public ResponseEntity<String> dictionary(@RequestParam (defaultValue = "") String word) {
        word = word.toLowerCase();
        word = word.trim();
        if (dictionary.containsKey(word)) {
            return ResponseEntity.ok(dictionary.get(word));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
