
$(document).ready(function(){
    
    //text type byte check (maxByte = "10")
    $(document).on("keyup", "[maxByte]", function(e){
        var str = $(this).val();
        var len = $(this).attr('maxByte');
        var count = 0;
        
        for (var i=0; i<str.length; i++) {
            
            if(escape(str.charAt(i)).length >= 4)
                count += 2;
            else if(escape(str.charAt(i)) == "%A7")
                count += 2;
            else
                if(escape(str.charAt(i)) != "%0D")
                    count++;
            if (count > len) return $(this).val(str.substring(0,i));
        }
    });
    
    //전화번호 타입 변환
    $(document).on("keyup blur", ".telNumber", function(e){
        $(this).val(telNoAutoFormatter($(this).val()));
    });
    
    //숫자만 입력
    $(document).on("keypress", ".numberFormat", function(e){

        if (event.which && (event.which  > 47 && event.which  < 58 || event.which == 8)) {
        } else {
            event.preventDefault();
        }
    });
    $(document).on("keyup blur", ".numberFormat", function(e){
        var key;
        if(window.event)
            key = window.event.keyCode; //IE
        else
            key = e.which; //firefox
        var event;
        event = e || window.event;
        if ( key == 8 || key == 46 || key == 37 || key == 39 )
            return;
        else
            event.target.value = event.target.value.replace(/[^0-9]/g, "");

    });

    $(document).on("keyup blur", ".amountFormat", function(e){
        if($(this).val() == 0){
            $(this).val(0);
        }else{
            var value = $(this).val().replace(/(^0+)/, "").replace(/[^0-9]/g, "");
            $(this).val(value.replace(/\B(?=(\d{3})+(?!\d))/g, ","));
        }

    });
    
    $(document).on("keyup blur", ".numberEngFormat", function(e){
        $(this).val($(this).val().replace(/[^0-9a-zA-Z]/g, ""));
    });
    
    $(document).on("keyup blur", ".regNo", function(e){
        $(this).val(regNoFormatter($(this).val()));
    });
    
    $(document).on("keyup blur", ".bizRegNo", function(e){
        $(this).val(bizRegNoFormatter($(this).val()));
    });
    
    
    
    datepicker();
    monthspicker();
    daterangepicker();
    yearspicker();
    
    // clockPicker // 20170915 추가
    //$('.input-group.clockpicker').clockpicker({});
    clockpicker();
        
});

function clockpicker() {
    $('.input-group.clockpicker').each(function(index) {
        $(this).clockpicker({});
    });
}

//레이러 팝업후 데이터피커 적용을 위한 function화
function datepicker() {
    $('.input-group.date.type1').datepicker({
        language : lng,
        todayBtn: "linked",
        keyboardNavigation: false,
        calendarWeeks: false, //20170915 수정
        autoclose: true,
        format: "yyyy-mm-dd"
//	}).find('>input').attr('readonly','ture');
    }).find('>input').removeAttr('readonly').mask('0000-00-00'); //datePicker keyIn을 위해 jquery.mask이용
}

function monthspicker() {
    $('.input-group.date.months').datepicker({
        language : lng,
        todayBtn: "linked",
        keyboardNavigation: false,
        autoclose: true,
        format: "yyyy-mm",
        viewMode: "months",
        minViewMode: "months"
//	}).find('>input').attr('readonly','ture');
    }).find('>input').removeAttr('readonly').mask('0000-00'); //datePicker keyIn을 위해 jquery.mask이용
}

//20170926 years picker추가
function yearspicker() {
    $('.input-group.date.years').datepicker({
        language : lng,
        todayBtn: "linked",
        keyboardNavigation: false,
        autoclose: true,
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years"
//	}).find('>input').attr('readonly','ture');
    }).find('>input').removeAttr('readonly').mask('0000'); //datePicker keyIn을 위해 jquery.mask이용
}


