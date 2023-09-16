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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.379375, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.23, 500, 1500, "POST Register Seller"], "isController": false}, {"data": [0.24, 500, 1500, "POST Login Seller"], "isController": false}, {"data": [0.4675, 500, 1500, "GET List Product"], "isController": false}, {"data": [0.505, 500, 1500, "PUT Update Offer-1"], "isController": false}, {"data": [0.4625, 500, 1500, "GET List Categories"], "isController": false}, {"data": [0.5675, 500, 1500, "GET Category"], "isController": false}, {"data": [0.43, 500, 1500, "GET Profile"], "isController": false}, {"data": [0.545, 500, 1500, "PUT Update Offer-0"], "isController": false}, {"data": [0.5225, 500, 1500, "GET List Offers"], "isController": false}, {"data": [0.5425, 500, 1500, "DEL Delete Product"], "isController": false}, {"data": [0.15, 500, 1500, "PUT Update Product"], "isController": false}, {"data": [0.1075, 500, 1500, "PUT Update Offer"], "isController": false}, {"data": [0.53, 500, 1500, "GET Product ID"], "isController": false}, {"data": [0.495, 500, 1500, "POST Create Offer"], "isController": false}, {"data": [0.015, 500, 1500, "PUT Update Profile"], "isController": false}, {"data": [0.26, 500, 1500, "POST Create Product"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 3200, 0, 0.0, 1302.4934375000023, 37, 22162, 1160.0, 2150.9, 2560.95, 3683.9199999999983, 16.419855710517943, 76.3110539222289, 197.5837496825067], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["POST Register Seller", 200, 0, 0.0, 1678.4200000000008, 615, 4226, 1550.5, 2324.4, 2772.5499999999997, 4201.6, 1.0429486243507646, 1.7719839718351722, 0.0], "isController": false}, {"data": ["POST Login Seller", 200, 0, 0.0, 1682.4099999999992, 321, 3676, 1526.5, 3046.9, 3096.6, 3554.680000000003, 1.0524820157135566, 1.7874001951038538, 0.0], "isController": false}, {"data": ["GET List Product", 200, 0, 0.0, 1022.9650000000003, 102, 2044, 983.0, 1531.2, 1719.9, 1996.2100000000007, 1.1122604469062476, 14.028330577012914, 0.23244505433392285], "isController": false}, {"data": ["PUT Update Offer-1", 200, 0, 0.0, 1054.0899999999992, 116, 1811, 1068.5, 1437.9, 1533.3499999999997, 1806.1800000000007, 1.1195951543921716, 22.879276853489777, 0.4493687582570143], "isController": false}, {"data": ["GET List Categories", 200, 0, 0.0, 1051.5649999999998, 40, 3038, 988.0, 1665.3000000000002, 1923.2999999999988, 2518.840000000001, 1.0694214964415, 1.117461915226958, 0.18693989049123877], "isController": false}, {"data": ["GET Category", 200, 0, 0.0, 883.9150000000002, 55, 2999, 896.5, 1386.9, 1555.6, 2857.450000000006, 1.0825439783491204, 0.7706782814614344, 0.19134810554803788], "isController": false}, {"data": ["GET Profile", 200, 0, 0.0, 1162.2450000000003, 37, 3115, 1131.5, 1705.5, 1938.4499999999996, 3023.9300000000003, 1.0849517196484757, 1.5013443229901269, 0.44923782141694696], "isController": false}, {"data": ["PUT Update Offer-0", 200, 0, 0.0, 818.6099999999998, 41, 2692, 779.0, 1312.7, 1469.7999999999997, 2136.710000000001, 1.1169627548769387, 1.2026329255488477, 0.5421196183338267], "isController": false}, {"data": ["GET List Offers", 200, 0, 0.0, 895.6249999999997, 48, 2691, 855.5, 1360.3000000000002, 1548.55, 2173.140000000001, 1.1241891785549674, 1.2387049727103077, 0.47646299169224193], "isController": false}, {"data": ["DEL Delete Product", 200, 0, 0.0, 884.3100000000003, 61, 3105, 855.5, 1355.3000000000002, 1646.349999999999, 2318.83, 1.129713730540681, 0.9696672745938679, 0.49866270137147245], "isController": false}, {"data": ["PUT Update Product", 200, 0, 0.0, 1783.7399999999998, 582, 5641, 1689.5, 2416.5000000000005, 2894.95, 5045.45000000001, 1.1032048099729714, 2.3669937927491866, 101.24077143320095], "isController": false}, {"data": ["PUT Update Offer", 200, 0, 0.0, 1872.8449999999996, 157, 4397, 1894.5, 2563.7, 2835.5, 3314.4500000000007, 1.1162396119951108, 24.012559614171778, 0.9897905934487897], "isController": false}, {"data": ["GET Product ID", 200, 0, 0.0, 878.1450000000006, 48, 2644, 824.0, 1366.4, 1473.95, 2209.4200000000005, 1.1142123354447657, 1.806634368436593, 0.2230600866857197], "isController": false}, {"data": ["POST Create Offer", 200, 0, 0.0, 991.1899999999998, 75, 2717, 934.0, 1422.1000000000001, 1669.7499999999995, 2458.550000000001, 1.1155920726027322, 3.356221709282284, 0.5872110616531959], "isController": false}, {"data": ["PUT Update Profile", 200, 0, 0.0, 2535.8850000000007, 1052, 22162, 2251.5, 3517.6000000000004, 4205.9, 6449.620000000012, 1.0769769257693653, 1.489809316186963, 51.82379311273256], "isController": false}, {"data": ["POST Create Product", 200, 0, 0.0, 1643.9350000000002, 499, 5135, 1469.5, 2444.9, 3005.0999999999985, 4543.250000000002, 1.0986234248486646, 2.2701969471314944, 53.06604340386496], "isController": false}]}, function(index, item){
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
