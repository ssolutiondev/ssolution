package com.ssolution.admin.system.domain.common.menu;

import java.util.Date;
import java.util.List;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: UserBokMrkVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.menu
 * 3. Comment	: 즐겨찾기
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:28:46
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class UserBokMrkVO extends PagingValueVO {

    private String userId;
    private String menuId;
    private String inptMenuId;
    private String regrId;
    private Date regDate;
    private String chgrId;
    private Date chgDate;
    private List<String> menuIdList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getInptMenuId() {
        return inptMenuId;
    }

    public void setInptMenuId(String inptMenuId) {
        this.inptMenuId = inptMenuId;
    }

    public String getRegrId() {
        return regrId;
    }

    public void setRegrId(String regrId) {
        this.regrId = regrId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getChgrId() {
        return chgrId;
    }

    public void setChgrId(String chgrId) {
        this.chgrId = chgrId;
    }

    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

    public List<String> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        this.menuIdList = menuIdList;
    }

}
