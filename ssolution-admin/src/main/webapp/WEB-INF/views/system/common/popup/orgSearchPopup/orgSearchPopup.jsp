<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>

<script type="text/javascript">

    var orgListPopup;
    var paramReturnIdForId;
    var paramReturnIdForNm;
    var paramTpCd;
    var paramTpDtlCd;
    var paramCallbackFuncNm;

    $(document).ready(function() {
        // 초기 설정
        initOrgSearchPopupPage();

        //팝업 오픈 후 그리드 선언
        var target = "orgListPopup";
        var _parents = $('#' + target).parents('.modal').attr('id');

        $('#' + _parents  ).on('shown.bs.modal', function() {

        	orgListPopup = $('#orgListPopup').DataTable( {
                processing: true,
                serverSide: false,
                scrollY: "301px",
                scrollCollapse: false,
                sScrollX: true,
                sScrollXInner: "100%",
                dom: 'Z<rt<"savana-pagarea"lip>>',
                pagingType: "full_numbers",
                lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
                order :[],
                select: "single",
                language: {
                    url: getTableLngUrl()
                },
                columns: [
                    { data: "orgId",    width: "15%" },
                    { data: "orgNm",    width: "29%" },
                    { data: "tpNm",     width: "18%" },
                    { data: "tpDtlNm",  width: "18%" },
                    { data: "orgLvlNm", width: "20%" }
                ],
                columnDefs: [
                    { orderable: false, targets: [] },
                    { className: "text-center", targets: [0, 2, 3, 4] }
                ],
                ajax: {
                    url: "/system/common/popup/orgSearchPopup/getOrgListPopupByAuth.json",
                    type: "post",
                    dataSrc: "orgList",
                    data : function(d){
                        // 파라메터 설정
                        d.soId = $("#soIdPopup").val();
                        d.authOrgId = $("#authOrgIdPopup").text();
                        d.tpCd = $("#orgTpPopup").val();
                        d.tpDtlCd = $("#orgTpDtlPopup").val();
                        d.orgId = $("#orgIdPopup").val().trim();
                        d.orgNm = $("#orgNmPopup").val().trim();
                    }
                }
            });

            //그리드 row 클릭 이벤트
            orgListPopup.on('select', function(e, dt, type, indexs){
                // 조직 목록 선택 ROW 설정
                var orgInfoPopup = orgListPopup.rows(indexs).data();

                // 트리메튜의 선택한 조직ID 에 해당하는 조직 선택
                $("#orgTreePopup").jstree("deselect_all");
            	$("#orgTreePopup").jstree("select_node", "#" + orgInfoPopup[0].orgId);

            	//선택된 노드로 스크롤 이동
                $("#orgTreePopup").scrollTop(0);
                var id = $("#orgTreePopup").jstree('get_selected');
                var top = $("#orgTreePopup #" + id[0]).offset().top;
                $("#orgTreePopup").scrollTop(top - $("#orgTreePopup").offset().top - 5);

            });

            //클릭 해지 이벤트 방지
            orgListPopup.on('user-select', function (e, dt, type, cell, originalEvent) {
                if ($(cell.node()).parent().hasClass('selected')) {
                    e.preventDefault();
                }
            });

        });

        // 조직ID 및 조직명 엔터키 입력 이벤트
        $("#orgIdPopup, #orgNmPopup").keydown(function(key) {
            if (key.keyCode == 13) {
                // 조직 목록 조회
                getOrgListPopupByAuth();
            }
        });

        // [조회] 버튼 클릭 이벤트
        $("#btnSearchOrgPopup").on("click", function(e) {
            // 조직 목록 조회
            getOrgListPopupByAuth();
        });

        // [선택] 버튼 클릭 이벤트
        $("#btnSelectOrgPopup").on("click", function(e) {
            // 조직 선택
            var selectedOrg = $('#orgListPopup').DataTable().rows('.selected').data();
            selectOrgInfo(selectedOrg[0]);
        });

        // 조직 목록 Row 더블클릭 이벤트
        $("#orgListPopup").dblclick(function(e) {
            // 조직 선택
            var selectedOrg = $('#orgListPopup').DataTable().rows('.selected').data();
            selectOrgInfo(selectedOrg[0]);
        });

        // 조직유형 셀렉트박스 이벤트 설정
        $("#orgTpPopup").on("change", function(e) {

            var orgTpCd = $(this).val();

            var param = new Object();
    		var colmnidhtml = '<option value=""><spring:message code="LAB.M15.LAB00109"/></option>'; /* 전체 */
    		var listLength = $("#ul_orgTpDtlCodeList > li").length;
    		for(var i=0; i<listLength;i++) {

    			var liObj = $("#ul_orgTpDtlCodeList > li").eq(i);
    			if(orgTpCd == liObj.attr("data-refcd") && liObj.attr("data-refcd")!="") {
//     				console.log(liObj.attr("data-refcd") +":"+ liObj.attr("data-commcd") +":"+ liObj.attr("data-commnm"));
    				colmnidhtml += '<option value="' + liObj.attr("data-commcd") +'">' + liObj.attr("data-commnm") + '</option>';
    			}
    		}

    		$("#orgTpDtlPopup").html(colmnidhtml);

        });

        if(paramTpCd == ''){
            // 조직유형 설정
            $("#orgTpPopup option:first").attr("selected", "selected").trigger("change");
        }else{
            $("#orgTpPopup").val(paramTpCd).trigger("change");
            $("#orgTpPopup").attr("disabled", "disabled");

            if(paramTpDtlCd != ''){
                $("#orgTpDtlPopup").val(paramTpDtlCd).trigger("change");
                $("#orgTpDtlPopup").attr("disabled", "disabled");
            }
        }
	});

    // 초기 페이지 설정
    function initOrgSearchPopupPage() {

        // 전달 받은 파라메터 설정
        paramReturnIdForId = "<c:out value='${paramOrgInfo.returnIdForOrgId}'/>";
        paramReturnIdForNm = "<c:out value='${paramOrgInfo.returnIdForOrgNm}'/>";
        paramTpCd          = "<c:out value='${paramOrgInfo.tpCd}'/>";
        paramTpDtlCd       = "<c:out value='${paramOrgInfo.tpDtlCd}'/>";
        paramCallbackFuncNm = "<c:out value='${paramOrgInfo.callbackFuncNm}'/>";

        // 권한조직ID 설정
        $("#soIdPopup").val("<c:out value='${paramOrgInfo.soId}'/>");
        $("#authOrgIdPopup").text("<c:out value='${paramOrgInfo.authOrgId}'/>");
        $("#authOrgNmPopup").text("<c:out value='${paramOrgInfo.authOrgNm}'/>");

        // 조직 조회(For Tree)
        getOrgListPopupForTree($("#soIdPopup").val(), $("#authOrgIdPopup").text());

    }

    // 조직 목록 조회(권한조직기준)
    function getOrgListPopupByAuth() {
    	// 조직 목록 조회
    	orgListPopup.clear();
    	orgListPopup.ajax.reload();
    }

    // 조직 트리 설정
    function getOrgListPopupForTree(paramSoId, paramAuthOrgId) {
    	// 파라메터 설정
        var param = new Object();
    	param.soId = paramSoId;
        param.authOrgId = paramAuthOrgId;

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
                    $("#orgTreePopup").jstree("open_node", "#" + nodeData.node.original.id);

                }).bind('loaded.jstree', function(event, data){
                    $("#orgTreePopup").jstree("select_node", "#" + paramAuthOrgId);

                });
            }
        });

    }

    // 선택한 조직 정보 리턴
    function selectOrgInfo(selectedOrgInfo) {

        var returnOrgId = "";
        var returnOrgNm = "";
        var returnOrgLvlCd = "";

        if (!isEmpty(selectedOrgInfo)) {
        	returnOrgId = selectedOrgInfo.orgId;
        	returnOrgNm = selectedOrgInfo.orgNm;
        	returnOrgLvlCd = selectedOrgInfo.orgLvlCd;
        }

        // 리턴값 설정
        $("#" + paramReturnIdForId).val(returnOrgId);
        $("#" + paramReturnIdForNm).val(returnOrgNm);

        if (!isEmpty(paramCallbackFuncNm) && typeof eval(paramCallbackFuncNm) == "function") {
        	// 파라메터 콜백함수명이 존재하는 경우
        	// 파라메터 설정
        	var param = new Object();
        	param.orgId = returnOrgId;
        	param.orgNm = returnOrgNm;
        	param.orgLvlCd = returnOrgLvlCd;

        	// 콜백 함수 호출
            new Function(paramCallbackFuncNm + "(" + JSON.stringify(param) + ")")();
        }

        // 화면 닫기
        $("#btnCloseFootPopup").trigger('click');
    }

