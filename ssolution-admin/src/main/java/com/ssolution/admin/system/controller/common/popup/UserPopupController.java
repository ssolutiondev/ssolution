
package com.ssolution.admin.system.controller.common.popup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssolution.admin.system.domain.common.popup.OrgSearchPopupVO;
import com.ssolution.admin.system.domain.common.popup.UserPopupVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.service.common.popup.OrgSearchPopupService;
import com.ssolution.admin.system.service.common.popup.UserPopupService;
import com.ssolution.admin.system.util.CommonUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * <PRE>
 * 1. FileName	: UserPopupController.java
 * 2. Package	: com.ssolution.admin.system.controller.common.popup
 * 3. Comment	: 관리자 관리
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 1:19:00
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/common/popup/userPopup")
public class UserPopupController {
    private static String URL = "system/common/popup/userPopup";

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 사용자관리 서비스
     */
    @Autowired
    private UserPopupService userPopupService;

    /**
     * 조직관리 서비스
     */
    @Autowired
    private OrgSearchPopupService orgSearchPopupService;

    /**
     * <PRE>
     * 1. MethodName: userPopup
     * 2. ClassName : UserMngAdminController
     * 3. Comment   : 사용자 검색 팝업 화면
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 19. 오후 2:15:29
     * </PRE>
     * 
     * @return String 페이지 URL
     * @param model     {@link Model}
     * @param request   {@link HttpServletRequest}
     * @param userPopup 사용자검색팝업VO
     * @return
     */
    @RequestMapping(value = "userPopup", method = RequestMethod.POST)
    public String userPopup(Model model, HttpServletRequest request, UserPopupVO userPopup) {

        // 등록자 세션 유저 설정
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        if (StringUtils.isEmpty(userPopup.getSoId())) {
            // 사업ID 존재하지 않는 경우
            // 사업ID 설정
            userPopup.setSoId(sessionUser.getSoId());
        }

        // 검색조직 유형에 따른 검색기준조직 설정
        // 검색 조직 유형
        String srchOrgTp = "";
        if (StringUtils.isEmpty(userPopup.getSrchOrgTp())) {
            // 검색조직유형 설정 존재하지 않는 경우
            srchOrgTp = "ALL";
        } else {
            // 검색조직유형 설정 존재하는 경우
            srchOrgTp = userPopup.getSrchOrgTp().toUpperCase();
        }

        // 검색 기준 조직
        String srchOrgId = "";
        String srchOrgNm = "";
        if (srchOrgTp.equals("ALL")) {
            // 파라메터 설정
            OrgSearchPopupVO orgSearch = new OrgSearchPopupVO();
            orgSearch.setSoId(userPopup.getSoId());
            orgSearch.setUserOrgId(sessionUser.getOrgId());

            // 유저 조직 기준으로 최상위 조직 조회
            OrgSearchPopupVO orgInfo = orgSearchPopupService.getAuthOrgInfo(orgSearch);
            srchOrgId = orgInfo.getOrgId();
            srchOrgNm = orgInfo.getOrgNm();

        } else if (srchOrgTp.equals("AUTH")) {
            // 유저 권한 조직 설정
            srchOrgId = sessionUser.getAuthOrgId();

        } else if (srchOrgTp.equals("MINE")) {
            // 하위 조직 표시 여부 설정
            userPopup.setSubOrgDispYn("N");
            // 유저 조직 설정
            srchOrgId = sessionUser.getOrgId();
            srchOrgNm = sessionUser.getOrgNm();

        } else if (srchOrgTp.equals("THIS")) {
            // 하위 조직 표시 여부 설정
            userPopup.setSubOrgDispYn("N");
            // 특정 조직 설정
            if (StringUtils.isEmpty(userPopup.getSrchOrgId())) {
                srchOrgId = sessionUser.getOrgId();
                srchOrgNm = sessionUser.getOrgNm();

            } else {
                // 파라메터 설정
                OrgSearchPopupVO orgSearch = new OrgSearchPopupVO();
                orgSearch.setSoId(userPopup.getSoId());
                orgSearch.setAuthOrgId(userPopup.getSrchOrgId());

                // 특정 조직 설정
                srchOrgId = userPopup.getSrchOrgId();
                srchOrgNm = orgSearchPopupService.getAuthOrgInfo(orgSearch).getOrgNm();

            }
        } else if (srchOrgTp.equals("LOW")) {
            // 특정 조직의 하위 설정
            if (StringUtils.isEmpty(userPopup.getSrchOrgId())) {
                srchOrgId = sessionUser.getOrgId();
                srchOrgNm = sessionUser.getOrgNm();

            } else {
                // 파라메터 설정
                OrgSearchPopupVO orgSearch = new OrgSearchPopupVO();
                orgSearch.setSoId(userPopup.getSoId());
                orgSearch.setAuthOrgId(userPopup.getSrchOrgId());

                // 특정 조직 설정
                srchOrgId = userPopup.getSrchOrgId();
                srchOrgNm = orgSearchPopupService.getAuthOrgInfo(orgSearch).getOrgNm();
            }
        }
        // 검색기준조직 설정
        userPopup.setSrchOrgId(srchOrgId);
        userPopup.setSrchOrgNm(srchOrgNm);

        // 조직 정보 설정
        OrgSearchPopupVO orgSearch = new OrgSearchPopupVO();
        orgSearch.setSoId(userPopup.getSoId());
        orgSearch.setAuthOrgId(sessionUser.getAuthOrgId());
        String authOrgNm = orgSearchPopupService.getAuthOrgInfo(orgSearch).getOrgNm();

        userPopup.setAuthOrgId(sessionUser.getAuthOrgId()); //권한조직ID
        userPopup.setAuthOrgNm(authOrgNm); //권한조직명
        userPopup.setOrgId(sessionUser.getOrgId()); //사용자조직ID
        userPopup.setOrgNm(sessionUser.getOrgNm()); //사용자조직명

        // 파라메터로 전달 받은 템플릿 정보 설정
        model.addAttribute("paramUserInfo", userPopup);

        return URL + "/popup/userPopup";
    }

    /**
     * <PRE>
     * 1. MethodName: getUserListPopup
     * 2. ClassName : UserMngAdminController
     * 3. Comment   : 사용자 정보 리스트 조회(팝업)
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 19. 오후 2:48:15
     * </PRE>
     * 
     * @return String 페이지 URL
     * @param model   {@link Model}
     * @param request {@link HttpServletRequest}
     * @param user    사용자검색팝업VO
     */
    @RequestMapping(value = "getUserListPopup", method = RequestMethod.POST)
    public String getUserListPopup(Model model, HttpServletRequest request, UserPopupVO user) {
        user.setUseYn("Y");
        model.addAttribute("userListPopup", userPopupService.getUserListPopup(user));
        return URL + "/ajax/userPopup";
    }

    /**
     * <PRE>
     * 1. MethodName: getUserUppOrg
     * 2. ClassName : UserMngAdminController
     * 3. Comment   : 사용자 상위 조직 조회
     * 4. 작성자    : Park Geun
     * 5. 작성일    : 2018. 02. 21. 오전 10:48:15
     * </PRE>
     * 
     * @return String 페이지 URL
     * @param model   {@link Model}
     * @param request {@link HttpServletRequest}
     * @param user    사용자검색팝업VO
     */
    @RequestMapping(value = "getUserUppOrg", method = RequestMethod.POST)
    public String getUserUppOrg(Model model, HttpServletRequest request, UserPopupVO user) {

        model.addAttribute("getUserUppOrg", userPopupService.getUserUppOrg(user));
        return URL + "/ajax/userPopup";
    }

}