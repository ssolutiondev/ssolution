package com.ssolution.admin.system.service.common.popup;

import java.util.List;

import com.ssolution.admin.system.domain.common.popup.UserGroupPopupVO;

/**
 * <PRE>
 * 1. FileName	: UserGroupPopupService.java
 * 2. Package	: com.ssolution.admin.system.service.common.popup
 * 3. Comment	: 사용자그룹 팝업 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:44:54
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface UserGroupPopupService {

    /**
     * <PRE>
     * 1. MethodName: userGroupList
     * 2. ClassName : UserGroupPopupService
     * 3. Comment   : 사용자그룹리스트 조회
     * 4. 작성자    : Kim Hye Won
     * 5. 작성일    : 2016. 6. 24. 오후 2:43:36
     * </PRE>
     * 
     * @return List<UserGroupMngVO> 사용자그룹 리스트
     * @param userGroup 사용자그룹VO
     */
    List<UserGroupPopupVO> userGroupList(UserGroupPopupVO userGroup);

}