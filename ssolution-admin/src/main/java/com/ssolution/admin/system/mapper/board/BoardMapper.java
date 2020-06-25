package com.ssolution.admin.system.mapper.board;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.board.FileVO;
import com.ssolution.admin.system.domain.board.BoardVO;
import com.ssolution.admin.system.domain.board.NtceTgtVO;

import java.util.List;
import java.util.Map;

/**
 * 
 * <PRE>
 * 1. ClassName: NoticeMapper
 * 2. FileName : NoticeMapper.java
 * 3. Package  : com.nsok.ccbs.system.mapper.notice.notice
 * 4. Comment  : 공지 게시판 Mapper
 * 5. 작성자   : Jeong Ki Nam
 * 6. 작성일   : 2017. 10. 25. 오전 11:21:34
 * 7. 변경이력
 *     이름    :    일자       : 변경내용
 * -------------------------------------------------------
 *     Jeong Ki Nam :    2017. 10. 25.    : 신규개발
 * </PRE>
 */
@Component
public interface BoardMapper {

    /**
     * 
     * <PRE>
     * 1. MethodName: getNoticeList
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 조회 
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 25. 오후 1:18:34
     * </PRE>
     * 
     * @return List<NoticeVO> 공지 게시판 리스트
     * @param noticeVO 게시판 VO
     */
    List<BoardVO> getNoticeList(@Param(value = "noticeVO") BoardVO boardVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertNoticeInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : Jeong Ki Nam
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 25. 오후 1:59:21
     * </PRE>
     * 
     * @return int 공지 게시판 글 등록 결과값
     * @param noticeVO 공지 게시판 VO
     */
//    int insertNoticeInfo(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertNoticeFile
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 파일 정보 저장
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 31. 오전 9:16:20
     * </PRE>
     * 
     * @return int 파일 정보 저장 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int insertNoticeFile(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: updateNoticeInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 수정
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 31. 오전 9:18:34
     * </PRE>
     * 
     * @return int 수정 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int updateNoticeInfo(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: updateNoticeFile
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 파일 정보 수정
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 31. 오전 9:19:04
     * </PRE>
     * 
     * @return String 공지 게시판
     * @param noticeVO 공지 게시판 VO
     * @return
     */
//    int updateNoticeFile(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteNoticeFile
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 파일 수정
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 31. 오전 9:20:40
     * </PRE>
     * 
     * @return String 파일 수정 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteNoticeFile(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: insertNoticeTgtInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 대상 저장
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 27. 오후 2:04:31
     * </PRE>
     * 
     * @return int 저장 수
     * @param noticeVO 공지 게시판 VO
     */
//    int insertNoticeTgtInfo(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getNoticeFileList
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 파일 정보 
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 10. 30. 오후 1:16:02
     * </PRE>
     * 
     * @return List<FileVO> 공지 리스트
     * @param noticeVO 공지 게시판 VO
     */
//    List<FileVO> getNoticeFileList(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteNoticeTgtInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 게시판 대상 삭제
     * 4. 작성자    : Jeong ki Nam
     * 5. 작성일    : 2017. 10. 30. 오후 5:45:46
     * </PRE>
     * 
     * @return int 게시판 대상 삭제 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteNoticeTgtInfo(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteNoticeInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시글 삭제
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 1. 오전 11:28:57
     * </PRE>
     * 
     * @return int 공지글 삭제 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteNoticeInfo(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: deleteNoticeAllFile
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시글 모든 파일 삭제
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 1. 오전 11:34:36
     * </PRE>
     * 
     * @return int 삭제 카운트
     * @param noticeVO 공지 게시판 VO
     */
//    int deleteNoticeAllFile(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getDashboardNoticeList
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시글 조회 (대쉬보드)
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 1. 오후 2:28:11
     * </PRE>
     * 
     * @return List<NoticeVO> 공지 게시글 리스트
     */
//    List<NoticeVO> getDashboardNoticeList(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getNoticeDetail
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 게시판 상세 정보
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 11. 2. 오전 10:58:39
     * </PRE>
     * 
     * @return NoticeVO 공지 게시판 VO
     * @param noticeVO 공지 게시판 VO
     */
//    NoticeVO getNoticeDetail(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getNoticeReadCount
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 게시판 읽음 여부 조회
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 12. 13. 오전 10:27:57
     * </PRE>
     * 
     * @return int
     * @param noticeVO
     */
//    int getNoticeReadCount(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: addNoticeReadCount
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 게시판 조회수 증가
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 12. 13. 오전 10:28:18
     * </PRE>
     * 
     * @return void
     * @param noticeVO 공지 게시판 VO
     */
//    void addNoticeReadCount(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: addNoticeReadInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 읽음 여부 정보 추가
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2017. 12. 13. 오전 10:51:09
     * </PRE>
     * 
     * @return void
     * @param noticeVO 공지 게시판 VO
     */
//    void addNoticeReadInfo(@Param(value = "noticeVO") NoticeVO noticeVO);

    /**
     * 
     * <PRE>
     * 1. MethodName: getNoticeFileInfo
     * 2. ClassName : NoticeMapper
     * 3. Comment   : 공지 게시판 물리 파일 삭제
     * 4. 작성자    : Jeong Ki Nam
     * 5. 작성일    : 2018. 1. 24. 오전 10:07:34
     * </PRE>
     * 
     * @return FileVO 파일 VO
     * @param int 공지 게시판 번호
     * @param int 파일 번호
     */
    FileVO getNoticeFileInfo(@Param(value = "ntceId") int ntceId, @Param(value = "ntceOrd") int ord);

    List<NtceTgtVO> getNtceTgtList(@Param(value = "ntceId") int ntceId);

    int getNtceId();

    int getReadNotice(@Param(value = "ntceId") int ntceId, @Param(value = "userId") String userId);

    Map<String,Object> getPushTmpl(@Param(value = "tmpltId") String tmpltId);
}