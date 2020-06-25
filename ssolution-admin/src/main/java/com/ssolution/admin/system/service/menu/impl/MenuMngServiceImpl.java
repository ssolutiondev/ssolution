package com.ssolution.admin.system.service.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.manage.MenuMngVO;
import com.ssolution.admin.system.domain.manage.ProgListPopupVO;
import com.ssolution.admin.system.mapper.menumng.MenuMngMapper;
import com.ssolution.admin.system.service.menu.MenuMngService;
import com.ssolution.core.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <PRE>
 * 1. FileName	: MenuMngServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.menu.impl
 * 3. Comment	: 메뉴관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:54:52
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class MenuMngServiceImpl implements MenuMngService {

    /** InquiryHistMapper Autowired. */
    @Autowired
    private MenuMngMapper menuMngMapper;

    /** MenuMngService Service */
    MenuMngService menuMngService;

    @Override
    public Map<String, Object> getMenuList(String lng) {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> menuSearchList = new ArrayList<Map<String, Object>>();

        Map<String, Object> searchData0 = new HashMap<String, Object>();
        searchData0.put("id", "0");
        searchData0.put("searchKey", "0");
        menuSearchList.add(searchData0);

        int order = 0;

        List<MenuMngVO> menuList1 = menuMngMapper.getAuthList("1", "0", lng);
        for (MenuMngVO menu1 : menuList1) {
            order++;
            Map<String, Object> menuData1 = new HashMap<String, Object>();
            Map<String, Object> searchData1 = new HashMap<String, Object>();
            menuData1.put("text", menu1.getMenuNm());
            menuData1.put("isFolder", true);
            menuData1.put("id", menu1.getMenuId());
            menuData1.put("expand", false);
            menuData1.put("order", order);
            menuData1.put("searchKey", menu1.getMenuId() + menu1.getMenuNm());
            menuData1.put("uppMenuId", 0);
            menuData1.put("lvl", menu1.getLvl());
            menuData1.put("uppMenuNm", menu1.getUppMenuNm());
            menuData1.put("icon", "fa fa-folder");

            searchData1.put("id", menu1.getMenuId());
            searchData1.put("searchKey", menu1.getMenuId() + menu1.getMenuNm());

            menuSearchList.add(searchData1);
            /*
             * 2레벨 메뉴
             */
            List<MenuMngVO> menuList2 = menuMngMapper.getAuthList("2", menu1.getMenuId(), lng);
            List<Map<String, Object>> childMenuList2 = new ArrayList<Map<String, Object>>();
            for (MenuMngVO menu2 : menuList2) {
                order++;
                Map<String, Object> menuData2 = new HashMap<String, Object>();
                Map<String, Object> searchData2 = new HashMap<String, Object>();
                menuData2.put("text", menu2.getMenuNm());
                menuData2.put("isFolder", true);
                menuData2.put("id", menu2.getMenuId());
                menuData2.put("expand", false);
                menuData2.put("order", order);
                menuData2.put("searchKey", menu2.getMenuId() + menu2.getMenuNm());
                menuData2.put("uppMenuId", menu2.getUppMenuId());
                menuData2.put("lvl", menu2.getLvl());
                menuData2.put("uppMenuNm", menu2.getUppMenuNm());
                menuData2.put("icon", "fa fa-folder");

                searchData2.put("id", menu2.getMenuId());
                searchData2.put("searchKey", menu2.getMenuId() + menu2.getMenuNm());

                menuSearchList.add(searchData2);

                childMenuList2.add(menuData2);

                /*
                 * 3레벨 메뉴
                 */
                List<MenuMngVO> menuList3 = menuMngMapper.getAuthList("3", menu2.getMenuId(), lng);
                List<Map<String, Object>> childMenuList3 = new ArrayList<Map<String, Object>>();
                for (MenuMngVO menu3 : menuList3) {
                    order++;
                    Map<String, Object> menuData3 = new HashMap<String, Object>();
                    Map<String, Object> searchData3 = new HashMap<String, Object>();
                    menuData3.put("text", menu3.getMenuNm());
                    menuData3.put("isFolder", true);
                    menuData3.put("id", menu3.getMenuId());
                    menuData3.put("expand", false);
                    menuData3.put("order", order);
                    menuData3.put("searchKey", menu3.getMenuId() + menu3.getMenuNm());
                    menuData3.put("uppMenuId", menu3.getMenuId());
                    menuData3.put("lvl", menu3.getLvl());
                    menuData3.put("uppMenuNm", menu3.getUppMenuNm());
                    menuData3.put("icon", "fa fa-folder");

                    searchData3.put("id", menu3.getMenuId());
                    searchData3.put("searchKey", menu3.getMenuId() + menu3.getMenuNm());

                    menuSearchList.add(searchData3);

                    childMenuList3.add(menuData3);

                    /*
                     * 4레벨 메뉴
                     */
                    List<MenuMngVO> menuList4 = menuMngMapper.getAuthList("4", menu3.getMenuId(), lng);
                    List<Map<String, Object>> childMenuList4 = new ArrayList<Map<String, Object>>();
                    for (MenuMngVO menu4 : menuList4) {
                        order++;
                        Map<String, Object> menuData4 = new HashMap<String, Object>();
                        Map<String, Object> searchData4 = new HashMap<String, Object>();
                        menuData4.put("text", menu4.getMenuNm());
                        menuData4.put("isFolder", false);
                        menuData4.put("id", menu4.getMenuId());
                        menuData4.put("expand", false);
                        menuData4.put("order", order);
                        menuData4.put("searchKey", menu4.getMenuId() + menu4.getMenuNm());
                        menuData4.put("uppMenuId", menu4.getUppMenuId());
                        menuData4.put("lvl", menu4.getLvl());
                        menuData4.put("uppMenuNm", menu4.getUppMenuNm());
                        menuData4.put("icon", "fa fa-check");

                        searchData4.put("id", menu4.getMenuId());
                        searchData4.put("searchKey", menu4.getMenuId() + menu4.getMenuNm());

                        menuSearchList.add(searchData4);

                        childMenuList4.add(menuData4);
                    }
                    menuData3.put("children", childMenuList4);

                }
                menuData2.put("children", childMenuList3);
            }
            menuData1.put("children", childMenuList2);
            menuList.add(menuData1);
        }

        returnMap.put("menuList", menuList);
        returnMap.put("menuSearchList", menuSearchList);

        return returnMap;
    }

    @Override
    public List<MenuMngVO> getDownMenuList(MenuMngVO menuMngVO) {

        List<MenuMngVO> downMenuList = menuMngMapper.getDownMenuList(menuMngVO);
        return downMenuList;
    }

    @Override
    public void insertMenuAction(MenuMngVO menu) {
        // 중복체크
        int checkMenu = menuMngMapper.checkMenu(menu);

        if (checkMenu > 0) {
            throw new ServiceException("MSG.M05.MSG00007");

        } else {
            menuMngMapper.insertMenuAction(menu);
            menuMngMapper.insertLngMenu(menu);

        }
    }

    @Override
    public void updateMenuAction(MenuMngVO menu) {
        menuMngMapper.updateMenuAction(menu);
        menuMngMapper.deleteMenuAction(menu.getMenuId());
        menuMngMapper.insertLngMenu(menu);
    }

    @Override
    public void deleteAction(MenuMngVO menu, String lng, String lvl) {
        if (lvl.equals("1")) {
            List<MenuMngVO> menuList1 = menuMngMapper.getMenuNoList(menu.getMenuId()); //1level의 menu_no로 2level의 menu_no 가져오기

            for (MenuMngVO menu2 : menuList1) {
                List<MenuMngVO> menuList2 = menuMngMapper.getMenuNoList(menu2.getMenuId()); //2level의 menu_no로 3level menu_no 가져오기

                for (MenuMngVO menu3 : menuList2) { //4level의 up_menu_no == 3level의 menu_no 삭제
                    System.err.println(menu3.getMenuId());
                    menuMngMapper.deleteMenuAuth(menu3.getMenuId()); //4level auth delete
                    menuMngMapper.deleteLastLng(menu3.getMenuId()); //4level lng 삭제
                    menuMngMapper.deleteUpAction(menu3.getMenuId()); //4level menu delete
                }
                menuMngMapper.deleteMenuAuth(menu2.getMenuId()); //3level auth delete
                menuMngMapper.deleteLastLng(menu2.getMenuId()); //3level lng 삭제
                menuMngMapper.deleteUpAction(menu2.getMenuId()); //3level menu delete
            }
            menuMngMapper.deleteMenuAuth(menu.getMenuId()); //2level auth delete
            menuMngMapper.deleteLastLng(menu.getMenuId()); //2level lng delete
            menuMngMapper.deleteUpAction(menu.getMenuId()); //2level menu delete

            menuMngMapper.deleteLastMenuAuth(menu.getMenuId()); //1level auth delete
            menuMngMapper.deleteMenuAction(menu.getMenuId()); //1level lng delete
            menuMngMapper.deleteAction(menu.getMenuId()); //1level menu delete
        } else if (lvl.equals("2")) {
            List<MenuMngVO> menuList1 = menuMngMapper.getMenuNoList(menu.getMenuId());

            for (MenuMngVO menu2 : menuList1) {
                menuMngMapper.deleteMenuAuth(menu2.getMenuId()); //4level auth delete
                menuMngMapper.deleteLastLng(menu2.getMenuId()); //4level lng 삭제
                menuMngMapper.deleteUpAction(menu2.getMenuId()); //4level menu delete
            }
            menuMngMapper.deleteMenuAuth(menu.getMenuId()); //3level auth delete
            menuMngMapper.deleteLastLng(menu.getMenuId()); //3level lng 삭제
            menuMngMapper.deleteUpAction(menu.getMenuId()); //3level menu delete

            menuMngMapper.deleteLastMenuAuth(menu.getMenuId()); //2level auth delete
            menuMngMapper.deleteMenuAction(menu.getMenuId()); //2level lng delete
            menuMngMapper.deleteAction(menu.getMenuId()); //2level menu delete

        } else if (lvl.equals("3")) {
            menuMngMapper.deleteMenuAuth(menu.getMenuId()); //4level auth delete
            menuMngMapper.deleteLastLng(menu.getMenuId()); //4level lng 삭제
            menuMngMapper.deleteUpAction(menu.getMenuId()); //4level menu delete

            menuMngMapper.deleteLastMenuAuth(menu.getMenuId()); //3level auth delete
            menuMngMapper.deleteMenuAction(menu.getMenuId()); //3level lng 삭제
            menuMngMapper.deleteAction(menu.getMenuId()); //3level menu delete
        } else if (lvl.equals("4")) {
            menuMngMapper.deleteLastMenuAuth(menu.getMenuId()); //4level auth delete
            menuMngMapper.deleteMenuAction(menu.getMenuId()); //4level lng 삭제
            menuMngMapper.deleteAction(menu.getMenuId()); //4level menu delete
        }
    }

    @Override
    public List<ProgListPopupVO> getProgListPopup(ProgListPopupVO progListPopup) {
        // 프로그램 리스트 조회
        return menuMngMapper.getProgListPopup(progListPopup);
    }

}