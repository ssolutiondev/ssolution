package com.ssolution.admin.system.domain.common.popup;

/**
 * <PRE>
 * 1. FileName	: UserGroupPopupVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.popup
 * 3. Comment	: 권한그룹팝업VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:29:32
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public class UserGroupPopupVO {
    /**
     * 사용자그룹ID
     */
    private String userGroupId;
    /**
     * 사용자그룹명
     */
    private String userGroupName;
    /**
     * multiselect 구분
     */
    private String multiFlag;

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getMultiFlag() {
        return multiFlag;
    }

    public void setMultiFlag(String multiFlag) {
        this.multiFlag = multiFlag;
    }

}