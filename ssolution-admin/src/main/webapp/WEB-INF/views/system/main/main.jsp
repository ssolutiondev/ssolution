<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<script>

    var noticeList;	//noticeList
    var isTablesDrawed = true;

    $(document).ready(function(){
        noticeList = $('#noticeList').DataTable({

            processing: true,
            serverSide: false,
            scrollY: "31.0vh",
            sScrollX: true,
            sScrollXInner: "100%",
            scrollCollapse: false,
            dom : '<rt<"savana-pagarea"lip>>',	//보여줄 내용 처리 */
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
            },
            columns: [
                { data: "ntceId" },
                { data: "ntceTpNm" },
                { data: "title" },
                { data: "ntceStrtDt" },
                { data: "ntceEndDt" },
                { data: "readCnt" },
                { data: "chgrNm" },
                { data: "chgDate" }
            ],
            columnDefs: [
                {
                    targets: [0, 5], orderable: false // colunm 별 sortable 없내는 방법
                },
                {
                    className: "text-center",
                    targets: [0,1,3,4,7]
                },
                {
                    className: "text-right",
                    targets: [5]
                },
                {
                    render: function ( data, type, row ) {
                        return stringToDateformatYYYYMMDD(data); //setStrtEndDt(data);
                    },
                    targets: [3,4]
                },
                {
                    render: function ( data, type, row ) {
                        return dateTypeFormatterYYYYMMDDHH24MISS(data);
                    },
                    targets: [7]
                }
            ]
            ,initComplete: function( settings, json ){
                if(isTablesDrawed == false){
                    isTablesDrawed = true;
                }
            },ajax: {
                url: '/system/main/getNoticeListAction.json',
                type: "post",
                data : function(d){
                    d.isTablesDrawed = isTablesDrawed;
                }
            }
        });

        Chart.defaults.global.defaultFontColor = "#fff"; //dashboard에서만 폰트컬러 재선언

        getRcptStat();
        getNewStat();
        getChgStat();

        $('#noticeList tbody').on('dblclick', 'tr', function () {
            var data = noticeList.row(this).data();
            noticeDetailPopup(data.ntceId);
            setTimeout(function(){
                noticeList.clear();
                noticeList.ajax.reload();
            },1000);
        });


    });

    // 상담 건수 조회
    function getRcptStat() {
        var param = new Object();
        param.stdrDays = 7;

        $.ajax({
            url: '/system/main/getRcptStat.json',
            type: 'post',
            data: param,
            dataType: 'json',
            success: function(data){
                var formattLavel = data.arrLabel;
                $.each(formattLavel, function (idx) {
                    // 기준 날짜 포맷팅
                    formattLavel[idx] = formattLavel[idx].substr(4, 2) + "/" + formattLavel[idx].substr(6, 2);
                });

                $("#divRcptChart h3[id='rcptCmpl']").html(numberWithCommas(data.rcptCmplArray[6]));
                $("#divRcptChart h3[id='rcpt']").html(numberWithCommas(data.rcptArray[6]));

                drawRcptStatsChart(data.arrLabel, data.rcptCmplArray, data.rcptArray);
            }
        });
    }

    // 상담 통계 그래프 표시
    function drawRcptStatsChart(paramLabel, rcptCmplArray, rcptArray) {

        var data = {
            labels: paramLabel,
            datasets: [
                {
                    label: '<spring:message code="LAB.M08.LAB00044"/>', <!-- 완료 -->
                    data: rcptCmplArray,
                    backgroundColor: "rgba(255,255,255,0.1)",
                    borderColor : "rgba(255,255,255,0.9)",
                    lineTension: 0.0,
                    pointHoverBackgroundColor :"#00947f",
                    borderWidth: 1,
					pointRadius: 4,
					pointBackgroundColor: '#00947f',
                }
                ,{
                    label: '<spring:message code="LAB.M09.LAB00510"/>',
                    data: rcptArray,
                    backgroundColor: "rgba(33,251,143,0.2)",
                    borderColor :  "rgba(33,251,143,1.0)",
                    lineTension: 0.0,
                    pointHoverBackgroundColor :"#00947f",
                    borderWidth: 1,
					pointRadius: 5,
					pointBackgroundColor: '#00947f',
					pointStyle: 'rect',
					borderDash:[3,3],
            }
            ]
        };

        //세부 option부분
        var options = {
            legend: {display: false},
            layout: {padding: {right:10}},
            scales:{
                xAxes: [{
                    gridLines: {display:false, tickMarkLength:10, color:"rgba(255, 255, 255, 0.4)", zeroLineColor:"rgba(255, 255, 255, 0.0)",},
                }],
                yAxes: [{
                    position: "right",
                    type: "linear",
                    gridLines: {display:true, beginAtZero:true, drawTicks:false, tickMarkLength:10, zeroLineColor:"rgba(255, 255, 255, 1.0)", zeroLineWidth:2, drawBorder:true, color:"rgba(255, 255, 255, 1.0)", drawOnChartArea: false},
                    ticks: {padding:10, suggestMin: 100000}
                }]
            },
            tooltips: {
                mode: 'index',
                intersect: false,
                callbacks: { // custom tooltip 부분
                    title: function (tooltipItem, data) {
                        return "";
                    },
                    labelColor: function(tooltipItem, chart) {
                        var dataset = chart.config.data.datasets[tooltipItem.datasetIndex];
                        return {
                            //borderColor: 'rgba(0,0,0,0.0)',
                            //backgroundColor: 'rgba(0,0,0,0.0)'
                            backgroundColor : dataset.borderColor
                        }
                    },
                    label: function (tooltipItem, data) {
                        var value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                        return " " + value + " " + '<spring:message code="LAB.M01.LAB00376"/>' + " ";
                    }
                },
            }
        };

        // 해당차트 생성부분
        var ctx = document.getElementById("rcptChart").getContext("2d"); // 생성할 차트 id
        var rcptChart  = new Chart(ctx, {
            type: 'line',     //타입
            data: data,   //변수로 선언 >  세부 data부분
            options: options  //변수로 선언 >  세부 option부분
        });
    }

    // 신규 건수 조회
    function getNewStat() {
        var param = new Object();
        param.stdrDays = 7;

        $.ajax({
            url: '/system/main/getNewStat.json',
            type: 'post',
            data: param,
            dataType: 'json',
            success: function(data){
                var formattLavel = data.arrLabel;
                $.each(formattLavel, function (idx) {
                    // 기준 날짜 포맷팅
                    formattLavel[idx] = formattLavel[idx].substr(4, 2) + "/" + formattLavel[idx].substr(6, 2);
                });

                $("#divNewChart h3[id='newCmpl']").html(numberWithCommas(data.cmplArray[6]));
                $("#divNewChart h3[id='newRcpt']").html(numberWithCommas(data.rcptArray[6]));

                drawNewStatsChart(data.arrLabel, data.cmplArray, data.rcptArray);
            }
        });
    }

    // 상담 통계 그래프 표시
    function drawNewStatsChart(paramLabel, cmplArray, rcptArray) {

        var data = {
            labels: paramLabel,
            datasets: [
                {
                    label: '<spring:message code="LAB.M08.LAB00044"/>', <!-- 완료 -->
                    data: cmplArray,
                    backgroundColor: "rgba(255,255,255,0.1)",
                    borderColor : "rgba(255,255,255,0.9)",
                    lineTension: 0.0,
                    pointHoverBackgroundColor :"#484a4d",
                    borderWidth: 1,
					pointRadius: 4,
					pointBackgroundColor: '#484a4d',
                }
                ,{
                    label: '<spring:message code="LAB.M09.LAB00510"/>',
                    data: rcptArray,
                    backgroundColor: "rgba(50,202,199,0.1)",
                    borderColor :  "rgba(50,202,199,1.0)",
                    lineTension: 0.0,
                    pointHoverBackgroundColor :"#484a4d",
                    borderWidth: 1,
					pointRadius: 5,
					pointBackgroundColor: '#484a4d',
					pointStyle: 'rect',
					borderDash:[3,3],
                }
            ]
        };

        //세부 option부분
        var options = {
            legend: {display: false},
            layout: {padding: {right:10}},
            scales:{
                xAxes: [{
                    gridLines: {display:false, tickMarkLength:10, color:"rgba(255, 255, 255, 0.4)", zeroLineColor:"rgba(255, 255, 255, 0.0)",},
                }],
                yAxes: [{
                    position: "right",
                    type: "linear",
                    gridLines: {display:true, beginAtZero:true, drawTicks:false, tickMarkLength:10, zeroLineColor:"rgba(255, 255, 255, 1.0)", zeroLineWidth:2, drawBorder:true, color:"rgba(255, 255, 255, 1.0)", drawOnChartArea: false},
                    ticks: {padding:10, suggestMin: 100000}
                }]
            },
            tooltips: {
                mode: 'index',
                intersect: false,
                callbacks: { // custom tooltip 부분
                    title: function (tooltipItem, data) {
                        return "";
                    },
                    labelColor: function(tooltipItem, chart) {
                        var dataset = chart.config.data.datasets[tooltipItem.datasetIndex];
                        return {
                            //borderColor: 'rgba(0,0,0,0.0)',
                            //backgroundColor: 'rgba(0,0,0,0.0)'
                            backgroundColor : dataset.borderColor
                        }
                    },
                    label: function (tooltipItem, data) {
                        var value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                        return " " + value + " " + '<spring:message code="LAB.M01.LAB00376"/>' + " ";
                    }
                },
            }
        };

        // 해당차트 생성부분
        var ctx = document.getElementById("newChart").getContext("2d"); // 생성할 차트 id
        var newChart  = new Chart(ctx, {
            type: 'line',     //타입
            data: data,   //변수로 선언 >  세부 data부분
            options: options  //변수로 선언 >  세부 option부분
        });
    }

    // 변경 건수 조회
    function getChgStat() {
        var param = new Object();
        param.stdrDays = 7;

        $.ajax({
            url: '/system/main/getChgStat.json',
            type: 'post',
            data: param,
            dataType: 'json',
            success: function(data){
                var formattLavel = data.arrLabel;
                $.each(formattLavel, function (idx) {
                    // 기준 날짜 포맷팅
                    formattLavel[idx] = formattLavel[idx].substr(4, 2) + "/" + formattLavel[idx].substr(6, 2);
                });

                $("#divChgChart h3[id='chgCmpl']").html(numberWithCommas(data.cmplArray[6]));
                $("#divChgChart h3[id='chgRcpt']").html(numberWithCommas(data.rcptArray[6]));

                drawChgStatsChart(data.arrLabel, data.cmplArray, data.rcptArray);
            }
        });
    }

    // 변경 통계 그래프 표시
    function drawChgStatsChart(paramLabel, cmplArray, rcptArray) {

        var data = {
            labels: paramLabel,
            datasets: [
                {
                    label: '<spring:message code="LAB.M08.LAB00044"/>', <!-- 완료 -->
                    data: cmplArray,
                    backgroundColor: "rgba(255,255,255,0.1)",
                    borderColor : "rgba(255,255,255,0.9)",
                    lineTension: 0.0,
                    pointHoverBackgroundColor :"#bf0202",
                    borderWidth: 1,
					pointRadius: 4,
					pointBackgroundColor: '#bf0202',
                }
                ,{
                    label: '<spring:message code="LAB.M09.LAB00510"/>',
                    data: rcptArray,
                    backgroundColor: "rgba(255,162,0,0.2)",
                    borderColor :  "rgba(255,162,0,1.0)",
                    lineTension: 0.0,
                    pointHoverBackgroundColor :"#bf0202",
                    borderWidth: 1,
					pointRadius: 5,
					pointBackgroundColor: '#bf0202',
					pointStyle: 'rect',
					borderDash:[3,3],
                }
            ]
        };

        //세부 option부분
        var options = {
            legend: {display: false},
            layout: {padding: {right:10}},
            scales:{
                xAxes: [{
                    gridLines: {display:false, tickMarkLength:10, color:"rgba(255, 255, 255, 0.4)", zeroLineColor:"rgba(255, 255, 255, 0.0)",},
                }],
                yAxes: [{
                    position: "right",
                    type: "linear",
                    gridLines: {display:true, beginAtZero:true, drawTicks:false, tickMarkLength:10, zeroLineColor:"rgba(255, 255, 255, 1.0)", zeroLineWidth:2, drawBorder:true, color:"rgba(255, 255, 255, 1.0)", drawOnChartArea: false},
                    ticks: {padding:10, suggestMin: 100000}
                }]
            },
            tooltips: {
                mode: 'index',
                intersect: false,
                callbacks: { // custom tooltip 부분
                    title: function (tooltipItem, data) {
                        return "";
                    },
                    labelColor: function(tooltipItem, chart) {
                        var dataset = chart.config.data.datasets[tooltipItem.datasetIndex];
                        return {
                            //borderColor: 'rgba(0,0,0,0.0)',
                            //backgroundColor: 'rgba(0,0,0,0.0)'
                            backgroundColor : dataset.borderColor
                        }
                    },
                    label: function (tooltipItem, data) {
                        var value = data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index];
                        return " " + value + " " + '<spring:message code="LAB.M01.LAB00376"/>' + " ";
                    }
                },
            }
        };

        // 해당차트 생성부분
        var ctx = document.getElementById("chgChart").getContext("2d"); // 생성할 차트 id
        var chgChart  = new Chart(ctx, {
            type: 'line',     //타입
            data: data,   //변수로 선언 >  세부 data부분
            options: options  //변수로 선언 >  세부 option부분
        });
    }

    function noticeDetailPopup(ntceId){
        var param = new Object();
        param.ntceId = ntceId;
        param.inptMenuId = $("#headerCurMenuId").val();
        commonPopup('/system/notice/notice/notice/noticeDetailPopup.ajax', param);
    }

