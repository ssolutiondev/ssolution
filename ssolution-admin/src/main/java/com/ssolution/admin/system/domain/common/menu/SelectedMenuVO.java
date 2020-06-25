package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * <PRE>
 * 1. FileName	: SelectedMenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: 선택 메뉴 정보
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:16:23
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
public class SelectedMenuVO implements Serializable {
    /** Serializable serialVersionUID. */
    private static final long serialVersionUID = 2269785928007434720L;

    /** 메뉴 번호. */
    private String menuId;

    /** 선택 메뉴 번호. */
    private String selectMenuId;

    /** 선택 메뉴 명 */
    private String selectMenuNm;

    /** 최상위 메뉴. */
    private String topMenuId;

    /** 최상위 메뉴 명 */
    private String topMenuNm;

}
