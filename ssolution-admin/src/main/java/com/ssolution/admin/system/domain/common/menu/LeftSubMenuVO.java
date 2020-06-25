package com.ssolution.admin.system.domain.common.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <PRE>
 * 1. FileName	: LeftSubMenuVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: Left 하위 메뉴
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:08:51
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
public class LeftSubMenuVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4829731718211382425L;

    /** 메뉴 ID */
    private String menuId;

    /** 메뉴명 */
    private String menuNm;

    /** 메뉴 레벨 */
    private String lvl;

    /** 기본 표시 URL */
    private String viewPath;

    /** 표시순서 */
    private String sortNo;

    /** 읽기 권한 */
    private String authInq;
    /** 쓰기 권한 */
    private String authReg;
    /** 출력 권한 */
    private String authPrt;
    /** 수정 권한 */
    private String authChg;
    /** 삭제 권한 */
    private String authDel;



}
