<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<script type="text/javascript">

    var paramSoId = "";
    var paramSrchOrgTp = "";
    var paramSrchOrgId = "";
    var paramSubOrgDispYn = "";
	var paramReturnIdForId = "";
	var paramReturnIdForNm = "";
	var paramReturnIdForOrgId = "";
	var paramReturnIdForOrgNm = "";
	var paramCallbackFuncNm = "";
	var userListPopup = "";
	var paramAuthOrgId = "";

	$(document).ready(function() {
		initUserPopupPage();

		//팝업 오픈 후 그리드 선언
		var target = "userListPopup";	
		var _parents = $('#' + target).parents('.modal').attr('id');

		$('#' + _parents  ).on('shown.bs.modal', function() {
		    // 유저 목록
			userListPopup = $('#userListPopup').DataTable( {
				processing: true,
				serverSide: false,
				scrollY: "301px",
				scrollCollapse: false,
                sScrollX: true,
                sScrollXInner: "100%",
				dom : 'Z<rt<"savana-pagarea"lip>>',
				colResize: {
					"tableWidthFixed": false
				},
				pagingType: "full_numbers",
				lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
				colReorder: true,
				select: 'single',
				order :[],
				language: {
					url: getTableLngUrl()
				},
				columns: [
					{ data: "userId",    width: "10%" }, //0:사용자ID
					{ data: "userNm",    width: "20%" }, //1:사용자명
					{ data: "rspofcNm",  width: "10%" }, //2:직책
					{ data: "orgId",     width: "10%" }, //3:조직ID
					{ data: "orgNm",     width: "20%" }, //4:조직명
					{ data: "cellPhnNo", width: "15%" }, //5:휴대폰번호
					{ data: "email",     width: "15%" }  //6:이메일
				],
				initComplete: function( settings, json ){
					if(paramAuthOrgId != undefined && paramAuthOrgId != ''){
						getUserList(paramSrchOrgId);	
					}
					
				},
				columnDefs: [
	                {
	                    render: function ( data, type, row ) {
	                        return telNoFormatter(data);
	                    },
	                    targets: [5]
	                },
					{ className: "text-center", targets: [0, 1, 2, 3, 5] }
				]
			});

			//그리드 row 클릭 이벤트
			userListPopup.on('select', function(e, dt, type, indexs){
				var row = $('#userListPopup').DataTable().rows('.selected').data();
			});

			//클릭 해지 이벤트 방지
			userListPopup.on('user-select', function (e, dt, type, cell, originalEvent) {
			    if ($(cell.node()).parent().hasClass('selected')) {
			        e.preventDefault();
			    }
			});

		});

	    // [조회] 버튼 이벤트
	    $("#btnSearchUserPopup").on("click", function(e) {
	    	// 노드 선택 해제
	    	$("#orgTreePopup").jstree("deselect_all");
	    	// 사용자 목록 초기화
	    	userListPopup.clear().draw();
			// 사용자 목록 조회
	    	getUserList();
	    });

	    // 사용자 검색조건 항목 엔터키 입력 이벤트
	    $("#condUserId, #condUserNm, #condOrgId, #condOrgNm").keydown(function(key) {
	    	if (key.keyCode == 13) {
	            // 노드 선택 해제
	            $("#orgTreePopup").jstree("deselect_all");
	            // 사용자 목록 초기화
	            userListPopup.clear().draw();
				// 사용자 목록 조회
		    	getUserList();
	    	}
	    });

	    // 사용자 목록 Row 더블클릭 이벤트
		$("#userListPopup").dblclick(function(e) {
			// 사용자 선택
			var selectedRow = $('#userListPopup').DataTable().rows('.selected').data();
			selectUserInfo(selectedRow[0]);
		});

	    // [선택] 버튼 이벤트
	 	$("#btnSelectUserPopup").on("click", function(e) {
			// 사용자 선택
			var selectedRow = $('#userListPopup').DataTable().rows('.selected').data();
	    	selectUserInfo(selectedRow[0]);
	 	});

	});

	// 초기화
	function initUserPopupPage() {

		// 전달 받은 파라메터 설정
		paramSoId = "<c:out value='${paramUserInfo.soId}'/>";
		paramSrchOrgTp = "<c:out value='${paramUserInfo.srchOrgTp}'/>";
		paramSrchOrgId = "<c:out value='${paramUserInfo.srchOrgId}'/>";
        paramSubOrgDispYn = "<c:out value='${paramUserInfo.subOrgDispYn}'/>";
		paramReturnIdForId = "<c:out value='${paramUserInfo.returnIdForUserId}'/>";
		paramReturnIdForNm = "<c:out value='${paramUserInfo.returnIdForUserNm}'/>";
		paramReturnIdForOrgId = "<c:out value='${paramUserInfo.returnIdForOrgId}'/>";
		paramReturnIdForOrgNm = "<c:out value='${paramUserInfo.returnIdForOrgNm}'/>";
		paramCallbackFuncNm = "<c:out value='${paramUserInfo.callbackFuncNm}'/>";
		paramAuthOrgId      = "<c:out value='${param.authOrgId}'/>";
		
		// 항목 설정
		$("#userAuthOrgPopup").text("<c:out value='${paramUserInfo.authOrgNm}'/>" + "(" + "<c:out value='${paramUserInfo.authOrgId}'/>" + ")");
		$("#userOrgPopup").text("<c:out value='${paramUserInfo.orgNm}'/>" + "(" + "<c:out value='${paramUserInfo.orgId}'/>" + ")");

		if (!isEmpty(paramSubOrgDispYn) && paramSubOrgDispYn == "N") {
			// 사용자 조직만 검색 가능
			// 조직 검색 조건 사용자 조직 설정
			$("#condOrgId").val(paramSrchOrgId);
			$("#condOrgNm").val("<c:out value='${paramUserInfo.srchOrgNm}'/>");

			// 조직 검색 조건 비활성화
			$("#condOrgId").attr("disabled", "disabled");
			$("#condOrgNm").attr("disabled", "disabled");
		}

		// 조직 조회(For Tree)
        getOrgListPopupForTree();

	}

    // 조직 트리 설정
    function getOrgListPopupForTree() {
        // 파라메터 설정
        var param = new Object();
        param.soId = paramSoId;         
        param.authOrgId = paramSrchOrgId;       //권한조직ID
        // redmine 결함 #7 관리담당자, 영업담당자에서 선택 범위 조정 으로 수정
        if(paramAuthOrgId != undefined && paramAuthOrgId != ''){
        	
        	param.authOrgId = paramAuthOrgId;       //권한조직ID
        }
        param.subOrgDispYn = paramSubOrgDispYn; //하위조직표시여부

        $.ajax({
            url : '/system/common/popup/orgSearchPopup/getOrgListPopupForTree.json',
            type : 'POST',
            data : param,
            dataType : 'json',
            success : function(data) {
                // 트리 설정
                $("#orgTreePopup").jstree('destroy');
                $("#orgTreePopup").jstree({
                    core : {
                        data : data
                    },
                    plugins : [ 
                        "search"
                    ]
                }).bind('select_node.jstree', function(event, data) {
                    nodeData = data;
                    // 노드 오픈
                    $("#orgTreePopup").jstree("open_node", "#" + nodeData.node.original.id);

                    if (typeof userListPopup == "object") {
                        // 선택한 조직의 유저 검색(트리여부)
                        getUserList(nodeData.node.original.id);

                    }

                }).bind('loaded.jstree', function(event, data){
                	
                	 // redmine 결함 #7 관리담당자, 영업담당자에서 선택 범위 조정 으로 수정
                	if(paramAuthOrgId != undefined && paramAuthOrgId != ''){
						 $('#orgTreePopup').jstree(true)._open_to(paramSrchOrgId);
	                	 $("#orgTreePopup").jstree("open_node", "#"+paramSrchOrgId  );	                	
						
					}
                	
                });
            }
        });
    }

	// 사용자 목록 검색
	function getUserList(treeNodeId) {
        // 파라메터 설정
        var param = new Object();
        param.soId = paramSoId;                    //사업ID
        param.srchOrgTp = paramSrchOrgTp;          //검색조직유형
        param.srchOrgId = paramSrchOrgId;          //검색조직ID
        
        
        if (!isEmpty(treeNodeId)) {
            param.orgId = treeNodeId;              //트리메뉴 선택 조직ID
            // redmine 결함 #7 관리담당자, 영업담당자에서 선택 범위 조정 으로 수정
            param.srchOrgId = treeNodeId;          //검색조직ID
            // 조회조건 클리어
            $("#condUserId").val("");
            $("#condUserNm").val("");
            if (paramSubOrgDispYn != "N") {
            	// 하위 조직 표시하는 경우 클리어
                $("#condOrgId").val("");
                $("#condOrgNm").val("");
            }

        } else {
            param.userId = $("#condUserId").val(); //사용자ID
            param.userNm = $("#condUserNm").val(); //사용자명
            param.orgId = $("#condOrgId").val();   //조직ID
            param.orgNm = $("#condOrgNm").val();   //조직명
        }

        // 사용자 검색
        $.ajax({
            url: '/system/common/popup/userPopup/getUserListPopup.json',
            type: 'post',
            data: param,
            dataType: 'json',
            success: function(data){
                // 계약 목록 설정
                userListPopup.clear();
                userListPopup.rows.add(data.userListPopup);
                userListPopup.draw();

            }
        });
	}

	// 사용자 선택 후 화면 닫기
	function selectUserInfo(userInfo) {

		var returnSoId = "";
		var returnUserId = "";
		var returnUserNm = "";
		var returnOrgId = "";
        var returnOrgNm = "";

		if (!isEmpty(userInfo)) {
			returnSoId = userInfo.soId;
			returnUserId = userInfo.userId;
			returnUserNm = userInfo.userNm;
			returnOrgId = userInfo.orgId;			
			returnOrgNm = userInfo.orgNm;
		}

		// 리턴값 설정
		$("#" + paramReturnIdForId).val(returnUserId);
		$("#" + paramReturnIdForNm).val(returnUserNm);
		
		if (!isEmpty(paramCallbackFuncNm) && typeof eval(paramCallbackFuncNm) == "function") {
            // 파라메터 콜백함수명이 존재하는 경우
            // 파라메터 설정
            var param = new Object();
            param.userId = returnUserId;
            param.userNm = returnUserNm;
            if(paramReturnIdForOrgId != undefined && paramReturnIdForOrgId != ''){
        		
            	if(returnOrgId != undefined && returnOrgId != ''){
	    			$("#" + paramReturnIdForOrgId).val(returnOrgId);
	    			$("#" + paramReturnIdForOrgNm).val(returnOrgNm);
            	}
            	param.uppOrgId = returnOrgId;
            	param.uppOrgNm = returnOrgNm;
            	 $.ajax({
            	        url: '/system/common/popup/userPopup/getUserUppOrg.json',
            	        type: 'post',
            	        data: param,
            	        dataType: 'json',
            	        success: function(data){
            	        	if(data.getUserUppOrg[0] != undefined){
		           	        	param.cnterOrgId = data.getUserUppOrg[0].uppOrgId;
	            	        	param.cnterOrgNm = data.getUserUppOrg[0].uppOrgNm;
	            	        	param.brOrgId = data.getUserUppOrg[0].orgId;
	            	        	param.brOrgNm = data.getUserUppOrg[0].orgNm;
            	        	}else{
            	        		param.cnterOrgId = "";	
	            	        	param.cnterOrgNm = "";
	            	        	param.brOrgId = "";
	            	        	param.brOrgNm = "";
            	        	}
            	        	
    	   	        	  new Function(paramCallbackFuncNm + "(" + JSON.stringify(param) + ")")();
            	        }
            	    });
            	
    		}else{
	            // 콜백 함수 호출
	            new Function(paramCallbackFuncNm + "(" + JSON.stringify(param) + ")")();
    		}
        }

		// 화면 닫기
		$("#btnCloseFoot").trigger('click');
	}


