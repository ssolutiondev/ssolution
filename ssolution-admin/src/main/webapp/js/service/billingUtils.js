var BillingConst = {
	SCREEN_LOCK : true,
	DISPLAY_LOADING_IMG : true
};

String.prototype.replaceAll = function(target, replacement) {
	return this.split(target).join(replacement);
};

/**
 * xxxx{0}yyyy{1}zzzz 식의 메시지에 파라미터를 순서대로 대입한 결과를 반환 
 * 
 * @param orgMsg 메시지 원형
 * @param msgParam 메시지 파라미터
 * @returns 완성된 메시지
 */
function buildMessage(orgMsg, msgParam) {
	if(!orgMsg) {
		return '';
	}
	if( !msgParam || ($.isArray(msgParam) && msgParam.length === 0) ) {
		return orgMsg;
	}
	for(var i in msgParam) {
		orgMsg = orgMsg.replace(/{\d}/, msgParam[i]);
	}
	return orgMsg;
}

/* jes_20180315 start */
function fnStringPad(token, length) {
    var str = "";
    for(var i=0; i<length; i++) {
    	str += token;
    }
    return str;
}
/* jes_20180315 end */

/**
*숫자와 콤마(,)만 입력가능
**/
function onlyNumAndComma(value) {
    if (value == null || value.length == 0) return '';
    var str = value.replace(/[^0-9/*/,]/g, '');
    return str;
}
/**
*combo value clear
**/
function commonClearCombo(divId){
    var $contents= $("#"+ divId +" :input");
    var node ,id;
    
    for(var i = 0 ; i < $contents.length ; i++){
        node = $contents.eq(i);
        id = node.attr('id');
        
        if(id == "" || id == "undefined") continue;
        
        if(node.prop('type').indexOf("select") > -1){
            $("#"+id).val("");
        }        
    }    
}

/**
 * _date1 ~ _date2의 기간(개월수) 반환
 *   ex) getMonthDiff('201801', '201802') = 2
 * 
 * @param _date1 시작월(YYYYMM)
 * @param _date2 종료월(YYYYMM)
 * @returns 기간(월단위)
 */
function getMonthDiff(_date1, _date2) {
	if(_date1 == null || _date1 == '') return '';
	if(_date2 == null || _date2 == '') return '';
	if(!/^(\d){6}$/.test(_date1)) return "invalid date";
	if(!/^(\d){6}$/.test(_date2)) return "invalid date";

    var yy1 = _date1.substr(0,4),
        mm1 = _date1.substr(4,2);

    var yy2 = _date2.substr(0,4),
   		mm2 = _date2.substr(4,2);

    if(yy1 == yy2) {
    	return Math.abs(mm1 - mm2) + 1;
    } else {
    	var yyDif = yy1 - yy2;
    	var mmDif = mm1 - mm2;
    	return Math.abs(yyDif*12 + mmDif) + 1
    }
}

/**
 * 기준날짜에서 계산일자를 더한 날짜를 반환
 * 
 * @param date 기준날짜
 * @param calcVal 계산일자
 * @returns 계산결과날짜
 */
function calcDate(date, calcVal) {
//	if(!date || calcVal == 0) {
	if(!date) {
		return date;
	}
	var y, m, d;
	if(typeof date === 'string') {
		var nDate = numberFormatter(date);
		if(nDate.length === 8) {
			y = Number(nDate.substr(0, 4));
			m = Number(nDate.substr(4, 2))-1;
			d = Number(nDate.substr(6));
			date = new Date(y, m, d);
			date.setDate(date.getDate() + calcVal);
		}
	} else if(typeof date.getDate === 'function') {
		date.setDate(date.getDate() + calcVal);
	}
	return date;
}

/**
 * 기준날짜에서 계산일자를 더한 날짜를 반환
 * 
 * @param date 기준날짜
 * @param calcVal 계산일자
 * @returns 계산결과날짜
 */
function calcMonth(date, calcVal) {
//	if(!date || calcVal == 0) {
	if(!date) {
		return date;
	}
	var y, m, d;
	if(typeof date === 'string') {
		var nDate = numberFormatter(date);
		if(nDate.length === 6 || nDate.length === 8) {
			y = Number(nDate.substr(0, 4));
			m = Number(nDate.substr(4, 2))-1;
			date = new Date(y, m, '01');
		}
	}
	var month = date.getMonth() + calcVal;
	var addYear = 0;
	if(month < 0) {
		month += 12;
		addYear -= 1;
	}
	date.setMonth(month);
	date.setYear(date.getFullYear() + addYear);

	return date;
}

function removeHtmlScriptChars(arg) {
	if(typeof arg == 'number' || !arg) {
		return arg;
	}
	arg = arg.replaceAll('>', '');
	arg = arg.replaceAll('<', '');
//	arg = arg.replaceAll('/', '');
	arg = arg.replaceAll('\'', '');
	arg = arg.replaceAll('"', '');

	return arg;
}

function executeBatch(param, url, retryInterval, maxRetryCnt, callbackFn) {
    $.ajax({
        url:url,
        type:'POST',
        data : param,
        dataType: 'json',
        global: false,
        async: false,
        success: function(data){
	        if(!isEmpty(data.batchLogVO.pgmExcSeqNo)) {

	            // data.batchLogVO이용해서 batchlog polling...
	            delete data.batchLogVO.regDate;
	            delete data.batchLogVO.chgDate;

	            pollingBatchStatus(data.batchLogVO, retryInterval, maxRetryCnt, callbackFn);
	        } else {
	            //에러메세지
	            swal('<spring:message code="MSG.M10.MSG00005"/>', "", "error"); // MSG.M10.MSG00005=처리에 실패했습니다. 관리자에게 문의해 주세요.
	        }
        },
        error: function(jqxhr) {
			if(jqxhr.status == '500'){
				if(jqxhr.hasOwnProperty('responseJSON') && jqxhr.responseJSON.exceptionMsg != null && jqxhr.responseJSON.exceptionMsg !=''){
					swal(jqxhr.responseJSON.exceptionMsg,"","error");
				} else {
					swal('<spring:message code="MSG.M10.MSG00005"/>',"","error"); // MSG.M10.MSG00005=처리에 실패했습니다. 관리자에게 문의해 주세요.
				}
			}else if(jqxhr.status == '401'){ //미인증
				if(timer) {
					clearInterval(timer);
				}
				swal({
					title: "세션 종료되어 로그아웃 되었습니다.",
					text: "로그인 페이지로 이동합니다.",
					type: "error",
					showCancelButton: false,
					confirmButtonClass: "btn-danger",
					confirmButtonText: "",
					closeOnConfirm: false
				},
				function() {
					location.href = "/system/login/login";
				});
			} else {
				swal('<spring:message code="MSG.M10.MSG00005"/>',"","error"); // MSG.M10.MSG00005=처리에 실패했습니다. 관리자에게 문의해 주세요.
			}
        }
    });
}

/** 
 * 사용자화면에서 배치상태코드가 진행중(BAT_PROC_STAT = 0)인동안 일정간격으로 재확인(서버에 request)하고, 
 *   진행중이 아닌 상태로 변하면 결과를 반환.
 * @param param 상태확인을 위해 DB조회시 필요한 params
 * @param retryInterval 재시도 간격
 * @param maxRetryCnt 재시도횟수 제한
 * @param callbackFn 콜백함수
 */
function pollingBatchStatus(param, retryInterval, maxRetryCnt, callbackFn) {

	if(!retryInterval) {
		retryInterval = 5;
	}
	if(!maxRetryCnt) {
		maxRetryCnt = 20;
	}

	if(BillingConst.SCREEN_LOCK) {
		setScreenLock('배치 작업중입니다.');
	}

	if(BillingConst.DISPLAY_LOADING_IMG) {
		if($('.loadingBox').css('display') == 'none') {
			loading('start');
		}
	}

	var id = setInterval(function() {

		var batProcStat = getBatchStatusOnetime(param);

		if( (batProcStat != null && batProcStat != '0') || --maxRetryCnt == 0) {
			clearInterval(id);
			param.batProcStat = batProcStat;
			if(callbackFn && typeof callbackFn == 'function') {
				if(BillingConst.SCREEN_LOCK) {
					releaseScreenLock();
				}
				callbackFn(param);
			}
		} else {
			if(batProcStat == '0') {
				// 배치상태가 실행중으로 확인되면 maxRetryCnt는 -1로 변경해서 횟수제한없는 루프처리.
				maxRetryCnt = -1;
			}
			if(BillingConst.DISPLAY_LOADING_IMG) {
				if($('.loadingBox').css('display') == 'none') {
					loading('start');
				}
			}
		}
	}, retryInterval);
}

/** 
 * 서버에서 배치상태코드가 진행중(BAT_PROC_STAT = 0)인동안 일정간격으로 재확인하고, 
 *    진행중이 아닌 상태로 변하면 결과를 반환.
 * @param param 상태확인을 위해 DB조회시 필요한 params
 * @return 배치상태코드  
 */
function getBatchStatus(param) {
	var batProcStat;
    $.ajax({
        url:'/billing/common/batch/log/getBatchStatus.json',
        type:'POST',
        data : param,
        dataType: 'json',
        global: false,
        success: function(data) {
        	batProcStat = data.batProcStat;
        },
        error: function(event) {
			if(event.responseJSON.exceptionMsg){
				swal(event.responseJSON.exceptionMsg,"","error");
			} else {
				swal('상태확인에 실패했습니다.',"","error");
			}
        }
    });

    return batProcStat;
}

/** 
 * 배치상태코드를 반환.
 * @param param 상태확인을 위해 DB조회시 필요한 params
 * @return 배치상태코드  
 */
function getBatchStatusOnetime(param) {
	var batProcStat;
    $.ajax({
        url:'/billing/common/batch/log/getBatchStatusOnetime.json',
        type:'POST',
        data : param,
        dataType: 'json',
        async: false,
        global: false,
        success: function(data) {
        	batProcStat = data.batProcStat;
        },
        error: function(event) {
			if(event.responseJSON.exceptionMsg){
				swal(event.responseJSON.exceptionMsg,"","error");
			} else {
				swal('상태확인에 실패했습니다.',"","error");
			}
        }
    });

    return batProcStat;
}

// in 페이지 Dim처리
function setScreenLock(msg) {
	if(!msg) {
		msg = '';
	}
    var dim = '<div id="dimMask" class="dimMask savana" style="z-index:3000; position:fixed; width:100%;"><span class="text" style="margin-top:40px;">' + msg + '<br/></span></div>';
  	$('#wrapper').append(dim);
  	$('#dimMask').show();
}

// in 페이지 Dim처리 삭제
function releaseScreenLock() {
	$('#dimMask, .dimMask.savana').remove();

	// hide wave image
	if($('.loadingBox').css('display') == 'block') {
		loading('stop');
	}
}

// datatables의 grid row를 초기화
function clearGrid(gridTableId) {
	var len = $('#' + gridTableId + ' thead tr th').length;
	$('#' + gridTableId + ' tbody').html('<tr class="odd"><td valign="top" colspan="' + len + '" class="dataTables_empty">데이터가 없습니다</td></tr>');
}

/*===========================납부계정정보 관련 START====================================================*/
var PYMACNTID_MAXSIZE = 10;
/* 납부계정정보 컴포넌트 셋팅
 * commonPymAcntInfo(buttonId, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm)
 *  (필수)buttonId        : 팝업버튼ID
 *  (필수)returnPymAcntId : 납부계정ID input box의 ID
 *  (필수)returnPymAcntNm : 납부계정명 input box의 ID
 *  (선택)returnCustId    : 고객ID를 리턴받아야 하는 경우 리턴받을 고객ID input box의 ID
 *  (선택)returnCustNm    : 고객명을 리턴받아야 하는 경우 리턴받을 고객명 input box의 ID
 */
function commonPymAcntInfo(buttonId, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm){
	initPymAcntInfo(1, buttonId, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);
}
//팝업에서 호출시
function commonPymAcntInfo2(buttonId, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm){
	initPymAcntInfo(2, buttonId, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);
}

//납부계정정보 컴포넌트 초기화
function initPymAcntInfo(flag, buttonId, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm){

	if(!isEmpty(buttonId)) {
		//button click
		$("#"+buttonId).on("click", function(e) {
			//getPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);
			callPopPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);
		});			
	}
	
	if(!isEmpty(returnPymAcntId) && !isEmpty(returnPymAcntNm)) {
		//property
		$("#"+returnPymAcntId).prop("maxlength",PYMACNTID_MAXSIZE);
		$("#"+returnPymAcntNm).prop("readonly" ,true);
		//keyup
		$("#"+returnPymAcntId).on("keyup", function(key) {
		    //영문+숫자만 가능
		    $(this).val($(this).val().replace(/[^!-z]/g,'').toUpperCase());
		});      
		//keydown
		$("#"+returnPymAcntId).on("keydown", function(key) {    
			//enter
		    if (key.keyCode == 13) {  
		    	getPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);            
		    }    	
		}); 
		//keyup
		$("#"+returnPymAcntId).on("keyup", function(key) {    
			//clear
			if ($(this).val().length < PYMACNTID_MAXSIZE) { 
				$("#"+returnPymAcntNm).val("");
				if(!isEmpty(returnCustId)) $("#"+returnCustId).val("");
				if(!isEmpty(returnCustNm)) $("#"+returnCustNm).val("");
			}   	
		}); 		
		//change   
		$("#"+returnPymAcntId).on("change", function() { 
			//clear
			if ($(this).val().length < PYMACNTID_MAXSIZE) { 
				$("#"+returnPymAcntNm).val("");
				if(!isEmpty(returnCustId)) $("#"+returnCustId).val("");
				if(!isEmpty(returnCustNm)) $("#"+returnCustNm).val("");
			}
			//입력된 값이 있을 경우 
		    if (!isEmpty($(this).val())) {  
		    	getPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);          
		    }    	
		}); 		
	}
}

