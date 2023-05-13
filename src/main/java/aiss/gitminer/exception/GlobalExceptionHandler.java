package aiss.gitminer.exception;

<<<<<<< HEAD
public class GlobalExceptionHandler {
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> handleValidationException(MethodArgumentNotValidException except) {
        List<FieldError> fieldErrors = except.getBindingResult().getFieldErrors();
        List<String> errores = fieldErrors.stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        Map<String, List<String>> res = new HashMap<>();
        res.put("errores",errores);

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

    }
>>>>>>> origin/main
}
