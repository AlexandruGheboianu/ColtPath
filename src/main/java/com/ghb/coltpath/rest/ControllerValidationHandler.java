package com.ghb.coltpath.rest;

import com.ghb.coltpath.dto.FieldMessage;
import com.ghb.coltpath.dto.RequestOutcomeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ghebo on 1/16/2016.
 */
@ControllerAdvice
public class ControllerValidationHandler {
    @Autowired
    private MessageSource msgSource;


    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RequestOutcomeMessage processAuthorizationError(ForbiddenException ex) {
        return new RequestOutcomeMessage(ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RequestOutcomeMessage processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        RequestOutcomeMessage message = new RequestOutcomeMessage("ERROR");
        message = processFieldError(message, result.getFieldErrors());
        message = processGlobalErrors(message, result.getGlobalErrors());
        return message;
    }

    private RequestOutcomeMessage processGlobalErrors(RequestOutcomeMessage message, List<ObjectError> globalErrors) {
        if (globalErrors != null) {
            Locale locale = LocaleContextHolder.getLocale();
            List<String> globalMessages = new ArrayList<>();
            for (ObjectError globalError : globalErrors) {
                globalMessages.add(msgSource.getMessage(globalError.getDefaultMessage(), null, locale));
            }
            message.setGlobalMessages(globalMessages);
        }
        return message;
    }

    private RequestOutcomeMessage processFieldError(RequestOutcomeMessage message, List<FieldError> errors) {
        if (errors != null) {
            Locale locale = LocaleContextHolder.getLocale();
            List<FieldMessage> fieldMessages = new ArrayList<>();
            for (FieldError fieldError : errors) {
                fieldMessages.add(new FieldMessage(fieldError.getField(),
                        msgSource.getMessage(fieldError.getDefaultMessage(), null, locale)));
            }
            message.setFields(fieldMessages);
        }
        return message;
    }
}