//납부계정정보 가져오기
function getPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm){
	
	//clear
	$("#"+returnPymAcntNm).val("");
	if(!isEmpty(returnCustId)) $("#"+returnCustId).val("");
	if(!isEmpty(returnCustNm)) $("#"+returnCustNm).val("");	
            
    //납부계정ID에 입력값이 있으며 입력값 길이가 10일 경우 납부계정ID로 조회
    if(!isEmpty(returnPymAcntId) && $("#"+returnPymAcntId).val().length == PYMACNTID_MAXSIZE){
        
        var param = new Object();
        param.condPymInfoPymAcntId = $("#"+returnPymAcntId).val();
        
        $.ajax({
        	url:"/billing/common/pyminfo/pymAcntInfoSearch/getPymInfoForBilling.json",
            type:'POST',
            data : param,
            async: false, //동기식
            dataType: 'json',
            success: function(rtnData){
                
                if(rtnData.data != null ) {
                    if(!isEmpty(returnPymAcntId)){
                        $("#"+returnPymAcntId).val(rtnData.data.pymAcntId);
                    }
                    if(!isEmpty(returnPymAcntNm)){
                        $("#"+returnPymAcntNm).val(rtnData.data.pymAcntNm);
                    }  
                    if(!isEmpty(returnCustId)){
                        $("#"+returnCustId).val(rtnData.data.custId);
                    }            
                    if(!isEmpty(returnCustNm)){
                        $("#"+returnCustNm).val(rtnData.data.custNm);
                    }                       
                }else {
                    callPopPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);
                }               
            }
        });     
    }else { //아닐 경우 팝업 오픈
    	callPopPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm);    
    }

}

