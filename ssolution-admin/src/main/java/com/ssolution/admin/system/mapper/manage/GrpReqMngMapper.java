package com.ssolution.admin.system.mapper.manage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.manage.GrpReqMngVO;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: GrpReqMngMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.manage
 * 3. Comment	: 사용자 그룹 등록 관리 Mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:03:08
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface GrpReqMngMapper {

    /**
     * 
     * <PRE>
     * 1. MethodName: getGrpReqList
     * 2. ClassName : GrpReqMngMapper
     * 3. Comment   : 사용자 그룹 리스트 
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 19. 오후 2:45:26
     * </PRE>
     * 
     * @return List<GrpReqMngVO> 사용자 그룹관리 리스트
     * @param grpReqMngVO 사용자 그룹 VO
     */
    List<GrpReqMngVO> getGrpReqList(@Param(value = "grpReqMngVO") GrpReqMngVO grpReqMngVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertGrpReqInfo
     * 2. ClassName : GrpReqMngMapper
     * 3. Comment   : 사용자 그룹 요청 등록
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 20. 오후 1:28:34
     * </PRE>
     * 
     * @return List<GrpReqMngVO> 사용자 그룹 요청 리스트
     * @param grpReqMngVO 사용자 그룹 요청 VO
     */
    int insertGrpReqInfo(@Param(value = "grpReqMngVO") GrpReqMngVO grpReqMngVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: updateGrpReqInfo
     * 2. ClassName : GrpReqMngMapper
     * 3. Comment   : 사용자 그룹 요청 수정
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 20. 오후 3:23:54
     * </PRE>
     * 
     * @return int 사용자 그룹 요청 수정 카운트
     * @param grpReqMngVO 사용자 그룹 요청 VO
     */
    int updateGrpReqInfo(@Param(value = "grpReqMngVO") GrpReqMngVO grpReqMngVO);

}
