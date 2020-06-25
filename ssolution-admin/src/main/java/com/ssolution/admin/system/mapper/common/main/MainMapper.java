package com.ssolution.admin.system.mapper.common.main;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface MainMapper {

    int getRcptCount(@Param("date") String date);

    int getRcptCmplCount(@Param("date") String date);

    int getNewRcptCount(@Param("date") String date);

    int getNewCmplCount(@Param("date") String date);

    int getChgRcptCount(@Param("date") String date);

    int getChgCmplCount(@Param("date") String date);

}