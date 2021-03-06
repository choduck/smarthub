$(document).ready(function (){
    var winH;
    var topbarH;

    winH = $(document).height();
    topbarH = $("#topbar").outerHeight();

    var viewportH = $(document).height() - $("#topbar").outerHeight();

    $("#contents-area").css("max-height", viewportH + "px");
    
    // issueList(발급처 정보 가져오기)
    $("#exampleModalCenter").on("show.bs.modal", function(){
    	onStep1();
    });
    
    $("#exampleModalCenter").on("hide.bs.modal", function(){
        $(".nav-item").removeClass("active");
        $(".tab-pane").removeClass("active fadein fade show");
        $(".tab-pane").addClass("fade");
    });
    
    $("#qr").hide();
});

var webSocket = null;	

var tempObject = {
	issuedPlaceCd : null,
	certiCd : null,
	field : [],
	getIssuedPlaceCd : function(){
		if (this.issuedPlaceCd == null){
			return "";
		}
		return this.issuedPlaceCd;
	},
	getCertiCd : function(){
		if (this.certiCd == null){
			return "";
		}
		return this.certiCd;
	},
	generateFieldJson : function(){
		var JSONObject = new Object();
		var ajsonArray = new Array();
		var aArray = new Array();
		
		
		JSONObject.sid = document.commonCode.sid.value;
		JSONObject.issuer = "did:woori:issuer_id";
					
		JSONObject.field_list = Object.keys(this.field);
					
		return JSON.stringify(JSONObject); 
	}
}

function onStep1(){
	onStep1LoadList();
	
    $(".nav-item").removeClass("active");
    $(".step1").addClass("active");
    $(".tab-pane").removeClass("active fadein fade show");
    $("#step1").addClass("tab-pane fade fadein active show");
}

function setDataStep1(){
	var value = $("input:checkbox[name=issuedPlaceCd]:checked").val();
	this.tempObject.issuedPlaceCd = value;
	document.commonCode.issuedPlaceCd.value = value; 
}

function onStep2(){
	if($("input:checkbox[name=issuedPlaceCd]").is(":checked")){
		setDataStep1();
	}else{
		alert("발급처 선택을 선택하지 않았습니다.");
		return true;
	}
	
	onStep2LoadList();
	
	$(".nav-item").removeClass("active");
    $(".step1,.step2").addClass("active");
    $(".tab-pane").removeClass("active fadein fade show");
    $("#step2").addClass("tab-pane fade fadein active show");
}

function setDataStep2(){
	var value = $("input:checkbox[name=certiCd]:checked").val();
	
	if(this.tempObject.certiCd != value){
		this.tempObject.field = [];
	}
	
	this.tempObject.certiCd = value;
	document.commonCode.certiCd.value = value;
}

function onStep3(){
	if($("input:checkbox[name=certiCd]").is(":checked")){
		setDataStep2();
	}else{
		alert("신분자격증명목록 선택을 선택하지 않았습니다.");
		return true;
	}
	
	onStep3LoadList();
	
	$(".nav-item").removeClass("active");
    $(".step1,.step2,.step3").addClass("active");
    $(".tab-pane").removeClass("active fadein fade show");
    $("#step3").addClass("tab-pane fade fadein active show");
}

function setDataStep3(){
	$("input:checkbox[name=orderNo]:checked").each(function(i, idx){
		var key = $(idx).data("fieldnm");
    	var value = $(idx).data("fieldkornm");
		//tempObject.field[key] = value;
    	tempObject.field[key] = key;
	});
}

function onStep4(){
	if($("input:checkbox[name=orderNo]:checked").length > 0 ){
		setDataStep3();	
	}else{
		alert("연동 정보 목록을 선택을 선택하지 않았습니다.");
		return true;
	}
	
	onStep4Load(tempObject.generateFieldJson());
	
	$(".nav-item").removeClass("active");
    $(".step1,.step2,.step3, .step4").addClass("active");
    $(".tab-pane").removeClass("active fadein fade show");
    $("#step4").addClass("tab-pane fade fadein active show");
}

