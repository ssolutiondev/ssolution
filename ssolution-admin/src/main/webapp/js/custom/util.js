$(document)
		.ready(
				function() {
					// Top메뉴 active class명 추가
					topMenuActive();

					// 데이터테이블 에러 메세지 생략
					$.fn.dataTable.ext.errMode = 'none';

					// 데이터테이블 렌더링 향상 // 기본 option 셋팅
					$.extend(true, $.fn.dataTable.defaults, {
						deferRender : true,
					})

					// sweetAlert 포커스 문제 해결
					$.fn.modal.Constructor.prototype.enforceFocus = function() {
					};

					// $('#modalPopup').on('shown.bs.modal', function (e) {
					// $.fn.dataTable.tables( {visible: true, api: true}
					// ).columns.adjust();
					// });

					// Bootstrap tabs 이벤트 // 20180219 datatables redraw에 따른 속도
					// 저하 편법 // 상담관리 부분 때문
					$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
						var dtdraw = $(this).data('dtdraw');
						if (dtdraw == 'off') {
							return;
						} else {
							$.fn.dataTable.tables({
								visible : true,
								api : true
							}).columns.adjust();
						}
						textareaPlaceholder('개인 정보 입력 금지.');
					});

					// Modal Popup 오픈이벤트
					$('#modalPopup').on(
							'shown.bs.modal',
							function() {
								initShownBsModal();
								datepickerUpdate();
								$('.input-group.clockpicker').clockpicker(
										'remove').clockpicker();
								nokey();
							});

					// Modal Popup 종료 이벤트
					$('#modalPopup').on(
							'hidden.bs.modal',
							function() {
								$("#modalPopup").empty();
								$("#modalPopup").off('shown.bs.modal');
								$("#modalPopup").on(
										'shown.bs.modal',
										function() {
											initShownBsModal();
											$('.input-group.clockpicker')
													.clockpicker('remove')
													.clockpicker();
										});
								$.fn.dataTable.tables({
									visible : true,
									api : true
								}).columns.adjust();
							});

					// Modal Popup2 오픈이벤트 // 레이어에서 다시 레이어를 띄울때
					$('#modalPopup2').on(
							'shown.bs.modal',
							function() {
								initShownBsModal();
								datepickerUpdate();
								$('.input-group.clockpicker').clockpicker(
										'remove').clockpicker();
								nokey();
							});

					// Modal Popup2 종료 이벤트 //레이어에서 띄워진 레이어를 닫을때
					$('#modalPopup2').on(
							'hidden.bs.modal',
							function() {
								$("#modalPopup2").empty();
								$("#modalPopup2").off('shown.bs.modal');
								$("#modalPopup2").on(
										'shown.bs.modal',
										function() {
											initShownBsModal();
											$('.input-group.clockpicker')
													.clockpicker('remove')
													.clockpicker();
										});
								$("body").addClass('modal-open');
								$.fn.dataTable.tables({
									visible : true,
									api : true
								}).columns.adjust();
							});

					// modal의 width가 window의 width보다 클때 모달에 강제로 추가한 width를 auto로
					// 변경하는 스크립트
					$(window).resize(function() {
						popupScroll();// 레이어팝업 스크롤 생기게 하는 스크립트
					});

					// textarea Placeholder //20171124
					textareaPlaceholder('개인 정보 입력 금지.');

					$('.i-checks').iCheck({
						checkboxClass : 'icheckbox_square-green',
						radioClass : 'iradio_square-green'
					});

					$(document).ajaxSend(function(e, xhr, options) {
						var token = $("meta[name='_csrf']").attr("content");
						var header = $("meta[name='_csrf_header']").attr("content");
						xhr.setRequestHeader(header, token);
					});

					// AJAX 호출 시
					$(document).ajaxStart(function(e) {
						loading('start');
					});

					// AJAX 호출 중지
					$(document).ajaxStop(function(e) {
						loading('stop');
						// AJAX 호출후 페이지에 scroll이 없다가 생기는 경우의 datatables column
						// 오류 부분 수정
						$.fn.dataTable.tables({
							visible : true,
							api : true
						}).columns.adjust();

						// 현재 너무 많은 부분에서 datepicker update가 되어있지 않아 daterpicker
						// update를 강제실행
						datepickerUpdate();
					});

					$(document)
							.ajaxError(
									function(event, jqxhr, settings,
											thrownError) {

										if (jqxhr.status == '401') {
											swal(
												{
													title : "세션 종료되어 로그아웃 되었습니다.",
													text : "로그인 페이지로 이동합니다.",
													type : "error",
													showCancelButton : false,
													confirmButtonClass : "btn-danger",
													confirmButtonText : "",
													closeOnConfirm : false
												},
												function() {
													location.href = "/system/login/login";
												});
										} else{
											if(jqxhr
												.hasOwnProperty('responseJSON')){
												if (jqxhr.responseJSON.exceptionMessage != null && jqxhr.responseJSON.exceptionMessage != '') {
													swal(jqxhr.responseJSON.exceptionMessage, jqxhr.responseJSON.status + " : " + jqxhr.responseJSON.statusMessage , "error");
												} else if(jqxhr.responseJSON.status != null && jqxhr.responseJSON.status != ''){
													swal("서버 에러가 발생했습니다.", jqxhr.responseJSON.status + " : " + jqxhr.responseJSON.statusMessage , "error");
												}
											}else{
												swal("서버 에러가 발생했습니다. 관리자에게 문의해 주세요.", "", "error");
											}
										}
									});

					// "%" key입력 방지
					nokey();
				});

