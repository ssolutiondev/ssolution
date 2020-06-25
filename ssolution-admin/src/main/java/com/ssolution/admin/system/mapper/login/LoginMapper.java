package com.ssolution.admin.system.mapper.login;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssolution.admin.system.domain.login.LoginVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginMapper {

    LoginVO getLoginUser(@Param("userId") String userId);

    int updateFailCount(@Param("userId") String userId);

    int updateAccountLock(@Param("userId") String userId);

    SessionUserVO getSessionUser(@Param("userId") String userId);

    List<String> getAuthGrpList(@Param("userId") String userId);

    Map<String, Object> getOrgInfo(@Param("userId") String userId, @Param("today")String dateStringYYYYMMDD);

    List<Map<String, Object>> getSoAuthList(@Param("userId") String userId);

    Map<String, String> getLoginDate(@Param("userId") String userId);

    int updateLastLoginDateTime(@Param("userId") String userId);
}
