<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<script type="text/javascript">

var userGrid;
var isUserGridDrawed = false;
var userAuthGrid;
var isUserAuthGridDrawed = false;
$(document).ready(function() {
    //사용자리스트 그리드
    userGrid = $('#userList').DataTable( {
        processing: true,
        serverSide: false,
        scrollY: "400px",
        scrollCollapse: false,
        dom : '<rt<"savana-pagarea"lip>>',  //보여줄 내용 처리
        pagingType: "full_numbers", //페이징 타입
        lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],  //화면에 보여줄 갯수처리
        select: true,
        order :[],
        language: {
            url: getTableLngUrl()
        }, //필수
        columns: [
            { data: "userId" },
            { data: "userNm" },
            { data: "orgId" },
            { data: "orgNm" }
        ],
        initComplete: function( settings, json ){
            if(isUserGridDrawed == false){
                isUserGridDrawed = true;
            }
        },ajax: {
            url: "/system/authMng/authMng/userAuthMng/getUserListAction.json",
            type: "post",
            dataSrc: "userList",
            data : function(data){
                data.userId = $("#condUserId").val();
                data.userNm = $("#condUserNm").val();
                data.orgId = $("#condOrgId").val();
                data.isTablesDrawed = isUserGridDrawed;
            }
        }
    });

    //권한그룹리스트 그리드
    authGrpUser = $('#userAuthList').DataTable( {
        processing: true,
        serverSide: false,
        scrollY: "505px",
        scrollCollapse: false,
        dom : '<rt>',    //보여줄 내용 처리
        select: false,
        language: {
            url: getTableLngUrl()
        }, //필수
        pageLength : -1,
        columns: [
            { data: "checkYn" },
            { data: "authGrpId" },
            { data: "authGrpNm" }
        ],
        columnDefs: [{
         'targets': 0,
         'searchable': false,
         'orderable': false,
         'className': 'center',
         'render': function (data, type, full, meta){
             if(full.checkYn == 'Y'){
                return '<label class="checkbox-inline i-checks gridCheck"><input type="checkbox" name="id[]" onclick="checkboxChanged();" value="' + $('<div/>').text(full.authGrpId).html() + '" checked></label>';
             }else{
                return '<label class="checkbox-inline i-checks gridCheck"><input type="checkbox" name="id[]" onclick="checkboxChanged();" value="' + $('<div/>').text(full.authGrpId).html() + '"></label>';
             }
         }
        }],
        order: [],
        initComplete: function( settings, json ){
            if(isUserAuthGridDrawed == false){
                isUserAuthGridDrawed = true;
            }
        },ajax: {
            url: "/system/authMng/authMng/userAuthMng/getUserAuthListAction.json",
            type: "post",
            dataSrc: "userAuthList",
            data : function(data){
                return makeParameters();
            }
        },
        drawCallback: function( settings ) {
            //initICheck();
        	icheckInit();
        }
    });


    //그리드 row 클릭 이벤트
    userGrid.on('select', function(e, dt, type, indexs){
        domEnableds(["updateBtn"]);
        searchUserAuthList();
    });

    //클릭 해지 이벤트 방지
    userGrid.on('user-select', function (e, dt, type, cell, originalEvent) {
        if ($(cell.node()).parent().hasClass('selected')) {
            e.preventDefault();
        }
    });

    //검색버튼
    $("#btn_srch").on("click", function(e) {
        domDisableds(["updateBtn", "deleteBtn"]);
        searchUserList();
    });

    //검색조건 사용자ID 엔터 이벤트
    $('#condUserId').on('keypress',function (e) { 
        if(event.keyCode == 13){
            searchUserList();
        }
    });

    //검색조건 사용자명 엔터 이벤트
    $('#condUserNm').on('keypress',function (e) {
        if(event.keyCode == 13){
            searchUserList();
        }
    });

    //검색 파라미터 - 조직 검색 팝업
    $("#condOrgNm_popup").on("click", function(e) {
        // 파라메터 설정 - 리턴값 설정할 항목명
        var param = new Object();
        param.returnIdForOrgId = "condOrgId";
        param.returnIdForOrgNm = "condOrgNm";

        // 조직 조회 화면 팝업
        commonPopup('/system/common/popup/orgSearchPopup/orgSearchPopup.ajax', param, 1200);
    });

    $("#updateBtn").on("click", function(e) {
        updateUserAuth();
    });

});


