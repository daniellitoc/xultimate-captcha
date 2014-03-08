<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>输入验证码</title>
<script>
	function loadAudio() {
		document.getElementById("audioCaptcha").src = "/xultimate-captcha/audio.wav?sessionId=${cookie['cookie2'].value}";
		document.getElementById("audioSupport").innerHTML = document.createElement('audio').canPlayType("audio/wav");
		document.getElementById("type").value = "audio";
	}
	
	function loadImage() {
		document.getElementById("imageCaptcha").src = "/xultimate-captcha/captcha.jpg?sessionId=${cookie['cookie2'].value}";
		document.getElementById("type").value = "image";
	}
</script>
</head>
<body>
	<audio style="display:none" controls autoplay id="audioCaptcha"></audio> 
	<span id="audioSupport"></span>
	<form action="/xultimate-captcha/admin/login/submit" method="post">
		<input type="hidden" id="type" name="type" value="image" >
		<a href="javascript:loadAudio()">here</a> <img onclick="loadImage()" id="imageCaptcha" src="/xultimate-captcha/captcha.jpg?sessionId=${cookie['cookie2'].value}">
		<input type='text' name='captchaText' value=''>
		<input type="submit"> 
	</form>
</body>
</html>