//납부계정조회 팝업 오픈
function callPopPymAcntInfo(flag, returnPymAcntId, returnPymAcntNm, returnCustId, returnCustNm){

    var param = new Object();
    
    //인풋 검색 값
    param.inputCustNm           = "";       //고객명
    param.inputCustId           = "";       //고객아이디
    param.inputBsnmCorpNm       = "";       //사업자 상호명
    param.inputBsnmRepstvNm     = "";       //사업자 대표자명
    
    /* jes_20180326 고객요청에 의한 수정 start */
    //param.inputPymAcntId        = $("#"+returnPymAcntId).val().trim();     //납부계정 아이디
    //param.inputPymAcntNm        = $("#"+returnPymAcntNm).val().trim();     //납부계정 명
    param.inputPymAcntId        = "";     //납부계정 아이디
    param.inputPymAcntNm        = "";     //납부계정 명
    /* jes_20180326 고객요청에 의한 수정 end */
    
    param.inputCtrtId           = "";       //계약 아이디
    param.inputSvcCmpsId        = "";       //회선 아이디
    param.inputBizNo            = "";       //사업자 번호
    param.inputRepstvTelNo      = "";       //대표자 전화번호
    
    //리턴받을 아이디
    param.returnSoId            = "";       //사업아이디
    param.returnCustId          = isEmpty(returnCustId)?"":returnCustId;    //고객아이디
    param.returnPymAcntId       = returnPymAcntId;        //납부계정아이디
    param.returnCustNm          = isEmpty(returnCustNm)?"":returnCustNm;    //고객명
    param.returnPymAcntNm       = returnPymAcntNm;        //납부계정명           

    //url, param
    if(flag == 1) {
    	commonPopup('/billing/common/pyminfo/pymAcntInfoSearch/billingPymInfoSearchPopup.ajax', param, 1200);	
    }else if(flag == 2) {
    	commonPopup2('/billing/common/pyminfo/pymAcntInfoSearch/billingPymInfoSearchPopup.ajax', param, 1200);
    }else {
    	//고객용 납부계정조회 팝업 - 사용안함
    	commonPopup('/customer/common/pyminfo/pymInfoSearch/pymInfoSearchPopup.ajax', param, 1200);	
    }
}
/*===========================납부계정정보 관련 END======================================================*/

