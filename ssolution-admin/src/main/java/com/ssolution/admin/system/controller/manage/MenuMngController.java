package com.ssolution.admin.system.controller.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.MenuMngVO;
import com.ssolution.admin.system.domain.manage.ProgListPopupVO;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.admin.system.service.menu.MenuMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.core.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <PRE>
 * 1. FileName	: MenuMngController.java
 * 2. Package	: com.ssolution.admin.system.controller.manage
 * 3. Comment	: 메뉴 관리 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:49:31
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/menuMng/menuMng/menuMng")
public class MenuMngController {

    /** the logger. */
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 메뉴관리 메인 URL
     */
    private static String URL = "system/menuMng/menuMng/menuMng";

    /**
     * 공통 서비스
     */
    @Autowired
    private CommonDataService commonDataService;

    /**
     * 메뉴관리 서비스
     */
    @Autowired
    MenuMngService menuMngService;

    /**
     * <PRE>
     * 1. MethodName: menuMng
     * 2. ClassName : MenuMngController
     * 3. Comment   : 메뉴관리 메인 뷰
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:39:33
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model   {@link Model}
     * @param menu    메뉴관리 VO
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping(value = "menuMng", method = RequestMethod.POST)
    public String menuMng(Model model, MenuMngVO menu, HttpServletRequest request) {

        // 공통코드 조회 - 사용여부
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        model.addAttribute("useYnCodeList", commonDataService.getCommonCodeList("SY00009", lng));

        return URL + "/menuMng";
    }

    /**
     * <PRE>
     * 1. MethodName: getMenuListAction
     * 2. ClassName : MenuMngController
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:40:10
     * </PRE>
     * 
     * @return Object
     * @param model
     * @param menu
     * @param request
     * @return
     */
    @RequestMapping(value = "getMenuListAction", method = RequestMethod.POST)
    public @ResponseBody Object getMenuListAction(Model model, MenuMngVO menu, HttpServletRequest request) {

        String lng = (String) request.getSession().getAttribute("sessionLanguage");

        // 메뉴 리스트 조회
        Map<String, Object> getMenuList = menuMngService.getMenuList(lng);

        Map<String, Object> rootNode = new HashMap<String, Object>();
        rootNode.put("id", "0");
        rootNode.put("lvl", "0");
        rootNode.put("text", "/");
        rootNode.put("icon", "fa fa-folder");
        rootNode.put("children", getMenuList.get("menuList"));
        rootNode.put("menuSearchList", getMenuList.get("menuSearchList"));

        ArrayList<Object> root = new ArrayList<Object>();
        root.add(rootNode);

        return root;
    }

    /**
     * <PRE>
     * 1. MethodName: getDownMenuListAction
     * 2. ClassName : MenuMngController
     * 3. Comment   : 하위 메뉴 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:44:32
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model   {@link Model}
     * @param menu    메뉴관리 VO
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping(value = "getDownMenuListAction", method = RequestMethod.POST)
    public String getDownMenuListAction(Model model, MenuMngVO menuMngVO, HttpServletRequest request) {

        if (menuMngVO.getIsTablesDrawed() == false) {
            model.addAttribute("data", new ArrayList<Object>());
            return URL + "/ajax/menuMng";
        }

        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        menuMngVO.setLng(lng);

        // 하위 메뉴 리스트 조회
        List<MenuMngVO> downMenuList = menuMngService.getDownMenuList(menuMngVO);
        model.addAttribute("data", downMenuList);

        return URL + "/ajax/menuMng";
    }

    /**
     * <PRE>
     * 1. MethodName: insertMenuAction
     * 2. ClassName : MenuMngController
     * 3. Comment   : 메뉴등록
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:47:50
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model   {@link Model}
     * @param menu    메뉴관리 VO
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping(value = "insertMenuAction", method = RequestMethod.POST)
    public String insertMenuAction(Model model, @RequestBody MenuMngVO menu, HttpServletRequest request) {

        // 언어 설정
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        menu.setLangTp(lng);

        // 등록자 세션 유저 설정
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        menu.setRegrId(sessionUser.getUserId());

        try {
            // 메뉴 등록
            menuMngService.insertMenuAction(menu);

        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/menuMng";
    }

    /**
     * <PRE>
     * 1. MethodName: updateMenuAction
     * 2. ClassName : MenuMngController
     * 3. Comment   : 메뉴수정
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:49:48
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model   {@link Model}
     * @param menu    메뉴관리 VO
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping(value = "updateMenuAction", method = RequestMethod.POST)
    public String updateMenuAction(Model model, @RequestBody MenuMngVO menu, HttpServletRequest request) {

        // 언어 설정
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        menu.setLangTp(lng);

        // 등록자 세션 유저 설정
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        menu.setRegrId(sessionUser.getUserId());

        try {
            // 메뉴 수정
            menuMngService.updateMenuAction(menu);

        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/menuMng";
    }

    /**
     * <PRE>
     * 1. MethodName: deleteAction
     * 2. ClassName : MenuMngController
     * 3. Comment   : 메뉴삭제(메뉴, 언어,권한)
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 2:50:03
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model   {@link Model}
     * @param menu    메뉴관리 VO
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping(value = "deleteAction", method = RequestMethod.POST)
    public String deleteAction(Model model, MenuMngVO menu, HttpServletRequest request) {

        // 언어 설정
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        // 등록자 세션 유저 설정
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        menu.setRegrId(sessionUser.getUserId());

        // 메뉴 삭제
        menuMngService.deleteAction(menu, lng, menu.getLvl());

        return URL + "/ajax/menuMng";
    }

    /**
     * <PRE>
     * 1. MethodName: progListPopup
     * 2. ClassName : MenuMngController
     * 3. Comment   : 프로그램 리스트 팝업
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 4:23:58
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model         {@link Model}
     * @param request       {@link HttpServletRequest}
     * @param progListPopup 프로그램 리스트 팝업VO
     */
    @RequestMapping(value = "progListPopup", method = RequestMethod.POST)
    public String progListPopup(Model model, HttpServletRequest request, ProgListPopupVO progListPopup) {

        // 언어 설정
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        // 공통코드 조회 - 시스템구분
        model.addAttribute("systemTpList", commonDataService.getCommonCodeList("SY00006", lng));
        // 파라메터로 전달 받은 템플릿 정보 설정
        model.addAttribute("paramInfo", progListPopup);

        return URL + "/popup/progListPopup";
    }

    /**
     * <PRE>
     * 1. MethodName: getProgListPopup
     * 2. ClassName : MenuMngController
     * 3. Comment   : 프로그램 리스트 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 10. 23. 오후 4:26:53
     * </PRE>
     * 
     * @return String 리턴 URL
     * @param model         {@link Model}
     * @param request       {@link HttpServletRequest}
     * @param progListPopup 프로그램 리스트 팝업VO
     */
    @RequestMapping(value = "getProgListPopup", method = RequestMethod.POST)
    public String getProgListPopup(Model model, HttpServletRequest request, ProgListPopupVO progListPopup) {

        // 언어 설정
        String langTp = (String) request.getSession().getAttribute("sessionLanguage");
        progListPopup.setLangTp(langTp);

        // 프로그램 리스트 조회
        model.addAttribute("progList", menuMngService.getProgListPopup(progListPopup));
        return URL + "/ajax/progListPopup";
    }

}