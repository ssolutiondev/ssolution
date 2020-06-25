package com.ssolution.admin.system.service.board;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssolution.admin.system.domain.board.FileVO;
import com.ssolution.admin.system.domain.board.BoardVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: BoardService.java
 * 2. Package	: com.ssolution.admin.system.service.board
 * 3. Comment	: 게시판 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:38:37
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface BoardService {

    /**
     * 
     * <PRE>
     * 1. MethodName: getBoardList
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시판 조회 리스트
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 25. 오후 1:16:13
     * </PRE>
     * 
     * @return List<BoardVO> 조회 리스트
     * @param noticeVO 공지 게시판 VO
     */
//    List<BoardVO> getBoardList(BoardVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertBoardInfo
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시판 글 등록
     * 4. 작성자    : Administrator
     * 5. 작성일    : 2017. 10. 25. 오후 2:01:15
     * </PRE>
     * 
     * @return int 공지 게시판 글 등록 결과값
     * @param noticeVO 공지 게시판 VO
     * @param request  파일 정보 request
     * @throws IOException 에러처리
     */
//    int insertBoardInfo(BoardVO noticeVO, MultipartHttpServletRequest request) throws IOException;

    /**
     * 
     * <PRE>
     * 1. MethodName: makeFiles
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시판 파일 등록
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 25. 오후 2:01:55
     * </PRE>
     * 
     * @return String 파일 등록 결과값
     * @param request  파일정보
     * @param noticeVO 공지 게시판 VO
     * @throws IOException 에러 처리
     */
//    int makeFiles(MultipartHttpServletRequest request, BoardVO noticeVO) throws IOException;

    /**
     * 
     * <PRE>
     * 1. MethodName: updateBoardInfo
     * 2. ClassName : BoardService
     * 3. Comment   :
     * 4. 작성자    : Administrator
     * 5. 작성일    : 2017. 10. 30. 오후 5:35:29
     * </PRE>
     * 
     * @return int 공지사항 수정 수
     * @param noticeVO 공지 게시판 VO
     * @param request  파일 정보 request
     * @throws Exception 예외처리
     */
//    int updateBoardInfo(BoardVO noticeVO, MultipartHttpServletRequest request) throws Exception;

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteBoardInfo
     * 2. ClassName : BoardService
     * 3. Comment   : 공지게시글 삭제
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 30. 오후 5:50:51
     * </PRE>
     * 
     * @return int 삭제 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteBoardInfo(BoardVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertBoardTgtInfo
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시판 대상 저장
     * 4. 작성자    : Jeong ki Nam
     * 5. 작성일    : 2017. 10. 27. 오후 2:02:20
     * </PRE>
     * 
     * @return int 저장 수
     * @param noticeVO 공지 게시판 VO
     */
//    int insertBoardTgtInfo(BoardVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteBoardTgtInfo
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시글 대상 삭제
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 30. 오후 5:51:37
     * </PRE>
     * 
     * @return int 공지 게시글 대상 삭제수
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteBoardTgtInfo(BoardVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: makeUpdateFiles
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시판 파일 수정
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 31. 오전 9:27:55
     * </PRE>
     * 
     * @return int 파일 수정 개수
     * @param request  파일 정보 request
     * @param noticeVO 게시판 정보 VO
     */
//    int makeUpdateFiles(MultipartHttpServletRequest request, BoardVO noticeVO) throws Exception;

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteBoardAllFile
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시글 모든 파일 삭제
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 1. 오전 11:33:10
     * </PRE>
     * 
     * @return int 삭제 수
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteBoardAllFile(BoardVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getDashboardBoardList
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시글 조회 (대쉬보드)
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 1. 오후 2:26:45
     * </PRE>
     * 
     * @param authMngVO
     * @return List<BoardVO> 공지 게시글 리스트
     * @param noticeVO 공지 게시판 VO
     */
//    List<BoardVO> getDashboardBoardList();

    /**
     * 
     * <PRE>
     * 1. MethodName: getBoardDetail
     * 2. ClassName : BoardService
     * 3. Comment   : 공지게시글 상세 정보
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 2. 오전 10:54:46
     * </PRE>
     * 
     * @return BoardVO 공지 게시판 VO
     * @param noticeVO 공지 게시판 VO
     */
//    BoardVO getBoardDetail(BoardVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getBoardFileList
     * 2. ClassName : BoardService
     * 3. Comment   : 공지사항 파일 리스트
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 3. 오후 12:12:15
     * </PRE>
     * 
     * @return void
     * @param noticeVO 공지사항
     * @param request  HttpServletResponse
     * @throws IOException
     */
//    Map<String, Object> getBoardFileList(BoardVO noticeVO,
//                                          HttpServletRequest request,
//                                          HttpServletResponse reponse) throws IOException;

    /**
     * 
     * <PRE>
     * 1. MethodName: fileDownLoad
     * 2. ClassName : BoardService
     * 3. Comment   : 파일 다운로드
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 3. 오후 2:41:23
     * </PRE>
     * 
     * @return Map<String, Object> 리턴 맵
     * @param fileList 파일정보
     * @param request  HttpServletRequest
     * @param response httpServletResponse
     * @throws IOException Exception 처리
     */
//    Map<String, Object> fileDownLoad(List<FileVO> fileList,
//                                     HttpServletRequest request,
//                                     HttpServletResponse response) throws IOException;

    /**
     * 
     * <PRE>
     * 1. MethodName: zipFileDownLoad
     * 2. ClassName : BoardService
     * 3. Comment   : 전체 파일 다운로드
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 7. 오전 9:14:44
     * </PRE>
     * 
     * @return void 리턴 타입
     * @param fileList 압축 대상 파일 리스트
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws IOException 에러 처리
     */
//    Map<String, Object> zipFileDownLoad(List<FileVO> fileList,
//                                        HttpServletRequest request,
//                                        HttpServletResponse response) throws IOException;

    /**
     * 
     * <PRE>
     * 1. MethodName: getReadHistInfo
     * 2. ClassName : BoardService
     * 3. Comment   : 공지 게시판 Hist 정보 
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 12. 13. 오전 11:36:29
     * </PRE>
     * 
     * @return void
     * @param noticeVO 공지 게시판 VO
     */
//    void getReadHistInfo(BoardVO noticeVO);

//    List<BoardVO> getUnreadBoardList();
}
