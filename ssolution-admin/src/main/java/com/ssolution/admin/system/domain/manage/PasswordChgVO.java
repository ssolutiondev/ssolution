package com.ssolution.admin.system.domain.manage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <PRE>
 * 1. FileName	: PasswordChgVO.java
 * 2. Package	: com.ssolution.admin.system.domain.manage
 * 3. Comment	: 패스워드 변경 VO
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:38:03
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Getter
@Setter
@ToString
public class PasswordChgVO {
    private String currPswd;
    private String chgPswd;
    private String chgPswdConfirm;
}
