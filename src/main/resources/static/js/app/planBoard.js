if (window.location.pathname === '/planboard') {
    // tripState 값을 특정 텍스트로 변환
    var tripStateElements = document.querySelectorAll('span[data-trip-state]');
    tripStateElements.forEach(function(element) {
        var tripState = parseInt(element.getAttribute('data-trip-state'));
        console.log(tripState);
        var tripStateText = convertTripStateToText(tripState);
        console.log(tripStateText)
        element.textContent = tripStateText;
    });
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