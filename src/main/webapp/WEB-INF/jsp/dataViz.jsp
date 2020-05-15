<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<script type="text/javascript"
        src="https://www.gstatic.com/charts/loader.js">
</script>
<script type="text/javascript">
    google.charts.load('current', {packages: ['corechart', 'line']});
</script>
</head>

<body>
<div id="container">
    <c:out value="${filterDate}">Date</c:out>
</div>
<script type="text/javascript">

    function drawChart() {


        // Define the chart to be drawn.
        var data = new google.visualization.DataTable();
//             data.addColumn('string', 'month');
//             data.addColumn('number', 'tokyo');
//             data.addColumn('number', 'newYork');
        data.addColumn('string', 'Hour');
        data.addColumn('number', 'd1');
        data.addColumn('number', 'd2');
        data.addColumn('number', 'd3');
        data.addColumn('number', 'd4');
        data.addColumn('number', 'd5');
        data.addColumn('number', 'd6');
        data.addColumn('number', 'd7');
        data.addColumn('number', 'd8');
        data.addColumn('number', 'd9');
        data.addColumn('number', 'd10');
        data.addColumn('number', 'd11');
        data.addColumn('number', 'd12');
        data.addColumn('number', 'd13');
        data.addColumn('number', 'd14');
        data.addColumn('number', 'd15');
        data.addColumn('number', 'd16');
        data.addColumn('number', 'd17');
        data.addColumn('number', 'd18');
        data.addColumn('number', 'd19');
        data.addColumn('number', 'd20');
        data.addColumn('number', 'd21');
        data.addColumn('number', 'd22');
        data.addColumn('number', 'd23');
        data.addColumn('number', 'd24');
        data.addColumn('number', 'd25');
        data.addColumn('number', 'd26');
        data.addColumn('number', 'd27');
        data.addColumn('number', 'd28');
        data.addColumn('number', 'd29');
        data.addColumn('number', 'd30');
        data.addColumn('number', 'd31');


        data.addRows([

//             <c:forEach items="${pieDataList}" var="entry">
//                  [ '${entry.month}', ${entry.tokyo}, ${entry.newYork} ],
//              </c:forEach>   

            <c:forEach items="${payload2list}" var="entry">
            ['${entry.hourName}', ${entry.d1}, ${entry.d2}, ${entry.d3}, ${entry.d4}, ${entry.d5}, ${entry.d6},
				${entry.d7}, ${entry.d8}, ${entry.d9}, ${entry.d10}, ${entry.d11}, ${entry.d12}, ${entry.d13},
				${entry.d14}, ${entry.d15}, ${entry.d16}, ${entry.d17}, ${entry.d18}, ${entry.d19}, ${entry.d20},
				${entry.d21}, ${entry.d22}, ${entry.d23}, ${entry.d24}, ${entry.d25}, ${entry.d26}, ${entry.d27},
				${entry.d28}, ${entry.d29}, ${entry.d30}, ${entry.d31}],
            </c:forEach>

        ]);

        var fd = "${filtarDate}";

        var payloadType = "${payloadType}";

        // Set chart options
        var options = {
            'title': payloadType + ' analysis report of ' + fd,
            hAxis: {
                title: 'Hour'
            },
            vAxis: {
                title: payloadType
            },
            'width': 1700,
            'height': 1440,
            pointsVisible: true,
            chartArea: {left: 60, top: 22}
        };

        // Instantiate and draw the chart.
        var chart = new google.visualization.LineChart(document.getElementById('container'));
        chart.draw(data, options);
    }

    google.charts.setOnLoadCallback(drawChart);
</script>


<%@ include file="common/footer.jspf" %>


