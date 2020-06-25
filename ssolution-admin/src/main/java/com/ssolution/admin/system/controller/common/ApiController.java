package com.ssolution.admin.system.controller.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssolution.admin.system.util.AESCipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * <PRE>
 * 1. FileName	: ApiController.java
 * 2. Package	: com.ssolution.admin.system.controller.common
 * 3. Comment	: Api 호출 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:36:03
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@RestController
@RequestMapping(value = "/api")
public class ApiController {
    @Value("${API_HOST}")
    private String API_HOST;
    @Value("${API_PORT}")
    private String API_PORT;
    @Value("${API_URL_CONVS}")
    private String API_URL_CONVS;
    @Value("${API_URL_SUBS}")
    private String API_URL_SUBS;
    @Value("${API_URL_CHANGE}")
    private String API_URL_CHANGE;
    @Value("${API_TOKEN}")
    private String API_TOKEN;
    @Value("${MOBILE_APP_TOKEN}")
    private String MOBILE_APP_TOKEN;
    @Value("${API_URL_MARKET}")
    private String API_URL_MARKET;
        
    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    ObjectMapper mapper = new ObjectMapper();

    /**
     * <PRE>
     * 1. MethodName: getApiServerUrl
     * 2. ClassName : ApiController
     * 3. Comment   : api 서버 정보
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @param module
     * @return String
     */
    private String getApiServerUrl(String module) {
        String apiUrl = "";
        switch(module){
            case "conversations":
                apiUrl = API_HOST + ":" + API_PORT + API_URL_CONVS;
                break;
            case "subscription":
                apiUrl = API_HOST + ":" + API_PORT + API_URL_SUBS;
                break;
            case "change":
                apiUrl = API_HOST + ":" + API_PORT + API_URL_CHANGE;
                break;
            case "market":
                apiUrl = API_HOST + ":" + API_PORT + API_URL_MARKET;
                break;
        }
        return apiUrl;
    }

    /**
     * <PRE>
     * 1. MethodName: apiClientHttpEntity
     * 2. ClassName : ApiController
     * 3. Comment   : Authorization 정보
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @param appType
     * @param params
     * @return HttpEntity<?>
     */
    private HttpEntity<?> apiClientHttpEntity(String appType, Object params, String module) {

        HttpHeaders requestHeaders = new HttpHeaders();
        if ("img".equals(module)) {
            requestHeaders.set("Authorization", "Bearer " + MOBILE_APP_TOKEN);
        } else {
            requestHeaders.set("Authorization", "Bearer " + API_TOKEN);
        }
        requestHeaders.set("Content-Type", "application/" + appType);

        if ("".equals(params) || StringUtils.isEmpty(params))
            return new HttpEntity<Object>(requestHeaders);
        else
            return new HttpEntity<Object>(params, requestHeaders);

    }


