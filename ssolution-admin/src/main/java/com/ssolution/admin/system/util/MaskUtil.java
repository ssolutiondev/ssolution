package com.ssolution.admin.system.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <PRE>
 * 1. FileName	: MaskUtil.java
 * 2. Package	: com.ssolution.admin.system.util
 * 3. Comment	: 마스크 처리 유틸
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:56:32
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class MaskUtil {

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(MaskUtil.class);

    /**
     * <PRE>
     * 1. MethodName: converToMask
     * 2. ClassName : MaskUtil
     * 3. Comment   : 마스킹 처리
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 1. 11. 오전 11:38:05
     * </PRE>
     * 
     * @return String 마스킹 처리 데이터
     * @param str      대상문자
     * @param strtIndx 시작인덱스
     * @param endIndx  종료인덱스
     * @param chgChar  마스킹처리문자
     * @throws Exception
     */
    public static String converToMask(String str, Integer strtIndx, Integer endIndx, String chgChar) throws Exception {
        int total = str.length();
        int startIndex = strtIndx.intValue();
        int endIndex = endIndx.intValue();
        if (endIndex == 9999) {
            endIndex = str.length();
        }
        int masklen = endIndex - startIndex;

        if (masklen == 0)
            return str;

        StringBuffer maskedbuf = new StringBuffer(str.substring(0, startIndex));
        for (int i = 0; i < masklen; i++) {
            int index = i + startIndex;
            if (" ".equals(str.substring(index, index + 1))) {
                maskedbuf.append(" ");
            } else {
                maskedbuf.append(chgChar);
            }

        }
        maskedbuf.append(str.substring(startIndex + masklen, total));
        return maskedbuf.toString();

    }

    public static String converToMaskMulti(String str,
                                           Integer strtIndx,
                                           Integer endIndx,
                                           Integer addStrtIndx,
                                           Integer addEndIndx,
                                           String chgChar) throws Exception {
        int total = str.length();
        int startIndex = strtIndx.intValue();
        int endIndex = endIndx.intValue();
        int addStartIndex = addStrtIndx.intValue();
        int addEndIndex = addEndIndx.intValue();
        if (addEndIndex == 9999) {
            addEndIndex = str.length();
        }
        int firstMasklen = endIndex - startIndex;
        if (firstMasklen == 0)
            return str;

        int secondMasklen = addEndIndex - addStartIndex;

        StringBuffer firstMaskedbuf = new StringBuffer(str.substring(0, startIndex));
        for (int i = 0; i < firstMasklen; i++) {
            int index = i + startIndex;
            if (" ".equals(str.substring(index, index + 1))) {
                firstMaskedbuf.append(" ");
            } else {
                firstMaskedbuf.append(chgChar);
            }
        }
        if (secondMasklen == 0) {
            firstMaskedbuf.append(str.substring(startIndex + firstMasklen, total));
            return firstMaskedbuf.toString();
        }

        StringBuffer secondMaskedbuf = new StringBuffer(str.substring(endIndex, addStartIndex));
        for (int i = 0; i < secondMasklen; i++) {
            int index = i + addStartIndex;
            if (" ".equals(str.substring(index, index + 1))) {
                secondMaskedbuf.append(" ");
            } else {
                secondMaskedbuf.append(chgChar);
            }
        }

        secondMaskedbuf.append(str.substring(addStartIndex + secondMasklen, total));
        firstMaskedbuf.append(secondMaskedbuf.toString());
        return firstMaskedbuf.toString();

    }

    public static String convert2CamelCase(String underScore) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }
}
