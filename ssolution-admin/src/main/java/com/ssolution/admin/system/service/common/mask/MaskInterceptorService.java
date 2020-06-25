package com.ssolution.admin.system.service.common.mask;

import java.util.List;
import java.util.Map;

import com.ssolution.admin.system.domain.common.excel.ExcelRowVO;
import com.ssolution.admin.system.domain.common.mask.MaskRuleVO;

/**
 * <PRE>
 * 1. FileName	: MaskInterceptorService.java
 * 2. Package	: com.ssolution.admin.system.service.common.mask
 * 3. Comment	: 마스킹 처리 룰 조회
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:42:16
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
public interface MaskInterceptorService {

    /**
     * <PRE>
     * 1. MethodName: getMaskRuleList
     * 2. ClassName : MaskInterceptorService
     * 3. Comment   : 사업자의 마스킹 룰 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 3. 9. 오후 3:11:28
     * </PRE>
     * 
     * @return Map<String,MaskRule> 해당 사업자의 마스킹 룰
     * @param soId 사업ID
     * @return
     */
    public Map<String, MaskRuleVO> getMaskRuleList(String soId);

    /**
     * <PRE>
     * 1. MethodName: processMask
     * 2. ClassName : MaskInterceptorService
     * 3. Comment   : 마스킹 처리
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 1. 12. 오후 3:28:23
     * </PRE>
     * 
     * @param soId     사업ID
     * @param isUnmask 마스킹 해제 여부
     * @param param    데이터
     */
    public void processMask(String soId, boolean isUnmask, Map<String, Object> param);

    public void processMaskList(String soId, boolean isUnmask, List<Map<String, Object>> param);

    /**
     * <PRE>
     * 1. MethodName: processExcelMask
     * 2. ClassName : MaskInterceptorService
     * 3. Comment   :
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 1. 12. 오후 3:28:51
     * </PRE>
     * 
     * @param soId     사업ID
     * @param isUnmask 마스킹 해제 여부
     * @param row      Excel Row Data
     */
    public void processExcelMask(String soId, boolean isUnmask, List<ExcelRowVO> rows);

    /**
     * <PRE>
     * 1. MethodName: getExcludedList
     * 2. ClassName : MaskInterceptorService
     * 3. Comment   : 재외 URL 리스트
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 1. 17. 오후 8:49:31
     * </PRE>
     * 
     * @return List<String> 제외 URL 리스트
     */
    public List<String> getExcludedList();

}
