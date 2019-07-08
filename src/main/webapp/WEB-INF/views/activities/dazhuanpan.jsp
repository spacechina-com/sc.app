<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>营销活动</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="shortcut icon" href="/favicon.ico">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<%@ include file="../common/headcss.jsp"%>
	<style type="text/css">
		/* 大转盘样式 */
		.banner{display:block;width:95%;margin-left:auto;margin-right:auto;}
		.banner .turnplate{display:block;width:100%;position:relative;}
		.banner .turnplate canvas.item{width:100%;}
		.banner .turnplate img.pointer{position:absolute;width:31.5%;height:42.5%;left:34.6%;top:23%;}
	</style>
</head>
<body>
<div class="page-group">
	<div class="page page-current">
		<div class="content" style="background-image:url(<%=request.getContextPath()%>/file/image?FILENAME=${pda.BACK_PATH});background-size:100% 100%;">
			<div class="row">
			<div class="col-100"><div style="padding:30px;text-align:center;font-size:1rem;color:#FFFFFF;font-weight:bold;">${pda.TOPIC}</div></div>
			</div>
			<div class="row">
			<div class="col-100" style="padding-left:30px;padding-right:30px;">
			<c:forEach var="ap" items="${activitiesprizeitemsData}" varStatus="step">
				<img id="image_${step.index}" src="<%=request.getContextPath()%>/file/image?FILENAME=${ap.IMAGE_PATH}" alt="图片" width="30" height="30" style="display:none; "/>
    		</c:forEach>
			<div class="banner">
				<div class="turnplate" style="background-image:url(<%=request.getContextPath()%>/static/dazhuanpan/images/turnplate-bg.png);background-size:100% 100%;">
					<canvas class="item" id="wheelcanvas" width="422px" height="422px" style="margin-top:5px;"></canvas>
					<img class="pointer" src="<%=request.getContextPath()%>/static/dazhuanpan/images/turnplate-pointer.png"/>
				</div>
			</div>
			</div>
			</div>
			<div class="row">
			<div class="col-100" style="padding-left:20px;padding-right:20px;font-size:0.75rem;color:#FFFFFF;">
				<p>${pda.DESCRIPTION}</p>
			</div>
			</div>
			<div class="row">
			<div class="col-100" style="padding-left:20px;padding-right:20px;">
				<a href="<%=request.getContextPath()%>/activities/listDraw?snid=${snid}&ACTIVITIES_ID=${pda.ACTIVITIES_ID}&MEMBER_ID=${USER_SESSION.MEMBER_ID}" class="external button button-big" style="color:#FFFFFF;border:1px #FFFFFF solid;">查看我的抽奖记录</a>
			</div>
			</div>
		</div>
		<nav class="bar bar-tab"><div style="text-align: center;margin:5px;color:#888888;font-size:14px;">Copyright ©2019 All Rights Reserved</div></nav>
	</div>
</div>
</body>
<%@ include file="../common/headjs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/dazhuanpan/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/dazhuanpan/js/awardRotate.js"></script>
<script type="text/javascript">
var jq = $.noConflict();
var turnplate={
		restaraunts:[],				//大转盘奖品名称
		colors:[],					//大转盘奖品区块对应背景颜色
		outsideRadius:192,			//大转盘外圆的半径
		textRadius:155,				//大转盘奖品位置距离圆心的距离
		insideRadius:68,			//大转盘内圆的半径
		startAngle:0,				//开始角度
		
		bRotate:false				//false:停止;ture:旋转
};

function suialert(text){
	$.alert(text);
}

