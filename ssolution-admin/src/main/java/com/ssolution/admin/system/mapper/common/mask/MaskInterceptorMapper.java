package com.ssolution.admin.system.mapper.common.mask;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.mask.MaskRuleVO;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName: MaskInterceptorMapper
 * 2. FileName : MaskInterceptorMapper.java
 * 3. Package  : com.nsok.ccbs.system.mapper.common.menu
 * 4. Comment  : 마스트 처리 Interceptor Mapper
 * 5. 작성자   : JHYun
 * 6. 작성일   : 2017. 10. 23. 오후 2:10:56
 * 7. 변경이력
 *     이름    :    일자       : 변경내용
 * -------------------------------------------------------
 *     JHYun :    2017. 10. 23.    : 신규개발
 * </PRE>
 */
@Component
public interface MaskInterceptorMapper {

    /**
     * <PRE>
     * 1. MethodName: list
     * 2. ClassName : MaskInterceptorMapper
     * 3. Comment   : 마스크 처리 대상 SO ID조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 3. 9. 오후 3:23:59
     * </PRE>
     * 
     * @return List<String> SO ID 리스트
     */
    List<String> getSoList();

    /**
     * <PRE>
     * 1. MethodName: getMaskRule
     * 2. ClassName : MaskInterceptorMapper
     * 3. Comment   : 마스크 처리 정보 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 3. 9. 오후 3:25:09
     * </PRE>
     * 
     * @return List<MaskRule> 해당 SO의 처리 정보 조회
     * @param soId 사업ID
     */
    List<MaskRuleVO> getMaskRule(@Param("soId") String soId);

}