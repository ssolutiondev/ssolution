<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>

<script type="text/javascript">

var dashboardGrid;

$(document).ready(function() {
	
	//팝업 오픈 후 그리드 선언
	var target = "dashboardGrid"; // 그리드를 그리리려고 하는 id	
	var _parents = $('#' + target).parents('.modal').attr('id');
	
	$('#' + _parents  ).on('shown.bs.modal', function() {
	
		dashboardGrid = $('#dashboardGrid').DataTable( {
			processing: true,
			serverSide: false,
			scrollY: "160px",
			scrollCollapse: false,
			dom : '<t>',	//보여줄 내용 처리
			select: true,
			order :[],
			language: {
				url: getTableLngUrl()
			}, //필수
			columns: [
				{ data: "commNm" },
				{ data: "refCd1" }
			],
			ajax: {
				url: "/system/common/popup/dashboardViewPopup/getDashboardListAction.json",
				type: "post",
				data : function(d){
				}
			}
		});
		
		//그리드 row 클릭 이벤트
		dashboardGrid.on('select', function(e, dt, type, indexs){
			var row = $('#dashboardGrid').DataTable().rows('.selected').data();
		});
		
		//클릭 해지 이벤트 방지
		dashboardGrid.on('user-select', function (e, dt, type, cell, originalEvent) {
		    if ($(cell.node()).parent().hasClass('selected')) {
		        e.preventDefault();
		    }
		});
		
	});
	
	//선택버튼
	$("#btnSelectFoot").on("click", function(e) {
		setDashboardViewMng();
    });
});

function setDashboardViewMng(){
	
	var data = $('#dashboardGrid').DataTable().rows('.selected').data();
	
	var dashboardCd = "${dashboard.returnId1}";
	var dashboardNm = "${dashboard.returnId2}";
	
	if(data.length > 0){
		$("#"+dashboardNm).val(data[0].commNm);
		$("#"+dashboardCd).val(data[0].commCd);
	}else{
		$("#"+dashboardNm).val("");
		$("#"+dashboardCd).val("");
	}
	
	$("#btnCloseFoot").trigger("click");
	
}

</script>

<div class="modal-dialog" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="modalTitle">
				<spring:message code="LAB.M15.LAB00057" />
			</h4>
		</div>
		
		<div class="modal-body">
			
			<div class="ibox-content">
				<table id="dashboardGrid" class="table table-striped table-bordered table-hover" width="100%">
					<colgroup>
						<col style="width: 40%;">
						<col style="width: 60%;">
					</colgroup>
					<thead>
           				<tr>
							<th><spring:message code="LAB.M13.LAB00017"/></th>
		            		<th><spring:message code="LAB.M13.LAB00016"/></th>
						</tr>
					</thead>
				</table>
			</div>
 		</div>
		
		<div class="modal-footer">
			<button type="button" id="btnSelectFoot" class="btn btn-black" ><spring:message code="LAB.M07.LAB00195" /></button>
			<button type="button" id="btnCloseFoot" class="btn btn-white" data-dismiss="modal"><spring:message code="LAB.M03.LAB00027" /></button>
		</div>
	</div>
 </div>