package com.ssolution.admin.system.service.board.impl;

import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.admin.system.domain.board.FileVO;
import com.ssolution.admin.system.domain.board.BoardVO;
import com.ssolution.admin.system.domain.board.NtceTgtVO;
import com.ssolution.admin.system.domain.common.code.CommonDataVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.ManagerAuthMngVO;
import com.ssolution.admin.system.mapper.board.BoardMapper;
import com.ssolution.admin.system.service.board.BoardService;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.admin.system.service.manager.ManagerAuthMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.core.exception.ServiceException;
import com.ssolution.core.rabbitmq.MessageProducer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <PRE>
 * 1. FileName	: BoardServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.board.impl
 * 3. Comment	: 게시판 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:38:28
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class BoardServiceImpl implements BoardService {

    /**
     * 공지사항 Mapper
     */
    @Autowired
    private BoardMapper noticeMapper;

    @Autowired
    private ManagerAuthMngService userAuthMngService;

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private MessageProducer messageProducer;

    private final static String MQ_EXCHANGE="push_exchanger";


//
//    /**
//     * 파일 경로
//     */
//    @Value("${FILE_PATH}")
//    private String filePath;
//
//    @Override
//    public List<BoardVO> getNoticeList(BoardVO noticeVO) {
//        List<BoardVO> noticeList = noticeMapper.getNoticeList(noticeVO);
//        int index = 0;
//        if (noticeList.size() > 0) {
//            for (BoardVo notice : noticeList) {
//                List<FileVO> fileList = noticeMapper.getNoticeFileList(notice);
//                noticeList.get(index).getFileVO().setFileList(fileList);
//                noticeList.get(index).getFileVO().setTotalFileCount(fileList.size());
//                index++;
//                List<NtceTgtVO> ntceTgList = noticeMapper.getNtceTgtList(notice.getNtceId());
//                StringBuffer tgtA = new StringBuffer();
//                StringBuffer tgtB = new StringBuffer();
//                for(NtceTgtVO ntce : ntceTgList){
//                    if("A".equals(ntce.getTgtTp())){
//                        tgtA.append(ntce.getTgtId());
//                        tgtA.append(",");
//                    }else if("B".equals(ntce.getTgtTp())){
//                        tgtB.append(ntce.getTgtId());
//                        tgtB.append(",");
//
//                    }
//                }
//                if(!StringUtils.isEmpty(tgtA.toString())){
//                    notice.setTgtIdAa(tgtA.toString().substring(0,tgtA.length()-1));
//                }
//                if(!StringUtils.isEmpty(tgtB.toString())){
//                    notice.setTgtIdBb(tgtB.toString().substring(0,tgtB.length()-1));
//                }
//            }
//        }
//        return noticeList;
//    }
//
//    @Override
//    public int insertNoticeInfo(BoardVo noticeVO, MultipartHttpServletRequest request) throws IOException {
//        SessionUserVO session_user = CommonUtil.getSessionManager();
//        noticeVO.setRegrId(session_user.getUserId());
//        noticeVO.setRegDate(DateUtil.sysdate());
//        noticeVO.setChgrId(session_user.getUserId());
//        noticeVO.setChgDate(DateUtil.sysdate());
//
//        int ntce_id = noticeMapper.getNtceId();
//        noticeVO.setNtceId(ntce_id);
//        int count = noticeMapper.insertNoticeInfo(noticeVO);
//
//        //공지 게시판 대상 정보 저장
//        count += insertNoticeTgtInfo(noticeVO);
//
//        //공지 게시판 멀티 파일 저장
//        count += makeFiles(request, noticeVO);
//
//        if("N".equals(noticeVO.getPopupYn()))
//            return count;
//
//        CommonDataVO noticeTp = commonDataService.getCommonCode("SY00016", noticeVO.getNtceTp(), CommonUtil.getSessionLng());
//
//        if(noticeTp == null || StringUtils.isEmpty(noticeTp.getRefCd3())) {
//            return count;
//        }
//
//        CommonDataVO noticeTgt = commonDataService.getCommonCode(noticeTp.getRefCd3(), noticeVO.getNtceTgtVO().getTgtIdBb(), CommonUtil.getSessionLng());
//        if(noticeTgt == null ||
//                StringUtils.isEmpty(noticeTgt.getRefCd1()) ||
//                StringUtils.isEmpty(noticeTgt.getRefCd2()) ||
//                StringUtils.isEmpty(noticeTgt.getRefCd3())) {
//            return count;
//        }
//
//        CommonDataVO pushDtl = commonDataService.getCommonCode("SY00037", noticeTgt.getRefCd2(), CommonUtil.getSessionLng());
//
//        if(pushDtl == null
//                || StringUtils.isEmpty(pushDtl.getRefCd2())) {
//            return count;
//        }
//
//        Map<String,Object> pushTmplt = noticeMapper.getPushTmpl(pushDtl.getRefCd2());
//
//        Map<String,Object> message = new HashMap<>();
//        message.put("title", "Y".equals(pushDtl.getRefCd3()) ? message.get("title_nm") : "");
//        message.put("message", pushTmplt.get("ct"));
//        message.put("ntce_id", ntce_id);
//        messageProducer.produceMessageQueue(MQ_EXCHANGE, noticeTgt.getRefCd3(), message);
//
//        return count;
//    }
//
//    @Override
//    public int makeFiles(MultipartHttpServletRequest request, BoardVo noticeVO) throws IOException {
//        String path = filePath + "notice/";
//
//        File dir = new File(path); //파일 저장 경로 확인, 없으면 만든다.
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        Iterator<String> itr = request.getFileNames(); //파일들을 Iterator 에 넣는다.
//        String originalFileName; //파일명
//        String uid; //uid
//        String external; //확장자
//        String newFileName; //새로운 파일명
//        FileVO fileVO; //file 객체
//        int ntceOrd = 1;
//        while (itr.hasNext()) { //파일을 하나씩 불러온다.
//            MultipartFile mpf = request.getFile(itr.next());
//
//            originalFileName = mpf.getOriginalFilename();
//            external = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//            uid = UUID.randomUUID().toString();
//            newFileName = uid + "." + external;
//
//            /* 파일 업로드 형식 제한 */
//            String[] arg = { MessageUtil.getMessage("MSG.M15.MSG00143") };
//            String[] availExternal = arg[0].split(",");//{"jpg","gif","jpeg","pdf","xml","xlsx","mp4"};
//
//            List<String> arrExtnl = Arrays.asList(availExternal);
//            if (!arrExtnl.contains(external.toLowerCase())) {
//                throw new ServiceException("MSG.M08.MSG00247", arg);
//            }
//            ////////////////////////////////
//
//            String fileFullPath = path + newFileName; //파일 전체 경로
//            try {
//                //파일 저장
//                mpf.transferTo(new File(fileFullPath)); //파일저장
//
//                fileVO = new FileVO(ntceOrd, path, originalFileName, newFileName);
//                noticeVO.setFileVO(fileVO);
//                noticeMapper.insertNoticeFile(noticeVO);
//                ntceOrd++;
//            } catch (IOException e) {
//                throw new ServiceException("MSG.M13.MSG00005");
//
//            }
//        }
//
//        return ntceOrd;
//    }
//
//    @Override
//    public int updateNoticeInfo(BoardVo noticeVO, MultipartHttpServletRequest request) throws Exception {
//        SessionUserVO session_user = CommonUtil.getSessionManager();
//        noticeVO.setChgrId(session_user.getUserId());
//        noticeVO.setChgDate(DateUtil.sysdate());
//        int count = noticeMapper.updateNoticeInfo(noticeVO);
//
//        //공지 게시판 대상 삭제
//        count += deleteNoticeTgtInfo(noticeVO);
//        count += insertNoticeTgtInfo(noticeVO);
//
//        //공지 게시판 멅티 파일 삭제
//        count += makeUpdateFiles(request, noticeVO);
//        return count;
//    }
//
//    @Override
//    public int deleteNoticeInfo(BoardVo noticeVO) {
//        int count = noticeMapper.deleteNoticeInfo(noticeVO);
//        //공지 게시판 대상 정보 삭제
//        count += deleteNoticeTgtInfo(noticeVO);
//
//        //공지 게시판 멅티 파일 저장
//        count += deleteNoticeAllFile(noticeVO);
//        return count;
//    }
//
//    @Override
//    public int insertNoticeTgtInfo(BoardVo noticeVO) {
//        SessionUserVO session_user = CommonUtil.getSessionManager();
//        noticeVO.setRegrId(session_user.getUserId());
//        noticeVO.setRegDate(DateUtil.sysdate());
//        noticeVO.setChgrId(session_user.getUserId());
//        noticeVO.setChgDate(DateUtil.sysdate());
//
//        int count = 0;
//        String[] tgtIdA = noticeVO.getNtceTgtVO().getTgtIdAa().split(",");
//        for (String gtgIds : tgtIdA) {
//            if(StringUtils.isEmpty(gtgIds)) continue;
//            noticeVO.getNtceTgtVO().setTgtId(gtgIds);
//            noticeVO.getNtceTgtVO().setTgtTp("A");
//            count += noticeMapper.insertNoticeTgtInfo(noticeVO);
//        }
//        String[] tgtIdB = noticeVO.getNtceTgtVO().getTgtIdBb().split(",");
//        for (String gtgIds : tgtIdB) {
//            if(StringUtils.isEmpty(gtgIds)) continue;
//            noticeVO.getNtceTgtVO().setTgtId(gtgIds);
//            noticeVO.getNtceTgtVO().setTgtTp("B");
//            count += noticeMapper.insertNoticeTgtInfo(noticeVO);
//        }
//        return count;
//    }
//
//    @Override
//    public int deleteNoticeTgtInfo(BoardVo noticeVO) {
//        return noticeMapper.deleteNoticeTgtInfo(noticeVO);
//    }
//
//    @Override
//    public int makeUpdateFiles(MultipartHttpServletRequest request, BoardVo noticeVO) throws Exception {
//        String path = filePath + "notice/";
//        File dir = new File(path); //파일 저장 경로 확인, 없으면 만든다.
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        Iterator<String> itr = request.getFileNames(); //파일들을 Iterator 에 넣는다.
//        String originalFileName; //파일명
//        String uid; //uid
//        String external; //확장자
//        String newFileName; //새로운 파일명
//
//        int ntceOrd = 1;
//        int count = 0;
//        for (int fileIndex = 0; fileIndex < noticeVO.getFileVO().getFileList().size(); fileIndex++) {
//
//            if (noticeVO.getFileVO().getFileList().get(fileIndex).getUuid().equals("") && itr.hasNext()) { //신규 파일 or 변경 파일 저장
//                MultipartFile mpf = request.getFile(itr.next());
//
//                originalFileName = mpf.getOriginalFilename();
//                external = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); // 확장자
//                uid = UUID.randomUUID().toString();
//                newFileName = uid + "." + external;
//
//                /* 파일 업로드 형식 제한 */
//                String[] arg = { MessageUtil.getMessage("MSG.M15.MSG00143") };
//                String[] availExternal = arg[0].split(",");//{"jpg","gif","jpeg","pdf","xml","xlsx","mp4"};
//
//                List<String> arrExtnl = Arrays.asList(availExternal);
//                if (!arrExtnl.contains(external.toLowerCase())) {
//                    throw new ServiceException("MSG.M08.MSG00247", arg);
//                }
//                ////////////////////////////////
//
//                String fileFullPath = path + "/" + newFileName; //파일 전체 경로
//                try {
//                    mpf.transferTo(new File(fileFullPath)); //파일저장
//
//                    noticeVO.getFileVO().setNtceId(noticeVO.getNtceId());
//                    noticeVO.getFileVO().setNtceOrd(ntceOrd);
//                    noticeVO.getFileVO().setFilePath(path);
//                    noticeVO.getFileVO().setFileNm(originalFileName);
//                    noticeVO.getFileVO().setUuid(newFileName);
//                    if (ntceOrd > noticeVO.getFileVO().getTotalFileCount()) {
//                        count += noticeMapper.insertNoticeFile(noticeVO);
//                    } else {
//                        FileVO fileInfo = noticeMapper.getNoticeFileInfo(noticeVO.getNtceId(), (fileIndex + 1));
//                        File file = new File(fileInfo.getFilePath() + fileInfo.getUuid());
//                        try {
//                            if (file.exists()) {
//                                file.delete();
//                            }
//                        } catch (Exception e) {
//                            throw new Exception("MSG.M13.MSG00055"); //파일삭제에 실패하였습니다.
//                        }
//                        count += noticeMapper.updateNoticeFile(noticeVO);
//                    }
//                    ntceOrd++;
//                } catch (IOException e) {
//                    throw new ServiceException("MSG.M13.MSG00005"); //파일 업로드 실패입니다.
//                }
//            } else { // 기존 파일일 경우
//                if (!noticeVO.getFileVO().getFileList().get(fileIndex).getUuid().equals("")) {
//                    noticeVO.getFileVO().setFileNm(noticeVO.getFileVO().getFileList().get(fileIndex).getFileNm());
//                    noticeVO.getFileVO().setNtceId(noticeVO.getNtceId());
//                    noticeVO.getFileVO().setNtceOrd(ntceOrd);
//                    noticeVO.getFileVO().setUuid(noticeVO.getFileVO().getFileList().get(fileIndex).getUuid());
//                    noticeVO.getFileVO().setFilePath(path);
//                    if (ntceOrd > noticeVO.getFileVO().getTotalFileCount()) {
//                        count += noticeMapper.insertNoticeFile(noticeVO);
//                    } else {
//                        count += noticeMapper.updateNoticeFile(noticeVO);
//                    }
//                    ntceOrd++;
//                } else {
//                    // 존재했던 파일  게시판 수정시 삭제한 파일을 물리적 파일도 삭제
//                    FileVO fileInfo = noticeMapper.getNoticeFileInfo(noticeVO.getNtceId(), (fileIndex + 1));
//                    if (fileInfo != null) {
//                        File file = new File(fileInfo.getFilePath() + fileInfo.getUuid());
//                        try {
//                            if (file.exists()) {
//                                file.delete();
//                            }
//                        } catch (Exception e) {
//                            throw new Exception("MSG.M13.MSG00055"); //파일삭제에 실패하였습니다.
//                        }
//                    }
//                }
//            }
//
//        }
//        if (count < noticeVO.getFileVO().getTotalFileCount()) { //	파일 목록 삭제
//            for (int idx = count; idx < noticeVO.getFileVO().getTotalFileCount(); idx++) {
//                noticeVO.getFileVO().setNtceOrd(ntceOrd);
//                count += noticeMapper.deleteNoticeFile(noticeVO);
//                ntceOrd++;
//            }
//        }
//
//        return count;
//    }
//
//    @Override
//    public int deleteNoticeAllFile(BoardVo noticeVO) {
//        return noticeMapper.deleteNoticeAllFile(noticeVO);
//    }
//
//    @Override
//    public List<BoardVo> getDashboardNoticeList() {
//
//        SessionUserVO sessionUser = CommonUtil.getSessionManager();
//        // 사용자의 권한 리스트 조회
//        ManagerAuthMngVO userAuthMng = new ManagerAuthMngVO();
//        userAuthMng.setUserId(sessionUser.getUserId());
//        List<ManagerAuthMngVO> userAuthMngAllList = userAuthMngService.getUserAuthList(userAuthMng);
//
//        BoardVo noticeVO = new BoardVo();
//        noticeVO.setStart(DateUtil.getDateStringYYYYMMDD(0));
//        noticeVO.setNtceTp("Z");
//        List<BoardVo> noticeList = noticeMapper.getDashboardNoticeList(noticeVO);
//        List<BoardVo> result = new ArrayList<>();
//        if (noticeList.size() > 0) {
//            for (BoardVo notice : noticeList) {
//                List<NtceTgtVO> ntceTgList = noticeMapper.getNtceTgtList(notice.getNtceId());
//                boolean isAuth = false;
//                for(NtceTgtVO ntceTg : ntceTgList){
//                    for(ManagerAuthMngVO authMngVO : userAuthMngAllList){
//                        if ("N".equals(authMngVO.getCheckYn())) continue;
//
//                        if(authMngVO.getAuthGrpId().equals(ntceTg.getTgtId())){
//                            result.add(notice);
//                            isAuth = true;
//                            break;
//                        }
//                    }
//                    if(isAuth) break;
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public BoardVo getNoticeDetail(BoardVo noticeVO) {
//        BoardVo noticeInfo = noticeMapper.getNoticeDetail(noticeVO);
//        List<FileVO> fileList = noticeMapper.getNoticeFileList(noticeVO);
//        noticeInfo.setFileList(fileList);
//        return noticeInfo;
//    }
//
//    @Override
//    public void getReadHistInfo(BoardVo noticeVO) {
//        SessionUserVO session_user = CommonUtil.getSessionManager();
//        noticeVO.setRegrId(session_user.getUserId());
//        if (noticeMapper.getNoticeReadCount(noticeVO) < 1) { // 읽지 않은 유저
//            noticeVO.setRegrId(session_user.getUserId());
//            noticeVO.setRegDate(DateUtil.sysdate());
//            noticeMapper.addNoticeReadInfo(noticeVO); // 조회 정보 추가
//        }
//    }
//
//
//    @Override
//    public Map<String, Object> getNoticeFileList(BoardVo noticeVO,
//                                                 HttpServletRequest request,
//                                                 HttpServletResponse response) throws IOException {
//        List<FileVO> fileList = noticeMapper.getNoticeFileList(noticeVO);
//        if (fileList.size() > 1) {
//            return zipFileDownLoad(fileList, request, response);
//        } else {
//            return fileDownLoad(fileList, request, response);
//        }
//    }
//
//    @Override
//    public Map<String, Object> fileDownLoad(List<FileVO> fileList,
//                                            HttpServletRequest request,
//                                            HttpServletResponse response) throws IOException {
//        String path = filePath + "notice/";
//        Map<String, Object> fileMap = new HashMap<String, Object>();
//        for (FileVO file : fileList) {
//            fileMap.put("downloadFile", FilenameUtils.concat(path , file.getUuid()));
//            fileMap.put("downloadFileName", file.getFileNm());
//        }
//
//        return fileMap;
//
//    }
//
//    @Override
//    public Map<String, Object> zipFileDownLoad(List<FileVO> fileList,
//                                               HttpServletRequest request,
//                                               HttpServletResponse response) throws IOException {
//        String path = filePath + "notice/";
//        File downloadFile = null;
//
//        int size = 1024;
//        byte[] buffer = new byte[size];
//        String fileName = UUID.randomUUID().toString() + ".zip";
//        String zipPath = FilenameUtils.concat(FileUtils.getTempDirectoryPath(),fileName); // 압축 이름
//        ZipOutputStream zout = null;
//        FileInputStream in = null;
//        try {
//
//            zout = new ZipOutputStream(new FileOutputStream(zipPath));
//
//            for (FileVO file : fileList) {
//                downloadFile = new File(path + file.getUuid()); // uuid.확장자
//                if (downloadFile.exists()) {
//                    in = new FileInputStream(downloadFile); // 압축대상 파일
//                    zout.putNextEntry(new ZipEntry(file.getFileNm())); // 압축 대상 넣기
//                    int len;
//                    while ((len = in.read(buffer)) > 0) {
//                        zout.write(buffer, 0, len);
//                    }
//                }
//            }
//            zout.closeEntry();
//        } catch (IOException e) {
//            throw new ServiceException("MSG.M13.MSG00041");
//        } finally {
//            zout.close();
//        }
//
//        Map<String, Object> fileMap = new HashMap<String, Object>();
//        fileMap.put("downloadFile", zipPath);
//        fileMap.put("downloadFileName", fileName);
//        return fileMap;
//    }
//
//    @Override
//    public List<BoardVO> getUnreadNoticeList() {
//
//        SessionUserVO sessionUser = CommonUtil.getSessionManager();
//
//        List<BoardVo> noticeList = getDashboardNoticeList();
//        List<BoardVo> unreadNoticeList = new ArrayList<>();
//        for(BoardVo notice : noticeList){
//            int count = noticeMapper.getReadNotice(notice.getNtceId(), sessionUser.getUserId());
//            if(count == 0){
//                unreadNoticeList.add(notice);
//            }
//        }
//        return unreadNoticeList;
//    }

}
