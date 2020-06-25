(function(){var uKJ=function(){if(this.g9C){this.g9C();return;}var ZC=uKJ.prototype;ZC.g9C=function(){this.ijl="";this.stS="";};ZC.setServerURL=function(kB){
this.ijl=kB;};ZC.setServerIP=function(Q0G){this.stS=Q0G;};ZC.createSecureOutputStream=function(gl,KB){var FtC=new ByteArray();var AG=0;var Y3C;
for(Y3C in KB){AG++;}FtC.writeInt(AG);for(Y3C in KB){var ePy=KB[Y3C];this.G4(FtC,Y3C);this.G4(FtC,ePy);}FtC.writeBytes(gl,0,gl.length);FtC.position=0;
return FtC;};ZC.createSecureInputStream=function(gl,KB){var size=gl.readInt();for(var i=0; i<size; i++){var Y3C=this.Pz(gl);var ePy=this.Pz(gl);
KB[Y3C]=ePy;}var FtC=new ByteArray();FtC.writeBytes(gl,gl.position,gl.length-gl.position);FtC.position=0;return FtC;};ZC.G4=function(gl,GQ){var i;
var ZpQ=GQ.length;gl.writeInt(ZpQ);var v;for(i=0; i<ZpQ; i++){v=GQ.charCodeAt(i);gl.writeByte((v>>>8)&255);gl.writeByte((v>>>0)&255);}};ZC.Pz=function(gl){
var ZpQ;var j0G,UWG;ZpQ=gl.readInt();if(ZpQ==-1){return "<NULL>";}else{if(ZpQ<-1){throw new Error("A malformed string has been read in a data input stream.");
}}var GQ="";var position=gl.position;for(var i=0; i<ZpQ; i++){j0G=gl[position+i*2];UWG=gl[position+i*2+1];if((j0G|UWG)<0){throw new Error("A malformed string has been read in a data input stream.");
}GQ+=String.fromCharCode((j0G<<8)+(UWG<<0));}gl.position+=ZpQ*2;return GQ;};this.g9C();};return uKJ;})();
