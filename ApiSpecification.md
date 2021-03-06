> http://localhost:8080/a/stations
>request method get
>response
# request method Get
```json
{
   "hasError":false,
   "responseTime":1592231199,
   "result":{
      "stationList":[
         {
            "id":4972,
            "stationId":"41923",
            "country":"BD",
            "stationName":"Dhaka",
            "number1":"23.7667",
            "number2":"90.3833",
            "quantity":"8",
            "stationKey":"VGTJ",
            "stationId2":"41923",
            "key2":"",
            "state":"Asia/Dhaka"
         },
         {
            "id":1448,
            "stationId":"10384",
            "country":"DE",
            "stationName":"\"Berlin / Tempelhof\"",
            "number1":"52.4667",
            "number2":"13.4000",
            "quantity":"50",
            "stationKey":"EDDI",
            "stationId2":"10384",
            "key2":"THF",
            "state":"Europe/Berlin"
         },
         {
            "id":5821,
            "stationId":"47662",
            "country":"JP",
            "stationName":"Tokyo",
            "number1":"35.6833",
            "number2":"139.7667",
            "quantity":"5",
            "stationKey":"RJTD",
            "stationId2":"47662",
            "key2":"",
            "state":"Asia/Tokyo"
         }
      ],
      "add":false,
      "delete":true,
      "stationSearch":true
   }
}
```

> http://localhost:8080/a/favorite?id=23804
>request method get
>response
```json
{
    "hasError": false,
    "responseTime": 1592231497,
    "result": {
        "stationList": [
            {
                "id": 23804,
                "stationId": "VGHS0",
                "country": "BD",
                "stationName": "\"Dhaka / Solpur\"",
                "number1": "23.8433",
                "number2": "90.3978",
                "quantity": "10",
                "stationKey": "VGHS",
                "stationId2": "",
                "key2": "DAC",
                "state": "Asia/Dhaka"
            },
            {
                "id": 4972,
                "stationId": "41923",
                "country": "BD",
                "stationName": "Dhaka",
                "number1": "23.7667",
                "number2": "90.3833",
                "quantity": "8",
                "stationKey": "VGTJ",
                "stationId2": "41923",
                "key2": "",
                "state": "Asia/Dhaka"
            },
            {
                "id": 1448,
                "stationId": "10384",
                "country": "DE",
                "stationName": "\"Berlin / Tempelhof\"",
                "number1": "52.4667",
                "number2": "13.4000",
                "quantity": "50",
                "stationKey": "EDDI",
                "stationId2": "10384",
                "key2": "THF",
                "state": "Europe/Berlin"
            },
            {
                "id": 5821,
                "stationId": "47662",
                "country": "JP",
                "stationName": "Tokyo",
                "number1": "35.6833",
                "number2": "139.7667",
                "quantity": "5",
                "stationKey": "RJTD",
                "stationId2": "47662",
                "key2": "",
                "state": "Asia/Tokyo"
            }
        ],
        "add": false,
        "delete": true,
        "stationSearch": false
    }
}
```

> http://localhost:8080/a/delFavorite?id=23804
>request method get
>response
```json
{
    "hasError": false,
    "responseTime": 1592231569,
    "result": {
        "stationList": [
            {
                "id": 4972,
                "stationId": "41923",
                "country": "BD",
                "stationName": "Dhaka",
                "number1": "23.7667",
                "number2": "90.3833",
                "quantity": "8",
                "stationKey": "VGTJ",
                "stationId2": "41923",
                "key2": "",
                "state": "Asia/Dhaka"
            },
            {
                "id": 1448,
                "stationId": "10384",
                "country": "DE",
                "stationName": "\"Berlin / Tempelhof\"",
                "number1": "52.4667",
                "number2": "13.4000",
                "quantity": "50",
                "stationKey": "EDDI",
                "stationId2": "10384",
                "key2": "THF",
                "state": "Europe/Berlin"
            },
            {
                "id": 5821,
                "stationId": "47662",
                "country": "JP",
                "stationName": "Tokyo",
                "number1": "35.6833",
                "number2": "139.7667",
                "quantity": "5",
                "stationKey": "RJTD",
                "stationId2": "47662",
                "key2": "",
                "state": "Asia/Tokyo"
            }
        ],
        "add": false,
        "delete": true,
        "stationSearch": false
    }
}
```

