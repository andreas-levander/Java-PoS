package app.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleException(NotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

//    @ExceptionHandler({JsonMappingException.class, JsonProcessingException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public @ResponseBody ErrorResponse handleJsonProcessingException(RuntimeException ex) {
//        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
//    }

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
