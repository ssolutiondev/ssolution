package com.ssolution.core.advice;

import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.admin.system.domain.common.error.CustomErrorVO;
import com.ssolution.core.exception.HttpStatusErrorMessage;
import com.ssolution.core.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;


@ControllerAdvice(basePackages = "com.ssolution.admin")
public class ErrorAdvice extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);

        if(ex instanceof ServiceException){
            return new ResponseEntity<>(new CustomErrorVO(status.value(), status.getReasonPhrase(), ex.getMessage()), status);
        }else if(ex instanceof HTTPException){
            HttpStatus httpStatus = HttpStatus.resolve(((HTTPException) ex).getStatusCode());
            HttpStatusErrorMessage message = HttpStatusErrorMessage.resolveHttpStatus(httpStatus.value());
            Object[] obj = {httpStatus.value()};
            return new ResponseEntity<>(new CustomErrorVO(httpStatus.value(), httpStatus.getReasonPhrase(), message == null ? MessageUtil.getMessage("MSG.M08.MSG00780", obj) : MessageUtil.getMessage(message.getReasonPhrase())), status);
        }else{
            Object[] obj = {ex.toString()};
            CustomErrorVO error = new CustomErrorVO(status.value(), status.getReasonPhrase(), MessageUtil.getMessage("MSG.M08.MSG00780", obj));
            return new ResponseEntity<>(error, status);
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