/*===========================계약정보 관련 START====================================================*/
var CTRTID_MAXSIZE = 10;
/* 계약정보 컴포넌트 셋팅
 * commonCtrtInfo(buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId)
 *  (필수)buttonId        : 팝업버튼ID
 *  (필수)returnCtrtId 	 : 계약ID input box의 ID
 *  (선택)returnCustNm 	 : 고객명을 리턴받아야 하는 경우 리턴받을 고객명 input box의 ID
 *  (선택)returnCustId    : 고객ID를 리턴받아야 하는 경우 리턴받을 고객ID input box의 ID
 *  (선택)returnPymAcntId : 납부계정ID를 리턴받아야 하는 경우 리턴받을 고객명 input box의 ID
 */
function commonCtrtInfo(buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){
	initCtrtInfo(1, buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
}
//팝업에서 호출시
function commonCtrtInfo2(buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){
	initCtrtInfo(2, buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
}
//계약정보 컴포넌트 초기화
function initCtrtInfo(flag, buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){

	if(!isEmpty(buttonId)) {
		//button click
		$("#"+buttonId).on("click", function(e) {
			//getCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
			callPopCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
		});			
	}
	
	if(!isEmpty(returnCtrtId) ) {
		//property
		$("#"+returnCtrtId).prop("maxlength",CTRTID_MAXSIZE);
		if(!isEmpty(returnCustNm)) $("#"+returnCustNm).prop("readonly" ,true);
		if(!isEmpty(returnCustId)) $("#"+returnCustId).prop("readonly" ,true);
		if(!isEmpty(returnPymAcntId)) $("#"+returnPymAcntId).prop("readonly" ,true);
		//keyup
		$("#"+returnCtrtId).on("keyup", function(key) {
		    //영문+숫자만 가능
		    $(this).val($(this).val().replace(/[^!-z]/g,'').toUpperCase());
		});      
		//keydown
		$("#"+returnCtrtId).on("keydown", function(key) {    
			//enter
		    if (key.keyCode == 13) {  
		    	getCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);            
		    }    	
		}); 
		//keyup
		$("#"+returnCtrtId).on("keyup", function(key) {    
			//clear
			if ($(this).val().length < CTRTID_MAXSIZE) { 		
				if(!isEmpty(returnCustNm)) $("#"+returnCustNm).val("");
				if(!isEmpty(returnCustId)) $("#"+returnCustId).val("");
				if(!isEmpty(returnPymAcntId)) $("#"+returnPymAcntId).val("");
			}   	
		}); 		
		//change   
		$("#"+returnCtrtId).on("change", function() { 
			//clear
			if ($(this).val().length < CTRTID_MAXSIZE) { 
				if(!isEmpty(returnCustNm)) $("#"+returnCustNm).val("");
				if(!isEmpty(returnCustId)) $("#"+returnCustId).val("");
				if(!isEmpty(returnPymAcntId)) $("#"+returnPymAcntId).val("");	
			}
			//입력된 값이 있을 경우 
		    if (!isEmpty($(this).val())) {  
		    	getCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);          
		    }    	
		});		
	}
}

