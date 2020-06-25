package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <PRE>
 * 1. FileName	: MenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: 메뉴 정보 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:10:36
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
public class MenuVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5658387248521088308L;

    /** 메뉴번호. */
    private String menuId;

    /** 메뉴명. */
    private String menuNm;

    /** 상위메뉴번호. */
    private String uppMenuId;

    /** 접근 url. */
    private String viewPath;

    /** LEVEL */
    private Integer lvl;

    /** 출력 순서. */
    private Integer sortNo;

    /** 설명. */
    private String menuDesc;

    /** 국가코드. */
    private String countryCode;

    /** 언어코드. */
    private String languageCode;

}