// "%" key입력 방지
var nokey = function() {
	$('input[type=text]').on('keydown', function(event) {
		var id = $(this).attr('id');
		if (id == 'loginUserPw' || id == 'loginOtpPw') {
			return;
		} else {
			if ((event.keyCode == 53) && (event.shiftKey == true)) {
				// alert('111');
				event.keyCode = 0;
				return false;
			}
		}

	});
}

// 초기 datatables가width를 잡지 못할때 // 상담관리 페이지에서만 사용하세요 ///20180219
var datatablesWidth = function(element) {
	this.$element = $(element);
	var id = this.$element.attr("href");
	$(id).find('.dataTables_scrollHeadInner').each(function(index) {
		var dtMinwidth = $(this).css('padding');
		var dtWidth = $(this).width();
		if (dtMinwidth = '0') {
			$(this).css('width', 'auto');
		} else {
			$(this).css('width', dtWidth);
		}
	});
}

// 20171124 textarea placeholder 셋팅
var textareaPlaceholder = function(text) {
	var text = text;
	$('textarea').each(function() {
		var is = $(this).is('[placeholder]');

		if (is == false) {
			$(this).attr('placeholder', text);
		} else if (is == true) {
			return;
		}

		/*
		 * 20180102 수정 var disabled = $(this).prop("disabled"); if (disabled ==
		 * false){ if (is == false){ $(this).attr('placeholder', text); } else
		 * if (is == true){ return; } } else if (disabled == true){ return; }
		 */
	});
};

var initICheck = function() {
	$('.i-checks').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
};

var initPopupICheck = function() {
	$('.modal-dialog .i-checks').iCheck({
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green'
	});
};
// loading 관련 추가 ('start')('stop')로 활성제어
var loading = function(state) {
	var s = state;
	var loading = '<div class="sk-spinner sk-spinner-wave"><div class="sk-rect1"></div><div class="sk-rect2"></div><div class="sk-rect3"></div><div class="sk-rect4"></div><div class="sk-rect5"></div></div>';

	if (s == 'start') {
		$('.loadingBox').append(loading).css('display', 'block');
	} else if (s == 'stop') {
		$('.loadingBox > *').remove();
		$('.loadingBox').css('display', 'none');
	}
};

// dataTables tooltip 관련
var dataTablesTooltip = function(text, e) {
	var stext = text;
	$("#tooltip").text(stext).animate({
		left : e.pageX + 10,
		top : e.pageY - 20
	}, 10);
	if (!$("#tooltip").is(':visible')) {
		$("#tooltip").show();
	}
};
var dataTablesTooltipHide = function() {
	$("#tooltip").hide();
};

