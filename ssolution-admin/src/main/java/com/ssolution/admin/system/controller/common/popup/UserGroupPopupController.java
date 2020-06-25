package com.ssolution.admin.system.controller.common.popup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssolution.admin.system.domain.common.popup.UserGroupPopupVO;
import com.ssolution.admin.system.service.common.popup.UserGroupPopupService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <PRE>
 * 1. FileName	: UserGroupPopupController.java
 * 2. Package	: com.ssolution.admin.system.controller.common.popup
 * 3. Comment	: 사용자그룹
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 1:15:54
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/common/popup/userGroupPopup")
public class UserGroupPopupController {
    /**
     * 사용자그룹 메인 URL
     */
    private static String URL = "system/common/popup/userGroupPopup";
    /**
     * 사용자그룹 서비스
     */
    @Autowired
    UserGroupPopupService userGroupPopupService;

    /**
     * <PRE>
     * 1. ClassName		: UserGroupPopupController
     * 2. MethodName	: userGroupListPopup
     * 3. Comment		: 사용자그룹 메인
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 10. 오후 1:16:19
     *	- 
     * </PRE>
     * @param model
     * @param userGroup
     * @param multiFlag
     * @param userGroupId
     * @param userGroupName
     * @param userId
     * @param baseId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "userGroupListPopup", method = RequestMethod.POST)
    public String userGroupListPopup(Model model,
                                     UserGroupPopupVO userGroup,
                                     String multiFlag,
                                     String userGroupId,
                                     String userGroupName,
                                     String userId,
                                     String baseId,
                                     HttpServletRequest request) throws Exception {
        model.addAttribute("userGroupId", userGroupId);
        model.addAttribute("userGroupName", userGroupName);
        model.addAttribute("userId", userId);
        userGroup.setMultiFlag(multiFlag);
        model.addAttribute("baseId", baseId);
        model.addAttribute("userGroup", userGroup);
        return URL + "/popup/userGroupListPopup";

    }

    /**
     * <PRE>
     * 1. ClassName		: UserGroupPopupController
     * 2. MethodName	: userGroupListActionPopup
     * 3. Comment		: 사용자그룹ID 조회
     * 4. 작성자			: DEV.YKLEE
     * 5. 작성일			: 2020. 6. 10. 오후 1:18:37
     *	- 
     * </PRE>
     * @param userGroup
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "userGroupListActionPopup", method = RequestMethod.POST)
    public String userGroupListActionPopup(UserGroupPopupVO userGroup,
                                           Model model,
                                           HttpServletRequest request) throws Exception {

        List<UserGroupPopupVO> userGroupList = userGroupPopupService.userGroupList(userGroup);
        model.addAttribute("data", userGroupList);

        return URL + "/ajax/userGroupListActionPopup";
    }
}