package com.ssolution.admin.system.service.common.mask.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.domain.common.code.CommonDataVO;
import com.ssolution.admin.system.domain.common.excel.ExcelCellVO;
import com.ssolution.admin.system.domain.common.excel.ExcelFileVO;
import com.ssolution.admin.system.domain.common.excel.ExcelRowVO;
import com.ssolution.admin.system.domain.common.mask.MaskRuleVO;
import com.ssolution.admin.system.domain.login.SessionUserVO;
import com.ssolution.admin.system.mapper.common.mask.MaskInterceptorMapper;
import com.ssolution.admin.system.service.common.code.CommonDataService;
import com.ssolution.admin.system.service.common.mask.MaskInterceptorService;
import com.ssolution.admin.system.util.CommonUtil;
import com.ssolution.admin.system.util.DateUtil;
import com.ssolution.admin.system.util.MaskUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class MaskInterceptorServiceImpl implements MaskInterceptorService{

    /** The logger. */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String PACKAGE = "com.ssolution.";

    @Autowired
    private MaskInterceptorMapper maskInterceptorMapper;

    @Autowired
    private CommonDataService commonDataService;


    @Autowired
    private HttpServletRequest request;

    private Map<String, Map<String, MaskRuleVO>> maskRule;

    /**
     * 제외 대상 URL
     */
    private List<String> excludedUrlList;

    @Override
    public Map<String, MaskRuleVO> getMaskRuleList(String soId) {
        if (maskRule == null) {
            return null;
        } else {
            return maskRule.get(soId);
        }
    }

    @Override
    public void processMask(String soId, boolean isUnmask, Map<String, Object> param) {

        //해당 사업자의 Rule정보가 없으면 통과
        Map<String, MaskRuleVO> maskRuleInfo = getMaskRuleList(soId);
        if (maskRuleInfo == null || maskRuleInfo.isEmpty()) {
            return;
        }

        // 슈퍼 권한을 가진 사용자의 경우 마스킹 처리 하지 않는다.
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        List<CommonDataVO> maskSuperUsers = commonDataService.getCommonCodeList("SY00033", lng);
        for (CommonDataVO code : maskSuperUsers) {
            if (code.getCommCd().equals(sessionUser.getUserId())) {
                return;
            }
        }

        //각 Model에서 Object 추출 후 대상 체크
        Set<String> keys = param.keySet();

        StringBuffer unmaskStringBuffer = new StringBuffer();
        for (String key : keys) {
            Object object = param.get(key);

            if (object == null)
                continue;

            String packageName = object.getClass().getPackage().getName();
            boolean isDomain = false;
            if (packageName != null &&
                (packageName.startsWith(PACKAGE) && packageName.indexOf(".domain") > 0)) {
                if (object instanceof ExcelFileVO) {
                    isDomain = false;
                } else {
                    isDomain = true;
                }

            }

            if (object instanceof String) {
                param.put(key, maskString(object, key, maskRuleInfo, isUnmask, unmaskStringBuffer));
            } else if (object instanceof Collection) {
                maskCollection(object, maskRuleInfo, isUnmask, unmaskStringBuffer);
            } else if (object instanceof Map) {
                maskMapObject(object, maskRuleInfo, isUnmask, unmaskStringBuffer);
            } else if (isDomain) {
                maskObject(object, maskRuleInfo, isUnmask, unmaskStringBuffer);
            }
        }

    }

    @Override
    public void processMaskList(String soId, boolean isUnmask, List<Map<String, Object>> paramList) {

        //해당 사업자의 Rule정보가 없으면 통과
        Map<String, MaskRuleVO> maskRuleInfo = getMaskRuleList(soId);
        if (maskRuleInfo == null || maskRuleInfo.isEmpty()) {
            return;
        }

        // 슈퍼 권한을 가진 사용자의 경우 마스킹 처리 하지 않는다.
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        List<CommonDataVO> maskSuperUsers = commonDataService.getCommonCodeList("SY00033", lng);
        for (CommonDataVO code : maskSuperUsers) {
            if (code.getCommCd().equals(sessionUser.getUserId())) {
                return;
            }
        }

        StringBuffer unmaskStringBuffer = new StringBuffer();
        for(Map<String, Object> param : paramList){
            Set<String> keys = param.keySet();
            for (String key : keys) {
                Object object = param.get(key);

                if (object == null)
                    continue;

                String packageName = object.getClass().getPackage().getName();

                boolean isDomain = false;
                if (packageName != null &&
                        (packageName.startsWith(PACKAGE) && packageName.indexOf(".domain") > 0)) {
                    if (object instanceof ExcelFileVO) {
                        isDomain = false;
                    } else {
                        isDomain = true;
                    }

                }

                if (object instanceof String) {
                    param.put(key, maskString(object, key, maskRuleInfo, isUnmask, unmaskStringBuffer));
                } else if (object instanceof Collection) {
                    maskCollection(object, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (object instanceof Map) {
                    maskMapObject(object, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (isDomain) {
                    maskObject(object, maskRuleInfo, isUnmask, unmaskStringBuffer);
                }
            }
        }

    }

    @Override
    public void processExcelMask(String soId, boolean isUnmask, List<ExcelRowVO> rows) {

        // 해당 사업자의 Rule정보가 없으면 통과
        Map<String, MaskRuleVO> maskRuleInfo = getMaskRuleList(soId);
        if (maskRuleInfo == null || maskRuleInfo.isEmpty()) {
            return;
        }
        if (rows == null || rows.size() == 0)
            return;

        // 슈퍼 권한을 가진 사용자의 경우 마스킹 처리 하지 않는다.
        String lng = (String) request.getSession().getAttribute("sessionLanguage");
        SessionUserVO sessionUser = CommonUtil.getSessionManager();
        List<CommonDataVO> maskSuperUsers = commonDataService.getCommonCodeList("SY00033", lng);
        for (CommonDataVO code : maskSuperUsers) {
            if (code.getCommCd().equals(sessionUser.getUserId())) {
                return;
            }
        }

        StringBuffer unmaskStringBuffer = new StringBuffer();
        for (ExcelRowVO row : rows) {
            // 대상 데이터가 없으면 통과
            if (row == null || row.getRowData() == null || row.getRowData().isEmpty()) {
                return;
            }

            Map<String, ExcelCellVO> rowData = row.getRowData();
            Set<String> keys = rowData.keySet();
            for (String key : keys) {
                ExcelCellVO cellData = rowData.get(key);
                if (cellData == null)
                    continue;
                if (cellData.getValue() instanceof String == false)
                    continue;

                cellData.setValue(maskString(cellData.getValue(), key, maskRuleInfo, isUnmask, unmaskStringBuffer));
            }
        }
    }


    @EventListener(ApplicationReadyEvent.class)
    public void loadRuleInfo() {
//        this.getMaskRule();
//        excludedUrlList = new ArrayList<String>();
//        List<CommonDataVO> urlList = commonDataService.getCommonCodeList("SY00029", "ko");
//        for (CommonDataVO data : urlList) {
//            excludedUrlList.add(data.getRefCd1());
//        }
    }

    private void getMaskRule() {
        if (maskRule != null) {
            maskRule.clear();
        } else {
            maskRule = new HashMap<String, Map<String, MaskRuleVO>>();
        }
        List<String> soList = maskInterceptorMapper.getSoList();

        for (String soId : soList) {
            List<MaskRuleVO> maskRuleList = maskInterceptorMapper.getMaskRule(soId);
            if (maskRuleList.size() > 0) {
                Map<String, MaskRuleVO> rule = new HashMap<String, MaskRuleVO>();
                for (MaskRuleVO maskRule : maskRuleList) {
                    rule.put(maskRule.getFldNm(), maskRule);
                }
                maskRule.put(soId, rule);
            }
        }
    }

    private String maskString(Object object,
                              String key,
                              Map<String, MaskRuleVO> maskRuleInfo,
                              boolean isUnmask,
                              StringBuffer unmaskStringBuffer) {
        String str = (String) object;
        if (str == null || str.isEmpty())
            return str;
        // String이면 해당 키에 일치하면 변경
        String camelCaseKey = MaskUtil.convert2CamelCase(key);
        if (maskRuleInfo.containsKey(camelCaseKey)) {
            try {
                MaskRuleVO rule = maskRuleInfo.get(camelCaseKey);

                if (isUnmask == false) {
                    if (rule.getAddStrtIndx() == null) {
                        str = MaskUtil.converToMask(object.toString(),
                                                    rule.getStrtIndx(),
                                                    rule.getEndIndx(),
                                                    rule.getChgChar());
                    } else {
                        str = MaskUtil.converToMaskMulti(object.toString(),
                                                         rule.getStrtIndx(),
                                                         rule.getEndIndx(),
                                                         rule.getAddStrtIndx(),
                                                         rule.getAddEndIndx(),
                                                         rule.getChgChar());
                    }
                } else {
                    if (rule.getAuthAddStrtIndx() == null) {
                        str = MaskUtil.converToMask(object.toString(),
                                rule.getAuthStrtIndx(),
                                rule.getAuthEndIndx(),
                                rule.getChgChar());
                    } else {
                        str = MaskUtil.converToMaskMulti(object.toString(),
                                rule.getAuthStrtIndx(),
                                rule.getAuthEndIndx(),
                                rule.getAuthAddStrtIndx(),
                                rule.getAuthAddEndIndx(),
                                rule.getChgChar());
                    }


                    String maskedData = "";
                    if (rule.getAddStrtIndx() == null) {
                        maskedData = MaskUtil.converToMask(object.toString(),
                                rule.getStrtIndx(),
                                rule.getEndIndx(),
                                rule.getChgChar());
                    } else {
                        maskedData = MaskUtil.converToMaskMulti(object.toString(),
                                rule.getStrtIndx(),
                                rule.getEndIndx(),
                                rule.getAddStrtIndx(),
                                rule.getAddEndIndx(),
                                rule.getChgChar());
                    }
                    if(unmaskStringBuffer.length() > 0)
                        unmaskStringBuffer.append(",");
                    unmaskStringBuffer.append(key);
                    unmaskStringBuffer.append(":");
                    unmaskStringBuffer.append(maskedData);
                }


            } catch (Exception e) {
                logger.error("Masked Processing Error : Key={}, Value={}", key, object);
                return str;
            }

        }
        return str;
    }

    /**
     * <PRE>
     * 1. MethodName: maskCollection
     * 2. ClassName : MaskInterceptor
     * 3. Comment   : Collection 객체의 마스크 처리
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 3. 10. 오전 10:13:50
     * </PRE>
     *
     * @return void
     * @param object       Collection 객체
     * @param maskRuleInfo 변경 Rule정보
     */
    private void maskCollection(Object object,
                                Map<String, MaskRuleVO> maskRuleInfo,
                                boolean isUnmask,
                                StringBuffer unmaskStringBuffer) {
        if (object == null)
            return;

        Iterator<?> items = ((Collection) object).iterator();
        while (items != null && items.hasNext()) {
            try {
                Object item = items.next();
                if (item == null)
                    continue;

                String packageName = item.getClass().getPackage().getName();
                boolean isDomain = false;
                if (packageName != null &&
                    (packageName.startsWith(PACKAGE) && packageName.indexOf(".domain") > 0)) {
                    if (item instanceof ExcelFileVO) {
                        isDomain = false;
                    } else {
                        isDomain = true;
                    }
                }

                if (item instanceof Map) {
                    maskMapObject(item, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (isDomain) {
                    maskObject(item, maskRuleInfo, isUnmask, unmaskStringBuffer);
                }
            } catch (Exception e) {
                logger.error("Masked Processing Error : {}", e.getMessage());
            }
        }
    }

    /**
     * <PRE>
     * 1. MethodName: maskMapObject
     * 2. ClassName : MaskInterceptor
     * 3. Comment   : Map객체의 마스크 처리
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 3. 10. 오전 10:13:16
     * </PRE>
     *
     * @return void
     * @param object       Map객체
     * @param maskRuleInfo 변경 Rule정보
     */
    private void maskMapObject(Object object,
                               Map<String, MaskRuleVO> maskRuleInfo,
                               boolean isUnmask,
                               StringBuffer unmaskStringBuffer) {
        if (object == null)
            return;

        Map<String, Object> map = (Map<String, Object>) object;
        Set<String> keys = map.keySet();
        for (String key : keys) {
            try {
                Object obj = map.get(key);

                if (obj == null)
                    continue;

                String packageName = obj.getClass().getPackage().getName();
                boolean isDomain = false;
                if (packageName != null &&
                    (packageName.startsWith(PACKAGE) && packageName.indexOf(".domain") > 0)) {
                    if (obj instanceof ExcelFileVO) {
                        isDomain = false;
                    } else {
                        isDomain = true;
                    }
                }

                if (obj instanceof String) {
                    map.put(key, maskString(obj, key, maskRuleInfo, isUnmask, unmaskStringBuffer));
                } else if (obj instanceof Collection) {
                    maskCollection(obj, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (obj instanceof Map) {
                    maskMapObject(obj, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (isDomain) {
                    maskObject(obj, maskRuleInfo, isUnmask, unmaskStringBuffer);
                }
            } catch (Exception e) {
                logger.error("Masked Processing Error : {}", e.getMessage());
            }
        }
    }

    /**
     * <PRE>
     * 1. MethodName: maskObject
     * 2. ClassName : MaskInterceptor
     * 3. Comment   : VO 객체의 마스크 처리
     * 4. 작성자    : JHYun
     * 5. 작성일    : 2016. 3. 10. 오전 10:12:45
     * </PRE>
     *
     * @return void
     * @param object       VO객체
     * @param maskRuleInfo 변경 Rule정보
     */
    private void maskObject(Object object,
                            Map<String, MaskRuleVO> maskRuleInfo,
                            boolean isUnmask,
                            StringBuffer unmaskStringBuffer) {
        if (object == null)
            return;

        Class<? extends Object> c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(object);

                if (value == null) {
                    continue;
                }
                String packageName = value.getClass().getPackage().getName();

                boolean isDomain = false;
                if (packageName != null &&
                    (packageName.startsWith(PACKAGE) && packageName.indexOf(".domain") > 0)) {
                    if (value instanceof ExcelFileVO) {
                        isDomain = false;
                    } else {
                        isDomain = true;
                    }
                }
                if (value instanceof String) {
                    fields[i].set(object, maskString(value, fields[i].getName(), maskRuleInfo, isUnmask, unmaskStringBuffer));
                } else if (value instanceof Collection) {
                    maskCollection(value, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (value instanceof Map) {
                    maskMapObject(value, maskRuleInfo, isUnmask, unmaskStringBuffer);
                } else if (isDomain) {
                    maskObject(value, maskRuleInfo, isUnmask, unmaskStringBuffer);
                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error("Masked Processing Error : {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Masked Processing Error : {}", e.getMessage());
            }

        }
    }

    @Override
    public List<String> getExcludedList() {
        return this.excludedUrlList;
    }

}
