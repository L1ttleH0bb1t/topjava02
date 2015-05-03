<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="dandelion" uri="http://github.com/dandelion" %>


<html>
<dandelion:bundle includes="mealsDatatable"/>
<jsp:include page="fragments/headTag.jsp"/>
<fmt:message key="meals.tableDateColumn" var="tableDateColumn" />
<fmt:message key="meals.tableMealColumn" var="tableMealColumn" />
<fmt:message key="meals.tableCaloriesColumn" var="tableCaloriesColumn" />

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="meals.title"/></h3>

            <c:set var="ajaxUrl" value="ajax/profile/meals/"/>
            <div class="view-box">
                <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.addButton"/></a>

                <datatables:table id="datatable" url="${ajaxUrl}" row="meal" theme="bootstrap3"
                                  cssClass="table table-striped" pageable="false" info="false">

                    <datatables:column title="Date" property="date" renderFunction="renderDate" />

                    <datatables:column title="Meal" property="meal" />

                    <datatables:column title="Calories" property="calories" />

                    <datatables:column sortable="false" renderFunction="renderUpdateBtn" />

                    <datatables:column sortable="false" renderFunction="renderDeleteBtn" />

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
                <h2 class="modal-title">Добавить</h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="addUpdateForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="meal" class="control-label col-xs-3">Meal</label>
                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="meal" name="meal" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="datetime" class="form-control" id="date" name="date" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var ajaxUrl = 'ajax/profile/meals/';
    //        $(document).ready(function () {
    $(function () {
        makeEditable();
    });
</script>
</html>