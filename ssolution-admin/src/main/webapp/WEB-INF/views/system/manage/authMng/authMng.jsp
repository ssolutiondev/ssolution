<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<script type="text/javascript">

var groupList;				//groupList
var isTablesDrawed = false;
var nodeData;				//선택된 트리 데이터
var menuSearchList = [];	//트리 전체 검색 데이터

$(document).ready(function(){

	groupList = $('#groupList').DataTable( {
		processing: true,
		serverSide: false,
		scrollY: "453px",
		scrollCollapse: false,
		dom : '<t>',	//보여줄 내용 처리
		select: 'single',
		order :[],
		language: {
			url: getTableLngUrl()
		}, //필수
		pageLength : -1,
		columns: [
			{ data: "authGrpId" },
			{ data: "authGrpNm" }
		],
		initComplete: function( settings, json ){
			if(isTablesDrawed == false){
				isTablesDrawed = true;
			}
		},
		columnDefs: [
			{ className: "text-center", targets: [0] }
		],
		ajax: {
			url: "/system/authMng/authMng/authMng/getAuthGroupListAction.json",
			type: "post",
			data : function(d){
				d.authGrpId = $("#condAuthGrpId").val();
				d.authGrpNm = $("#condAuthGrpNm").val();
				d.isTablesDrawed = isTablesDrawed;
			}
		}
	});

	//그리드 row 클릭 이벤트
	groupList.on('select', function(e, dt, type, indexs){
		var row = $('#groupList').DataTable().rows('.selected').data();
		setSelectedData(row[0]);
	});

	//클릭 해지 이벤트 방지
	groupList.on('user-select', function (e, dt, type, cell, originalEvent) {
	    if ($(cell.node()).parent().hasClass('selected')) {
	    	var row = $('#groupList').DataTable().rows('.selected').data();
			setSelectedData(row[0]);
	        e.preventDefault();
	    }
	});

	//트리초기화
	$("#menuTree").jstree({
		core : {
			data : [{ "text" : "/", "id": "0", "icon" : "fa fa-folder" }]
		}
	});

	//검색
	$("#btnSearchGroup").on("click", function(e) {
		searchGroup();
	});

	$("#condAuthGrpId").keypress(function(event) {
   		if(event.keyCode == 13){
   			searchGroup();
   		}
   	});

	$("#condAuthGrpNm").keypress(function(event) {
   		if(event.keyCode == 13){
   			searchGroup();
   		}
   	});

	//할당메뉴만 조회 체크박스 이벤트
	$("#condAsgnYn").on("ifChanged", function(e){
		setMenuTree("0");
	});

	//트리 검색 이벤트
	$("#condMenu").keypress(function(event) {
   		if(event.keyCode == 13){
   			searchTreeMenu();
   		}
   	});

	//권한 체크박스 이벤트
	$("#areaCheckBox input[type=checkbox]").on("ifClicked", function(e) {
		changeCheckBox(this);
    });

	//수정
	$("#btnUpdate").on("click", function(e) {
		updateAuthInfo();
	});

});

//권한그룹 검색
function searchGroup(){
	groupList.clear();
	groupList.ajax.reload();

	AuthInfoInitiation();
	setMenuTree("0");
}

//그룹 선택
function setSelectedData(data){

	$("#authGrpDesc").val(data.authGrpDesc);
	$("#mainView").val(data.mainView);

	AuthInfoInitiation();
	setMenuTree("0");
}

