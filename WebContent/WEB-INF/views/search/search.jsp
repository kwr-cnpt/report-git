<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="search">
<form action="#" method="POST">
    社員番号：<input type="text" name="code" value="${code}">&nbsp;
    従業員名：<input type="text" name="name" value="${name}">
    <input type="submit" value="検索" id="search_button">
</form>
</div>