<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<script type="text/javascript">

var mainList;	//mainList
var isTablesDrawed = false;

$(document).ready(function(){
	
	//setSearchDate(); // 검색 날짜 셋팅
	$("#sdate").val(setGapDate(-31)); // 검색 날짜 셋팅
	$("#edate").val(getToday()); // 검색 날짜 셋팅
	
	//검색 달력 
	$('#searchDate .input-daterange').datepicker({
		language : lng,
		todayBtn: "linked",
        keyboardNavigation: false,
        calendarWeeks: true,
        autoclose: true,
        endDate : dateFormatterUsingDateYYYYMMDD(new Date),
	    format: "yyyy-mm-dd"
	});
	
	mainList = $('#mainList').DataTable({
		processing: true,
		serverSide: false,
		scrollY: "250px",
		scrollCollapse: false,
		dom : 'Z<rt<"savana-pagarea"lip>>',	//보여줄 내용 처리 */
		colResize: {
			"tableWidthFixed": false
		},
		pagingType: "full_numbers",	        //페이징 타입
		lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],	//화면에 보여줄 갯수처리
		language: {
			url: getTableLngUrl()
		},  //필수
		colReorder: true,
		select: true,
		order: [],                                              // table 로딩시 sortable을 셋팅하고 싶을때
		columns: [
			{ data: "reqId" },
			{ data: "reqDt" },
			{ data: "reqUserNm" },
			{ data: "reqStat" },
			{ data: "procDt" },
			{ data: "procUserNm" },
			{ data: "regrId" },
			{ data: "regDate" },
			{ data: "chgrId" },
			{ data: "chgDate" }
		],
		columnDefs: [
			{
				className: "text-center",
				targets: [0,1,3,4,7,9] 
			},
  			{
				render: function ( data, type, row ) {
					return stringToDateformatYYYYMMDD(data);
				},
				targets: [1,4] 
			},
			{
	  			render: function ( data, type, row ) {
					return dateTypeFormatterYYYYMMDDHH24MISS(data);
				},
				targets: [7,9]
			},
			{
	  			render: function ( data, type, row ) {
	  				return setStat(data);
				},
				targets: [3]
			}
  		]
		,initComplete: function( settings, json ){
			if(isTablesDrawed == false){
				isTablesDrawed = true;
			}
		},ajax: {
			url: "/system/authMng/grpReqMng/grpReqMng/mainListAction.json",
			type: "post",
			data : function(d){
				d.condReqUserId = $("#condReqUserId").val();	//검색어 : 요청자
				d.condReqStat = $("#condReqStat").val();	//검색어 : 요청상태
				d.start = dateFormatToStringYYYYMMDD($("#sdate").val());
				d.end =  dateFormatToStringYYYYMMDD($("#edate").val());
				d.isTablesDrawed = isTablesDrawed;
			}
		}
    });
	
	//그리드 row 클릭 이벤트
	mainList.on('select', function(e, dt, type, indexs){
		var row = $('#mainList').DataTable().rows('.selected').data();
		setSelectedData(row[0]);
	});
	
	//클릭 해지 이벤트 방지
	mainList.on('user-select', function (e, dt, type, cell, originalEvent) {
	    if ($(cell.node()).parent().hasClass('selected')) {
	    	var row = $('#mainList').DataTable().rows('.selected').data();
			setSelectedData(row[0]);
	    	
	        e.preventDefault();
	    }
	});
	
	//권한 요청 조회
	$("#btnSearch").on("click", function(e){
		searchMainList();
	});
	
	//상세정보 초기화
	$("#btnInit").on("click", function(e){
		initGrpReqInfo();
	});
	
	//권한 요청 등록
	$("#btnInsert").on("click", function(e){
		insertGrpReqInfo();
	});
	
	//권한 요청 수정
	$("#btnUpdate").on("click", function(e){
		updateGrpReqInfo();
	});
	
	//사용자 검색 popup
	$("#btnUserPopup").on("click", function(e){
		userPopup();
	});
	
});

