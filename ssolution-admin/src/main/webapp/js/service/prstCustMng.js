
//가망고객 아이디 조회
function getPrspctCustId() {
    return ( prstCustBasicInfoData.prspctCustId != undefined ) ? prstCustBasicInfoData.prspctCustId : "";
}

//회선상품 등록 여부 확인
function isLineProdExists() {
    return (dtLineProdList.rows().count() > 0);
}

//회선 상품 선택 정보 확인
function getLineProdRowData() {
    var count = dtLineProdList.rows('.selected').count();
    if (count == 0) {
        return "";
    }
    var data = dtLineProdList.rows('.selected').data()[0];
    return data;
}

//회선아이디 조회
function getProdCmpsId() {
    return getLineProdRowData().prspctProdCmpsId;
}

//회선상품 구성아이디 조회
function isProdCmpsId() {
    var prodId = getProdCmpsId();
    return isEmpty(prodId) || prodId == "0" ? false : true;
}

// 등록 된 회선상품 카운트
function getLineProdRgedCount() {
    var count = dtLineProdList.rows().count();
    var rCount = 0;
    for (var i = 0; i < count; i++) {

        var cmpsId = dtLineProdList.rows(i).data()[0].prspctProdCmpsId;
        if (!isEmpty(cmpsId) && cmpsId != '0') {
            rCount++;
        }
    }
    return rCount;
}

function getLineProdCdListArray() {
    // 퍼펙트가드 저장 전 회선상품별 보험유형이 동일한지 체크
    var prodCdList = [];
    dtLineProdList.rows().every( function( rowIdx, tableLoop, rowLoop ) {
        prodCdList.push(this.data().prodCd);
    });
    return prodCdList;
}

//선택 된 자재 datatable row 조회
function getPossMatrRowData() {
    if (dtMatrPossList.rows('.selected').count() == 0) {
        return false;
    }
    return dtMatrPossList.rows('.selected').data()[0];
}

//선택 된 부가서비스 datatable row 조회
function getPossExtProdRowData() {
    if (dtExtProdPossList.rows('.selected').count() == 0) {
        return false;
    }
    return dtExtProdPossList.rows('.selected').data()[0];
}

//datatable 목록 전체 초기화
function clearPrstCustDataTable() {
    ( isLineProdTablesDrawed ) ? dtLineProdList.clear().draw() : "";
    ( isExtProdTablesDrawed ) ? dtExtProdList.clear().draw() : "";
    ( isMatrTablesDrawed ) ? dtMatrList.clear().draw() : "";
}

//업종 셀렉트 박스 옵션 생성
function makeIndutySelectbox(commList, target, selectedValue) {

    $(target).find("option:not(:first)").remove();
    var selected = "";
    selectedValue = (isEmpty(selectedValue)) ? "" : selectedValue;
    $.each(commList, function(inx) {
        selected = (selectedValue == this.commCd) ? "selected='selected'" : "";
        $(target).append("<option value='"+this.commCd+"' "+selected+">"+this.commNm+"</option>");
    });
}

//업태 선택 이벤트
function changeBizCndt(src, target, selectedValue, callback) {

    var value = $(src).find("option:selected").val();

    if (isEmpty(value)) {
        $(target).find("option:not(:first)").remove();
    }
    else {

        var cd, nm, refCd1;
        var code;
        var newList = [];
        for (var i in indutyCodeList) {
            code = indutyCodeList[i];
            refCd1 = code.refCd1;
            if (refCd1 == value) {  //  업태코드를 부모코드로 가지는 경우 출력
                newList.push(code);
            }
        }
        makeIndutySelectbox(newList, target, selectedValue);
    }
}

//디폴트값 설정
function setDefaultValue() {
    //디폴트 값 설정 (등록모드인 경우에만 설정)
    if (isEmpty(getPrspctCustId())) {
        $("#resRegDt").text(userInfo.today);
    }
}

