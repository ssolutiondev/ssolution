package com.ssolution.admin.system.upload;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssolution.core.constant.EndPoint;

public class ImageUpload {
		
	public static String fileUpload(MultipartFile file, String url, String modelNumber, String token) throws Exception {
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Bearer " + token);
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        if(!url.endsWith(EndPoint.UPLOAD_BANNER_IMAGE)) {
        	requestHeaders.set("model_number", modelNumber);
        }
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        File f = convert(file);
        body.add("file", new FileSystemResource(f));
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);
		JsonNode json = response.getBody();
		System.out.println(json.toString());
		int statusCode = json.get("statusCode").asInt();
		String imgPath = "";
		
		if(statusCode == 200) {
			JsonNode resultBody = json.get("body");
			
			if(resultBody.isArray()) {
				for(JsonNode node : resultBody) {
					imgPath = node.get("cdn").asText();
					break;
				}
			}
			
			if(f.exists()) {
				f.delete();
			}
		}
		
		return imgPath;
	}
	
	private static File convert(MultipartFile file) throws Exception {
		File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
		
		return convFile;
	}
}
