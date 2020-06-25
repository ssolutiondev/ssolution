package com.ssolution.admin.system.service.common.popup.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.common.popup.UserGroupPopupVO;
import com.ssolution.admin.system.mapper.common.popup.UserGroupPopupMapper;
import com.ssolution.admin.system.service.common.popup.UserGroupPopupService;

import java.util.List;

@Service
public class UserGroupPopupServiceImpl implements UserGroupPopupService {
    @Autowired
    private UserGroupPopupMapper userGroupPopupMapper;

    @Override
    public List<UserGroupPopupVO> userGroupList(UserGroupPopupVO userGroup) {
        List<UserGroupPopupVO> userGroupList = userGroupPopupMapper.userGroupList(userGroup);
        return userGroupList;
    }
}