// 전체 입력 요소 비활성 및 밸류 초기화
function initializePrstCust(){

    currentLineProdRowIndex = -1;   //현재 선택 된 회선상품 위치

    /* 데이터 저장 객체 */
    prstCustBasicInfoData = {};     // 조회 된 가망고객정보 저장 객체
    prstCustPfctGurdInfoData = {};  // 조회 된 가망고객정보 저장 객체
    prstCustFinsInfoData = {};      // 조회 된 화재서비스 저장 객체
    prstCustCnstData = {};          // 공사정보 저장 객체

    // 가망고객 영역 비활성
    disablePrstCustInfo();
    // 용역료 비활성
    disableServcCostInfo();
    // 퍼펙트가드 비활성
    disablePfctGurdInfo();
    // 화재보험 비활성
    disableFinsInfo();
    // 결합정보 비활성
    disableComboInfo();
    // 회선정보 비활성
    disableLineProdInfo();
    // 자재정보 비활성
    disableMatrInfo();
    // 공사정보 비활성
    disableCnstInfo();
    // 부가서비스정보 비활성
    disableExtProdInfo();
    // 장비서비스정보 비활성
    disableDeviceProdInfo();
    //디폴트 값 설정
    setDefaultValue();
    // 견적조회 초기화
    estVO = {};
}


//전체 입력 요소 비활성 및 밸류 초기화 가망고객 정보 제외
function initializePrstCustEstDoc(){

    currentLineProdRowIndex = -1;   //현재 선택 된 회선상품 위치

    /* 데이터 저장 객체 */
    prstCustBasicInfoData = {};     // 조회 된 가망고객정보 저장 객체
    prstCustPfctGurdInfoData = {};  // 조회 된 가망고객정보 저장 객체
    prstCustFinsInfoData = {};      // 조회 된 화재서비스 저장 객체
    prstCustCnstData = {};          // 공사정보 저장 객체

    // 가망고객 영역 비활성
    //disablePrstCustInfo();
    // 용역료 비활성
    disableServcCostInfo();
    // 퍼펙트가드 비활성
    disablePfctGurdInfo();
    // 화재보험 비활성
    disableFinsInfo();
    // 결합정보 비활성
    disableComboInfo();
    // 회선정보 비활성
    disableLineProdInfo();
    // 자재정보 비활성
    disableMatrInfo();
    // 공사정보 비활성
    disableCnstInfo();
    // 부가서비스정보 비활성
    disableExtProdInfo();
    // 장비서비스정보 비활성
    disableDeviceProdInfo();
    //디폴트 값 설정
    setDefaultValue();
    // 견적조회 초기화
    estVO = {};
}
// #초기화 버튼
function clearPrstCust(){
    // 입력 및 버튼 비활성
    initializePrstCust();
    // 가망고객정보 밸류 초기화
//     setValuePrstCustBasicInfo({});
    clearValuePrstCustInfo();
    //datatable 초기화
    clearPrstCustDataTable();
    //디폴트 값 설정
    setDefaultValue();
    // 가망고객 영역 활성
    initializePrstCustInfo();
    // 초기화 버튼 활성
    domEnableds(['btnClearPrstCust', 'btnInsertPrstCust', 'btnEstDocInqPopup']);
    // 용역료 탭 활성
    $("#tabViewServcCost").click();
}
//견족조회시 초기화
function clearPrstCustEstDoc(){
    // 입력 및 버튼 비활성
    //initializePrstCust();
    initializePrstCustEstDoc();
    // 가망고객정보 밸류 초기화

    //datatable 초기화
    clearPrstCustDataTable();
    //디폴트 값 설정
    setDefaultValue();
    // 가망고객 영역 활성
    initializePrstCustInfo();
    // 버튼 비활성
    domDisableds([
        "btnSearchPrspctCustId",    // 가망고객조회
        "btnEstDocInqPopup",        // 견적조회
        "btnAddInfo",               // 추가정보
        "btnUpdatePrstCust",
        "btnElctrnAppr",
        "btnReqCnst",
        "btnReApprvReady",
        "btnCancel"
//      "btnComplete"               // 개통완료 - 테스트용
    ]);
    // 초기화 버튼 활성
    domEnableds(['btnClearPrstCust', 'btnInsertPrstCust', 'btnEstDocInqPopup']);
    // 용역료 탭 활성
    $("#tabViewServcCost").click();
}

//현재 선택된 datatable row index 확인
function getRowIndex(tabledata) {

    var inx = -1;
    tabledata.rows().every( function( rowIdx, tableLoop, rowLoop ) {
        if ($(this.node()).hasClass("selected")) {
            inx = rowIdx;
        }
    });
    return inx;
}

/*==============================================================
* #고객정보 관련 함수 정의
*
*
*
==============================================================*/