//계약정보 가져오기
function getCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){
	
	//clear
	if(!isEmpty(returnCustNm)) $("#"+returnCustNm).val("");
	if(!isEmpty(returnCustId)) $("#"+returnCustId).val("");
	if(!isEmpty(returnPymAcntId)) $("#"+returnPymAcntId).val("");		
            
    //계약ID에 입력값이 있으며 입력값 길이가 10일 경우 계약ID로 조회
    if(!isEmpty(returnCtrtId) && $("#"+returnCtrtId).val().length == CTRTID_MAXSIZE){
        
        var param = new Object();
        param.ctrtId = $("#"+returnCtrtId).val();
        
        $.ajax({
        	url:"/billing/common/pyminfo/pymAcntInfoSearch/getCtrtInfoForBilling.json",
            type:'POST',
            data : param,
            async: false, //동기식
            dataType: 'json',
            success: function(rtnData){
                
                if(rtnData.data != null ) {
                    if(!isEmpty(returnCtrtId)){
                        $("#"+returnCtrtId).val(rtnData.data.ctrtId);
                    }
                    if(!isEmpty(returnCustNm)){
                        $("#"+returnCustNm).val(rtnData.data.custNm);
                    }  
                    if(!isEmpty(returnCustId)){
                        $("#"+returnCustId).val(rtnData.data.custId);
                    }            
                    if(!isEmpty(returnPymAcntId)){
                        $("#"+returnPymAcntId).val(rtnData.data.pymAcntId);
                    }                       
                }else {
                    callPopCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
                }               
            }
        });     
    }else { //아닐 경우 팝업 오픈
    	callPopCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);    
    }

}