function searchMainList(){
	if($("#sdate").val() == ""){
		alert("<spring:message code='MSG.M07.MSG00240'/>");
		$("#sdate").focus();
		return;
	}
	if($("#edate").val() == ""){
		alert("<spring:message code='MSG.M09.MSG00170'/>");
		$("#edate").focus();
		return;
	}
	if($("#sdate").val() > $("#edate").val()){
		$('#sdate').focus();
		swal('<spring:message code="MSG.M09.MSG00105"/>', "", "error");
		return false;
	}
	if(getDateDiff(dateFormatToStringYYYYMMDD($("#sdate").val()), dateFormatToStringYYYYMMDD($("#edate").val())) > 31){
		swal('<spring:message code="MSG.M07.MSG00179"/>', "", "error");
		/* $('#sdate').focus(); */
		return false;
	}
	
	mainList.clear();
	mainList.ajax.reload();
	
	clearUserReqInfo();		//권한 요청 정보 초기화
}

function setStat(data){
	var state = "SEL";
	"<c:forEach items="${reqStatList }" var="item">";
	if("${item.commCd}" == data){
		state = "${item.commNm}";
	}
	"</c:forEach>";
	
	return state;
		
}

function userPopup(){
	// 파라메터 설정 - 리턴값 설정할 항목명
    var param = new Object();
    param.returnIdForUserId = "condReqUserId";
    param.returnIdForUserNm = "condReqUserNm";
    //param.paramUserId = $("#condReqUserId").val();
    param.paramUserNm = $("#condReqUserNm").val();

	commonPopup('/system/common/popup/userPopup/userPopup.ajax', param);
}

function clearUserReqInfo(){
	//태그 비활성화
	domDisableds(["reqId","reqDt","reqUserNm","reqStat","procUserNm","procDate","reqCt","procCt"]);
	//값 초기화
	valueClears(["reqId","reqDt","reqDate","reqUserId","reqUserNm","reqStat","procUserNm","procUserId","procDate","reqCt","procCt"]);
	
	//버튼 활성화
	domEnableds(["btnInit"]);
	//버튼비활성화
	domDisableds(["btnInsert","btnUpdate","btnDelete"]);
	
	//선택된 로우 해지
	mainList.rows().deselect();
}

function initGrpReqInfo(){
	//태그 활성화
	domEnableds(["reqCt"]);
	//태그 비활성화
	domDisableds(["reqId","reqUserNm","reqDt","reqStat","procUserNm","procDate","procCt"]);
	//값 초기화
	valueClears(["reqId","reqDt","reqUserId","reqUserNm","reqStat","procUserId","procUserNm","procDate","reqCt","procCt","reqDate"]);
	
	$("#reqStat").val("A");
	$("#reqUserNm").val("${session_user.userNm }");
	$("#reqDt").val(getToday());
	
	//버튼 활성화
	domEnableds(["btnInit","btnInsert"]);
	//버튼비활성화
	domDisableds(["btnUpdate","btnDelete"]);
	
	//선택된 로우 해지
	mainList.rows().deselect();
	
}

function checkParam(type){		//update일 경우, 파라미터로 type = 1을 전달
	var param = new Object();
	
	param.reqCt = $("#reqCt").val().trim();
	param.inptMenuId = $("#headerCurMenuId").val();
	param.reqId = $("#reqId").val();
	param.reqDt = dateFormatToStringYYYYMMDD($("#reqDt").val());
	param.reqUserId = $("#reqUserId").val();
	param.reqStat = $("#reqStat").val();
	
	param.procUserId = $("#procUserId").val();
	param.procCt = $("#procCt").val().trim();
	
	if(isEmpty(param.reqStat)){
		$('#reqStat').focus();
		swal('<spring:message code="MSG.M08.MSG00144"/>', "", "error");
		return;
	}
	
	if(isEmpty(param.reqCt)){
		$('#reqCt').focus();
		swal('<spring:message code="MSG.M08.MSG00105"/>', "", "error");
		return;
	}
	
	if(type == 1){
		if(param.reqStat == "A"){
			$('#reqStat').focus();
			swal('<spring:message code="MSG.M08.MSG00149"/>', "", "error");
			return;
		}
		if(isEmpty(param.procCt)){
			$('#procCt').focus();
			swal('<spring:message code="MSG.M10.MSG00057"/>', "", "error");
			return;
		}
		param.procDt = dateFormatToStringYYYYMMDD(getToday());
		param.reqId = $("#reqId").val();
	}
	
	return param;
}


