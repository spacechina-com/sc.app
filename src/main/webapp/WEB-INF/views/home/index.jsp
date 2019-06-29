<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>云码品牌主页</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="shortcut icon" href="/favicon.ico">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<%@ include file="../common/headcss.jsp"%>
</head>
<body>
<div class="page-group">
	<div class="page page-current">
		<div class="content">
			<div class="swiper-container" data-space-between='10' data-autoplay='3000'>
			    <div class="swiper-wrapper">
			      <div class="swiper-slide"><img style="width:100%;max-height:200px;" src="<%=request.getContextPath()%>/static/images/swiper1.jpg" alt=""></div>
			      <div class="swiper-slide"><img style="width:100%;max-height:200px;" src="<%=request.getContextPath()%>/static/images/swiper2.jpg" alt=""></div>
			      <div class="swiper-slide"><img style="width:100%;max-height:200px;" src="<%=request.getContextPath()%>/static/images/swiper3.jpg" alt=""></div>
			      <div class="swiper-slide"><img style="width:100%;max-height:200px;" src="<%=request.getContextPath()%>/static/images/swiper4.jpg" alt=""></div>
			    </div>
			    <div class="swiper-pagination"></div>
			</div>
		<div class="row" style="padding:10px;padding-top:0px;">
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">扫码验证</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image1.png" alt=""></div></div>
			<div class="col-33"><a class="external" href="<%=request.getContextPath()%>/activities?COMPANY_ID=${pd.COMPANY_ID}&GOODS_ID=${pd.GOODS_ID}&BATCH_ID=${pd.BATCH_ID}"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">营销活动</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image2.png" alt=""></div></a></div>
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">积分商城</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image3.png" alt=""></div></div>
		</div>
		<div class="row" style="padding:10px;padding-top:0px;">
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">资讯公告</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image4.png" alt=""></div></div>
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">会员中心</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image5.png" alt=""></div></div>
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">商品溯源</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image6.png" alt=""></div></div>
		</div>
		<div class="row" style="padding:10px;padding-top:0px;">
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">企业官网</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image7.png" alt=""></div></div>
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">商品中心</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image8.png" alt=""></div></div>
			<div class="col-33"><div style="position:relative;width:100%;"><span style="position:absolute;bottom:20px;text-align: center;color: #FFFFFF;width:100%;">发展历程</span><img style="width:100%;" src="<%=request.getContextPath()%>/static/images/image9.png" alt=""></div></div>
		</div>
		</div>
		<nav class="bar bar-tab"><div style="text-align: center;margin:5px;color:#888888;font-size:14px;">Copyright ©2019 All Rights Reserved</div></nav>
	</div>
</div>
</body>
<%@ include file="../common/headjs.jsp"%>
<script type="text/javascript">

$.init();

</script>
</html>