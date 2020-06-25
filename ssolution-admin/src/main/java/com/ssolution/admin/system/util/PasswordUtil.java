package com.ssolution.admin.system.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <PRE>
 * 1. FileName	: PasswordUtil.java
 * 2. Package	: com.ssolution.admin.system.util
 * 3. Comment	: 패스워드 규칙 체크 유틸
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:56:44
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class PasswordUtil {

    /**
     * 패스워드 규칙 체크
     * - 대소문자, 숫자, 특수문자 조합의 8자리 이상
     * @param password 체크 패스워드
     * @return true 사용할 수 있는 패스워드, false 사용할 수 없는 패스워드
     */
    public static boolean checkPassword(String password) {

        // 빈 문자는 사용 할 수 없음
        if (StringUtils.isEmpty(password)) return false;

        // 비밀번호 유효성 검사식1 : 숫자, 특수문자가 포함되어야 한다.
        String regExpSymbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";

        // 비밀번호 유효성 검사식2 : 영문자 대소문자가 적어도 하나씩은 포함되어야 한다.
        String regExpAlpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";

        // 정규표현식 컴파일 한다.
        Pattern patternSymbol = Pattern.compile(regExpSymbol);
        Pattern patternAlpha = Pattern.compile(regExpAlpha);

        // 문자 매칭 한다.
        Matcher matcherSymbol = patternSymbol.matcher(password);
        Matcher matcherAlpha = patternAlpha.matcher(password);

        if (matcherSymbol.find() && matcherAlpha.find() && password.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }
}
