package com.ssolution.admin.system.domain.manage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: MenuMngVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 메뉴관리 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:37:51
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
public class MenuMngVO extends PagingValueVO {

    /**
     * 메뉴ID
     */
    private String menuId;

    /**
     * 메뉴명
     */
    private String menuNm;

    /**
     * 상위메뉴ID
     */
    private String uppMenuId;

    /**
     * 상위메뉴명
     */
    private String uppMenuNm;

    /**
     * URL 코드
     */
    private String urlCd;

    /**
     * URL명(프로그램명)
     */
    private String urlNm;

    /**
     * URL PATH
     */
    private String urlPath;

    /**
     * 레벨
     */
    private String lvl;

    /**
     * 정렬 순서
     */
    private String sortNo;

    /**
     * 메뉴 설명
     */
    private String menuDesc;

    /**
     * 사용여부
     */
    private String useYn;

    /**
     * 입력 메뉴 ID
     */
    private String inptMenuId;

    /**
     * 메뉴ID(조건)
     */
    private String condUppMenuId;

    /**
     * 언어
     */
    private String langTp;

    /**
     * 등록자ID
     */
    private String regrId;

    /**
     * 등록자명
     */
    private String regrNm;

    /**
     * 등록일시
     */
    private Date regDate;

    /**
     * 수정자ID
     */
    private String chgrId;

    /**
     * 수정자명
     */
    private String chgrNm;

    /**
     * 수정일시
     */
    private Date chgDate;



}