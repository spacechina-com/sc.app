<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>饭饭精选&nbsp;&nbsp;靠谱还实惠</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="shortcut icon" href="/favicon.ico">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<%@ include file="../common/headcss.jsp"%>
	<%@ include file="../common/utiljs.jsp"%>
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
</head>
<body>
<div class="page-group">
	<div class="page page-current">
		<div class="content infinite-scroll" data-distance="30">
			<div class="row">
				<div class="col-100" style="position:relative">
					<img class='card-cover' src="<%=request.getContextPath()%>/static/icon/active.gif" width="100%" height="160px"/>
					<div class="row" style="position:absolute;z-index:999;height:150px;top:5px;width:100%;">
						<div class="col-60" style="padding:20px;">
							<div class="row">
								<div class="col-100" style="margin-top:10px;">
									<span style="font-size:18px;">${USER_SESSION.NICKNAME}</span>
									<c:if test="${USER_SESSION.MEMBERTYPE_ID eq 1 or USER_SESSION.MEMBERTYPE_ID eq 3}">
										<span onclick="javascript:location.href='<%=request.getContextPath()%>/member';" style="margin-left:15px;background:#ffffff;padding-top:2px;padding-bottom:2px;padding-left:7px;padding-right:7px;border-top-left-radius:5px;border-top-right-radius:5px;border-bottom-left-radius:5px;border-bottom-right-radius:5px;">未加入会员</span>
									</c:if>
								</div>
							</div>
							<div class="row" style="margin-top:15px;">
								<div class="col-50 smallText"><div>${ordersNum}</div><div>订单</div></div>
								<div class="col-50 smallText"><div>${cardsNum}</div><div>卡券</div></div>
							</div>
						</div>
						<div class="col-40" style="padding:20px;">
							<img align="middle" style="margin:10px; width:80px;border-radius:50%;" src="${USER_SESSION.PHOTO}"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row module2" style="margin-top:10px;">
				<div class="col-33"><a class="external" href="<%=request.getContextPath()%>/discount"><img src="<%=request.getContextPath()%>/static/icon/home/wz.png"/><p class="mdText">周四五折</p></a></div>
				<div class="col-33"><a class="external" href="<%=request.getContextPath()%>/lottery"><img src="<%=request.getContextPath()%>/static/icon/home/cj.png"/><p class="mdText">免费抽奖</p></a></div>
				<div class="col-33"><a class="external" href="<%=request.getContextPath()%>/shop"><img src="<%=request.getContextPath()%>/static/icon/home/tj.png"/><p class="mdText">好店推荐</p></a></div>
				<!-- <div class="col-25"><a class="external" href="<%=request.getContextPath()%>/seller"><img src="<%=request.getContextPath()%>/static/icon/home/rz.png"/><p>商家入驻</p></a></div> -->
			</div>
			<div style="width:100%;height:10px;background:#F2F2F2;">&nbsp;</div>
			<div class="row TitleBox">
				<div class="col-50 Title yhhd">优惠活动</div>
				<div class="col-50 more_activity" style="padding-top:8px;"><a class="external moreText" href="<%=request.getContextPath()%>/stand">查看更多</a></div>

			</div>
			<div class="row" style="padding:5px;padding-top:0px;">
				<div class="col-100">
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<c:forEach var="var" items="${standData}">
								<div class="swiper-slide"><a class="external"
								<c:if test="${var.GSTATE eq '2'}">href="javascript:$.alert('对不起,该产品已售空');"</c:if><c:if test="${var.GSTATE ne '2'}">href="<%=request.getContextPath()%>/goods/info?GOODS_ID=${var.GOODS_ID}"</c:if>><img width="100%" src="<%=request.getContextPath()%>/file/image?FILENAME=${var.FILEPATH}" onerror="javascript:this.src='<%=request.getContextPath()%>/file/image?FILENAME=${var.FILEPATH}';"/></a></div>
							</c:forEach>
						</div>
						<div class="swiper-pagination"></div>
					</div>
				</div>
			</div>
			<div class="row TitleBox">
				<div class="col-50"><div class="Title">今日热卖</div><div style="font-size: 12px">好店爆款,人手一份</div></div>
				<div class="col-50">
					<div class="content-block" style="margin:0px;margin-top:10px;padding:0 0.1rem">
						<div class="buttons-row">
							<a href="#tab1" class="tab-link active button">推荐</a>
							<a href="#tab2" class="tab-link button">最热</a>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="padding:5px;">
				<div class="col-100">
					<div class="tabs">
						<div id="tab1" class="tab active">
							<div id="goods1">

							</div>
						</div>
						<div id="tab2" class="tab">
							<div id="goods2">

							</div>
						</div>
					</div>
				</div>
			</div>
			<h5>&nbsp;</h5>
			<div style="width:60px;position:fixed;right:2%;top:70%;z-index:999;" onclick="goCustomer();"><img width="55" src="<%=request.getContextPath()%>/static/icon/weixin.png"/></div>
		</div>
		<%@ include file="../common/nav.jsp"%>
	</div>
