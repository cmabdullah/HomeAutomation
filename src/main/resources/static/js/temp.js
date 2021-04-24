var stompClient;

/* Chart Configuration */
var config = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: 'Temperature',
            backgroudColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: [],
            fill: false

        }]
    },
    options: {
        responsive: true,
        title: {
            display: true,
            text: 'Temperature'
        },
        tooltips: {
            mode: 'index',
            intersect: false
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                display: true,
                type: 'time',
                time: {
                    displayFormats: {
                        quarter: 'h:mm:ss a'
                    }
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Time'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Value'
                }
            }]
        }
    }
};

/* Document Ready Event */
$(document).ready(function () {

    var ctx = document.getElementById('lineChart').getContext('2d');
    window.myLine = new Chart(ctx, config);

    /* Configuring WebSocket on Client Side */
    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // Subscribe to the Public Topic
        //stompClient.subscribe("/topic/" + "" + "/queue/commentary", messageReceived);
        stompClient.subscribe("/user" + "/weatherTemp" + "/queue/commentary", function (temperature) {

            const body = new String(temperature.body);
            //const messages= body.split('  ');
            const words = body.split('  ');
            console.log(words.length);
            var value = 0;
            if (words.length === 3){
                console.log(chars[8]);
                const temp = words[1].split('=');
                console.log(temp);
                console.log(temp[1]);
                value = parseFloat(temp[1])
                console.log(value);
            }
            /**
            var jsonData = JSON.parse(temperature.body);
            console.log(jsonData.commentary)
            var value = jsonData.commentary;
            console.log('value cm: ' + value);
            console.log('temperature cm: ' + jsonData);
             */

            $('#temperature').text(value);
            /* Push new data On X-Axis of Chart */
            config.data.labels.push(new Date());
            /* Push new data on Y-Axis of chart */
            config.data.datasets.forEach(function (dataset) {
                dataset.data.push(value);
            });
            window.myLine.update();
        });
    });

});
