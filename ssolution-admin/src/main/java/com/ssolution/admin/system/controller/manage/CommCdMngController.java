package com.ssolution.admin.system.controller.manage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssolution.admin.system.domain.common.code.CommCdDtlVO;
import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.service.common.code.CommCdMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.core.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <PRE>
 * 1. FileName	: CommCdMngController.java
 * 2. Package	: com.ssolution.admin.system.controller.manage
 * 3. Comment	: 공통코드 관리 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 4:38:15
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/config/codeMng/commCdMng")
public class CommCdMngController {

    /**
     * 공통코드 관리 메인 URL
     */
    private static String URL = "system/config/codeMng/commCdMng";

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommCdMngService commCdMngService;

    /**
     *
     * <PRE>
     * 1. MethodName: commCdMng
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 공통 코드 관리 화면
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 20. 오후 3:59:00
     * </PRE>
     * 
     * @return String
     * @param mode
     * @param request
     * @return
     */
    @RequestMapping(value = "commCdMng", method = RequestMethod.POST)
    public String commCdMng(Model mode, HttpServletRequest request) {
        return URL + "/commCdMng";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: getCommonCodeGrpListAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 코드 트리 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 20. 오후 5:40:08
     * </PRE>
     * 
     * @return Object
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "getCommCdGrpListAction", method = RequestMethod.POST)
    public @ResponseBody Object getCommonCodeGrpListAction(Model model, HttpServletRequest request) {

        String lng = (String) request.getSession().getAttribute("sessionLanguage");

        ArrayList<Object> root = new ArrayList<Object>(); // json data

        Map<String, Object> rootNode = new HashMap<String, Object>();

        rootNode.put("id", "0");
        rootNode.put("text", "/");
        rootNode.put("isFolder", true);
        rootNode.put("icon", "fa fa-folder");

        Map<String, Object> codeGrpTypList = commCdMngService.getCdGrpTreeList(lng);

        rootNode.put("children", codeGrpTypList.get("cdGrpTypList"));
        rootNode.put("cdGrpSearchList", codeGrpTypList.get("cdGrpSearchList"));

        root.add(rootNode);

        return root;
    }

