package com.ssolution.admin.system.controller.common;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssolution.admin.system.util.AESCipher;

/**
 * <PRE>
 * 1. FileName	: UsedPhoneApiController.java
 * 2. Package	: com.ssolution.admin.system.controller.common
 * 3. Comment	: 
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 5:02:42
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@RestController
public class UsedPhoneApiController {
	@Value("${USEDPHONE_API_URL}")
    private String USEDPHONE_API_URL;
	@Value("${API_TOKEN}")
    private String API_TOKEN;
	
	@Autowired
    RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@RequestMapping(value = "/usedPhone/api", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf8")
	public String adminApi(HttpServletRequest request, @Nullable @RequestBody Map<String, Object> params)  throws JsonProcessingException {
		String apiUrl = request.getParameter("apiUrl");
		String url = USEDPHONE_API_URL + apiUrl;
		String method = request.getMethod().toUpperCase();
		HttpEntity<?> entity = null;
		ResponseEntity<String> response = null;
		String result = "";
		
		if("POST".equals(method)) {
			String encParams = "";
			
            try {
            	JSONObject json = new JSONObject();
            	
            	for( Map.Entry<String, Object> entry : params.entrySet() ) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    json.put(key, value);
                }
            	
            	String secretKey = String.format("%-" + 32 + "s", API_TOKEN).replace(' ', '0');
            	encParams = AESCipher.encrypt(json.toString(), secretKey.getBytes());
            	entity = apiClientHttpEntity(encParams);
            	response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            	result = AESCipher.decrypt(response.getBody(), secretKey.getBytes());
            } catch(Exception e) {
            	e.printStackTrace();
            	logger.error("Used Phone Api Error...", e);
            }
		} else {
			entity = apiClientHttpEntity(params);
			
			StringBuilder paramString = new StringBuilder();
			Enumeration enumeration = request.getParameterNames();
			
			while(enumeration.hasMoreElements()) {
	            String parameterName = enumeration.nextElement().toString();
	            
	            if(!"apiUrl".equals(parameterName)) {
	            	paramString.append(parameterName).append("=").append(request.getParameter(parameterName)).append("&");
	            }
	        }
			
			if (paramString.length() > 0) {
				url = url + "?" + paramString.toString().substring(0, paramString.length()-1);
			}
			
			response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			result = response.getBody();
		}
		
		return result;
	}
	
    private HttpEntity<?> apiClientHttpEntity(Object params) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Bearer " + API_TOKEN);
        requestHeaders.set("Content-Type", "application/json");
        requestHeaders.set("cipher-enable", "True");

        if ("".equals(params) || StringUtils.isEmpty(params))
            return new HttpEntity<Object>(requestHeaders);
        else
            return new HttpEntity<Object>(params, requestHeaders);

    }
}
