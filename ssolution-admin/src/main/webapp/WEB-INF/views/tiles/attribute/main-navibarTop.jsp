<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>


<script type="text/javascript">
var client = null;

$(function () {
    var isLogin = '${isLogin}';

    $("#pswdChgPopup").on("click", function(e) {
        var param = new Object();
        //url, param , width
        commonPopup('/system/userMng/userInfoMng/userMngAdmin/pwdChgPopup.ajax', param, '500');

    });
    
    if(isLogin == 'Y'){
        toastr.options = {
                  "closeButton": true,
                  "debug": false,
                  "progressBar": true,
                  "preventDuplicates": false,
                  "positionClass": "toast-top-right",
                  "onclick": null,
                  "showDuration": "400",
                  "hideDuration": "1000",
                  "timeOut": "5000",
                  "extendedTimeOut": "1000",
                  "showEasing": "swing",
                  "hideEasing": "linear",
                  "showMethod": "fadeIn",
                  "hideMethod": "fadeOut"
        };
        
        
        
        var lastDate = '${session_user.lastLoginDate1}';

        toastr['success']( stringToDateformatYYYYMMDDHH24MISS(lastDate), '<div class="userInfoToastr"><span>${session_user.orgNm}</span><span>${session_user.userNm}</span></div><div class="loginTimeToastr"><spring:message code="LAB.M10.LAB00054"/></div>');
    }
    var fastMenu = getFastMenu();

    $('.typeahead').typeahead({
            items: 20,
            autoSelect :false,
            source: JSON.parse(fastMenu),
            afterSelect: function(item) {
                console.log(item);
                goLeftMenuPage(item.subMenuId, item.viewPath, item.menuId, item.name, item.topMenuId, item.topMenuNm);
            }
    });

    dorwBokMrkMenu("MAIN");

    getUnreadNoticeList();

});

function logout() {
    swal({
        title: '<spring:message code="MSG.M04.MSG00005"/>',
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        closeOnConfirm: false
    }, function () {
        if (client != null) {
            client.disconnect();
            client = null;
        }
        
        document.frmMenuHandle.action="<%=request.getContextPath()%>/system/login/logoutAction";
        document.frmMenuHandle.method="post";
        document.frmMenuHandle.submit();
    });
}

/*
 * 즐겨찾기 그리기
 */
var dorwBokMrkMenu = function(type){
    
    var bokMrkMenu = getBokMrkMenu();
    var bokMrkHtml = "";
    
    $.each(bokMrkMenu, function(index, data){
        bokMrkHtml += '<li><a href="javascript:;" class="inline_link" onclick="goLeftMenuPage(\''+data.subMenuId+'\',\''+data.viewPath+'\',\''+data.menuId+'\',\''+data.name+'\',\''+data.topMenuId+'\',\''+data.topMenuNm+'\');">' + data.name + '</a> <i class="savanaicon-del inline_icoBtn" onclick="deleteUserBokMrk(\''+data.menuId+'\');" ></i></li>'
    });
    
    if(type == "MENU" && (bokMrkMenu == null || bokMrkMenu.length < 10) ){
        bokMrkHtml += '<li class="addBtn"><a href="javascript:;" class="btn" onclick="insertUserBokMrk();"><i class="savanaicon-mark_plus"></i><spring:message code="LAB.M10.LAB00059"/></a></li>';
    }
    
    if(bokMrkMenu == null){
        $("#bokMrkCount").html(0);
        if(type == "MAIN"){
            $("#dropdownBokMrk").addClass("disabled");
        }
    }else{
        if(type == "MAIN"){
            $("#dropdownBokMrk").removeClass("disabled");
        }
        $("#bokMrkCount").html(bokMrkMenu.length)
    }
    
    $("#dropdown-favorite").html(bokMrkHtml);
    
}

/*
 * 즐겨찾기 추가
 */
var insertUserBokMrk = function(){
    
    var param = new Object();
    //param.userId = "";
    param.menuId = $("#headerCurMenuId").val();
    param.inptMenuId = $("#headerCurMenuId").val();
    
    var bokMrkMenu = getBokMrkMenu();
    var chkMenu = false;
    
    $.each(bokMrkMenu, function(index, data){
        if(param.menuId == data.menuId){
            chkMenu = true;
        }
    });
    
    if(chkMenu){
        swal('<spring:message code="MSG.M09.MSG00111"/>', "", "error");		//즐겨찾기에 등록된 메뉴 입니다.
        return;
    }else{
        swalConfirm('<spring:message code="MSG.M09.MSG00112"/>', "warning", function(){	//즐겨찾기 등록을 하시겠습니까?
            $.ajax({
                url:'/system/common/menu/userBokMrk/insertUserBokMrkAction.json',
                type:'POST',
                data : param,
                dataType: 'json',
                success: function(data){
                    
                    setBokMrkMenu(data.BOK_MRK_MENU);
                    dorwBokMrkMenu("MENU");
                    
                    swal('<spring:message code="MSG.M03.MSG00038"/>',"","success");
                    
                }
            });
        }); //즐겨찾기 등록을 하시겠습니까?
    }
    
};

