package com.ssolution.admin.system.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.GrpReqMngVO;
import com.ssolution.admin.system.mapper.manage.GrpReqMngMapper;
import com.ssolution.admin.system.service.manager.GrpReqMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: GrpReqMngServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.manager.impl
 * 3. Comment	: 사용자 그룹 등록 관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:46:27
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class GrpReqMngServiceImpl implements GrpReqMngService {

    @Autowired
    GrpReqMngMapper grpReqMngMapper;

    @Override
    public List<GrpReqMngVO> getGrpReqList(GrpReqMngVO grpReqMngVO) {
        return grpReqMngMapper.getGrpReqList(grpReqMngVO);
    }

    @Override
    public int insertGrpReqInfo(GrpReqMngVO grpReqMngVO) {

        SessionUserVO session_user = CommonUtil.getSessionManager();
        grpReqMngVO.setRegrId(session_user.getUserId());
        grpReqMngVO.setReqUserId(session_user.getUserId());
        grpReqMngVO.setRegDate(DateUtil.sysdate());
        grpReqMngVO.setReqDt(DateUtil.getDateStringYYYYMMDD(0));
        grpReqMngVO.setReqDate(DateUtil.sysdate());

        return grpReqMngMapper.insertGrpReqInfo(grpReqMngVO);
    }

    @Override
    public int updateGrpReqInfo(GrpReqMngVO grpReqMngVO) {
        SessionUserVO session_user = CommonUtil.getSessionManager();
        grpReqMngVO.setProcUserId(session_user.getUserId());
        grpReqMngVO.setProcDate(DateUtil.sysdate());
        grpReqMngVO.setChgrId(session_user.getUserId());
        grpReqMngVO.setChgDate(DateUtil.sysdate());

        return grpReqMngMapper.updateGrpReqInfo(grpReqMngVO);
    }

}
