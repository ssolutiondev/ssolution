<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<script type="text/javascript">

var authGroupList;	//authGroupList
var isTablesDrawed = false;

$(document).ready(function(){

	authGroupList = $('#authGroupList').DataTable( {
		processing: true,
		serverSide: false,
		scrollY: "350px",
		scrollCollapse: false,
		dom : 'Z<rt<"savana-pagarea"lip>>',	//보여줄 내용 처리 */
		colResize: {
			"tableWidthFixed": false
		},
		pagingType: "full_numbers",	//페이징 타입
		lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],	//화면에 보여줄 갯수처리
		colReorder: true,
		select: true,
		order :[],
		language: {
			url: getTableLngUrl()
		}, //필수
		columns: [
			{ data: "authGrpId" },
			{ data: "authGrpNm" },
			{ data: "dashboardTpNm" },
			{ data: "authGrpDesc" },
			{ data: "ord" },
			{ data: "regrNm" },
			{ data: "regDate" },
			{ data: "chgrNm" },
			{ data: "chgDate" }
		],
		columnDefs: [
		    {
                  className: "text-center",
                  targets: [0,4,6,8] 
            },
			{
				render: function ( data, type, row ) {
					return dateTypeFormatterYYYYMMDDHH24MISS(data);
				},
				targets: [6, 8]
			}
		],
		initComplete: function( settings, json ){
			if(isTablesDrawed == false){
				isTablesDrawed = true;
			}
		},ajax: {
			url: "/system/authMng/authMng/grpMng/getGrpListAction.json",
			type: "post",
			dataSrc : "grpList",
			data : function(d){
				d.authGrpId = $("#condGrpId").val();
				d.authGrpNm = $("#condGrpNm").val();
				d.isTablesDrawed = isTablesDrawed;
			}
		}
	});
	
	//그리드 row 클릭 이벤트
	authGroupList.on('select', function(e, dt, type, indexs){
		var row = $('#authGroupList').DataTable().rows('.selected').data();
		setSelectedData(row[0]);
	});
	
	//클릭 해지 이벤트 방지
	authGroupList.on('user-select', function (e, dt, type, cell, originalEvent) {
	    if ($(cell.node()).parent().hasClass('selected')) {
	    	var row = $('#authGroupList').DataTable().rows('.selected').data();
			setSelectedData(row[0]);
	        e.preventDefault();
	    }
	});
	
	//검색
	$("#btnSearchAuthGroup").on("click", function(e) {
		searchAuthGroup();
	});
	$( "#condGrpId" ).keypress(function(event) {
   		if(event.keyCode == 13){
   			searchAuthGroup();
   		}
   	});
	$( "#condGrpNm" ).keypress(function(event) {
   		if(event.keyCode == 13){
   			searchAuthGroup();
   		}
   	});
	
	//그룹아이디 중복 체크
	$("#btnChkGrpId").on("click", function(e) {
		checkUserGroupId();
	});
	
	//btnDashboardView
	$("#btnDashboardView").on("click", function(e) {
		
		var param = new Object();
		param.returnId1 = "dashboardTp";	//리턴받는 id
		param.returnId2 = "dashboardTpNm";	//리턴받는 name
		
		//url, param
		commonPopup('/system/common/popup/dashboardViewPopup/dashboardViewPopup.ajax', param);
    });
	
	
	//초기화 버튼
	$("#btnClearAuthGroup").on("click", function(e) {
		authGroupInfoInitiation();
	});
	
	//등록 버튼
	$("#btnInsertAuthGroup").on("click", function(e) {
		insertAuthGroup();
    });
	
	//수정 버튼
	$("#btnUpdateAuthGroup").on("click", function(e) {
		updateAuthGroup();
    });
	
	//삭제 버튼
	$("#btnDeleteAuthGroup").on('click', function(e){
		deleteAuthGroup();
	});
});

//검색
function searchAuthGroup(){
	
	authGroupList.clear();
	authGroupList.ajax.reload();
	
	authGroupInfoDisabled();
}

