package com.ssolution.admin.system.service.manager.impl;

import com.ssolution.admin.system.util.StringUtil;
import com.ssolution.admin.system.util.MessageUtil;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.ManagerAuthMngVO;
import com.ssolution.admin.system.mapper.manage.UserAuthMngMapper;
import com.ssolution.admin.system.service.manager.ManagerAuthMngService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.core.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerAuthMngServiceImpl implements ManagerAuthMngService {

    /** InquiryHistMapper Autowired. */
    @Autowired
    private UserAuthMngMapper userAuthMngMapper;
/*
    @Override
    public List<ManagerAuthMngVO> getUserList(ManagerAuthMngVO userAuth) {
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        return userAuthMngMapper.getUserList(userAuth, sessionUser.getSoAuthList(), DateUtil.getDateStringYYYYMMDD(0));
    }

    @Override
    public List<ManagerAuthMngVO> getUserAuthList(ManagerAuthMngVO userAuth) {
        if (StringUtil.isEmpty(userAuth.getUserId())) { //대상 사용자ID 필수값 체크
            String[] arg = { MessageUtil.getMessage("LAB.M07.LAB00067") };
            throw new ServiceException("MSG.M13.MSG00027", arg); // 필수값 체크
        }

        return userAuthMngMapper.getUserAuthList(userAuth);
    }

    @Override
    public void updateUserAuth(ManagerAuthMngVO userAuth) {
        if (StringUtil.isEmpty(userAuth.getUserId())) { //대상 사용자ID 필수값 체크
            String[] arg = { MessageUtil.getMessage("LAB.M07.LAB00067") };
            throw new ServiceException("MSG.M13.MSG00027", arg); // 필수값 체크
        }

        //권한정보삭제
        userAuthMngMapper.deleteAllAuth(userAuth);

        //선택 권한정보가 없으면 종료
        if (userAuth.getUpdateAuthIdList() == null || userAuth.getUpdateAuthIdList().size() == 0) {
            return;
        }

        SessionUserVO su = CommonUtil.getSessionManager();
        userAuth.setRegrId(su.getUserId());
        userAuth.setRegDate(DateUtil.sysdate());

        for (String authGrpId : userAuth.getUpdateAuthIdList()) {
            userAuthMngMapper.insertUserAuth(userAuth.getUserId(),
                                             authGrpId,
                                             userAuth.getInptMenuId(),
                                             userAuth.getRegrId(),
                                             userAuth.getRegDate());
        }
    }*/

	@Override
	public List<ManagerAuthMngVO> getManagerList(ManagerAuthMngVO managerAuth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManagerAuthMngVO> getManagerAuthList(ManagerAuthMngVO managerAuth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateManagerAuth(ManagerAuthMngVO managerAuth) {
		// TODO Auto-generated method stub
		
	}
}