function onStep5(){
    $(".nav-tabs").addClass("d-none");
}

function onStep1LoadList(){
	$("div#step1 table tbody").html("");
	var form = document.commonCode;
	
	form.uppCd.value = "A0001";
	
	$(".step1").addClass("active ");
	$("#step1").addClass("fadein active show");
	
	jQuery.ajax({
		type:"POST"
			,url:"/rest/wini/issuePlaceList.do"
			,dataType:"JSON"
			,data: $("#commonCode").serialize()
			,success:function(data){
				$.each(data, function(idx, item){
					var html = "<tr>";
					//checkbox
					html+="<td>";
					html+="<input type='checkbox' name='issuedPlaceCd' value='"+item.cd+"' onclick='step1checkClick(this)' />";
					html+="</td>";
					
					// 번호
					html+="<td>";
					html+=item.cd;
					html+="</td>";
					// 발급처목록
					html+="<td>";
					html+=item.cdNm;
					html+="</td>";
					
					html+="</tr>";
					$("div#step1 table tbody").append(html);
				});
				form.issuedPlaceCd.value = "";
				if("" != tempObject.getIssuedPlaceCd()){
					$("input:checkbox[name=issuedPlaceCd]:checkbox[value='"+tempObject.getIssuedPlaceCd()+"']").prop("checked",true);
		    	}
				
			}
			,error:function(xhr, status, error){
				
			}
	})
}

function onStep2LoadList(){
	
	$("div#step2 table tbody").html("");
	var form = document.commonCode;
	form.uppCd.value = "B0001";
	
	$(".step1").addClass("active ");
	$("#step1").addClass("fadein active show");
	
	jQuery.ajax({
		type:"POST"
			,url:"/rest/wini/certiList.do"
			,dataType:"JSON"
			,data: $("#commonCode").serialize()
			,success:function(data){
				$.each(data, function(idx, item){
					var html = "<tr>";
					//checkbox
					html+="<td>";
					html+="<input type='checkbox' name='certiCd' value='"+item.cd+"' onclick='step2checkClick(this)' />";
					html+="</td>";
					
					// 번호
					html+="<td>";
					html+=item.cd;
					html+="</td>";
					// 발급처목록
					html+="<td>";
					html+=item.cdNm;
					html+="</td>";
					
					html+="</tr>";
					$("div#step2 table tbody").append(html);
					
				});
				
				if("" != tempObject.getCertiCd()){
					$("input:checkbox[name=certiCd]:checkbox[value='"+tempObject.getCertiCd()+"']").prop("checked",true);
		    	}
			}
			,error:function(xhr, status, error){
				
			}
	})
}

function onStep3LoadList(){
	$("div#step3 table tbody").html("");
	var form = document.commonCode;
	
	if(form.certiCd.value == ""){
		form.certiCd.value = "10001";
	}
	
	$(".step1").addClass("active ");
	$("#step1").addClass("fadein active show");
	
	jQuery.ajax({
		type:"POST"
			,url:"/rest/wini/certiFieldList.do"
			,dataType:"JSON"
			,data: $("#commonCode").serialize()
			,success:function(data){
				$.each(data, function(idx, item){
					var html = "<tr>";
					//checkbox
					html+="<td>";
					html+="<input type='checkbox' name='orderNo' value='"+item.fieldNm+"' data-fieldNm='"+item.fieldNm+"' data-fieldKorNm='"+item.fieldKorNm+"' onclick='step3checkClick(this);' />";
					html+="</td>";
					
					// 번호
					html+="<td>";
					html+=item.orderNo;
					html+="</td>";
					// 발급처목록
					html+="<td>";
					html+=item.fieldKorNm;
					html+="</td>";
					
					html+="</tr>";
					$("div#step3 table tbody").append(html);
				});
				
				for(var tempKey of Object.keys(tempObject.field)){
					$("input:checkbox[name=orderNo]:checkbox[value='"+tempKey+"']").prop("checked",true);
				}
				
				webSocketConnect();
				
			}
			,error:function(xhr, status, error) {
				
			}
	})
}

