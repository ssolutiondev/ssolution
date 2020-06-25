package com.ssolution.admin.system.service.manager;

import java.util.List;
import java.util.Map;

import com.ssolution.admin.system.domain.manage.AuthMngVO;
import com.ssolution.admin.system.domain.manage.GrpMngVO;

/**
 * <PRE>
 * 1. FileName	: AuthMngService.java
 * 2. Package	: com.ssolution.admin.system.service.manager
 * 3. Comment	: 그룹별권한관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:53:55
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface AuthMngService {

    /**
     * <PRE>
     * 1. MethodName: getAuthGroupList
     * 2. ClassName : AuthMngService
     * 3. Comment   : 권한 그룹 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 9:29:41
     * </PRE>
     * 
     * @return List<GrpMngVO> 그룹 권한 리스트
     * @param grpMng 그룹권한VO
     */
    List<GrpMngVO> getAuthGroupList(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: getAuthList
     * 2. ClassName : AuthMngService
     * 3. Comment   : 권한정보 조회
     * 4. 작성자    : Kim Hye Won
     * 5. 작성일    : 2016. 6. 27. 오전 9:17:15
     * </PRE>
     * 
     * @return List<Map<String,Object>> 사용자그룹아이디에 해당하는 권한정보
     * @param authGrpId  사용자그룹ID
     * @param condAsgnYn 할당메뉴만 조회
     * @param lng        언어코드
     */
    Map<String, Object> getAuthList(String authGrpId, String condAsgnYn, String lng);

    /**
     * <PRE>
     * 1. MethodName: updateAuth
     * 2. ClassName : AuthMngService
     * 3. Comment   : 권한정보 수정
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 24. 오전 10:12:15
     * </PRE>
     * 
     * @return void
     * @param authMng 권한관리VO
     */
    void updateAuth(AuthMngVO authMng);

}