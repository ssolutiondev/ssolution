package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <PRE>
 * 1. FileName	: TopMenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: 최상위 메뉴
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:27:53
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
public class TopMenuVO implements Serializable {

    private static final long serialVersionUID = 5763294192476000409L;

    /** 메뉴 NO */
    private String menuId;

    /** 메뉴명 */
    private String menuNm;

    /** 메뉴 레벨 */
    private String lvl;

    /** 표시순서 */
    private String sortNo;

    /** 하위 메뉴 리스트 **/
    private List<TopSubMenuVO> subMenu;



}
