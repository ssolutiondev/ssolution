package com.ssolution.admin.system.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssolution.admin.system.domain.dashboard.DashboardVO;
import com.ssolution.admin.system.service.common.code.CommonDataService;

import javax.servlet.http.HttpServletRequest;


/**
 * <PRE>
 * 1. FileName	: DashboardController.java
 * 2. Package	: com.ssolution.admin.system.controller.dashboard
 * 3. Comment	: 대시보드 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:36:54
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/common/popup/dashboardViewPopup")
public class DashboardController {
    /**
     * Dashboard 메인 URL
     */
    private static String URL = "system/common/popup/dashboardViewPopup";

    /**
     * Dashboard 서비스
     */
    @Autowired
    private CommonDataService commonDataService;

    /**
     * <PRE>
     * 1. MethodName: dashboardViewPopup
     * 2. ClassName : DashboardViewController
     * 3. Comment   : Dashboard 조회 팝업 오픈
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 20. 오후 4:53:27
     * </PRE>
     * 
     * @return String 페이지URL
     * @param model         {@link Model}
     * @param request       {@link HttpServletRequest}
     * @param dashboardView Dashboard View 정보
     * @return
     */
    @RequestMapping(value = "dashboardViewPopup", method = RequestMethod.POST)
    public String dashboardViewPopup(Model model, DashboardVO dashboardView, HttpServletRequest request) {

        model.addAttribute("dashboard", dashboardView);

        return URL + "/popup/dashboardViewPopup";

    }

    /**
     * <PRE>
     * 1. MethodName: getDashboardListAction
     * 2. ClassName : DashboardViewController
     * 3. Comment   : Dashboard View 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 20. 오후 4:53:22
     * </PRE>
     * 
     * @return String 페이지URL
     * @param model         {@link Model}
     * @param request       {@link HttpServletRequest}
     * @param dashboardView Dashboard View 정보
     * @return
     */
    @RequestMapping(value = "getDashboardListAction", method = RequestMethod.POST)
    public String getDashboardListAction(Model model, DashboardVO dashboardView, HttpServletRequest request) {
        String lng = (String) request.getSession().getAttribute("sessionLanguage");

        model.addAttribute("data", commonDataService.getCommonCodeList("SY00005", lng));

        return URL + "/ajax/dashboardViewPopup";

    }
}