//검색 후 상세정보 초기화
function authGroupInfoDisabled(){
	
	//인풋 비활성화 
	domDisableds(["authGrpId", "authGrpNm", "authGrpDesc", "ord"]);
	//텍스트 밸류 초기화
	valueClears(["authGrpId", "authGrpNm", "dashboardTp", "dashboardTpNm", "authGrpDesc", "ord"]);
	
	//버튼 비활성화
	domDisableds(["btnChkGrpId", "btnDashboardView", "btnInsertAuthGroup", "btnUpdateAuthGroup", "btnDeleteAuthGroup"]);
	//버튼 활성화
	domEnabled("btnClearAuthGroup");
	
}

//초기화
function authGroupInfoInitiation(){

	//태그 활성화 
	domEnableds(["authGrpId", "authGrpNm", "authGrpDesc", "ord"]);
	//밸류 초기화
	valueClears(["authGrpId", "authGrpNm", "dashboardTp", "dashboardTpNm", "authGrpDesc", "ord"]);
	//태그 버튼비활성화
	domDisableds(["btnUpdateAuthGroup", "btnDeleteAuthGroup"]);
	//버튼활성화
	domEnableds(["btnChkGrpId", "btnDashboardView", "btnClearAuthGroup", "btnInsertAuthGroup"]);
	
	//선택된 로우 해지
	authGroupList.rows().deselect();
	
	$("#authGrpId").focus();
}


//상세정보
function setSelectedData(data){
	
	//태그 활성화 
	domEnableds(["authGrpNm", "authGrpDesc", "ord"]);
	//태그 비활성화
	domDisableds(["authGrpId"]);
	
	$("#authGrpId").val(data.authGrpId);
	$("#authGrpNm").val(data.authGrpNm);
	$("#dashboardTp").val(data.dashboardTp);
	$("#dashboardTpNm").val(data.dashboardTpNm);
	$("#authGrpDesc").val(data.authGrpDesc);
	$("#ord").val(data.ord);
	
	//버튼비활성화
	domDisableds(["btnChkGrpId", "btnInsertAuthGroup"]);
	//버튼활성화
	domEnableds(["btnDashboardView", "btnClearAuthGroup", "btnUpdateAuthGroup", "btnDeleteAuthGroup"]);
	
	$("#authGrpNm").focus();
}

//그룹아이디 중복 체크
function checkUserGroupId(){
	
	var param = new Object();
	param.authGrpId = $('#authGrpId').val().trim();
	
 	if(isEmpty(param.authGrpId)){
 		swal('<spring:message code="MSG.M01.MSG00051"/>', "", "error");
		return;
	}
 	
	$.ajax({
		url:'/system/authMng/authMng/grpMng/getCheckGrpIdAction.json',
		type:'POST',
		data : param,
		dataType: 'json',
		success: function(data){
            		
			if(data.check==1){
				swal('<spring:message code="MSG.M01.MSG00049"/>', "", "error"); //아이디 이미존재
				$("#authGrpId").val("");	//아이디 초기화
				return false;
			}else{
				swal('<spring:message code="MSG.M07.MSG00013"/>', "", "success"); //ID사용가능

				//태그 비활성화
				domDisableds(["authGrpId", "btnChkGrpId"]);
			}
		}
         	
	});
}

//파라미터 체크 리턴
function checkParam(){
	
	var param = new Object();
	
	param.authGrpId = $('#authGrpId').val().trim();
	param.authGrpNm = $('#authGrpNm').val().trim();
	param.dashboardTp = $('#dashboardTp').val().trim();
	param.authGrpDesc = $('#authGrpDesc').val().trim();
	param.ord = $('#ord').val().trim();
	
	if(isEmpty(param.authGrpId)){
		$("#authGrpId").focus();
		swal('<spring:message code="MSG.M01.MSG00051"/>', "", "error");
		return false;
	}
	
	if($("#authGrpId").attr("disabled") != "disabled"){
		$("#authGrpId").focus();
		swal('<spring:message code="MSG.M01.MSG00048"/>', "", "error");
		return false;
	}
	
	if(isEmpty(param.authGrpNm)){
		$("#authGrpNm").focus();
		swal('<spring:message code="MSG.M01.MSG00052"/>', "", "error");
		return false;
	}
	
	if(isEmpty(param.dashboardTp)){
		$("#dashboardTp").focus();
		swal('<spring:message code="MSG.M05.MSG00011"/>', "", "error");
		return false;
	}
	
	if(isEmpty(param.authGrpDesc)){
		$("#authGrpDesc").focus();
		swal('<spring:message code="MSG.M07.MSG00079"/>', "", "error");
		return false;
	}
	
	if(isEmpty(param.ord)){
		$("#ord").focus();
		swal('<spring:message code="MSG.M13.MSG00015"/>', "", "error");
		return false;
	}
	
	return param;
}