</script>
<ul id="ul_orgTpDtlCodeList" name="ul_orgTpDtlCodeList" style="display:none;" >
<c:forEach items="${orgTpDtlCodeList}" var="orgTpDtlCodeList" varStatus="status">
	<li
		data-commcd="${orgTpDtlCodeList.commCd}"
		data-refcd="${orgTpDtlCodeList.refCd1}"
		data-commnm="${orgTpDtlCodeList.commNm}"
	/>
</c:forEach>
</ul>
<div class="modal-dialog modal-lg" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="modalTitle"><spring:message code="LAB.M09.LAB00141"/><!-- 조직검색 --></h4>
		</div>

		<div class="modal-body">
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
		                                <th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00492"/></label></th><!-- 권한조직ID -->
		                                <td id="authOrgIdPopup"></td>
		                            </tr>
                                    <tr>
                                        <th class="active"><label class="control-label"><spring:message code="LAB.M01.LAB00493"/></label></th><!-- 권한조직명 -->
                                        <td id="authOrgNmPopup"></td>
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
		                        <customTag:auth auth="${menuAuthR}">
				                    <a href="javascript:;" class="btn btn-black btn-lg pull-right" role="button" id="btnSearchOrgPopup"  ><i class="savanaicon-search"></i>&nbsp;<spring:message code="LAB.M09.LAB00158"/></a>
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
										<col width="auto">
									</colgroup>
									<tbody>
										<tr>
											<th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00268"/></label></th><!-- 조직유형 -->
											<td>
												<select id="orgTpPopup" name="orgTpPopup" class="form-control" >
													<c:forEach items="${orgTpCodeList}" var="orgTpCodeList" varStatus="status">
														<option value="${orgTpCodeList.commCd}">${orgTpCodeList.commNm}</option>
													</c:forEach>
												</select>
											</td>
											<th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00269"/></label></th><!-- 조직상세유형 -->
											<td>
		                                        <select id="orgTpDtlPopup" name="orgTpDtlPopup" class="form-control" >
		                                            <c:forEach items="${orgTpDtlCodeList}" var="orgTpDtlCodeList" varStatus="status">
		                                                <option value="${orgTpDtlCodeList.commCd}">${orgTpDtlCodeList.commNm}</option>
		                                            </c:forEach>
		                                        </select>
											</td>
										</tr>
		                                <tr>
		                                    <th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00139"/></label></th><!-- 조직ID -->
		                                    <td>
		                                        <input type="text" id="orgIdPopup" name="orgIdPopup" class="form-control">
		                                    </td>
		                                    <th class="active"><label class="control-label"><spring:message code="LAB.M09.LAB00147"/></label></th><!-- 조직명 -->
		                                    <td>
		                                        <input type="text" id="orgNmPopup" name="orgNmPopup" class="form-control">
		                                    </td>
		                                </tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="ibox">
						<div class="ibox-content">
							<div class="table-responsive" style="margin-top: 10px;">
								<table id="orgListPopup" class="table table-striped table-bordered table-hover">
									<thead>
				           				<tr>
                                            <th><spring:message code="LAB.M09.LAB00139"/></th><!-- 조직ID -->
                                            <th><spring:message code="LAB.M09.LAB00147"/></th><!-- 조직명 -->
                                            <th><spring:message code="LAB.M09.LAB00268"/></th><!-- 조직유형 -->
                                            <th><spring:message code="LAB.M09.LAB00269"/></th><!-- 조직상세유형 -->
                                            <th><spring:message code="LAB.M09.LAB00146"/></th><!-- 조직레벨 -->
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
			<button type="button" id="btnSelectOrgPopup" class="btn btn-black" ><spring:message code="LAB.M07.LAB00195" /></button>
			<button type="button" id="btnCloseFootPopup" class="btn btn-white" data-dismiss="modal"><spring:message code="LAB.M03.LAB00027" /></button>
		</div>
	</div>
 </div>