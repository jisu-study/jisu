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

returnTripState();