<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%--https://mdbootstrap.com/docs/jquery/forms/slider/--%>

<div class="container">
    <h1>Home automation</h1>

    <div class="row">

            <div class="form-group row">
                <label for="light1" class="col-sm-4 col-form-label">Light</label>
                <div class="col-sm-8">
                    <input type="checkbox"  data-size="lg" id="light1" class="switch" >
                </div>
            </div>

        <input type="text" name="" id="l1" readonly>

            <div class="form-group row">
                <label for="fan" class="col-sm-4 col-form-label">Fan</label>
                <div class="col-sm-8">
                    <input type="checkbox"  data-size="lg" id="fan" class="switch">
                </div>
            </div>
        <input type="text" name="" id="f1" readonly>


        <div class="form-group row">
            <label for="cars" class="col-sm-4 col-form-label">Mood</label>
            <div class="col-sm-2">

                <select id="cars" name="cars">
                    <option value="volvo">regular</option>
                    <option value="saab">custom</option>
<%--                    <option value="fiat">auto pilot</option>--%>
                </select>

            </div>
        </div>


        <div class="form-group row">
            <label for="regulatorParam" class="col-sm-4 col-form-label">Regulator</label>
            <div class="col-sm-2">
                <input path="regulatorParam" type="range" class="custom-range" value="${currentRegulatorValue}"
                       id="regulatorParam" min="0" max="100"/>
            </div>
        </div>
        <input type="text" id="sp1" />




    </div>
</div>
<%@ include file="common/footer.jspf" %>