// 가망고객 정보 영역 비활성
function disablePrstCustInfo() {
    domDisableds(prstCustInfoList);
    domDisableds(prstCustBasicInfoList);
    // 팝업 비활성
    domDisableds([
        "btnSearchPrspctCustId",
        "btnSearchSalesCnterId",
        "btnSearchBrOrgId",
        "btnSearchSalesPsnInchrg",
        "btnSearchMngCar",
        "btnSearchMngCnterOrg",
        "btnSearchMngBrOrg",
        "btnSearchMngPsnInchrg",
        "btnSearchMngPsnInchrg",
        "btnSearchElctrnApprPopup",
        "btnSearchPottlCustPopup",
        "btnSearchCtrtIdPopup"
    ]);
    // 버튼 비활성
    domDisableds([
        "btnEstDocInqPopup",
        "btnAddInfo",
        "btnClearPrstCust",
        "btnInsertPrstCust",
        "btnUpdatePrstCust",
        "btnElctrnAppr",
        "btnReqCnst",
        "btnReApprvReady",
        "btnCancel"
//      "btnComplete"               // 개통완료 - 테스트용
    ]);
    // 밸류 초기화
    clearValuePrstCustInfo();
}

// 가망고객 영역 초기 활성
function initializePrstCustInfo() {
    domEnableds(prstCustInfoList);
    domEnableds(prstCustBasicInfoList);
    // 팝업 활성
    domEnableds([
        "btnSearchPrspctCustId",
        "btnSearchSalesCnterId",
        "btnSearchBrOrgId",
        "btnSearchSalesPsnInchrg",
        "btnSearchMngCar",
        "btnSearchMngCnterOrg",
        "btnSearchMngBrOrg",
        "btnSearchMngPsnInchrg",
        "btnSearchMngPsnInchrg",
        "btnSearchElctrnApprPopup",
        "btnSearchPottlCustPopup",
        "btnSearchCtrtIdPopup"
    ]);
    // 버튼 활성
    domEnableds(["btnClearPrstCust"]);
}

// 밸류 초기화
function clearValuePrstCustInfo() {
//  for(var key in prstCustBasicInfoData) {
//      prstCustBasicInfoData[key] = "";
//  }
//  setValuePrstCustBasicInfo(prstCustBasicInfoData);
    prstCustBasicInfoData = {};
    fnClearValue('prstCustInfo', true);
    fnClearValue('prstCustBasicInfo', true);
    setValuePrstCustBasicInfo(prstCustBasicInfoData);
}

// 용역료 정보 영역 비활성
function disableServcCostInfo() {
    domDisableds(prstCustServcCostInfoList);
}

function initializeServcCostInfo() {
    domEnableds(prstCustServcCostInfoList);
}

//가망고객 디폴트 입력 요소 활성
function enablePrstCustInfo() {
    domEnableds(prstCustInfoList);      //가망정보
    domEnableds(prstCustBasicInfoList); //가망 기본정보
}

//초기화면 디폴트 로딩
function loadPrstCust(prspctCustId) {
    //초기화 수행
//  initializePrstCust();
    // [화면초기화]
    clearPrstCust();
    initializePrstCustInfo();   //가망고객
    initializeServcCostInfo();  //용역료
    initializePfctGurdInfo();   //퍼펙트가드
    initializeFinsInfo();       //화재서비스
    initializeComboInfo();      //결합정보
    //가망고객 기본정보 조회
    setPrstCustBasicInfo(prspctCustId, function(data) {
        //회선 리스트 조회
        getLineProdList();
        //자재정보 리스트 조회
        getMatrPossList();
        getMatrList();
        //부가서비스 리스트 조회
        getExtProdPossList();
        getExtProdList();
        // 장비서비스 목록
        getDevicePossList();
        getDeviceList();
    });
}

