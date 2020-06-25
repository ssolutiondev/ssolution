
var CalCnstCost = {

	//자재 공사비 기준코드 정의
	stdrItemCds : [
		{ type : "cable", option : "M", cd : "CS00000001" },				//케이블단가 기준코드
		{ type : "cable", option : "V", cd : "CS00000003" },
		{ type : "acege", option : "M", cd : "CS00000002" },				//평수 기준코드
		{ type : "acege", option : "V", cd : "CS00000004" },
		{ type : "acege", option : "F", cd : "CS00000006" },
		{ type : "vstcnt", option : "S", cd : "CS00000018" },				//방문횟수
		{ type : "dstnc", option : "", cd : "CS00000016" },					//이동거리
		{ type : "presv", option : "Y", cd : "CS00000020" },				//보전금액 대상
		{ type : "presv", option : "N", cd : "CS00000019" },				//보전금액 제외
		{ type : "fnlsctr_cost", option : "", cd : "CS00000008" },			//금융권 설치비
	],
	//기준 유형별 기준코드 조회
	getStdrItemCd : function(type, option) {
		var vo;
		var stdrItemCd;
		for (var inx in this.stdrItemCds) {
			vo = this.stdrItemCds[inx];
			if (vo.type == type && vo.option == option) {
				stdrItemCd = vo.cd;
				break;
			}

		}
		return stdrItemCd;
	},
	//자재 공사비 기준정보 조회
	getCostStdrInfo : function(param, callback) {

		$.ajax({
			url:'/customer/common/cnst/cnst/costCableAmtInfoAction.json',
			type:'POST',
			data : param,
			async : false,
			dataType: 'json',
			success: function(data){
				if (typeof callback == "function") {
					callback(data);
				}
			}
		});
	},
	//평수 계산
	calAcege : function(val1, val2) {

		val1 = isEmpty(val1) ? 0 : parseInt(val1);	//가로
		val2 = isEmpty(val2) ? 0 : parseInt(val2);	//세로

		var acege = Math.round(val1 * val2 / 3.3);
		return acege;
	},
	/**
	 * 무인 평수 금액 계산
	 */
	calMnlssAcegeAmt : function(applyDist, matrCnt, utpStdAmt, genStdAmt, utpDist, genDist) {

		applyDist = parseInt(isEmpty(applyDist) ? 0 : applyDist);		//적용거리
		matrCnt = parseInt(isEmpty(matrCnt) ? 0 : matrCnt);				//적용 기기수
//		matrQty = parseInt(isEmpty(matrQty) ? 0 : matrQty);				//적용 기기수량
		utpStdAmt = parseInt(isEmpty(utpStdAmt) ? 0 : utpStdAmt);		//utp케이블단가
		genStdAmt = parseInt(isEmpty(genStdAmt) ? 0 : genStdAmt);		//무인케이블단가
		utpDist = parseInt(isEmpty(utpDist) ? 0 : utpDist);			//utp케이블길이
		genDist = parseInt(isEmpty(genDist) ? 0 : genDist);			//무인케이블길이

//		console.log(":::calMnlssAcegeAmt")
//		console.log(":::applyDist : " + applyDist)
//		console.log(":::matrCnt : " + matrCnt)
//		console.log(":::utpStdAmt : " + utpStdAmt)
//		console.log(":::genStdAmt : " + genStdAmt)
//		console.log(":::utpDist : " + utpDist)
//		console.log(":::genDist : " + genDist)

		//평수금액 = (구간별 평수 적용거리 * 적용 기기수량 * 무인케이블 단가) + ((utp케이블거리 * 단가) + (무인 케이블거리 * 단가) )
		totalAcege = applyDist * matrCnt * genStdAmt + ( utpDist * utpStdAmt) + ( genDist * genStdAmt );
		return totalAcege;
	},
	calMnlssAcegeAmtWithLmt : function(applyDist, matrCnt, utpStdAmt, genStdAmt, utpDist, genDist, mnlssLmtAmt) {

		applyDist = parseInt(isEmpty(applyDist) ? 0 : applyDist);		//적용거리
		matrCnt = parseInt(isEmpty(matrCnt) ? 0 : matrCnt);				//적용 기기수
//		matrQty = parseInt(isEmpty(matrQty) ? 0 : matrQty);				//적용 기기수량
		utpStdAmt = parseInt(isEmpty(utpStdAmt) ? 0 : utpStdAmt);		//utp케이블단가
		genStdAmt = parseInt(isEmpty(genStdAmt) ? 0 : genStdAmt);		//무인케이블단가
		utpDist = parseInt(isEmpty(utpDist) ? 0 : utpDist);			//utp케이블길이
		genDist = parseInt(isEmpty(genDist) ? 0 : genDist);			//무인케이블길이

//		console.log(":::calMnlssAcegeAmt")
//		console.log(":::applyDist : " + applyDist)
//		console.log(":::matrCnt : " + matrCnt)
//		console.log(":::utpStdAmt : " + utpStdAmt)
//		console.log(":::genStdAmt : " + genStdAmt)
//		console.log(":::utpDist : " + utpDist)
//		console.log(":::genDist : " + genDist)
//		console.log(":::mnlssLmtAmt : " + mnlssLmtAmt)

		var _rsltCd = "";
		var _acegeAmt = applyDist * matrCnt * genStdAmt;
		if(isEmpty(mnlssLmtAmt) == false && parseInt(_acegeAmt) > parseInt(mnlssLmtAmt))
		{
			_acegeAmt = mnlssLmtAmt;
			_rsltCd = "99";			// 제한금액초과
		}
		//평수금액 = (구간별 평수 적용거리 * 적용 기기수량 * 무인케이블 단가) + ((utp케이블거리 * 단가) + (무인 케이블거리 * 단가) )
		totalAcege = _acegeAmt + ( utpDist * utpStdAmt) + ( genDist * genStdAmt );
//		console.log(":::totalAcege : " + totalAcege)
		var result = new Object();
		result.totalAcegeAmt = totalAcege;
		result.rsltCd = _rsltCd;
		return result;
	},
	//무인 공사비 계산
	calMnlssCnstCost : function(acegeAmt, mhrlsCost, diffAmt) {

		acegeAmt = isEmpty(acegeAmt) ? 0 : parseInt(acegeAmt);	//평수금액
		mhrlsCost = isEmpty(mhrlsCost) ? 0 : parseInt(mhrlsCost);	//기기공사비
		diffAmt = isEmpty(diffAmt) ? 0 : parseInt(diffAmt);		//난이도%
//		sefsuplyMatrCost = isEmpty(sefsuplyMatrCost) ? 0 : parseInt(sefsuplyMatrCost);		//사급자재비

		//무인공사비  = ((평수금액+기기공사비)*난이도%
//		costAmt = Math.round((acegeAmt + mhrlsCost) * (diffAmt/100));
		//2018.01.05 난이도율 적용 수정 + 1추가
		console.log("============================================")
		console.log(diffAmt)
		if (diffAmt == 0) {
			costAmt = acegeAmt + mhrlsCost;
		}
		else {
//			costAmt = Math.round((acegeAmt + mhrlsCost) * ( diffAmt / 100 + 1 ));
			costAmt = ((acegeAmt + mhrlsCost) * ( diffAmt / 100 + 1 )).toFixed(1);
			costAmt = Math.round(costAmt);
		}
		return costAmt;
	},
	//카메라 케이블 단가 계산
	calCameraUnitCost : function(acegeVal, cableAsnCost, cableAsnCost2) {

		//카메라 케이블 단가 계산용 파라미터
		acegeVal = parseInt(isEmpty(acegeVal) ? 0 : acegeVal);					//평수
		cableAsnCost = parseInt(isEmpty(cableAsnCost) ? 0 : cableAsnCost);		//케이블 부착비
		cableAsnCost2 = parseInt(isEmpty(cableAsnCost2) ? 0 : cableAsnCost2);	//케이블 부착비2

		//카메라 케이블 단가
		var carmeraAmt = 0;

		//영상보안 평수가 200을 기준으로 카메라 케이블 단가 기준 적용
		if (parseInt (acegeVal) < 200) {
			carmeraAmt = cableAsnCost;
		}
		else if(parseInt (acegeVal) >= 200 && parseInt (acegeVal) < 400) {
			carmeraAmt = cableAsnCost2;
		}
		else { //카메라 케이블 기준을 초과 호출 함수에서 필요에 따라 처리
			return -1;
		}
		return carmeraAmt;
	},
	//영상 평수 금액 계산
	calVidoAcegeAmt : function(applyDist, carmeraCnt, carmeraAmt, vidoUtpAmt, vidoMulAmt, vidoCoAAmt, utpDist, mulDist, coaDist) {

		//평수계산 파라미터
		applyDist = parseInt(isEmpty(applyDist) ? 0 : applyDist);		//구간별 평수 적용거리
		carmeraCnt = parseInt(isEmpty(carmeraCnt) ? 0 : carmeraCnt);	//카메라 수량
		carmeraAmt = parseInt(isEmpty(carmeraAmt) ? 0 : carmeraAmt);	//카메라 단가
		vidoUtpAmt = parseInt(isEmpty(vidoUtpAmt) ? 0 : vidoUtpAmt);		//utp케이블단가
		vidoMulAmt = parseInt(isEmpty(vidoMulAmt) ? 0 : vidoMulAmt);		//mul케이블단가
		vidoCoAAmt = parseInt(isEmpty(vidoCoAAmt) ? 0 : vidoCoAAmt);		//무인케이블단가
		utpDist = parseInt(isEmpty(utpDist) ? 0 : utpDist);			//utp케이블길이
		mulDist = parseInt(isEmpty(mulDist) ? 0 : mulDist);			//멀티케이블길이
		coaDist = parseInt(isEmpty(coaDist) ? 0 : coaDist);			//동축케이블길이

//		console.log(":::calVidoAcegeAmt")
//		console.log(":::applyDist : " + applyDist)
//		console.log(":::carmeraCnt : " + carmeraCnt)
//		console.log(":::carmeraAmt : " + carmeraAmt)
//		console.log(":::vidoUtpAmt : " + vidoUtpAmt)
//		console.log(":::vidoMulAmt : " + vidoMulAmt)
//		console.log(":::vidoCoAAmt : " + vidoCoAAmt)
//		console.log(":::utpDist : " + utpDist)
//		console.log(":::mulDist : " + mulDist)
//		console.log(":::coaDist : " + coaDist)


		//평수금액 = ((영상적용거리 * 카메라수량 * 카메라별 케이블 단가)) + ((utp케이블거리 * 단가) + (멀티케이블거리 * 단가)+ (동축케이블거리 * 단가))
		totalAcege = this.calVidoAcegeAmt2(applyDist, carmeraCnt, carmeraAmt, ( utpDist * vidoUtpAmt), ( mulDist * vidoMulAmt), ( coaDist * vidoCoAAmt ));
//		totalAcege = applyDist * carmeraCnt * carmeraAmt + ( utpDist * vidoUtpAmt) + ( mulDist * vidoMulAmt) + ( coaDist * vidoCoAAmt );
	 	return totalAcege;
	},
	//영상 평수 금액 계산
	calVidoAcegeAmt2 : function(applyDist, carmeraCnt, carmeraAmt, vidoUtpAmt, vidoMulAmt, vidoCoAAmt) {

		//평수계산 파라미터
		applyDist = parseInt(isEmpty(applyDist) ? 0 : applyDist);		//구간별 평수 적용거리
		carmeraCnt = parseInt(isEmpty(carmeraCnt) ? 0 : carmeraCnt);	//카메라 수량
		carmeraAmt = parseInt(isEmpty(carmeraAmt) ? 0 : carmeraAmt);	//카메라 단가
		vidoUtpAmt = parseInt(isEmpty(vidoUtpAmt) ? 0 : vidoUtpAmt);		//utp케이블단가
		vidoMulAmt = parseInt(isEmpty(vidoMulAmt) ? 0 : vidoMulAmt);		//mul케이블단가
		vidoCoAAmt = parseInt(isEmpty(vidoCoAAmt) ? 0 : vidoCoAAmt);		//무인케이블단가

//		console.log(":::calVidoAcegeAmt2")
//		console.log(":::applyDist : " + applyDist)
//		console.log(":::carmeraCnt : " + carmeraCnt)
//		console.log(":::carmeraAmt : " + carmeraAmt)
//		console.log(":::vidoUtpAmt : " + vidoUtpAmt)
//		console.log(":::vidoMulAmt : " + vidoMulAmt)
//		console.log(":::vidoCoAAmt : " + vidoCoAAmt)

		//평수금액 = ((영상적용거리 * 카메라수량 * 카메라별 케이블 단가)) + ((utp케이블거리 * 단가) + (멀티케이블거리 * 단가)+ (동축케이블거리 * 단가))
		totalAcege = applyDist * carmeraCnt * carmeraAmt + vidoUtpAmt + vidoMulAmt + vidoCoAAmt;
		return totalAcege;
	},
	//영상 공사비 계산
	calVidoCnstCost : function(acegeAmt, mhrlsCost, diffAmt) {

		acegeAmt = isEmpty(acegeAmt) ? 0 : parseInt(acegeAmt);	//평수금액
		mhrlsCost = isEmpty(mhrlsCost) ? 0 : parseInt(mhrlsCost);	//기기공사비
		diffAmt = isEmpty(diffAmt) ? 0 : parseInt(diffAmt);		//난이도%
//		sefsuplyMatrCost = isEmpty(sefsuplyMatrCost) ? 0 : parseInt(sefsuplyMatrCost);		//사급자재비

		//영상공사비 = ((평수금액+기기공사비)*난이도%)
		if (diffAmt == 0) {
			costAmt = acegeAmt + mhrlsCost;
		}
		else {
//			costAmt = Math.round((acegeAmt + mhrlsCost) * (diffAmt / 100 + 1));
			costAmt = ((acegeAmt + mhrlsCost) * (diffAmt / 100 + 1)).toFixed(1);
			costAmt = Math.round(costAmt);
		}
		return costAmt;
	},
	//금융권 평수 금액 계산
	calFnlsctrAcegeAmt : function(fnlsctrCl, atmQty, applyDist, matrQty, callback) {

		fnlsctrCl = fnlsctrCl;										//금융권 구분
		atmQty = parseInt(isEmpty(atmQty) ? 0 : atmQty);			//CD기 수량
		applyDist = parseInt(isEmpty(applyDist) ? 0 : applyDist);	//적용거리
		matrQty = parseInt(isEmpty(matrQty) ? 0 : matrQty);			//적용 기기수량

//		console.log(":::calFnlsctrAcegeAmt")
//		console.log(":::fnlsctrCl : " + fnlsctrCl)
//		console.log(":::atmQty : " + atmQty)
//		console.log(":::applyDist : " + applyDist)
//		console.log(":::matrQty : " + matrQty)

		if (isEmpty(fnlsctrCl) == true) {
			callback(0);
			return;
		}

		// 금융권 설치비 확인
		var param = {};
		param.stdrItmCd = this.getStdrItemCd("fnlsctr_cost", "");
		param.searchAcege = fnlsctrCl;
		this.getCostStdrInfo(param, function(data) {

			var columnList = data.data;

			var totalAcege = 0;
			if (isEmpty(columnList) == true) {
				//지점,금고,대여금고 : (구간별 평수 적용거리 * 적용기기수량 * 550) * 200%
				totalAcege = parseInt(applyDist) * parseInt(matrQty) * 550 * 2;
			}
			else {
				if(columnList.length > 0){
					for(var i=0; i<columnList.length; i++){
						var costVal = columnList[i].cnstCostReflctVal;
						totalAcege = parseInt(atmQty) * 50000 + parseInt(costVal);
					}
				}
			}
			callback(totalAcege);
		});
	},
	//금융권 공사비 계산
	calFnlsctrCnstCost : function(acegeAmt, mhrlsCost) {

		acegeAmt = isEmpty(acegeAmt) ? 0 : parseInt(acegeAmt);	//평수금액
		mhrlsCost = isEmpty(mhrlsCost) ? 0 : parseInt(mhrlsCost);	//기기공사비
//		sefsuplyMatrCost = isEmpty(sefsuplyMatrCost) ? 0 : parseInt(sefsuplyMatrCost);		//사급자재비

		//평수금액 + 기기공사비
		costAmt = acegeAmt + mhrlsCost;
		return costAmt;
	},
	/*
	 * 예상발주금액
	 */
	calExpectOrderAmt : function(cnstTp,
			mnlssCnstCost,
			vidoCnstCost,
			fnlsctrCnstcost,
			vstAmt,
			mvmDstncAmt,
			sefsuplyMatrAmt,
			addLbrChrg,
			presvAmt,
			vForce,
			offcSafer,
			turboVccne) {

		cnstTp = isEmpty(cnstTp) ? 1 : cnstTp;									//공사유형
		mnlssCnstCost = isEmpty(mnlssCnstCost) ? 0 : parseInt(mnlssCnstCost);	//무인공사비
		vidoCnstCost = isEmpty(vidoCnstCost) ? 0 : parseInt(vidoCnstCost);		//영상공사비
		fnlsctrCnstcost = isEmpty(fnlsctrCnstcost) ? 0 : parseInt(fnlsctrCnstcost);//금융권공사비
		vstAmt = isEmpty(vstAmt) ? 0 : parseInt(vstAmt);						//방문횟수금액
		mvmDstncAmt = isEmpty(mvmDstncAmt) ? 0 : parseInt(mvmDstncAmt);			//이동거리금액

		sefsuplyMatrAmt = isEmpty(sefsuplyMatrAmt) ? 0 : parseInt(sefsuplyMatrAmt);//사급자재비
		addLbrChrg = isEmpty(addLbrChrg) ? 0 : parseInt(addLbrChrg);			//추가노무비
		presvAmt = isEmpty(presvAmt) ? 0 : parseInt(presvAmt);					//보전금액

		vForce = isEmpty(vForce) ? 0 : parseInt(vForce);						//v force
		offcSafer = isEmpty(offcSafer) ? 0 : parseInt(offcSafer);				//오피스세이퍼
		turboVccne = isEmpty(turboVccne) ? 0 : parseInt(turboVccne);			//터보백신

		var cnstTpVal = 0;
		if (cnstTp == "20" || cnstTp == "30"){		//20 : 휴일, 30:야간
			cnstTpVal = 1.1;
		} else if(cnstTp == "40") {					//40 : 휴일/야간
			cnstTpVal = 1.2;
		} else {
			cnstTpVal = 1;
		}

		//예상발주금액 구하기 = (무인공사비+영상공사비+금융권)*공사유형+방문횟수금액 + 이동거리금액 + V Forece + 오피스세이퍼 + 터보백신 + 사급자재 + 추가노무비 + 보전금액
		var expoertOrderAmt = (mnlssCnstCost + vidoCnstCost + fnlsctrCnstcost) * cnstTpVal + vstAmt + mvmDstncAmt;
		expoertOrderAmt += sefsuplyMatrAmt + addLbrChrg + presvAmt;
		expoertOrderAmt += vForce + offcSafer + turboVccne;
		expoertOrderAmt = Math.round(expoertOrderAmt);
		return expoertOrderAmt;

	}
//	calExpectOrderAmt : function(cnstTp, mnlssCnstCost, vidoCnstCost, fnlsctrCnstcost, vstAmt, mvmDstncAmt) {
//
//		cnstTp = isEmpty(cnstTp) ? 1 : cnstTp;									//공사유형
//		mnlssCnstCost = isEmpty(mnlssCnstCost) ? 0 : parseInt(mnlssCnstCost);	//무인공사비
//		vidoCnstCost = isEmpty(vidoCnstCost) ? 0 : parseInt(vidoCnstCost);		//영상공사비
//		fnlsctrCnstcost = isEmpty(fnlsctrCnstcost) ? 0 : parseInt(fnlsctrCnstcost);//금융권공사비
//		vstAmt = isEmpty(vstAmt) ? 0 : parseInt(vstAmt);						//방문횟수금액
//		mvmDstncAmt = isEmpty(mvmDstncAmt) ? 0 : parseInt(mvmDstncAmt);			//이동거리금액
//
//		var cnstTpVal = 0;
//		if (cnstTp == "20" || cnstTp == "30"){		//20 : 휴일, 30:야간
//			cnstTpVal = 1.1;
//		} else if(cnstTp == "40") {					//40 : 휴일/야간
//			cnstTpVal = 1.2;
//		} else {
//			cnstTpVal = 1;
//		}
//
//		//예상발주금액 구하기 = (무인공사비+영상공사비+금융권)*공사유형+방문횟수금액 + 이동거리금액
//		var expoertOrderAmt = (mnlssCnstCost + vidoCnstCost + fnlsctrCnstcost) * cnstTpVal + vstAmt + mvmDstncAmt;
//		expoertOrderAmt = Math.round(expoertOrderAmt);
//		return expoertOrderAmt;
//
//	}

};

