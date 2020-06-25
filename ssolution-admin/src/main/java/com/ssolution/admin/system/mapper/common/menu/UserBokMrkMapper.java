package com.ssolution.admin.system.mapper.common.menu;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.menu.UserBokMrkVO;

import java.util.List;

/**
 *
 * <PRE>
 * 1. ClassName: UserBokMrkMapper
 * 2. FileName : UserBokMrkMapper.java
 * 3. Package  : com.nsok.ccbs.system.mapper.common.common
 * 4. Comment  : 즐겨찾기
 * 5. 작성자   : jhkim
 * 6. 작성일   : 2017. 11. 1. 오후 4:17:26
 * 7. 변경이력
 *     이름    :    일자       : 변경내용
 * -------------------------------------------------------
 *     jhkim :    2017. 11. 1.    : 신규개발
 * </PRE>
 */
@Component
public interface UserBokMrkMapper {

    /**
     *
     * <PRE>
     * 1. MethodName: getUserBokMrkList
     * 2. ClassName : UserBokMrkMapper
     * 3. Comment   : 즐겨찾기 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 11. 1. 오후 4:15:10
     * </PRE>
     * 
     * @return List<UserBokMrkVO>
     * @param userBokMrkVO
     * @return
     */
    List<UserBokMrkVO> getUserBokMrkList(@Param(value = "userBokMrkVO") UserBokMrkVO userBokMrkVO);

    /**
     *
     * <PRE>
     * 1. MethodName: insertUserBokMrk
     * 2. ClassName : UserBokMrkMapper
     * 3. Comment   : 즐겨찾기 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 11. 1. 오후 4:15:18
     * </PRE>
     * 
     * @return int
     * @param userBokMrkVO
     * @return
     */
    int insertUserBokMrk(@Param(value = "userBokMrkVO") UserBokMrkVO userBokMrkVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteUserBokMrk
     * 2. ClassName : UserBokMrkMapper
     * 3. Comment   : 즐겨찾기 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 11. 1. 오후 4:15:32
     * </PRE>
     * 
     * @return int
     * @param userBokMrkVO
     * @return
     */
    int deleteUserBokMrk(@Param(value = "userBokMrkVO") UserBokMrkVO userBokMrkVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteUserBokMrkAuth
     * 2. ClassName : UserBokMrkMapper
     * 3. Comment   : 권한 없는 즐겨찾기 메뉴 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 11. 1. 오후 5:09:24
     * </PRE>
     * 
     * @return int
     * @param userBokMrkVO
     * @return
     */
    int deleteUserBokMrkAuth(@Param(value = "userBokMrkVO") UserBokMrkVO userBokMrkVO);
}
