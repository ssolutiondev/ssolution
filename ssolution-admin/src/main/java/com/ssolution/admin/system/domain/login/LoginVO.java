package com.ssolution.admin.system.domain.login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginVO{

    private String userId;

    private String userNm;

    private String pswd;

    private String userTp;

    private String permIpBand;

    private String pswdChgPlnDt;

    private String lockYn;

    private int loginFailCnt;


}
