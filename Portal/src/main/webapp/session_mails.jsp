<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create session</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet"  type="text/css" />

    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12" style="margin-top: 10px">
            <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">Add Mail</button>

            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Add email and custom variables</h4>
                        </div>
                        <div class="modal-body">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="inputEmail">Email</label>
                                    <input type="text" class="form-control" placeholder="Email" id="inputEmail">
                                </div>
                                <c:if test="${!empty variables}">
                                    <c:forEach items="${variables}" var="v">
                                        <div class="form-group">
                                            <label>${v}</label>
                                            <input type="text" class="form-control">
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="buttonAdd" onclick="addSessionMails([${variables}]);">Add</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </div>
    </div>
    <div class="row">
        <table class="table table-hover" style="margin-top: 10px">
            <tr>
                <th>Email</th>
                <c:if test="${!empty variables}">
                    <c:forEach items="${variables}" var="v">
                        <th>${v}</th>
                    </c:forEach>
                </c:if>
            </tr>
            <tr>
                <td>row 1, cell 1</td>
                <td>row 1, cell 2</td>
            </tr>
            <tr>
                <td>row 2, cell 1</td>
                <td>row 2, cell 2</td>
            </tr>
        </table>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {

    });

    function addSessionMails(variables){
        var email = document.getElementById("inputEmail").value;
        for(var i = 0; i < variables.length; i++){

        }
    };
</script>


</body>
</html>