//견적조회시 가망고객 정보 재세팅 하지 않음
function setValuePrstCustBasicInfoEstDoc(info){


    var prspctCustId = isEmpty(info) ? "" : info.prspctCustId;
    var regDt = isEmpty(info) ? "" : stringToDateformatYYYYMMDD(info.regDt);
    var prspctStat = isEmpty(info) ? "" : info.prspctStat;
    var prspctStatNm = isEmpty(info) ? "" : info.prspctStatNm;
    var ctrtdocWritngCl = isEmpty(info) ? "" : info.ctrtdocWritngCl;
    var salesCnterOrgId = isEmpty(info) ? "" : info.salesCnterOrgId;
    var salesCnterOrgNm = isEmpty(info) ? "" : info.salesCnterOrgNm;
    var salesBrOrgId = isEmpty(info) ? "" : info.salesBrOrgId;
    var salesBrOrgNm = isEmpty(info) ? "" : info.salesBrOrgNm;
    var salesPsnInchrgId = isEmpty(info) ? "" : info.salesPsnInchrgId;
    var salesPsnInchrgNm = isEmpty(info) ? "" : info.salesPsnInchrgNm;
    var mngCarInsttCd = isEmpty(info) ? "" : info.mngCarInsttCd;
    var mngCarInsttNm = isEmpty(info) ? "" : info.mngCarInsttNm;
    var mngCarCd = isEmpty(info) ? "" : info.mngCarCd;
    var mngCarNm = isEmpty(info) ? "" : info.mngCarNm;
    var mngCnterOrgId = isEmpty(info) ? "" : info.mngCnterOrgId;
    var mngCnterOrgNm = isEmpty(info) ? "" : info.mngCnterOrgNm;
    var mngBrOrgId = isEmpty(info) ? "" : info.mngBrOrgId;
    var mngBrOrgNm = isEmpty(info) ? "" : info.mngBrOrgNm;
    var mngPsnInchrgId = isEmpty(info) ? "" : info.mngPsnInchrgId;
    var mngPsnInchrgNm = isEmpty(info) ? "" : info.mngPsnInchrgNm;
    var ctrtHopeDt = isEmpty(info) ? "" : stringToDateformatYYYYMMDD(info.ctrtHopeDt);
    var corpNm = isEmpty(info) ? "" : info.corpNm;
    var bizNo = isEmpty(info) ? "" : bizRegNoFormatter(info.bizNo);
    var bizCndtCd = isEmpty(info) ? "" : info.bizCndtCd;
    var indutyCd = isEmpty(info) ? "" : info.indutyCd;
    var ctrtPrd = isEmpty(info) ? "" : info.ctrtPrd;
    var ctrtInflowCl = isEmpty(info) ? "" : info.ctrtInflowCl;
    var ctrtTp = isEmpty(info) ? "" : info.ctrtTp;
    var othcoCd = isEmpty(info) ? "" : info.othcoCd;
    var freeSvcMth = isEmpty(info) ? "" : info.freeSvcMth;
    var promtnCd = isEmpty(info) ? "" : info.promtnCd;
    var allwncClcStdr = isEmpty(info) ? "" : info.allwncClcStdr;
    var selngCl = isEmpty(info) ? "" : info.selngCl;
    var elctrnApprId = isEmpty(info) ? "" : info.elctrnApprId;
    var fnlsctrCl = isEmpty(info) ? "" : info.fnlsctrCl;
    var pottialCustId = isEmpty(info) ? "" : info.pottialCustId;
    var pottialCustNm = isEmpty(info) ? "" : info.pottialCustNm;
    var pottialCustTpNm = isEmpty(info) ? "" : info.pottialCustTpNm;
    var ctrtId = isEmpty(info) ? "" : info.ctrtId;
    var ctrtDt = isEmpty(info) ? "" : info.ctrtDt;
    var rateStrtDt = isEmpty(info) ? "" : info.rateStrtDt;
    var chgDate = isEmpty(info) ? "" : dateTypeFormatterYYYYMMDDHH24MISS(info.chgDate);
    var indutyCd = isEmpty(info) ? "" : info.indutyCd;
    var pureServcCost = isEmpty(info) ? "" : numberWithCommas(info.pureServcCost);
    var stdServcCost = isEmpty(info) ? "" : numberWithCommas(info.stdServcCost);
    var dcRt = isEmpty(info) ? "" : info.dcRt;
    var matrCost = isEmpty(info) ? "" : numberWithCommas(info.matrCost);
    var cnstCost = isEmpty(info) ? "" : numberWithCommas(info.cnstCost);
    var cnvrsnCost = isEmpty(info) ? "" : numberWithCommas(info.cnvrsnCost);
    var prdAplyRt = isEmpty(info) ? "" : info.prdAplyRt;
    var ctrtInflowAplyRt = isEmpty(info) ? "" : info.ctrtInflowAplyRt;
    var highArpuRt = isEmpty(info) ? "" : info.highArpuRt;
    var pnlPrd = isEmpty(info) ? "" : info.pnlPrd;
    var allwncReflctAmt = isEmpty(info) ? "" : numberWithCommas(info.allwncReflctAmt);
    var sacReflctRt = isEmpty(info) ? "" : info.sacReflctRt;
    var thsMthAvgPnlPrd = isEmpty(info) ? "" : info.thsMthAvgPnlPrd;
    var thsYrAvgPnlPrd = isEmpty(info) ? "" : info.thsYrAvgPnlPrd;
    var stdRtinErn = isEmpty(info) ? "" : numberWithCommas(info.stdRtinErn);
    var enggRtinErn = isEmpty(info) ? "" : numberWithCommas(info.enggRtinErn);
    var rtinErnSum = isEmpty(info) ? "" : numberWithCommas(info.rtinErnSum);
    var extprodServcCost = isEmpty(info) ? "" : numberWithCommas(info.extprodServcCost);
    var insuFee = isEmpty(info) ? "" : numberWithCommas(info.insuFee);
    var insuSubsAmt = isEmpty(info) ? "" : numberWithCommas(info.insuSubsAmt);
    var billcnstCost = isEmpty(info) ? "" : numberWithCommas(info.billcnstCost);
    var rmvlCost = isEmpty(info) ? "" : numberWithCommas(info.rmvlCost);
    var deposit = isEmpty(info) ? "" : numberWithCommas(info.deposit);
    var servcCost = isEmpty(info) ? "" : numberWithCommas(info.servcCost);
    var bizCndtCdNm = isEmpty(info) ? "" : info.bizCndtCdNm;
    var indutyNm = isEmpty(info) ? "" : info.indutyNm;
    var statChgDate = isEmpty(info) ? "" : stringToDateformatYYYYMMDDHH24MISS(info.statChgDate);

    //가망고객 기본 정보
    $("#prspctCustId").val("");
    $("#corpNm").val(corpNm);                   //상호명
    $("#bizNo").val(bizNo);                     //사업자번호
    $("#bizCndtCd").val(bizCndtCd);
//  $("#indutyCd").val(indutyCd);
    $("#ctrtPrd").val(ctrtPrd);                 //계약기간
    $("#ctrtInflowCl").val(ctrtInflowCl);       //계약유입구분
    $("#ctrtTp").val(ctrtTp);                   //계약유형
    $("#othcoCd").val(othcoCd);                 //타사코드
    $("#freeSvcMth").val(freeSvcMth);           //무상서비스개월수
    $("#promtnCd").val(promtnCd);               //프로모션
    $("#allwncClcStdr").val(allwncClcStdr);     //수당 계산 기준
    $("#selngCl").val(selngCl);                 //매출구분
    $("#elctrnApprId").val(elctrnApprId);       //전자결재id
    $("#prstCustFnlsctrCl").val(fnlsctrCl);     //금융권구분
//  $("#fnlsctrCl").val(fnlsctrCl);             //금융권구분
    $("#pottialCustId").val(pottialCustId);     //잠재고객ID
    $("#pottialCustNm").val(pottialCustNm);     //잠재고객명
    $("#resPottialCustTpNm").text(pottialCustTpNm);         //TODO 잠재고객유형
    $("#ctrtId").val(ctrtId);                   //계약id
    $("#resCtrtDt").text(stringToDateformatYYYYMMDD(ctrtDt));               //계약일자
    if (prspctStat == "10") {
        $("#resRateStrtDt").text(rateStrtDt);   //개통일자 ( 개통완료시에만 출력)
    }
    $("#resStatChgDate").text(statChgDate);     //상태 변경 일자

    //용역료 출력
    $("#resPureServcCost").text(pureServcCost);         //순용역료
    $("#resStdServcCost").text(stdServcCost);           //표준용역료
    $("#resDcRt").text(dcRt);                           //할인율
    $("#resMatrCost").text(matrCost);                   //자재비
    $("#resCnstCost").text(cnstCost);                   //예상공사비
    $("#resCnvrsnCost").text(cnvrsnCost);               //환산료
    $("#resPrdAplyRt").text(prdAplyRt);                 //기간적용률
    $("#resCtrtInflowAplyRt").text(ctrtInflowAplyRt);   //계약유입적용률
    $("#resHighArpuRt").text(highArpuRt);               //고ARPU률
    $("#resPnlPrd").text(pnlPrd);                       //손익기간
    $("#resAllwncReflctAmt").text(allwncReflctAmt);     //수당반영료
    $("#resSacReflctRt").text(sacReflctRt);             //SAC반영률
    $("#resThsMthAvgPnlPrd").text(thsMthAvgPnlPrd);     //당월평균손익
    $("#resThsYrAvgPnlPrd").text(thsYrAvgPnlPrd);       //당해평균손익
    $("#resStdRtinErn").text(stdRtinErn);               //표준유보금

    $("#enggRtinErn").val(enggRtinErn);                 //약정유보금
    $("#resRtinErnSum").text(rtinErnSum);               //유보금합계
    $("#resExtprodServcCost").text(extprodServcCost);   //부가서비스 용역료

    $("#resPfctGurd").text(insuFee);                    //퍼펙트가드
    $("#resFireServc").text(insuSubsAmt);               //화재보험

    $("#billcnstCost").val(billcnstCost);               //청구공사비
    $("#rmvlCost").val(rmvlCost);                       //철거비
    $("#deposit").val(deposit);                         //보증금
    $("#resTotServcCost").text(servcCost);              //총용역료

    // 기본정보 업종 공통코드 목록 설정
    if (!isEmpty(bizCndtCd)) {
        changeBizCndt($("#bizCndtCd"), $("#indutyCd"), indutyCd);
    }

    //퍼펙트 가드 업태/업종 출력
    $("#resBizCndtCd").text(bizCndtCdNm);
    $("#resIndutyCd").text(indutyNm);

    // 계약유형에 따른 타사코드 설정
    setOthco(ctrtTp);


}