//계약조회 팝업 오픈
function callPopCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){

    var param = new Object();
    //인풋 검색 값
    param.inputCustNm       = "";           //고객명
    param.inputCustId       = "";           //고객아이디
    param.inputBsnmCorpNm   = "";           //사업자 상호명
    param.inputBsnmRepstvNm = "";           //사업자 대표자명
    param.inputPymAcntId    = "";           //납부계정 아이디
    param.inputPymAcntNm    = "";           //납부계정 명
    param.inputCtrtId       = $("#"+returnCtrtId).val();           //계약 아이디
    param.inputSvcCmpsId    = "";           //회선 아이디
    param.inputBizNo        = "";           //사업자 번호
    param.inputRepstvTelNo  = "";           //대표자 전화번호
    param.inputOrgTp        = "";           //영업/관리 구분  - 영업:SALES 관리 : MNG
    param.inputHqOrgId      = "";           //본부
    param.inputCnterOrgId   = "";           //센터
    param.inputBrOrgId      = "";           //지사
    param.inputUserId       = "";           //담당자
    
    //리턴 받을 아이디
    param.returnSoId        = "";           //사업아이디
    param.returnCtrtId      = isEmpty(returnCtrtId)?"":returnCtrtId;           //계약 아이디
    param.returnCustId      = isEmpty(returnCustId)?"":returnCustId;           //고객아이디
    param.returnPymAcntId   = isEmpty(returnPymAcntId)?"":returnPymAcntId;           //납부계정아이디
    param.returnCustNm      = isEmpty(returnCustNm)?"":returnCustNm;           //고객명
    param.returnPymAcntNm   = "";           //납부계정명    

    //url, param
    if(flag == 2) {
    	commonPopup2('/customer/common/ctrt/ctrt/ctrtSearchPopup.ajax', param, 1200); 	
    }else {
    	commonPopup('/customer/common/ctrt/ctrt/ctrtSearchPopup.ajax', param, 1200); 
    }
}
/*===========================계약정보 관련 END======================================================*/