// dataTable에서 원하는 td에 inline Edit를 할수 있도록 하는 스크립트 //dataTableInlineEdite('대상
// 테이블 id', dataTable를 그리는 변수);
var dataTableInlineEdite = function(tid, tTable, callback) {
	var id = tid;
	var table = tTable;

	$('#' + id + ' tbody').on(
			'dblclick',
			'td.inlinePopEdit',
			function() {
				var $this = $(this);
				var cellData = table.cell(this).data();
				var thisWidth = $this.outerWidth();
				var obj = $this.offset();
				console.log(cellData);

				var editorDiv = '<div id="DT_editorDiv" class="DT_editorDiv">'
						+ '    	<input type="text" class="editorInput" />'
						+ '</div>' + '<div class="DT_editorDivBg"></div>';

				$this.addClass('editIngNow');
				$('body').append(editorDiv);

				var editorDiv_h = $('#DT_editorDiv').outerHeight();
				var editorDiv_W = thisWidth;
				$('#DT_editorDiv').css({
					'top' : obj.top - editorDiv_h,
					'left' : obj.left,
					'width' : thisWidth
				});
				$('#DT_editorDiv>input.editorInput').val(cellData).focus();

			});

	$('body').on('keydown', '#DT_editorDiv > input.editorInput', function(key) {
		if (key.keyCode == 13) {// 키가 13이면 실행 (엔터는 13)
			var $this = $(this);
			var thisVal = $this.val();
			var $thisTd = $('#' + id + ' tbody td.editIngNow');

			var cell = table.cell($('#' + id + ' tbody td.editIngNow'));

			if (cell.data() != thisVal) {
				cell.data(thisVal);
				callback(table, cell, thisVal);
			}

			$thisTd.removeClass('editIngNow');

			$('#DT_editorDiv').next('.DT_editorDivBg').remove();
			$('#DT_editorDiv').remove();

			$.fn.dataTable.tables({
				visible : true,
				api : true
			}).columns.adjust();

		}
	});

	$('body').on('click', '.DT_editorDivBg', function(key) {
		var cellData = $('#DT_editorDiv > input.editorInput').val();
		var $thisTd = $('#' + id + ' tbody td.editIngNow');

		var cell = table.cell($('#' + id + ' tbody td.editIngNow'));

		if (cell.data() != cellData) {
			cell.data(cellData);
			callback(table, cell, thisVal);
		}

		$thisTd.removeClass('editIngNow');
		$('#DT_editorDiv').next('.DT_editorDivBg').remove();
		$('#DT_editorDiv').remove();

		$.fn.dataTable.tables({
			visible : true,
			api : true
		}).columns.adjust();
	});
}

// 아이디 값으로 비활성화(id)
var domDisabled = function(id) {
	$("#" + id).attr("disabled", "disabled");
};

// 아이디 값으로 비활성화(ids)
var domDisableds = function(ids) {
	$.each(ids, function(index, id) {
		domDisabled(id);
	});
};

// 아이디 값으로 활성화(id)
var domEnabled = function(id) {
	$("#" + id).removeAttr("disabled");
};

// 아이디 값으로 활성화(ids)
var domEnableds = function(ids) {
	$.each(ids, function(index, id) {
		domEnabled(id);
	});
};

/*
 * input value 초기화(id)
 */
var valueClear = function(id) {
	$("#" + id).val("");
};

/*
 * input value 초기화(ids)
 */
var valueClears = function(ids) {
	$.each(ids, function(index, id) {
		valueClear(id);
	});
};

// 아이디 값으로 dom show
var domShow = function(id) {
	$("#" + id).show();
};

// 아이디 값으로 dom show(ids)
var domShows = function(ids) {
	$.each(ids, function(index, id) {
		domShow(id);
	});
};

// 아이디 값으로 dom hide
var domHide = function(id) {
	$("#" + id).hide();
};

// 아이디 값으로 dom hide(ids)
var domHides = function(ids) {
	$.each(ids, function(index, id) {
		domHide(id);
	});
};

var fnClearValue = function(divId, ab) {
	var $contents = $("#" + divId + " :input");
	var node, id;

	for (var i = 0; i < $contents.length; i++) {
		node = $contents.eq(i);
		id = node.attr('id');

		if (id == "" || id == "undefined")
			continue;

		if (node.prop('type') == "text" || node.prop('type') == "textarea"
				|| node.prop('type') == "email") {
			node.val("");
		} else if (node.prop('type').indexOf("select") > -1) {
			$("#" + id + " option:eq(0)").prop("selected", true);
		} else if (node.prop('type') == "radio") {// radio

		} else if (node.prop('type') == "checkbox") {
			$("#" + id).iCheck('uncheck');
		}

	}

};

