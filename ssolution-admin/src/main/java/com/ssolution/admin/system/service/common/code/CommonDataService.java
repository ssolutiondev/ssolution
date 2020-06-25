package com.ssolution.admin.system.service.common.code;

import java.util.List;

import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;
import com.ssolution.admin.system.domain.common.code.CommonDataVO;

public interface CommonDataService {

    /**
     * <PRE>
     * 1. MethodName: getCommonGrpList
     * 2. ClassName : CommonDataService
     * 3. Comment   : 공통코드 그룹 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 11. 17. 오전 10:49:51
     * </PRE>
     *
     * @return List<CommCdGrpVO> 그룹리스트
     * @param codeType 시스템ID
     * @param lng      언어유형
     * @return
     */
    List<CommCdGrpVO> getCommonGrpList(String codeType, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCode
     * 2. ClassName : ChargeService
     * 3. Comment   공통코드 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 4. 28. 오전 10:16:32
     * </PRE>
     *
     * @return CommonCodeVO 공통코드VO
     * @param grpCd 공통코드그룹
     * @param code  상세코드
     * @param lng   언어코드
     */
    public CommonDataVO getCommonCode(String grpCd, String code, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListOptionalSearch
     * 2. ClassName : CommonDataService
     * 3. Comment   : 공통코드리스트 조회 - 조회 조건에서 필수값이 아닌 선택 값에 사용
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 24. 오전 10:57:02
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드VO
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListOptionalSearch(String grpCd, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListMandatorySearch
     * 2. ClassName : CommonDataService
     * 3. Comment   : 공통코드리스트 조회 - 조회 조건에서 필수값인 경우 사용
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 24. 오전 10:57:33
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드VO
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListMandatorySearch(String grpCd, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListUseDefaultSearch
     * 2. ClassName : CommonDataService
     * 3. Comment   : 공통코드리스트 조회  - 조회 조건에서 기본값 설정(사용여부 등)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 24. 오전 10:57:54
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드VO
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListUseDefaultSearch(String grpCd, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListInput
     * 2. ClassName : CommonDataService
     * 3. Comment   : 공통코드리스트 조회 - 입력 필드에서 사용
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 24. 오전 10:58:57
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드VO
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListInput(String grpCd, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListInput
     * 2. ClassName : CommonDataService
     * 3. Comment   : 공통코드리스트 조회 - 입력 필드에서 사용(REF필터)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2018. 1. 3. 오후 9:31:45
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드VO
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListInput(String grpCd, String ref1, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeList
     * 2. ClassName : ChargeService
     * 3. Comment   : 공통코드 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 4. 28. 오전 10:55:45
     * </PRE>
     *
     * @return List<CommonCodeVO> 공통코드그룹리스트
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeList(String grpCd, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef1
     * 2. ClassName : ChargeService
     * 3. Comment   : 공통코드리스트 조회(참조1)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 5. 9. 오후 3:54:39
     * </PRE>
     *
     * @return List<CommonCodeVO> 공통코드그룹리스트
     * @param grpCd 공통코드그룹
     * @param ref1  참조1
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListByRef1(String grpCd, String ref1, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef2
     * 2. ClassName : ChargeService
     * 3. Comment   :공통코드리스트 조회(참조2)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 5. 9. 오후 3:54:40
     * </PRE>
     *
     * @return List<CommonCodeVO> 공통코드그룹리스트
     * @param grpCd 공통코드그룹
     * @param ref2  참조2
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListByRef2(String grpCd, String ref2, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef3
     * 2. ClassName : ChargeService
     * 3. Comment   : 공통코드리스트 조회(참조3)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 5. 9. 오후 3:54:42
     * </PRE>
     *
     * @return List<CommonCodeVO> 공통코드그룹리스트
     * @param grpCd 공통코드그룹
     * @param ref3  참조3
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListByRef3(String grpCd, String ref3, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef4
     * 2. ClassName : ChargeService
     * 3. Comment   : 공통코드리스트 조회(참조4)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 5. 9. 오후 3:54:44
     * </PRE>
     *
     * @return List<CommonCodeVO> 공통코드그룹리스트
     * @param grpCd 공통코드그룹
     * @param ref4  참조4
     * @param lng   언어코드
     */
    public List<CommonDataVO> getCommonCodeListByRef4(String grpCd, String ref4, String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonGrp
     * 2. ClassName : ChargeService
     * 3. Comment   : 공통코드 그룹 조회
     * 4. 작성자    : JSW
     * 5. 작성일    : 2018. 09. 07.
     * </PRE>
     *
     * @return CommCdGrpVO 그룹정보
     * @param commCdGrp 공통코드 그룹
     * @param lng       언어유형
     */
    CommCdGrpVO getCommonGrp(String commCdGrp, String lng);

}
