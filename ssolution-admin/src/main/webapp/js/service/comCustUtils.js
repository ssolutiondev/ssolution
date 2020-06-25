var onPopupOrgDisabledIds = [];
var onPopupIsComboInitComplete = false;



//////////////////////////////////////////////////////////////////
//조직정보관련 function p_시작
//////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////
// 조직정보 - 조회조건
//////////////////////////////////////////////////////////////////
/**
 * 조회조건 - 조직정보콤보 초기처리
 */
function p_fnInitSearchParamOrgCombo() {

	// 본부목록 최상위에 ::: 전체 ::: 추가후, ::: 전체 :::를 기본값으로 설정
	$('#searchHqOrgIdP').prepend('<option value="">::: 전체 :::</option>');
	$('#searchHqOrgIdP').prop('selectedIndex', 0);

	// 센터, 지사에 ::: 전체 ::: 를 추가
	p_fnInitOption(['searchCnterOrgIdP', 'searchBrOrgIdP'], 'SEARCH');

	// 본부 변경시 이벤트
	$('#searchHqOrgIdP').on('change', function(e) {
		var html = p_fnGenerateCnterOptionsHtml(this.value);

		p_fnInitOption(['searchCnterOrgIdP', 'searchBrOrgIdP'], 'SEARCH');

		// 차량은 본부목록에 0000이 추가되므로 아래와같이 분기...
		if(this.value == '0000') {
			domDisableds(['searchCnterOrgIdP', 'searchBrOrgIdP']);
		} else {
			$('#searchCnterOrgIdP').append(html);

			if(onPopupIsComboInitComplete) {
				if(onPopupOrgDisabledIds.indexOf('searchCnterOrgIdP') == -1) {
					domEnabled('searchCnterOrgIdP');
				}
				if(onPopupOrgDisabledIds.indexOf('searchBrOrgIdP') == -1) {
					domEnabled('searchBrOrgIdP');
				}
			}
		}
	});
	// 센터 변경시 이벤트
	$('#searchCnterOrgIdP').on('change', function(e) {
		var html = p_fnGenerateBrOptionsHtml(this.value, 'SEARCH');

		p_fnInitOption(['searchBrOrgIdP'], 'SEARCH');
		$('#searchBrOrgIdP').append(html);
	});
}

/**
 * 조회조건 - 조직정보 초기처리
 *
 * @param hqCd 본부
 * @param cnterCd 센터
 * @param brCd 지사
 */
function p_fnInitSearchParamOrgComboVal(hqCd, cnterCd, brCd) {
	if(hqCd) {
		onPopupOrgDisabledIds.push('searchHqOrgIdP'); // save disabled id
		$('#searchHqOrgIdP').val(hqCd);
		$('#searchHqOrgIdP').trigger('change');
	} else {
		$('#searchHqOrgIdP').prop('disabled', false);
	}
	// 0000선택시, 센터/지사 목록조회도 하지 않고, 값 설정도 하지않음.
	if(hqCd == '0000') {
		return;
	}
	if(cnterCd) {
		onPopupOrgDisabledIds.push('searchCnterOrgIdP'); // save disabled id
		$('#searchCnterOrgIdP').val(cnterCd);
		$('#searchCnterOrgIdP').trigger('change');
	} else {
		$('#searchCnterOrgIdP').prop('disabled', false);
	}
	if(brCd) {
		onPopupOrgDisabledIds.push('searchBrOrgIdP'); // save disabled id
		$('#searchBrOrgIdP').val(brCd);
	} else {
		$('#searchBrOrgIdP').prop('disabled', false);
	}
	onPopupIsComboInitComplete = true;
}



//서버를통해 선택된 조직의 하위조직목록을 가져와 selectbox option으로 구성
function p_fnGetSubOrgIds(orgId, tpDtlCd) {

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
function p_fnGenerateCnterOptionsHtml(orgId) {
	var html = '';
	var list = p_fnGetSubOrgIds(orgId, '1006');
	if(list && typeof list == 'object') {
		for(var i=0; i < list.length; i++) {
			var commonVO = list[i];
			html += '<option value="' + commonVO.orgId + '">' + commonVO.orgNm + '</option>';
		}
	}
	return html;
}

//센터 선택시 지사 select option  html 생성
function p_fnGenerateBrOptionsHtml(orgId) {
	var html = '';
	var list = p_fnGetSubOrgIds(orgId, '1007');
	if(list && typeof list == 'object') {
		for(var i=0; i < list.length; i++) {
			var commonVO = list[i];
			html += '<option value="' + commonVO.orgId + '">' + commonVO.orgNm + '</option>';
		}
	}
	return html;
}

// INPUT, SEARCH로 입력값인지, 조회조건인지 구분해서 기본옵션html 생성해서 반환
function p_fnInitOption(tgtIds, which) {
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
//조직정보관련 function p_끝
//////////////////////////////////////////////////////////////////