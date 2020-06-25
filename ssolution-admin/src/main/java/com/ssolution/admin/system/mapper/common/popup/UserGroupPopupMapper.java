package com.ssolution.admin.system.mapper.common.popup;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.popup.UserGroupPopupVO;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: UserGroupPopupMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.common.popup
 * 3. Comment	: 사용자그룹팝업 Mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:02:10
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface UserGroupPopupMapper {

    /**
     * <PRE>
     * 1. MethodName: userGroupList
     * 2. ClassName : UserGroupMngMapper
     * 3. Comment   : 사용자그룹팝업 리스트조회
     * 4. 작성자    : Kim Hye Won
     * 5. 작성일    : 2016. 6. 24. 오후 2:44:45
     * </PRE>
     * 
     * @return List<UserGroupMngVO> 사용자그룹팝업 리스트
     * @param userGroup 사용자그룹VO
     * @param sidx      Sort 대상 키
     * @param sord      Sort 유형(DESC, ASC)
     */
    List<UserGroupPopupVO> userGroupList(@Param(value = "userGroup") UserGroupPopupVO userGroup);

}