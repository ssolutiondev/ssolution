package com.ssolution.admin.system.domain.common.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import com.ssolution.admin.system.domain.common.paging.PagingValueVO;

/**
 * <PRE>
 * 1. FileName	: CommonDataVO.java
 * 2. Package	: com.ssolution.admin.system.domain.common.code
 * 3. Comment	: 공통코드 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:01:27
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommonDataVO extends PagingValueVO {

    private String commCdGrp;
    private String commCdGrpNm;
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
    private String regrNm;
    private Date regDate;
    private String chgrId;
    private String chgrNm;
    private Date chgDate;
}
