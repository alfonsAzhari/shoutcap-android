
var kat = "trucker";
var imgPath = "img_cap/";
var baseballColorJson = [
   {
      "id":"1",
      "code":"#000000",
      "name":"Black",
      "price":"30000"
   },
   {
      "id":"2",
      "code":"#FF333F",
      "name":"Red",
      "price":"30000"
   },
   {
      "id":"8",
      "code":"#FFFFFF",
      "name":"White",
      "price":"30000"
   },
   {
      "id":"9",
      "code":"#04B404",
      "name":"Neon Green",
      "price":"40000"
   },
   {
      "id":"10",
      "code":"#FF0040",
      "name":"Neon Pink",
      "price":"40000"
   },
   {
      "id":"11",
      "code":"#C8FE2E",
      "name":"Neon Yellow",
      "price":"40000"
   },
   {
      "id":"12",
      "code":"#FF3300",
      "name":"Neon Orange",
      "price":"40000"
   },
   {
      "id":"13",
      "code":"#CCFF99",
      "name":"Glow In The Dark",
      "price":"55000"
   },
   {
      "id":"14",
      "code":"#CCCCCC",
      "name":"Reflective",
      "price":"55000"
   },
   {
      "id":"15",
      "code":"#000001",
      "name":"Flock Black",
      "price":"55000"
   },
   {
      "id":"16",
      "code":"#FF333E",
      "name":"Flock Red",
      "price":"55000"
   },
   {
      "id":"17",
      "code":"#FFFFFE",
      "name":"Flock White",
      "price":"55000"
   }
];
var truckerClassicJson = [
   {
      "id":"1",
      "code":"#000000",
      "name":"Black",
      "price":"30000"
   },
   {
      "id":"2",
      "code":"#FF333F",
      "name":"Red",
      "price":"30000"
   },
   {
      "id":"3",
      "code":"#FF6608",
      "name":"Orange",
      "price":"30000"
   },
   {
      "id":"4",
      "code":"#0017E6",
      "name":"Blue",
      "price":"30000"
   },
   {
      "id":"5",
      "code":"#0B0B61",
      "name":"Dark Blue",
      "price":"30000"
   },
   {
      "id":"6",
      "code":"#088A08",
      "name":"Green",
      "price":"30000"
   },
   {
      "id":"7",
      "code":"#540183",
      "name":"Violet",
      "price":"30000"
   }
];
var truckerMixedJson = [
   {
      "id":"1",
      "code":"#000000",
      "name":"Black",
      "price":"30000"
   },
   {
      "id":"2",
      "code":"#FF333F",
      "name":"Red",
      "price":"30000"
   },
   {
      "id":"3",
      "code":"#FF6608",
      "name":"Orange",
      "price":"30000"
   },
   {
      "id":"4",
      "code":"#0017E6",
      "name":"Blue",
      "price":"30000"
   },
   {
      "id":"5",
      "code":"#0B0B61",
      "name":"Dark Blue",
      "price":"30000"
   },
   {
      "id":"6",
      "code":"#088A08",
      "name":"Green",
      "price":"30000"
   }
];
var truckerColorJson = [
   {
      "id":"1",
      "code":"#000000",
      "name":"Black",
      "price":"30000"
   },
   {
      "id":"2",
      "code":"#FF333F",
      "name":"Red",
      "price":"30000"
   },
   {
      "id":"3",
      "code":"#FF6608",
      "name":"Orange",
      "price":"30000"
   },
   {
      "id":"4",
      "code":"#0017E6",
      "name":"Blue",
      "price":"30000"
   },
   {
      "id":"8",
      "code":"#FFFFFF",
      "name":"White",
      "price":"30000"
   },
   {
      "id":"9",
      "code":"#04B404",
      "name":"Neon Green",
      "price":"40000"
   },
   {
      "id":"10",
      "code":"#FF0040",
      "name":"Neon Pink",
      "price":"40000"
   },
   {
      "id":"11",
      "code":"#C8FE2E",
      "name":"Neon Yellow",
      "price":"40000"
   },
   {
      "id":"12",
      "code":"#FF3300",
      "name":"Neon Orange",
      "price":"40000"
   },
   {
      "id":"13",
      "code":"#CCFF99",
      "name":"Glow In The Dark",
      "price":"55000"
   },
   {
      "id":"14",
      "code":"#CCCCCC",
      "name":"Reflective",
      "price":"55000"
   },
   {
      "id":"15",
      "code":"#000001",
      "name":"Flock Black",
      "price":"55000"
   },
   {
      "id":"16",
      "code":"#FF333E",
      "name":"Flock Red",
      "price":"55000"
   },
   {
      "id":"17",
      "code":"#FFFFFE",
      "name":"Flock White",
      "price":"55000"
   }
];