$(function(){
	//动态添加大转盘的奖品与奖品区域背景颜色
	<c:forEach var="ap" items="${activitiesprizeitemsData}">
		turnplate.restaraunts.push('${ap.DESCRIPTION}');
	</c:forEach>
	
	//turnplate.restaraunts = ["50M免费流量包", "10闪币", "谢谢参与", "5闪币", "10M免费流量包", "20M免费流量包", "20闪币 ", "30M免费流量包", "100M免费流量包", "2闪币"];
	turnplate.colors = ["#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF","#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF","#FFF4D6", "#FFFFFF"];

	
	var rotateTimeOut = function (){
		jq('#wheelcanvas').rotate({
			angle:0,
			animateTo:2160,
			duration:8000,
			callback:function (){
				alert('网络超时，请检查您的网络设置！');
			}
		});
	};

	//旋转转盘 item:奖品位置; txt：提示语;
	var rotateFn = function (item, txt){
		var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length*2));
		if(angles<270){
			angles = 270 - angles; 
		}else{
			angles = 360 - angles + 270;
		}
		jq('#wheelcanvas').stopRotate();
		jq('#wheelcanvas').rotate({
			angle:0,
			animateTo:angles+1800,
			duration:8000,
			callback:function (){
				suialert(txt);
				turnplate.bRotate = !turnplate.bRotate;
			}
		});
	};

	$('.pointer').click(function (){
		if(turnplate.bRotate)return;
		turnplate.bRotate = !turnplate.bRotate;
		
        $.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/activities/createDraw',
	    	data:{
	    		"ACTIVITIES_ID":"${pda.ACTIVITIES_ID}",
	    		"MEMBER_ID":"${USER_SESSION.MEMBER_ID}"
	    	},
	    	async: false,
			dataType:'json',
			cache: false,
			beforeSend:function(){
				
			},
			success: function(data){
				
				if(!data.flag){
					$.alert(data.message,function(){
						
					});
					turnplate.bRotate = !turnplate.bRotate;
				}else{
				
				//获取随机数(奖品个数范围内)
				//var item = rnd(1,turnplate.restaraunts.length);
				var item = rnd(parseInt(data.data.PRIZEITEMS_INDEX)+1,parseInt(data.data.PRIZEITEMS_INDEX)+1)
				//奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
				rotateFn(item, turnplate.restaraunts[item-1]);
				/* switch (item) {
					case 1:
						rotateFn(252, turnplate.restaraunts[0]);
						break;
					case 2:
						rotateFn(216, turnplate.restaraunts[1]);
						break;
					case 3:
						rotateFn(180, turnplate.restaraunts[2]);
						break;
					case 4:
						rotateFn(144, turnplate.restaraunts[3]);
						break;
					case 5:
						rotateFn(108, turnplate.restaraunts[4]);
						break;
					case 6:
						rotateFn(72, turnplate.restaraunts[5]);
						break;
					case 7:
						rotateFn(36, turnplate.restaraunts[6]);
						break;
					case 8:
						rotateFn(360, turnplate.restaraunts[7]);
						break;
					case 9:
						rotateFn(324, turnplate.restaraunts[8]);
						break;
					case 10:
						rotateFn(288, turnplate.restaraunts[9]);
						break;
				} */
				console.log(item);
				}
			},
			error:function(){
				
			}
		});
	});
});

function rnd(n, m){
	var random = Math.floor(Math.random()*(m-n+1)+n);
	return random;
	
}


//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
window.onload=function(){
	drawRouletteWheel();
};

function drawRouletteWheel() {    
  var canvas = document.getElementById("wheelcanvas");    
  if (canvas.getContext) {
	  //根据奖品个数计算圆周角度
	  var arc = Math.PI / (turnplate.restaraunts.length/2);
	  var ctx = canvas.getContext("2d");
	  //在给定矩形内清空一个矩形
	  ctx.clearRect(0,0,422,422);
	  //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
	  ctx.strokeStyle = "#FFBE04";
	  //font 属性设置或返回画布上文本内容的当前字体属性
	  ctx.font = '16px Microsoft YaHei';      
	  for(var i = 0; i < turnplate.restaraunts.length; i++) {       
		  var angle = turnplate.startAngle + i * arc;
		  ctx.fillStyle = turnplate.colors[i];
		  ctx.beginPath();
		  //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
		  ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);    
		  ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
		  ctx.stroke();  
		  ctx.fill();
		  //锁画布(为了保存之前的画布状态)
		  ctx.save();   
		  
		  //----绘制奖品开始----
		  ctx.fillStyle = "#E5302F";
		  var text = turnplate.restaraunts[i];
		  var line_height = 17;
		  //translate方法重新映射画布上的 (0,0) 位置
		  ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);
		  
		  //rotate方法旋转当前的绘图
		  ctx.rotate(angle + arc / 2 + Math.PI / 2);
		  
		  /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
		  if(text.indexOf("M")>0){//流量包
			  var texts = text.split("M");
			  for(var j = 0; j<texts.length; j++){
				  ctx.font = j == 0?'bold 20px Microsoft YaHei':'16px Microsoft YaHei';
				  if(j == 0){
					  ctx.fillText(texts[j]+"M", -ctx.measureText(texts[j]+"M").width / 2, j * line_height);
				  }else{
					  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
				  }
			  }
		  }else if(text.indexOf("M") == -1 && text.length>6){//奖品名称长度超过一定范围 
			  text = text.substring(0,6)+"||"+text.substring(6);
			  var texts = text.split("||");
			  for(var j = 0; j<texts.length; j++){
				  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
			  }
		  }else{
			  //在画布上绘制填色的文本。文本的默认颜色是黑色
			  //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
			  ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
		  }
		  
		
		  var img= document.getElementById("image_"+i);
		  img.onload=function(){  
			  ctx.drawImage(img,-15,10);      
		  };  
		  ctx.drawImage(img,-15,10); 
		 
		  
		  //添加对应图标
		  /**
		  if(text.indexOf("闪币")>0){
			  var img= document.getElementById("shan-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  }; 
			  ctx.drawImage(img,-15,10);  
		  }else if(text.indexOf("谢谢参与")>=0){
			  var img= document.getElementById("sorry-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  };  
			  ctx.drawImage(img,-15,10);  
		  }
		  */
		  //把当前画布返回（调整）到上一个save()状态之前 
		  ctx.restore();
		  //----绘制奖品结束----
	  }     
  } 
}

</script>
</html>