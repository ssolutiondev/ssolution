package com.ssolution.admin.system.service.common.popup.impl;

import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.admin.system.domain.common.popup.OrgSearchPopupVO;
import com.ssolution.admin.system.mapper.common.popup.OrgSearchPopupMapper;
import com.ssolution.admin.system.service.common.popup.OrgSearchPopupService;
import com.ssolution.core.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * <PRE>
 * 1. FileName	: OrgSearchPopupServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.common.popup.impl
 * 3. Comment	: 조직 검색 팝업
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:43:17
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class OrgSearchPopupServiceImpl implements OrgSearchPopupService {

    /** 로그 출력. */
    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /** OrganizationMapper Autowired. */
    @Autowired
    private OrgSearchPopupMapper organizationMngMapper;


    @Override
    public OrgSearchPopupVO getAuthOrgInfo(OrgSearchPopupVO orgSearch) {
        // 필수 파라메터 체크(사업ID)
        if (StringUtils.isEmpty(orgSearch.getSoId())) {
            // Exception 발생(필수값 누락입니다. (항목 : 사업ID))
            String[] arg = { MessageUtil.getMessage("LAB.M07.LAB00004") };
            throw new ServiceException("MSG.M13.MSG00027", arg);
        }

        // 권한 조직 정보 조회
        OrgSearchPopupVO orgInfo = organizationMngMapper.getAuthOrgInfo(orgSearch);
        return orgInfo;
    }

    @Override
    public List<OrgSearchPopupVO> getOrgListByAuth(OrgSearchPopupVO orgSearch) {
        // 필수 파라메터 체크(사업ID)
        if (StringUtils.isEmpty(orgSearch.getSoId())) {
            // Exception 발생(필수값 누락입니다. (항목 : 사업ID))
            String[] arg = { MessageUtil.getMessage("LAB.M07.LAB00004") };
            throw new ServiceException("MSG.M13.MSG00027", arg);
        }
        // 필수 파라메터 체크(권한조직ID)
        if (StringUtils.isEmpty(orgSearch.getAuthOrgId())) {
            // Exception 발생(필수값 누락입니다. (항목 : 권한조직ID))
            String[] arg = { MessageUtil.getMessage("LAB.M01.LAB00492") };
            throw new ServiceException("MSG.M13.MSG00027", arg);
        }

        // 조직 목록 조회
        List<OrgSearchPopupVO> orgList = organizationMngMapper.getOrgListByAuth(orgSearch);
        return orgList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getOrgListByAuthForTree(OrgSearchPopupVO orgSearch) {
        // 필수 파라메터 체크(사업ID)
        if (StringUtils.isEmpty(orgSearch.getSoId())) {
            // Exception 발생(필수값 누락입니다. (항목 : 사업ID))
            String[] arg = { MessageUtil.getMessage("LAB.M07.LAB00004") };
            throw new ServiceException("MSG.M13.MSG00027", arg);
        }
        // 필수 파라메터 체크(권한조직ID)
        if (StringUtils.isEmpty(orgSearch.getAuthOrgId())) {
            // Exception 발생(필수값 누락입니다. (항목 : 권한조직ID))
            String[] arg = { MessageUtil.getMessage("LAB.M01.LAB00492") };
            throw new ServiceException("MSG.M13.MSG00027", arg);
        }

        // 권한조직ID
        String authOrgId = orgSearch.getAuthOrgId();

        // 조직 목록 For Tree
        List<Map<String, Object>> orgListForTree = new ArrayList<>();

        // 조직 설정을 위한 인덱스 조직 맵 생성
        Map<String, Object> indexOrgInfo = new HashMap<>();

        // 유저 권한 조직의 하위 조직 목록 조회
        List<Map<String, Object>> orgList = organizationMngMapper.getOrgListByAuthForTree(orgSearch);
        if (orgList != null && orgList.size() > 0) {
            // 자신의 조직(최상위 조직) 설정
            orgListForTree.add(orgList.get(0));
        }

        // 트리 구성을 위한 데이터 편집
        for (Map<String, Object> orgMap : orgList) {
            // 조직 맵의 키 소문자로 수정
            changeKeyToLower(orgMap);

            // 인덱스 조직 설정
            indexOrgInfo.put(orgMap.get("id").toString(), orgMap);

            // 상위 조직 조회
            if (orgMap.containsKey("upp_org_id")  && StringUtils.isEmpty(orgMap.get("upp_org_id")) == false &&
                    !authOrgId.equals(orgMap.get("id"))) {
                // 상위 조직이 존재하는 경우, 현재 조직정보를 상위 조직의 children 으로 설정
                // 인덱스 조직 리스트로부터 상위 조직 정보 조회
                Map<String, Object> uppOrgInfo = (Map<String, Object>) indexOrgInfo.get(orgMap.get("upp_org_id")
                                                                                              .toString());

                List<Map<String, Object>> childrenList = null;
                if (uppOrgInfo.containsKey("children")) {
                    // children 이 존재하는 경우,
                    // children 리스트 조회
                    childrenList = (List<Map<String, Object>>) uppOrgInfo.get("children");

                } else {
                    // children 이 존재하지 않는 경우,
                    // children 리스트 생성
                    childrenList = new ArrayList<>();

                }
                // children 리스트에 현재 조직정보 설정
                childrenList.add(orgMap);
                // 상위 조직 정보에 children 리스트 설정
                uppOrgInfo.put("children", childrenList);

            }
        }

        return orgListForTree;
    }

    /**
     * <PRE>
     * 1. MethodName: changeKeyToLower
     * 2. ClassName : OrgSearchPopupServiceImpl
     * 3. Comment   :
     * 4. 작성자    : JHLee
     * 5. 작성일    : 2017. 12. 15. 오후 6:27:32
     * </PRE>
     * 
     * @return Map<String,Object>
     * @param paramMap
     * @return
     */
    private Map<String, Object> changeKeyToLower(Map<String, Object> paramMap) {

        // 파라메터 맵의 키 조회
        Iterator<String> itKey = paramMap.keySet().iterator();
        while (itKey.hasNext()) {
            String key = itKey.next();
            // 키 소문자로 바꾸어 설정
            paramMap.put(key.toLowerCase(), paramMap.get(key));
        }

        return paramMap;
    }

}
