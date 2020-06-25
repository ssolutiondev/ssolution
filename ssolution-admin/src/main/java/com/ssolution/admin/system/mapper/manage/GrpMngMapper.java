package com.ssolution.admin.system.mapper.manage;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.manage.GrpMngVO;

import java.util.List;


/**
 * <PRE>
 * 1. FileName	: GrpMngMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.manage
 * 3. Comment	: 그룹 관리 Mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:02:59
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface GrpMngMapper {

    /**
     * <PRE>
     * 1. MethodName: getGrpList
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:09:27
     * </PRE>
     * 
     * @return List<GrpMngVO> 그룹리스트
     * @param grpMng 조회조건
     */
    List<GrpMngVO> getGrpList(@Param(value = "grpMng") GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: checkGrpId
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹 ID 중복 체크
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:10:21
     * </PRE>
     * 
     * @return int 권한그룹수
     * @param grpMng 조회조건
     */
    int checkGrpId(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: insertGrp
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹 등록
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:11:00
     * </PRE>
     * 
     * @return int 등록수
     * @param grpMng 권한그룹정보
     */
    int insertGrp(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: updateGrp
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹 수정
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:11:50
     * </PRE>
     * 
     * @return int 수정수
     * @param grpMng 권한그룹정보
     */
    int updateGrp(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: deleteGrpDtl
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹의 하위 권한 삭제
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:12:32
     * </PRE>
     * 
     * @return void
     * @param grpMng 삭제 대상 권한 그룹 정보
     */
    void deleteGrpDtl(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: deleteGrpUser
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹의 하위 사용자 삭제
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:13:41
     * </PRE>
     * 
     * @return void
     * @param grpMng 삭제 대상 권한 그룹 정보
     */
    void deleteGrpUser(GrpMngVO grpMng);

    /**
     * <PRE>
     * 1. MethodName: deleteGrp
     * 2. ClassName : GrpMngMapper
     * 3. Comment   : 권한 그룹 삭제
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 18. 오후 8:14:16
     * </PRE>
     * 
     * @return int 삭제수
     * @param grpMng 삭제 권한 그룹
     */
    int deleteGrp(GrpMngVO grpMng);

}