//메뉴트리
function setMenuTree(id){

	var row = $('#groupList').DataTable().rows('.selected').data();
	var rowData = row[0];

	if(isEmpty(rowData)){
		//트리초기화
		$("#menuTree").jstree('destroy');
		$("#menuTree").jstree({
			core : {
				data : [{ "text" : "/", "id" : "0", "icon" : "fa fa-folder" }]
			}
		});
		return;
	}

	var param = new Object();
	param.authGrpId = rowData.authGrpId;
	param.condAsgnYn = ($('#condAsgnYn').prop("checked") ? 'Y' : 'N');

	$.ajax({
		url : '/system/authMng/authMng/authMng/getAuthListAction.json',
		type : 'POST',
		data : param,
		dataType : 'json',
		success : function(data) {

			menuSearchList = data[0].menuSearchList;

			domDisableds(["btnUpdate"]);

			$("#condMenu").val("");

			$("#menuTree").jstree('destroy');

			//트리초기화
			$("#menuTree").jstree({
				core : {
					data : data
				},
				plugins : [	
					"search"
				]
			}).bind('select_node.jstree', function(event, data){

				$("#menuTree").jstree("open_node", "#"+data.node.original.id);

				if(data.node.id == "0"){
					AuthInfoInitiation();
				}else{
					nodeData = data;

					$('#menuId').val(data.node.original.id);
					$('#menuNm').val(data.node.original.text);

					//자식 노드가 없을 경우
					if(isEmpty(data.node.children)){
						//domEnableds(["chkAuthAll", "chkAuthR", "chkAuthC", "chkAuthU", "chkAuthD", "chkAuthP"]);
						$("#chkAuthAll").iCheck('enable');
						$("#chkAuthR").iCheck('enable');
						$("#chkAuthC").iCheck('enable');
						$("#chkAuthU").iCheck('enable');
						$("#chkAuthD").iCheck('enable');
						$("#chkAuthP").iCheck('enable');

					}else{
						//버튼비활성화
						//domEnableds(["chkAuthAll"]);
						//버튼활성화
						//domDisableds(["chkAuthR", "chkAuthC", "chkAuthU", "chkAuthD", "chkAuthP"]);
						$("#chkAuthAll").iCheck('enable');
						$("#chkAuthR").iCheck('disable');
						$("#chkAuthC").iCheck('disable');
						$("#chkAuthU").iCheck('disable');
						$("#chkAuthD").iCheck('disable');
						$("#chkAuthP").iCheck('disable');
					}

					setCheckbox(data.node.original.authAll, "chkAuthAll");
					setCheckbox(data.node.original.authInq, "chkAuthR");
					setCheckbox(data.node.original.authReg, "chkAuthC");
					setCheckbox(data.node.original.authChg, "chkAuthU");
					setCheckbox(data.node.original.authDel, "chkAuthD");
					setCheckbox(data.node.original.authPrt, "chkAuthP");

					domEnableds(["btnUpdate"]);
				}
			}).bind('loaded.jstree', function(event, data){
				$("#menuTree").jstree("select_node", "#"+id);
			});
		}
	});

}

function setCheckbox(value, id){

	if(value == 'Y'){
		$('#'+id).iCheck('check');
	}else{
		$('#'+id).iCheck('uncheck');
	}
}

//트리내 검색
function searchTreeMenu(){

	var text = $("#condMenu").val();
	var selectNode = $("#menuTree").jstree('get_selected');
	var searchIndex = 0;
	var searchBl = true;

	$("#menuTree").jstree('deselect_all');

	for(var i=0; i<menuSearchList.length; i++){
		if(menuSearchList[i].id == selectNode[0]){
			searchIndex = i;
			break;
		}
	}

	for(var j=searchIndex+1; j<menuSearchList.length; j++){
		if(menuSearchList[j].searchKey.indexOf(text) > -1){
			$("#menuTree").jstree("select_node", "#"+menuSearchList[j].id);
			searchBl = false;
			break;
		}
	}

	if(searchBl){
		$("#menuTree").jstree("select_node", "#"+"0");
	}

	//선택된 노드로 스크롤 이동
	$("#menuTree").scrollTop(0);

	var id = $("#menuTree").jstree('get_selected')[0]
	var top = $("#menuTree #"+id).offset().top;

	$("#menuTree").scrollTop(top - $("#menuTree").offset().top - 5);

}

//권한 체크박스 이벤트
function changeCheckBox(obj){

	if($(obj).attr("id") == "chkAuthAll"){

		var chkAuthAll = ($('#chkAuthAll').prop("checked") ? 'Y' : 'N');

		if(chkAuthAll == "N"){
			setCheckbox("Y", "chkAuthR");
			setCheckbox("Y", "chkAuthC");
			setCheckbox("Y", "chkAuthU");
			setCheckbox("Y", "chkAuthD");
			setCheckbox("Y", "chkAuthP");

		}else{
			setCheckbox("N", "chkAuthR");
			setCheckbox("N", "chkAuthC");
			setCheckbox("N", "chkAuthU");
			setCheckbox("N", "chkAuthD");
			setCheckbox("N", "chkAuthP");
		}

	}else{
		var chkAuthR = ($('#chkAuthR').prop("checked") ? 'Y' : 'N');
		var chkAuthC = ($('#chkAuthC').prop("checked") ? 'Y' : 'N');
		var chkAuthU = ($('#chkAuthU').prop("checked") ? 'Y' : 'N');
		var chkAuthD = ($('#chkAuthD').prop("checked") ? 'Y' : 'N');
		var chkAuthP = ($('#chkAuthP').prop("checked") ? 'Y' : 'N');

		if($(obj).attr("id") == "chkAuthR"){
			chkAuthR = ($('#chkAuthR').prop("checked") ? 'N' : 'Y');
		}
		if($(obj).attr("id") == "chkAuthC"){
			chkAuthC = ($('#chkAuthC').prop("checked") ? 'N' : 'Y');
		}
		if($(obj).attr("id") == "chkAuthU"){
			chkAuthU = ($('#chkAuthU').prop("checked") ? 'N' : 'Y');
		}
		if($(obj).attr("id") == "chkAuthD"){
			chkAuthD = ($('#chkAuthD').prop("checked") ? 'N' : 'Y');
		}
		if($(obj).attr("id") == "chkAuthP"){
			chkAuthP = ($('#chkAuthP').prop("checked") ? 'N' : 'Y');
		}

		if(chkAuthR == 'Y' && chkAuthC == 'Y' && chkAuthU == 'Y' && chkAuthD == 'Y' && chkAuthP =='Y'){
			setCheckbox("Y", "chkAuthAll");

		}else{
			setCheckbox("N", "chkAuthAll");
		}
	}
}