</script>
<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="modalTitle"><spring:message code="LAB.M07.LAB00077"/></h4>
		</div>

		<div class="modal-body ">
		    <div class="row">
                <div class="col-lg-4">
                    <div class="ibox">
                        <div class="ibox-title"></div>
                        <div class="ibox-content">
                            <input type="hidden" name="soIdPopup" id="soIdPopup" />
                            <table class="table table-bordered table-search savana-b">
                                <colgroup>
                                    <col width="40%">
                                    <col width="60%">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00599"/></label></th><!-- 사용자 권한조직 -->
                                        <td id="userAuthOrgPopup"></td>
                                    </tr>
                                    <tr>
                                        <th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00600"/></label></th><!-- 사용자 조직 -->
                                        <td id="userOrgPopup"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="ibox">
                        <div class="ibox-content">
                            <div class="savana-menutree" id="orgTreePopup" style="height:368px; overflow:auto;">
                            </div>
                        </div>
                    </div>
                </div>

				<div class="col-lg-8">
					<div class="ibox">
						<div class="ibox-title">
							<div class="ibox-tools">
								<a href="javascript:;" class="btn btn-black btn-lg pull-right" role="button" id="btnSearchUserPopup"><i class="savanaicon-search"></i>&nbsp;<spring:message code="LAB.M09.LAB00158"/></a>
							</div>
						</div>
						<!-- 조회부 -->
						<div class="ibox-content">
						    <table class="table table-bordered table-search savana-b">
						    	<colgroup>
									<col width="15%">
									<col width="25%"">
									<col width="15%">
									<col width="35%">
								</colgroup>
						        <tbody>
									<tr>
										<th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00067"/></label></th><!-- 사용자ID -->
										<td><input name="condUserId" id="condUserId" type="text" class="form-control"/></td>
										<th class="active"><label class="control-label"><spring:message code="LAB.M07.LAB00071"/></label></th><!-- 사용자명 -->
                                        <td><input name="condUserNm" id="condUserNm" type="text" class="form-control"/></td>
								    </tr>
								    <tr>
                                        <th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00139"/></label></th><!-- 조직ID -->
                                        <td><input name="condOrgId" id="condOrgId" type="text" class="form-control"/></td>
                                        <th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00147"/></label></th><!-- 조직명 -->
                                        <td><input name="condOrgNm" id="condOrgNm" type="text" class="form-control"/></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="ibox">
						<div class="ibox-content">
							<div class="table-responsive" style="margin-top: 10px;">
								<table id="userListPopup" class="table table-striped table-bordered table-hover">
									<thead>
							            <tr>
											<th><spring:message code="LAB.M07.LAB00067"/></th><!-- 사용자ID -->
											<th><spring:message code="LAB.M07.LAB00071"/></th><!-- 사용자명 -->
											<th><spring:message code="LAB.M09.LAB00217"/></th><!-- 직책 -->
							                <th><spring:message code="LAB.M09.LAB00139"/></th><!-- 조직ID -->
											<th><spring:message code="LAB.M09.LAB00147"/></th><!-- 조직명 -->
											<th><spring:message code="LAB.M14.LAB00076"/></th><!-- 휴대폰번호 -->
											<th><spring:message code="LAB.M08.LAB00119"/></th><!-- 이메일 -->
							            </tr>
						        	</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
		    </div>
 		</div>

		<div class="modal-footer">
			<button type="button" id="btnSelectUserPopup" class="btn btn-black" ><spring:message code="LAB.M07.LAB00195" /></button>
			<button type="button" id="btnCloseFoot" class="btn btn-white" data-dismiss="modal"><spring:message code="LAB.M03.LAB00027" /></button>
		</div>
	</div>
 </div>
