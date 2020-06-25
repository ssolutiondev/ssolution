package com.ssolution.admin.system.service.common.code.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.common.code.CommCdDtlLangVO;
import com.ssolution.admin.system.domain.common.code.CommCdDtlVO;
import com.ssolution.admin.system.domain.common.code.CommCdGrpLangVO;
import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;
import com.ssolution.admin.system.domain.common.code.CommonDataVO;
import com.ssolution.admin.system.mapper.common.code.CommCdMngMapper;
import com.ssolution.admin.system.service.common.code.CommCdMngService;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.core.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommCdMngServiceImpl implements CommCdMngService {

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private CommCdMngMapper commCdMngMapper;

    /**
     * 공통코드 그룹 리스트 트리 조회
     */
    @Override
    public Map<String, Object> getCdGrpTreeList(String lng) {

        Map<String, Object> returnMap = new HashMap<String, Object>();

        Map<String, Object> searchData0 = new HashMap<String, Object>();
        List<Map<String, Object>> cdGrpSearchList = new ArrayList<Map<String, Object>>();

        searchData0.put("id", "0");
        searchData0.put("searchKey", "0");
        cdGrpSearchList.add(searchData0);

        List<Map<String, Object>> cdGrpTypList = new ArrayList<Map<String, Object>>();
        List<CommonDataVO> codeTypList = commonDataService.getCommonCodeList("SY00006", lng);
        int parentIndex = 0;
        for (CommonDataVO codeTyp : codeTypList) {
            parentIndex++;
            Map<String, Object> codeGrpTyp = new HashMap<String, Object>();

            codeGrpTyp.put("text", codeTyp.getCommNm());
            codeGrpTyp.put("isFolder", true);
            codeGrpTyp.put("id", codeTyp.getCommCd());
            codeGrpTyp.put("name", codeTyp.getCommNm());
            codeGrpTyp.put("sysId", codeTyp.getCommCd());
            codeGrpTyp.put("sysNm", codeTyp.getCommNm());
            codeGrpTyp.put("expand", false);
            codeGrpTyp.put("order", parentIndex);
            codeGrpTyp.put("icon", "fa fa-folder");

            List<CommCdGrpVO> commCdGrpList = commCdMngMapper.getCdGrpList(codeTyp.getCommCd(), lng);
            List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
            int childIndex = 0;
            for (CommCdGrpVO commCdGrp : commCdGrpList) {
                childIndex++;

                Map<String, Object> codeGrp = new HashMap<String, Object>();
                Map<String, Object> searchData2 = new HashMap<String, Object>();
                StringBuilder sb = new StringBuilder();
                sb.append(commCdGrp.getCommCdGrp());
                sb.append("_");
                sb.append(commCdGrp.getCommCdGrpNm());
                codeGrp.put("text", sb);
                codeGrp.put("isFolder", false);
                codeGrp.put("id", commCdGrp.getCommCdGrp());
                codeGrp.put("name", commCdGrp.getCommCdGrpNm());
                codeGrp.put("sysId", codeTyp.getCommCd());
                codeGrp.put("sysNm", codeTyp.getCommNm());
                codeGrp.put("order", childIndex);
                codeGrp.put("icon", "fa fa-check");

                searchData2.put("id", commCdGrp.getCommCdGrp());
                searchData2.put("searchKey", sb);

                cdGrpSearchList.add(searchData2);

                childList.add(codeGrp);
            }
            codeGrpTyp.put("children", childList);
            cdGrpTypList.add(codeGrpTyp);
        }

        returnMap.put("cdGrpTypList", cdGrpTypList);
        returnMap.put("cdGrpSearchList", cdGrpSearchList);

        return returnMap;
    }

    /**
     * 공통코드 상세 리스트 조회
     */
    @Override
    public List<CommCdDtlVO> getCommCdList(String condGroupId, String lng) {

        List<CommCdDtlVO> commCdList = commCdMngMapper.getCommCdList(condGroupId, lng);
        for (CommCdDtlVO code : commCdList) {
            code.setCodeLngList(commCdMngMapper.getCdDtlLngList(code.getCommCdGrp(), code.getCommCd()));
        }
        return commCdList;
    }

    /**
     * 빈 언어 리스트 조회
     */
    @Override
    public List<Map<String, Object>> getLngList() {
        return commCdMngMapper.getLngList();
    }

    /**
     * 공통코드 등록
     */
    @Override
    public void insertCdDtlInfo(CommCdDtlVO commCdDtlVO) {

        //코드 중복 체크
        int cdCnt = commCdMngMapper.getCdDtlCnt(commCdDtlVO.getCommCdGrp(), commCdDtlVO.getCommCd());

        if (cdCnt > 0) {
            throw new ServiceException("MSG.M03.MSG00018");
        }
        int insCnt = commCdMngMapper.insertCdDtl(commCdDtlVO);

        if (insCnt == 1) {

            List<CommCdDtlLangVO> lngList = commCdDtlVO.getParamLngList();

            for (CommCdDtlLangVO commCdDtlLangVO : lngList) {

                commCdDtlLangVO.setCommCdGrp(commCdDtlVO.getCommCdGrp());
                commCdDtlLangVO.setCommCd(commCdDtlVO.getCommCd());
                commCdDtlLangVO.setInptMenuId(commCdDtlVO.getInptMenuId());
                commCdDtlLangVO.setRegrId(commCdDtlVO.getRegrId());
                commCdDtlLangVO.setRegDate(commCdDtlVO.getRegDate());
                commCdDtlLangVO.setChgrId(commCdDtlVO.getChgrId());
                commCdDtlLangVO.setChgDate(commCdDtlVO.getChgDate());

                commCdMngMapper.insertCdDtlLang(commCdDtlLangVO);
            }
        }

    }

    /**
     * 공통코드 수정
     */
    @Override
    public void updateCdDtl(CommCdDtlVO commCdDtlVO) {

        int updateCnt = commCdMngMapper.updateCdDtl(commCdDtlVO);

        if (updateCnt == 1) {

            List<CommCdDtlLangVO> lngList = commCdDtlVO.getParamLngList();

            for (CommCdDtlLangVO commCdDtlLangVO : lngList) {

                commCdDtlLangVO.setCommCdGrp(commCdDtlVO.getCommCdGrp());
                commCdDtlLangVO.setCommCd(commCdDtlVO.getCommCd());
                commCdDtlLangVO.setInptMenuId(commCdDtlVO.getInptMenuId());
                commCdDtlLangVO.setRegrId(commCdDtlVO.getRegrId());
                commCdDtlLangVO.setRegDate(commCdDtlVO.getRegDate());
                commCdDtlLangVO.setChgrId(commCdDtlVO.getChgrId());
                commCdDtlLangVO.setChgDate(commCdDtlVO.getChgDate());

                int updateLngCnt = commCdMngMapper.updateCdDtlLang(commCdDtlLangVO);
                if (updateLngCnt == 0) {
                    commCdMngMapper.insertCdDtlLang(commCdDtlLangVO);
                }
            }

        }

    }

    @Override
    public void deleteCdDtl(CommCdDtlVO commCdDtlVO) {
        commCdMngMapper.deleteCdDtl(commCdDtlVO);
        commCdMngMapper.deleteCdDtlLng(commCdDtlVO);
    }

    /**
     * 코드그룹 정보 등록
     */
    @Override
    public void insertCdGrp(CommCdGrpVO commCdGrpVO) {
        //그룹 중복 체크
        int cdCnt = commCdMngMapper.getCdGrpCnt(commCdGrpVO.getCommCdGrp());

        if (cdCnt > 0) {
            throw new ServiceException("MSG.M03.MSG00018");
        }
        int insCnt = commCdMngMapper.insertCdGrp(commCdGrpVO);

        if (insCnt == 1) {

            List<CommCdGrpLangVO> commCdGrpLangVOList = commCdGrpVO.getCommCdGrpLangVOList();

            for (CommCdGrpLangVO commCdGrpLangVO : commCdGrpLangVOList) {

                commCdGrpLangVO.setCommCdGrp(commCdGrpVO.getCommCdGrp());
                commCdGrpLangVO.setInptMenuId(commCdGrpVO.getInptMenuId());
                commCdGrpLangVO.setRegrId(commCdGrpVO.getRegrId());
                commCdGrpLangVO.setRegDate(commCdGrpVO.getRegDate());
                commCdGrpLangVO.setChgrId(commCdGrpVO.getChgrId());
                commCdGrpLangVO.setChgDate(commCdGrpVO.getChgDate());

                commCdMngMapper.insertCdGrpLang(commCdGrpLangVO);

            }
        }

    }

    /**
     * 코드그룹 정보 수정
     */
    @Override
    public void updateCdGrp(CommCdGrpVO commCdGrpVO) {

        int uptCnt = commCdMngMapper.updateCdGrp(commCdGrpVO);

        if (uptCnt == 1) {

            List<CommCdGrpLangVO> commCdGrpLangVOList = commCdGrpVO.getCommCdGrpLangVOList();

            for (CommCdGrpLangVO commCdGrpLangVO : commCdGrpLangVOList) {

                commCdGrpLangVO.setCommCdGrp(commCdGrpVO.getCommCdGrp());
                commCdGrpLangVO.setInptMenuId(commCdGrpVO.getInptMenuId());
                commCdGrpLangVO.setRegrId(commCdGrpVO.getRegrId());
                commCdGrpLangVO.setRegDate(commCdGrpVO.getRegDate());
                commCdGrpLangVO.setChgrId(commCdGrpVO.getChgrId());
                commCdGrpLangVO.setChgDate(commCdGrpVO.getChgDate());

                int uptLngCnt = commCdMngMapper.updateCdGrpLang(commCdGrpLangVO);

                if (uptLngCnt == 0) {
                    commCdMngMapper.insertCdGrpLang(commCdGrpLangVO);
                }

            }

        }

    }

    /**
     * 코드그룹 정보 조회
     */
    @Override
    public CommCdGrpVO getCdGrpInfo(CommCdGrpVO commCdGrpVO) {
        return commCdMngMapper.getCdGrpInfo(commCdGrpVO);
    }

    /**
     * 공통코드 그룹 삭제
     */
    @Override
    public void deleteCdGrp(CommCdGrpVO commCdGrpVO) {
        commCdMngMapper.deleteCd(commCdGrpVO);
        commCdMngMapper.deleteCdLang(commCdGrpVO);
        commCdMngMapper.deleteCdGrp(commCdGrpVO);
        commCdMngMapper.deleteCdGrpLang(commCdGrpVO);
    }

}
