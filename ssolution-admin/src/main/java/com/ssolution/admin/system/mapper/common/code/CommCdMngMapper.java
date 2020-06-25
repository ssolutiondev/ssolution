package com.ssolution.admin.system.mapper.common.code;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.ssolution.admin.system.domain.common.code.CommCdDtlLangVO;
import com.ssolution.admin.system.domain.common.code.CommCdDtlVO;
import com.ssolution.admin.system.domain.common.code.CommCdGrpLangVO;
import com.ssolution.admin.system.domain.common.code.CommCdGrpVO;

import java.util.List;
import java.util.Map;


@Component
public interface CommCdMngMapper {

    /**
     *
     * <PRE>
     * 1. MethodName: getCdGrpList
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 9:02:14
     * </PRE>
     * 
     * @return List<CommCdGrpVO>
     * @param codeType
     * @param lng
     * @return
     */
    List<CommCdGrpVO> getCdGrpList(@Param(value = "codeType") String codeType, @Param(value = "lng") String lng);

    /**
     *
     * <PRE>
     * 1. MethodName: getCommCdList
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 9:02:34
     * </PRE>
     * 
     * @return List<CommCdGrpVO>
     * @param condGroupId
     * @param lng
     * @return
     */
    List<CommCdDtlVO> getCommCdList(@Param(value = "condGroupId") String condGroupId, @Param(value = "lng") String lng);

    /**
     *
     * <PRE>
     * 1. MethodName: getCdDtlLngList
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 언어 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 9:30:41
     * </PRE>
     * 
     * @return List<Map<String,Object>>
     * @param commGrp
     * @param commCd
     * @return
     */
    List<Map<String, Object>> getCdDtlLngList(@Param(value = "commCdGrp") String commCdGrp,
                                              @Param(value = "commCd") String commCd);

    /**
     *
     * <PRE>
     * 1. MethodName: getLngList
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 빈언어 리스트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:13:25
     * </PRE>
     * 
     * @return List<Map<String,Object>>
     * @return
     */
    List<Map<String, Object>> getLngList();

    /**
     *
     * <PRE>
     * 1. MethodName: getCdDtlCnt
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 카운트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:17:32
     * </PRE>
     * 
     * @return int
     * @param commonGrp
     * @param commonCd
     * @return
     */
    int getCdDtlCnt(@Param(value = "commCdGrp") String commCdGrp, @Param(value = "commCd") String commonCd);

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdDtl
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:18:56
     * </PRE>
     * 
     * @return int
     * @param commCdDtlVO
     * @return
     */
    int insertCdDtl(@Param(value = "commCdDtlVO") CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdDtlLng
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 언어 정보 추가
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오전 10:22:03
     * </PRE>
     * 
     * @return int
     * @param commCdDtlLangVO
     * @return
     */
    int insertCdDtlLang(@Param(value = "commCdDtlLangVO") CommCdDtlLangVO commCdDtlLangVO);

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdDtl
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 3:31:38
     * </PRE>
     * 
     * @return int
     * @param commCdDtlVO
     * @return
     */
    int updateCdDtl(@Param(value = "commCdDtlVO") CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdDtlLang
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 언어 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 3:58:22
     * </PRE>
     * 
     * @return int
     * @param commCdDtlLangVO
     * @return
     */
    int updateCdDtlLang(@Param(value = "commCdDtlLangVO") CommCdDtlLangVO commCdDtlLangVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdDtl
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 샂제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 4:14:00
     * </PRE>
     * 
     * @return int
     * @param commCdDtlVO
     * @return
     */
    int deleteCdDtl(@Param(value = "commCdDtlVO") CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdDtlLng
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 상세 언어 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 4:14:10
     * </PRE>
     * 
     * @return int
     * @param commCdDtlVO
     * @return
     */
    int deleteCdDtlLng(@Param(value = "commCdDtlVO") CommCdDtlVO commCdDtlVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getCdGrpCnt
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 카운트 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:12:02
     * </PRE>
     * 
     * @return int
     * @param commCdGrp
     * @return
     */
    int getCdGrpCnt(@Param(value = "commCdGrp") String commCdGrp);

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdGrp
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:15:16
     * </PRE>
     * 
     * @return int
     * @param commCdGrpVO
     * @return
     */
    int insertCdGrp(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: insertCdGrpLang
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 언어 정보 등록
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:20:06
     * </PRE>
     * 
     * @return int
     * @param commCdGrpLangVO
     * @return
     */
    int insertCdGrpLang(@Param(value = "commCdGrpLangVO") CommCdGrpLangVO commCdGrpLangVO);

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdGrp
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 정보 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:35:21
     * </PRE>
     * 
     * @return int
     * @param commCdGrpVO
     * @return
     */
    int updateCdGrp(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: updateCdGrpLang
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 언어 정보 수정
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 1:38:33
     * </PRE>
     * 
     * @return int
     * @param commCdGrpLangVO
     * @return
     */
    int updateCdGrpLang(@Param(value = "commCdGrpLangVO") CommCdGrpLangVO commCdGrpLangVO);

    /**
     *
     * <PRE>
     * 1. MethodName: getCdGrpInfo
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 정보 조회
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:18:40
     * </PRE>
     * 
     * @return CommCdGrpVO
     * @param commCdGrpVO
     * @return
     */
    CommCdGrpVO getCdGrpInfo(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdGrp
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:40:38
     * </PRE>
     * 
     * @return int
     * @param commCdGrpVO
     * @return
     */
    int deleteCdGrp(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdGrpLang
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 그룹 언어 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:40:47
     * </PRE>
     * 
     * @return int
     * @param commCdGrpVO
     * @return
     */
    int deleteCdGrpLang(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCd
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:40:59
     * </PRE>
     * 
     * @return int
     * @param commCdGrpVO
     * @return
     */
    int deleteCd(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);

    /**
     *
     * <PRE>
     * 1. MethodName: deleteCdLang
     * 2. ClassName : CommCdMngMapper
     * 3. Comment   : 공통코드 언어 삭제
     * 4. 작성자    : jhkim
     * 5. 작성일    : 2017. 10. 23. 오후 2:41:08
     * </PRE>
     * 
     * @return int
     * @param commCdGrpVO
     * @return
     */
    int deleteCdLang(@Param(value = "commCdGrpVO") CommCdGrpVO commCdGrpVO);
}
