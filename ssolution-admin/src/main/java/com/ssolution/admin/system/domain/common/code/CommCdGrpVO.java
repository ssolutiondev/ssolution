package com.ssolution.admin.system.domain.common.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommCdGrpVO extends PagingValueVO {

    private String commCdGrp;
    private String commCdGrpNm;
    private String sysId;
    private String len;
    private String refCd1;
    private String refCd2;
    private String refCd3;
    private String rmrk;
    private String useYn;
    private String sysUseYn;
    private String inptMenuId;
    private String regrId;
    private Date regDate;
    private String chgrId;
    private Date chgDate;
    private List<CommCdGrpLangVO> commCdGrpLangVOList;
    private List<Map<String, Object>> codeLngList;


}
