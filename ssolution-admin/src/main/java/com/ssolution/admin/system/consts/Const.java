package com.ssolution.admin.system.consts;

/**
 * <PRE>
 * 1. FileName	: Const.java
 * 2. Package	: com.ssolution.admin.system.consts
 * 3. Comment	: 공통코드
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 9. 오후 4:02:58
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 9.	|	신규 작성
 * </PRE>
 */
public interface Const {

    public abstract static class WORK_TYPE {
        /** 검색. */
        public static final String SEARCH = "0";

        /** 입력. */
        public static final String INSERT = "1";

        /** 삭제. */
        public static final String DELETE = "2";

        /** 변경. */
        public static final String UPDATE = "3";

        /** 페이지 이동. */
        public static final String MOVE_PAGE = "4";

        public static final String NOT_MATCH = "5";
    }


}
