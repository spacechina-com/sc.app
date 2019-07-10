<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>营销活动初始化</title>
</head>
<body>
<script type="text/javascript">
	location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=${APPID}&redirect_uri=${RETURN}&response_type=code&scope=snsapi_userinfo&state=park#wechat_redirect';
</script>
</body>
</html>