> localhost:8080/a/stations
>post request
```json
{
    "keyword" : "dhaka"
}
```

> response
```json

{
    "hasError": false,
    "responseTime": 1592232788,
    "result": {
        "stationList": [
            {
                "id": 23804,
                "stationId": "VGHS0",
                "country": "BD",
                "stationName": "\"Dhaka / Solpur\"",
                "number1": "23.8433",
                "number2": "90.3978",
                "quantity": "10",
                "stationKey": "VGHS",
                "stationId2": "",
                "key2": "DAC",
                "state": "Asia/Dhaka"
            }
        ],
        "add": true,
        "delete": false,
        "stationSearch": true
    }
}
```

> localhost:8080/a/remote
> request type get
```json
{
    "hasError": false,
    "responseTime": 1592233567,
    "result": {
        "stations": [
            "41923\tBD\tDhaka\tAsia/Dhaka",
            "10384\tDE\t\"Berlin / Tempelhof\"\tEurope/Berlin",
            "47662\tJP\tTokyo\tAsia/Tokyo"
        ],
        "payloadTypes": [
            "humidity",
            "temperature",
            "dewpoint",
            "winddirection",
            "windspeed",
            "precipitation",
            "pressure"
        ]
    }
}
```

> localhost:8080/a/remote
> post request body
```json
{
    "targetDate" : "2018-12-27",
    "namePath" : "41923\tBD\tDhaka\tAsia/Dhaka",
    "payloadType": "temperature",
    "payloadState": ""
}
```

