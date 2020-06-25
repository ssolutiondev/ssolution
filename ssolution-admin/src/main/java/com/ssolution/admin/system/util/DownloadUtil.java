package com.ssolution.admin.system.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * <PRE>
 * 1. FileName	: DownloadUtil.java
 * 2. Package	: com.ssolution.admin.system.util
 * 3. Comment	: 파일 다운로드 유틸
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:56:13
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class DownloadUtil {

    public static String getBrowser(HttpServletRequest request) {

        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        } else if (header.indexOf("Trident/7.0") > -1) {
            //IE 11 이상 //IE 버전 별 체크 >> Trident/6.0(IE 10) , Trident/5.0(IE 9) , Trident/4.0(IE 8)
            return "MSIE";
        }
        return "Firefox";

    }

    public static String getDisposition(String filename, String browser) throws Exception {

        String encodedFilename = null;
        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else {
            throw new RuntimeException("Not supported browser");
        }
        return encodedFilename;

    }

}
