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
public class CommCdDtlVO extends PagingValueVO {

    private String commCdGrp;
    private String commCd;
    private String commNm;
    private String refCd1;
    private String refCd2;
    private String refCd3;
    private String refCd4;
    private String defltYn;
    private String rmrk;
    private String sortNo;
    private String useYn;
    private String sysUseYn;
    private String inptMenuId;
    private String regrId;
    private Date regDate;
    private String chgrId;
    private Date chgDate;

    private String regrNm;
    private String chgrNm;

    private String commCdGrpNm;
    private List<CommCdDtlLangVO> paramLngList;
    private List<Map<String, Object>> codeLngList;
}
