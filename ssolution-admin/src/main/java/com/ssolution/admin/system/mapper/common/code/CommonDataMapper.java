package com.ssolution.admin.system.mapper.common.code;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;
import com.ssolution.admin.system.domain.common.code.CommonDataVO;

import java.util.List;

/**
 * <PRE>
 * 1. FileName	: CommonDataMapper.java
 * 2. Package	: com.ssolution.admin.system.mapper.common.code
 * 3. Comment	: 공통코드 Mapper
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 11:01:13
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Component
public interface CommonDataMapper {

    /**
     * <PRE>
     * 1. MethodName: getCommonGrp
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 그룹 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 11. 17. 오전 10:51:02
     * </PRE>
     *
     * @return List<CommCdGrpVO> 그룹정보
     * @param codeType 시스템 ID
     * @param lng      언어유형
     * @return
     */
    List<CommCdGrpVO> getCommonGrpList(@Param(value = "codeType") String codeType, @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonGrp
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 그룹 조회
     * 4. 작성자    : JSW
     * 5. 작성일    : 2018. 09. 07.
     * </PRE>
     *
     * @return CommCdGrpVO 그룹정보
     * @param commCdGrp 공통코드 그룹
     * @param lng       언어유형
     */
    CommCdGrpVO getCommonGrp(@Param(value = "commCdGrp") String commCdGrp, @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCode
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:51:19
     * </PRE>
     *
     * @return CommonDataVO 공통코드정보
     * @param grpCd 공통코드그룹
     * @param code  코드
     * @param lng   언어코드
     */
    CommonDataVO getCommonCode(@Param(value = "grpCd") String grpCd,
                               @Param(value = "code") String code,
                               @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeList
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:51:45
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드 리스트
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    List<CommonDataVO> getCommonCodeList(@Param(value = "grpCd") String grpCd, @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListDefaultUse
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 리스트 조회
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:51:45
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드 리스트
     * @param grpCd 공통코드그룹
     * @param lng   언어코드
     */
    List<CommonDataVO> getCommonCodeListDefaultUse(@Param(value = "grpCd") String grpCd,
                                                   @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef1
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 리스트 조회(참조1)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:52:15
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드 리스트
     * @param grpCd 공통코드그룹
     * @param ref1  참조1
     * @param lng   언어코드
     */
    List<CommonDataVO> getCommonCodeListByRef1(@Param(value = "grpCd") String grpCd,
                                               @Param(value = "ref1") String ref1,
                                               @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef2
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 리스트 조회(참조2)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:52:15
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드 리스트
     * @param grpCd 공통코드그룹
     * @param ref2  참조2
     * @param lng   언어코드
     */
    List<CommonDataVO> getCommonCodeListByRef2(@Param(value = "grpCd") String grpCd,
                                               @Param(value = "ref2") String ref2,
                                               @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef3
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 리스트 조회(참조3)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:52:15
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드 리스트
     * @param grpCd 공통코드그룹
     * @param ref3  참조3
     * @param lng   언어코드
     */
    List<CommonDataVO> getCommonCodeListByRef3(@Param(value = "grpCd") String grpCd,
                                               @Param(value = "ref3") String ref3,
                                               @Param(value = "lng") String lng);

    /**
     * <PRE>
     * 1. MethodName: getCommonCodeListByRef4
     * 2. ClassName : CommonDataMapper
     * 3. Comment   : 공통코드 리스트 조회(참조4)
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2017. 10. 23. 오후 8:52:15
     * </PRE>
     *
     * @return List<CommonDataVO> 공통코드 리스트
     * @param grpCd 공통코드그룹
     * @param ref4  참조4
     * @param lng   언어코드
     */
    List<CommonDataVO> getCommonCodeListByRef4(@Param(value = "grpCd") String grpCd,
                                               @Param(value = "ref4") String ref4,
                                               @Param(value = "lng") String lng);

}
