<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="dandelion" uri="http://github.com/dandelion" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<dandelion:bundle includes="mealsDatatable"/>
<jsp:include page="fragments/headTag.jsp"/>
<fmt:message key="meals.date" var="date" />
<fmt:message key="meals.meal" var="meal" />
<fmt:message key="meals.calories" var="calories" />
<fmt:message key="meals.deleteButton" var="deleteBtn" />
<fmt:message key="meals.updateButton" var="updateBtn" />
<fmt:message key="meals.startDate" var="startDate" />
<fmt:message key="meals.endDate" var="endDate" />
<fmt:message key="meals.startTime" var="startTime" />
<fmt:message key="meals.endTime" var="endTime" />

<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="meals.title"/></h3>

            <c:set var="ajaxUrl" value="ajax/profile/meals/"/>
            <div class="view-box">
                <form:form modelAttribute="filter" class="form-horizontal" action="ajax/profile/meals/filter" charset="utf-8"
                           accept-charset="UTF-8" id="filter">
                    <div class="form-group">
                        <spring:bind path="startDate">
                            <label class="col-sm-1"><fmt:message key="meals.fromDate"/></label>
                            <div class="col-sm-2"><form:input path="startDate" class="form-control date-picker" placeholder="${startDate}"/></div>
                        </spring:bind>
                        <spring:bind path="endDate">
                            <label class="col-sm-1"><fmt:message key="meals.toDate"/></label>
                            <div class="col-sm-2"><form:input path="endDate" class="form-control date-picker" placeholder="${endDate}"/></div>
                        </spring:bind>
                    </div>
                    <div class="form-group">
                        <spring:bind path="startTime">
                            <label class="col-sm-1"><fmt:message key="meals.fromTime"/></label>
                            <div class="col-sm-2"><form:input path="startTime" class="form-control time-picker" placeholder="${startTime}"/></div>
                        </spring:bind>
                        <spring:bind path="endTime">
                            <label class="col-sm-1"><fmt:message key="meals.toTime"/></label>
                            <div class="col-sm-2"><form:input path="endTime" class="form-control time-picker" placeholder="${endTime}"/></div>
                        </spring:bind>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-6">
                            <button type="submit" class="btn btn-primary pull-right"><fmt:message key="meals.filterButton"/></button>
                        </div>
                    </div>
                </form:form>
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.addButton"/></a>

                <datatables:table id="datatable" url="${ajaxUrl}" row="meal" theme="bootstrap3"
                                  cssClass="table table-striped" pageable="false" info="false">

                    <datatables:column title="${date}" property="date" renderFunction="renderDate" />

                    <datatables:column title="${meal}" property="meal" />

                    <datatables:column title="${calories}" property="calories" />

                    <datatables:column sortable="false" renderFunction="renderUpdateBtn" />

                    <datatables:column sortable="false" renderFunction="renderDeleteBtn" />

                    <datatables:column property="limitExceeded" cssCellClass="hidden limitExceeded" />

                    <datatables:callback type="init" function="makeEditable"/>
                </datatables:table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.editMeal"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="addUpdateForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="meal" class="control-label col-xs-3">${meal}</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="meal" name="meal" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3">${date}</label>

                        <div class="col-xs-9">
                            <input type="datetime" class="form-control datetime-picker" id="date" name="date" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">${calories}</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary"><fmt:message key="meals.save"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var ajaxUrl = '${ajaxUrl}';

    var updateButton = '${updateBtn}';

    var deleteButton = '${deleteBtn}';

    var token = $("meta[name='_csrf']").attr("content");

    var header = $("meta[name='_csrf_header']").attr("content");

    //        $(document).ready(function () {

</script>
</html>