//변경처리
function updateAuthInfo(){

	var row = $('#groupList').DataTable().rows('.selected').data();
	var rowData = row[0];

	var param = new Object();

	param.authGrpId = rowData.authGrpId;
	param.menuId = nodeData.node.id;
	param.lvl = nodeData.node.original.level;
	param.authInq = $('#chkAuthR').prop("checked") ? 'Y' : 'N';
	param.authReg = $('#chkAuthC').prop("checked") ? 'Y' : 'N';
	param.authChg = $('#chkAuthU').prop("checked") ? 'Y' : 'N';
	param.authDel = $('#chkAuthD').prop("checked") ? 'Y' : 'N';
	param.authPrt = $('#chkAuthP').prop("checked") ? 'Y' : 'N';
	param.inptMenuId = $("#headerCurMenuId").val();

	//상위 레벨 메뉴 삭제 처리시 확인 메세지
	if(param.authInq =='N' && param.authReg == 'N' && param.authChg == 'N' && param.authDel == 'N' && param.authPrt == 'N'){

		swalConfirm('<spring:message code="MSG.M09.MSG00016" />', "warning", function(){
			updateAuthInfoStep2(param);
		});
	}else{
		updateAuthInfoStep2(param);
	}
}

function updateAuthInfoStep2(param){

	swalConfirm('<spring:message code="MSG.M01.MSG00069" />', "warning", function(){

		$.ajax({
	        url : '/system/authMng/authMng/authMng/updateAuthAction.json',
	        type : 'POST',
	        data : param,
	        dataType: 'json',
	        success: function(data){

	        	AuthInfoInitiation();
	        	setMenuTree(param.menuId);

	        	swal('<spring:message code="MSG.M09.MSG00005"/>',"","success");
	        }
	    });
	});

}

function AuthInfoInitiation(){

	$("#chkAuthAll").iCheck('disable');
	$("#chkAuthR").iCheck('disable');
	$("#chkAuthC").iCheck('disable');
	$("#chkAuthU").iCheck('disable');
	$("#chkAuthD").iCheck('disable');
	$("#chkAuthP").iCheck('disable');

	setCheckbox("N", "chkAuthAll");
	setCheckbox("N", "chkAuthR");
	setCheckbox("N", "chkAuthC");
	setCheckbox("N", "chkAuthU");
	setCheckbox("N", "chkAuthD");
	setCheckbox("N", "chkAuthP");

	valueClears(["menuId", "menuNm"]);

	domDisableds(["btnUpdate"]);

	//domDisableds(["chkAuthAll", "chkAuthR", "chkAuthC", "chkAuthU", "chkAuthD", "chkAuthP"]);
	nodeData = "";
}

