package com.ssolution.admin.system.service.common.main.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssolution.admin.system.mapper.common.main.MainMapper;
import com.ssolution.admin.system.service.common.main.MainService;
import com.ssolution.admin.system.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. FileName	: MainServiceImpl.java
 * 2. Package	: com.ssolution.admin.system.service.common.main.impl
 * 3. Comment	: 메뉴관리 서비스
 * 4. 작성자		: DEV.YKLEE
 * 5. 작성일		: 2020. 6. 11. 오전 10:39:38
 * 6. 변경이력
 *		이름		|	일자		|	변경내용
 * -------------------------------------------
 * 		DEV.YKLEE	|	2020. 6. 11.	|	신규 작성
 * </PRE>
 */
@Service
public class MainServiceImpl implements MainService {

    private static final String RCPT_RCPT_CACHE = "RCPT_RCPT_CACHE";
    private static final String RCPT_CMPL_CACHE = "RCPT_CMPL_CACHE";
    private static final String NEW_RCPT_CACHE = "NEW_RCPT_CACHE";
    private static final String NEW_CMPL_CACHE = "NEW_CMPL_CACHE";
    private static final String CHG_RCPT_CACHE = "CHG_RCPT_CACHE";
    private static final String CHG_CMPL_CACHE = "CHG_CMPL_CACHE";
    private static final String STAT_CACHE = "statCache";



    @Autowired
    private MainMapper mainMapper;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public List<String> getBaseDt() {
        List<String> baseDtList = new ArrayList<>();
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(-6));
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(-5));
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(-4));
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(-3));
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(-2));
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(-1));
        baseDtList.add(DateUtil.getDateStringYYYYMMDD(0));
        return baseDtList;
    }

    @Override
    public List<String> getRcptStat(List<String> baseDtList) {
        List<String> statList = new ArrayList<>();
        Ehcache statCache = cacheManager.getEhcache(STAT_CACHE);
        String today = DateUtil.getDateStringYYYYMMDD(0);
        for(String date : baseDtList){
            String cacheKey = RCPT_RCPT_CACHE + date;
            String value = "";

            if(today.equals(date)){
                value = String.valueOf(mainMapper.getRcptCount(date));
            }else{
                Element statElement = statCache.get(cacheKey);
                if (statElement != null && statElement.getObjectValue() != null) {
                    value = (String) statElement.getObjectValue();
                }else{
                    value = String.valueOf(mainMapper.getRcptCount(date));
                    statElement = new Element(cacheKey, value);
                    statCache.put(statElement);
                }
            }
            statList.add(value);
        }
        return statList;
    }

    @Override
    public List<String> getRcptCmplStat(List<String> baseDtList) {
        List<String> statList = new ArrayList<>();
        Ehcache statCache = cacheManager.getEhcache(STAT_CACHE);
        String today = DateUtil.getDateStringYYYYMMDD(0);

        for(String date : baseDtList){
            String cacheKey = RCPT_CMPL_CACHE + date;
            Element statElement = statCache.get(cacheKey);
            String value = "";

            if(today.equals(date)){
                value = String.valueOf(mainMapper.getRcptCmplCount(date));
            }else{
                if (statElement != null && statElement.getObjectValue() != null) {
                    value = (String) statElement.getObjectValue();

                }else{
                    value = String.valueOf(mainMapper.getRcptCmplCount(date));
                    statElement = new Element(cacheKey, value);
                    statCache.put(statElement);
                }

            }
            statList.add(value);
        }
        return statList;

    }

    @Override
    public List<String> getNewRcptStat(List<String> baseDtList) {
        List<String> statList = new ArrayList<>();
        Ehcache statCache = cacheManager.getEhcache(STAT_CACHE);
        String today = DateUtil.getDateStringYYYYMMDD(0);

        for(String date : baseDtList){
            String cacheKey = NEW_RCPT_CACHE + date;
            Element statElement = statCache.get(cacheKey);
            String value = "";

            if(today.equals(date)){
                value = String.valueOf(mainMapper.getNewRcptCount(date));
            }else{
                if (statElement != null && statElement.getObjectValue() != null) {
                    value = (String) statElement.getObjectValue();

                }else{
                    value = String.valueOf(mainMapper.getNewRcptCount(date));
                    statElement = new Element(cacheKey, value);
                    statCache.put(statElement);
                }

            }
            statList.add(value);
        }
        return statList;
    }

    @Override
    public List<String> getNewCmplStat(List<String> baseDtList) {
        List<String> statList = new ArrayList<>();
        Ehcache statCache = cacheManager.getEhcache(STAT_CACHE);
        String today = DateUtil.getDateStringYYYYMMDD(0);

        for(String date : baseDtList){
            String cacheKey = NEW_CMPL_CACHE + date;
            Element statElement = statCache.get(cacheKey);
            String value = "";

            if(today.equals(date)){
                value = String.valueOf(mainMapper.getNewCmplCount(date));
            }else{
                if (statElement != null && statElement.getObjectValue() != null) {
                    value = (String) statElement.getObjectValue();

                }else{
                    value = String.valueOf(mainMapper.getNewCmplCount(date));
                    statElement = new Element(cacheKey, value);
                    statCache.put(statElement);
                }

            }
            statList.add(value);
        }
        return statList;
    }

    @Override
    public List<String> getChgRcptStat(List<String> baseDtList) {
        List<String> statList = new ArrayList<>();
        Ehcache statCache = cacheManager.getEhcache(STAT_CACHE);
        String today = DateUtil.getDateStringYYYYMMDD(0);

        for(String date : baseDtList){
            String cacheKey = CHG_RCPT_CACHE + date;
            Element statElement = statCache.get(cacheKey);
            String value = "";

            if(today.equals(date)){
                value = String.valueOf(mainMapper.getChgRcptCount(date));
            }else{
                if (statElement != null && statElement.getObjectValue() != null) {
                    value = (String) statElement.getObjectValue();

                }else{
                    value = String.valueOf(mainMapper.getChgRcptCount(date));
                    statElement = new Element(cacheKey, value);
                    statCache.put(statElement);
                }

            }
            statList.add(value);
        }
        return statList;
    }

    @Override
    public List<String> getChgCmplStat(List<String> baseDtList) {
        List<String> statList = new ArrayList<>();
        Ehcache statCache = cacheManager.getEhcache(STAT_CACHE);
        String today = DateUtil.getDateStringYYYYMMDD(0);

        for(String date : baseDtList){
            String cacheKey = CHG_CMPL_CACHE + date;
            Element statElement = statCache.get(cacheKey);
            String value = "";

            if(today.equals(date)){
                value = String.valueOf(mainMapper.getChgCmplCount(date));
            }else{
                if (statElement != null && statElement.getObjectValue() != null) {
                    value = (String) statElement.getObjectValue();

                }else{
                    value = String.valueOf(mainMapper.getChgCmplCount(date));
                    statElement = new Element(cacheKey, value);
                    statCache.put(statElement);
                }
            }
            statList.add(value);
        }
        return statList;
    }

}