package com.ssolution.admin.system.service.common.popup;


import java.util.List;

import com.ssolution.admin.system.domain.common.popup.UserPopupVO;
import com.ssolution.admin.system.domain.manage.ManagerMngAdminVO;

/**
 * <PRE>
 * 1. FileName	: UserPopupService.java
 * 2. Package	: com.ssolution.admin.system.service.common.popup
 * 3. Comment	: 사용자관리 관리자용 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:45:05
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface UserPopupService {

    /**
     * <PRE>
     * 1. MethodName: getUserListPopup
     * 2. ClassName : UserMngAdminService
     * 3. Comment   : 사용자 목록 조회(For Popup)
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 29. 오후 2:00:19
     * </PRE>
     *
     * @return List<UserMngAdminVO> 사용자목록
     * @param userPopup 사용자팝업VO
     */
    List<ManagerMngAdminVO> getUserListPopup(UserPopupVO userPopup);

    /**
     * <PRE>
     * 1. MethodName: getUserUppOrg
     * 2. ClassName : UserMngAdminService
     * 3. Comment   :  사용자 상위 조직 조회
     * 4. 작성자    : Park Geun
     * 5. 작성일    : 2018. 02. 21. 오전 10:48:15
     * </PRE>
     *
     * @return List<UserMngAdminVO> 사용자목록
     * @param userPopup 사용자팝업VO
     */
    List<ManagerMngAdminVO> getUserUppOrg(UserPopupVO userPopup);


}