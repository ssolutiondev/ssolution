(function(){var J0s=function(){if(this.g9C){this.g9C();return;}var ZC=J0s.prototype;ZC.g9C=function(){this.ijl="";this.stS="";};ZC.setServerURL=function(kB){
this.ijl=kB;};ZC.setServerIP=function(Q0G){this.stS=Q0G;};ZC.createSecureOutputStream=function(gl,KB){var FtC=new ByteArray();var AG=0;var Y3C;
for(Y3C in KB){AG++;}FtC.writeInt(AG);for(Y3C in KB){var ePy=KB[Y3C];this.G4(FtC,Y3C);this.G4(FtC,ePy);}var aYj=new ByteArray();var rjP=new this.tHS("forcs@#$",aYj);
rjP.write(gl,0,gl.length);FtC.writeBytes(aYj,0,aYj.length);FtC.position=0;return FtC;};ZC.createSecureInputStream=function(gl,KB){var size=gl.readInt();
for(var i=0; i<size; i++){var Y3C=this.Pz(gl);var ePy=this.Pz(gl);KB[Y3C]=ePy;}var FtC=new ByteArray();var iDS=new this.qHS("forcs@#$",gl);var ZwP=new ByteArray();
iDS.OL(ZwP,0,gl.length-gl.position);FtC.writeBytes(ZwP,0,ZwP.length);FtC.position=0;return FtC;};ZC.G4=function(gl,GQ){var i;var ZpQ=GQ.length;
gl.writeInt(ZpQ);var v;for(i=0; i<ZpQ; i++){v=GQ.charCodeAt(i);gl.writeByte((v>>>8)&255);gl.writeByte((v>>>0)&255);}};ZC.Pz=function(gl){var ZpQ;
var j0G,UWG;ZpQ=gl.readInt();if(ZpQ==-1){return "<NULL>";}else{if(ZpQ<-1){throw new Error("A malformed string has been read in a data input stream.");
}}var GQ="";var position=gl.position;for(var i=0; i<ZpQ; i++){j0G=gl[position+i*2];UWG=gl[position+i*2+1];if((j0G|UWG)<0){throw new Error("A malformed string has been read in a data input stream.");
}GQ+=String.fromCharCode((j0G<<8)+(UWG<<0));}gl.position+=ZpQ*2;return GQ;};var qHS=function(Nr,PNz){if(this.vFb){this.vFb(Nr,PNz);return;}var QzC=qHS.prototype;
QzC.vFb=function(Nr,PNz){this.FsU=Nr;this.ax=0;this.bx=0;this.cx=0;this.dx=0;this.si=0;this.RQ=0;this.DTS=0;this.VsC=0;this.i=0;this.EjC=0;this.PHC=new Array();
this.PMj=0;this.sMj=0;this.WvQ=0;this.No=PNz;this.XWC=new ByteArray();this.XWC.setLength(17);var JzC=new ByteArray();JzC.writeMultiByte(Nr,"iso-8859-1");
this.XWC.writeBytes(JzC,0,JzC.length>16?16:JzC.length);this.XWC.set(16,0);this.clear();};QzC.clear=function(){this.ax=0;this.bx=0;this.cx=0;this.dx=0;
this.si=0;this.RQ=0;this.DTS=0;this.VsC=0;this.i=0;this.EjC=0;this.PMj=0;this.sMj=0;this.WvQ=0;for(var i=0; i<8; i++){this.PHC[i]=0;}};QzC.oUj=function(){
var c=this.No.readByte();if(c==-1){return -1;}this.AlS();this.PMj=this.EjC>>>8;this.sMj=this.EjC&255;c=c^(this.PMj^this.sMj);for(this.WvQ=0; this.WvQ<=15; this.WvQ++){
this.XWC.set(this.WvQ,this.XWC.get(this.WvQ)^c);}return c;};QzC.OL=function(b,off,xG,lQy){if(b===undefined){b=null;}if(off===undefined){off=-1;
}if(xG===undefined){xG=-1;}if(lQy===undefined){lQy=-1;}if((b==null)||this.No==null){cyC("Null point exception");return -1;}if(xG<1){return 0;
}this.No.readBytes(b,off,xG);var rt=xG;if(rt<=0){return rt;}var c=0;var i=0;for(i=0; i<rt; i++){this.AlS();this.PMj=this.EjC>>>8;this.sMj=this.EjC&255;
c=b.get(i+off);c=c^(this.PMj^this.sMj);for(var j=0; j<16; j++){this.XWC.set(j,this.XWC.get(j)^c);}b.set(i+off,c);}return rt;};QzC.JB=function(){
return 0;};QzC.bLy=function(){this.dx=this.DTS+this.i;this.ax=this.PHC[this.i];this.cx=346;this.bx=20021;this.RQ=this.ax;this.ax=this.si;this.si=this.RQ;
this.RQ=this.ax;this.ax=this.dx;this.dx=this.RQ;this.ax=this.ax*this.bx&65535;this.RQ=this.ax;this.ax=this.cx;this.cx=this.RQ;if(this.ax!=0){
this.ax=(this.ax*this.si)&65535;this.cx=(this.ax+this.cx)&65535;}this.RQ=this.ax;this.ax=this.si;this.si=this.RQ;this.ax=(this.ax*this.bx)&65535;
this.dx=(this.cx+this.dx)&65535;this.ax=this.ax+1;this.DTS=this.dx;this.PHC[this.i]=this.ax;this.VsC=this.ax^this.dx;this.i=this.i+1;};QzC.AlS=function(){
this.PHC[0]=(this.XWC.get(0)*256)+this.XWC.get(1);this.bLy();this.EjC=this.VsC;this.PHC[1]=this.PHC[0]^((this.XWC.get(2)*256)+this.XWC.get(3));
this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[2]=this.PHC[1]^((this.XWC.get(4)*256)+this.XWC.get(5));this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[3]=this.PHC[2]^((this.XWC.get(6)*256)+this.XWC.get(7));
this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[4]=this.PHC[3]^((this.XWC.get(8)*256)+this.XWC.get(9));this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[5]=this.PHC[4]^((this.XWC.get(10)*256)+this.XWC.get(11));
this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[6]=this.PHC[5]^((this.XWC.get(12)*256)+this.XWC.get(13));this.bLy();this.EjC=this.EjC^this.VsC;
this.PHC[7]=this.PHC[6]^((this.XWC.get(14)*256)+this.XWC.get(15));this.bLy();this.EjC=this.EjC^this.VsC;this.i=0;};this.vFb(Nr,PNz);};ZC.qHS=qHS;
var tHS=function(Nr,B7R){if(this.WFb){this.WFb(Nr,B7R);return;}var QzC=tHS.prototype;QzC.WFb=function(Nr,B7R){this.ax=0;this.bx=0;this.cx=0;this.dx=0;
this.si=0;this.RQ=0;this.DTS=0;this.VsC=0;this.i=0;this.EjC=0;this.PHC=new Array();this.PMj=0;this.sMj=0;this.WvQ=0;this.Km=B7R;this.PHC.length=8;
this.XWC=new ByteArray();this.XWC.setLength(17);var JzC=new ByteArray();JzC.writeMultiByte(Nr,"iso-8859-1");this.XWC.writeBytes(JzC,0,JzC.length>16?16:JzC.length);
this.XWC.set(16,0);this.clear();};QzC.clear=function(){this.ax=0;this.bx=0;this.cx=0;this.dx=0;this.si=0;this.RQ=0;this.DTS=0;this.VsC=0;this.i=0;
this.EjC=0;this.PMj=0;this.sMj=0;this.WvQ=0;for(var i=0; i<8; i++){this.PHC[i]=0;}};QzC.ozC=function(b){this.AlS();this.PMj=this.EjC>>>8;this.sMj=this.EjC&255;
for(this.WvQ=0; this.WvQ<=15; this.WvQ++){this.XWC.set(this.WvQ,this.XWC.get(this.WvQ)^b);}b=b^(this.PMj^this.sMj);this.Km.writeByte(b);};QzC.write=function(b,off,xG){
if(b===undefined){b=null;}if(off===undefined){off=-1;}if(xG===undefined){xG=-1;}if((b==null)||this.Km==null){cyC("Null point exception");return;
}if(xG<1){return;}var c=0;var bSS=new ByteArray();bSS.setLength(xG);for(var i=0; i<xG; i++){this.AlS();this.PMj=this.EjC>>>8;this.sMj=this.EjC&255;
c=b.get(i+off);for(var j=0; j<16; j++){this.XWC.set(j,this.XWC.get(j)^c);}c=c^(this.PMj^this.sMj);bSS.set(i,c);}this.Km.writeBytes(bSS,0,xG);
bSS=null;};QzC.flush=function(){};QzC.close=function(){};QzC.bLy=function(){this.dx=this.DTS+this.i;this.ax=this.PHC[this.i];this.cx=346;this.bx=20021;
this.RQ=this.ax;this.ax=this.si;this.si=this.RQ;this.RQ=this.ax;this.ax=this.dx;this.dx=this.RQ;this.ax=this.ax*this.bx&65535;this.RQ=this.ax;
this.ax=this.cx;this.cx=this.RQ;if(this.ax!=0){this.ax=(this.ax*this.si)&65535;this.cx=(this.ax+this.cx)&65535;}this.RQ=this.ax;this.ax=this.si;
this.si=this.RQ;this.ax=(this.ax*this.bx)&65535;this.dx=(this.cx+this.dx)&65535;this.ax=this.ax+1;this.DTS=this.dx;this.PHC[this.i]=this.ax;this.VsC=this.ax^this.dx;
this.i=this.i+1;};QzC.AlS=function(){this.PHC[0]=(this.XWC.get(0)*256)+this.XWC.get(1);this.bLy();this.EjC=this.VsC;this.PHC[1]=this.PHC[0]^((this.XWC.get(2)*256)+this.XWC.get(3));
this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[2]=this.PHC[1]^((this.XWC.get(4)*256)+this.XWC.get(5));this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[3]=this.PHC[2]^((this.XWC.get(6)*256)+this.XWC.get(7));
this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[4]=this.PHC[3]^((this.XWC.get(8)*256)+this.XWC.get(9));this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[5]=this.PHC[4]^((this.XWC.get(10)*256)+this.XWC.get(11));
this.bLy();this.EjC=this.EjC^this.VsC;this.PHC[6]=this.PHC[5]^((this.XWC.get(12)*256)+this.XWC.get(13));this.bLy();this.EjC=this.EjC^this.VsC;
this.PHC[7]=this.PHC[6]^((this.XWC.get(14)*256)+this.XWC.get(15));this.bLy();this.EjC=this.EjC^this.VsC;this.i=0;};this.WFb(Nr,B7R);};ZC.tHS=tHS;
this.g9C();};return J0s;})();
