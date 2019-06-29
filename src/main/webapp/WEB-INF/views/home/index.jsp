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
		      <div class="swiper-slide"><img height="300" src="<%=request.getContextPath()%>/static/images/swiper1.jpg" alt=""></div>
		      <div class="swiper-slide"><img height="300" src="<%=request.getContextPath()%>/static/images/swiper2.jpg" alt=""></div>
		      <div class="swiper-slide"><img height="300" src="<%=request.getContextPath()%>/static/images/swiper3.jpg" alt=""></div>
		    </div>
		    <div class="swiper-pagination"></div>
		  </div>
		</div>
		<nav class="bar bar-tab">123</nav>
	</div>
</div>
</body>
<%@ include file="../common/headjs.jsp"%>
<script type="text/javascript">

$.init();

</script>
</html>