    /**
     *
     * <PRE>
     * 1. MethodName: getCommonCodeListAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 공통코드 상세 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 9:46:31
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @param condGroupId
     * @return
     */
    @RequestMapping(value = "getCommCdListAction", method = RequestMethod.POST)
    public String getCommonCodeListAction(Model model, HttpServletRequest request, String condGroupId) {
        String lng = (String) request.getSession().getAttribute("sessionLanguage");

        if (StringUtils.isEmpty(condGroupId)) {
            model.addAttribute("data", "");
            return URL + "/ajax/commCdMng";
        }

        List<CommCdDtlVO> codeList = commCdMngService.getCommCdList(condGroupId, lng);
        model.addAttribute("data", codeList);
        return URL + "/ajax/commCdMng";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: getLngListAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 빈 언어 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:14:47
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "getLngListAction", method = RequestMethod.POST)
    public String getLngListAction(Model model, HttpServletRequest request) {
        model.addAttribute("data", commCdMngService.getLngList());
        return URL + "/ajax/commCdMng";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: insertCodeDetailAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 코드상세 정보 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:44:23
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @param commCdDtlVO
     * @return
     */
    @RequestMapping(value = "insertCdDtlInfoAction", method = RequestMethod.POST)
    public String insertCdDtlInfoAction(Model model, HttpServletRequest request, @RequestBody CommCdDtlVO commCdDtlVO) {

        SessionUserVO sessionUser = CommonUtil.getSessionManager();

        commCdDtlVO.setChgrId(sessionUser.getUserId());
        commCdDtlVO.setRegrId(sessionUser.getUserId());
        commCdDtlVO.setChgDate(DateUtil.sysdate());
        commCdDtlVO.setRegDate(DateUtil.sysdate());
        try {
            commCdMngService.insertCdDtlInfo(commCdDtlVO);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/commCdMng";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdDtlAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 코드 상세 정보 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 4:03:35
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @param commCdDtlVO
     * @return
     */
    @RequestMapping(value = "updateCdDtlAction", method = RequestMethod.POST)
    public String updateCdDtlAction(Model model, HttpServletRequest request, @RequestBody CommCdDtlVO commCdDtlVO) {

        SessionUserVO sessionUser = CommonUtil.getSessionManager();

        commCdDtlVO.setChgrId(sessionUser.getUserId());
        commCdDtlVO.setChgDate(DateUtil.sysdate());
        try {
            commCdMngService.updateCdDtl(commCdDtlVO);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/commCdMng";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdDtlAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 공통 코드 상세 정보 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 4:17:18
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @param commCdDtlVO
     * @return
     */
    @RequestMapping(value = "deleteCdDtlAction", method = RequestMethod.POST)
    public String deleteCdDtlAction(Model model, HttpServletRequest request, CommCdDtlVO commCdDtlVO) {
        try {
            commCdMngService.deleteCdDtl(commCdDtlVO);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/commCdMng";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: commCdGrpPopup
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 공통코드 그룹 등록,수정 팝업
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 11:06:31
     * </PRE>
     * 
     * @return String
     * @param request
     * @param model
     * @param mode
     * @param sysId
     * @param sysNm
     * @param codeGrp
     * @param codeGrpNm
     * @return
     */
    @RequestMapping(value = "commCdGrpPopup", method = RequestMethod.POST)
    public String commCdGrpPopup(HttpServletRequest request,
                                 Model model,
                                 String mode,
                                 String sysId,
                                 String sysNm,
                                 String cdGrp,
                                 String cdGrpNm) {

        model.addAttribute("mode", mode);
        model.addAttribute("sysId", sysId);
        model.addAttribute("sysNm", sysNm);
        model.addAttribute("cdGrp", cdGrp);
        model.addAttribute("cdGrpNm", cdGrpNm);

        return URL + "/popup/commCdGrpPopup";

    }

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdGrpAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 공통코드 그룹 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:29:02
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @param commCdGrpVO
     * @return
     */
    @RequestMapping(value = "insertCdGrpAction", method = RequestMethod.POST)
    public String insertCdGrpAction(Model model, HttpServletRequest request, @RequestBody CommCdGrpVO commCdGrpVO) {

        SessionUserVO sessionUser = CommonUtil.getSessionManager();

        commCdGrpVO.setChgrId(sessionUser.getUserId());
        commCdGrpVO.setRegrId(sessionUser.getUserId());
        commCdGrpVO.setChgDate(DateUtil.sysdate());
        commCdGrpVO.setRegDate(DateUtil.sysdate());

        try {
            commCdMngService.insertCdGrp(commCdGrpVO);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/commonCodeGrpPopup";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdGrpAction
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 공통코드 그룹 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:41:15
     * </PRE>
     * 
     * @return String
     * @param model
     * @param request
     * @param commCdGrpVO
     * @return
     */
    @RequestMapping(value = "updateCdGrpAction", method = RequestMethod.POST)
    public String updateCdGrpAction(Model model, HttpServletRequest request, @RequestBody CommCdGrpVO commCdGrpVO) {

        SessionUserVO sessionUser = CommonUtil.getSessionManager();

        commCdGrpVO.setChgrId(sessionUser.getUserId());
        commCdGrpVO.setChgDate(DateUtil.sysdate());
        try {
            commCdMngService.updateCdGrp(commCdGrpVO);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("MSG.M10.MSG00005");
        }

        return URL + "/ajax/commonCodeGrpPopup";
    }

    /**
     *
     * <PRE>
     * 1. MethodName: getCommonGrpInfo
     * 2. ClassName : CommCdMngController
     * 3. Comment   : 코드그룹 정보 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:22:18
     * </PRE>
     * 
     * @return String
     * @param request
     * @param model
     * @param commCdGrpVO
     * @return
     */
    @RequestMapping(value = "getCommCdGrpInfoAction", method = RequestMethod.POST)
    public String getCommonGrpInfo(HttpServletRequest request, Model model, CommCdGrpVO commCdGrpVO) {

        model.addAttribute("cdGrp", commCdMngService.getCdGrpInfo(commCdGrpVO));

        return URL + "/ajax/commonCodeGrpPopup";

    }

    @RequestMapping(value = "deleteCdGrpAction", method = RequestMethod.POST)
    public String deleteCodeGrpAction(Model model, HttpServletRequest request, CommCdGrpVO commCdGrpVO) {

        commCdMngService.deleteCdGrp(commCdGrpVO);

        return URL + "/ajax/commonCode";
    }

}