> post response body 
```json
{
    "hasError": false,
    "responseTime": 1592235364,
    "result": {
        "filtarDate": "2018-12",
        "payloadType": "humidity",
        "payload2List": [
            {
                "hourName": "h 0",
                "d1": 84.0,
                "d2": 91.0,
                "d3": 87.0,
                "d4": 86.0,
                "d5": 79.0,
                "d6": 79.0,
                "d7": 90.0,
                "d8": 67.0,
                "d9": 97.0,
                "d10": 95.0,
                "d11": 90.0,
                "d12": 93.0,
                "d13": 89.0,
                "d14": 83.0,
                "d15": 78.0,
                "d16": 77.0,
                "d17": 81.0,
                "d18": 76.0,
                "d19": 85.0,
                "d20": 93.0,
                "d21": 90.0,
                "d22": 90.0,
                "d23": 91.0,
                "d24": 93.0,
                "d25": 88.0,
                "d26": 84.0,
                "d27": 85.0,
                "d28": 80.0,
                "d29": 80.0,
                "d30": 88.0,
                "d31": 93.0
            },
            {
                "hourName": "h 1",
                "d1": 75.0,
                "d2": 77.0,
                "d3": 77.0,
                "d4": 78.0,
                "d5": 77.0,
                "d6": 76.0,
                "d7": 79.0,
                "d8": 75.0,
                "d9": 78.0,
                "d10": 82.0,
                "d11": 80.0,
                "d12": 77.0,
                "d13": 78.0,
                "d14": 75.0,
                "d15": 69.0,
                "d16": 74.0,
                "d17": 82.0,
                "d18": 85.0,
                "d19": 84.0,
                "d20": 89.0,
                "d21": 89.0,
                "d22": 87.0,
                "d23": 87.0,
                "d24": 88.0,
                "d25": 85.0,
                "d26": 83.0,
                "d27": 79.0,
                "d28": 76.0,
                "d29": 75.0,
                "d30": 80.0,
                "d31": 86.0
            },
            {
                "hourName": "h 2",
                "d1": 67.0,
                "d2": 68.0,
                "d3": 67.0,
                "d4": 68.0,
                "d5": 70.0,
                "d6": 70.0,
                "d7": 72.0,
                "d8": 67.0,
                "d9": 72.0,
                "d10": 73.0,
                "d11": 72.0,
                "d12": 71.0,
                "d13": 69.0,
                "d14": 70.0,
                "d15": 64.0,
                "d16": 70.0,
                "d17": 81.0,
                "d18": 92.0,
                "d19": 86.0,
                "d20": 86.0,
                "d21": 86.0,
                "d22": 83.0,
                "d23": 84.0,
                "d24": 84.0,
                "d25": 81.0,
                "d26": 78.0,
                "d27": 74.0,
                "d28": 72.0,
                "d29": 69.0,
                "d30": 75.0,
                "d31": 82.0
            },
            {
                "hourName": "h 3",
                "d1": 66.0,
                "d2": 56.0,
                "d3": 56.0,
                "d4": 57.0,
                "d5": 60.0,
                "d6": 69.0,
                "d7": 57.0,
                "d8": 63.0,
                "d9": 59.0,
                "d10": 66.0,
                "d11": 68.0,
                "d12": 69.0,
                "d13": 63.0,
                "d14": 66.0,
                "d15": 58.0,
                "d16": 68.0,
                "d17": 83.0,
                "d18": 93.0,
                "d19": 89.0,
                "d20": 83.0,
                "d21": 84.0,
                "d22": 79.0,
                "d23": 80.0,
                "d24": 81.0,
                "d25": 77.0,
                "d26": 71.0,
                "d27": 68.0,
                "d28": 68.0,
                "d29": 64.0,
                "d30": 69.0,
                "d31": 65.0
            },
            {
                "hourName": "h 4",
                "d1": 52.0,
                "d2": 53.0,
                "d3": 52.0,
                "d4": 55.0,
                "d5": 57.0,
                "d6": 58.0,
                "d7": 57.0,
                "d8": 53.0,
                "d9": 60.0,
                "d10": 58.0,
                "d11": 58.0,
                "d12": 57.0,
                "d13": 56.0,
                "d14": 59.0,
                "d15": 54.0,
                "d16": 64.0,
                "d17": 76.0,
                "d18": 92.0,
                "d19": 82.0,
                "d20": 76.0,
                "d21": 73.0,
                "d22": 71.0,
                "d23": 72.0,
                "d24": 73.0,
                "d25": 69.0,
                "d26": 65.0,
                "d27": 62.0,
                "d28": 58.0,
                "d29": 56.0,
                "d30": 62.0,
                "d31": 67.0
            },
            {
                "hourName": "h 5",
                "d1": 45.0,
                "d2": 47.0,
                "d3": 46.0,
                "d4": 49.0,
                "d5": 51.0,
                "d6": 53.0,
                "d7": 50.0,
                "d8": 47.0,
                "d9": 55.0,
                "d10": 52.0,
                "d11": 51.0,
                "d12": 49.0,
                "d13": 50.0,
                "d14": 54.0,
                "d15": 49.0,
                "d16": 61.0,
                "d17": 73.0,
                "d18": 88.0,
                "d19": 75.0,
                "d20": 69.0,
                "d21": 64.0,
                "d22": 63.0,
                "d23": 65.0,
                "d24": 66.0,
                "d25": 62.0,
                "d26": 58.0,
                "d27": 56.0,
                "d28": 49.0,
                "d29": 49.0,
                "d30": 54.0,
                "d31": 58.0
            },
            {
                "hourName": "h 6",
                "d1": 38.0,
                "d2": 39.0,
                "d3": 41.0,
                "d4": 41.0,
                "d5": 44.0,
                "d6": 47.0,
                "d7": 39.0,
                "d8": 47.0,
                "d9": 38.0,
                "d10": 44.0,
                "d11": 37.0,
                "d12": 36.0,
                "d13": 44.0,
                "d14": 49.0,
                "d15": 47.0,
                "d16": 59.0,
                "d17": 68.0,
                "d18": 81.0,
                "d19": 67.0,
                "d20": 63.0,
                "d21": 58.0,
                "d22": 52.0,
                "d23": 56.0,
                "d24": 57.0,
                "d25": 54.0,
                "d26": 48.0,
                "d27": 47.0,
                "d28": 40.0,
                "d29": 40.0,
                "d30": 44.0,
                "d31": 39.0
            },
            {
                "hourName": "h 7",
                "d1": 38.0,
                "d2": 39.0,
                "d3": 39.0,
                "d4": 42.0,
                "d5": 43.0,
                "d6": 46.0,
                "d7": 43.0,
                "d8": 40.0,
                "d9": 47.0,
                "d10": 44.0,
                "d11": 44.0,
                "d12": 42.0,
                "d13": 42.0,
                "d14": 46.0,
                "d15": 46.0,
                "d16": 55.0,
                "d17": 65.0,
                "d18": 80.0,
                "d19": 65.0,
                "d20": 59.0,
                "d21": 55.0,
                "d22": 52.0,
                "d23": 54.0,
                "d24": 54.0,
                "d25": 51.0,
                "d26": 46.0,
                "d27": 46.0,
                "d28": 41.0,
                "d29": 41.0,
                "d30": 43.0,
                "d31": 46.0
            },
            {
                "hourName": "h 8",
                "d1": 38.0,
                "d2": 38.0,
                "d3": 38.0,
                "d4": 41.0,
                "d5": 42.0,
                "d6": 45.0,
                "d7": 42.0,
                "d8": 40.0,
                "d9": 45.0,
                "d10": 43.0,
                "d11": 43.0,
                "d12": 41.0,
                "d13": 40.0,
                "d14": 45.0,
                "d15": 44.0,
                "d16": 53.0,
                "d17": 63.0,
                "d18": 78.0,
                "d19": 62.0,
                "d20": 56.0,
                "d21": 54.0,
                "d22": 50.0,
                "d23": 51.0,
                "d24": 52.0,
                "d25": 49.0,
                "d26": 44.0,
                "d27": 45.0,
                "d28": 40.0,
                "d29": 40.0,
                "d30": 42.0,
                "d31": 44.0
            },
            {
                "hourName": "h 9",
                "d1": 37.0,
                "d2": 38.0,
                "d3": 38.0,
                "d4": 41.0,
                "d5": 43.0,
                "d6": 43.0,
                "d7": 40.0,
                "d8": 41.0,
                "d9": 42.0,
                "d10": 36.0,
                "d11": 34.0,
                "d12": 39.0,
                "d13": 39.0,
                "d14": 44.0,
                "d15": 44.0,
                "d16": 52.0,
                "d17": 60.0,
                "d18": 81.0,
                "d19": 60.0,
                "d20": 52.0,
                "d21": 49.0,
                "d22": 48.0,
                "d23": 49.0,
                "d24": 48.0,
                "d25": 47.0,
                "d26": 42.0,
                "d27": 44.0,
                "d28": 39.0,
                "d29": 38.0,
                "d30": 40.0,
                "d31": 41.0
            },
            {
                "hourName": "h 10",
                "d1": 43.0,
                "d2": 44.0,
                "d3": 44.0,
                "d4": 46.0,
                "d5": 47.0,
                "d6": 49.0,
                "d7": 46.0,
                "d8": 46.0,
                "d9": 48.0,
                "d10": 48.0,
                "d11": 48.0,
                "d12": 45.0,
                "d13": 46.0,
                "d14": 50.0,
                "d15": 48.0,
                "d16": 58.0,
                "d17": 67.0,
                "d18": 83.0,
                "d19": 67.0,
                "d20": 60.0,
                "d21": 58.0,
                "d22": 55.0,
                "d23": 56.0,
                "d24": 55.0,
                "d25": 53.0,
                "d26": 49.0,
                "d27": 49.0,
                "d28": 44.0,
                "d29": 45.0,
                "d30": 46.0,
                "d31": 48.0
            },
            {
                "hourName": "h 11",
                "d1": 50.0,
                "d2": 51.0,
                "d3": 51.0,
                "d4": 53.0,
                "d5": 53.0,
                "d6": 56.0,
                "d7": 53.0,
                "d8": 52.0,
                "d9": 54.0,
                "d10": 55.0,
                "d11": 56.0,
                "d12": 53.0,
                "d13": 53.0,
                "d14": 57.0,
                "d15": 54.0,
                "d16": 64.0,
                "d17": 73.0,
                "d18": 90.0,
                "d19": 74.0,
                "d20": 68.0,
                "d21": 65.0,
                "d22": 63.0,
                "d23": 63.0,
                "d24": 62.0,
                "d25": 59.0,
                "d26": 55.0,
                "d27": 56.0,
                "d28": 49.0,
                "d29": 52.0,
                "d30": 53.0,
                "d31": 56.0
            },
            {
                "hourName": "h 12",
                "d1": 58.0,
                "d2": 59.0,
                "d3": 59.0,
                "d4": 60.0,
                "d5": 57.0,
                "d6": 68.0,
                "d7": 57.0,
                "d8": 62.0,
                "d9": 59.0,
                "d10": 64.0,
                "d11": 49.0,
                "d12": 61.0,
                "d13": 63.0,
                "d14": 62.0,
                "d15": 66.0,
                "d16": 76.0,
                "d17": 83.0,
                "d18": 90.0,
                "d19": 78.0,
                "d20": 78.0,
                "d21": 69.0,
                "d22": 69.0,
                "d23": 72.0,
                "d24": 69.0,
                "d25": 64.0,
                "d26": 59.0,
                "d27": 61.0,
                "d28": 55.0,
                "d29": 56.0,
                "d30": 63.0,
                "d31": 54.0
            },
            {
                "hourName": "h 13",
                "d1": 62.0,
                "d2": 62.0,
                "d3": 63.0,
                "d4": 63.0,
                "d5": 64.0,
                "d6": 65.0,
                "d7": 64.0,
                "d8": 63.0,
                "d9": 65.0,
                "d10": 67.0,
                "d11": 67.0,
                "d12": 64.0,
                "d13": 64.0,
                "d14": 66.0,
                "d15": 65.0,
                "d16": 74.0,
                "d17": 82.0,
                "d18": 93.0,
                "d19": 83.0,
                "d20": 81.0,
                "d21": 76.0,
                "d22": 74.0,
                "d23": 76.0,
                "d24": 75.0,
                "d25": 70.0,
                "d26": 67.0,
                "d27": 66.0,
                "d28": 60.0,
                "d29": 62.0,
                "d30": 63.0,
                "d31": 69.0
            },
            {
                "hourName": "h 14",
                "d1": 66.0,
                "d2": 67.0,
                "d3": 68.0,
                "d4": 67.0,
                "d5": 69.0,
                "d6": 69.0,
                "d7": 68.0,
                "d8": 67.0,
                "d9": 69.0,
                "d10": 71.0,
                "d11": 71.0,
                "d12": 69.0,
                "d13": 68.0,
                "d14": 69.0,
                "d15": 68.0,
                "d16": 75.0,
                "d17": 81.0,
                "d18": 92.0,
                "d19": 85.0,
                "d20": 84.0,
                "d21": 80.0,
                "d22": 78.0,
                "d23": 80.0,
                "d24": 80.0,
                "d25": 75.0,
                "d26": 72.0,
                "d27": 70.0,
                "d28": 64.0,
                "d29": 65.0,
                "d30": 67.0,
                "d31": 73.0
            },
            {
                "hourName": "h 15",
                "d1": 72.0,
                "d2": 69.0,
                "d3": 70.0,
                "d4": 70.0,
                "d5": 57.0,
                "d6": 69.0,
                "d7": 72.0,
                "d8": 71.0,
                "d9": 73.0,
                "d10": 75.0,
                "d11": 73.0,
                "d12": 72.0,
                "d13": 70.0,
                "d14": 70.0,
                "d15": 67.0,
                "d16": 74.0,
                "d17": 80.0,
                "d18": 84.0,
                "d19": 86.0,
                "d20": 85.0,
                "d21": 81.0,
                "d22": 82.0,
                "d23": 81.0,
                "d24": 82.0,
                "d25": 77.0,
                "d26": 72.0,
                "d27": 71.0,
                "d28": 63.0,
                "d29": 69.0,
                "d30": 69.0,
                "d31": 71.0
            },
            {
                "hourName": "h 16",
                "d1": 74.0,
                "d2": 75.0,
                "d3": 74.0,
                "d4": 74.0,
                "d5": 74.0,
                "d6": 75.0,
                "d7": 74.0,
                "d8": 74.0,
                "d9": 77.0,
                "d10": 79.0,
                "d11": 77.0,
                "d12": 75.0,
                "d13": 74.0,
                "d14": 72.0,
                "d15": 72.0,
                "d16": 78.0,
                "d17": 82.0,
                "d18": 87.0,
                "d19": 86.0,
                "d20": 86.0,
                "d21": 85.0,
                "d22": 84.0,
                "d23": 85.0,
                "d24": 85.0,
                "d25": 80.0,
                "d26": 77.0,
                "d27": 74.0,
                "d28": 69.0,
                "d29": 71.0,
                "d30": 73.0,
                "d31": 75.0
            },
            {
                "hourName": "h 17",
                "d1": 77.0,
                "d2": 79.0,
                "d3": 76.0,
                "d4": 77.0,
                "d5": 75.0,
                "d6": 77.0,
                "d7": 77.0,
                "d8": 77.0,
                "d9": 80.0,
                "d10": 81.0,
                "d11": 79.0,
                "d12": 77.0,
                "d13": 76.0,
                "d14": 73.0,
                "d15": 73.0,
                "d16": 79.0,
                "d17": 82.0,
                "d18": 86.0,
                "d19": 86.0,
                "d20": 87.0,
                "d21": 86.0,
                "d22": 85.0,
                "d23": 87.0,
                "d24": 85.0,
                "d25": 81.0,
                "d26": 78.0,
                "d27": 76.0,
                "d28": 71.0,
                "d29": 73.0,
                "d30": 76.0,
                "d31": 78.0
            },
            {
                "hourName": "h 18",
                "d1": 85.0,
                "d2": 65.0,
                "d3": 75.0,
                "d4": 74.0,
                "d5": 68.0,
                "d6": 89.0,
                "d7": 67.0,
                "d8": 77.0,
                "d9": 88.0,
                "d10": 82.0,
                "d11": 75.0,
                "d12": 76.0,
                "d13": 76.0,
                "d14": 72.0,
                "d15": 75.0,
                "d16": 78.0,
                "d17": 80.0,
                "d18": 88.0,
                "d19": 89.0,
                "d20": 85.0,
                "d21": 85.0,
                "d22": 83.0,
                "d23": 85.0,
                "d24": 82.0,
                "d25": 79.0,
                "d26": 77.0,
                "d27": 74.0,
                "d28": 74.0,
                "d29": 77.0,
                "d30": 79.0,
                "d31": 0.0
            },
            {
                "hourName": "h 19",
                "d1": 81.0,
                "d2": 83.0,
                "d3": 79.0,
                "d4": 79.0,
                "d5": 77.0,
                "d6": 80.0,
                "d7": 79.0,
                "d8": 78.0,
                "d9": 83.0,
                "d10": 85.0,
                "d11": 83.0,
                "d12": 82.0,
                "d13": 79.0,
                "d14": 75.0,
                "d15": 75.0,
                "d16": 80.0,
                "d17": 80.0,
                "d18": 87.0,
                "d19": 89.0,
                "d20": 87.0,
                "d21": 87.0,
                "d22": 86.0,
                "d23": 87.0,
                "d24": 85.0,
                "d25": 81.0,
                "d26": 79.0,
                "d27": 76.0,
                "d28": 76.0,
                "d29": 76.0,
                "d30": 81.0,
                "d31": 0.0
            },
            {
                "hourName": "h 20",
                "d1": 82.0,
                "d2": 84.0,
                "d3": 81.0,
                "d4": 79.0,
                "d5": 78.0,
                "d6": 82.0,
                "d7": 80.0,
                "d8": 79.0,
                "d9": 84.0,
                "d10": 87.0,
                "d11": 86.0,
                "d12": 84.0,
                "d13": 81.0,
                "d14": 76.0,
                "d15": 75.0,
                "d16": 82.0,
                "d17": 80.0,
                "d18": 87.0,
                "d19": 90.0,
                "d20": 89.0,
                "d21": 88.0,
                "d22": 87.0,
                "d23": 87.0,
                "d24": 86.0,
                "d25": 83.0,
                "d26": 80.0,
                "d27": 76.0,
                "d28": 78.0,
                "d29": 78.0,
                "d30": 83.0,
                "d31": 0.0
            },
            {
                "hourName": "h 21",
                "d1": 88.0,
                "d2": 88.0,
                "d3": 76.0,
                "d4": 77.0,
                "d5": 67.0,
                "d6": 86.0,
                "d7": 76.0,
                "d8": 81.0,
                "d9": 95.0,
                "d10": 87.0,
                "d11": 80.0,
                "d12": 83.0,
                "d13": 82.0,
                "d14": 75.0,
                "d15": 74.0,
                "d16": 85.0,
                "d17": 81.0,
                "d18": 87.0,
                "d19": 87.0,
                "d20": 88.0,
                "d21": 83.0,
                "d22": 81.0,
                "d23": 84.0,
                "d24": 81.0,
                "d25": 81.0,
                "d26": 75.0,
                "d27": 76.0,
                "d28": 77.0,
                "d29": 76.0,
                "d30": 82.0,
                "d31": 0.0
            },
            {
                "hourName": "h 22",
                "d1": 84.0,
                "d2": 86.0,
                "d3": 83.0,
                "d4": 80.0,
                "d5": 79.0,
                "d6": 84.0,
                "d7": 82.0,
                "d8": 82.0,
                "d9": 87.0,
                "d10": 89.0,
                "d11": 89.0,
                "d12": 85.0,
                "d13": 82.0,
                "d14": 76.0,
                "d15": 75.0,
                "d16": 82.0,
                "d17": 82.0,
                "d18": 85.0,
                "d19": 89.0,
                "d20": 90.0,
                "d21": 88.0,
                "d22": 87.0,
                "d23": 87.0,
                "d24": 86.0,
                "d25": 84.0,
                "d26": 81.0,
                "d27": 78.0,
                "d28": 79.0,
                "d29": 81.0,
                "d30": 85.0,
                "d31": 0.0
            },
            {
                "hourName": "h 23",
                "d1": 86.0,
                "d2": 87.0,
                "d3": 85.0,
                "d4": 83.0,
                "d5": 79.0,
                "d6": 85.0,
                "d7": 83.0,
                "d8": 84.0,
                "d9": 90.0,
                "d10": 88.0,
                "d11": 87.0,
                "d12": 85.0,
                "d13": 81.0,
                "d14": 76.0,
                "d15": 75.0,
                "d16": 81.0,
                "d17": 81.0,
                "d18": 84.0,
                "d19": 90.0,
                "d20": 91.0,
                "d21": 90.0,
                "d22": 89.0,
                "d23": 89.0,
                "d24": 87.0,
                "d25": 85.0,
                "d26": 82.0,
                "d27": 78.0,
                "d28": 80.0,
                "d29": 83.0,
                "d30": 87.0,
                "d31": 0.0
            }
        ]
    }
}

```


```json


```


```json


```


```json


```


```json


```

