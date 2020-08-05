<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="search">
<form action="#" method="POST">
    期間：<input type="date" name="startDate" value="${startDate}">〜<input type="date" name="endDate" value="${endDate}"><br />
    従業員名：<input type="text" name="name" value="${name}">&nbsp;
    内容：<input type="text" name="content" value="${content}">
    <input type="submit" value="検索" id="search_button">

</form>
</div>