package com.ssolution.admin.system.service.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.manage.AuthMngVO;
import com.ssolution.admin.system.domain.manage.GrpMngVO;
import com.ssolution.admin.system.mapper.manage.AuthMngMapper;
import com.ssolution.admin.system.service.manager.AuthMngService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthMngServiceImpl implements AuthMngService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthMngMapper authMngMapper;

    @Override
    public List<GrpMngVO> getAuthGroupList(GrpMngVO grpMng) {
        // 권한 그룹 리스트 조회
        List<GrpMngVO> groupList = authMngMapper.getAuthGroupList(grpMng);
        return groupList;
    }

    @Override
    public Map<String, Object> getAuthList(String authGrpId, String condAsgnYn, String lng) {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<Map<String, Object>> authList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> menuSearchList = new ArrayList<Map<String, Object>>();

        Map<String, Object> searchData0 = new HashMap<String, Object>();
        searchData0.put("id", "0");
        searchData0.put("searchKey", "0");
        menuSearchList.add(searchData0);

        int order = 0;
        /*
         * 1레벨 권한 그룹 조회
         */
        List<AuthMngVO> authList1 = authMngMapper.getAuthList(authGrpId, condAsgnYn, 1, "0", lng);
        for (AuthMngVO auth1 : authList1) {
            order++;
            Map<String, Object> authData1 = new HashMap<String, Object>();
            Map<String, Object> searchData1 = new HashMap<String, Object>();
            authData1.put("text", auth1.getMenuNm());
            authData1.put("isFolder", true);
            authData1.put("id", auth1.getMenuId());
            authData1.put("expand", false);
            authData1.put("order", order);
            authData1.put("searchKey", auth1.getMenuId() + auth1.getMenuNm());
            authData1.put("authAll", auth1.getAuthAll());
            authData1.put("authInq", auth1.getAuthInq());
            authData1.put("authReg", auth1.getAuthReg());
            authData1.put("authDel", auth1.getAuthDel());
            authData1.put("authChg", auth1.getAuthChg());
            authData1.put("authPrt", auth1.getAuthPrt());
            authData1.put("icon", "fa fa-folder");
            authData1.put("level", "2");

            searchData1.put("id", auth1.getMenuId());
            searchData1.put("searchKey", auth1.getMenuId() + auth1.getMenuNm());

            menuSearchList.add(searchData1);
            /*
             * 2레벨 권한 그룹 조회
             */
            List<AuthMngVO> authList2 = authMngMapper.getAuthList(authGrpId, condAsgnYn, 2, auth1.getMenuId(), lng);
            List<Map<String, Object>> childAuthList2 = new ArrayList<Map<String, Object>>();
            for (AuthMngVO auth2 : authList2) {
                order++;
                Map<String, Object> authData2 = new HashMap<String, Object>();
                Map<String, Object> searchData2 = new HashMap<String, Object>();
                authData2.put("text", auth2.getMenuNm());
                authData2.put("isFolder", true);
                authData2.put("id", auth2.getMenuId());
                authData2.put("expand", false);
                authData2.put("order", order);
                authData2.put("searchKey", auth2.getMenuId() + auth2.getMenuNm());
                authData2.put("authAll", auth2.getAuthAll());
                authData2.put("authInq", auth2.getAuthInq());
                authData2.put("authReg", auth2.getAuthReg());
                authData2.put("authDel", auth2.getAuthDel());
                authData2.put("authChg", auth2.getAuthChg());
                authData2.put("authPrt", auth2.getAuthPrt());
                authData2.put("icon", "fa fa-folder");
                authData2.put("level", "3");

                searchData2.put("id", auth2.getMenuId());
                searchData2.put("searchKey", auth2.getMenuId() + auth2.getMenuNm());

                menuSearchList.add(searchData2);

                childAuthList2.add(authData2);

                /*
                 * 3레벨 권한 그룹 조회
                 */
                List<AuthMngVO> authList3 = authMngMapper.getAuthList(authGrpId, condAsgnYn, 3, auth2.getMenuId(), lng);
                List<Map<String, Object>> childAuthList3 = new ArrayList<Map<String, Object>>();
                for (AuthMngVO auth3 : authList3) {
                    order++;
                    Map<String, Object> authData3 = new HashMap<String, Object>();
                    Map<String, Object> searchData3 = new HashMap<String, Object>();
                    authData3.put("text", auth3.getMenuNm());
                    authData3.put("isFolder", true);
                    authData3.put("id", auth3.getMenuId());
                    authData3.put("expand", false);
                    authData3.put("order", order);
                    authData3.put("searchKey", auth3.getMenuId() + auth3.getMenuNm());
                    authData3.put("authAll", auth3.getAuthAll());
                    authData3.put("authInq", auth3.getAuthInq());
                    authData3.put("authReg", auth3.getAuthReg());
                    authData3.put("authDel", auth3.getAuthDel());
                    authData3.put("authChg", auth3.getAuthChg());
                    authData3.put("authPrt", auth3.getAuthPrt());
                    authData3.put("icon", "fa fa-folder");
                    authData3.put("level", "4");

                    searchData3.put("id", auth3.getMenuId());
                    searchData3.put("searchKey", auth3.getMenuId() + auth3.getMenuNm());

                    menuSearchList.add(searchData3);

                    childAuthList3.add(authData3);

                    /*
                     * 4레벨 권한 그룹 조회
                     */
                    List<AuthMngVO> authList4 = authMngMapper.getAuthList(authGrpId,
                                                                          condAsgnYn,
                                                                          4,
                                                                          auth3.getMenuId(),
                                                                          lng);
                    List<Map<String, Object>> childAuthList4 = new ArrayList<Map<String, Object>>();
                    for (AuthMngVO auth4 : authList4) {
                        order++;
                        Map<String, Object> authData4 = new HashMap<String, Object>();
                        Map<String, Object> searchData4 = new HashMap<String, Object>();
                        authData4.put("text", auth4.getMenuNm());
                        authData4.put("isFolder", false);
                        authData4.put("id", auth4.getMenuId());
                        authData4.put("expand", false);
                        authData4.put("order", order);
                        authData4.put("searchKey", auth4.getMenuId() + auth4.getMenuNm());
                        authData4.put("authAll", auth4.getAuthAll());
                        authData4.put("authInq", auth4.getAuthInq());
                        authData4.put("authReg", auth4.getAuthReg());
                        authData4.put("authDel", auth4.getAuthDel());
                        authData4.put("authChg", auth4.getAuthChg());
                        authData4.put("authPrt", auth4.getAuthPrt());
                        authData4.put("level", "5");
                        authData4.put("icon", "fa fa-check");

                        searchData4.put("id", auth4.getMenuId());
                        searchData4.put("searchKey", auth4.getMenuId() + auth4.getMenuNm());

                        menuSearchList.add(searchData4);

                        childAuthList4.add(authData4);
                    }
                    authData3.put("children", childAuthList4);

                }
                authData2.put("children", childAuthList3);
            }
            authData1.put("children", childAuthList2);
            authList.add(authData1);
        }

        returnMap.put("authList", authList);
        returnMap.put("menuSearchList", menuSearchList);

        return returnMap;
    }

    @SuppressWarnings("unused")
    @Override
    public void updateAuth(AuthMngVO authMng) {

        boolean deleteYn = false;
        if ("N".equals(authMng.getAuthInq()) && "N".equals(authMng.getAuthReg()) &&
            "N".equals(authMng.getAuthChg()) &&
            "N".equals(authMng.getAuthDel()) &&
            "N".equals(authMng.getAuthPrt())) {

            deleteYn = true;
        }

        if (deleteYn) { //전체 권한 삭제인 경우
            if ("5".equals(authMng.getLvl())) {//4레벨의 메뉴인 경우 삭제 처리
                int deleteCnt = authMngMapper.deleteAuth(authMng.getAuthGrpId(), authMng.getMenuId());
                logger.debug("권한 삭제 : " + authMng.getAuthGrpId() + "/" + authMng.getMenuId());

            } else if ("4".equals(authMng.getLvl())) {//3레벨의 메뉴인 경우 하위 레벨까지 삭제 처리
                List<String> menuNoList = new ArrayList<String>();
                menuNoList.add(authMng.getMenuId());
                List<String> menuList4Level = authMngMapper.getMenuList(authMng.getMenuId(), 4);
                menuNoList.addAll(menuList4Level);
                for (String menu : menuNoList) {
                    int deleteCnt = authMngMapper.deleteAuth(authMng.getAuthGrpId(), menu);
                    logger.debug("권한 삭제 : " + authMng.getAuthGrpId() + "/" + menu);
                }

            } else if ("3".equals(authMng.getLvl())) {//2레벨의 메뉴인 경우 하위 레벨까지 삭제 처리
                List<String> menuNoList = new ArrayList<String>();
                menuNoList.add(authMng.getMenuId());
                List<String> menuList3Level = authMngMapper.getMenuList(authMng.getMenuId(), 3);
                menuNoList.addAll(menuList3Level);
                for (String menu3Level : menuList3Level) {
                    List<String> menuList4Level = authMngMapper.getMenuList(menu3Level, 4);
                    menuNoList.addAll(menuList4Level);
                }

                for (String menu : menuNoList) {
                    int deleteCnt = authMngMapper.deleteAuth(authMng.getAuthGrpId(), menu);
                    logger.debug("권한 삭제 : " + authMng.getAuthGrpId() + "/" + menu);
                }

            } else if ("2".equals(authMng.getLvl())) {//1레벨의 메뉴인 경우 하위 레벨까지 삭제 처리
                List<String> menuNoList = new ArrayList<String>();
                menuNoList.add(authMng.getMenuId());
                List<String> menuList2Level = authMngMapper.getMenuList(authMng.getMenuId(), 2);
                menuNoList.addAll(menuList2Level);
                for (String menu2Level : menuList2Level) {
                    List<String> menuList3Level = authMngMapper.getMenuList(menu2Level, 3);
                    menuNoList.addAll(menuList3Level);
                    for (String menu3Level : menuList3Level) {
                        List<String> menuList4Level = authMngMapper.getMenuList(menu3Level, 4);
                        menuNoList.addAll(menuList4Level);
                    }

                }
                for (String menu : menuNoList) {
                    int deleteCnt = authMngMapper.deleteAuth(authMng.getAuthGrpId(), menu);
                    logger.debug("권한 삭제 : " + authMng.getAuthGrpId() + "/" + menu);
                }
            }

        } else { //단순 변경인 경우

            int updateCnt = authMngMapper.updateAuth(authMng);

            if (updateCnt == 0) { //권한 추가
                int insertCnt = authMngMapper.insertAuth(authMng);
                logger.debug("권한 추가 : " + authMng.getAuthGrpId() +
                             "/" +
                             authMng.getMenuId() +
                             "/R:" +
                             authMng.getAuthInq() +
                             "/C:" +
                             authMng.getAuthReg() +
                             "/U:" +
                             authMng.getAuthChg() +
                             "/D:" +
                             authMng.getAuthDel() +
                             "/P:" +
                             authMng.getAuthPrt());

                // 파라메터 설정
                AuthMngVO paramAuthMng = authMng;
                paramAuthMng.setAuthInq("Y"); //조회권한
                paramAuthMng.setAuthReg("Y"); //등록권한
                paramAuthMng.setAuthChg("Y"); //수정권한
                paramAuthMng.setAuthDel("Y"); //삭제권한
                paramAuthMng.setAuthPrt("Y"); //출려권한

                if ("5".equals(authMng.getLvl())) {//4레벨인 경우, 상위 레벨 체크해서 추가 처리
                    String menu3Level = authMngMapper.getUpMenuNo(authMng.getMenuId());
                    int menu3LevelAuthCnt = authMngMapper.getMenuAuthCnt(authMng.getAuthGrpId(), menu3Level);
                    if (menu3LevelAuthCnt == 0) {
                        paramAuthMng.setMenuId(menu3Level); //메뉴ID(3Level)
                        authMngMapper.insertAuth(paramAuthMng);
                        logger.debug("권한 추가 3레벨  : " + authMng.getAuthGrpId() + "/" + menu3Level);
                    }

                    String menu2Level = authMngMapper.getUpMenuNo(menu3Level);
                    int menu2LevelAuthCnt = authMngMapper.getMenuAuthCnt(authMng.getAuthGrpId(), menu2Level);
                    if (menu2LevelAuthCnt == 0) {
                        paramAuthMng.setMenuId(menu2Level); //메뉴ID(2Level)
                        authMngMapper.insertAuth(paramAuthMng);
                        logger.debug("권한 추가 2레벨  : " + authMng.getAuthGrpId() + "/" + menu2Level);
                    }

                    String menu1Level = authMngMapper.getUpMenuNo(menu2Level);
                    int menu1LevelAuthCnt = authMngMapper.getMenuAuthCnt(authMng.getAuthGrpId(), menu1Level);
                    if (menu1LevelAuthCnt == 0) {
                        paramAuthMng.setMenuId(menu1Level); //메뉴ID(1Level)
                        authMngMapper.insertAuth(paramAuthMng);
                        logger.debug("권한 추가 1레벨  : " + authMng.getAuthGrpId() + "/" + menu1Level);
                    }

                } else if ("4".equals(authMng.getLvl())) {//3레벨인 경우, 상위 레벨 체크해서 추가 처리
                    String menu2Level = authMngMapper.getUpMenuNo(authMng.getMenuId());
                    int menu2LevelAuthCnt = authMngMapper.getMenuAuthCnt(authMng.getAuthGrpId(), menu2Level);
                    if (menu2LevelAuthCnt == 0) {
                        paramAuthMng.setMenuId(menu2Level); //메뉴ID(2Level)
                        authMngMapper.insertAuth(paramAuthMng);
                        logger.debug("권한 추가 2레벨  : " + authMng.getAuthGrpId() + "/" + menu2Level);
                    }

                    String menu1Level = authMngMapper.getUpMenuNo(menu2Level);
                    int menu1LevelAuthCnt = authMngMapper.getMenuAuthCnt(authMng.getAuthGrpId(), menu1Level);
                    if (menu1LevelAuthCnt == 0) {
                        paramAuthMng.setMenuId(menu1Level); //메뉴ID(1Level)
                        authMngMapper.insertAuth(paramAuthMng);
                        logger.debug("권한 추가 1레벨  : " + authMng.getAuthGrpId() + "/" + menu1Level);
                    }

                } else if ("3".equals(authMng.getLvl())) {//2레벨의 경우, 상위 레벨 체크해서 추가 처리
                    String menu1Level = authMngMapper.getUpMenuNo(authMng.getMenuId());
                    int menu1LevelAuthCnt = authMngMapper.getMenuAuthCnt(authMng.getAuthGrpId(), menu1Level);
                    if (menu1LevelAuthCnt == 0) {
                        paramAuthMng.setMenuId(menu1Level); //메뉴ID(1Level)
                        authMngMapper.insertAuth(paramAuthMng);
                        logger.debug("권한 추가 1레벨  : " + authMng.getAuthGrpId() + "/" + menu1Level);
                    }
                }

            } else {
                logger.debug("권한 변경 : " + authMng.getAuthGrpId() +
                             "/" +
                             authMng.getMenuId() +
                             "/R:" +
                             authMng.getAuthInq() +
                             "/C:" +
                             authMng.getAuthReg() +
                             "/U:" +
                             authMng.getAuthChg() +
                             "/D:" +
                             authMng.getAuthDel() +
                             "/P:" +
                             authMng.getAuthPrt());

            }
        }
    }
}