//data-type를 이용한 viewMode 설정 //20170928
function daterangepicker() {
    $('.input-group.input-daterange').each(function(index) {
        
        var type = $(this).attr('data-type');
        if (type == 'months'){
            $(this).datepicker({
                language : lng,
                todayBtn: "linked",
                keyboardNavigation: false,
                autoclose: true,
                format: "yyyy-mm",
                viewMode: "months",
                minViewMode: "months",
                useCurrent: "false"
//			}).find('>input').attr('readonly','ture');
            }).find('>input').removeAttr('readonly').mask('0000-00'); //datePicker keyIn을 위해 jquery.mask이용
        } else if (type == 'years'){
            $(this).datepicker({
                language : lng,
                todayBtn: "linked",
                keyboardNavigation: false,
                autoclose: true,
                format: "yyyy",
                viewMode: "years",
                minViewMode: "years"
//			}).find('>input').attr('readonly','ture');
            }).find('>input').removeAttr('readonly').mask('0000');  //datePicker keyIn을 위해 jquery.mask이용
        } else {
            $(this).datepicker({
                language : lng,
                todayBtn: "linked",
                keyboardNavigation: false,
                autoclose: true,
                format: "yyyy-mm-dd"
//			}).find('>input').attr('readonly','ture');
            }).find('>input').removeAttr('readonly').mask('0000-00-00');  //datePicker keyIn을 위해 jquery.mask이용
        }
    });
}


Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
var dateTypeFormatterYYYYMMDDHH24MISS = function(cellValue) {
    var date = new Date(cellValue);

    if(cellValue == null || cellValue == '') return '';
    if (date == undefined || date == null) {
        return '';
    }
    if ( lng == 'ko'){
        return date.format("yyyy-MM-dd hh:mm:ss");
    }else if (lng = 'en'){
        return date.format("MM/dd/yyyy hh:mm:ss");
    }else{
        return date.format("MM/dd/yyyy hh:mm:ss");
    }
}

var dateTypeFormatterYYYYMMDD = function(cellValue) {
    var date = new Date(cellValue);

    if(cellValue == null || cellValue == '') return '';
    if (date == undefined || date == null) {
        return '';
    }
    if ( lng == 'ko'){
        return date.format("yyyy-MM-dd");
    }else if (lng = 'en'){
        return date.format("MM/dd/yyyy");
    }else{
        return date.format("MM/dd/yyyy");
    }
}

/*
 * Date Type을 받아 포멧팅된 String(YYYYMMDD)으로 변환
 */
function dateFormatterUsingDateYYYYMMDD(dateValue){
    if (dateValue == null) return '';
    if ( lng == 'ko'){
        return dateValue.format("yyyy-MM-dd");
    }else if (lng = 'en'){
        return dateValue.format("MM/dd/yyyy");
    }else{
        return dateValue.format("MM/dd/yyyy");
    }
}

function dateFormatterUsingDateYYYYMM(dateValue){
    if (dateValue == null) return '';
    if ( lng == 'ko'){
        return dateValue.format("yyyy-MM");
    }else if (lng = 'en'){
        return dateValue.format("MM/yyyy");
    }else{
        return dateValue.format("MM/yyyy");
    }
}

function dateFormatterUsingDateYYYY(dateValue){
    if (dateValue == null) return '';
        return dateValue.format("yyyy");
}

/*
 * Date Type을 받아 포멧팅된 String(YYYYMMDD)으로 변환
 */
function dateFormatterUsingDateYYYYMMDDHH24MISS(dateValue){
    if (dateValue == null) return '';

    if ( lng == 'ko'){
        return dateValue.format("yyyy-MM-dd hh:mm:ss");
    }else if (lng = 'en'){
        return dateValue.format("MM/dd/yyyy hh:mm:ss");
    }else{
        return dateValue.format("MM/dd/yyyy hh:mm:ss");
    }
}

/*
 *  포맷팅된 날짜 String을 YYYY형식으로 변경
 */
function dateFormatToStringYYYY(value) {
    if(value == null || value == '') return '';
    if(lng =='ko'){
        var yy = value.substr(0,4)
    }else if(lng ='en'){
        var yy = value.substr(3,4)
    }else{
        var yy = value.substr(3,4)
    }
    return yy;
}

/*
 *  포맷팅된 날짜 String을 YYYYMM형식으로 변경
 */
function dateFormatToStringYYYYMM(value) {
    if(value == null || value == '') return '';
    if(lng =='ko'){
        var yy = value.substr(0,4),
        mm = value.substr(5,2)
    }else if(lng ='en'){
        var yy = value.substr(3,4),
        mm = value.substr(0,2)
    }else{
        var yy = value.substr(3,4),
            mm = value.substr(0,2)
    }
    return yy + mm;
}

/*
 *  포멧팅된 날짜 String을 YYYYMMDD로 변경
 */
function dateFormatToStringYYYYMMDD(value) {
    if(value == null || value == '') return '';
    if(lng =='ko'){
        var yy = value.substr(0,4),
        mm = value.substr(5,2),
        dd = value.substr(8,2);
    }else if(lng ='en'){
        var yy = value.substr(6,4),
        mm = value.substr(0,2),
        dd = value.substr(3,2);
    }else{
        var yy = value.substr(6,4),
            mm = value.substr(0,2),
            dd = value.substr(3,2);
    }
    return yy + mm + dd;
}


