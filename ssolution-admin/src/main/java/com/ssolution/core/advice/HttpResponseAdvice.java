package com.ssolution.core.advice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssolution.admin.system.service.common.mask.MaskInterceptorService;
import com.ssolution.admin.system.util.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;


@ControllerAdvice(basePackages = {"com.ssolution.admin.system.controller"})
public class HttpResponseAdvice implements ResponseBodyAdvice<Object> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaskInterceptorService maskInterceptorService;


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // Mask 처리 공통 로직을 작성 한다.

        if(body == null) return body;
        String response = body.toString();
        try {
            ObjectMapper mapper = new ObjectMapper();

            if(response.startsWith("[")){
                List<Map<String,Object>> responseObject = mapper.readValue(response, new TypeReference<List<Map<String, Object>>>(){});
                logger.debug("before mask process : {}" , responseObject.toString());
                maskInterceptorService.processMaskList(CommonUtil.getSessionManager().getSoId(), (boolean) CommonUtil.getSession().getAttribute("maskSwitch"), responseObject);
                logger.debug("after mask process : {}" , responseObject.toString());
                response = mapper.writeValueAsString(responseObject);
            }else{
                Map<String, Object> responseObject = mapper.readValue(response, new TypeReference<Map<String, Object>>(){});
                logger.debug("before mask process : {}" , responseObject.toString());
                maskInterceptorService.processMask(CommonUtil.getSessionManager().getSoId(), (boolean) CommonUtil.getSession().getAttribute("maskSwitch"), responseObject);
                logger.debug("after mask process : {}" , responseObject.toString());
                response = mapper.writeValueAsString(responseObject);
            }
        } catch (Exception e){
            logger.warn("mask process error ", e);
            return body;
        }

        return response;
    }
}
