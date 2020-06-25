/*===========================조직정보 관련 START====================================================*/

/* 조직정보 컴포넌트 셋팅
 * commonOrgInfo(buttonId, returnOrgId, returnOrgNm)
 *  (필수)buttonId        : 팝업버튼ID
 *  (필수)returnOrgId     : 조직ID input box의 ID
 *  (필수)returnOrgNm     : 조직명 input box의 ID
 *  
 */
function commonOrgInfo(buttonId, returnOrgId, returnOrgNm ){
	initOrgInfo(1, buttonId, returnOrgId, returnOrgNm);
}
//팝업에서 호출시
function commonOrgInfo2(buttonId, returnOrgId, returnOrgNm ){
	initOrgInfo(2, buttonId, returnOrgId, returnOrgNm );
}
//조직정보 컴포넌트 초기화
function initOrgInfo(flag, buttonId, returnOrgId, returnOrgNm){

	if(!isEmpty(buttonId)) {
		//조직 검색 팝업 - 조회영역
		$("#"+buttonId).on("click", function(e) {
			callPopOrgInfo(flag, returnOrgId, returnOrgNm);
		});			
	}
	
	if(!isEmpty(returnOrgId) && !isEmpty(returnOrgNm)) {
		$("#"+returnOrgId).prop("maxlength","10");
		$("#"+returnOrgNm).prop("readonly" ,true);
		$("#"+returnOrgId).on("keyup", function(key) {
		    //영문+숫자만 가능
		    $(this).val($(this).val().replace(/[^!-z]/g,'').toUpperCase());
		});      
		//조직ID keydown
		$("#"+returnOrgId).on("keydown", function(key) {    
			//clear
			$("#"+returnOrgNm).val("");
			//enter
		    if (key.keyCode == 13) {  
		    	getOrgInfo(flag, returnOrgId, returnOrgNm);            
		    }    	
		}); 
		//조직ID change
		$("#"+returnOrgId).on("change", function() { 
			//clear
			$("#"+returnOrgNm).val("");
			//입력된 값이 있을 경우 
		    if (!isEmpty($(this).val())) {  
		    	getOrgInfo(flag, returnOrgId, returnOrgNm);          
		    }    	
		}); 		
	}
}

//조직정보 가져오기
function getOrgInfo(flag, returnOrgId, returnOrgNm){
            
    //조직ID에 입력값이 있으며 입력값 길이가 10일 경우 조직ID로 조회
    if(!isEmpty(returnOrgId) ){
        
        var param = new Object();
        
        param.orgId        = $("#"+returnOrgId).val();
        param.tpCd         = "ALL";
        param.tpDtlCd      = "ALL";
        param.searchOption = "ALL";
        
        
        $.ajax({
        	url:"/allowance/organization/orgMng/orgInfoMng/orgSearchForApproval.json",
            type:'POST',
            data : param,
            async: false, //동기식
            dataType: 'json',
            success: function(rtnData){
                
                if(rtnData.data != null ) {
                    if(!isEmpty(returnOrgId)){
                        $("#"+returnOrgId).val(rtnData.data.orgId);
                    }
                    if(!isEmpty(returnOrgNm)){
                        $("#"+returnOrgNm).val(rtnData.data.orgNm);
                    }  
                }else {
                	callPopOrgInfo(flag, returnOrgId, returnOrgNm);
                }               
            }
        });     
    }else { //아닐 경우 팝업 오픈
    	callPopOrgInfo(flag, returnOrgId, returnOrgNm);    
    }

}

//조직조회 팝업 오픈
function callPopOrgInfo(flag, returnOrgId, returnOrgNm){

    var param = new Object();
    
    //인풋 검색 값
//    param.orgId         = $("#"+returnOrgId).val().trim();     //조직 아이디
//    param.orgNm         = $("#"+returnOrgNm).val().trim();     //조직 명
    
    param.orgId         = returnOrgId ;     //조직 아이디 
    param.orgNm         = returnOrgNm ;     //조직 명
    param.tpCd          = "ALL";
    param.tpDtlCd       = "ALL";
    param.searchOption  = ""; // ALL 이면 전체 나옴
    
    //url, param
    if(flag == 2) {
    	commonPopup2('/allowance/organization/orgMng/orgInfoMng/orgSearchPopup.ajax', param,'1000');
    }else {
    	commonPopup('/allowance/organization/orgMng/orgInfoMng/orgSearchPopup.ajax', param,'1000');
    }
}
/*===========================조직정보 관련 END======================================================*/

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
function draftRegCtrtInfo(buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){
	initDraftRegCtrtInfo(1, buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
}
//팝업에서 호출시
function draftRegCtrtInfo2(buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){
	initDraftRegCtrtInfo(2, buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
}
//계약정보 컴포넌트 초기화
function initDraftRegCtrtInfo(flag, buttonId, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){

	if(!isEmpty(buttonId)) {
		//button click
		$("#"+buttonId).on("click", function(e) {
			//getDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
			callPopDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
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
		    	getDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);            
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
		    	getDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);          
		    }    	
		});		
	}
}

//계약정보 가져오기
function getDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){
	
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
                    callPopDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);
                }               
            }
        });     
    }else { //아닐 경우 팝업 오픈
    	callPopDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId);    
    }

}

//계약조회 팝업 오픈
function callPopDraftRegCtrtInfo(flag, returnCtrtId, returnCustNm, returnCustId, returnPymAcntId){

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
    	commonPopup2('/approval/approval/draftMng/draftRegCtrtSearch/draftRegCtrtSearchPopup.ajax', param, 1200); 	
    }else {
    	commonPopup('/approval/approval/draftMng/draftRegCtrtSearch/draftRegCtrtSearchPopup.ajax', param, 1200); 
    }
}
/*===========================계약정보 관련 END======================================================*/