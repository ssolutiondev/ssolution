package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <PRE>
 * 1. FileName	: FastMenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: 자동완성용 메뉴정보
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:08:35
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FastMenuVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8559347445634431709L;

    /**
     * 메뉴명
     */
    private String name;

    /**
     * 메뉴ID
     */
    private String menuId;

    /**
     * path
     */
    private String viewPath;

    /**
     * 최상위메뉴번호
     */
    private String topMenuId;

    /**
     * 최상위메뉴명
     */
    private String topMenuNm;

    /**
     * 서브메뉴번호
     */
    private String subMenuId;

    /**
     * 서브메뉴명
     */
    private String subMenuNm;

}
