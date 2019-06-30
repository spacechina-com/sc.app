<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/top.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>我的抽奖记录</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<link rel="shortcut icon" href="/favicon.ico">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<%@ include file="../common/headcss.jsp"%>
</head>
<body>
<div class="page-group">
	<div class="page page-current" id="listdata">
		<div class="content">
		<c:forEach var="var" items="${drawuserData}">
		<div class="card">
		    <div class="card-content">
		      <div class="list-block media-list">
		        <ul>
		          <li class="item-content">
		          	<div class="item-media">
		              <img src="http://gqianniu.alicdn.com/bao/uploaded/i4//tfscom/i3/TB10LfcHFXXXXXKXpXXXXXXXXXX_!!0-item_pic.jpg_250x250q60.jpg" width="44">
		            </div>
		            <div class="item-inner">
		              <div class="item-title-row">
		                <div class="item-title">${var.DESCRIPTION}</div>
		              </div>
		            </div>
		          </li>
		        </ul>
		      </div>
		    </div>
		    <div class="card-footer">
		      <span>${var.CREATE_TIME}</span>
		      <span>
		      	<div style="border:1px #888888 solid;border-radius:5px;padding:3px;">
					<c:choose>
						<c:when test="${var.STATE eq 1}">已处理</c:when>
						<c:when test="${var.STATE eq 0}">未处理</c:when>
						<c:otherwise>未知</c:otherwise>
					</c:choose>
				</div>
			  </span>
		    </div>
		  </div>
			          </c:forEach> 	  
		  <div class="content-block">
    <div class="row">
      <div class="col-50"><a href="<%=request.getContextPath()%>/activities?COMPANY_ID=${pda.COMPANY_ID}&GOODS_ID=${pda.GOODS_ID}&BATCH_ID=${pda.BATCH_ID}" class="external button button-big button-fill">返回抽奖</a></div>
      <div class="col-50"><a href="#transportposition" class="button button-big button-fill">地址管理</a></div>
    </div>
  </div>
		  
		</div>
		<nav class="bar bar-tab"><div style="text-align: center;margin:5px;color:#888888;font-size:14px;">Copyright ©2019 All Rights Reserved</div></nav>
	</div>
	<div class="page" id='transportposition'>
	<div class="content">
	<div class="list-block" style="margin:0 0;">
    <ul>
      <!-- Text inputs -->
      <li>
        <div class="item-content">
          <div class="item-media"><i class="icon icon-form-name"></i></div>
          <div class="item-inner">
            <div class="item-title label">姓名</div>
            <div class="item-input">
              <input id="REALNAME" type="text" placeholder="真实姓名" value="${pdar.REALNAME}">
            </div>
          </div>
        </div>
      </li>
      <li>
        <div class="item-content">
          <div class="item-media"><i class="icon icon-form-name"></i></div>
          <div class="item-inner">
            <div class="item-title label">电话</div>
            <div class="item-input">
              <input id="PHONE" type="text" placeholder="联系方式" value="${pdar.PHONE}">
            </div>
          </div>
        </div>
      </li>
      <li class="align-top">
        <div class="item-content">
          <div class="item-media"><i class="icon icon-form-comment"></i></div>
          <div class="item-inner">
            <div class="item-title label">快递住址</div>
            <div class="item-input">
              <textarea id="ADDRESS" placeholder="这里输入完整住址">${pdar.DESCRIPTION}</textarea>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
  <div class="content-block">
    <div class="row">
      <div class="col-50"><a id="backButton" href="#listdata" class="button button-big button-fill button-danger">取消</a></div>
      <div class="col-50"><a href="javascript:transportposition();" class="button button-big button-fill button-success">提交</a></div>
    </div>
  </div>
  </div>
  <nav class="bar bar-tab"><div style="text-align: center;margin:5px;color:#888888;font-size:14px;">Copyright ©2019 All Rights Reserved</div></nav>
	</div>
</div>
</body>
<%@ include file="../common/headjs.jsp"%>
<script type="text/javascript">
	function transportposition(){
		if($("#REALNAME").val()==""){
			$.alert("真实姓名不许为空")
			return;
		}
		if($("#PHONE").val()==""){
			$.alert("联系方式不许为空")
			return;
		}
		if($("#ADDRESS").val()==""){
			$.alert("快递地址不许为空")
			return;
		}
		
		 $.ajax({
				type: "POST",
				url: '<%=request.getContextPath()%>/activities/manageAddress',
		    	data:{
		    		"ADDRESS_ID":"${pdar.ADDRESS_ID}",
		    		"REALNAME":$("#REALNAME").val(),
		    		"PHONE":$("#PHONE").val(),
		    		"DESCRIPTION":$("#ADDRESS").val()
		    	},
		    	async: false,
				dataType:'json',
				cache: false,
				beforeSend:function(){
					
				},
				success: function(data){
					if(data.flag){
						$.alert('保存地址成功',function(){
							$("#backButton").click();
						})
					}
				},
				error:function(){
					
				}
			});
	}
</script>
</html>