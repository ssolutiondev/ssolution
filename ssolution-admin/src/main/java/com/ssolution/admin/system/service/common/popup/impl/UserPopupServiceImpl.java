package com.ssolution.admin.system.service.common.popup.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.common.popup.UserPopupVO;
import com.ssolution.admin.system.domain.manage.ManagerMngAdminVO;
import com.ssolution.admin.system.mapper.common.popup.UserPopupMapper;
import com.ssolution.admin.system.service.common.popup.UserPopupService;

import java.util.List;

@Service
public class UserPopupServiceImpl implements UserPopupService {

    @Autowired
    private UserPopupMapper userPopupMapper;


    @Override
    public List<ManagerMngAdminVO> getUserListPopup(UserPopupVO userPopup) {

        // 사용자 목록 조회(For Popup)
        List<ManagerMngAdminVO> userList = userPopupMapper.getUserListPopup(userPopup);
        return userList;
    }

    @Override
    public List<ManagerMngAdminVO> getUserUppOrg(UserPopupVO userPopup) {

        // 사용자 목록 조회(For Popup)
        List<ManagerMngAdminVO> userList = userPopupMapper.getUserUppOrg(userPopup);
        return userList;
    }
}