//AuthGroup 등록
function insertAuthGroup(){
	
	var param = checkParam();
	param.inptMenuId = $("#headerCurMenuId").val();
	
	if(param){
		swalConfirm('<spring:message code="MSG.M01.MSG00065"/>', "warning", function(){
			$.ajax({
				url:'/system/authMng/authMng/grpMng/insertGrpAction.json',
				type:'POST',
				data : param,
				dataType: 'json',
				success: function(data){
					searchAuthGroup();
					authGroupInfoDisabled();	//상세정보 초기화
					
					swal('<spring:message code="MSG.M09.MSG00005"/>',"","success");
				}
			});
		});
	}
}

//AuthGroup 수정
function updateAuthGroup(){
	
	var param = checkParam();
	param.inptMenuId = $("#headerCurMenuId").val();
	
	if(param){
		swalConfirm('<spring:message code="MSG.M01.MSG00066"/>', "warning", function(){
			$.ajax({
				url:'/system/authMng/authMng/grpMng/updateGrpAction.json',
				type:'POST',
				data : param,
				dataType: 'json',
				success: function(data){
					searchAuthGroup();
					authGroupInfoDisabled();	//상세정보 초기화
					
					swal('<spring:message code="MSG.M07.MSG00089"/>',"","success");
				}
			});
		});
	}
}

function deleteAuthGroup(){
	
	var row = $('#authGroupList').DataTable().rows('.selected').data();
	
	var param = new Object();
	param.authGrpId = row[0].authGrpId;
	param.inptMenuId = $("#headerCurMenuId").val();
	
	if(param){
		swalConfirm('<spring:message code="MSG.M01.MSG00067"/>', "warning", function(){
			$.ajax({
				url:'/system/authMng/authMng/grpMng/deleteGrpAction.json',
				type:'POST',
				data : param,
				dataType: 'json',
				success: function(data){
					searchAuthGroup();
					authGroupInfoDisabled();	//상세정보 초기화
					
					swal('<spring:message code="MSG.M07.MSG00053"/>',"","success");
				}
			});
		});
	}
	
}

