package com.ssolution.admin.system.controller.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssolution.admin.system.domain.board.BoardVO;
import com.ssolution.admin.system.domain.common.code.CommonDataVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.AuthMngVO;
import com.ssolution.admin.system.domain.manage.ManagerAuthMngVO;
import com.ssolution.admin.system.service.board.BoardService;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.admin.system.service.manager.ManagerAuthMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DownloadUtil;
import com.ssolution.core.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: BoardController.java
 * 2. Package	: com.ssolution.admin.system.controller.board
 * 3. Comment	: 게시판 컨트롤러
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 10. 오후 2:44:00
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 10.	|	신규 작성
 * </PRE>
 */
@Controller
@RequestMapping(value = "/system/notice/notice/notice")
public class BoardController {
    private static String URL = "system/notice/notice/notice";

    /**
     * 공지 게시판 서비스
     */
    @Autowired
    private BoardService boardService;

    @Autowired
    private ManagerAuthMngService managerAuthMngService;

    /**
     * 공통코드 서비스
     */
    @Autowired
    private CommonDataService commonDataService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    
//    /**
//     * <PRE>
//     * 1. ClassName		: BoardController
//     * 2. MethodName	: notice
//     * 3. Comment		: 게시판
//     * 4. 작성자			: DEV.YKLEE
//     * 5. 작성일			: 2020. 6. 10. 오후 6:09:58
//     *	- 
//     * </PRE>
//     * @param model
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "baord", method = RequestMethod.POST)
//    public String notice(Model model, HttpServletRequest request) {
//
//        SessionUserVO sessionUser = CommonUtil.getSessionManager();
//
//        String lng = (String) request.getSession().getAttribute("sessionLanguage");
//
//        // 사용자의 권한 리스트 조회
//        ManagerAuthMngVO userAuthMng = new ManagerAuthMngVO();
//        userAuthMng.setUserId(sessionUser.getUserId());
//        List<ManagerAuthMngVO> userAuthMngAllList = userAuthMngService.getUserAuthList(userAuthMng);
//        List<ManagerAuthMngVO> userAuthMngList = new ArrayList<>();
//
//        // 사용자의 권한에 따라 공지 유형 필터링
//        List<CommonDataVO> ntceAllTpList = commonDataService.getCommonCodeListInput("SY00016", lng);
//        List<CommonDataVO> ntceTpList = new ArrayList<>();
//        Map<String,CommonDataVO> ntceTpMap = new HashMap<>();
//        ntceTpList.add(ntceAllTpList.get(0));
//
//        for (ManagerAuthMngVO userAuth : userAuthMngAllList) {
//            if ("N".equals(userAuth.getCheckYn())) continue;
//
//            userAuthMngList.add(userAuth);
//            for (CommonDataVO code : ntceAllTpList) {
//                String searchKey = userAuth.getAuthGrpId() + ",";
//                if (!StringUtils.isEmpty(code.getRefCd1()) && code.getRefCd1().contains(searchKey)) {
//                    ntceTpMap.put(code.getCommCd(), code);
//                }
//            }
//        }
//        for(String code : ntceTpMap.keySet()){
//            ntceTpList.add(ntceTpMap.get(code));
//        }
//
//        model.addAttribute("ntceTpListInput", ntceTpList);
//        model.addAttribute("ntceTgtAListInput", userAuthMngAllList);
//
//        for (CommonDataVO ntceTp : ntceTpList) {
//            if (StringUtils.isEmpty(ntceTp.getRefCd3())) continue;
//            model.addAttribute("ntceTgtB" + ntceTp.getCommCd(), commonDataService.getCommonCodeList(ntceTp.getRefCd3(), lng));
//        }
//
//        return URL + "/notice";
//    }
//
//
//    /**
//     * <PRE>
//     * 1. ClassName		: BoardController
//     * 2. MethodName	: getNoticeListAction
//     * 3. Comment		: 게시글 리스트 조회
//     * 4. 작성자			: DEV.YKLEE
//     * 5. 작성일			: 2020. 6. 10. 오후 6:10:11
//     *	- 
//     * </PRE>
//     * @param model
//     * @param boardVO
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "getNoticeListAction", method = RequestMethod.POST)
//    public String getNoticeListAction(Model model, BoardVO boardVO, HttpServletRequest request) {
//        if (boardVO.getIsTablesDrawed() == false) {
//            model.addAttribute("data", new ArrayList<Object>());
//            return URL + "/ajax/notice";
//        }
//        List<BoardVO> list = boardService.getNoticeList(boardVO);
//        model.addAttribute("data", list);
//
//        return URL + "/ajax/notice";
//    }
//
//    /**
//     * <PRE>
//     * 1. ClassName		: BoardController
//     * 2. MethodName	: insertNoticeInfoAction
//     * 3. Comment		: 게시판 글 등록
//     * 4. 작성자			: DEV.YKLEE
//     * 5. 작성일			: 2020. 6. 10. 오후 6:10:32
//     *	- 
//     * </PRE>
//     * @param model
//     * @param boardVO
//     * @param request
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(value = "insertNoticeInfoAction", method = RequestMethod.POST)
//    public String insertNoticeInfoAction(Model model,
//                                         BoardVO boardVO,
//                                         MultipartHttpServletRequest request) throws IOException {
//        //공지 게시판 정보 저장
//        int returnNoticeCount = boardService.insertNoticeInfo(boardVO, request);
//        model.addAttribute("count", returnNoticeCount);
//
//        return URL + "/ajax/notice";
//    }
//
//    /**
//     * <PRE>
//     * 1. ClassName		: BoardController
//     * 2. MethodName	: updateNoticeInfoAction
//     * 3. Comment		: 게시판 글 수정
//     * 4. 작성자			: DEV.YKLEE
//     * 5. 작성일			: 2020. 6. 10. 오후 6:10:45
//     *	- 
//     * </PRE>
//     * @param model
//     * @param boardVO
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "updateNoticeInfoAction", method = RequestMethod.POST)
//    public String updateNoticeInfoAction(Model model,
//                                         BoardVO boardVO,
//                                         MultipartHttpServletRequest request) throws Exception {
//        //공지 게시판 정보 삭제
//        int returnNoticeCount = boardService.updateNoticeInfo(boardVO, request);
//        model.addAttribute("count", returnNoticeCount);
//        return URL + "/ajax/notice";
//    }
//
//    /**
//     * <PRE>
//     * 1. ClassName		: BoardController
//     * 2. MethodName	: deleteNoticeInfoAction
//     * 3. Comment		: 게시판 글 삭제
//     * 4. 작성자			: DEV.YKLEE
//     * 5. 작성일			: 2020. 6. 10. 오후 6:11:00
//     *	- 
//     * </PRE>
//     * @param model
//     * @param boardVO
//     * @return
//     */
//    @RequestMapping(value = "deleteNoticeInfoAction", method = RequestMethod.POST)
//    public String deleteNoticeInfoAction(Model model, BoardVO boardVO) {
//        //공지 게시판 정보 삭제
//        int returnNoticeCount = boardService.deleteNoticeInfo(boardVO);
//        model.addAttribute("count", returnNoticeCount);
//        return URL + "/ajax/notice";
//    }
//
//
//    /**
//     * <PRE>
//     * 1. ClassName		: BoardController
//     * 2. MethodName	: downloadFileAction
//     * 3. Comment		: 게시판 파일 다운로드
//     * 4. 작성자			: DEV.YKLEE
//     * 5. 작성일			: 2020. 6. 10. 오후 6:11:10
//     *	- 
//     * </PRE>
//     * @param model
//     * @param boardVO
//     * @param request
//     * @param response
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping(value = "downloadFileAction", method = {RequestMethod.POST})
//    public ResponseEntity<Resource> downloadFileAction(Model model,
//                                                       BoardVO boardVO,
//                                                       HttpServletRequest request,
//                                                       HttpServletResponse response) throws IOException {
//
//        Map<String, Object> fileMap = boardService.getNoticeFileList(boardVO, request, response);
//        model.addAttribute("downloadFile", fileMap.get("downloadFile"));
//        model.addAttribute("downloadFileName", fileMap.get("downloadFileName"));
//        model.addAttribute("isDelete", fileMap.get("isDelete") == null ? false : fileMap.get("isDelete"));
//
//
//        // Load file as Resource
//        Resource resource = loadFileAsResource((String)fileMap.get("downloadFile"));
//
//        // Try to determine file's content type
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        String downloadName = null;
//        try {
//            downloadName = DownloadUtil.getDisposition((String)fileMap.get("downloadFileName"), DownloadUtil.getBrowser(request));
//        } catch (Exception e) {
//            throw new ServiceException("MSG.M13.MSG00057");
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +downloadName + "\"")
//                .header(HttpHeaders.TRANSFER_ENCODING, "binary")
//                .body(resource);
//    }
//
//    private Resource loadFileAsResource(String path) {
//        try {
//            Path filePath = Paths.get(path).toAbsolutePath().normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new ServiceException("MSG.M03.MSG00004");
//            }
//        } catch (Exception ex) {
//            throw new ServiceException("MSG.M03.MSG00004");
//        }
//    }

    
}
