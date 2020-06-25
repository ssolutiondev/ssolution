package com.ssolution.admin.system.service.common.main;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: MainService.java
 * 2. Package	: com.ssolution.admin.system.service.common.main
 * 3. Comment	: 메인 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:41:57
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface MainService {

    List<String> getBaseDt();

    List<String> getRcptStat(List<String> baseDtList);

    List<String> getRcptCmplStat(List<String> baseDtList);

    List<String> getNewRcptStat(List<String> baseDtList);

    List<String> getNewCmplStat(List<String> baseDtList);

    List<String> getChgRcptStat(List<String> baseDtList);

    List<String> getChgCmplStat(List<String> baseDtList);


}
