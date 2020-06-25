package com.ssolution.admin.system.service.common.code;

import java.util.List;
import java.util.Map;

import com.ssolution.admin.system.domain.common.code.CommCdDtlVO;
import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;

public interface CommCdMngService {

    /**
     *
     * <PRE>
     * 1. MethodName: getCdGrpTreeList
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 공통코드 그룹 리스트 트리 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 9:03:20
     * </PRE>
     * 
     * @return Map<String,Object>
     * @param lng
     * @return
     */
    public Map<String, Object> getCdGrpTreeList(String lng);

    /**
     *
     * <PRE>
     * 1. MethodName: getCommCdList
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 공통코드 상세 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 9:36:41
     * </PRE>
     * 
     * @return List<CommCdDtlVO>
     * @param condGroupId
     * @param lng
     * @return
     */
    List<CommCdDtlVO> getCommCdList(String condGroupId, String lng);

    /**
     *
     * <PRE>
     * 1. MethodName: getLngList
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 빈 언어 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:13:53
     * </PRE>
     * 
     * @return List<Map<String,Object>>
     * @return
     */
    List<Map<String, Object>> getLngList();

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdDtlInfo
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 공통코드 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:25:02
     * </PRE>
     * 
     * @return void
     * @param commCdDtlVO
     */
    void insertCdDtlInfo(CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdDtl
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 공통코드 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 3:32:14
     * </PRE>
     * 
     * @return void
     * @param commCdDtlVO
     */
    void updateCdDtl(CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdDtl
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 공통코드 상세 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 4:14:49
     * </PRE>
     * 
     * @return void
     * @param commCdDtlVO
     */
    void deleteCdDtl(CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdGrp
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 코드그룹 정보 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:22:34
     * </PRE>
     * 
     * @return void
     * @param commCdGrpVO
     */
    void insertCdGrp(CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdGrp
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 코드그룹 정보 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:35:50
     * </PRE>
     * 
     * @return void
     * @param commCdGrpVO
     */
    void updateCdGrp(CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getCdGrpInfo
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 코드그룹 정보 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:19:25
     * </PRE>
     * 
     * @return CommCdGrpVO
     * @param commCdGrpVO
     * @return
     */
    CommCdGrpVO getCdGrpInfo(CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdGrp
     * 2. ClassName : CommCdMngService
     * 3. Comment   : 공통코드 그룹 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:42:03
     * </PRE>
     * 
     * @return void
     * @param commCdGrpVO
     */
    void deleteCdGrp(CommCdGrpVO commCdGrpVO);
}
