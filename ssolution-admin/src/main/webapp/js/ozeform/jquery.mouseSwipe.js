;(function($) {
$.fn.swipeForToolbar = function(options) {

var defaults = {DECAY:0.9, MOUSEDOWN_DECAY:0.5, SPEED_SPRING:0.5, BOUNCE_SPRING:0.08,
  HORIZ:true, TYPE:'mouseSwipe', SNAPDISTANCE:20, DURATION:250, EASING:'swing', ARROWS:false, FADEARROWS:false,
  SLIDESHOWTIME:4000, AUTOSTART:0, PAUSEONHOVER:false, PAGENUM:'#pagenum', DISABLELINKS:true
};
var o = $.extend(defaults, options);

var plugin = this; //jQuery object or a collection of jQuery objects.
var elm = document.getElementById(plugin.attr('id')); //instance
var panel = plugin.children(".panel"); // panels inside slider.
var vw = parseInt(plugin.parents('div').css('width'),10);  //viewport width.
var vh = parseInt(plugin.parents('div').css('height'),10); //viewport height.
var _velocity = 0, bouncing = 0, _mouseDownLT, _mouseDownXY, _lastMouseDownXY, panelnum=1;
var edge, sliderLT, sliderLen, _mouseDown = false, ie = false, hasTouch = false, VIEWPORT, len;
var startAnimFrame=false, slideInterval=null;

if (o.HORIZ==true) {
  var sliderW = parseInt(panel.css('width'),10) * panel.length;
  VIEWPORT=vw; 
  edge='left'; 
  panel.css('float','left'); 
  plugin.css('width',sliderW); 
  sliderLen = sliderW;
  plugin.after("<div class='navLeft'></div><div class='navRight'></div>");
} else {
  var sliderH = parseInt(panel.css('height'),10) * panel.length;
  VIEWPORT=vh; 
  edge='top'; 
  panel.css('float','none'); 
  plugin.css('height',sliderH); 
  sliderLen = sliderH;
  plugin.after("<div class='navTop'></div><div class='navBottom'></div>");
}
plugin.css(edge,0);
	var btnTL = $(this).next('.navTop,.navLeft');
	var btnBR = btnTL.next('.navBottom,.navRight');
	btnTL.bind('mouseup', function(e) { 
		panelnum--; 
		arrows($(this).attr('class')); 
		return false; 
	});
	btnBR.bind('mouseup', function(e) { 
		panelnum++; 
		arrows($(this).attr('class')); 
		return false; 
	});
	if (o.ARROWS==false) { 
		$(btnTL).css('display','none'); 
		$(btnBR).css('display','none'); 
	}

var mouseswipe=function(sliderLT) {
	if (_mouseDown) {
		//document.getElementById("ozlog").innerHTML = "mouseswipe1";
		_velocity *= o.MOUSEDOWN_DECAY;
	} else {
		//document.getElementById("ozlog").innerHTML = "mouseswipe2";
		_velocity *= o.DECAY;
	}
	if (!_mouseDown) {
		var VIEWPORT2 = 0; 
		if (o.HORIZ==true) {
			VIEWPORT2 = parseInt(plugin.parents('div').css('width'),10);
		}else{
			VIEWPORT2 = parseInt(plugin.parents('div').css('height'),10);
		}
		//sdocument.getElementById("ozlog").innerHTML = "sliderLT : " + sliderLT +", sliderLen : "+ sliderLen + "B_SPR : " + o.BOUNCE_SPRING;
		if (sliderLT > 0)  {
			bouncing = -sliderLT * o.BOUNCE_SPRING;
			//document.getElementById("ozlog").innerHTML = "mouseswipe3 : " + bouncing;
		} else if (sliderLT + sliderLen < VIEWPORT2) {
			bouncing = (VIEWPORT2 - sliderLen - sliderLT) * o.BOUNCE_SPRING;
			//document.getElementById("ozlog").innerHTML = "mouseswipe4 : " + bouncing;
		} else { bouncing = 0; }
		if (_lastMouseDownXY-_mouseDownXY < 0) {
			//document.getElementById("ozlog").innerHTML = "mouseswipe5 : " + edge + ", Math : " + Math.ceil(_velocity + bouncing);
			plugin.css(edge,sliderLT + Math.ceil(_velocity + bouncing)); //swipe left
		} else {
			//document.getElementById("ozlog").innerHTML = "mouseswipe6 : " + edge + ", Math : " + Math.ceil(_velocity + bouncing);
			plugin.css(edge,sliderLT + Math.floor(_velocity + bouncing)); //swipe right  
		}
	}
};

var panelswipe=function(sliderLT) {
  if (o.HORIZ==true) { len = vw; a = 'left'; } else { a='top'; len = vh; }
  panelnum = Math.ceil(Math.abs((sliderLT/panel.length) / (len/panel.length)));
  if (_lastMouseDownXY-_mouseDownXY <= -(o.SNAPDISTANCE)) {
      if (panelnum >= panel.length) { panelnum=panel.length-1; }
      animate(a,-len * panelnum,len);
  } else if (_lastMouseDownXY-_mouseDownXY >= o.SNAPDISTANCE) {
      animate(a,(panelnum * -len) + len,len);
  }
};

var arrows = function(elm) {
	if (elm=='navTop') {
		len = vh; 
		a = 'top'; 
		b = -sliderH + len; 
		d = parseInt(plugin.css(a)) + len; 
	}
    else if (elm=='navBottom') {
		len = vh; 
		a = 'top'; 
		b = -sliderH + len; 
		d = parseInt(plugin.css(a)) - len; 
	}
    else if (elm=='navLeft') {
		len = vw; 
		a = 'left'; 
		b = -sliderW + len; 
		d = parseInt(plugin.css(a)) + len; 
	}
    else {
		len = vw; a = 'left'; 
		b = -sliderW + len; 
		d = parseInt(plugin.css(a)) - len; 
	} //navRight
    if (panelnum>panel.length) {
		panelnum=1; animate(a,0,len); 
		return; 
	}
    if (panelnum<1) { 
		panelnum=panel.length; 
		animate(a,b,len); 
		return; 
	}
    animate(a,d,len); //panel by panel
};

var animate = function(a,b,len) {
	var aniArgs = {}; 
	aniArgs[a] = b;  
	plugin.stop(true,true).animate(aniArgs,{
		duration: o.DURATION, 
		easing: o.EASING, 
		complete: function(){
			update(len);
		}}
	);
};

var update=function(len) {
	sliderLT = parseInt(plugin.css(edge),10);
	panelnum = Math.ceil(Math.abs((sliderLT/panel.length) / (len/panel.length))+1);
	$(o.PAGENUM).html(panelnum);
	btn = o.HORIZ==true ? '.navRight' : '.navTop';
	if (o.ARROWS==true) {
		if (o.FADEARROWS==true && panelnum==panel.length) { 
			$(btnBR, btn).fadeOut('fast'); 
		} else { 
			$(btnBR, btn).fadeIn('fast');
		}
		if (o.FADEARROWS==true && panelnum==1) { 
			$(btnTL, btn).fadeOut('fast'); 
		} else { 
			$(btnTL, btn).fadeIn('fast'); 
		}
	}
};

var mouseEnter=function(e) { clearInterval(slideInterval); slideInterval=null; }; //only on PAUSEONHOVER, panelSwipe

var mouseLeave=function(e) { //only on PAUSEONHOVER, panelSwipe
	clearInterval(slideInterval); 
	slideInterval=null; //just in case.
	slideInterval=setInterval(function() { 
		panelnum++; 
		arrows(btnBR.attr('class'));
		},
		o.SLIDESHOWTIME);
};

window.requestAnimFrame = function(){ 
	return ( window.requestAnimationFrame || window.webkitRequestAnimationFrame || 
	window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || 
	function(callback){ window.setTimeout(callback, 1000 / 60); } );
}();

function frame() {
	//document.getElementById("ozlog").innerHTML = "plugin.css(edge) : " + plugin.css(edge) + "parseInt : " + parseInt(plugin.css(edge),10);
	
	if($(plugin).width() > $(plugin.parents('div')).width()){
		mouseswipe(parseInt(plugin.css(edge),10)); 
	}
	if (startAnimFrame == true) { 
		requestAnimFrame(frame); 
	} 
};

var disablelinks=function() {
	//document.getElementById("ozlog").innerHTML = "disablelinks1";
	$('a', plugin).each(function(){ 
		//document.getElementById("ozlog").innerHTML = "disablelinks2";
		$(this).click(function(){
			if(Math.abs(_lastMouseDownXY-_mouseDownXY) >= o.SNAPDISTANCE) {
				return false;
			} 
		}); 
	}); 
};

var touchStart=function(e) { //mouse down
  plugin.stop(true,false);
  if (o.ARROWS==true) { $(btnTL, btn).css('display','none'); $(btnBR, btn).css('display','none'); }
  if (!_mouseDown) {
    if (hasTouch) { e.preventDefault(); e = event.touches[0]; } else { if (!e) e = window.event; }
    if (elm.setCapture) {
      elm.setCapture(); //if dragged outside of div
    } else {
      window.addEventListener('mousemove', touchMove, false);
      window.addEventListener('mouseup', touchEnd, false);
    }
    if (o.HORIZ==true) {
      _mouseDownXY = _lastMouseDownXY = ie == true ? e.clientX : e.pageX;
      _mouseDownLT = document.getElementById(plugin.attr('id')).offsetLeft;
    } else {
      _mouseDownXY = _lastMouseDownXY = ie == true ? e.clientY : e.pageY;
      _mouseDownLT = document.getElementById(plugin.attr('id')).offsetTop;
    }
    _mouseDown = true;
    if (o.TYPE=='mouseSwipe' && startAnimFrame==false) { startAnimFrame=true; requestAnimFrame(frame); } //mouseSwipe
  }
};

var touchMove=function(e) { //mouse move
	//document.getElementById("ozlog").innerHTML = "plugin.clientWidth : "+plugin[0].clientWidth;
	var VIEWPORT2 = 0; 
	if (o.HORIZ==true) {
		VIEWPORT2 = parseInt(plugin.parents('div').css('width'),10);
	}else{
		VIEWPORT2 = parseInt(plugin.parents('div').css('height'),10);
	}
	if(plugin[0].clientWidth < VIEWPORT2)
		return;

  if (_mouseDown) {
    if (hasTouch) { e.preventDefault(); e = event.touches[0]; } else { if (!e) e = window.event; }
    if (ie == true) {
      var MouseXY = edge == 'left' ? e.clientX : e.clientY;
    } else {
      var MouseXY = edge == 'left' ? e.pageX : e.pageY;
    }
    plugin.css(edge, _mouseDownLT + (MouseXY - _mouseDownXY));
    _velocity += ((MouseXY - _lastMouseDownXY) * o.SPEED_SPRING);
    _lastMouseDownXY = MouseXY;
  }
};

var touchEnd=function(e) { //mouse up
  if (_mouseDown) {
      _mouseDown = false;
      disablelinks();
    if (elm.setCapture) { 
      elm.releaseCapture();
    } else { 
    window.removeEventListener('mousemove', touchMove, false);
    window.removeEventListener('mouseup', touchEnd, false);
    }
    if (o.TYPE=='panelSwipe') { panelswipe(parseInt(plugin.css(edge),10)); }
  }
};

hasTouch = 'ontouchstart' in window;
plugin.bind('mousedown touchstart', function(event){ touchStart(event); }); 
plugin.bind('mousemove touchmove', function(event){ touchMove(event); }); 
plugin.bind('mouseup touchend', function(event){ touchEnd(event); });

var slidetype = o.PAUSEONHOVER==true && o.TYPE=='panelSwipe';
if (slidetype) { plugin.bind('mouseenter', function(event){ mouseEnter(event); }); }
if (slidetype) { plugin.bind('mouseleave', function(event){ mouseLeave(event); }); }
if (o.FADEARROWS==true) { btn = o.HORIZ==true ? '.navRight' : '.navTop'; $(btnTL, btn).css('display','none'); }
if (o.AUTOSTART>0) { setTimeout(mouseLeave, o.AUTOSTART); }
} //end options
})(jQuery)