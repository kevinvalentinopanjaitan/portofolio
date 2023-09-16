/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.40921875, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.32, 500, 1500, "POST Register Seller"], "isController": false}, {"data": [0.3675, 500, 1500, "GET Seller Product ID"], "isController": false}, {"data": [0.2675, 500, 1500, "POST Register Buyer"], "isController": false}, {"data": [0.0375, 500, 1500, "POST Product"], "isController": false}, {"data": [0.4025, 500, 1500, "GET Buyer Products"], "isController": false}, {"data": [0.3575, 500, 1500, "POST Buyer Order"], "isController": false}, {"data": [0.59, 500, 1500, "GET Buyer Order"], "isController": false}, {"data": [0.4325, 500, 1500, "PUT Buyer Order By ID"], "isController": false}, {"data": [0.5825, 500, 1500, "GET Buyer Order ID"], "isController": false}, {"data": [0.45, 500, 1500, "POST Login"], "isController": false}, {"data": [0.46, 500, 1500, "POST Login Buyer"], "isController": false}, {"data": [0.3875, 500, 1500, "GET Seller Product"], "isController": false}, {"data": [0.66, 500, 1500, "GET Buyer Products ID"], "isController": false}, {"data": [0.425, 500, 1500, "DEL Seller Product ID"], "isController": false}, {"data": [0.6325, 500, 1500, "DEL Buyer Order"], "isController": false}, {"data": [0.175, 500, 1500, "PUT Update Profil"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3200, 0, 0.0, 1527.6459375000004, 57, 13232, 1000.0, 3235.0, 4767.0, 8771.409999999987, 13.01670205582538, 9.0101573495554, 42.70619539722297], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["POST Register Seller", 200, 0, 0.0, 2002.2849999999992, 337, 7006, 1421.0, 4221.5, 5259.45, 6989.840000000002, 0.8369286392795718, 0.48630130895639184, 0.0], "isController": false}, {"data": ["GET Seller Product ID", 200, 0, 0.0, 1521.365, 474, 7517, 1127.0, 2529.8, 3788.4499999999975, 6796.720000000002, 0.8448943037226042, 0.593241215211477, 0.29455787737204076], "isController": false}, {"data": ["POST Register Buyer", 200, 0, 0.0, 1921.9299999999992, 302, 6956, 1465.0, 4200.3, 5458.749999999999, 6948.0, 0.8466396870819717, 0.480368806830689, 1.1513969025687047], "isController": false}, {"data": ["POST Product", 200, 0, 0.0, 3788.1499999999974, 880, 12292, 2465.5, 9229.800000000001, 9723.9, 10503.45, 0.8337884428583935, 0.553687637835652, 0.0], "isController": false}, {"data": ["GET Buyer Products", 200, 0, 0.0, 1405.1050000000002, 267, 5771, 850.5, 3955.600000000001, 4759.9, 5502.97, 0.8526203153842546, 0.6178166738428876, 0.33888326988417156], "isController": false}, {"data": ["POST Buyer Order", 200, 0, 0.0, 1503.6499999999994, 142, 8713, 1135.0, 3026.7000000000003, 3726.65, 6743.490000000021, 0.8552527485685206, 0.5646004472971875, 0.3541280912041531], "isController": false}, {"data": ["GET Buyer Order", 200, 0, 0.0, 667.7649999999999, 95, 4991, 563.5, 934.8000000000001, 1091.0499999999997, 3962.5900000000192, 0.8606963033093773, 1.0355252399190946, 0.29250225932779617], "isController": false}, {"data": ["PUT Buyer Order By ID", 200, 0, 0.0, 1266.8700000000006, 102, 11482, 766.0, 2037.5000000000002, 2606.9999999999964, 11012.96000000001, 0.8637406014225808, 0.5659862730024919, 0.33992916247392585], "isController": false}, {"data": ["GET Buyer Order ID", 200, 0, 0.0, 738.9899999999996, 126, 5931, 581.0, 1040.0, 1346.499999999999, 4500.220000000007, 0.8622101896000207, 1.037346634362525, 0.29385874626016334], "isController": false}, {"data": ["POST Login", 200, 0, 0.0, 1379.139999999999, 196, 4537, 998.5, 3269.8, 4051.649999999999, 4531.82, 0.8355475342992262, 0.42103762470546946, 0.0], "isController": false}, {"data": ["POST Login Buyer", 200, 0, 0.0, 1260.2800000000002, 212, 5036, 933.0, 2753.7, 3736.9, 4772.52, 0.8458304786554679, 0.41795920136686204, 0.23871582844866232], "isController": false}, {"data": ["GET Seller Product", 200, 0, 0.0, 1279.049999999999, 487, 3981, 1090.5, 2221.2000000000003, 2701.5, 3469.7200000000003, 0.841421497477839, 0.5924461910952362, 0.28841693907687643], "isController": false}, {"data": ["GET Buyer Products ID", 200, 0, 0.0, 768.1950000000002, 57, 3235, 526.5, 1768.7, 2445.85, 3226.530000000005, 0.8549383375724026, 0.9509519204052408, 0.18284325774253532], "isController": false}, {"data": ["DEL Seller Product ID", 200, 0, 0.0, 1473.2900000000002, 76, 13232, 803.0, 4036.4000000000005, 4847.9, 8435.430000000026, 0.8682515150988939, 0.2645453835066942, 0.32135480881101636], "isController": false}, {"data": ["DEL Buyer Order", 200, 0, 0.0, 753.89, 77, 6456, 526.0, 1399.4, 1778.1, 3551.2400000000052, 0.8669906321662194, 0.2709345725519436, 0.3183481227485337], "isController": false}, {"data": ["PUT Update Profil", 200, 0, 0.0, 2712.38, 328, 8254, 1712.0, 6530.5, 7194.8499999999985, 7972.910000000002, 0.8452978195542744, 0.5770148201840213, 40.0024686988859], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 3200, 0, "", "", "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