var fnSetAbility = function(divId, ab) {
	var $contents = $("#" + divId + " :input");
	var node, id;

	for (var i = 0; i < $contents.length; i++) {
		node = $contents.eq(i);
		id = node.attr('id');

		if (id == "" || id == "undefined")
			continue;
		if (ab) {
			$("#" + id).removeAttr("disabled");
		} else {
			$("#" + id).attr("disabled", "disabled");
		}

		if ($("#" + id).parent().hasClass("input-group date")
				|| $("#" + id).parent().hasClass("input-group clockpicker")) {

			$("#" + id).parent().css("pointer-events", ab ? "auto" : "none");

		}
	}
};

/*
 * 공백체크
 */
var isEmpty = function(value) {
	if (typeof value == "number") {
		// 숫자의 경우 분리
		if (value == null || value == undefined) {
			return true;
		} else {
			return false;
		}
	} else {
		if (value == ""
				|| value == null
				|| value == undefined
				|| (value != null && typeof value == "object" && !Object
						.keys(value).length)) {
			return true;
		} else {
			return false;
		}
	}

};

/*
 * 서버페이징 파라미터 셋팅
 */
var getGridPageParam = function(obj) {

	var param = new Object();

	if (obj.order.length > 0) {
		param.sidx = obj.columns[obj.order[0].column].data;
		param.sort = obj.order[0].dir;
	}

	param.start = obj.start;
	// param.end = obj.start + obj.length;
	param.end = obj.length;

	return param;
};

/*
 * 서버페이징 파라미터 셋팅
 */
var getGridPageParamWithName = function(obj) {

	var param = new Object();

	if (obj.order.length > 0) {
		param.sidx = obj.columns[obj.order[0].column].name;
		param.sort = obj.order[0].dir;
	}

	param.start = obj.start;
	param.end = obj.length;

	return param;
};

/*
 * 공통 검색 팝업 (url, param)
 */
var commonPopup = function(url, param, width) {
	var eWidth = width; // 레이어 팝업의 width 강제설정

	if (width == null) {
		eWidth = '';
	} else {
		eWidth = width;
	}

	$.ajax({
		type : "post",
		url : url,
		data : param,
		async : true,
		success : function(data) {
			$("#modalPopup").html(data);
		},
		complete : function() {
			// $('#modalPopup').modal();
			$('#modalPopup').modal({
				backdrop : 'static' // 20180203 backdrop click close 기능 막기
			}).find('.modal-dialog').css({
				width : eWidth
			});
			$('#modalPopup').after($('.modal-backdrop'));
			popupScroll();// 레이어팝업 스크롤 생기게 하는 스크립트
		}
	});

};

/*
 * 공통 검색 팝업 (url, param)
 */
var commonPopup2 = function(url, param, width) {

	var eWidth = width; // 레이어 팝업의 width 강제설정

	if (width == null) {
		eWidth = '';
	} else {
		eWidth = width;
	}

	$.ajax({
		type : "post",
		url : url,
		data : param,
		async : true,
		success : function(data) {
			$("#modalPopup2").html(data);
		},
		complete : function() {
			// $('#modalPopup2').modal();
			$('#modalPopup2').modal().find('.modal-dialog').css({
				width : eWidth
			});
			// $('#modalPopup2').after($('.modal-backdrop'));
			$('.sweet-overlay, .sweet-alert, .clockpicker-popover').remove(); // swal,
			// clockpicker
			// 액션이후
			// 오류
			// 수정을
			// 위해
			// swal를
			// 제거
			$('#modalPopup2').attr('style', 'z-index: 2052 !important').next(
					'.modal-backdrop')
					.attr('style', 'z-index: 2051 !important');

			popupScroll();// 레이어팝업 스크롤 생기게 하는 스크립트
		}
	});

};

/*
 * 팝업 사이즈 비교하여 모달창에 스크롤 생기게 하는 스크립트
 */
