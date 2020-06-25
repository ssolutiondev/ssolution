package com.ssolution.admin.system.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.domain.manage.PasswordChgVO;
import com.ssolution.admin.system.domain.manage.ManagerMngAdminVO;
import com.ssolution.admin.system.mapper.manage.UserMngAdminMapper;
import com.ssolution.admin.system.service.manager.ManagerMngAdminService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.admin.system.util.PasswordUtil;
import com.ssolution.core.exception.ServiceException;

import java.util.List;

@Service
public class ManagerMngAdminServiceImpl implements ManagerMngAdminService {

    @Autowired
    private UserMngAdminMapper userMngAdminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //기본 패스워드
    public static final String DEF_PASS = "hh00700!";

    @Override
    public List<ManagerMngAdminVO> getUserInfoList(ManagerMngAdminVO user) {

        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        user.setToday(DateUtil.getDateStringYYYYMMDD(0));
        user.setRegDate(DateUtil.sysdate());

        return userMngAdminMapper.getUserInfoList(user, sessionUser.getSoAuthList());
    }

    @Override
    public ManagerMngAdminVO getUserInfo(String userId) {
        return userMngAdminMapper.getUserInfo(userId);
    }

    @Override
    public int insertUserInfo(ManagerMngAdminVO user){
        int check = userMngAdminMapper.checkUserId(user);

        if (check > 0) {
            throw new ServiceException("MSG.M14.MSG00018"); //기존 사용자 존재
        }

        // 패스워드 규칙을 체크 한다.
        if(!PasswordUtil.checkPassword(user.getPswd()))
            throw new ServiceException("MSG.M13.MSG00075");


        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        user.setToday(DateUtil.getDateStringYYYYMMDD(0));
        user.setRegDate(DateUtil.sysdate());
        user.setRegrId(sessionUser.getUserId());
        user.setPswd(passwordEncoder.encode(user.getPswd()));
        user.setLoginFailCnt(0);

        userMngAdminMapper.insertUserInfo(user);

        // 기본 사용자 그룹에 사용자 ID추가
        if (userMngAdminMapper.userAuthCount(user) == 0) {
            userMngAdminMapper.insertUserAuth(user);
        }

        // 사업 권한에 소속 조직의 SO 추가
        String soId = userMngAdminMapper.getUserSoId(user, user.getToday());
        user.setSoId(soId);
        userMngAdminMapper.insertSoAuth(user);

        return 1;
    }

    @Override
    public int updateUserInfo(ManagerMngAdminVO user){
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        user.setToday(DateUtil.getDateStringYYYYMMDD(0));
        user.setChgDate(DateUtil.sysdate());
        user.setChgrId(sessionUser.getUserId());

        //현재 Password 조회
        ManagerMngAdminVO password = userMngAdminMapper.getUserInfo(user.getUserId());
        if ("PASSWORD_INIT".equals(user.getPswd())) {
            user.setPswd(passwordEncoder.encode(DEF_PASS));
            user.setPswdChgPlnDt(DateUtil.getDateStringYYYYMMDD(7));
            user.setLockYn("N");
        } else {
            user.setPswd(password.getPswd());
            user.setPswd1(password.getPswd1());
            user.setPswd2(password.getPswd2());
        }

        if (userMngAdminMapper.userAuthCount(user) == 0) { // 기본 사용자 그룹에 사용자 ID추가
            userMngAdminMapper.insertUserAuth(user);
        }

        return userMngAdminMapper.updateUserInfo(user);
    }

    @Override
    public int checkUserId(ManagerMngAdminVO user) {
        int check = userMngAdminMapper.checkUserId(user);
        return check;
    }

    @Override
    public int updatePassword(String userId, PasswordChgVO passwordChgVO) {
        ManagerMngAdminVO userInfo = getUserInfo(userId);
        //현재 패스워드 일치 체크 한다.
        boolean isMatchPswd = passwordEncoder.matches(passwordChgVO.getCurrPswd(), userInfo.getPswd());
        if(isMatchPswd == false)
            throw new ServiceException("MSG.M14.MSG00038");

        // 변경 패스워드와 확인 패스워드 일치 여부 체크 한다.
        if(passwordChgVO.getChgPswd().equals(passwordChgVO.getChgPswdConfirm()) == false)
            throw new ServiceException("MSG.M06.MSG00043");

        // 변경할 패스워드가 현재 패스워드와 일치 하는지 체크 한다.
        boolean isMatchchgPswd = passwordEncoder.matches(passwordChgVO.getChgPswd(), userInfo.getPswd());
        if(isMatchchgPswd)
            throw new ServiceException("MSG.M14.MSG00037");

        // 변경할 패스워드가 직전 패스워드와 일치 하는지 체크 한다.
        boolean isMatchchgPreviosPswd = passwordEncoder.matches(passwordChgVO.getChgPswd(), userInfo.getPswd1());
        if(isMatchchgPreviosPswd)
            throw new ServiceException("MSG.M09.MSG00066");

        // 패스워드 규칙을 체크 한다.
        if(!PasswordUtil.checkPassword(passwordChgVO.getChgPswdConfirm()))
            throw new ServiceException("MSG.M13.MSG00075");

        // 패스워드 변경 처리를 한다.
        userInfo.setPswd2(userInfo.getPswd1());
        userInfo.setPswd1(userInfo.getPswd());
        userInfo.setPswd(passwordEncoder.encode(passwordChgVO.getChgPswd()));
        userInfo.setLoginFailCnt(0);
        userInfo.setPswdChgPlnDt(DateUtil.addDayYYYYMMDD(DateUtil.getDateStringYYYYMMDD(0), userInfo.getPswdChgCycl()));
        userInfo.setLockYn("N");
        userInfo.setChgrId(userId);
        userInfo.setChgDate(DateUtil.sysdate());

        int chgCnt = userMngAdminMapper.updatePassword(userInfo);
        return chgCnt;
    }
}