package com.ssolution.admin.system.domain.common.popup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: OrgSearchPopupVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.popup
 * 3. Comment	: 조직 검색 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:29:02
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
public class OrgSearchPopupVO extends PagingValueVO {

    /**
     * 하위조직 표시 여부
     */
    private String subOrgDispYn;

    /**
     * 권한조직ID
     */
    private String authOrgId;

    /**
     * 권한조직명
     */
    private String authOrgNm;

    /**
     * 조직ID(세션유저)
     */
    private String userOrgId;

    /**
     * 리턴ID(For 조직ID)
     */
    private String returnIdForOrgId;

    /**
     * 리턴ID(For 조직명)
     */
    private String returnIdForOrgNm;

    /**
     * 콜백함수명
     */
    private String callbackFuncNm;

    /**
     * 로우번호
     */
    private int rowNo;

    /**
     * 레벨
     */
    private int lvl;

    /**
     * 사업ID
     */
    private String soId;

    /**
     * 조직ID
     */
    private String orgId;

    /**
     * 조직명
     */
    private String orgNm;

    /**
     * 조직유형코드
     */
    private String tpCd;

    /**
     * 조직유형명
     */
    private String tpNm;

    /**
     * 조직유형상세코드
     */
    private String tpDtlCd;

    /**
     * 조직유형상세명
     */
    private String tpDtlNm;

    /**
     * 상위조직ID
     */
    private String uppOrgId;

    /**
     * 상위조직명
     */
    private String uppOrgNm;

    /**
     * 조직레벨코드
     */
    private String orgLvlCd;

    /**
     * 조직레벨명
     */
    private String orgLvlNm;

    /**
     * 조직권한레벨코드
     */
    private String orgAuthLvlCd;

    /**
     * 조직권한레벨명
     */
    private String orgAuthLvlNm;

    /**
     * 조직권한조직ID
     */
    private String orgAuthOrgId;

    /**
     * 조직권한조직명
     */
    private String orgAuthOrgNm;

    /**
     * 조직상태코드
     */
    private String orgStatCd;

    /**
     * 조직상태명
     */
    private String orgStatNm;

    /**
     * 조직상태변경일
     */
    private String orgStatChgDt;

    /**
     * 적용시작일
     */
    private String aplyStrtDt;

    /**
     * 적용종료일
     */
    private String aplyEndDt;

    /**
     * 전화번호
     */
    private String telNo;

    /**
     * 비고
     */
    private String rmrk;

    /**
     * 입력 메뉴ID
     */
    private String inptMenuId;

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
     * 변경자ID
     */
    private String chgrId;

    /**
     * 변경자명
     */
    private String chgrNm;

    /**
     * 변경일시
     */
    private Date chgDate;


}
