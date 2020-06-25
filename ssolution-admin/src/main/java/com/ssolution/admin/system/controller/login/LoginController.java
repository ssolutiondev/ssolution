package com.ssolution.admin.system.controller.login;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.service.common.menu.MenuTagService;
import com.ssolution.admin.system.service.login.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * <PRE>
 * 1. FileName	: LoginController.java
 * 2. Package	: com.ssolution.admin.system.controller.login
 * 3. Comment	: 로그인 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:37:35
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/login")
public class LoginController {

    private String thisUrl = "system/login";

    private static final String TOP_MENU_CACHE = "topMenuCache";
    private static final String LEFT_MENU_CACHE = "leftMenuCache";
    private static final String AUTH_MENU_CACHE = "authMenuCache";

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuTagService menuTagService;

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = "login", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(Model model, HttpServletRequest request){

        String resultUrl = thisUrl + "/login";
        Locale locale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());
        // 기본 언어 한국어로 세팅
        request.getSession().setAttribute("sessionCountry", locale.getCountry());
        request.getSession().setAttribute("sessionLanguage", locale.getLanguage());

        return resultUrl;
    }


    /**
     * <PRE>
     * 1. MethodName: loginAction
     * 2. ClassName : LoginController
     * 3. Comment   : 로그인 Action
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오전 8:52:32
     * </PRE>
     *
     * @return Object
     * @param userId   사용자ID
     * @param pswd     패스워드
     * @param request  {@link HttpServletRequest}
     */
    @RequestMapping(value = "loginAction", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginAction(@RequestParam(required = false) String userId,
                       @RequestParam(required = false) String pswd,
                       HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Session 정보 세팅 한다.
        session = request.getSession(true);
        String remoteAddress = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddress == null) {
            remoteAddress = request.getRemoteAddr();
        }

        Map<String, Object> loginInfo = loginService.login(userId, pswd, remoteAddress, session.getId());


        SessionUserVO sessionUser = (SessionUserVO)loginInfo.get("SESSION_USER");
        session.removeAttribute("session_user");
        session.setAttribute("session_user", sessionUser);
        session.setAttribute("maskSwitch", loginInfo.get("MASK_SWITCH"));

        loginInfo.put("FAST_MENU", menuTagService.getFastMenu(userId, session.getId(), "ko"));

        // 기본 언어 한국어로 세팅
        Locale locale = new Locale(request.getLocale().getLanguage(), request.getLocale().getCountry());
        session.setAttribute("sessionCountry", locale.getCountry());
        session.setAttribute("sessionLanguage", locale.getLanguage());

        return loginInfo;
    }


    @RequestMapping(value = "logoutAction", method = RequestMethod.POST)
    public String logoutAction(Model model, HttpServletRequest request) {
        String resultUrl = "redirect:/system/login/login";

        HttpSession session = request.getSession(false);

        if (session == null)
            return resultUrl;

        SessionUserVO sessionUser = (SessionUserVO) session.getAttribute("session_user");
        if (sessionUser != null) {
            loginService.logout(sessionUser, "N");

            removeCache(sessionUser, LEFT_MENU_CACHE);
            removeCache(sessionUser, TOP_MENU_CACHE);
            removeCache(sessionUser, AUTH_MENU_CACHE);
        }

//        session.invalidate();
        return resultUrl;
    }

    private void removeCache(SessionUserVO sessionUser, String cacheKey) {
        Ehcache ehcache = cacheManager.getCache(cacheKey);
        for (String key : (List<String>) ehcache.getKeys()) {
            if (key.startsWith(sessionUser.getSessionId())) {
                ehcache.remove(key);
            }
        }
    }
}
