package com.ssolution.admin.system.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.GrpMngVO;
import com.ssolution.admin.system.mapper.manage.GrpMngMapper;
import com.ssolution.admin.system.service.manager.GrpMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.core.exception.ServiceException;

import java.util.List;


@Service
public class GrpMngServiceImpl implements GrpMngService {
    @Autowired
    private GrpMngMapper grpMngMapper;

    @Override
    public List<GrpMngVO> getGrpList(GrpMngVO grpMng) {
        return grpMngMapper.getGrpList(grpMng);
    }

    @Override
    public int checkGrpId(GrpMngVO grpMng) {
        return grpMngMapper.checkGrpId(grpMng);
    }

    @Override
    public int insertGrp(GrpMngVO grpMng) {
        int check = grpMngMapper.checkGrpId(grpMng);
        if (check > 0) {
            throw new ServiceException("MSG.M14.MSG00018");
        }

        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        grpMng.setRegDate(DateUtil.sysdate());
        grpMng.setRegrId(sessionUser.getUserId());

        int insertCnt = grpMngMapper.insertGrp(grpMng);
        return insertCnt;
    }

    @Override
    public int updateGrp(GrpMngVO grpMng) {
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        grpMng.setChgDate(DateUtil.sysdate());
        grpMng.setChgrId(sessionUser.getUserId());

        int updateCnt = grpMngMapper.updateGrp(grpMng);
        return updateCnt;
    }

    @Override
    public int deleteGrp(GrpMngVO grpMng) {
        grpMngMapper.deleteGrpDtl(grpMng);
        grpMngMapper.deleteGrpUser(grpMng);
        int deleteCnt = grpMngMapper.deleteGrp(grpMng);
        return deleteCnt;
    }
}