/*
 *  포멧팅된 날짜 String을 YYYYMMDDHH24MISS로 변경
 */
function dateFormatToStringYYYMMDDHHMISS(value) {
    if(value == null || value == '') return '';
    if(lng =='ko'){
        var yy = value.substr(0,4),
        mm = value.substr(5,2),
        dd = value.substr(8,2),
        hh = value.substr(11,2),
        mi = value.substr(14,2),
        ss = value.substr(17,2);
    }else if(lng ='en'){
        var yy = value.substr(6,4),
        mm = value.substr(0,2),
        dd = value.substr(3,2),
        hh = value.substr(11,2),
        mi = value.substr(14,2),
        ss = value.substr(17,2);
    }else{
        var yy = value.substr(6,4),
            mm = value.substr(0,2),
            dd = value.substr(3,2),
            hh = value.substr(11,2),
            mi = value.substr(14,2),
            ss = value.substr(17,2);
    }
    return yy + mm + dd + hh + mi + ss;
}


/*
 * String(YYYYMMDDHH24MISS)을 포멧팅된 날짜 형식의 String으로 변환
 */
function stringToDateformatYYYYMMDDHH24MISS(value) {
    if(value == null || value == '') return '';
    if(!/^(\d){14}$/.test(value)) return value;

    var yy = value.substr(0,4),
        mm = value.substr(4,2) - 1,
        dd = value.substr(6,2),
        hh = value.substr(8,2),
        mi = value.substr(10,2),
        ss = value.substr(12,2);

    var date = new Date(yy,mm,dd,hh,mi,ss,0);

    if ( lng == 'ko'){
        return date.format("yyyy-MM-dd hh:mm:ss");
    }else if (lng = 'en'){
        return date.format("MM/dd/yyyy hh:mm:ss");
    }else{
        return date.format("MM/dd/yyyy hh:mm:ss");
    }
}

/*
 * String(YYYYMMDD)을 포멧팅된 날짜 형식의 String으로 변환
 */
function stringToDateformatYYYYMMDD(value) {
    if(value == null || value == '') return '';
    if(!/^(\d){8}$/.test(value)) return value;

    var yy = value.substr(0,4),
        mm = value.substr(4,2) - 1,
        dd = value.substr(6,2);

    var date = new Date(yy,mm,dd);


    if ( lng == 'ko'){
        return date.format("yyyy-MM-dd");
    }else if (lng = 'en'){
        return date.format("MM/dd/yyyy");
    }else{
        return date.format("MM/dd/yyyy");
    }
}

/*
 * String(YYYYMM)을 포멧팅된 날짜 형식의 String으로 변환
 */
function stringToDateformatYYYYMM(value) {
    if(value == null || value == '') return '';
    if(!/^(\d){6}$/.test(value)) return value;
    
    var yy = value.substr(0,4);
    var mm = value.substr(4,2) - 1;
    var date = new Date(yy,mm);
    
    if ( lng == 'ko'){
        return date.format("yyyy-MM");
    }else if (lng = 'en'){
        return date.format("MM/yyyy");
    }else{
        return date.format("MM/yyyy");
    }
}

var stringTypeFormatterYYYYMMDD = function(cellValue,options,rowObject) {
    if(cellValue == null || cellValue == '') return '';
    if(!/^(\d){8}$/.test(cellValue)) return cellValue;

    var yy = cellValue.substr(0,4),
        mm = cellValue.substr(4,2) - 1,
        dd = cellValue.substr(6,2);

    var date = new Date(yy,mm,dd);


    if ( lng == 'ko'){
        return date.format("yyyy-MM-dd");
    }else if (lng = 'en'){
        return date.format("MM/dd/yyyy");
    }else{
        return date.format("MM/dd/yyyy");
    }
}
/*
 *  전화번호 Formatting
 */
var telNoFormatter = function(cellValue) {

    if(cellValue == null || cellValue == ''){
        return '';
    }
    
    var formatNum = '';
    
    var regStr = /^(0[01][0126789\*]{1}|02|0[3-9\*]{1}[0-9\*]{1})?-?([0-9\*]{3,4})-?([0-9\*]{4}$)/;
    
    if(cellValue.length < 9){
        formatNum = cellValue.replace(regStr, '$2-$3');
    }else{
        formatNum = cellValue.replace(regStr, '$1-$2-$3');
    }
    
    if(formatNum == null || formatNum == ''){
        formatNum = cellValue;
    }
    return formatNum;
}


