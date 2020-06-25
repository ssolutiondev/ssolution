package com.ssolution.admin.system.service.manager;

import java.util.List;

import com.ssolution.admin.system.domain.manage.GrpReqMngVO;


/**
 * <PRE>
 * 1. FileName	: GrpReqMngService.java
 * 2. Package	: com.ssolution.admin.system.service.manager
 * 3. Comment	: 사용자 그룹 등록 관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:54:09
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface GrpReqMngService {

    /**
     * 
     * <PRE>
     * 1. MethodName: getGrpReqList
     * 2. ClassName : GrpReqMngService
     * 3. Comment   : 사용자 요청 리스트
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 19. 오후 2:42:42
     * </PRE>
     * 
     * @return List<GrpReqMngVO> 사용자 그룹관리 리스트
     * @param grpReqMngVO 사용자 그룹관리 VO
     */
    List<GrpReqMngVO> getGrpReqList(GrpReqMngVO grpReqMngVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertGrpReqInfo
     * 2. ClassName : GrpReqMngService
     * 3. Comment   : 사용자 권한 요청 등록
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 20. 오전 11:28:00
     * </PRE>
     * 
     * @return int 사용자 권한 요청 카운트
     * @param grpReqMngVO 사용자 그룹 관리 VO
     */
    int insertGrpReqInfo(GrpReqMngVO grpReqMngVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: updateGrpReqInfo
     * 2. ClassName : GrpReqMngService
     * 3. Comment   : 사용자 권한 요청 수정
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 20. 오후 3:14:05
     * </PRE>
     * 
     * @return int 사용자 권한 요청 카운트
     * @param grpReqMngVO 사용자 권한 요청 VO
     */
    int updateGrpReqInfo(GrpReqMngVO grpReqMngVO);

}