function insertGrpReqInfo(){
	var param = checkParam();
	
	if(param){
		swalConfirm('<spring:message code="MSG.M03.MSG00036"/>', "warning", function(){
			 $.ajax({
				url:'/system/authMng/grpReqMng/grpReqMng/insertGrpReqInfoAction.json',
				type:'POST',
				data : param,
				dataType: 'json',
				success: function(data){
					searchMainList();
					
					swal('<spring:message code="MSG.M09.MSG00005"/>',"","success");
				}
			});
		});
	}
}

function updateGrpReqInfo(){
var param = checkParam(1);
	
	if(param){
		swalConfirm('<spring:message code="MSG.M07.MSG00088"/>', "warning", function(){
			 $.ajax({
				url:'/system/authMng/grpReqMng/grpReqMng/updateGrpReqInfoAction.json',
				type:'POST',
				data : param,
				dataType: 'json',
				success: function(data){
					searchMainList();
					
					swal('<spring:message code="MSG.M07.MSG00089"/>',"","success");
				}
			});
		});
	}
}

// 사용자 권한 요청 상세보기
function setSelectedData(data){
	//태그 활성화 
	domEnableds(["reqStat"]);
	//태그 비활성화 
	domDisableds(["reqCt","reqId","reqDt","reqUserId","procDt","procUserNm"]);
	//값 초기화
	valueClears(["reqStat","procCt","reqId","reqCt","reqDt","reqUserId","reqUserNm","procDt","procUserId","procUserNm"]);
	
	domEnableds(["procCt"]);
	//태그 비활성화 
	domDisableds(["reqCt"]);
	
	$("#reqDate").val(dateTypeFormatterYYYYMMDDHH24MISS(data.reqDate));
	$("#procDate").val(dateTypeFormatterYYYYMMDDHH24MISS(data.procDate));
	$("#reqUserId").val(data.reqUserId);
	$("#reqUserNm").val(data.reqUserNm);
	$("#procUserId").val(data.procUserId);
	$("#procUserNm").val(data.procUserNm);
	$("#reqStat").val(data.reqStat);
	$("#reqId").val(data.reqId);
	$("#reqCt").val(data.reqCt);
	$("#procCt").val(data.procCt);
	
	//버튼 활성화
	domEnableds(["btnInit"]);
	if(data.reqStat == "B" || data.reqStat == "C"){	// 완료(B),반려(C) 일경우는 수정버튼 비활성화
		domDisableds(["btnUpdate"]);
		domDisableds(["reqStat","procCt"]);
	}else{
		domEnableds(["btnUpdate"]);
		domEnableds(["reqStat","procCt"]);
	}
	//버튼 비활성화
	domDisableds(["btnInsert"]);
}

//검색 날짜 셋팅
function setGapDate(gapDate){ 
	var today = getToday();
	
	var startArray = today.split("-");
	var startObj = new Date(startArray[0], Number(startArray[1])-1, startArray[2]);
	var startDt = (Math.ceil((startObj.getTime())/(1000*60*60*24))) + gapDate +1 ;	
	var newDt = new Date(startDt*(1000*60*60*24));
	
	var year  = newDt.getFullYear();
    var month = newDt.getMonth() + 1; // 0부터 시작하므로 1더함 더함
    var day   = newDt.getDate();

	if (("" + month).length == 1) { month = "0" + month; }
	if (("" + day).length   == 1) { day   = "0" + day;   }
	var resultDate = (year + "-" + month + "-" + day);  
	
	return resultDate;	
}