/*
 * 즐겨찾기 삭제
 */
var deleteUserBokMrk = function(menuId){
    console.log($("#headerCurMenuId").val());
    var param = new Object();
    param.menuId = menuId;
    
    swalConfirm('<spring:message code="MSG.M09.MSG00113"/>', "warning", function(){		//즐겨찾기를 삭제 하시겠습니까?
        $.ajax({
            url:'/system/common/menu/userBokMrk/deleteUserBokMrkAction.json',
            type:'POST',
            data : param,
            dataType: 'json',
            success: function(data){
                
                setBokMrkMenu(data.BOK_MRK_MENU);
                if(isEmpty($("#headerCurMenuId").val())){
                    dorwBokMrkMenu("MAIN");
                }else{
                    dorwBokMrkMenu("MENU");
                }
                
                swal('<spring:message code="MSG.M07.MSG00053"/>',"","success");
                
            }
        });
    }); //즐겨찾기를 삭제 하시겠습니까?
    
};

// 미확인 공지 사항 조회
function getUnreadNoticeList() {

    // 공지사항 조회
    $.ajax({
        url: '/system/main/getUnreadNoticeList.json',
        type: 'post',
        dataType: 'json',
        success: function(data){

            var noticeHtml = '';

            if(data.noticeCnt == '0'){

                // 건수 설정
                $("#noticeListCntForNaviTop").html('0');
                // 메세지 설정
                $("#noticeListForNaviTop").html(noticeHtml);

            }else{
                for(i = 0; i < data.noticeCnt; i++){

                    noticeHtml += '<li><a href="javascript:;" class="inline_link" onclick="openNoticePopup(\''+ data.noticeList[i].ntceId +'\');">'
                        + '<i class="savanaicon-alert_line"></i>'
                        + data.noticeList[i].title
                        + '</a></li>';
                }

                // 건수 설정
                $("#noticeListCntForNaviTop").html(data.noticeCnt);
                // 메세지 설정
                $("#noticeListForNaviTop").html(noticeHtml);
            }
        }
    });

}

function openNoticePopup(id) {
    noticeDetailPopup(id);
    setTimeout(function(){
        getUnreadNoticeList();
    },2000);

}

function noticeDetailPopup(ntceId){
    var param = new Object();
    param.ntceId = Number(ntceId);
    param.inptMenuId = $("#headerCurMenuId").val();
    commonPopup('/system/notice/notice/notice/noticeDetailPopup.ajax', param);
}



</script>

<nav class="navbar navbar-static-top savana-gnb" role="navigation">
    <div class="navbar-header savana-logo">
        <h1 class="navbar-brand"><span class="hidden">SAVANA MIS</span></h1>
    </div>
    <div class="navbar-collapse" id="navbar">
        <ul class="nav navbar-top-links navbar-left savana-gnbArea">
            <customTag:topmenu />
        </ul>
        <ul class="nav navbar-top-links navbar-right savana-privateArea">
            <li>
                <input type="text" placeholder="<spring:message code="LAB.M05.LAB00059"/>" class="typeahead form-control" />
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle count-info" id="dropdownBokMrk" data-toggle="dropdown" href="#">
                    <i class="savanaicon-star"></i>  <span class="label bgColor_red" id="bokMrkCount">0</span>
                </a>
                <ul id="dropdown-favorite" class="dropdown-menu dropdown-favorite">
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                    <i class="savanaicon-bell"></i>  <span class="label bgColor_black" id="noticeListCntForNaviTop">0</span>
                </a>
                <ul class="dropdown-menu dropdown-alerts" id="noticeListForNaviTop"></ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                    <i class="savanaicon-user"></i>
                </a>
                <ul class="dropdown-menu dropdown-myinfo" style="width:210px;">
                    <li class="userInfoBox">
                         <div class="userInfo">
                             <span id="userInfoOrgNm" class="">${session_user.orgNm}</span>
                             <span id="userInfoUserNm" class="">${session_user.userNm}</span>
                         </div>
                     </li>
                    <li><a href="javascript:void(0);" id="pswdChgPopup"><spring:message code="LAB.M13.LAB00050"/><i class="savanaicon-arrow_line_right"></i></a></li><!-- 패스워드변경 -->
                </ul>
            </li>
            <li class="logout">
                <a href="javascript:logout();">
                    <i class="savanaicon-logout"></i>
                </a>
            </li>
        </ul>
    </div>
</nav>