</script>

<style type="text/css">
    .mainChartBox .chartArea {height:20vh; min-height:210px;}
    .mainChartBox .chartArea canvas {height:100%; min-height:130px;}
    .productGraph .chartArea {height:19.5vh; min-height:155px; margin-top:0.8vh; margin-bottom:0.8vh;}
    .productGraph .chartArea canvas {height:100%; min-height:155px;}
    .dashboard .savana .productTotal {padding:2.7vh 5%; min-height:127px;}
    .savana .dataTables_scrollBody { min-height:223px;}
    .top-navigation.dashboard .wrapper.wrapper-content {padding-bottom:70px;}
    .productGraph .chartArea {position:relative;}
    .productGraph .chartArea .chartLegend {top:auto; bottom:8px;}
    .productGraph .chartArea .chartLegend ul li {margin:0 6px; font-size:12px; letter-spacing:-0.06em;}
    .productGraph .chartArea .chartLegend ul li span {width:8px; height:8px; margin:3px 2px 0 0;}
</style>

<div class="row">
    <div class="col-lg-4">
        <div class="ibox">
            <div class="ibox-content mainChartBox bgGreen" id="divRcptChart">
                <div class="title">
                    <div class="col-xs-8">
                        <h5 class="tit">
                            <spring:message code="LAB.M07.LAB00742"/><!-- 상담 -->
                            <small class="subtitle-text">&nbsp;(</small>
                            <small class="subtitle-text">
                                <spring:message code="LAB.M08.LAB00044"/>&nbsp; / &nbsp;<spring:message code="LAB.M09.LAB00510"/>
                            </small>
                            <small class="subtitle-text">)&nbsp;</small>
                        </h5>
                    </div>
                    <div class="col-xs-4">
                        <div class="pull-right">
                            <div class="text-right">
                                <h3 class="value" id="rcptCmpl" style="display: inline;"></h3>
                                <h3 style="display: inline;">&nbsp;/&nbsp;</h3>
                                <h3 class="value" id="rcpt" style="display: inline;"></h3>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="chartArea">
                    <canvas id="rcptChart" style="height:140px"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox">
            <div class="ibox-content mainChartBox bgBlack" id="divNewChart">
                <div class="title">
                    <div class="col-xs-8">
                        <h5 class="tit">
                            <spring:message code="LAB.M07.LAB00743"/><!-- 신규가입 -->
                            <small class="subtitle-text">&nbsp;(</small>
                            <small class="subtitle-text">
                                <spring:message code="LAB.M01.LAB00724"/>&nbsp; /&nbsp; <spring:message code="LAB.M07.LAB00744"/>
                            </small>
                            <small class="subtitle-text">)&nbsp;</small>
                        </h5>
                    </div>
                    <div class="col-xs-4">
                        <div class="pull-right">
                            <div class="text-right">
                                <h3 class="value" id="newCmpl" style="display: inline;"></h3>
                                <h3 style="display: inline;">&nbsp;/&nbsp;</h3>
                                <h3 class="value" id="newRcpt" style="display: inline;"></h3>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="chartArea">
                    <canvas id="newChart" style="height:140px"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-lg-4">
        <div class="ibox">
            <div class="ibox-content  mainChartBox bgRed" id="divChgChart">
                <div class="title">
                    <div class="col-xs-8">
                        <h5 class="tit">
                            <h5 class="tit">
                                <spring:message code="LAB.M06.LAB00314"/><!-- 변경 -->
                                <small class="subtitle-text">&nbsp;(</small>
                                <small class="subtitle-text">
                                    <spring:message code="LAB.M08.LAB00044"/>&nbsp; /&nbsp; <spring:message code="LAB.M09.LAB00510"/>
                                </small>
                                <small class="subtitle-text">)&nbsp;</small>
                            </h5>
                        </h5>
                    </div>
                    <div class="col-xs-4">
                        <div class="pull-right">
                            <div class="text-right">
                                <h3 class="value" id="chgCmpl" style="display: inline;"></h3>
                                <h3 style="display: inline;">&nbsp;/&nbsp;</h3>
                                <h3 class="value" id="chgRcpt" style="display: inline;"></h3>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="chartArea">
                    <canvas id="chgChart" style="height:140px"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <div class="ibox">
            <div class="ibox-title">
                <h5><spring:message code="LAB.M01.LAB00092"/></h5><!-- 공지사항 -->
            </div>
            <div class="ibox-content">
                <div class="table-responsive">
                    <table id="noticeList" class="table table-striped table-bordered table-hover" >
                        <colgroup>
                            <col style="width: 5%;">
                            <col style="width: 10%;">
                            <col style="width: 40%;">
                            <col style="width: 10%;">
                            <col style="width: 10%;">
                            <col style="width: 5%;">
                            <col style="width: 10%;">
                            <col style="width: 10%;">
                        </colgroup>
                        <thead>
                        <tr>
                            <th><spring:message code="LAB.M06.LAB00050"/></th><!-- 공지사항번호 -->
                            <th><spring:message code="LAB.M01.LAB00356"/></th><!-- 게시타입 -->
                            <th><spring:message code="LAB.M09.LAB00092"/></th><!-- 제목 -->
                            <th><spring:message code="LAB.M01.LAB00097"/></th><!-- 개시일자 -->
                            <th><spring:message code="LAB.M01.LAB00098"/></th><!-- 개시일자 -->
                            <th><spring:message code="LAB.M09.LAB00161"/></th><!-- 조회수 -->
                            <th><spring:message code="LAB.M07.LAB00256"/></th><!-- 수정자 -->
                            <th><spring:message code="LAB.M07.LAB00254"/></th><!-- 수정일시 -->
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