var popupScroll = function() {
	var wW = $(window).width(), lW = $('.modal-dialog').outerWidth();
	if ($('.modal-dialog').length > 0) {
		if (wW < lW) {
			$('.modal-open .modal').css('overflow-x', 'auto');
		}
	}
};

/*
 * modalConfirm (타이틀, 이미지타입, 콜백메소드)
 */
var swalConfirm = function(title, type, callback) {
	swal({
		title : title,
		type : type,
		showCancelButton : true,
		closeOnConfirm : false
	}, function(e) {
		if (e) {// true 일 경우
			callback();
		}
	});
};

/*
 * modalConfirm (타이틀, 이미지타입, 콜백메소드) - 취소 Callback
 */
var swalConfirmWith = function(title, type, callback, callbackCancel) {
	swal({
		title : title,
		type : type,
		showCancelButton : true,
		closeOnConfirm : false
	}, function(e) {
		if (e) {// true 일 경우
			callback();
		} else {
			callbackCancel();
		}
	});
};

/* modalConfirm tab키 프레스 오류 관련 추가 */
var close = window.swal.close;
window.swal.close = function() {
	close();
	window.onkeydown = null;
};

/*
 * 자동완성 메뉴 검색 로컬 변수 세팅
 */
var setFastMenu = function(fastMenu) {
	localStorage.clear();
	localStorage.setItem('FAST_MENU', JSON.stringify(fastMenu));
};

/*
 * 자동완성 메뉴 데이터 조회
 */
var getFastMenu = function() {
	var fastMenu = localStorage.getItem('FAST_MENU');
	return fastMenu;
};

/*
 * 즐겨찾기 변수 세팅
 */
var setBokMrkMenu = function(bokMrkMenu) {
	localStorage.removeItem("BOK_MRK_MENU");
	localStorage.setItem('BOK_MRK_MENU', JSON.stringify(bokMrkMenu));
};

/*
 * 즐겨찾기 조회
 */
var getBokMrkMenu = function() {
	var bokMrkMenu = JSON.parse(localStorage.getItem('BOK_MRK_MENU'));
	return bokMrkMenu;
};

/*
 * 주민번호 유효성 검사
 */
var checkJumin = function(chkNum) {

	var strJumin = "" + chkNum;
	var checkBit = new Array(2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5);
	var num7 = strJumin.substr(6, 1);
	var num13 = strJumin.substr(12, 1);
	var total = 0;
	if (strJumin.length == 13) {
		for (i = 0; i < checkBit.length; i++) { // 주민번호 12자리를 키값을 곱하여 합산한다.
			total += strJumin.substr(i, 1) * checkBit[i];
		}
		// 외국인 구분 체크
		if (num7 == 0 || num7 == 9) { // 내국인 ( 1800년대 9: 남자, 0:여자)
			total = (11 - (total % 11)) % 10;
		} else if (num7 > 4) { // 외국인 ( 1900년대 5:남자 6:여자 2000년대 7:남자, 8:여자)
			total = (13 - (total % 11)) % 10;
		} else { // 내국인 ( 1900년대 1:남자 2:여자 2000년대 3:남자, 4:여자)
			total = (11 - (total % 11)) % 10;
		}

		if (total != num13) {
			return false;
		}
		return true;
	} else {
		return false;
	}

	/*
	 * var jumin = chkNum;
	 *
	 * //주민등록 번호 13자리를 검사한다. var fmt = /^\d{6}[1234]\d{6}$/; //포멧 설정 if
	 * (!fmt.test(jumin)) { return false; } // 생년월일 검사 var birthYear =
	 * (jumin.charAt(6) <= "2") ? "19" : "20"; birthYear += jumin.substr(0, 2);
	 * var birthMonth = jumin.substr(2, 2) - 1; var birthDate = jumin.substr(4,
	 * 2); var birth = new Date(birthYear, birthMonth, birthDate);
	 *
	 * if ( birth.getYear() % 100 != jumin.substr(0, 2) || birth.getMonth() !=
	 * birthMonth || birth.getDate() != birthDate) { return false; } // Check
	 * Sum 코드의 유효성 검사 var buf = new Array(13); for (var i = 0; i < 13; i++)
	 * buf[i] = parseInt(jumin.charAt(i));
	 *
	 * multipliers = [2,3,4,5,6,7,8,9,2,3,4,5]; for (var sum = 0, i = 0; i < 12;
	 * i++) sum += (buf[i] *= multipliers[i]);
	 *
	 * if ((11 - (sum % 11)) % 10 != buf[12]) { return false; }
	 *
	 * return true;
	 */
}