</div>
</body>
<%@ include file="../common/headjs.jsp"%>
<script type="text/javascript">
	var swiper = new Swiper('.swiper-container', {
		pagination: {
			el: '.swiper-pagination',
			dynamicBullets: true,
		},
		autoplay:true
	});
</script>
<script type="text/javascript">
	search(true);

	var page_currentPage = 1;

	function search(flag){
		$.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/home/search',
			data:{
				"page_currentPage":page_currentPage
			},
			async: false,
			dataType:'json',
			cache: false,
			beforeSend:function(){
				$.showPreloader();
				//$("#shops").html('');
			},
			success: function(data){
				pageData = data.page;
				var list = data.page.data;
				var html = "";
				$.each(list,function(index,value){
					var backmoney = 0;
					if("${USER_SESSION.MEMBERTYPE_ID}"=="1"){
						backmoney = value.MEMBERBACKMONEY;
					}else if("${USER_SESSION.MEMBERTYPE_ID}"=="2"){
						backmoney = value.VIPBACKMONEY;
					}else{
						backmoney = value.BACKMONEY0;
					}
					var hrefitem = "<%=request.getContextPath()%>/goods/info?GOODS_ID="+value.GOODS_ID;
					var flage ="抢购中";
					if(value.STATE == '2'){
						hrefitem = "javascript:$.alert('对不起,该产品已售空');";
						var flage ="售罄";
					}
						html += `
						<div class="card demo-card-header-pic proBox" style="position:relative;">
								<div valign="bottom" class="card-header color-white no-border no-padding">
								<a class="external" href="`+hrefitem+`"><img class='card-cover' height="200" width="100%" src="<%=request.getContextPath()%>/file/image?FILENAME=`+value.FILEPATH+`" alt="" onerror="javascript:this.src='<%=request.getContextPath()%>/file/image?FILENAME=`+value.FILEPATH+`';"></a>
								<div id="goods_time_id_`+value.GOODS_ID+`_zr_left" class="suspend left" style="font-size:11.5px">`+flage+`</div>
							`;
							var today =getDate24Hours();
							var endtimes=value.ENDTIME;
							if(today<endtimes){
								html += `
							<div id="goods_time_id_`+value.GOODS_ID+`_zr_right" class="suspend right" style="font-size:10px">活动倒计时<div id="goods_time_id_s`+value.GOODS_ID+`_zr" class="timeBox" name="timeName"><span>0</span>天<span>0</span><span>0</span><span>0</span></div></div>
							`;
								TimeDown("goods_time_id_s"+value.GOODS_ID+"_zr",value.ENDTIME)
							};

					html += `
						</div>
					<div class="card-content">
							<div class="card-content-inner proText">
							<p class="proBoxText"><span class="proBangIcon">爆</span><span class="proInfo smTitle">`+value.GOODSDESC+`</span></p>
					</div>
					</div>
					<div class="card-footer proBoxText">
							<div class="proBoxTextLeft">
							<span class="priceTitle flexClumnBox"><font class="price">`+value.SELLMONEY+`</font></span>
					<span class="smText" style="padding-top:5px">元</span>
							<span class="delete costPrice flexClumnBox smallText">￥ `+value.ORIGINALMONEY+`</span>
					<span class="flexClumnBox yjBox smText">佣金</span>
							<span class="yjText smallText">`+backmoney+`元</span>
					</div>
					<div class="proBoxTextRight">
							<span class="smallText">已抢:`+value.VIRTUALSELLED+`</span>
					</div>
					</div>
					</div>
    					`;


				})
				if(flag){
					$("#goods1").html(html);
				}else{
					$("#goods1").append(html);
				}


				var list = data.page1.data;
				var html = "";
				$.each(list,function(index,value){
					var backmoney = 0;
					if("${USER_SESSION.MEMBERTYPE_ID}"=="1"){
						backmoney = value.MEMBERBACKMONEY;
					}else if("${USER_SESSION.MEMBERTYPE_ID}"=="2"){
						backmoney = value.VIPBACKMONEY;
					}else{
						backmoney = value.BACKMONEY0;
					}
					var hrefitem = "<%=request.getContextPath()%>/goods/info?GOODS_ID="+value.GOODS_ID;
					var flage ="抢购中";
					if(value.STATE == '2'){
						hrefitem = "javascript:$.alert('对不起,该产品已售空');";
						var flage ="售罄";
					}
					html += `
					<div class="card demo-card-header-pic proBox" style="position:relative;">
								<div valign="bottom" class="card-header color-white no-border no-padding">
								<a class="external" href="`+hrefitem+`"><img class='card-cover' height="200" width="100%" src="<%=request.getContextPath()%>/file/image?FILENAME=`+value.FILEPATH+`" alt="" onerror="javascript:this.src='<%=request.getContextPath()%>/file/image?FILENAME=`+value.FILEPATH+`';"></a>
								<div id="goods_time_id_`+value.GOODS_ID+`_zr_left" class="suspend left" style="font-size:11.5px">`+flage+`</div>
							`;
						var today =getDate24Hours();
						var endtimes=value.ENDTIME;
						if(today<endtimes){
							html += `
								<div id="goods_time_id_`+value.GOODS_ID+`_zr_right" class="suspend right" style="font-size:10px">活动倒计时<div id="goods_time_id_`+value.GOODS_ID+`_tj" class="timeBox"><span>0</span>天<span>0</span><span>0</span><span>0</span></div></div>
							`;
							TimeDown("goods_time_id_"+value.GOODS_ID+"_tj",value.ENDTIME)
						}

					html += `
					</div>
					<div class="card-content">
							<div class="card-content-inner proText">
							<p class="proBoxText"><span class="proBangIcon">爆</span><span class="proInfo smTitle">`+value.GOODSDESC+`</span></p>
					</div>
					</div>
					<div class="card-footer proBoxText">
							<div class="proBoxTextLeft">
							<span class="priceTitle flexClumnBox"><font class="price">`+value.SELLMONEY+`</font></span>
					<span class="smText" style="padding-top:5px">元</span>
							<span class="delete costPrice flexClumnBox smallText">￥ `+value.ORIGINALMONEY+`</span>
					<span class="flexClumnBox yjBox smText">佣金</span>
							<span class="yjText smallText">`+backmoney+`元</span>
					</div>
					<div class="proBoxTextRight">
							<span class="smallText">已抢:`+value.VIRTUALSELLED+`</span>
					</div>
					</div>
					</div>
    					`;

				})
				if(flag){
					$("#goods2").html(html);
				}else{
					$("#goods2").append(html);
				}

				setTimeout(function(){$.hidePreloader();if(flag && '${showNotice}' == 'yes'){searchUnRead();}},1000);
				loading = false;
			},
			error:function(){

			}
		});
	}