</script>

	<div class="row">
		<div class="col-lg-12">
		
			<!-- 조회부 -->
			<div class="ibox">
				<div class="ibox-title">
					<div class="ibox-tools">
						<customTag:auth auth="${menuAuthR}">
							<a href="javascript:;" class="btn btn-black btn-lg pull-right" role="button" id="btnSearchAuthGroup" name="btnSearchAuthGroup" ><i class="savanaicon-search"></i>&nbsp;<spring:message code="LAB.M09.LAB00158"/></a>
						</customTag:auth>
					</div>
				</div>
				<div class="ibox-content">
					<div class="table-responsive">
					    <table class="table table-bordered table-search savana-b">
					    	<colgroup>
								<col width="15%">
								<col width="35%">
								<col width="15%">
								<col width="35%">
							</colgroup>
					        <tbody>
						        <tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00182"/></label></th>
									<td>
										<input type="text" id="condGrpId" name="condGrpId" class="form-control" />
									</td>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00186"/></label></th>
									<td>
					 					<input type="text" id="condGrpNm" name="condGrpNm" class="form-control">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<!-- 데이터테이블 -->
			<div class="ibox">
				<div class="ibox-title">
					<h5><spring:message code="LAB.M01.LAB00184"/></h5>
				</div>
				<div class="ibox-content">
					<div class="table-responsive">
						<table id="authGroupList" class="table table-striped table-bordered table-hover">
							<thead>
					            <tr>
					                <th><spring:message code="LAB.M01.LAB00182"/></th>
					                <th><spring:message code="LAB.M01.LAB00186"/></th>
					                <th><spring:message code="MSG.M15.MSG00052"/></th>
					                <th><spring:message code="LAB.M07.LAB00200"/></th>
					                <th><spring:message code="LAB.M13.LAB00021"/></th>
									<th><spring:message code="LAB.M03.LAB00082"/></th>
									<th><spring:message code="LAB.M03.LAB00080"/></th>
									<th><spring:message code="LAB.M07.LAB00256"/></th>
									<th><spring:message code="LAB.M07.LAB00254"/></th>
					            </tr>
				        	</thead>
						</table>
					</div>
				</div>
			</div>
			
			<!-- 정보 -->
			<div class="ibox">
				<div class="ibox-title">
					<h5><spring:message code="LAB.M01.LAB00229"/></h5>
				</div>
				<div class="ibox-content">
					<div class="table-responsive">
					    <table class="table table-bordered table-info savana-b">
					    	<colgroup>
								<col width="8%">
								<col width="25%">
								<col width="8%">
								<col width="25%">
								<col width="8%">
								<col width="26%">
							</colgroup>
					        <tbody>
						        <tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00182"/></label><span class="pull-right imp">*</span></th>
									<td>
										<div class="input-group">
											<input name="authGrpId" id="authGrpId" type="text" class="form-control" maxByte="7" disabled />
								            <span class="input-group-btn">
												<button id="btnChkGrpId" name="btnChkGrpId" class="btn btn-sm btn-red" role="button" disabled ><spring:message code="LAB.M07.LAB00066"/></button>
								            </span>
										</div>
									</td>
									<th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00186"/></label><span class="pull-right imp">*</span></th>
									<td ><input type="text" id="authGrpNm" name="authGrpNm" class="form-control" disabled maxByte="50" /></td>
									<th class="active"><label class="control-label"><spring:message code="MSG.M15.MSG00052"/></label><span class="pull-right imp">*</span></th>
									<td >
										<div class="input-group">
											<input name="dashboardTpNm" id="dashboardTpNm" type="text" class="form-control" disabled />
											<input type="hidden" id="dashboardTp" name="dashboardTp" />
								            <span class="input-group-btn">
												<button id="btnDashboardView" name="btnDashboardView" class="btn btn-sm btn-red" role="button" disabled ><i class="savanaicon-search"></i></button>
								            </span>
										</div>
									</td>
								</tr>
								<tr>
									<th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00200"/></label><span class="pull-right imp">*</span></th>
									<td colspan="3" ><input type="text" id="authGrpDesc" name="authGrpDesc" class="form-control" disabled maxByte="100" /></td>
									<th class="active"><label class="control-label"><spring:message code="LAB.M13.LAB00021"/></label><span class="pull-right imp">*</span></th>
									<td><input type="text" id="ord" name="ord" class="form-control numberFormat" disabled maxByte="5" /></td>
								</tr>
					        </tbody>
					    </table>
					</div>
				</div>
			</div>
			
			<div class="btnArea">
				<div class="form-group pull-right">
					<button id="btnClearAuthGroup" class="btn btn-de btn-white" title='<spring:message code="LAB.M10.LAB00050"/>' disabled ><i class="savanaicon-reflash"></i>&nbsp;<spring:message code="LAB.M10.LAB00050"/></button>
					<customTag:auth auth="${menuAuthC}">
						<button id="btnInsertAuthGroup" class="btn btn-de btn-black" title='<spring:message code="LAB.M03.LAB00075"/>' disabled ><i class="savanaicon-reg"></i>&nbsp;<spring:message code="LAB.M03.LAB00075"/></button>
					</customTag:auth>
					<customTag:auth auth="${menuAuthU}">
						<button id="btnUpdateAuthGroup" class="btn btn-de btn-red" title='<spring:message code="LAB.M10.LAB00050"/>' disabled ><i class="savanaicon-edit"></i>&nbsp;<spring:message code="LAB.M07.LAB00252"/></button>
					</customTag:auth>
					<customTag:auth auth="${menuAuthD}">
						<button id="btnDeleteAuthGroup" class="btn btn-de btn-red" title='<spring:message code="LAB.M07.LAB00082"/>' disabled><i class="savanaicon-del"></i>&nbsp;<spring:message code="LAB.M07.LAB00082"/></button>
					</customTag:auth>
				</div>
			</div>
		</div>
	</div>
