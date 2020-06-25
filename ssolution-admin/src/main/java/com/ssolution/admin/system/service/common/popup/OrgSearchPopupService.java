package com.ssolution.admin.system.service.common.popup;

import java.util.List;
import java.util.Map;

import com.ssolution.admin.system.domain.common.popup.OrgSearchPopupVO;

/**
 * <PRE>
 * 1. FileName	: OrgSearchPopupService.java
 * 2. Package	: com.ssolution.admin.system.service.common.popup
 * 3. Comment	: 조직 검색 팝업
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:44:11
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface OrgSearchPopupService {


    /**
     * <PRE>
     * 1. MethodName: getAuthOrgInfo
     * 2. ClassName : OrgSearchPopupService
     * 3. Comment   : 권한조직 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 20. 오전 10:00:18
     * </PRE>
     * 
     * @return OrgSearchPopupVO 조직정보
     * @param orgSearch 조직관리VO
     */
    public OrgSearchPopupVO getAuthOrgInfo(OrgSearchPopupVO orgSearch);

    /**
     * <PRE>
     * 1. MethodName: getOrgListByAuth
     * 2. ClassName : OrgSearchPopupService
     * 3. Comment   : 권한조직 기준으로 조직 목록 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 7. 오후 4:33:05
     * </PRE>
     * 
     * @return List<OrgSearchPopupVO> 조직목록
     */
    public List<OrgSearchPopupVO> getOrgListByAuth(OrgSearchPopupVO orgSearch);

    /**
     * <PRE>
     * 1. MethodName: getOrgListByAuthForTree
     * 2. ClassName : OrgSearchPopupService
     * 3. Comment   : 권한조직 기준으로 조직 목록 조회(For Tree)
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 8. 오전 10:47:52
     * </PRE>
     * 
     * @return List<Map<String,Object>>
     * @param orgSearch
     * @return
     */
    public List<Map<String, Object>> getOrgListByAuthForTree(OrgSearchPopupVO orgSearch);

}