function icheckInit(){
	$('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green'
    });
	
    //권한 전체 선택 이벤트
    $("#userAuthSelectAllChk").on("ifClicked", function(e) {
       var rows = authGrpUser.rows({ 'search': 'applied' }).nodes();
       if(this.checked == false){
    	   $('input[type="checkbox"]', rows).iCheck("check");   
       }else{
    	   $('input[type="checkbox"]', rows).iCheck("uncheck");
       }
    });
};


/**
 * 사용자리스트 검색
 */
function searchUserList(){
    authGrpUser.clear();
    authGrpUser.draw();

    userGrid.clear();
    userGrid.ajax.reload();
}
/**
 * 권한리스트 검색
 */
function searchUserAuthList(){
    $("#userAuthSelectAllChk").iCheck("uncheck");
    authGrpUser.clear();
    authGrpUser.ajax.reload();
}


/**
 * 권한리스트 조회 파라미터
 */
function makeParameters(){
    var param = new Object();

    var row = $('#userList').DataTable().rows('.selected').data();
    if(row.length == 0){
        param.userId = '';
        param.isTablesDrawed = false;
    }else{
        param.userId = row[0].userId;
        param.isTablesDrawed = true;
    }
    return param;
}


/**
 * 체크 박스 변경 이벤트
 */
function checkboxChanged(){
    //var checked[] = $('input[name="ids']);

    var checked = [];
    $(':checkbox:checked').each(function(i){
      checked[i] = $(this).val();
    });

    if(checked.length > 0) domEnableds(["deleteBtn"]); 
    else domDisableds(["deleteBtn"]);
}

/**
 * 권한 수정
 */
function updateUserAuth(){

    var param = checkUpdate();
    
    param.inptMenuId = $("#headerCurMenuId").val();
    
    if(param == false){
        return;
    }

    swalConfirm('<spring:message code="MSG.M06.MSG00041"/>', "warning", function(){
        $.ajax({
            url: "/system/authMng/authMng/userAuthMng/updateUserAuthAction.json",
            type:'POST',
            data : JSON.stringify(param) ,
            contentType : "application/json; charset=UTF-8",
            dataType: 'json',
            success: function(data){
                searchUserAuthList();
                swal('<spring:message code="MSG.M07.MSG00089"/>',"","success");
            }
        });
    });
    
}

/**
 * 변경 내용 저장 체크
 */
function checkUpdate(){
    /**
     * 사용자 선택 체크
     */    
    var userRow = userGrid.rows('.selected').data();
    if(userRow.length == 0){
        swal('<spring:message code="MSG.M07.MSG00044"/>', "", "error");
        return false;
    }
    var orgUserAuthList = authGrpUser.rows().data();

    /**
     * 변경 여부 체크
     */    
    var isChanged = false;
    $.each(orgUserAuthList, function(index, value){ 
        var checked = false;
        $('#userAuthList :checkbox:checked').not( "#userAuthSelectAllChk" ).each(function(i){
            var authGrpId = $(this).val();
            if(authGrpId != value.authGrpId) return;
            if(value.checkYn == "N"){ 
                isChanged = true; //미선택에서 선택으로 변경
            }else{
                checked = true; //선택에서 선택 유지
            }
            return false;
        });
        if(isChanged) return false; //변경 되었으면 종료
        if(value.checkYn == "Y" && checked == false){ //선택에서 미선택 되었으면 변경종료
            isChanged = true;
            return false;
        }
    });

    if(isChanged == false){
        swal('<spring:message code="MSG.M06.MSG00026"/>', "", "error");
        return false;
    }

    var param = new Object();
    var userAuthList = [];
    $('#userAuthList :checkbox:checked').not( "#userAuthSelectAllChk" ).each(function(i){
        userAuthList[i] = $(this).val();
    });
    param.updateAuthIdList = userAuthList;
    param.userId = userRow[0].userId;
    return param;
}

