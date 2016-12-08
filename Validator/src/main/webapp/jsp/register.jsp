<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>请注册</title>
</head>
<body>

<div style="margin:0 auto; padding-top: 100px;" align="center">
    <form action="doRegister.html" method="post">
        <p>
            ${ERR_userName }<br>
            <span>用户名：</span><input type="text" name="userName">
        </p>
        <p>
            ${ERR_password }<br>
            <span>密    码：</span><input type="password" name="password">
        </p>
        <p>
            ${ERR_realName }<br>
            <span>真实姓名：</span><input type="text" name="realName">
        </p>
        <p>
            ${ERR_email }<br>
            <span>邮    箱：</span><input type="text" name="email">
        </p>
        <p>
            ${ERR_age }<br>
            <span>年    龄：</span><input type="text" name="age">
        </p>
        <p>
            <input type="submit" value="注册">
        </p>
    </form>
</div>

</body>
</html>