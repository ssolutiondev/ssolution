package com.ssolution.admin.system.domain.common.logging;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class MenuConnLoggingVO extends PagingValueVO {

    private String userId;
    private String acsDt;
    private String acsTm;
    private String sessionId;
    private String lvl1MenuId;
    private String lvl2MenuId;
    private String lvl3MenuId;
    private String lvl4MenuId;
    private String menuAcsUrl;
    private String userNm;
    private String orgId;
    private String orgNm;
    private String startDt;
    private String endDt;
    private String lvl1MenuNm;
    private String lvl2MenuNm;
    private String lvl3MenuNm;
    private String lvl4MenuNm;
}
