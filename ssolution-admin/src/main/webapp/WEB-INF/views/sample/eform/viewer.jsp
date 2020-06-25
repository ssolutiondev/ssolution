<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="OZViewer" style="width:98%;height:98%"></div>

<script>
 
	function SetOZParamters_OZViewer(){
		
		var oz;
		oz = document.getElementById("OZViewer");
		
		oz.sendToActionScript("viewer.childcount","8");
		oz.sendToActionScript("viewer.showtree","false");
		oz.sendToActionScript("viewer.usetoolbar","false");
		//멀티 보고서를 하나의 보고서처럼 사용할지 여부를 설정
		oz.sendToActionScript("global.concatpage","true");
		
		oz.sendToActionScript("connection.servlet","http://127.0.0.1:9090/eform80/server");
		//	oz.sendToActionScript("connection.reportname","eForm/01.프로젝트신청서.ozr");  //C유형_기능확인.ozr
		oz.sendToActionScript("connection.reportname","eForm/C유형_기능확인.ozr");  //C유형_기능확인.ozr
		//편집 모드 또는 주석 모드 아이콘 표시 여부를 설정
		oz.sendToActionScript("comment.all","true");
		//PDF 파일로 저장할 때 주석을 포함하여 저장할지 여부를 설정
		oz.sendToActionScript("pdf.savecomment", "true");
		
		oz.sendToActionScript("child1.connection.servlet", "http://127.0.0.1:9090/eform80/server");
		oz.sendToActionScript("child1.connection.reportname", "eForm/모바일화면점검.ozr");
		oz.sendToActionScript("child1.eform.signpad_type", "dialog");    //그룹 서명
		oz.sendToActionScript("child1.comment.all", "true");
		oz.sendToActionScript("child1.pdf.savecomment", "true");
		
		oz.sendToActionScript("child2.connection.servlet", "http://127.0.0.1:9090/eform80/server");
		oz.sendToActionScript("child2.connection.reportname", "eForm/03.주문서.ozr");
		oz.sendToActionScript("child2.comment.all", "true");
		oz.sendToActionScript("child2.pdf.savecomment", "true");
		
		oz.sendToActionScript("child3.connection.servlet", "http://127.0.0.1:9090/eform80/server");
		oz.sendToActionScript("child3.connection.reportname", "eForm/04.견적서.ozr");
		oz.sendToActionScript("child3.viewer.external_functions_path", "ozp://eForm/04.견적서.js");
		oz.sendToActionScript("child3.connection.pcount", "1");
		oz.sendToActionScript("child3.connection.args1", "rowCount=10");
		oz.sendToActionScript("child3.connection.inputjson", "{'CRN':'111-22-33333', 'company':'(주)인테리어', 'name':'홍길동', 'address':'서울 강남구 논현동', 'state_business':'', 'item':'', 'phone':''}");
		oz.sendToActionScript("child3.comment.all", "true");
		oz.sendToActionScript("child3.pdf.savecomment", "true");
		
		oz.sendToActionScript("child4.connection.servlet", "http://127.0.0.1:9090/eform80/server");
		oz.sendToActionScript("child4.connection.reportname", "eForm/05.출장보고서.ozr");
		oz.sendToActionScript("child4.connection.pcount", "5");
		oz.sendToActionScript("child4.connection.args1", "signatureCount=4");
		oz.sendToActionScript("child4.connection.args2", "signaturePosition담당,부서장,임원,사장,,");
		oz.sendToActionScript("child4.connection.args3", "enablename_sign=true");
		oz.sendToActionScript("child4.connection.args4", "enabledate_sign=true");
		oz.sendToActionScript("child4.connection.args5", "rowCount=1");
		oz.sendToActionScript("child4.ozsystem.user_name", "홍길동");     //시스템 필드 값 설정
		oz.sendToActionScript("child4.comment.all", "true");
		oz.sendToActionScript("child4.pdf.savecomment", "true");
		
		return true;
	}
	
	start_ozjs("OZViewer","/eform/ozhviewer80/");
	
</script>