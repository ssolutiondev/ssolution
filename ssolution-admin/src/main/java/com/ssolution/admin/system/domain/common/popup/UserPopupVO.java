package com.ssolution.admin.system.domain.common.popup;


import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <PRE>
 * 1. FileName	: UserPopupVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.popup
 * 3. Comment	: 사용자관리 관리자용 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:29:41
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
public class UserPopupVO extends PagingValueVO {

    /**
     * 사용자ID
     */
    private String userId;

    /**
     * 사용자명
     */
    private String userNm;

    /**
     * 사용여부
     */
    private String useYn;

    /**
     * 잠금여부
     */
    private String lockYn;

    /**
     * 사업ID
     */
    private String soId;

    /**
     * 하위조직 표시 여부
     */
    private String subOrgDispYn;

    /**
     * 검색조직유형(설정없음:전체조직유저, ALL:전체조직유저, AUTH:권한조직하위유저, MINE:사용자조직유저, THIS:특정조직)
     */
    private String srchOrgTp;

    /**
     * 검색조직ID(특정조직 검색시 설정 필요, 사용자 조직 유저 조회)
     */
    private String srchOrgId;

    /**
     * 검색조직명
     */
    private String srchOrgNm;

    /**
     * 사용자 조직ID
     */
    private String orgId;

    /**
     * 사용자 조직명
     */
    private String orgNm;

    /**
     * 사용자 상위 조직ID
     */
    private String uppOrgId;

    /**
     * 사용자 상위 조직명
     */
    private String uppOrgNm;

    /**
     * 권한조직ID
     */
    private String authOrgId;

    /**
     * 권한조직명
     */
    private String authOrgNm;

    /**
     * 리턴항목명(사용자ID)
     */
    private String returnIdForUserId;

    /**
     * 리턴항목명(사용자명)
     */
    private String returnIdForUserNm;

    /**
     * 리턴항목명(사용조직)
     */
    private String returnIdForOrgId;
    /**
     * 리턴항목명(사용조직명)
     */
    private String returnIdForOrgNm;
    /**
     * 콜백함수명
     */
    private String callbackFuncNm;

}
