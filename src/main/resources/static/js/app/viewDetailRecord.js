$(document).ready(function () {

    var urlParams = new URLSearchParams(window.location.search);
    var recordId = urlParams.get('recordId');
    var url = "update_record?recordId=" + encodeURIComponent(recordId);
    document.getElementById('updateButton').href = url;

    $.get("/fetch-record/" + recordId, function (data) {

        $('#recordTitle').text(data.recordTitle);
        $('#location').text(data.location);

        var startDate = new Date(data.startDate);
        var endDate = new Date(data.endDate);
        $('#startDate').text(startDate.toLocaleDateString());
        $('#endDate').text(endDate.toLocaleDateString());


    });

    //record-content를 가져오기 위한 부분

    $.get("/fetch-record-content/" + recordId, function (data) {
        var contentHTML = "";
        data.sort(function(a, b) {
            var dateA = new Date(a.date), dateB = new Date(b.date);
            return dateA - dateB;
        });

        for (var i = 0; i < data.length; i++) {
            contentHTML += "<p><b>" + data[i].date + "</b>: " + data[i].content + "</p>";
        }
        $('#content').html(contentHTML);
    });

});