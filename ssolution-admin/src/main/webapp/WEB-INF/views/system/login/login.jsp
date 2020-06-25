<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>


<script type="text/javascript">

    $(document).ready(function() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

        init();

        $('#loginUserId').on("keyup", function(){
            $('#loginUserId').val($(this).val().toUpperCase());
        });

        $('#loginUserId').keypress(function(event) {
            $('#loginUserId').val($(this).val().toUpperCase());
            if(event.keyCode === 13){
                login();
            }
        });

        $('#loginUserPw').keypress(function(event) {
            if(event.keyCode === 13){
                login();
            }

        });


        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName === 'Netscape' && agent.indexOf('trident') !== -1) || (agent.indexOf("msie") !== -1)) {
            // ie일 경우
            $("#loginUserPw").attr('type','password');
            $("#loginUserId").focus();
        }else{
            // ie가 아닐 경우
            $("#loginUserId").focus();
        }
        
        //로그인 width제어를 위해 20190327 추가
        $('body').addClass('loginBody');
    });



    /*
     * 초기화
     */
    function init(){
        $('#loginUserId').val('').focus();
        $('#loginUserPw').val('');
    }

	function goMain(data){
		setFastMenu(data.FAST_MENU);
		setBokMrkMenu(data.BOK_MRK_MENU);
		goMenuPage(data.DASHBOARD_URL + '?isLogin=Y');
	}

    function login(){
        if ($("body").data("loginAction")) {
            // "로그인 중복 호출 방지";
            return;
        }
        if($("#loginUserId").val() == "") {
            swal("<spring:message code="MSG.M08.MSG00004"/>");
            $("#loginUserId").focus();
            return;
        }

        if($("#loginUserPw").val() == "") {
            swal("<spring:message code="MSG.M06.MSG00039"/>");
            $("#loginUserPw").focus();
            return;
        }

        var param = new Object();
        param.userId = $("#loginUserId").val().toUpperCase();
        param.pswd = $("#loginUserPw").val();
        param.useLanguage =  'KR-ko';

        $.ajax({
            url:  '/system/login/loginAction',
            type: 'POST',
            data: param,
            beforeSend : function(xhr) {
                $("body").data("loginAction", true);
            },
            success: function(data){
                if(data.RESULT == "GO_MAIN") {
                    goMain(data);
                } else if(data.RESULT === "GO_MAIN_PASSWORD_NOTIFY") {
                    swal({
                        title : data.MESSAGE,
                        type : "warning",
                        showCancelButton : false,
                        closeOnConfirm : false
                    }, function(e) {
                        if (e) {// true 일 경우
                            goMain(data);
                        }
                    });
                } else if(data.RESULT === "PASSWORD_EXPIRED") {
					swal(data.MESSAGE);
				} else if(data.RESULT === "ERROR_INPUT_NULL") {
                    swal('<spring:message code="MSG.M08.MSG00002"/>');
                } else if(data.RESULT === "LOGIN_FAIL") {
                    swal('<spring:message code="MSG.M08.MSG00001"/>');
                } else if(data.RESULT === "FAIL_PASS_IP_BANDWIDTH") {
                    swal('<spring:message code="MSG.M09.MSG00018"/>');
                } else if(data.RESULT === "OVER_LOGIN_FAIL_COUNT") {
                    swal('<spring:message code="MSG.M04.MSG00003"/>');
                } else if(data.RESULT === "LOCK_ACCOUNT") {
                    swal('<spring:message code="MSG.M04.MSG00002"/>');
                } else if(data.RESULT === "LOGIN_FAIL_PASSWORD_ENCODE"){
                    swal('<spring:message code="MSG.M13.MSG00074"/>');
                } else if(data.RESULT === "LOGIN_ERROR") {
                    swal('<spring:message code="MSG.M04.MSG00008"/>');
                } else if(data.RESULT === "CHECK_ACCOUNT_INFO") {
                    swal('<spring:message code="MSG.M07.MSG00340"/>');
                }
            },
            error: function(e){
            },
            complete: function() {
                $("body").data("loginAction", false);
            }
        });
    }
   
</script>

<style type="text/css">
    input#loginUserPw{-webkit-text-security:disc;}
</style>

<form name="frmMenuHandle">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="savana-login">

	<div class="loginBg">
		<div class="bgLeft"></div>
		<div class="bgRIght"></div>	
		
		<div class="characterWrap animated">
			<span class="eyes">
				<i class="leftCont"></i>
				<i class="rightCont" ></i>
			</span>
			<span class="hands">
				<i class="leftCont"></i>
				<i class="rightCont"></i>
			</span>
		</div>
	</div>
	
	<div class="loginLeftCont animated">
		<h3 class="logo"><span class=hidden>S-solution Logo</span></h3>
		<div class="brandTitle">
			<div class="bName">Hanwha EST</div>
			<div class="sName">ADMINISTRATOR</div>
		</div>
		<div class="copyright">Copyright 한화 EST CO,LTD All rights reserved</div>
	</div>

    <div class="loginBox animated">
        <!--  -->
        <h3 class="loginText"><span class="text">LOGIN</span></h3>
        
        <!--  기존 일반 로그인 -->
        <div id="loginForm" class="m-t-10 login-form">

            <div class="input-group m-b-10">
                <input id="loginUserId" type="text" class="form-control" autocomplete="" placeholder="사용자ID" value="" />
                <span class="input-group-addon savanaicon-user"></span>
            </div>
            <div class="input-group m-b-10">
                <input id="loginUserPw" type="text" class="form-control" autocomplete="off" placeholder="Password"  value=""  />
                <span class="input-group-addon savanaicon-key_lock"></span>
            </div>

            <button type="button" class="btn loginBtn" onclick="javascript:login();" >LOGIN</button>
        </div>
    </div>


</div>