function getTableLngUrl(){
    if ( lng == 'ko'){
        return '/js/plugins/dataTables/i18n/KR_ko.json';
    }else if(lng == 'en'){
        return '/js/plugins/dataTables/i18n/KR_ko.json';
    }else{
        return '/js/plugins/dataTables/i18n/KR_ko.json';
    }
}

/*
 * 전화번호 자동 Formatter(Input keyup event)
 */
function telNoAutoFormatter(value){
    if (value == null || value.length == 0) return '';
    
    var str = value.replace(/[^0-9/*]/g, '');
    var tmp = '';

    if(str.length > 11){
        return str = str.substr(0,11);
    }

    var regStr = /^(01[0126789\*]{1}|02|0[3-9\*]{1}[0-9\*]{1})?-?([0-9\*]{3,4})-?([0-9\*]{4}$)/;
    
    if(str.length < 9){
        tmp = str.replace(regStr, '$2-$3');
    }else{
        tmp = str.replace(regStr, '$1-$2-$3');
    }
    
    if(tmp == null || tmp == ''){
        tmp = str;
    }
    
    return tmp;
}

//숫자이외의 문자 제거
function numberFormatter(value){
    if (value == null || value.length == 0) return '';
    if(value == 0){
        return value;
    }else{
        return value.replace(/[^0-9/*]/g, '');

    }
}

//금액 포맷팅
function amountFormatter(value){
    if (value == null || value.length == 0) return '0';
    if(value == 0){
        return value.toString();
    }else{
        return value.toString().replace(/(^0+)/, "").replace(/[^0-9]/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
}

/*
 * 이메일 체크
 */
function checkEmailStr(str){
    if(str == null || str.length == 0){
        return false;
    }
    var regExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regExp.test(str);

}

//ip 형식 체크
function checkIP(strIP) {

    var strIP = strIP.split(".");
    if (strIP.length != 4) {
        return false;
    }
    if (((0 <= strIP[0] && strIP[0] <= 255) || strIP[0] == "*")
            && ((0 <= strIP[1] && strIP[1] <= 255) || strIP[1] == "*")
            && ((0 <= strIP[2] && strIP[2] <= 255) || strIP[2] == "*")
            && ((0 <= strIP[3] && strIP[3] <= 255) || strIP[3] == "*")) {
        return true;

    } else {
        return false;
    }

}

//금액 콤마
function numberWithCommas(x) {
    if(x == "" || x == null || x == "undefined") return x;
    else return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//금액 콤마2
function numberWithCommas2(x) {
    if(x == "" || x == null || x == "undefined") {
        return x;
    } else {
        var isMinus = false;
        x = x.toString();
        if(x.length > 0 && x.substring(0, 1) == '-') {
            isMinus = true;
        }
        x = numberFormatter(x);
        return (isMinus ? '-' : '') + x.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
}


/*
 * 주민번호 Formatter
 */
var regNoFormatter = function(value){

    if (value == null || value.length == 0) return '';
    var str = value.replace(/[^0-9/*]/g, '');
    
    if(str.length <= 7){
        str = value.replace(/[^0-9]/g, '');
    }

    var tmp = '';
    if(str.length <= 6){
        tmp = str.substr(0,6);
    }
    else if(str.length > 6){
        tmp = str.substr(0,6) + '-' + str.substr(6,7);
    }
    
    return tmp;
};

/*
 *  사업자번호 Formatting
 */

var bizRegNoFormatter = function(value) {
    if(value == null || value == '') return '';
    var str = value.replace(/[^0-9/]/g, '');

    var tmp = str;
    if(str.length > 10){
        str = str.substr(0,10);
    }
    
    if(str.length <= 3){
        tmp = str;
    }else if(str.length > 3 && str.length <= 5){
        tmp = str.substr(0,3) + '-' + str.substr(3,2);
    }else if(str.length >= 6){
        tmp = str.substr(0,3) + '-' + str.substr(3,2) + '-' + str.substr(5,5);
    }
    
    return tmp;
}

//시간 포맷
function timeFormatter(value){
    
    if (value == null || value.length == 0) return '';

    if(value.length == 6 || value.length == 4){
        var str = value.substr(0,2) + ':' + value.substr(2,2);
        return str;

    }else{
        return str;
    }
}

function maskAddr(baseAddr, dtlAddr){
    var addr = baseAddr == null ? "" : baseAddr;
    addr = addr + (dtlAddr == null ? "" : ' ' + dtlAddr);
    return addr;
}