//가망고객 정보 밸류 설정
function setValuePrstCustBasicInfo(info) {

  var prspctCustId = isEmpty(info) ? "" : info.prspctCustId;
  var regDt = isEmpty(info) ? "" : stringToDateformatYYYYMMDD(info.regDt);
  var prspctStat = isEmpty(info) ? "" : info.prspctStat;
  var prspctStatNm = isEmpty(info) ? "" : info.prspctStatNm;
  var ctrtdocWritngCl = isEmpty(info) ? "" : info.ctrtdocWritngCl;
  var salesCnterOrgId = isEmpty(info) ? "" : info.salesCnterOrgId;
  var salesCnterOrgNm = isEmpty(info) ? "" : info.salesCnterOrgNm;
  var salesBrOrgId = isEmpty(info) ? "" : info.salesBrOrgId;
  var salesBrOrgNm = isEmpty(info) ? "" : info.salesBrOrgNm;
  var salesPsnInchrgId = isEmpty(info) ? "" : info.salesPsnInchrgId;
  var salesPsnInchrgNm = isEmpty(info) ? "" : info.salesPsnInchrgNm;
  var mngCarInsttCd = isEmpty(info) ? "" : info.mngCarInsttCd;
  var mngCarInsttNm = isEmpty(info) ? "" : info.mngCarInsttNm;
  var mngCarCd = isEmpty(info) ? "" : info.mngCarCd;
  var mngCarNm = isEmpty(info) ? "" : info.mngCarNm;
  var mngCnterOrgId = isEmpty(info) ? "" : info.mngCnterOrgId;
  var mngCnterOrgNm = isEmpty(info) ? "" : info.mngCnterOrgNm;
  var mngBrOrgId = isEmpty(info) ? "" : info.mngBrOrgId;
  var mngBrOrgNm = isEmpty(info) ? "" : info.mngBrOrgNm;
  var mngPsnInchrgId = isEmpty(info) ? "" : info.mngPsnInchrgId;
  var mngPsnInchrgNm = isEmpty(info) ? "" : info.mngPsnInchrgNm;
  var ctrtHopeDt = isEmpty(info) ? "" : stringToDateformatYYYYMMDD(info.ctrtHopeDt);
  var corpNm = isEmpty(info) ? "" : info.corpNm;
  var bizNo = isEmpty(info) ? "" : bizRegNoFormatter(info.bizNo);
  var bizCndtCd = isEmpty(info) ? "" : info.bizCndtCd;
  var indutyCd = isEmpty(info) ? "" : info.indutyCd;
  var ctrtPrd = isEmpty(info) ? "" : info.ctrtPrd;
  var ctrtInflowCl = isEmpty(info) ? "" : info.ctrtInflowCl;
  var ctrtTp = isEmpty(info) ? "" : info.ctrtTp;
  var othcoCd = isEmpty(info) ? "" : info.othcoCd;
  var freeSvcMth = isEmpty(info) ? "" : info.freeSvcMth;
  var promtnCd = isEmpty(info) ? "" : info.promtnCd;
  var allwncClcStdr = isEmpty(info) ? "" : info.allwncClcStdr;
  var selngCl = isEmpty(info) ? "" : info.selngCl;
  var elctrnApprId = isEmpty(info) ? "" : info.elctrnApprId;
  var fnlsctrCl = isEmpty(info) ? "" : info.fnlsctrCl;
  var pottialCustId = isEmpty(info) ? "" : info.pottialCustId;
  var pottialCustNm = isEmpty(info) ? "" : info.pottialCustNm;
  var pottialCustTpNm = isEmpty(info) ? "" : info.pottialCustTpNm;
  var ctrtId = isEmpty(info) ? "" : info.ctrtId;
  var ctrtDt = isEmpty(info) ? "" : info.ctrtDt;
  var rateStrtDt = isEmpty(info) ? "" : info.rateStrtDt;
  var chgDate = isEmpty(info) ? "" : dateTypeFormatterYYYYMMDDHH24MISS(info.chgDate);
  var indutyCd = isEmpty(info) ? "" : info.indutyCd;
  var pureServcCost = isEmpty(info) ? "" : numberWithCommas(info.pureServcCost);
  var stdServcCost = isEmpty(info) ? "" : numberWithCommas(info.stdServcCost);
  var dcRt = isEmpty(info) ? "" : info.dcRt;
  var matrCost = isEmpty(info) ? "" : numberWithCommas(info.matrCost);
  var cnstCost = isEmpty(info) ? "" : numberWithCommas(info.cnstCost);
  var cnvrsnCost = isEmpty(info) ? "" : numberWithCommas(info.cnvrsnCost);
  var prdAplyRt = isEmpty(info) ? "" : info.prdAplyRt;
  var ctrtInflowAplyRt = isEmpty(info) ? "" : info.ctrtInflowAplyRt;
  var highArpuRt = isEmpty(info) ? "" : info.highArpuRt;
  var pnlPrd = isEmpty(info) ? "" : info.pnlPrd;
  var allwncReflctAmt = isEmpty(info) ? "" : numberWithCommas(info.allwncReflctAmt);
  var sacReflctRt = isEmpty(info) ? "" : info.sacReflctRt;
  var thsMthAvgPnlPrd = isEmpty(info) ? "" : info.thsMthAvgPnlPrd;
  var thsYrAvgPnlPrd = isEmpty(info) ? "" : info.thsYrAvgPnlPrd;
  var stdRtinErn = isEmpty(info) ? "" : numberWithCommas(info.stdRtinErn);
  var enggRtinErn = isEmpty(info) ? "" : numberWithCommas(info.enggRtinErn);
  var rtinErnSum = isEmpty(info) ? "" : numberWithCommas(info.rtinErnSum);
  var extprodServcCost = isEmpty(info) ? "" : numberWithCommas(info.extprodServcCost);
  var insuFee = isEmpty(info) ? "" : numberWithCommas(info.insuFee);
  var insuSubsAmt = isEmpty(info) ? "" : numberWithCommas(info.insuSubsAmt);
  var billcnstCost = isEmpty(info) ? "" : numberWithCommas(info.billcnstCost);
  var rmvlCost = isEmpty(info) ? "" : numberWithCommas(info.rmvlCost);
  var deposit = isEmpty(info) ? "" : numberWithCommas(info.deposit);
  var servcCost = isEmpty(info) ? "" : numberWithCommas(info.servcCost);
  var bizCndtCdNm = isEmpty(info) ? "" : info.bizCndtCdNm;
  var indutyNm = isEmpty(info) ? "" : info.indutyNm;
  var statChgDate = isEmpty(info) ? "" : stringToDateformatYYYYMMDDHH24MISS(info.statChgDate);

  //가망고객 정보
  $("#prspctCustId").val(prspctCustId);
  $("#resRegDt").text(regDt);
  $("#resPrspctStat").text(prspctStatNm); //가망상태
  $("#ctrtdocWritngCl").val(ctrtdocWritngCl); //계약서작성구분
  $("#salesCnterOrgId").val(salesCnterOrgId);
  $("#salesCnterOrgNm").val(salesCnterOrgNm);
  $("#salesBrOrgId").val(salesBrOrgId);
  $("#salesBrOrgNm").val(salesBrOrgNm);
  $("#salesPsnInchrgId").val(salesPsnInchrgId);
  $("#salesPsnInchrgNm").val(salesPsnInchrgNm);
  $("#mngCarInsttCd").val(mngCarInsttCd);
  $("#mngCarInsttNm").val(mngCarInsttNm);
  $("#mngCarCd").val(mngCarCd);
  $("#mngCarNm").val(mngCarNm);
  $("#mngCnterOrgId").val(mngCnterOrgId);
  $("#mngCnterOrgNm").val(mngCnterOrgNm);
  $("#mngBrOrgId").val(mngBrOrgId);
  $("#mngBrOrgNm").val(mngBrOrgNm);
  $("#mngPsnInchrgId").val(mngPsnInchrgId);
  $("#mngPsnInchrgNm").val(mngPsnInchrgNm);
  $("#ctrtHopeDt").val(ctrtHopeDt);

  //가망고객 기본 정보
  $("#corpNm").val(corpNm);                   //상호명
  $("#bizNo").val(bizNo);                     //사업자번호
  $("#bizCndtCd").val(bizCndtCd);
//$("#indutyCd").val(indutyCd);
  $("#ctrtPrd").val(ctrtPrd);                 //계약기간
  $("#ctrtInflowCl").val(ctrtInflowCl);       //계약유입구분
  $("#ctrtTp").val(ctrtTp);                   //계약유형
  $("#othcoCd").val(othcoCd);                 //타사코드
  $("#freeSvcMth").val(freeSvcMth);           //무상서비스개월수
  $("#promtnCd").val(promtnCd);               //프로모션
  $("#allwncClcStdr").val(allwncClcStdr);     //수당 계산 기준
  $("#selngCl").val(selngCl);                 //매출구분
  $("#elctrnApprId").val(elctrnApprId);       //전자결재id
  $("#prstCustFnlsctrCl").val(fnlsctrCl);     //금융권구분
//$("#fnlsctrCl").val(fnlsctrCl);             //금융권구분
  $("#pottialCustId").val(pottialCustId);     //잠재고객ID
  $("#pottialCustNm").val(pottialCustNm);     //잠재고객명
  $("#resPottialCustTpNm").text(pottialCustTpNm);         //TODO 잠재고객유형
  $("#ctrtId").val(ctrtId);                   //계약id
  $("#resCtrtDt").text(stringToDateformatYYYYMMDD(ctrtDt));               //계약일자
  if (prspctStat == "10") {
      $("#resRateStrtDt").text(rateStrtDt);   //개통일자 ( 개통완료시에만 출력)
  }
  $("#resStatChgDate").text(statChgDate);     //상태 변경 일자

  //용역료 출력
  $("#resPureServcCost").text(pureServcCost);         //순용역료
  $("#resStdServcCost").text(stdServcCost);           //표준용역료
  $("#resDcRt").text(dcRt);                           //할인율
  $("#resMatrCost").text(matrCost);                   //자재비
  $("#resCnstCost").text(cnstCost);                   //예상공사비
  $("#resCnvrsnCost").text(cnvrsnCost);               //환산료
  $("#resPrdAplyRt").text(prdAplyRt);                 //기간적용률
  $("#resCtrtInflowAplyRt").text(ctrtInflowAplyRt);   //계약유입적용률
  $("#resHighArpuRt").text(highArpuRt);               //고ARPU률
  $("#resPnlPrd").text(pnlPrd);                       //손익기간
  $("#resAllwncReflctAmt").text(allwncReflctAmt);     //수당반영료
  $("#resSacReflctRt").text(sacReflctRt);             //SAC반영률
  $("#resThsMthAvgPnlPrd").text(thsMthAvgPnlPrd);     //당월평균손익
  $("#resThsYrAvgPnlPrd").text(thsYrAvgPnlPrd);       //당해평균손익
  $("#resStdRtinErn").text(stdRtinErn);               //표준유보금

  $("#enggRtinErn").val(enggRtinErn);                 //약정유보금
  $("#resRtinErnSum").text(rtinErnSum);               //유보금합계
  $("#resExtprodServcCost").text(extprodServcCost);   //부가서비스 용역료

  $("#resPfctGurd").text(insuFee);                    //퍼펙트가드
  $("#resFireServc").text(insuSubsAmt);               //화재보험

  $("#billcnstCost").val(billcnstCost);               //청구공사비
  $("#rmvlCost").val(rmvlCost);                       //철거비
  $("#deposit").val(deposit);                         //보증금
  $("#resTotServcCost").text(servcCost);              //총용역료

  // 기본정보 업종 공통코드 목록 설정
  if (!isEmpty(bizCndtCd)) {
      changeBizCndt($("#bizCndtCd"), $("#indutyCd"), indutyCd);
  }

  //퍼펙트 가드 업태/업종 출력
  $("#resBizCndtCd").text(bizCndtCdNm);
  $("#resIndutyCd").text(indutyNm);

  // 계약유형에 따른 타사코드 설정
  setOthco(ctrtTp);
}