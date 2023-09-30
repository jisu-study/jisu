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

function groupDatePlan() {
    var datePlans = {{ plan.datePlans | to_json }};

    // datePlans를 사용하여 동적으로 테이블 생성
    var tableBody = document.querySelector("tbody[name='plan_table']");
    datePlans.forEach(function(plan) {
        var row = document.createElement("tr");
        row.innerHTML = `
            <td name="tour_spots">${plan.tourSpot}</td>
            <td name="contents">${plan.content}</td>
            <td name="start_times">${plan.startTime}</td>
            <td name="end_times">${plan.endTime}</td>
            <td name="costs">${plan.cost || 0}</td>
        `;
        tableBody.appendChild(row);
    });

    // 여행 비용 표시
    var budgetElement = document.getElementById("budget");
    budgetElement.textContent = {{ plan.budget }};
}

returnTripState();

if (window.location.pathname !== '/planboard') {
    groupDatePlan();
}