package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * <PRE>
 * 1. FileName	: LeftTopMenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: Left 상위 메뉴
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:10:26
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
public class LeftTopMenuVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7925398158401727056L;

    /** 메뉴 ID */
    private String menuId;

    /** 메뉴명 */
    private String menuNm;

    /** 메뉴 레벨 */
    private String lvl;

    /** 표시순서 */
    private String sortNo;

    /** 최상위 메뉴명 */
    private String topMenuNm;

    /** 하위 메뉴 리스트 **/
    private List<LeftSubMenuVO> leftSubMenu;



}
