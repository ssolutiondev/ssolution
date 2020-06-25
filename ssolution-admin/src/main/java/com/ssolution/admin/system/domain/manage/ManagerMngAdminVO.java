package com.ssolution.admin.system.domain.manage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: ManagerMngAdminVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 사용자관리 관리자용 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:37:41
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
public class ManagerMngAdminVO extends PagingValueVO {
    private String soId; // 사업ID
    private String soNm; // 사업명
    private String userId; // 사용자ID
    private String userNm; // 사용자명
    private String pswd; // 패스워드
    private String userGrpId; // 사용자그룹ID
    private String userGrpNm; // 사용자그룹명
    private String orgId; // 조직ID
    private String orgNm; // 조직명
    private String uppOrgId; // 상위 조직ID
    private String uppOrgNm; // 상위 조직명
    private String userTp; // 사용자 유형
    private String empNo; // 사원번호
    private String pstn; // 직급
    private String rspofc; // 직책
    private String telNo; // 전화번호
    private String cellPhnNo; // 휴대폰번호
    private String email; // 이메일
    private String permIpBand; // 허용IP대역
    private String pswdChgPlnDt; // 패스워드 변경 예정일
    private int pswdChgCycl; // 패스워드 변경 주기
    private String useYn; // 사용여부
    private String lockYn; // 잠김여부
    private String pswd1; // 패스워드1
    private String pswd2; // 패스워드2
    private int loginFailCnt; // 로그인 실패 수
    private Date lastLoginDate1; // 최종로그인일시1
    private Date lastLoginDate2; // 최종로그인일시2
    private String inptMenuId; // 입력 메뉴ID
    private String regrId; // 등록자ID
    private String regrNm; // 등록자명
    private Date regDate; // 등록일시
    private String chgrId; // 수정자ID
    private String chgrNm; // 수정자명
    private Date chgDate; // 수정일시
    private String today; // 일자(YYYYMMDD)
    private String result;
    private String rspofcNm; // 직책명

}
