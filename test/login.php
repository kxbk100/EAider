<?php
	define("LOGIN_SUCCESS", 0);		//登录成功
	define("NOT_EXIST", 1);		//账号不存在
	define("PASSWORD_ERROR", 2);		//密码错误
	
	header("Content-Type:text/html;charset=utf-8");
	$studentId = addslashes($_POST['studentId']);
	$password = addslashes($_POST['password']);
	
	$dsn = "mysql:host=localhost; dbname=stuapp_data";
	$opt = array("PDO::MYSQL_ATTR_INIT_COMMAND=>'set names utf8'");
	$pdo = new pdo($dsn,"root","root",$opt);
	
	if($result = $pdo->query("select * from stu_info where stu_id='{$studentId}'")->fetch()){
		if($result['password'] === $password){
			echo LOGIN_SUCCESS;
		}else{
			echo PASSWORD_ERROR;
		}
	}else{
		echo NOT_EXIST;
	}
			
?>