var CalMatrCost = {

	/*
	 * prodCd : 상품코드
	 * qty : 수량
	 * matrCost : 표준용역료(서비스단가 * 수량)
	 * matrUnitPrc : 자재비(자재단가 * 수량)
	 */
	getCloudCost : function(prodCd, qty, matrCost, matrUnitPrc) {

		if (isEmpty(prodCd) == true) return false;
		if (isEmpty(qty) == true) return false;
		if (isEmpty(matrCost) == true) return false;
		if (isEmpty(matrUnitPrc) == true) return false;

		qty = Number(qty);
		var stdServcCost = 0
		var clcServcCost = matrUnitPrc;
		var result = true;
		var prodGubun = (prodCd == "PD00001002") ? "01" : "02";

		switch (qty) {
		case 1:
			if (prodGubun == "01") { stdServcCost = 30000; }
			else { stdServcCost = 50000; }
			break;
		case 2:
			if (prodGubun == "01") { stdServcCost = 35000; }
			else { stdServcCost = 60000; }
			break;
		case 3:
			if (prodGubun == "01") { stdServcCost = matrCost + 25000; }
			else { stdServcCost = matrCost + 44000; }
			clcServcCost = 439000;
			break;
		case 4:
			if (prodGubun == "01") { stdServcCost = matrCost + 25000; }
			else { stdServcCost = matrCost + 44000; }
			clcServcCost = 503000;
			break;
		case 5:
			if (prodGubun == "01") { stdServcCost = 79000; }
			else { stdServcCost = 120000; }
			break;
		case 6:
			if (prodGubun == "01") { stdServcCost = 94000; }
			else { stdServcCost = 140000; }
			break;
		case 7:
			if (prodGubun == "01") { stdServcCost = 114000; }
			else { stdServcCost = 160000; }
			break;
		case 8:
			if (prodGubun == "01") { stdServcCost = 134000; }
			else { stdServcCost = 180000; }
			break;
		case 9:
			if (prodGubun == "01") { stdServcCost = 154000; }
			else { stdServcCost = 200000; }
			break;
		case 10:
			if (prodGubun == "01") { stdServcCost = 174000; }
			else { stdServcCost = 220000; }
			break;
		case 11:
			if (prodGubun == "01") { stdServcCost = 194000; }
			else { stdServcCost = 240000; }
			break;
		case 12:
			if (prodGubun == "01") { stdServcCost = 214000; }
			else { stdServcCost = 260000; }
			break;
		case 13:
			if (prodGubun == "01") { stdServcCost = 234000; }
			else { stdServcCost = 280000; }
			break;
		case 14:
			if (prodGubun == "01") { stdServcCost = 254000; }
			else { stdServcCost = 300000; }
			break;
		case 15:
			if (prodGubun == "01") { stdServcCost = 274000; }
			else { stdServcCost = 320000; }
			break;
		case 16:
			if (prodGubun == "01") { stdServcCost = 294000; }
			else { stdServcCost = 340000; }
			break;
		default:
//			result = false;
			stdServcCost = matrCost;
			clcServcCost = matrUnitPrc;
			break;
		}

		if (result == false) {
			return false;
		}

		var result = new Object();
		result.matrCost = stdServcCost;
		result.matrUnitPrc = clcServcCost;
		return result;
	},
	/*
	 * matrAvailList : 사용가능자재 datatable
	 * data : 선택자재 data of datatable
	 * prodCd : 회선상품코드
	 */
	getMatrSctnServcCost : function(matrAvailList, data, prodCd) {

		var result = false;

		// 2018.02.26 사용가능 자재목록에서 서비스 구간단가(용역료) 사용여부 확인 추가
		$(matrAvailList).each(function(inx) {
			var availData = matrAvailList[inx];
			var _matrMdlCd = isEmpty(data.matrMdlCd) == false ? data.matrMdlCd : data.matrCd

			if (availData.matrMdlCd == _matrMdlCd
					&& availData.matrTp == data.matrTp
					&& availData.matrCl == data.matrCl
			)
			{
				var sctnUnitList = availData.sctnUnitList;						//자재공사비 수량별 서비스 구간 단가 목록
				if (isEmpty(sctnUnitList) == false) {

					var qty = data.qty;
					var matrTp = data.matrTp;	// 자재유형(01:신품,02:철걸품)
					var isApply = false;		// 20180307 서비스단가 구간 없을 시 원서비스 단가적용

					for ( var inx in sctnUnitList ) {

						var sctnVO = sctnUnitList[inx];
						var s_sctnStrt = sctnVO.sctnStrt;				// 시작구간
						var s_sctnEnd = sctnVO.sctnEnd;					// 종료구간
						var s_svcUnitPrc = sctnVO.svcUnitPrc;			// 서비스 단가
						var s_newMhrlsCalcrt = sctnVO.newMhrlsCalcrt;	// 신품 산정율
						var s_rmvlMhrlsCalcrt = sctnVO.rmvlMhrlsCalcrt;	// 철거품 산정율

						if (s_sctnStrt <= qty && qty <= s_sctnEnd) {

							var s_rt = ( matrTp == "02" ) ?  s_rmvlMhrlsCalcrt : s_newMhrlsCalcrt;
							//================================================================
							//20180303 서비스 단가 및 용역료 재수정
							var s_matrcost = Number(s_svcUnitPrc) * s_rt / 100;		// 서비스 ( 용역료)
							var s_unitcost = parseInt(s_matrcost) / parseInt(qty);
							result = {};
							result.matrCost = s_matrcost;
							result.unitMatrCost = s_unitcost;
							isApply = true;			// 20180307 서비스단가 구간 없을 시 원서비스 단가적용
							//===============================================================
							break;
						}
					}
					// 20180307 서비스 구간단가 적용대상이 아닌 경우 원서비스단가적용
					if (isApply == false) {
						result = {};
						result.matrCost = availData.matrCost * qty;
						result.unitMatrCost = availData.matrCost;
					}
				}

				// 20180330 클라우드 표준용역료 및 자재비 적용
				var rsltMatrCost = isEmpty(result) == true ? data.matrCost : result.matrCost;
				var rsltMatrUnitPrc = data.matrUnitPrc;
				if (data.matrCl1 == '2' && data.matrCl == "D22") {
					var cloudVO = CalMatrCost.getCloudCost(prodCd, qty, rsltMatrCost, rsltMatrUnitPrc);

					if (isEmpty(result)) result = {};
					result.matrCost = cloudVO.matrCost;
					result.unitMatrCost = 0;
					result.matrUnitPrc = cloudVO.matrUnitPrc;
				}

				// break loop
				return false;
			}
		});
		return result;
	},
	getSctnCost : function(matrAvailList, data) {

		var prodCd = data.prodCd;				// 상품코드
		var qty = data.qty;						// 수량
		var matrCd = isEmpty(data.matrMdlCd) == false ? data.matrMdlCd : data.matrCd;	// 자재코드
		var matrTp = data.matrTp;				// 자재유형(01:신품,02:철거품)
		var matrCl = data.matrCl;				// 자재구분(D22:클라우드)
		var matrCl1 = data.matrCl1;				// 자재구분1(2:영상,1:무인+)
		var matrCost = data.matrCost;			// 표준용역료(용역료단가 * 수량)
		var matrUnitPrc = data.matrUnitPrc;		// 자재비(자재단가 * 수량)
		var svcUnitPrc = data.svcUnitPrc;		// 서비스(표준용역료) 단가
		var clcUnitPrc = data.clcUnitPrc;		// 자재비 단가

		var rsltMatrCost = matrCost;
		var rsltUnitMatrCost = svcUnitPrc;
		var rsltCd = false;

		$(matrAvailList).each(function(indexs) {
			var matrVO = matrAvailList[indexs];
			if (matrVO.matrMdlCd == matrCd && matrVO.matrTp == matrTp && matrVO.matrCl == matrCl) {

				var sctnUnitList = matrVO.sctnUnitList;						//자재공사비 수량별 서비스 구간 단가 목록
				if (isEmpty(sctnUnitList) == false) {

					for ( var inx in sctnUnitList ) {

						var sctnVO = sctnUnitList[inx];
						var s_sctnStrt = sctnVO.sctnStrt;				// 시작구간
						var s_sctnEnd = sctnVO.sctnEnd;					// 종료구간
						var s_svcUnitPrc = sctnVO.svcUnitPrc;			// 서비스 단가
						var s_newMhrlsCalcrt = sctnVO.newMhrlsCalcrt;	// 신품 산정율
						var s_rmvlMhrlsCalcrt = sctnVO.rmvlMhrlsCalcrt;	// 철거품 산정율

						if (s_sctnStrt <= qty && qty <= s_sctnEnd) {

							var s_rt = ( matrTp == "02" ) ?  s_rmvlMhrlsCalcrt : s_newMhrlsCalcrt;
							var s_matrcost = Number(s_svcUnitPrc) * s_rt / 100;		// 서비스 ( 용역료)
							var s_unitcost = parseInt(s_matrcost) / parseInt(qty);
							rsltMatrCost = s_matrcost;
							rsltUnitMatrCost = s_unitcost;
							rsltCd = true;
							break;
						}
					}
				}
			}
		});

		if (rsltCd == false) {
			return false;
		}

		var result = new Object();
		result.matrCost = rsltMatrCost;
		result.unitMatrCost = rsltUnitMatrCost;
		return result;
	}

};