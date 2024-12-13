package homework12._2.Am.homework12._2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Parameter;

@RestController
public class CalculatorController {
    @GetMapping("/calculator")
    public ResponseEntity<String> calculator(
            @RequestParam (value = "firstNumber", defaultValue = "") String firstNumberStr,
            @RequestParam (value = "secondNumber", defaultValue = "") String secondNumberStr,
            @RequestParam (value = "operator", defaultValue = "") String operatorStr
    )
    {
        operatorStr = operatorStr.trim();

        if( firstNumberStr.isEmpty() || secondNumberStr.isEmpty() || operatorStr.isEmpty() ) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
        double firstNumber = Double.parseDouble(firstNumberStr);
        double secondNumber = Double.parseDouble(secondNumberStr);
        char operator = operatorStr.charAt(0);
        double result = 0;
        switch (operator) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber == 0) {
                    return ResponseEntity.badRequest().body("Division by zero is not allowed");
                }
                result = firstNumber / secondNumber;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid operator");
        }
        return ResponseEntity.ok("Result: " + result );
    }

}
