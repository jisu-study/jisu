function returnTripState() {
    if (window.location.pathname === '/planboard') {
        var tripStateElements = document.querySelectorAll('span[data-trip-state]');

        tripStateElements.forEach(function(element) {
            var tripState = parseInt(element.getAttribute('data-trip-state'));
            var tripStateText = convertTripStateToText(tripState);
            element.textContent = tripStateText;
        });
    }
    else {
        var tripSateElement = document.getElementById('trip_state')
        var tripState = parseInt(tripSateElement.getAttribute('data-trip-state'));
        var tripStateText = convertTripStateToText(tripState);

        tripSateElement.textContent = tripStateText;
    }
}

// tripState 값을 특정 텍스트로 변환하는 함수
function convertTripStateToText(tripState) {
    switch (tripState) {
        case 0:
            return '여행전';
        case 1:
            return '여행중';
        case 2:
            return '여행후';
        default:
            return '알 수 없음';
    }
}

function createNewTable(cnt, date) {
    var newTable = document.createElement("table");
    newTable.setAttribute("id", "table"+cnt);
    newTable.className = "table n-table";

    var tableHead = document.createElement("thead");
    newTable.appendChild(tableHead);

    var headerRow1 = document.createElement("tr");
    tableHead.appendChild(headerRow1);

    var th = document.createElement("tr");
    th.setAttribute("scope", "col");
    headerRow1.appendChild(th);

    var h5Element = document.createElement("h5");
    h5Element.setAttribute("name", "dates");
    h5Element.textContent = date+"일차";
    th.appendChild(h5Element);

    var headerRow2 = document.createElement("tr");
    tableHead.appendChild(headerRow2);

    var headerColumns = ["장소", "계획 세부 내용", "시작 시각", "종료 시각", "비용"];
    headerColumns.forEach(function(columnText) {
        var headerColumn = document.createElement("th");
        headerColumn.setAttribute("scope", "col");
        headerColumn.textContent = columnText;
        headerRow2.appendChild(headerColumn);
    });

    return newTable;
}

function makePlanView() {
    var datePlans = extractData();
    var groupedDatePlans = groupDatePlan(datePlans);
    var container = document.getElementById("tables");
    var cnt = 0;

    for (var date in groupedDatePlans) {
        var tmp = groupedDatePlans[date];

        var newTable = createNewTable(cnt, date);
        container.appendChild(newTable);

        var tBody = document.createElement("tbody");
        tBody.setAttribute("name", "plan_table");
        newTable.appendChild(tBody);

        cnt += 1;

        tmp.forEach(function(dp) {
            var row = document.createElement("tr");

            row.innerHTML = `
                <td name="tour_spots">${dp["tour-spots"]}</td>
                <td name="contents">${dp["contents"]}</td>
                <td name="start_times">${dp["start-times"]}</td>
                <td name="end_times">${dp["end-times"]}</td>
                <td name="costs">${dp["costs"]}</td>
            `;

            tBody.appendChild(row);
        });
    }
}

function extractData() {
    var datePlans = [];
    var dp = {}
    // 모든 div 엘리먼트를 선택합니다.
    var divElements = document.querySelectorAll('div[name^="data_"]');

    idx=-1;
    divElements.forEach(function (divElement) {
        var nameAttribute = divElement.getAttribute('name');
        var dataKey = nameAttribute.substring(5); // "data_" 부분을 제외한 키를 추출합니다.
        var dataValue = divElement.getAttribute('data-' + dataKey);

        if (idx<5) {
            dp[dataKey] = dataValue;
            idx += 1;
        }
        else {
            idx=0;
            datePlans.push(dp);
            dp = {}
            dp[dataKey] = dataValue;
        }
        // 추출한 데이터를 data 객체에 추가합니다.
    });

    datePlans.push(dp);

    return datePlans;
}

function groupDatePlan(datePlans) {
    var groupedDatePlans = {};

    datePlans.forEach(function(datePlan) {
        var date = datePlan.date;

        if (!groupedDatePlans[date]) {
            groupedDatePlans[date] = [];
        }
        groupedDatePlans[date].push(datePlan);
    });

    return groupedDatePlans;
}

returnTripState();

if (window.location.pathname !== '/planboard') {
    makePlanView();
}