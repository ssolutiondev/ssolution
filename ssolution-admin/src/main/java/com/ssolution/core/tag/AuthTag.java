package com.ssolution.core.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * <PRE>
 * 1. FileName	: AuthTag.java
 * 2. Package	: com.ssolution.core.tag
 * 3. Comment	: 권한 처리 태그 라이브러리
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:59:03
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class AuthTag extends BodyTagSupport {

    /** BodyTagSupport serialVersionUID. */
    private static final long serialVersionUID = 4866359152234315166L;

    /** 로그 출력. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 권한 코드. */
    private String auth;

    /**
     * Tag Lib 출력.
     * 
     * @return int
     * @throws JspException javax.servlet.jsp.JspException
     */
    public int doEndTag() throws JspException {

        final BodyContent bodyContent = getBodyContent();

        try {
            if ("Y".equals(auth)) {
                pageContext.getOut().print(bodyContent.getString());
            } else {
                pageContext.getOut().print("");
            }
        } catch (IOException ioe) {
            logger.error("error", ioe);
        }

        return SKIP_BODY;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

}
