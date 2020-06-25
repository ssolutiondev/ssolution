package com.ssolution.admin.system.controller.common;

import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.core.exception.HttpStatusErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;


/**
 * <PRE>
 * 1. FileName	: ErrorController.java
 * 2. Package	: com.ssolution.admin.system.controller.common
 * 3. Comment	: 에러 처리
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:36:20
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {
    /**
     * Dashboard 메인 URL
     */
    private static String URL = "error";

    @RequestMapping(value = "{errorCode}", method = {RequestMethod.GET, RequestMethod.POST})
    public String error(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model, @PathVariable int errorCode) {

        String header = httpServletRequest.getHeader("x-requested-with");
        if("XMLHttpRequest".equals(header)){
            throw new HTTPException(errorCode);
        }else{
            HttpStatus status = HttpStatus.resolve(errorCode);
            HttpStatusErrorMessage message = HttpStatusErrorMessage.resolveHttpStatus(status.value());
            Object[] obj = {status.value()};

            model.addAttribute("status",  status.value());
            model.addAttribute("message",status.getReasonPhrase());
            model.addAttribute("error", message == null ? MessageUtil.getMessage("MSG.M08.MSG00780", obj) : MessageUtil.getMessage(message.getReasonPhrase()));
            return URL + "/error";
        }
    }
}