var truckerList = {
	"1": "Classic",
	"2": "Mixed",
	"3": "Color"
}

function populateListColor(index, object) {
	$('.colorFont').append(
		$("<option></option>").attr("value", object.id).text(object.name)
	);
}

function drawTextToImage() {
	useCORS = true;
	var text = document.getElementById('tx_shout');
	var canvas = document.getElementById("myCanvas");
	var image = document.getElementById("img_createshoutcap");
	var textShout = document.getElementById("div_measure_text");
	var fontSizeShout = parseFloat($('#tx_shout').css('font-size'));
	var fontName = $('.canvas-textarea').css('font-family');
	var fontColor = $('.canvas-textarea').css('color');

	//console.log(image.width);
	//console.log(image.height);

	canvas.width = image.width;
	canvas.height = image.height;

	var context = canvas.getContext("2d");
	context.font = fontSizeShout + "px " + fontName;
	context.fillStyle = fontColor;
	context.drawImage(image, 0, 0, canvas.width, canvas.height);
		context.font = fontSizeShout + "px Calibri";
		context.drawImage(image, 0, 0, canvas.width, canvas.height);

	//console.log(canvas.width);
	//console.log(canvas.height);

	var shout = text.value.split(/\r|\r\n|\n/);
	switch(shout.length) {
		case 1:
		var y = 0.2 * fontSizeShout;
		context.fillText(shout[0], (canvas.width - context.measureText(shout[0]).width) / 2, y + canvas.height / 2);
		break;

		case 2:
		var y = 0.1 * fontSizeShout;
		context.fillText(shout[0], (canvas.width - context.measureText(shout[0]).width) / 2, y + canvas.height / 2 - fontSizeShout/2);
		context.fillText(shout[1], (canvas.width - context.measureText(shout[1]).width) / 2, y + canvas.height / 2 + fontSizeShout/2);
		break;

		case 3:
		context.fillText(shout[0], (canvas.width - context.measureText(shout[0]).width) / 2, canvas.height / 2 - fontSizeShout);
		context.fillText(shout[1], (canvas.width - context.measureText(shout[1]).width) / 2, canvas.height / 2);
		context.fillText(shout[2], (canvas.width - context.measureText(shout[2]).width) / 2, canvas.height / 2 + fontSizeShout);
		break;

	}
}

function changeFont(font) {
	$('#tx_shout').css('font-family', font);
}

function changeFontColor(color) {
	$('#tx_shout').css('color', color);
}

function changeCap(imgSrc) {
	$('#img_createshoutcap').attr("src", imgPath + imgSrc);
		var dataImage = canvas.toDataURL('image/png');
		sendCapData("wa",1,1,1,"wawa","wawd",1231,dataImage);
}

function sendCapData (text, model, type, size, font, color, price, dataImage){
	var image = dataImage.split(",");
	var capObj = "{\"cap\" :" +
    				"[" +
    					"{" +
    						"\"text\" : \"" + text + "\"," +
                            "\"model\" : \"" + model + "\"," +
                            "\"type\" : \"" + type + "\"," +
                            "\"size\" : \"" + size + "\"," +
                            "\"font\" : \"" + font + "\"," +
                            "\"color\" : \"" + color + "\"," +
                            "\"price\" : \"" + price + "\"," +
                            "\"image\" : \"" + image[1] + "\"" +
                        "}" +
                    "]" +
                  "}";

    Android.capData(capObj);
}