/*
 * 법인번호 유효성 체크
 */
var checkCorpNo = function(chkNum) {
	var corpNo = chkNum;
	if (corpNo == "" || corpNo == null || corpNo.length != 13) {
		return false;
	} else {
		return true;
	}
	// var fmt = /^\d{13}$/; //포멧 설정
	// if (!fmt.test(corpNo)) {
	// return false;
	// }
	//
	// var arr_regno = corpNo.split("");
	// var arr_wt = new Array(1,2,1,2,1,2,1,2,1,2,1,2);
	// var iSum_regno = 0;
	// var iCheck_digit = 0;
	// for (i = 0; i < 12; i++){
	// iSum_regno += eval(arr_regno[i]) * eval(arr_wt[i]);
	// }
	//
	// iCheck_digit = 10 - (iSum_regno % 10);
	// iCheck_digit = iCheck_digit % 10;
	//
	// if (iCheck_digit != arr_regno[12]){
	// return false;
	// }
	return true;
}

/**
 * Modal 팝업 초기화
 */
var initShownBsModal = function() {
	$.fn.dataTable.tables({
		visible : true,
		api : true
	}).columns.adjust();
	// datepicker();
	// monthspicker();
	// daterangepicker();
	// yearspicker();
	initPopupICheck();
	textareaPlaceholder('개인 정보 입력 금지.');
}

/*
 * 사업자번호 유효성 체크
 */
var checkBizNo = function(chkNum) {

	if (isEmpty(chkNum)) {
		return false;
	}
	var bizNo = chkNum;
	if ((bizNo = (bizNo + '').match(/\d{1}/g)).length != 10) {
		return false;
	}

	return true;

	// if(isEmpty(chkNum)){
	// return false;
	// }
	// var bizNo = chkNum;
	// if ((bizNo = (bizNo+'').match(/\d{1}/g)).length != 10) { return false; }
	// // 합 / 체크키
	// var sum = 0, key = [1, 3, 7, 1, 3, 7, 1, 3, 5];
	//
	// // 0 ~ 8 까지 9개의 숫자를 체크키와 곱하여 합에더합니다.
	// for (var i = 0 ; i < 9 ; i++) { sum += (key[i] * Number(bizNo[i])); }
	//
	// // 각 8번배열의 값을 곱한 후 10으로 나누고 내림하여 기존 합에 더합니다.
	// // 다시 10의 나머지를 구한후 그 값을 10에서 빼면 이것이 검증번호 이며 기존 검증번호와 비교하면됩니다.
	// return (10 - ((sum + Math.floor(key[8] * Number(bizNo[8]) / 10)) % 10))
	// == Number(bizNo[9]);

	// bizID는 숫자만 10자리로 해서 문자열로 넘긴다.

	// if(isEmpty(chkNum)){
	// return false;
	// }
	// var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1);
	// var tmpBizID, i, chkSum=0, c2, remander;
	// bizID = bizID.replace(/-/gi,'');
	//
	// for (i=0; i<=7; i++) chkSum += checkID[i] * bizID.charAt(i);
	// c2 = "0" + (checkID[8] * bizID.charAt(8));
	// c2 = c2.substring(c2.length - 2, c2.length);
	// chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));
	// remander = (10 - (chkSum % 10)) % 10 ;
	//
	// if (Math.floor(bizID.charAt(9)) == remander) return true ; // OK!
	// return false;

	// retrun true;
}

/*
 * tab 버튼 추가 2017.08.11
 */
