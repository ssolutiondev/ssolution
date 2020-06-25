package com.ssolution.admin.system.util;

import java.util.regex.Pattern;

public class FormatterUtil {
    /**
     * <PRE>
     * 1. MethodName: formattTelNo
     * 2. ClassName : ExcelXlsxView
     * 3. Comment   : 전화번호 포맷
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2018. 1. 3. 오전 11:22:52
     * </PRE>
     *
     * @return String 포맷팅한 전화번호
     * @param paramTelno 전화번호
     */
    public static String formattTelNo(String paramTelno) {
        if (paramTelno == null || paramTelno.trim().length() == 0) {
            return paramTelno;
        }

        String regEx = "^(01[016789\\*]{1}|02|0[3-9\\*]{1}[0-9\\*]{1})?-?([0-9\\*]{3,4})-?([0-9\\*]{4}$)";
        if (!Pattern.matches(regEx, paramTelno)) {
            return paramTelno;
        }

        String formattedTelNo = "";
        if (paramTelno.length() < 9) {
            formattedTelNo = paramTelno.replaceAll(regEx, "$2-$3");

        } else {
            formattedTelNo = paramTelno.replaceAll(regEx, "$1-$2-$3");
        }

        return formattedTelNo;
    }

    /**
     * <PRE>
     * 1. MethodName: formattBizNo
     * 2. ClassName : ExcelXlsxView
     * 3. Comment   : 사업자번호 포맷
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2018. 1. 3. 오전 11:23:19
     * </PRE>
     *
     * @return String 포맷팅한 사업자번호
     * @param paramTelno 사업자번호
     */
    public static String formattBizNo(String paramBizno) {
        if (paramBizno == null || paramBizno.trim().length() == 0) {
            return paramBizno;
        }

        String regEx = "^[0-9\\*]{10}$";
        if (!Pattern.matches(regEx, paramBizno)) {
            return paramBizno;
        }

        return paramBizno.substring(0, 3) + '-' + paramBizno.substring(3, 5) + '-' + paramBizno.substring(5);
    }
}
