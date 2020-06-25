package com.ssolution.admin.system.domain.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



@NoArgsConstructor
@Getter
@Setter
@ToString
public class SessionUserVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3232265411246637758L;

    /** 사업ID */
    private String soId;

    /** 사업명 */
    private String soNm;

    /** 조직ID */
    private String orgId;

    /** 조직명 */
    private String orgNm;

    /** 사용자 ID. */
    private String userId;

    /** 사용자 이름. */
    private String userNm;

    /** 사용자 유형 */
    private String userTp;

    /** 기본 그룹 ID. */
    private String authGrpId;

    /** 기본 그룹명. */
    private String authGrpNm;

    /** 메인뷰 **/
    private String dashboardUrl;

    /** IP대역. */
    private String permIpBand;

    /** Login Gateway IP. */
    private String loginGatewayIp;

    /** 로그인일시1 */
    private String lastLoginDate1;

    /** 로그인일시2 */
    private String lastLoginDate2;

    /** 로그인 실패 횟수. */
    private Integer loginFailCnt;

    /** 세션ID */
    private String sessionId;

    /** 직책코드 **/
    private String rspofc;

    /** 권한조직ID */
    private String authOrgId;

    /** 권한조직명 */
    private String authOrgNm;

    /** 권한그룹ID 리스트 */
    private List<String> authGrpIdList;

    /** 권한그룹ID 리스트 */
    private List<Map<String, Object>> soAuthList;

}
