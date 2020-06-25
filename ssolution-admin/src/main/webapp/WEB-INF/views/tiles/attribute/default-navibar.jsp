<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>

<script type="text/javascript">
function goMainPage(url){
	var mainUrl = url + '?noSelectMenu=Y';
	goMenuPage(mainUrl);
}
$(document).ready(function(){
    var maskSwitch = '${maskSwitch}';
    if(maskSwitch == "true"){
    	$("#maskSwitchCheck").iCheck("uncheck");
    	
    }else{
    	$("#maskSwitchCheck").iCheck("check");
    	
    }
    //권한 체크박스 이벤트
    $("#maskSwitchCheck").on("click", function(e) {
    	  var checkStat = $('#maskSwitchCheck').prop("checked");
         //권한 체크박스 이벤트
	      var param = new Object();
          var messsageConfirm = checkStat ? '<spring:message code="MSG.M05.MSG00037"/>' : '<spring:message code="MSG.M05.MSG00035"/>'; 
          var messsageComplete = checkStat ? '<spring:message code="MSG.M05.MSG00038"/>' : '<spring:message code="MSG.M05.MSG00036"/>';
          
	      param.maskYn = $('#maskSwitchCheck').prop("checked") ? "Y" : "N";
	      swalConfirmWith(messsageConfirm, "warning", function() {
	          $.ajax({
	              url: "/system/main/updateMaskAction.json",
	              type: 'POST',
	              data: param,
	              dataType: 'json',
	              success: function(data){
	                  swal(messsageComplete, "", "success");
	              }
	          });
	      }, function(){
	    	  checkStat ? $("#maskSwitchCheck").iCheck("uncheck") : $("#maskSwitchCheck").iCheck("check"); 
	    	  
	      });
	});
});
</script>
<nav class="navbar-default navbar-static-side savana-snb" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="profile-element"> 
                    <a href="javascript:goMainPage('${session_user.dashboardUrl}');">                       
                    	<h3>SAVANA MIS</h3>
                    </a>
                </div>
				<div class="maskingSwitch">
					<div class="onoffswitch">
				        <input type="checkbox" checked class="onoffswitch-checkbox" id="maskSwitchCheck">
				        <label class="onoffswitch-label" for="maskSwitchCheck">
				        	<span class="onoffswitch-inner"></span>
				            <span class="onoffswitch-switch"></span>
				    	</label>
				    </div>
				</div>                
            </li>
            <customTag:menu menuId="${selectedMenu.menuId}" selectMenuId="${selectedMenu.selectMenuId}" selectMenuNm="${selectedMenu.selectMenuNm}"	topMenuId='${selectedMenu.topMenuId}' topMenuNm='${selectedMenu.topMenuNm}'/>
        </ul>
    </div>
</nav>