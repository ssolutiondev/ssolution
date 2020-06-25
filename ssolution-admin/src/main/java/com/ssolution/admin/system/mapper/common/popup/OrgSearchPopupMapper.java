package com.ssolution.admin.system.mapper.common.popup;

import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.popup.OrgSearchPopupVO;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. FileName	: OrgSearchPopupMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.common.popup
 * 3. Comment	: 조직 검색 팝업
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:02:00
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface OrgSearchPopupMapper {


    /**
     * <PRE>
     * 1. MethodName: getAuthOrgInfo
     * 2. ClassName : OrganizationMngMapper
     * 3. Comment   : 권한 조직 정보 조회
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 20. 오전 10:02:59
     * </PRE>
     * 
     * @return OrgSearchPopupVO 조직정보
     * @param orgSearch 조직관리VO
     */
    OrgSearchPopupVO getAuthOrgInfo(OrgSearchPopupVO orgSearch);

    /**
     * <PRE>
     * 1. MethodName: getOrgListByAuth
     * 2. ClassName : OrganizationMngMapper
     * 3. Comment   : 조직권한으로 조직 목록 조회(For Grid)
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 7. 오후 4:36:24
     * </PRE>
     * 
     * @return List<OrgSearchPopupVO> 조직목록
     */
    List<OrgSearchPopupVO> getOrgListByAuth(OrgSearchPopupVO orgSearch);

    /**
     * <PRE>
     * 1. MethodName: getOrgListByAuthForTree
     * 2. ClassName : OrganizationMngMapper
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 8. 오후 1:30:14
     * </PRE>
     * 
     * @return List<Map<String,Object>> 조직목록
     */
    List<Map<String, Object>> getOrgListByAuthForTree(OrgSearchPopupVO orgSearchPopupVO);

}
