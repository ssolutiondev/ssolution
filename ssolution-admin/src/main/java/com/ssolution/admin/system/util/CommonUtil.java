package com.ssolution.admin.system.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssolution.admin.system.domain.common.menu.SelectedMenuVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: CommonUtil.java
 * 2. Package	: com.ssolution.admin.system.util
 * 3. Comment	: 공통 유틸
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:55:32
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class CommonUtil {

    /**
     * <PRE>
     * 1. MethodName: getSession
     * 2. ClassName : CommonUtil
     * 3. Comment   : 세션 정보 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:32:27
     * </PRE>
     *
     * @return HttpSession {@link HttpSession}
     */
    public static HttpSession getSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (servletRequestAttributes == null) {
            return null;
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getSession(false);
    }

    /**
     * <PRE>
     * 1. MethodName: getCreateSession
     * 2. ClassName : CommonUtil
     * 3. Comment   : 세션 생성
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:32:37
     * </PRE>
     *
     * @return HttpSession {@link HttpSession}
     */
    public static HttpSession getCreateSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession(true);
    }

    /**
     * <PRE>
     * 1. MethodName: getSessionManager
     * 2. ClassName : CommonUtil
     * 3. Comment   : SessionUser 정보 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:32:53
     * </PRE>
     *
     * @return SessionUser 세션USER 정보
     */
    public static SessionUserVO getSessionManager() {
        HttpSession session = getSession();

        if (session != null) {
            SessionUserVO sessionUser = (SessionUserVO) session.getAttribute("session_user");
            return sessionUser;
        }

        return null;
    }

    /**
     * <PRE>
     * 1. MethodName: setSessionManager
     * 2. ClassName : CommonUtil
     * 3. Comment   : SessionUser를 저장
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:33:11
     * </PRE>
     *
     * @return void
     * @param sessionUser 세션USER 정보
     */
    public static void setSessionManager(SessionUserVO sessionUser) {
        HttpSession session = getCreateSession();

        if (sessionUser != null) {
            session.setAttribute("session_user", sessionUser);
        }
    }

    /**
     * <PRE>
     * 1. MethodName: getSessionSelectMenuInfo
     * 2. ClassName : CommonUtil
     * 3. Comment   : Session Menu 선택 정보 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:33:55
     * </PRE>
     *
     * @return SelectedMenuVO 메뉴 정보
     */
    public static SelectedMenuVO getSessionSelectMenuInfo() {
        HttpSession session = getSession();

        if (session != null) {
            SelectedMenuVO menuInfo = (SelectedMenuVO) session.getAttribute("selectedMenu");
            return menuInfo;
        }

        return null;
    }

    /**
     * <PRE>
     * 1. MethodName: setSessionSelectMenuInfo
     * 2. ClassName : CommonUtil
     * 3. Comment   : Session에 선택 메뉴 정보 세팅
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 2:34:17
     * </PRE>
     *
     * @return void
     * @param menuInfo 선택 메뉴 정보
     */
    public static void setSessionSelectMenuInfo(SelectedMenuVO menuInfo) {
        HttpSession session = getCreateSession();

        if (menuInfo != null) {
            session.setAttribute("selectedMenu", menuInfo);
        }
    }


    /**
     * <PRE>
     * 1. MethodName: getSessionLng
     * 2. ClassName : CommonUtil
     * 3. Comment   : 세션 정보의 언어코드 리턴
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 1. 4. 오전 10:08:01
     * </PRE>
     *
     * @return String 언어코드
     */
    public static String getSessionLng() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            String lng = (String) request.getSession().getAttribute("sessionLanguage");
            if (StringUtils.isEmpty(lng) == false) {
                return lng;
            } else {
                return "ko";
            }
        } else {
            return "ko";
        }

    }

    /**
     *
     * <PRE>
     * 1. MethodName: getBizNo
     * 2. ClassName : CommonUtil
     * 3. Comment   : 사업자번호 형식 리턴
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2018. 1. 26. 오전 9:48:02
     * </PRE>
     *
     * @return String
     * @param bizNo
     * @return
     */
    public static String getBizNo(String bizNo) {

        if (bizNo == null || bizNo.length() != 10) {
            return bizNo;
        }

        bizNo = bizNo.substring(0, 3) + "-" + bizNo.substring(3, 5) + "-" + bizNo.substring(5, 10);

        return bizNo;
    }

    /**
     *
     * <PRE>
     * 1. MethodName: getTelNoHyphen
     * 2. ClassName : CommonUtil
     * 3. Comment   : 전화번호 '-' 추가
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2018. 1. 26. 오전 10:58:18
     * </PRE>
     *
     * @return String
     * @param num
     * @param mask
     * @return
     */
    public static String getTelNoHyphen(String num, String mask) {

        String formatNum = "";
        if (num == null || num.equals(""))
            return formatNum;
        num = num.replaceAll("-", "");

        if (num.length() == 11) {
            if (mask.equals("Y")) {
                formatNum = num.replaceAll("(\\S{3})(\\S{3,4})(\\S{4})", "$1-****-$3");
            } else {
                formatNum = num.replaceAll("(\\S{3})(\\S{3,4})(\\S{4})", "$1-$2-$3");
            }
        } else if (num.length() == 8) {
            formatNum = num.replaceAll("(\\S{4})(\\S{4})", "$1-$2");
        } else {
            if (num.indexOf("02") == 0) {
                if (mask.equals("Y")) {
                    formatNum = num.replaceAll("(\\S{2})(\\S{3,4})(\\S{4})", "$1-****-$3");
                } else {
                    formatNum = num.replaceAll("(\\S{2})(\\S{3,4})(\\S{4})", "$1-$2-$3");
                }
            } else {
                if (mask.equals("Y")) {
                    formatNum = num.replaceAll("(\\S{3})(\\S{3,4})(\\S{4})", "$1-****-$3");
                } else {
                    formatNum = num.replaceAll("(\\S{3})(\\S{3,4})(\\S{4})", "$1-$2-$3");
                }
            }
        }
        return formatNum;
    }

    /**
     *
     * <PRE>
     * 1. MethodName: getNumFormat
     * 2. ClassName : CommonUtil
     * 3. Comment   : 숫자 1000단위 컴마
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2018. 1. 26. 오전 10:58:35
     * </PRE>
     *
     * @return String
     * @param num
     * @return
     */
    public static String getNumFormat(int num) {

        String numStr = String.format("%,d", num);
        return numStr;
    }

    /**
     *
     * <PRE>
     * 1. MethodName: convertObjectToMap
     * 2. ClassName : CommonUtil
     * 3. Comment   : VO 를 Map 로 변환
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2018. 1. 26. 오후 1:52:17
     * </PRE>
     *
     * @return Map
     * @param obj
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertObjectToMap(Object obj) {
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                map.put(fields[i].getName(), fields[i].get(obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     *
     * <PRE>
     * 1. MethodName: convertMapToObject
     * 2. ClassName : CommonUtil
     * 3. Comment   : Map 를 VO로 변환
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2018. 1. 26. 오후 1:52:34
     * </PRE>
     *
     * @return Object
     * @param map
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMapToObject(Map<String, Object> map, Object obj) {
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;
        Iterator itr = map.keySet().iterator();

        while (itr.hasNext()) {
            keyAttribute = (String) itr.next();
            methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methodString.equals(methods[i].getName())) {
                    try {
                        methods[i].invoke(obj, map.get(keyAttribute));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }

    /**
     * <PRE>
     * 1. MethodName: subStringBytes
     * 2. ClassName : CommonUtil
     * 3. Comment   : 문자열을 지정된 사이즈 만큼 자르기
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 5. 8. 오후 3:17:11
     * </PRE>
     *
     * @return String
     * @param str
     * @param byteLength
     * @return
     */
    public static String subStringBytes(String str, int byteLength) {
        // String 을 byte 길이 만큼 자르기.
        int retLength = 0;
        int tempSize = 0;
        int asc;
        if (str == null || "".equals(str) || "null".equals(str)) {
            str = "";
        }

        int length = str.length();

        for (int i = 1; i <= length; i++) {
            asc = str.charAt(i - 1);
            if (asc > 127) {
                if (byteLength >= tempSize + 2) {
                    tempSize += 2;
                    retLength++;
                } else {
                    return str.substring(0, retLength);
                }
            } else {
                if (byteLength > tempSize) {
                    tempSize++;
                    retLength++;
                }
            }
        }

        return str.substring(0, retLength);
    }

}
