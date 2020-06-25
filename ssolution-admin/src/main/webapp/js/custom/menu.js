/**
 * url을 이용한 페이지 이동
 * 
 * @param url
 */
function goMenuPage(url) {
	if(url==null || url=='')return;
	var f = makeform(url);
	f.submit();
}

/**
 * top메뉴 클릭 이동
 * 
 * @param menuNo 레벨 2 메뉴번호
 * @param url 이동 URL
 */
function goTopMenu(menuId, url, selectMenuId, selectMenuNm, topMenuId, topMenuNm) {
	
	var f = makeform(url);
	f.appendChild(AddData('menuId', menuId));
	f.appendChild(AddData('selectMenuId', selectMenuId));
	f.appendChild(AddData('selectMenuNm', selectMenuNm));
	f.appendChild(AddData('topMenuId', topMenuId));
	f.appendChild(AddData('topMenuNm', topMenuNm));
	f.target = "";
	f.submit();
}

function goTopMenuNewTab(menuId, url, selectMenuId, selectMenuNm, topMenuId, topMenuNm) {
	
	var f = makeform(url);
	
	f.appendChild(AddData('menuId', menuId));
	f.appendChild(AddData('selectMenuId', selectMenuId));
	f.appendChild(AddData('selectMenuNm', selectMenuNm));
	f.appendChild(AddData('topMenuId', topMenuId));
	f.appendChild(AddData('topMenuNm', topMenuNm));
	f.target = "_blank";
	f.submit();
}

function goTopMenuNewTabWithParam(menuId, url, selectMenuId, selectMenuNm, topMenuId, topMenuNm, param) {
	
	var f = makeform(url);
	
	f.appendChild(AddData('menuId', menuId));
	f.appendChild(AddData('selectMenuId', selectMenuId));
	f.appendChild(AddData('selectMenuNm', selectMenuNm));
	f.appendChild(AddData('topMenuId', topMenuId));
	f.appendChild(AddData('topMenuNm', topMenuNm));
	$.map(param, function(value, key) {
		f.appendChild(AddData(key, value));
	});
	f.target = "_blank";
	f.submit();
}

/**
 * left 메뉴 클릭 이동
 * 
 * @param url 이동 url
 * @param menuNo 메뉴번호
 */

function goLeftMenuPage(menuId, url, selectMenuId, selectMenuNm, topMenuId, topMenuNm) {
	if(url==null || url=='')return;
	
	var f = makeform(url);
	f.appendChild(AddData('menuId', menuId));
	f.appendChild(AddData('selectMenuId', selectMenuId));
	f.appendChild(AddData('selectMenuNm', selectMenuNm));
	f.appendChild(AddData('topMenuId', topMenuId));
	f.appendChild(AddData('topMenuNm', topMenuNm));
	f.target = "";
	f.submit();
}


/**
 * left 메뉴 클릭 이동
 * 
 * @param url 이동 url
 * @param menuNo 메뉴번호
 */
function goLeftMenuPageNewTab(menuId, url, selectMenuId, selectMenuNm, topMenuId, topMenuNm) {
	if(url==null || url=='')return;
	
	var f = makeform(url);
	f.appendChild(AddData('menuId', menuId));
	f.appendChild(AddData('selectMenuId', selectMenuId));
	f.appendChild(AddData('selectMenuNm', selectMenuNm));
	f.appendChild(AddData('topMenuId', topMenuId));
	f.appendChild(AddData('topMenuNm', topMenuNm));
	f.target = "_blank";
	f.submit();
}

/**
 * menu 이동 form을 참조하여 return
 * 
 * @param ActionURL 이동 url
 * @returns document.frmMenuHandle
 */
function makeform(ActionURL) {
	var f = document.frmMenuHandle;

	var csrf ;
	while (f.hasChildNodes()) {
		if(f.childElementCount == 0) break;

		var name = f.firstElementChild.name;
		if(name.indexOf("csrf") >= 0){
			csrf = f.firstElementChild
		}
	    f.removeChild(f.firstChild);
	}

	f.appendChild(csrf);
	
	f.action=ActionURL;
	f.method="post";
	f.target="";
	return f;
}

function makeformDownload(ActionURL, method) {
	var f = document.frmMenuHandle;

	var csrf ;
	while (f.hasChildNodes()) {
		if(f.childElementCount == 0) break;

		var name = f.firstElementChild.name;
		if(name.indexOf("csrf") >= 0){
			csrf = f.firstElementChild
		}
		f.removeChild(f.firstChild);
	}

	f.appendChild(csrf);

	f.action=ActionURL;
	f.method=method;
	f.target="";
	return f;
}

/**
 * input 태그 생성하여 return
 * 
 * @param name name 
 * @param value 변수값
 * @returns var i = document.createElement("input")
 */
function AddData(name, value) {
	var i = document.createElement("input");
	i.setAttribute("type", "hidden");
	i.setAttribute("name", name);
	i.setAttribute("value", value);
	return i;
}


