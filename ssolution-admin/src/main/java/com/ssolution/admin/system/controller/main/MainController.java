package com.ssolution.admin.system.controller.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssolution.admin.system.domain.common.menu.SelectedMenuVO;
import com.ssolution.admin.system.service.board.BoardService;
import com.ssolution.admin.system.service.common.main.MainService;

import javax.servlet.http.HttpServletRequest;

/**
 * <PRE>
 * 1. FileName	: MainController.java
 * 2. Package	: com.ssolution.admin.system.controller.main
 * 3. Comment	: 메인 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:37:50
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/main")
public class MainController {

    @Autowired
    private BoardService boardService;


    private String thisUrl = "system/main";

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "main", method = {RequestMethod.POST})
    public String mainCustomer(@RequestParam(required = false, defaultValue = "N") String isLogin,
                               HttpServletRequest request,
                               Model model) {

        Object oSelectedMenu = request.getSession().getAttribute("selectedMenu");
        if (oSelectedMenu != null) {
            SelectedMenuVO selectedMenu = (SelectedMenuVO) oSelectedMenu;
            request.getSession().setAttribute("selectedMenu", selectedMenu);
        }
        model.addAttribute("isLogin", isLogin);
        request.getSession().setAttribute("mainPath", "/" + thisUrl + "/mainCustomer");
        request.getSession().setAttribute("lastPagePath", "");
        return thisUrl + "/main";
    }

}