function resizetext(textarea,h){
	var fontsize=parseFloat($(textarea).css('font-size'),10);
	$(textarea).css('height',h+'px')
	constheight=h;
	scrollHeight = $(textarea)[0].scrollHeight;
	scrollWidth = $(textarea)[0].scrollWidth;
	clientHeight = $(textarea)[0].clientHeight;
	clientWidth = $(textarea)[0].clientWidth;
	if((scrollHeight>clientHeight) || (scrollWidth>clientWidth)){
		do{fontsize = fontsize-1;$(textarea).css('font-size',fontsize+'px');scrollHeight = $(textarea)[0].scrollHeight;scrollWidth = $(textarea)[0].scrollWidth;
	}while((scrollHeight>clientHeight) || (scrollWidth>clientWidth));
}
if((scrollHeight<=clientHeight) && (scrollWidth<=clientWidth)){
	do{fontsize = fontsize+1;$(textarea).css('font-size',fontsize+'px');scrollHeight = $(textarea)[0].scrollHeight;scrollWidth = $(textarea)[0].scrollWidth;
}while((scrollHeight<=clientHeight) && (scrollWidth<=clientWidth));
}
fontsize = fontsize-1;
$(textarea).css('font-size',fontsize+'px');
scrollHeight = $(textarea)[0].scrollHeight;
scrollWidth = $(textarea)[0].scrollWidth;
height=constheight;
while((scrollHeight<=height)){
	height = height-1;$(textarea).css('height',height+'px');scrollHeight = $(textarea)[0].scrollHeight;
}
height = height+1;
atas = constheight-height;
atas = atas/2;
$(textarea).css('height',height+'px');
$(textarea).css('top',atas+'px');
$("#create_fontsize").val(fontsize+'px');
$("#create_areaheight").val(height+'px');
$("#create_areatop").val(atas+'px');
}
function menucreate_opacity(){
	$("#menu_select_main_kategori").hide();
	$("#menu_select_kategori").hide();
	$("#sliderClassic").hide();
	$("#sliderColor").hide();
	$("#sliderMixed").hide();
	$("#sliderBaseball").hide();
	$("#menu_select_model").hide();
	$("#menu_select_size").hide();
	$("#menu_select_fontfamily").hide();
	$("#menu_select_fontcolor_Classic").hide();
	$("#menu_select_fontcolor_Color").hide();
	$("#menu_select_fontcolor_Mixed").hide();
	$("#menu_select_fontcolor_Baseball").hide();
	$("#menu_select_option").hide();
	$("#menu_select_suggestions").hide();
	$("#div_menucreate_model").css("opacity","1");
	$("#div_menucreate_size").css("opacity","1");
	$("#div_menucreate_fontfamily").css("opacity","1");
	$("#div_menucreate_fontcolor").css("opacity","1");
	$("#div_menucreate_option").css("opacity","1");
	$("#div_menucreate_suggestions").css("opacity","1");
	$("#div_menucreate_share").css("opacity","1");
	$("#div_menucreate_preview").css("opacity","1");
}
function showcaption(){
	$("#img_ch_option_caption").show();
	$("#div_shoutareacaption").show();
	$("#option_caption").val("y");
	var constheight = parseFloat($("#div_shoutarea").css("height"),10);
	var fontfamily = $("#tx_shout").css("font-family");
	var fontcolor = $("#tx_shout").css("color");
	var fonttransform = $("#tx_shout").css("transform");
	var newheight = (constheight * 83) / 100;
	newheight = Math.floor(newheight);
	$("#createshoutheight").val(newheight+"px");
	$("#div_shoutarea").css("height",newheight+"px");
	$("#tx_shout").css("height",newheight+"px");
	resizetext("#tx_shout",newheight);
	$("#tx_shoutcaption").css("font-family",fontfamily);
	$("#tx_shoutcaption").css("color",fontcolor);
	//resizetext("#tx_shoutcaption",24);
}
function hidecaption(){
	$("#img_ch_option_caption").hide();
	$("#option_caption").val("n");
	if($("#div_shoutareacaption").is(":visible")) {
		var constheight = parseFloat($("#div_shoutarea").css("height"),10);
		var newheight = (constheight * 100) / 83;
		newheight = Math.ceil(newheight);
		$("#createshoutheight").val(newheight+"px");
		$("#div_shoutarea").css("height",newheight+"px");
		$("#tx_shout").css("height",newheight+"px");
		resizetext("#tx_shout",newheight);
	}
	$("#div_shoutareacaption").hide();
}
function transformshout(textarea,csstransform){
	$(textarea).css("transform",csstransform);
	$(textarea).css("-moz-transform",csstransform);
	$(textarea).css("-webkit-transform",csstransform);
	$(textarea).css("-o-transform",csstransform);
	$(textarea).css("-ms-transform",csstransform);
}
function showmirror(){
	$("#img_ch_option_mirror").show();
	$("#option_mirror").val("y");
	scaletransform = "scale(-1,1)";
	transformshout("#tx_shout",scaletransform);
}
function hidemirror(){
	$("#img_ch_option_mirror").hide();
	$("#option_mirror").val("n");
	scaletransform = "scale(1,1)";
	transformshout("#tx_shout",scaletransform);
}
function showfliph(){
	$("#img_ch_option_fliph").show();
	$("#option_fliph").val("y");
}
function hidefliph(){
	$("#img_ch_option_fliph").hide();
	$("#option_fliph").val("n");
}
$(document).ready(function(){
	$("#div_shoutareacaption").hide();
	var option_caption = $("#option_caption").val();
	var option_mirror = $("#option_mirror").val();
	var option_fliph = $("#option_fliph").val();
	var constheight = parseFloat($("#div_shoutarea").css("height"),10);
	if(option_caption=="y"){
		showcaption();
	}else{
		hidecaption();
	}
	if(option_mirror=="y"){
		showmirror();
	}else{
		hidemirror();
	}
	hidefliph();
	var delayshow = 100;
	var delayhide = 0;
	var minimalfontsize=20;
	var shoutareawidth = parseFloat($("#div_shoutcap").css("width"),10);
	$("#shoutareawidth").val(shoutareawidth);
	menucreate_opacity();
	//SELECT MENU MODEL
	$("#div_menucreate_model").click(function(){
		if($("#menu_select_main_kategori").is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$(this).css("opacity","0.5");
			$("#menu_select_main_kategori").slideDown();
		}
	});
	$("#menu_select_main_kategori_trucker").click(function(){
		if($("#menu_select_kategori").is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$("#div_menucreate_model").css("opacity","0.5");
			$("#menu_select_kategori").slideDown();
		}
	});
	//SELECT KATEGORI
	$(".menu_select_kategories").click(function(){
		menucreate_opacity();
		$("#div_menucreate_model").css("opacity","0.5");
		var prev_kategori=$("#create_kategori").val();
		var kategori = $(this).attr("kategori");
		if(prev_kategori != kategori){
			$("#div_menucreate_judul_fontcolor").html("4");
			$("#div_menucreate_desk_fontcolor").html("SELECT FONT COLOR");
			$("#create_fontcolor").val("");
			$("#tx_shout").css("color","#000000");
		}
		$("#create_kategori").val(kategori);
		$("#menu_select_kategori").hide();
		$("#menu_select_model").slideDown();
		$("#slider"+kategori).slideDown();
		Slider = $("#slider"+kategori).Swipe({
			continuous: true,
			callback:function(c,d){
						//posisi dihitung dari 0
						var b=Slider.getPos();
					}
				}).data("Swipe");
	});
	$("#nextmodel").click(function(){
		Slider.next();
	});
	$("#prevmodel").click(function(){
		Slider.prev();
	});
	//SUB SELECT MODEL
	$(".sub_select_model").click(function(){
		var model = $(this).attr("model");
		var image = $(this).attr("image");
		var capcolor = $(this).attr("capcolor");
		$("#create_model").val(model);
		$("#create_capcolor").val(capcolor);
		$("#img_createshoutcap").attr("src",image);
		$("#menu_select_model").hide();
		$("#div_menucreate_judul_model").html("model:");
		kategori=$("#create_kategori").val();
		if(kategori != "Baseball"){
			$("#div_menucreate_desk_model").html("trucker "+kategori);
		}else{
			$("#div_menucreate_desk_model").html(kategori);
		}
		$("#span_capcolor").html(capcolor);
		$("#div_menucreate_model").css("opacity","1");
	});
	//SELECT MENU SIZE
	$("#div_menucreate_size").click(function(){
		if($("#menu_select_size").is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$(this).css("opacity","0.5");
			$("#menu_select_size").slideDown();
		}
	});
	//SELECT SUB MENU SIZE
	$(".sub_menu_select_size").click(function(){
		sz=$(this).attr("sz");
		$("#create_size").val(sz);
		$("#menu_select_size").hide();
		$("#div_menucreate_judul_size").html("size:");
		$("#div_menucreate_desk_size").html(sz);
		$("#div_menucreate_size").css("opacity","1");
	});
	//SELECT MENU FONT FAMILY
	$("#div_menucreate_fontfamily").click(function(){
		if($("#menu_select_fontfamily").is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$(this).css("opacity","0.5");
			$("#menu_select_fontfamily").slideDown();
		}
	});
	//SELECT SUB MENU FONT FAMILY
	$(".sub_select_fontfamily").click(function(){
		ft=$(this).attr("ft");
		$("#create_fontfamily").val(ft);
		$("#menu_select_fontfamily").hide();
		$("#tx_shout").css("font-family",ft);
		$("#div_menucreate_judul_fontfamily").html("font type:");
		$("#div_menucreate_desk_fontfamily").html(ft);
		var constheight = parseFloat($("#div_shoutarea").css("height"),10)
		resizetext("#tx_shout",constheight);
		$("#tx_shoutcaption").css("font-family",ft);
		$("#div_menucreate_fontfamily").css("opacity","1");
	});
	//SELECT FONT COLOR
	$("#div_menucreate_fontcolor").click(function(){
		kategori=$("#create_kategori").val();
		if(kategori==""){
			kategori="Mixed";
		}
		if($("#menu_select_fontcolor_"+kategori).is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$(this).css("opacity","0.5");
			$("#menu_select_fontcolor_"+kategori).slideDown();
		}
	});
	//SELECT SUB FONT COLOR
	$(".sub_select_fontcolor").click(function(){
		fc=$(this).attr("fc");
		fcname=$(this).attr("fcname");
		$("#create_fontcolor").val(fc);
		kategori=$("#create_kategori").val();
		if(kategori==""){
			kategori="Mixed";
		}
		$("#menu_select_fontcolor_"+kategori).hide();
		$("#div_menucreate_judul_fontcolor").html("font color:");
		$("#div_menucreate_desk_fontcolor").html(fcname);
		$("#tx_shout").css("color",fc);
		$("#tx_shoutcaption").css("color",fc);
		$("#div_menucreate_fontcolor").css("opacity","1");
	});
	//SELECT OPTION
	$("#div_menucreate_option").click(function(){
		if($("#menu_select_option").is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$(this).css("opacity","0.5");
			$("#menu_select_option").slideDown();
		}
	});
	//SELECT SUGGESTIONS
	$("#div_menucreate_suggestions").click(function(){
		if($("#menu_select_suggestions").is(":visible")){
			menucreate_opacity();
		}else{
			menucreate_opacity();
			$(this).css("opacity","0.5");
			$("#menu_select_suggestions").slideDown();
		}
	});
	//SELECT SUB SUGGESTIONS
	$(".sub_select_suggestions").click(function(){
		$("#div_typehere").hide();
		sg=$(this).attr("sg");
		$("#menu_select_suggestions").hide();
		$("#tx_shout").val(sg);
		var constheight = parseFloat($("#div_shoutarea").css("height"),10)
		resizetext("#tx_shout",constheight);
		$("#div_menucreate_suggestions").css("opacity","1");
	});
	//TYPE SHOUT
	$("#tx_shout").live("keyup", function(event){
		/*
		var constheight = parseFloat($("#div_shoutarea").css("height"),10)
		resizetext("#tx_shout",constheight);
		*/
		$("#div_typehere").hide();
		var constheight = parseFloat($("#div_shoutarea").css("height"),10)
		shouttmp = $("#tx_shout").val();
		lines = shouttmp.split(/\r|\r\n|\n/);
		jum_baris = lines.length;
		if(jum_baris>3){
			$("#tx_shout").val(shout);
		}
		resizetext("#tx_shout",constheight);
		fontsize = parseFloat($("#tx_shout").css("font-size"),10)
		if(fontsize < minimalfontsize){
			$("#tx_shout").val(shout);
			resizetext("#tx_shout",constheight);
		}
		shout = $("#tx_shout").val();
	});
	/* TYPE SHOUT GROUP CAPTION*/
	$("#tx_shoutcaption").live("keyup", function(event){
		$("#div_typeherecaption").hide();
		shouttmp = $("#tx_shoutcaption").val();
		lines = shouttmp.split(/\r|\r\n|\n/);
		jum_baris = lines.length;
		jum_text = shouttmp.length;
		scrollWidth = $("#tx_shoutcaption")[0].scrollWidth;
		clientWidth = $("#tx_shoutcaption")[0].clientWidth;
		if((scrollWidth > clientWidth) || (jum_baris>1) ){
			$("#tx_shoutcaption").val(shout);
		}
		shout = $("#tx_shoutcaption").val();
	});
	/* KLIK MINUS */
	$("#img_resizeminus").click(function(){
		var constheight = parseFloat($("#div_shoutarea").css("height"),10)
		var shout = $("#tx_shout").val();
		shout = shout.trim();
		var fontsize=parseFloat($("#tx_shout").css('font-size'),10);
		fontsize = fontsize - 2;
		if((shout != "") && (fontsize>=minimalfontsize)){
			$("#tx_shout").css('font-size',fontsize+'px');
			var scrollHeight = $("#tx_shout")[0].scrollHeight;
			var height=parseFloat($("#tx_shout").css('height'),10);
			while((scrollHeight<=height)){
				height = height-1;
				$("#tx_shout").css('height',height+'px');
				scrollHeight = $("#tx_shout")[0].scrollHeight;
				height=parseFloat($("#tx_shout").css('height'),10);
			}
			height = height+1;
			atas = constheight-height;
			atas = atas/2;
			$("#tx_shout").css('height',height+'px');
			$("#tx_shout").css('top',atas+'px');
			$("#create_fontsize").val(fontsize+'px');
			$("#create_areaheight").val(height+'px');
			$("#create_areatop").val(atas+'px');
		}
	});
	/* KLIK PLUS */
	$("#img_resizeplus").click(function(){
		var constheight = parseFloat($("#div_shoutarea").css("height"),10)
		resizetext("#tx_shout",constheight);
	});
	$("#formaction").submit(function(){
		var kategori = $("#create_kategori").val();
		var model = $("#create_model").val();
		var size = $("#create_size").val();
		var fontfamily = $("#create_fontfamily").val();
		var fontcolor = $("#create_fontcolor").val();
		if( ( kategori=="" ) || ( model=="" ) || ( size=="" ) || ( fontfamily == "" ) || ( fontcolor == "" ) ){
			alert("Silahkan pilih dulu model, size, font type dan font color");
			return false;
		}else{
			return true;
		}
	});
	// FITUR OPTION CAPTION GROUP
	/*
	$("#ch_option_caption").change(function() {
        if($(this).is(":checked")) {
			showcaption();
        }else{
			hidecaption();
		}
    });
*/
$("#div_option_caption").click(function() {
	if($("#img_ch_option_caption").is(":visible")) {
		hidecaption();
	}else{
		showcaption();
	}
});
$("#div_option_mirror").click(function() {
	if($("#img_ch_option_mirror").is(":visible")) {
		hidemirror();
	}else{
		showmirror();
	}
});
$("#div_close_feature").click(function() {
	menucreate_opacity();
});
});
