<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="search">
<form action="#" method="POST">
    社員番号：<input type="text" name="code" value="${code}">&nbsp;
    従業員名：<input type="text" name="name" value="${name}">
    フォロー中
    <c:choose>
        <c:when test="${status == 1}">
            <input type="checkbox" name="status" value="1" checked="checked">
        </c:when>
        <c:otherwise>
            <input type="checkbox" name="status" value="1">
        </c:otherwise>
    </c:choose>
    未フォロー
    <c:choose>
        <c:when test="${status == 0}">
            <input type="checkbox" name="status" value="0" checked="checked">
        </c:when>
        <c:otherwise>
            <input type="checkbox" name="status" value="0">
        </c:otherwise>
    </c:choose>
    <input type="submit" value="検索" id="search_button">
</form>
</div>