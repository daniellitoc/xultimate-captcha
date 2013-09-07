<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	function loadAudio() {
		document.getElementById("audioCaptcha").src = "<c:url value="/audio.wav" />";
		document.getElementById("audioSupport").innerHTML = document.createElement('audio').canPlayType("audio/wav");
	}
</script>
</head>
<body>
	<audio style="display:none" controls autoplay id="audioCaptcha"></audio>
	<bgsound loop="1" src="<c:url value="/audio.wav" />" autostart="true">
	<span id="audioSupport"></span>
	<form action="<c:url value="/admin/login/submit" />" method="post">
		<a href="javascript:loadAudio()">here</a> <img src="<c:url value="/captcha.png" />"> <input type='text' name='j_captcha_paran_name' value=''>
		
		<input type="submit"> 
	</form>
</body>
</html>