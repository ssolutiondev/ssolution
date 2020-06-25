var searchParamOrgComboDisabledIds = [];
var orgComboDisabledIds = [];
var isComboInitComplete = false;
var isSearchParamComboInitComplete = false;

//////////////////////////////////////////////////////////////////
//첨부파일 - 파일변경
//////////////////////////////////////////////////////////////////

//첨부파일 파일변경 시 uuid clear
function openfile(obj) {
	$(obj).next("input[type=hidden][name=uuid]").val(""); 
}

//////////////////////////////////////////////////////////////////
//날짜관련 - 일수 or 개월수 계산 
//////////////////////////////////////////////////////////////////
/*
* param : pStartDate - 시작일
* param : pEndDate   - 마지막일
* param : pType      - 'D':일수, 'M':개월수 
*/
function getDateGap(pStartDate, pEndDate, pType) {
	var strSDT = new Date(pStartDate.substring(0,4),pStartDate.substring(4,6)-1,pStartDate.substring(6,8));
	var strEDT = new Date(pEndDate.substring(0,4),pEndDate.substring(4,6)-1,pEndDate.substring(6,8));
	var strTermCnt = 0;
   
	if(pType == 'D') {  //일수 차이
		strTermCnt = (strEDT.getTime()-strSDT.getTime())/(1000*60*60*24);
	} else {            //개월수 차이
		if(pEndDate.substring(0,4) == pStartDate.substring(0,4)) {
			strTermCnt = pEndDate.substring(4,6) * 1 - pStartDate.substring(4,6) * 1;
			strTermCnt = strTermCnt + 1;
		} else {
			strTermCnt = Math.round((strEDT.getTime()-strSDT.getTime())/(1000*60*60*24*365/12));
		}
	}
	return strTermCnt;
}

function fnClearValueAll(divId){
	var $contents= $("#"+ divId +" :input");
	var node ,id;

	for(var i = 0 ; i < $contents.length ; i++){
		node = $contents.eq(i);
		id = node.attr('id');
		
		if(id == "" || id == "undefined") continue;
		
	    if(node.prop('type') == "text" || node.prop('type') == "textarea" || node.prop('type') == "email" 
	    		|| node.prop('type') == "password" || node.prop('type') == "number" || node.prop('type') == "hidden"){
	    	node.val("");
	    }else if(node.prop('type').indexOf("select") > -1){
	    	$("#"+id +" option:eq(0)").prop("selected",true);
	    }else if(node.prop('type') == "checkbox"){
	    	$("#"+id).iCheck('uncheck');
	    }
	}
}

//////////////////////////////////////////////////////////////////
//조직정보관련 function 시작
//////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////
// 조직정보 - 조회조건
//////////////////////////////////////////////////////////////////

//jes_20180312
/**
 * 조직정보 - 조회조건 - 필요한 화면에서 활성화 처리
 */
function fnEnableOrgCombo() {
	//각 화면 IS_ORG_AUTH 가 true일 경우 풀어줄려고 했으나 없앰.
	if(typeof searchHqCd == 'object' && typeof searchCnterCd == 'object' && typeof searchBrCd == 'object') {
		domEnableds(["searchHqCd","searchCnterCd","searchBrCd"]);
	}	
}

/**
 * 조회조건 - 조직정보콤보 초기처리
 */
function fnInitSearchParamOrgCombo() {

	// 본부목록 최상위에 ::: 전체 ::: 추가후, ::: 전체 :::를 기본값으로 설정
	$('#searchHqCd').prepend('<option value="">::: 전체 :::</option>');
	$('#searchHqCd').prop('selectedIndex', 0);

	// 센터, 지사에 ::: 전체 ::: 를 추가
	fnInitOption(['searchCnterCd', 'searchBrCd'], 'SEARCH');

	// 본부 변경시 이벤트
	$('#searchHqCd').on('change', function(e) {
		var html = fnGenerateCnterOptionsHtml(this.value);

		fnInitOption(['searchCnterCd', 'searchBrCd'], 'SEARCH');

		// 차량은 본부목록에 0000이 추가되므로 아래와같이 분기...
		if(this.value == '0000') {
			domDisableds(['searchCnterCd', 'searchBrCd']);
		} else {
			$('#searchCnterCd').append(html);

			if(isSearchParamComboInitComplete) {
				if(searchParamOrgComboDisabledIds.indexOf('searchCnterCd') == -1) {
					domEnabled('searchCnterCd');
				}
				if(searchParamOrgComboDisabledIds.indexOf('searchBrCd') == -1) {
					domEnabled('searchBrCd');
				}
			}
		}
	});
	// 센터 변경시 이벤트
	$('#searchCnterCd').on('change', function(e) {
		var html = fnGenerateBrOptionsHtml(this.value, 'SEARCH');

		fnInitOption(['searchBrCd'], 'SEARCH');
		$('#searchBrCd').append(html);
	});
}


