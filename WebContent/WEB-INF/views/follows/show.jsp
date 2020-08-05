<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
            <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>フォロー管理</h2>

        <form method="GET" action="<c:url value='/search/employee' />">
            <c:import url="search.jsp" />
        </form>

        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>状態</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    （削除済み）
                                </c:when>
                                <c:when test="${followed.contains(employee)}">
                                フォロー中
                                </c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}"></c:when>
                                <c:when test="${followed.contains(employee)}">
                                    <form method="POST" action="${pageContext.request.contextPath}/follows/destroy?id=${employee.id}&rd=2">
                                        <button type="submit">フォロー解除</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form method="POST" action="${pageContext.request.contextPath}/follows/create?id=${employee.id}&rd=2">
                                       <button type="submit">フォローする</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${employees_count} 件）<br />
            <c:forEach var="i" begin="1" end="${(((employees_count - 1) / 15) + 1)}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/follows/show?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/follows/index' />">フォロー中の日報一覧へ戻る</a></p>

    </c:param>
</c:import>