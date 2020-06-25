package com.ssolution.core.exception;

import com.ssolution.admin.system.util.MessageUtil;

/**
 * <PRE>
 * 1. FileName	: ServiceException.java
 * 2. Package	: com.ssolution.core.exception
 * 3. Comment	: Service Exception
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:57:22
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 4627685429817530816L;

    /**
     * 입력변수.
     */
    private Object[] args = null;

    public Object[] getArgs() {
        return args;
    }

    /**
     * 프로퍼티 파일의 키를 지정한다.
     *
     * @param propKey String
     */
    public ServiceException(String propKey) {
        super(MessageUtil.getMessage(propKey));
    }

    /**
     * 프로퍼티 파일의 키, arg를 지정한다.
     *
     * @param propKey String
     * @param args    Object[]
     */
    public ServiceException(String propKey, Object[] args) {
        super(MessageUtil.getMessage(propKey, args));
        this.args = args;
    }

    /**
     * 프로퍼티 파일의 키와 내포할 예외를 지정한다.
     *
     * 프로퍼티 파일의 템플릿 문자열 처리시 첫번째(그리고 단 하나의) arg는 t.getMessage()가 된다.
     *
     * @param propKey String
     * @param t       Throwable
     */
    public ServiceException(String propKey, Throwable t) {
        super(MessageUtil.getMessage(propKey), t);
        this.args = new Object[] { t.getMessage() };
    }

    /**
     * 프로퍼티 파일의 키, arg 배열, 내포할 예외를 지정한다.
     *
     * 프로퍼티 파일의 템플릿 문자열 처리시 Object[] args가 순서대로 들어가는데 더하여 마지막 arg로 t.getMessage()를 추가해준다.
     * 
     * @param propKey String
     * @param args    Object[]
     * @param t       Throwable
     */
    public ServiceException(String propKey, Object[] args, Throwable t) {
        super(MessageUtil.getMessage(propKey), t);

        this.args = new Object[args.length + 1];
        System.arraycopy(args, 0, this.args, 0, args.length);
        this.args[args.length] = t.getMessage();
    }

}