function fnInitSearchParamOrgComboPopup() {

	// 본부목록 최상위에 ::: 전체 ::: 추가후, ::: 전체 :::를 기본값으로 설정
	$('#searchHqCdPopup').prepend('<option value="">::: 전체 :::</option>');
	$('#searchHqCdPopup').prop('selectedIndex', 0);

	// 센터, 지사에 ::: 전체 ::: 를 추가
	fnInitOption(['searchCnterCdPopup', 'searchBrCdPopup'], 'SEARCH');

	// 본부 변경시 이벤트
	$('#searchHqCdPopup').on('change', function(e) {
		var html = fnGenerateCnterOptionsHtml(this.value);

		fnInitOption(['searchCnterCdPopup', 'searchBrCdPopup'], 'SEARCH');

		// 차량은 본부목록에 0000이 추가되므로 아래와같이 분기...
		if(this.value == '0000') {
			domDisableds(['searchCnterCdPopup', 'searchBrCdPopup']);
		} else {
			$('#searchCnterCdPopup').append(html);

			if(isSearchParamComboInitComplete) {
				if(searchParamOrgComboDisabledIds.indexOf('searchCnterCdPopup') == -1) {
					domEnabled('searchCnterCdPopup');
				}
				if(searchParamOrgComboDisabledIds.indexOf('searchBrCdPopup') == -1) {
					domEnabled('searchBrCdPopup');
				}
			}
		}
	});
	// 센터 변경시 이벤트
	$('#searchCnterCdPopup').on('change', function(e) {
		var html = fnGenerateBrOptionsHtml(this.value, 'SEARCH');

		fnInitOption(['searchBrCdPopup'], 'SEARCH');
		$('#searchBrCdPopup').append(html);
	});
}

/**
 * 조회조건 - 조직정보 초기처리
 * 
 * @param hqCd 본부
 * @param cnterCd 센터
 * @param brCd 지사
 */
function fnInitSearchParamOrgComboVal(hqCd, cnterCd, brCd) {
	if(hqCd) {
		searchParamOrgComboDisabledIds.push('searchHqCd'); // save disabled id
		$('#searchHqCd').val(hqCd);
		$('#searchHqCd').trigger('change');
	} else {
		$('#searchHqCd').prop('disabled', false);
	}
	// 0000선택시, 센터/지사 목록조회도 하지 않고, 값 설정도 하지않음.
	if(hqCd == '0000') {
		return;
	}
	if(cnterCd) {
		searchParamOrgComboDisabledIds.push('searchCnterCd'); // save disabled id
		$('#searchCnterCd').val(cnterCd);
		$('#searchCnterCd').trigger('change');
	} else {
		$('#searchCnterCd').prop('disabled', false);
	}
	if(brCd) {
		searchParamOrgComboDisabledIds.push('searchBrCd'); // save disabled id
		$('#searchBrCd').val(brCd);
	} else {
		$('#searchBrCd').prop('disabled', false);
	}
	isSearchParamComboInitComplete = true;
}

function fnInitSearchParamOrgComboValPopup(hqCd, cnterCd, brCd) {
	if(hqCd) {
		searchParamOrgComboDisabledIds.push('searchHqCdPopup'); // save disabled id
		$('#searchHqCdPopup').val(hqCd);
		$('#searchHqCdPopup').trigger('change');
	} else {
		$('#searchHqCdPopup').prop('disabled', false);
	}
	// 0000선택시, 센터/지사 목록조회도 하지 않고, 값 설정도 하지않음.
	if(hqCd == '0000') {
		return;
	}
	if(cnterCd) {
		searchParamOrgComboDisabledIds.push('searchCnterCdPopup'); // save disabled id
		$('#searchCnterCdPopup').val(cnterCd);
		$('#searchCnterCdPopup').trigger('change');
	} else {
		$('#searchCnterCdPopup').prop('disabled', false);
	}
	if(brCd) {
		searchParamOrgComboDisabledIds.push('searchBrCdPopup'); // save disabled id
		$('#searchBrCdPopup').val(brCd);
	} else {
		$('#searchBrCdPopup').prop('disabled', false);
	}
	isSearchParamComboInitComplete = true;
}


