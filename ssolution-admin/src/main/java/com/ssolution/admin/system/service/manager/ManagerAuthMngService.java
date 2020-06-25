package com.ssolution.admin.system.service.manager;

import java.util.List;

import com.ssolution.admin.system.domain.manage.ManagerAuthMngVO;


/**
 * <PRE>
 * 1. FileName	: ManagerAuthMngService.java
 * 2. Package	: com.ssolution.admin.system.service.manager
 * 3. Comment	: 그룹별 사용자 관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:54:34
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface ManagerAuthMngService {

    /**
     * <PRE>
     * 1. MethodName: getManagerList
     * 2. ClassName : ManagerAuthMngService
     * 3. Comment   : 사용자조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 19. 오후 5:13:51
     * </PRE>
     * 
     * @return List<ManagerAuthMngVO>
     * @param managerAuth
     * @return
     */
    List<ManagerAuthMngVO> getManagerList(ManagerAuthMngVO managerAuth);

    /**
     * <PRE>
     * 1. MethodName: getManagerAuthList
     * 2. ClassName : ManagerAuthMngService
     * 3. Comment   : 그룹별 사용자 리스트
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 19. 오후 5:14:08
     * </PRE>
     * 
     * @return List<ManagerAuthMngVO>
     * @param managerAuth
     * @return
     */
    List<ManagerAuthMngVO> getManagerAuthList(ManagerAuthMngVO managerAuth);

    /**
     * <PRE>
     * 1. MethodName: updateManagerAuth
     * 2. ClassName : ManagerAuthMngService
     * 3. Comment   : 그룹별 사용자 정보 수정
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 19. 오후 5:14:33
     * </PRE>
     * 
     * @return void
     * @param managerAuth
     */
    void updateManagerAuth(ManagerAuthMngVO managerAuth);

}