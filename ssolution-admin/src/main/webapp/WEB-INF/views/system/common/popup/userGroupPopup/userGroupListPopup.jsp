<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>

<script type="text/javascript">

var userGroupPopGrid;
var isTablesDrawedPop = false;

$(document).ready(function() {
	
	//팝업 오픈 후 그리드 선언
	var target = "userGroupPopGrid"; // 그리드를 그리리려고 하는 id	
	var _parents = $('#' + target).parents('.modal').attr('id');
	
	$('#' + _parents  ).on('shown.bs.modal', function() {
	
		userGroupPopGrid = $('#userGroupPopGrid').DataTable( {
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
				{ data: "userGroupId" },
				{ data: "userGroupName" }
			],
			ajax: {
				url: "/system/common/popup/userGroupPopup/userGroupListActionPopup.json",
				type: "post",
				data : function(d){
				}
			}
		});
		
		//그리드 row 클릭 이벤트
		userGroupPopGrid.on('select', function(e, dt, type, indexs){
			var row = $('#userGroupPopGrid').DataTable().rows('.selected').data();
			//setSelectedData(row[0]);
		});
		
		//클릭 해지 이벤트 방지
		userGroupPopGrid.on('user-select', function (e, dt, type, cell, originalEvent) {
		    if ($(cell.node()).parent().hasClass('selected')) {
		        e.preventDefault();
		    }
		});
		
	});
	
	//선택버튼
	$("#btnSelectFoot").on("click", function(e) {
		setUserGroup();
    });
});

function setUserGroup(){
	
	var data = $('#userGroupPopGrid').DataTable().rows('.selected').data();
	
	var userGroupId = "${userGroupId}";
	var userGroupName = "${userGroupName}";
	
	if(data.length > 0){
		$("#"+userGroupId).val(data[0].userGroupId);
		$("#"+userGroupName).val(data[0].userGroupName);
	}else{
		$("#"+userGroupId).val("");
		$("#"+userGroupName).val("");
	}
	
	$("#btnCloseFoot").trigger("click");
	
}

</script>

<input type="hidden" id="tmp1" name="tmp1" value="${userGroup.userGroupId}"/>
<input type="hidden" id="tmp2" name="tmp2" value="${userGroup.userGroupName}"/>
<input type="hidden" id="multiFlag" name="multiFlag" value="${userGroup.multiFlag}"/>
<input type="hidden" id="userId" name="userId" value="${userId}"/>
<input type="hidden" id="baseId" name="baseId" value="${baseId}"/>

<div class="modal-dialog" role="document">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="modalTitle">
				<c:if test="${userGroup.multiFlag eq 'N'}"> 
					<spring:message code="LAB.M01.LAB00202"/>
				</c:if>
				<c:if test="${userGroup.multiFlag ne 'N'}"> 
					<spring:message code="LAB.M01.LAB00177"/>
				</c:if>
			</h4>
		</div>
		
		<div class="modal-body">
			
			<div class="ibox-content">
				<table id="userGroupPopGrid" class="table table-striped table-bordered table-hover" width="100%">
					<colgroup>
						<col style="width: 40%;">
						<col style="width: 60%;">
					</colgroup>
					<thead>
           				<tr>
							<th><spring:message code="LAB.M01.LAB00202" /></th>
		            		<th><spring:message code="LAB.M01.LAB00203" /></th>
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