//////////////////////////////////////////////////////////////////
// 조직정보 - 입력항목
//////////////////////////////////////////////////////////////////
function fnInitOrgCombo() {

	// 본부목록 최상위에 ::: 선택 ::: 추가후, ::: 선택 ::: 을 기본값으로 설정
	$('#hqCd').prepend('<option value="">::: 선택 :::</option>');
	$('#hqCd').prop('selectedIndex', 0);

	// 센터, 지사에 ::: 선택 ::: 을 추가
	fnInitOption(['cnterCd', 'brCd'], 'INPUT');

	// 본부 변경시
	$('#hqCd').on('change', function(e) {
		var html = fnGenerateCnterOptionsHtml(this.value);

		fnInitOption(['cnterCd', 'brCd'], 'INPUT');

		// 차량은 본부목록에 0000이 추가되므로 아래와같이 분기...
		if(this.value == '0000') {
			domDisableds(['cnterCd', 'brCd']);
		} else {
			$('#cnterCd').append(html);

			if(isComboInitComplete) {
				if(orgComboDisabledIds.indexOf('cnterCd') == -1) {
					domEnabled('cnterCd');
				}
				if(orgComboDisabledIds.indexOf('brCd') == -1) {
					domEnabled('brCd');
				}
			}
		}
	});

	// 센터 변경시
	$('#cnterCd').on('change', function(e) {
		var html = fnGenerateBrOptionsHtml(this.value, 'INPUT');

		fnInitOption(['brCd'], 'INPUT');
		$('#brCd').append(html);
	});
}

//화면 초기처리시 orgCombo의 각 항목들에 값이 있는경우 초기값으로 설정, 없는경우 disabled속성 해제(선택가능)
function fnSetOrgComboVal(hqCd, cnterCd, brCd) {
	if(hqCd) {
//		orgComboDisabledIds.push('hqCd'); // save disabled id
		$('#hqCd').val(hqCd);
		$('#hqCd').trigger('change');
	} else {
		$('#hqCd').prop('disabled', false);
	}
	if(cnterCd) {
//		orgComboDisabledIds.push('cnterCd'); // save disabled id
		if($.trim(cnterCd) != '0000') {
			$('#cnterCd').val(cnterCd);
		}
		$('#cnterCd').trigger('change');
	} else {
		if(!$('#hqCd').prop('disabled')) {
			$('#cnterCd').prop('disabled', false);
		}
	}
	if(brCd) {
//		orgComboDisabledIds.push('brCd'); // save disabled id
		$('#brCd').val(brCd);
	} else {
		if(!$('#cnterCd').prop('disabled')) {
			$('#brCd').prop('disabled', false);
		}
	}
	isComboInitComplete = true;
}

//서버를통해 선택된 조직의 하위조직목록을 가져와 selectbox option으로 구성
function fnGetSubOrgIds(orgId, tpDtlCd) {

	var returnVal;

	if(orgId && tpDtlCd){
		var param = new Object();
		param.tpDtlCd = tpDtlCd;
		param.orgId = orgId;

		$.ajax({
			url:'/vehicle/common/searchOrgIdListAction.json',
			type:'POST',
			data : param,
			async : false,
			dataType: 'json',
			success: function(data){
				returnVal = data.data;
			}
		});
	}
	return returnVal;
}

//본부 선택시 센터 select option html 생성
function fnGenerateCnterOptionsHtml(orgId) {
	var html = '';
	var list = fnGetSubOrgIds(orgId, '1006');
	if(list && typeof list == 'object') {
		for(var i=0; i < list.length; i++) {
			var commonVO = list[i];
			html += '<option value="' + commonVO.orgId + '">' + commonVO.orgNm + '</option>';
		}
	}
	return html;
}

//센터 선택시 지사 select option  html 생성
function fnGenerateBrOptionsHtml(orgId) {
	var html = '';
	var list = fnGetSubOrgIds(orgId, '1007');
	if(list && typeof list == 'object') {
		for(var i=0; i < list.length; i++) {
			var commonVO = list[i];
			html += '<option value="' + commonVO.orgId + '">' + commonVO.orgNm + '</option>';
		}
	}
	return html;
}

// INPUT, SEARCH로 입력값인지, 조회조건인지 구분해서 기본옵션html 생성해서 반환
function fnInitOption(tgtIds, which) {
	if(tgtIds) {
		for(var i=0; i < tgtIds.length; i++) {
			var tgtId = tgtIds[i];
			switch(which) {
			case 'INPUT':
				$('#' + tgtId).html('<option value=""> ::: 선택 ::: </option>'); break;
			case 'SEARCH':
				$('#' + tgtId).html('<option value=""> ::: 전체 ::: </option>'); break;
			}
		}
	}
}
//////////////////////////////////////////////////////////////////
//조직정보관련 function 끝
//////////////////////////////////////////////////////////////////