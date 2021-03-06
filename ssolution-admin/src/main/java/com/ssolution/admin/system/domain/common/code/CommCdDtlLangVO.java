package com.ssolution.admin.system.domain.common.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommCdDtlLangVO extends PagingValueVO {
    private String commCdGrp;
    private String commCd;
    private String langTp;
    private String commNm;
    private String inptMenuId;
    private String regrId;
    private Date regDate;
    private String chgrId;
    private Date chgDate;

}
