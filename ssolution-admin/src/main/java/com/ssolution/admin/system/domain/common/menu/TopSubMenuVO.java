package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <PRE>
 * 1. FileName	: TopSubMenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: 상위 서브 메뉴
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:28:08
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
public class TopSubMenuVO implements Serializable {
    private static final long serialVersionUID = -6676050901542939674L;

    /** 메뉴 NO */
    private String menuId;

    /** 메뉴명 */
    private String menuNm;

    /** 메뉴 레벨 */
    private String lvl;

    /** 기본 표시 URL */
    private String viewPath;

    /** 표시순서 */
    private String sortNo;

    /** 선택 메뉴 NO */
    private String selectMenuId;

    /** 선택 메뉴명 */
    private String selectMenuNm;

    /** 최상위 메뉴 NO */
    private String topMenuId;

    /** 최상위 메뉴명 */
    private String topMenuNm;


}