$(document)
		.ready(
				function() {

					if ($(".list").length > 0) { // list class가 있을경우만 실행

						var hidWidth;
						var scrollBarWidths = 40;

						var widthOfList = function() {
							var itemsWidth = 0;
							$('.list li').each(function() {
								var itemWidth = $(this).outerWidth();
								itemsWidth += itemWidth;
							});
							return itemsWidth;
						};

						var widthOfHidden = function() {
							return (($('.tab-wrap').outerWidth())
									- widthOfList() - getLeftPosi())
									- scrollBarWidths;
						};

						var getLeftPosi = function() {
							return $('.list').position().left;
						};

						var reAdjust = function() {
							if (($('.list').outerWidth()) < widthOfList()) {
								$('.scroller-right').show();
							} else {
								$('.scroller-right').hide();
							}

							if (getLeftPosi() < 0) {
								$('.scroller-left').show();
							} else {
								$('.item').animate({
									left : "-=" + getLeftPosi() + "px"
								}, 'slow');
								$('.scroller-left').hide();
							}
						}

						reAdjust();

						$(window).on('resize', function(e) {
							reAdjust();
						});

						$('.scroller-right').click(function() {

							$('.scroller-left').fadeIn('slow');
							$('.scroller-right').fadeOut('slow');

							$('.list').animate({
								left : "+=" + widthOfHidden() + "px"
							}, 'slow', function() {

							});
						});

						$('.scroller-left').click(function() {

							$('.scroller-right').fadeIn('slow');
							$('.scroller-left').fadeOut('slow');

							$('.list').animate({
								left : "-=" + getLeftPosi() + "px"
							}, 'slow', function() {

							});
						});

					}// .list 유무체크

				});

jQuery.download = function(url, data, method) {
    // url과 data를 입력받음
    if (url && data) {
        // data 는 string 또는 array/object 를 파라미터로 받는다.
        data = typeof data == 'string' ? data : decodeURIComponent(jQuery.param(data));

		var f = makeformDownload(url, method);

		// 파라미터를 form의 input으로 만든다.
		var inputs = '';
		jQuery.each(data.split('&'), function () {
			var pair = this.split('=');

			var x = document.createElement("INPUT");
			x.setAttribute("type", "hidden");
			x.setAttribute("name", pair[0]);
			x.setAttribute("value", pair[1]);
			f.appendChild(x);
		});
		f.submit();
	}
};

/**
 * 두 날짜 사이 일자 반환
 *
 * @param _date1
 *            시작일(YYYYMMDD)
 * @param _date2
 *            종료일(YYYYMMDD)
 * @returns 차이일
 */
function getDateDiff(_date1, _date2) {

	if (_date1 == null || _date1 == '')
		return '';
	if (_date2 == null || _date2 == '')
		return '';
	if (!/^(\d){8}$/.test(_date1))
		return "invalid date";
	if (!/^(\d){8}$/.test(_date2))
		return "invalid date";

	var yy1 = _date1.substr(0, 4), mm1 = _date1.substr(4, 2) - 1, dd1 = _date1
			.substr(6, 2);

	var diffDate_1 = new Date(yy1, mm1, dd1);

	var yy2 = _date2.substr(0, 4), mm2 = _date2.substr(4, 2) - 1, dd2 = _date2
			.substr(6, 2);

	var diffDate_2 = new Date(yy2, mm2, dd2);

	var diff = Math.abs(diffDate_2.getTime() - diffDate_1.getTime());
	diff = Math.ceil(diff / (1000 * 3600 * 24));
	diff = diff + 1;
	return diff;
}

// chartJs 기본 셋팅 관련부분 /////////////////////////////////
// chart 공통 부분
Chart.defaults.global.responsive = true; // 가변형
Chart.defaults.global.responsiveAnimationDuration = 300; // 가변형 animation
Chart.defaults.global.maintainAspectRatio = false; // 가로세로 비율 유지,

// 폰트
Chart.defaults.global.defaultFontColor = "#333";
Chart.defaults.global.defaultFontFamily = "Arial, Helvetica, sans-serif";
Chart.defaults.global.defaultFontSize = 12;

// 툴팁
Chart.defaults.global.tooltips.cornerRadius = 3;
Chart.defaults.global.tooltips.bodySpacing = 5;
Chart.defaults.global.tooltips.xPadding = 5;
Chart.defaults.global.tooltips.yPadding = 8;
Chart.defaults.global.tooltips.multiKeyBackground = "rgba(0,0,0,0.0)";

// animation
Chart.defaults.global.animation.duration = 100;
Chart.defaults.global.animation.easing = "easeOutBounce";