function webSocketConnect() {
	
	//var target = "ws://52.79.127.56:8887/websocket";
	var target = "ws://localhost:8887/websocket";
	
	if ("WebSocket" in window) {
		webSocket = new WebSocket(target);
	} else if ("MozWebSocket" in window) {
		webSocket = new MozWebSocket(target);
	} else {
		alert("WebSocket is not supported by this browser.");
		return;
	}
	
	//웹 소켓이 연결되었을 때 호출되는 이벤트
    webSocket.onopen = function(message){
        console.log("Server connect...\n");
    };
    
    //웹 소켓이 닫혔을 때 호출되는 이벤트
    webSocket.onclose = function(message){
    	 console.log("Server Disconnect...\n");
    };
    
    //웹 소켓이 에러가 났을 때 호출되는 이벤트
    webSocket.onerror = function(message){
    	 console.log("error...\n");
    };
    
    //웹 소켓에서 메시지가 날라왔을 때 호출되는 이벤트
    webSocket.onmessage = function(message){
    	 //console.log("Recieve From Server => "+message.data+"\n");
    	console.log("Recieve From Server => "+message.data+"\n");
    	if (message.data == "S") {
    		$("#qr").show();
    	}
    	
    };
}

function onStep4Load(json){
	var form = document.commonCode;
	
	$(".step1").addClass("active ");
	$("#step1").addClass("fadein active show");
	
	var JSONObject = new Object();
	JSONObject.args = json;
	
	send(json);
}

function send(json) {// JSON 문자열을 서버로 전송한다
	webSocket.send(json);
}

//웹소켓 종료
function disConnect(){
    webSocket.close();
}

function step1checkClick(check){
	$("input:checkbox[name=issuedPlaceCd]").prop("checked",false);
	if("" == check.value){
		return;
	}
	$("input:checkbox[name=issuedPlaceCd]:checkbox[value='"+check.value+"']").prop("checked",true);
}

function step2checkClick(check){
	$("input:checkbox[name=certiCd]").prop("checked",false);
	if("" == check.value){
		return;
	}
	$("input:checkbox[name=certiCd]:checkbox[value='"+check.value+"']").prop("checked",true);
}

function step3checkClick(check){
	if($("input:checkbox[name=orderNo]:checked").length ==  $("input:checkbox[name=orderNo]").length ){
		$("#step3AllCheck").prop("checked",true);
	}else{
		$("#step3AllCheck").prop("checked",false);
	}
}

function step3Check(check){
	if($(check).prop("checked")){
		$("input:checkbox[name=orderNo]").prop("checked",true);
		$("input:checkbox[name=orderNo]").each(function(i, item){
			var key = $(item).data("fieldnm");
	    	var value = $(item).data("fieldkornm");
	    	tempObject.field[key] = value;
		});
	}else{
		$("input:checkbox[name=orderNo]").prop("checked",false);
		$("input:checkbox[name=orderNo]").each(function(i, item){
			var key = $(item).data("fieldnm");
			delete tempObject.field[key];
		});
	}
}

function onStep2(){
	if($("input:checkbox[name=issuedPlaceCd]").is(":checked")){
		setDataStep1();
	}else{
		alert("발급처 선택을 선택하지 않았습니다.");
		return true;
	}
	
	onStep2LoadList();
	
	$(".nav-item").removeClass("active");
    $(".step1,.step2").addClass("active");
    $(".tab-pane").removeClass("active fadein fade show");
    $("#step2").addClass("tab-pane fade fadein active show");
}