</script>


	<div class="row">
	    <div class="col-lg-6">
	    	<div class="ibox">
	    		<div class="ibox-title">
		            <h5><spring:message code="LAB.M07.LAB00070"/></h5>
		            <div class="ibox-tools">
		            	<customTag:auth auth="${menuAuthR}">
                            <a href="javascript:;" class="btn btn-black btn-lg pull-right" role="button" id="btn_srch" name="btn_srch" ><i class="savanaicon-search"></i>&nbsp;<spring:message code="LAB.M09.LAB00158"/></a>
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
			                        <th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00067"/></label></th> <!-- 사용자ID -->
			                        <td>
			                            <input id="condUserId" name="condUserId" type="text" class="form-control" />
			                        </td>
			                        <th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00071"/></label></th> <!-- 사용자명-->
			                        <td>
			                            <input id="condUserNm" name="condUserNm" type="text" class="form-control" />
			                        </td>
			                    </tr>
			                    <tr>
			                        <th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00138"/></label></th> <!-- 조직 -->
			                        <td colspan="3">
			                            <div class="input-group">
			                                <input id="condOrgNm" name="condOrgNm" type="text" class="form-control" disabled />
			                                <input type="hidden" id="condOrgId" name="condOrgId">
			                                <span class="input-group-btn">
			                                    <a href="javascript:;" class="btn btn-sm btn-red" role="button" id="condOrgNm_popup" name="condOrgNm_popup" ><i class="savanaicon-search"></i></a>
			                                </span>
			                            </div>
			                        </td>
			                    </tr>
			                </tbody>
			            </table>
		       		</div>
		        </div>
	    	</div>
	    	
	    	<div class="ibox">
		    	<div class="ibox-content">
		            <div class="table-responsive">
		                <table id="userList" class="table table-striped table-bordered table-hover">
		                    <colgroup>
		                        <col width="20%">
		                        <col width="30%">
		                        <col width="20%">
		                        <col width="30%">
		                    </colgroup>
		                    <thead>
		                        <tr>
		                            <th><spring:message code="LAB.M07.LAB00067"/></th> <!-- 사용자ID -->
		                            <th><spring:message code="LAB.M07.LAB00071"/></th> <!-- 사용자명 -->
		                            <th><spring:message code="LAB.M09.LAB00138"/></th> <!-- 조직    -->
		                            <th><spring:message code="LAB.M09.LAB00147"/></th> <!-- 조직명   -->
		                        </tr>
		                    </thead>
		                </table>
		            </div>
		        </div>
		    </div>
	    
	    </div>
	    
	    
	    
	    <div class="col-lg-6">
	    	<div class="ibox">
	    		<div class="ibox-title">
		            <h5><spring:message code="LAB.M07.LAB00073"/></h5> <!-- 사용자별 권한 그룹-->
		        </div>
		        <div class="ibox-content">
		            <div class="table-responsive">
		                <table id="userAuthList" class="table table-striped table-bordered table-hover">
		                    <colgroup>
		                        <col width="8%">
		                        <col width="30%">
		                        <col width="62%">
		                    </colgroup>
		                    <thead>
		                    <tr>
		                        <th><label class="checkbox-inline i-checks"><input type="checkbox" id="userAuthSelectAllChk"></label></th>
		                        <th><spring:message code="LAB.M01.LAB00178"/></th> <!-- 권한그룹ID -->
		                        <th><spring:message code="LAB.M01.LAB00179"/></th> <!-- 권한그룹명 -->
		                    </tr>
		                    </thead>
		                </table>
		            </div>
		        </div>
	    	</div>
	    	
	    	<!--버튼동작 -->
	    	<div class="btnArea">
				<div class="form-group pull-right">
				    <customTag:auth auth="${menuAuthU}">
				        <button id="updateBtn" class="btn btn-de btn-red" title='<spring:message code="LAB.M07.LAB00252"/>' disabled ><i class="savanaicon-edit"></i>&nbsp;<spring:message code="LAB.M07.LAB00252"/></button>
				    </customTag:auth>
				</div>
			</div>
			
	    </div>
	</div>

