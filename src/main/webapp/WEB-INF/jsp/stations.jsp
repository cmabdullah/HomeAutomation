<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">
    <div class="table-responsive">
        <table id="bookListTable" class="table taable-bordered table-hover table-striped">
            <thead>
            <tr>
                <th><input id="selectAllBooks" type="checkbox" /></th>
                <th>country</th>
                <th>stationId</th>
                <th>stationName</th>
                <th>state</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${stationList }" var="station">
            <tr >

                <td>
                    <input hidden="hidden" name="id" ${station.id} />
                    <input id="selected" ${station.id} class="checkboxBook" type="checkbox" />
                </td>

                <td>${station.country}</td>
                <td >${station.stationId}</td>
                <td >${station.stationName}</td>
                <td >${station.state}</td>


                <td><a type="button" class="btn btn-success" href="/favorite?id=${station.id}">Add</a></td>
                <td><a type="button" class="btn btn-warning" href="/delFavorite?id=${station.id}">Delete</a></td>
<%--                <td>--%>
<%--                    <input hidden="hidden" name="id" ${station.id} />--%>
<%--                    <button id="${station.id}" class="btn btn-danger btn-xs delete-book" type="submit" value="delete" >--%>
<%--                        <span class="fa fa-times"></span>Delete</button>--%>
<%--                </td>--%>

            </tr>
            </c:forEach>

            </tbody>

        </table>
    </div>

<%--    <button th:id="deleteSelected" class="btn btn-danger ">Delete Selected</button>--%>

</div>


<%@ include file="common/footer.jspf" %>


