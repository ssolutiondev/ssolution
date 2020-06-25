package com.ssolution.admin.system.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssolution.admin.system.upload.ImageUpload;
import com.ssolution.core.constant.EndPoint;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: JsonUtil.java
 * 2. Package	: com.ssolution.admin.system.util
 * 3. Comment	: JSON 관련 유틸
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 18. 오후 3:01:30
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 18.	|	신규 작성
 * </PRE>
 */
public class JsonUtil {

	@Value("${FILE_UPLOAD_PATH}")
    private String FILE_UPLOAD_PATH;
	
	@Value("${FILE_UPLOAD_TOKEN}")
	private String FILE_UPLOAD_TOKEN;
	
    /** the logger. */
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * <PRE>
     * 1. ClassName		: JsonUtil
     * 2. MethodName	: getJson
     * 3. Comment		: JSON 파싱
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 18. 오후 3:03:17
     *	- 
     * </PRE>
     * @param request
     * @return
     * @throws Exception
     */
    private JSONObject getJson(MultipartHttpServletRequest request) throws Exception {
		Map<String, Object> attrTempMap = new HashMap<>();
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		int length = 0;
		String mdlCd = "";
		
		for(Enumeration<String> en = request.getParameterNames(); en.hasMoreElements();) {
			String key = en.nextElement();
			String value = request.getParameter(key);
			
			if(key.startsWith("attr.")) {
				String[] split = key.split("\\.");
				int idx = Integer.parseInt(split[1]);
				
				if(idx > length) {
					length = idx;
				}
				
				attrTempMap.put(key, value);
			} else {
				if("mnco_mdl_cd".equals(key)) {
					mdlCd = value;
				}
				
				json.put(key, value);
			}
		}
		
		Iterator<String> itr = request.getFileNames();
		
		while(itr.hasNext()) {
			String key = itr.next();
			MultipartFile mpf = request.getFile(key);
			attrTempMap.put(key, mpf);
		}
		
		for(int i=0; i<length+1; i++) {
			JSONObject attrObj = new JSONObject();
			String endPoint = "";
			String seq = "";
			MultipartFile file = null;
			
			for(String key : attrTempMap.keySet()) {
				String[] split = key.split("\\.");
				int idx = Integer.parseInt(split[1]);
				
				if(idx == i) {
					Object obj = attrTempMap.get(key);
					
					if("attr_tp".equals(split[2])) {
						if("03".equals(obj)) {
							endPoint = EndPoint.UPLOAD_PHONE_COLOR_IMAGE;
						} else if("04".equals(obj)) {
							endPoint = EndPoint.UPLOAD_PHONE_IMAGE;
						}
					}
					
					if("seq".equals(split[2])) {
						seq = obj.toString();
					}
					
					if("img_file".equals(split[2]) && obj != null) {
						file = (MultipartFile)obj;
					} else {
						attrObj.put(split[2], obj);
					}
				}
			}
			
			if(file != null) {
				// 파일 업로드
				String imgPath = ImageUpload.fileUpload(file, FILE_UPLOAD_PATH + "/" + endPoint, mdlCd + "_" + seq, FILE_UPLOAD_TOKEN);
				attrObj.put("img_path", imgPath);
			}
			
			array.put(attrObj);
		}
		
		json.put("attr", array);
		
		return json;
	}

}