</script>

	<div class="row">
		<div class="col-lg-3">
			<div class="ibox">
				<div class="ibox-title">
					<h5><spring:message code="LAB.M01.LAB00229"/></h5>
					<div class="ibox-tools">
						<customTag:auth auth="${menuAuthR}">
							<a href="javascript:;" class="btn btn-black btn-lg pull-right" role="button" id="btnSearchGroup" name="btnSearchGroup" ><i class="savanaicon-search"></i>&nbsp;<spring:message code="LAB.M09.LAB00158"/></a>
						</customTag:auth>
					</div>
				</div>

				<!-- 조회부 -->
				<div class="ibox-content">
					<div class="table-responsive">
					    <table class="table table-bordered table-search savana-b">
					    	<colgroup>
								<col width="40%">
								<col width="60%">
							</colgroup>
					        <tbody>
						        <tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00182"/></label></th>
									<td>
										<input name="condAuthGrpId" id="condAuthGrpId" type="text" class="form-control"/>
									</td>
								</tr>
								<tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00186"/></label></th>
									<td>
										<input name="condAuthGrpNm" id="condAuthGrpNm" type="text" class="form-control"/>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="ibox-content m-t-sm">
					<div class="table-responsive">
						<table id="groupList" class="table table-striped table-bordered table-hover">
							<colgroup>
								<col width="40%">
								<col width="60%">
							</colgroup>
							<thead>
					            <tr>
					                <th><spring:message code="LAB.M01.LAB00182"/></th>
									<th><spring:message code="LAB.M01.LAB00186"/></th>
					            </tr>
				        	</thead>
						</table>
					</div>
				</div>
			</div>

			<div class="ibox-content m-t-sm">
				<div class="table-responsive">
				    <table class="table table-bordered table-info savana-b">
				    	<colgroup>
							<col width="40%">
							<col width="60%">
						</colgroup>
				        <tbody>
					        <tr>
								<th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00200"/></label></th>
								<td>
									<input name="authGrpDesc" id="authGrpDesc" type="text" class="form-control" disabled />
								</td>
							</tr>
							<tr>
								<th class="active"><label class="control-label"><spring:message code="MSG.M15.MSG00052"/></label></th>
								<td>
									<input name="mainView" id="mainView" type="text" class="form-control" disabled />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="col-lg-9">
			<div class="ibox">
				<div class="ibox-title">
					<h5><spring:message code="LAB.M01.LAB00180"/></h5>
				</div>

				<div class="ibox-content">
					<div class="table-responsive">
					    <table class="table table-bordered table-info savana-b">
					    	<colgroup>
								<col width="15%">
								<col width="50%">
								<col width="35%">
							</colgroup>
					        <tbody>
								<tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M05.LAB00027"/>/<spring:message code="LAB.M05.LAB00026"/></label></th>
									<td>
										<input name="condMenu" id="condMenu" type="text" class="form-control"/>
									</td>
									<td>
										<label class="checkbox-inline i-checks"><input type="checkbox" id="condAsgnYn" value="">&nbsp;<spring:message code="LAB.M14.LAB00016"/></label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="ibox-content">
					<div class="savana-menutree" id="menuTree" style="height:516px; overflow:auto;">
					</div>
				</div>

				<div class="ibox-content m-t-sm">
					<div class="table-responsive">
					    <table class="table table-bordered table-info savana-b">
					    	<colgroup>
								<col width="15%">
								<col width="35%">
								<col width="15%">
								<col width="35%">
							</colgroup>
					        <tbody>
								<tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M05.LAB00027"/></label></th>
									<td><input name="menuId" id="menuId" type="text" class="form-control" disabled /></td>
									<th class="active"><label class="control-label"><spring:message code="LAB.M05.LAB00026"/></label></th>
									<td><input name="menuNm" id="menuNm" type="text" class="form-control" disabled /></td>
								</tr>
								<tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00176"/></label></th>
									<td colspan="3" id="areaCheckBox">
										<label class="checkbox-inline i-checks"><input type="checkbox" id="chkAuthAll" >&nbsp;<spring:message code="LAB.M09.LAB00063"/></label>
										<label class="checkbox-inline i-checks"><input type="checkbox" id="chkAuthR" >&nbsp;<spring:message code="LAB.M09.LAB00158"/></label>
										<label class="checkbox-inline i-checks"><input type="checkbox" id="chkAuthC" >&nbsp;<spring:message code="LAB.M03.LAB00075"/></label>
										<label class="checkbox-inline i-checks"><input type="checkbox" id="chkAuthU" >&nbsp;<spring:message code="LAB.M07.LAB00252"/></label>
										<label class="checkbox-inline i-checks"><input type="checkbox" id="chkAuthD" >&nbsp;<spring:message code="LAB.M07.LAB00082"/></label>
										<label class="checkbox-inline i-checks"><input type="checkbox" id="chkAuthP" >&nbsp;<spring:message code="LAB.M10.LAB00079"/></label>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<!--버튼동작 -->
			<div class="btnArea">
				<div class="form-group pull-right">
					<customTag:auth auth="${menuAuthU}">
					<button id="btnUpdate" class="btn btn-de btn-red" title='<spring:message code="LAB.M07.LAB00252"/>' disabled ><i class="savanaicon-edit"></i>&nbsp;<spring:message code="LAB.M07.LAB00252"/></button>
					</customTag:auth>
				</div>
			</div>
		</div>
	</div>