function getToday() { 
	var date = new Date();
	   
    var year  = date.getFullYear(); 
    var month = date.getMonth() + 1 ; // 0부터 시작하므로 1더함 더함
    var day   = date.getDate();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }
   
	return (year + "-" + month + "-" + day);  

}

</script>
<div class="row">
	<div class="col-lg-12">
		<div class="ibox">
			<div class="ibox-title">
				<div class="ibox-tools">
					<customTag:auth auth="${menuAuthR}">
						<a href="javascript:;" class="btn btn-black btn-lg pull-right" role="button" id="btnSearch"><i class="savanaicon-search"></i>&nbsp;<spring:message code="LAB.M09.LAB00158"/></a>
					</customTag:auth>
				</div>
			</div>
			<div class="ibox-content">
                <div class="table-responsive">
                	<table class="table table-bordered table-search savana-b">
	                	<colgroup>
							<col width="12%">
							<col width="21.333%">
							<col width="12%">
							<col width="21.333%">
							<col width="12%">
							<col width="21.333%">
						</colgroup>
	                	<tbody>
		               		<tr>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00082"/></label></th>
		               			<td>
		               				<div class="input-group input-daterange" id="searchDate">
									    <input type="text" class="form-control" name="start" id="sdate"/>
									    <span class="input-group-addon">to</span>
									    <input type="text" class="form-control" name="end" id="edate" />
									</div>
		               			</td>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00083"/></label></th>
		               			<td>
		               				<div class="input-group">
		               					<input type="text" id="condReqUserNm" name="condReqUserNm" class="form-control" disabled> 
		               					<input type="hidden" id="condReqUserId" name="condReqUserId" class="form-control" disabled> 
		               					<span class="input-group-btn">
	           								<a href="javascript:;" class="btn btn-sm btn-red" role="button" id="btnUserPopup"><i class="savanaicon-search"></i></a>
	           							</span>
	                                </div>
		               			</td>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00234"/></label></th>
		               			<td>
		               				<select id="condReqStat" name="condReqStat" class="form-control">
										<c:forEach items="${reqStatList }" var="item">
						             		<option value="${item.commCd}">${item.commNm}</option>
						             	</c:forEach>
									</select>
		               			</td>
		               		</tr>
	                	</tbody>
                	</table>
                </div>
             </div>
		</div>
		
		<div class="ibox">
			<div class="ibox-title">
				<h5><spring:message code="LAB.M08.LAB00235"/></h5><!-- 요청리스트 -->
			</div>
			<div class="ibox-content">
                <div class="table-responsive">
		            <table id="mainList" class="table table-striped table-bordered table-hover" >
			            <thead>
				            <tr>
				                <th><spring:message code="LAB.M08.LAB00236"/></th>
				                <th><spring:message code="LAB.M08.LAB00082"/></th>
				                <th><spring:message code="LAB.M08.LAB00083"/></th>
				                <th><spring:message code="LAB.M08.LAB00234"/></th>
				                <th><spring:message code="LAB.M10.LAB00023"/></th>
				                <th><spring:message code="LAB.M10.LAB00024"/></th>
				                <th><spring:message code="LAB.M03.LAB00082"/></th>
				                <th><spring:message code="LAB.M03.LAB00080"/></th>
				                <th><spring:message code="LAB.M07.LAB00256"/></th>
				                <th><spring:message code="LAB.M07.LAB00254"/></th>
				            </tr>
			            </thead>
			            <!-- <tbody>
				            <tr>
				                <td>201700001</td>
				                <td>2017.09.30</td>
				                <td>홍길동(admin4)</td>
				                <td>신청중</td>
				                <td></td>
				                <td></td>
				                <td>관리자(admin)</td>
				                <td>2017-02-03 14:00:00</td>
				                <td>관리자(admin)<</td>
				                <td>2017-02-03 14:00:00</td>
				            </tr>
			            </tbody> -->
		            </table>
                </div>
            </div>
		</div>
		
		<div class="ibox">
			<div class="ibox-title">
				<h5><spring:message code="LAB.M01.LAB00294"/></h5><!-- 권한요청 정보 -->
			</div>
			<div class="ibox-content">
                <div class="table-responsive">
		            <table class="table table-bordered table-info savana-b">
	                	<colgroup>
							<col width="12%">
							<col width="">
							<col width="12%">
							<col width="">
							<col width="12%">
							<col width="">
							<col width="12%">
							<col width="">
						</colgroup>
	                	<tbody>
		               		<tr>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00236"/></label></th>
		               			<td><!-- 요청 ID -->
		               				<input name="reqId" id="reqId" type="text" class="form-control" disabled />
		               			</td>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00242"/></label></th>
		               			<td><!-- 요청일시 -->
		               				<input name="reqDate" id="reqDate" type="text" class="form-control" disabled />
		               			</td>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00083"/></label></th>
		               			<td><!-- 요청자 -->
		               				<input name="reqUserId" id="reqUserId" type="hidden" class="form-control" disabled />
		               				<input name="reqUserNm" id="reqUserNm" type="text" class="form-control" disabled />
		               			</td>
		               		</tr>
		               		<tr>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M10.LAB00024"/></label></th>
		               			<td><!-- 처리자 -->
		               				<input name="procUserId" id="procUserId" type="hidden" class="form-control" disabled />
		               				<input name="procUserNm" id="procUserNm" type="text" class="form-control" disabled />
		               			</td>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M10.LAB00021"/></label></th>
		               			<td><!-- 처리일시 -->
		               				<input name="procDate" id="procDate" type="text" class="form-control" disabled />
		               			</td>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00234"/></label></th>
		               			<td><!-- 요청상태 -->
		               				<select name="reqStat" id="reqStat" class="form-control" disabled>
						             	<c:forEach items="${reqStatListInput }" var="item">
						             		<option value="${item.commCd}">${item.commNm}</option>
						             	</c:forEach>
						        	</select>
		               			</td>
		               		</tr>
		               		<tr>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M08.LAB00080"/></label><span class="pull-right imp">*</span></th>
		               			<td colspan="7"><!-- 요청내용 -->
		               				<textarea  class="form-control" id="reqCt" name="reqCt" rows="4" disabled></textarea>
		               			</td>
		               		</tr>
		               		<tr>
		               			<th class="active"><label class="control-label"><spring:message code="LAB.M10.LAB00019"/></label><span class="pull-right imp">*</span></th>
		               			<td colspan="7"><!-- 처리내용 -->
		               				<textarea  class="form-control" id="procCt" name="reqCt" rows="4" disabled></textarea>
		               			</td>
		               		</tr>
	                	</tbody>
                	</table>
                </div>
            </div>
		</div>
		
		<div class="btnArea">
			<div class="form-group pull-right">
				<customTag:auth auth="${menuAuthC}">
				<button id="btnInit" class="btn btn-de btn-white" title='<spring:message code="LAB.M10.LAB00050"/>' disabled ><i class="savanaicon-reflash"></i>&nbsp;<spring:message code="LAB.M10.LAB00050"/></button>
				<button id="btnInsert" class="btn btn-de btn-black" title='<spring:message code="LAB.M03.LAB00075"/>' disabled ><i class="savanaicon-reg"></i>&nbsp;<spring:message code="LAB.M03.LAB00075"/></button>
				</customTag:auth>
				<customTag:auth auth="${menuAuthU}">
				<button id="btnUpdate" class="btn btn-de btn-red" title='<spring:message code="LAB.M07.LAB00252"/>' disabled ><i class="savanaicon-edit"></i>&nbsp;<spring:message code="LAB.M07.LAB00252"/></button>
				</customTag:auth>
			</div>
		</div>
	    
	</div>
</div>	
	
	

