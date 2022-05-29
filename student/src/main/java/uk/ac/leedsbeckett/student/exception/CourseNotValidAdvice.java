package uk.ac.leedsbeckett.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CourseNotValidAdvice {

    /**
     * ResponseBody signals that this advice is rendered straight into the response body.     *
     * ExceptionHandler configures the advice to only respond if an InvoiceNotValidException is thrown.     *
     * ResponseStatus says to issue an HttpStatus.UNPROCESSABLE_ENTITY, i.e. an HTTP 422.
     *
     * The body of the advice generates the content.
     */

    @ResponseBody
    @ExceptionHandler(CourseNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String invoiceNotValidHandler(CourseNotValidException ex) {
        return ex.getMessage();
    }
}
