package app.errors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoItemInProductCatalog.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(NoItemInProductCatalog ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler({JsonMappingException.class, JsonProcessingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleJsonProcessingException(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

//    @ExceptionHandler({InvalidFormatException.class, MismatchedInputException.class})
//    public void handlerIllegalArgumentException(JsonProcessingException exception,
//                                                ServletWebRequest webRequest) throws IOException {
//        if(exception instanceof InvalidFormatException) {
//            webRequest.getResponse().sendError(HttpStatus.CONFLICT.value(), exception.getMessage());
//        } else if (exception instanceof MismatchedInputException) {
//            webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
//        }
//    }
}