</script>
<script type="text/javascript">

	var loading = false;

	$.init();

	$(document).on('infinite',function(){

		if(parseInt(pageData.currentPage) >= parseInt(pageData.totalPage)){
			$.toast("数据已经到底了");
			return;
		}

		page_currentPage++;
		// 如果正在加载，则退出
		if (loading) return;
		// 设置flag
		loading = true;
		// 模拟1s的加载过程
		setTimeout(function() {
			// 重置加载flag
			search(false);

			//容器发生改变,如果是js滚动，需要刷新滚动
			$.refreshScroller();
		}, 500);
	});
</script>
<script type="text/javascript">
	function goCustomer(){
		wx.miniProgram.navigateTo({
			url: '/pages/customer/customer'
		})
	}


	function searchUnRead(){
		$.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/notice/listAllUnRead',
			data:{

			},
			async: false,
			dataType:'json',
			cache: false,
			beforeSend:function(){

			},
			success: function(data){
				showUnRead(data,0);
			},
			error:function(){

			}
		});
	}

	function showUnRead(data,index){
		if(index >= data.length){
			return;
		}
		var notice = data[index];

		$.modal({
			title:  '系统消息通知',
			text: notice.NOTICECONTENT,
			buttons: [
				{
					text: '确定',
					onClick: function() {
						saveNoticeRecord(notice.NOTICE_ID);setTimeout(function(){showUnRead(data,(index+1))},3000);
					}
				},
				{
					text: '取消',
					onClick: function() {
						setTimeout(function(){showUnRead(data,(index+1))},3000);
					}
				}
			]
		});
	}

	function saveNoticeRecord(id){
		$.ajax({
			type: "POST",
			url: '<%=request.getContextPath()%>/notice/saveRecord',
			data:{
				"NOTICE_ID":id
			},
			async: false,
			dataType:'json',
			cache: false,
			beforeSend:function(){

			},
			success: function(data){

			},
			error:function(){

			}
		});
	}
</script>
</html>