    /**
     * <PRE>
     * 1. MethodName: apiTemplate
     * 2. ClassName : ApiController
     * 3. Comment   : api 호출
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @return String 페이지 URL
     * @param request {@link HttpServletRequest}
     * @param params
     * @param module
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/{module}", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, produces = "application/json; charset=utf8")
    public ResponseEntity adminApiTemplate(HttpServletRequest request, @Nullable @RequestBody Map<String, Object> params, @PathVariable("module") String module) throws IOException {

        String method = request.getMethod().toUpperCase();
        String resultJson = "";
        HttpStatus status = HttpStatus.OK;

        switch (method){
            case "GET":
                if ("img".equals(module)) {
                    return getImgApi(request, module);
                } else {
                    resultJson = getRestApi(request, module);
                }
                break;
            case "POST":
                resultJson = postRestApi(request, params, module);
                break;
            case "PUT":
                resultJson = putRestApi(request, params, module);
                break;
            case "DELETE":
                status = deleteRestApi(request, params, module);
                break;
        }

        return new ResponseEntity(resultJson, status);
    }

    /**
     * <PRE>
     * 1. MethodName: getImgApi
     * 2. ClassName : ApiController
     * 3. Comment   : 이미지 호출
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @param request
     * @param module
     * @return response
     * @throws IOException
     */
    public ResponseEntity getImgApi(HttpServletRequest request, String module) throws IOException {

        String baseUrl = request.getParameter("apiUrl");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<?> entity = apiClientHttpEntity("json", null, module);

        ResponseEntity<byte[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, byte[].class, "1");

        if (response.getStatusCode() == HttpStatus.OK) {
            if(baseUrl.toUpperCase().endsWith("PNG")){
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (baseUrl.toUpperCase().endsWith("JPG") || baseUrl.toUpperCase().endsWith("JPEG")){
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (baseUrl.toUpperCase().endsWith("GIF")){
                headers.setContentType(MediaType.IMAGE_GIF);
            } else {
                headers.setContentType(MediaType.IMAGE_JPEG);
            }
            return new ResponseEntity(response.getBody(), headers, HttpStatus.OK);
        }
        return null;
    }

     /**
      * <PRE>
      * 1. MethodName: getRestApi
      * 2. ClassName : ApiController
      * 3. Comment   : get method 처리
      * 4. 작성자    : HHG
      * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
      * </PRE>
      *
     * @param request
     * @param module
      * @return response
      * @throws JsonProcessingException
     */
    public String getRestApi(HttpServletRequest request, String module) throws JsonProcessingException {

        StringBuilder paramString = new StringBuilder();
        Enumeration enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()){
            String parameterName = enumeration.nextElement().toString();
            paramString.append(parameterName).append("=").append(request.getParameter(parameterName)).append("&");
        }

        String apiUrl = request.getParameter("apiUrl");
        String isTablesDrawed = request.getParameter("isTablesDrawed");
        String baseUrl = getApiServerUrl(module) + apiUrl;

        Map<String, Object> resultMap = new HashMap();
        if (isTablesDrawed != null && "false".equals(isTablesDrawed)) {
            resultMap.put("status", "200");
            resultMap.put("data", "");
            return mapper.writeValueAsString(resultMap);
        } else {
            if (paramString.length()>0)
                baseUrl = baseUrl + "?" + paramString.toString().substring(0, paramString.length()-1);

            int statusCode = 200;
            String errorResponse = null;
            ResponseEntity<String> response = null;
            HttpEntity<?> requestEntity = apiClientHttpEntity("json", null, module);
            try {
                response = restTemplate.exchange(baseUrl, HttpMethod.GET, requestEntity, String.class);
            }catch (HttpStatusCodeException e){
                statusCode = e.getStatusCode().value();
                errorResponse = e.getResponseBodyAsString();
                resultMap.put("status", statusCode);
                resultMap.put("data", errorResponse);
            }
            if (statusCode == 200) return response.getBody();
            else return mapper.writeValueAsString(resultMap);
        }
    }

    /**
     * <PRE>
     * 1. MethodName: getRestApi
     * 2. ClassName : ApiController
     * 3. Comment   : post method 처리
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @param request
     * @param params
     * @param module
     * @return response
     * @throws JsonProcessingException
     */
    public String postRestApi(HttpServletRequest request, Map<String, Object> params, String module) throws JsonProcessingException {
    	String secretKey = String.format("%-" + 32 + "s", API_TOKEN).replace(' ', '0');
        String apiUrl = String.valueOf(params.get("apiUrl"));
        String baseUrl = getApiServerUrl(module) + apiUrl;
        String isTablesDrawed = String.valueOf(params.get("isTablesDrawed"));
        int statusCode = 200;
        String errorResponse = null;
        ResponseEntity<String> response = null;
        HttpEntity<?> requestEntity = null;
        // 마켓api호출시 암복호화설정
        if( "market".equals(module) ) {
        	String encParams = "";
            try {
            	JSONObject json = new JSONObject();
                json.put("data", params);
            	encParams = AESCipher.encrypt(json.toString(), secretKey.getBytes());
            	requestEntity = apiClientHttpEntity("json", encParams, module);
            } catch(Exception e) {
            	e.printStackTrace();
            }
        }
        else {
        	requestEntity = apiClientHttpEntity("json", params, module);
        }
        Map<String, Object> resultMap = new HashMap();
        if (isTablesDrawed != null && "false".equals(isTablesDrawed)) {
            resultMap.put("status", "200");
            resultMap.put("data", "");
            return mapper.writeValueAsString(resultMap);
        } else {
            try {
                response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);
            }catch (HttpStatusCodeException e){
                statusCode = e.getStatusCode().value();
                errorResponse = e.getResponseBodyAsString();
                if (statusCode == 500) errorResponse = "";
                resultMap.put("status", statusCode);
                resultMap.put("data", errorResponse);
            }
            String result = "";
            if (statusCode == 200) {
                // 마켓api호출시 암복호화설정
            	if( "market".equals(module) ) {
            		try {	            	
                		result = AESCipher.decrypt(response.getBody(), secretKey.getBytes());
                	}catch (Exception e) {
                		e.printStackTrace();
                	}
            	}
            	else {
            		result = response.getBody();
            	}
            }
            else {
            	result =  mapper.writeValueAsString(resultMap);
            }
            return result;
            
        }
    }

    /**
     * <PRE>
     * 1. MethodName: getRestApi
     * 2. ClassName : ApiController
     * 3. Comment   : put method 처리
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @param request
     * @param params
     * @param module
     * @return response
     */
    public String putRestApi(HttpServletRequest request, Map<String, Object> params, String module) {

        String id = String.valueOf(params.get("id"));
        String apiUrl = String.valueOf(params.get("apiUrl"));
        String baseUrl = getApiServerUrl(module) + apiUrl + "/" + id;
        HttpEntity<?> requestEntity = apiClientHttpEntity("json", params, module);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, String.class);

        return response.getBody();
    }

    /**
     * <PRE>
     * 1. MethodName: getRestApi
     * 2. ClassName : ApiController
     * 3. Comment   : delete method 처리
     * 4. 작성자    : HHG
     * 5. 작성일    : 2019. 03. 20. 오후 3:10:49
     * </PRE>
     *
     * @param request
     * @param params
     * @param module
     * @return response
     */
    public HttpStatus deleteRestApi(HttpServletRequest request, Map<String, Object> params, String module) {

        String id = String.valueOf(params.get("id"));
        String apiUrl = String.valueOf(params.get("apiUrl"));
        String baseUrl = getApiServerUrl(module) + apiUrl + "/" + id;
        HttpEntity<?> requestEntity = apiClientHttpEntity("json", "", module);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, requestEntity, String.class);

        return response.getStatusCode();
    }

}