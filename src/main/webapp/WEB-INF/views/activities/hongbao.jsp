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
</head>
<body>
<div class="page-group">
	<div class="page page-current">
		<div class="content" style="background-image:url(<%=request.getContextPath()%>/file/image?FILENAME=${pda.BACK_PATH});background-size:100% 100%;">
			<div class="row">
			<div class="col-100"><div style="padding:30px;text-align:center;font-size:1rem;color:#FFFFFF;font-weight:bold;">${pda.TOPIC}</div></div>
			</div>
			<div class="row">
			<div class="col-100" style="padding-top:200px;padding-bottom:60px;padding-left:20px;padding-right:20px;">
				<a href="javascript:createRate();" class="external button button-big button-fill button-danger" style="color:#FFFFFF;">立即抽奖</a>
			</div>
			</div>
			<div class="row">
			<div class="col-100" style="padding-left:20px;padding-right:20px;font-size:0.75rem;color:#FFFFFF;">
				<p>${pda.DESCRIPTION}</p>
			</div>
			</div>
			<div class="row">
			<div class="col-100" style="padding-left:20px;padding-right:20px;">
				<a href="<%=request.getContextPath()%>/activities/listDraw?ACTIVITIES_ID=${pda.ACTIVITIES_ID}&MEMBER_ID=${USER_SESSION.MEMBER_ID}" class="external button button-big" style="color:#FFFFFF;border:1px #FFFFFF solid;">查看我的抽奖记录</a>
			</div>
			</div>
		</div>
		<nav class="bar bar-tab"><div style="text-align: center;margin:5px;color:#888888;font-size:14px;">Copyright ©2019 All Rights Reserved</div></nav>
	</div>
</div>
</body>
<%@ include file="../common/headjs.jsp"%>
<script type="text/javascript">

function createRate(){
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
			}else{
				$.alert(data.data.DESCRIPTION,function(){
					
				});
			
			}
		},
		error:function(){
			
		}
	});
	}
</script>
</html>