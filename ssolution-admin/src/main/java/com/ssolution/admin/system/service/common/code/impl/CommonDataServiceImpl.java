package com.ssolution.admin.system.service.common.code.impl;

import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;
import com.ssolution.admin.system.domain.common.code.CommonDataVO;
import com.ssolution.admin.system.mapper.common.code.CommonDataMapper;
import com.ssolution.admin.system.service.common.code.CommonDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonDataServiceImpl implements CommonDataService {

    /** CommonDataMapper Autowired. */
    @Autowired
    private CommonDataMapper commonDataMapper;

    @Override
    public CommonDataVO getCommonCode(String grpCd, String code, String lng) {
        return commonDataMapper.getCommonCode(grpCd, code, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeList(String grpCd, String lng) {
        return commonDataMapper.getCommonCodeList(grpCd, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListByRef1(String grpCd, String ref1, String lng) {
        return commonDataMapper.getCommonCodeListByRef1(grpCd, ref1, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListByRef2(String grpCd, String ref2, String lng) {
        return commonDataMapper.getCommonCodeListByRef2(grpCd, ref2, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListByRef3(String grpCd, String ref3, String lng) {
        return commonDataMapper.getCommonCodeListByRef3(grpCd, ref3, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListByRef4(String grpCd, String ref4, String lng) {
        return commonDataMapper.getCommonCodeListByRef4(grpCd, ref4, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListOptionalSearch(String grpCd, String lng) {
        List<CommonDataVO> orgCodeList = commonDataMapper.getCommonCodeList(grpCd, lng);

        if (orgCodeList.size() == 0)
            return orgCodeList;

        StringBuilder name = new StringBuilder();
        name.append("::: ");
        name.append(MessageUtil.getMessage("LAB.M09.LAB00063"));
        name.append(" :::");

        List<CommonDataVO> codeList = new ArrayList<CommonDataVO>();
        CommonDataVO hearderCode = new CommonDataVO();
        hearderCode.setCommCdGrp(grpCd);
        hearderCode.setCommCd("ALL");
        hearderCode.setCommNm(name.toString()); //전체
        codeList.add(hearderCode);
        codeList.addAll(orgCodeList);
        return codeList;
    }

    @Override
    public List<CommonDataVO> getCommonCodeListMandatorySearch(String grpCd, String lng) {
        List<CommonDataVO> orgCodeList = commonDataMapper.getCommonCodeList(grpCd, lng);

        if (orgCodeList.size() == 0)
            return orgCodeList;

        StringBuilder name = new StringBuilder();
        name.append("::: ");
        name.append(MessageUtil.getMessage("LAB.M07.LAB00195"));
        name.append(" :::");

        List<CommonDataVO> codeList = new ArrayList<CommonDataVO>();
        CommonDataVO hearderCode = new CommonDataVO();
        hearderCode.setCommCdGrp(grpCd);
        hearderCode.setCommCd("SEL");
        hearderCode.setCommNm(name.toString()); //선택
        codeList.add(hearderCode);
        codeList.addAll(orgCodeList);
        return codeList;
    }

    @Override
    public List<CommonDataVO> getCommonCodeListUseDefaultSearch(String grpCd, String lng) {
        return commonDataMapper.getCommonCodeListDefaultUse(grpCd, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListInput(String grpCd, String lng) {
        List<CommonDataVO> orgCodeList = commonDataMapper.getCommonCodeList(grpCd, lng);

        if (orgCodeList.size() == 0)
            return orgCodeList;

        StringBuilder name = new StringBuilder();
        name.append("::: ");
        name.append(MessageUtil.getMessage("LAB.M07.LAB00195"));
        name.append(" :::");

        List<CommonDataVO> codeList = new ArrayList<CommonDataVO>();
        CommonDataVO hearderCode = new CommonDataVO();
        hearderCode.setCommCdGrp(grpCd);
        hearderCode.setCommCd("");
        hearderCode.setCommNm(name.toString()); //선택
        codeList.add(hearderCode);
        codeList.addAll(orgCodeList);
        return codeList;
    }

    @Override
    public List<CommCdGrpVO> getCommonGrpList(String codeType, String lng) {
        return commonDataMapper.getCommonGrpList(codeType, lng);
    }

    @Override
    public List<CommonDataVO> getCommonCodeListInput(String grpCd, String ref1, String lng) {
        List<CommonDataVO> orgCodeList = commonDataMapper.getCommonCodeListByRef1(grpCd, ref1, lng);

        if (orgCodeList.size() == 0)
            return orgCodeList;

        StringBuilder name = new StringBuilder();
        name.append("::: ");
        name.append(MessageUtil.getMessage("LAB.M07.LAB00195"));
        name.append(" :::");

        List<CommonDataVO> codeList = new ArrayList<CommonDataVO>();
        CommonDataVO hearderCode = new CommonDataVO();
        hearderCode.setCommCdGrp(grpCd);
        hearderCode.setCommCd("");
        hearderCode.setCommNm(name.toString()); //선택
        codeList.add(hearderCode);
        codeList.addAll(orgCodeList);
        return codeList;
    }

    @Override
    public CommCdGrpVO getCommonGrp(String commCdGrp, String lng) {
        return commonDataMapper.getCommonGrp(commCdGrp, lng);
    }
}
