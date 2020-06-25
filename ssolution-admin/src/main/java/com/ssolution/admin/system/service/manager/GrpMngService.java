package com.ssolution.admin.system.service.manager;

import java.util.List;

import com.ssolution.admin.system.domain.manage.GrpMngVO;


public interface GrpMngService {

    /**
     * <PRE>
     * 1. MethodName: getGrpList
     * 2. ClassName : GrpMngService
     * 3. Comment   : 권한 그룹 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:47:26
     * </PRE>
     * 
     * @return List<GrpMngVO> 권한 그룹 리스트
     * @param grpMng 조회 조건
     */
    List<GrpMngVO> getGrpList(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: checkGrpId
     * 2. ClassName : GrpMngService
     * 3. Comment   : 권한 그룹 중복 체크
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:48:03
     * </PRE>
     * 
     * @return int 중복수
     * @param grpMng 조회 조건
     */
    int checkGrpId(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: insertGrp
     * 2. ClassName : GrpMngService
     * 3. Comment   : 권한 그룹 등록
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:48:52
     * </PRE>
     * 
     * @return int 등록수
     * @param grpMng 권한그룹정보
     */
    int insertGrp(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: updateGrp
     * 2. ClassName : GrpMngService
     * 3. Comment   : 권한 그룹 수정
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:49:47
     * </PRE>
     * 
     * @return int 수정수
     * @param grpMng 권한그룹정보
     */
    int updateGrp(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: deleteGrp
     * 2. ClassName : GrpMngService
     * 3. Comment   : 권한 그룹 삭제
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:50:17
     * </PRE>
     * 
     * @return int 삭제수
     * @param grpMng 권한그룹정보
     */
    int deleteGrp(GrpMngVO grpMng);

}