// bar chart용 //공통을 채울때
Chart.defaults.global.elements.rectangle.borderWidth = 1;
Chart.defaults.global.elements.rectangle.borderSkipped = 'bottom';

// line chart용 //공통을 채울때
Chart.defaults.global.elements.line.borderWidth = 1;

Chart.defaults.global.elements.point.radius = 1;
Chart.defaults.global.elements.point.hoverRadius = 5;
Chart.defaults.global.elements.point.hoverBorderWidth = 0;

// 로드 이후 datepicker셋팅후 datepicker을 일과 update하기위해
// 현재 너무 많은 부분에서 datepicker update가 되어있지 않아 pace.js의 옵션으리 이용해 로딩이후 초기 셋팅후
// daterpicker update를
Pace.on('done', function() {
	// alert('222');
	datepickerUpdate();
});

function datepickerUpdate() {
	datepicker();
	monthspicker();
	daterangepicker();
	yearspicker();

	// 사용여부는 장단점이 있음
	setDaterangepickerMinMax();

	$('.date.type1, .date.months, .date.years').datepicker('update');
	$('.input-daterange > input.form-control').datepicker('update');

}

// setDaterangepickerMinMax //Daterangepicker에서 자동으로 form to의 min max를 설정하는 스크립트
// from은 to 이후를 클릭하룻 없고 to는 from 이전을 클릭 할수 없게함
function setDaterangepickerMinMax() {

	$('.input-group.input-daterange').each(
			function(index, element) {

				var n = $(this).find('input.form-control').length;
				// alert(n);

				if (n == 2) {
					/* Find 'From' */
					$(this).find('input.form-control:eq(0)').addClass(
							"from" + index);

					/* Find 'To' */
					$(this).find('input.form-control:eq(1)').addClass(
							"to" + index);

					var startDate = $(".from" + index).datepicker("getDate");
					var FromEndDate = $(".to" + index).datepicker("getDate");

					$(".from" + index).datepicker('setEndDate', FromEndDate)
							.on(
									'changeDate',
									function(selected) {
										var startDate = $(this).datepicker(
												"getDate");
										$('.to' + index).datepicker(
												"setStartDate", startDate);
									});

					$('.to' + index).datepicker('setStartDate', startDate)
							.on(
									'changeDate',
									function(selected) {
										var FromEndDate = $(this).datepicker(
												"getDate");
										$('.from' + index).datepicker(
												'setEndDate', FromEndDate);
									});
				}
			});
}

// Top메뉴 active class명 추가
function topMenuActive() {
	var curLocation_1 = $(".savana-page-heading > .location ").find("li").eq(1)
			.text().replace(/\s/g, ""), curLocation_2 = $(
			".savana-page-heading > .location ").find("li").eq(2).text().replace(
			/\s/g, ""),

	gnb = $(".savana-gnbArea"), gnbMenu_1 = gnb.find(">li"), gnbMenu_2 = gnb
			.find(">li>ul>li");

	gnbMenu_1.each(function() {
		var _this = $(this), menuStr = _this.find(">a").text().replace(/\s/g,
				"");
		if (menuStr == curLocation_1) {
			_this.addClass("active");
		}
	});

	gnbMenu_2.each(function() {
		var _this = $(this), menuStr = _this.find(">span").eq(0).text()
				.replace(/\s/g, "");
		if (menuStr == curLocation_2) {
			_this.addClass("active");
		}
	});
}

// Object to Map
var fnOjbToMap = (function(obj) {
	var mp = new Map;
	Object.keys(obj).forEach(function(k) {
		mp.set(k, obj[k]);
	});
	return mp;
});

// Map to Object
var fnMapToObj = (function(aMap) {
	var obj = {};
	aMap.forEach(function(v, k) {
		obj[k] = v;
	});
	return obj;
});

// window popup
function PopupCenter(url, title, w, h) {
	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : screen.left;
	var dualScreenTop = window.screenTop != undefined ? window.screenTop : screen.top;

	width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
	var top = ((height / 2) - (h / 2)) + dualScreenTop;
	var newWindow = window.open(url, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

	// Puts focus on the newWindow
	if (window.focus) {
		newWindow.focus();
	}
}