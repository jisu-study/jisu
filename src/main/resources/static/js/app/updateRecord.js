
    var recordId; // record content에서도 쓸거라 전역변수로 해줘야됨

    $(document).ready(function () {

        var urlParams = new URLSearchParams(window.location.search);
        recordId = urlParams.get('recordId');

         $('#detailButton').attr('href', 'view_detail_record?recordId=' + recordId);
         //이 여행 기록 상세보기로 가는 버튼

        $.get("/fetch-record/" + recordId, function (data) {
            $('#recordTitleInput').val(data.recordTitle);
            $('#locationInput').val(data.location);

            var startDate = new Date(data.startDate).toISOString().split('T')[0];
            var endDate = new Date(data.endDate).toISOString().split('T')[0];
            $('#startDateInput').val(startDate);
            $('#endDateInput').val(endDate);
            $('#costInput').val(data.cost);
        });



         $('#saveBasicInfoButton').click(function() {
                var updatedBasicInfo = {
                    recordTitle: $('#recordTitleInput').val(),
                    location: $('#locationInput').val(),
                    startDate: $('#startDateInput').val(),
                    endDate: $('#endDateInput').val(),
                    cost: $('#costInput').val()
                };

                $.ajax({
                    url: "/update-record/" + recordId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify(updatedBasicInfo),
                    success: function(response) {
                        alert('기본 정보가 성공적으로 업데이트되었습니다.');
                        location.reload();
                    },
                    error: function(response) {
                        alert('기본 정보 업데이트 중 오류가 발생했습니다.');
                    }
                });
            });

        $('#updateButton').click(function() {
            var updatedRecord = {
                recordTitle: $('#recordTitleInput').val(),
                location: $('#locationInput').val(),
                startDate: $('#startDateInput').val(),
                endDate: $('#endDateInput').val(),
                cost: $('#costInput').val(),
                content: []
            };

            $('#content').children().each(function(index, element) {
                var date = $(element).find('input[type=date]').val();
                var content = $(element).find('input[type=text]').val();
                updatedRecord.content.push({date: date, content: content});
            });

            $.ajax({
                url: "/update-record/" + recordId,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(updatedRecord),
                success: function(response) {
                    alert('Record updated successfully.');
                },
                error: function(response) {
                    alert('Error updating record.');
                }
            });
        });

             $('#saveContentButton').click(function() {
                     var updatedData = [];

                     $('#content').children().each(function(index, element) {
                         var date = $(element).find('input[type=date]').val();
                         var content = $(element).find('input[type=text]').val();
                         updatedData.push({date: date, content: content});
                     });

                     sendDataToServer(recordId, updatedData);
                });

         createInitialTemplate();

    });
$(document).ready(function () {
    // ... 기존 코드 ...

    createInitialTemplate();
});

function createInitialTemplate() {
    $.get("/fetch-record-content/" + recordId, function (data) {
        var contentHTML = "";
        var startDate = new Date($('#startDateInput').val());
        var endDate = new Date($('#endDateInput').val());

        for (var d = startDate; d <= endDate; d.setDate(d.getDate() + 1)) {
            var currentDate = d.toISOString().split('T')[0];
            var currentContent = "";
            for (var i = 0; i < data.length; i++) {
                if (data[i].date === currentDate) {
                    currentContent = data[i].content;
                    break;
                }
            }
            contentHTML += '<p><b><input type="date" value="' + currentDate + '"></b>: <input type="text" value="' + currentContent + '"></p>';
        }
        $('#content').html(contentHTML);
    });
}



function sendDataToServer(recordId, data) { // date_content랑 약간 다름
    var totalCount = data.length;
    var processedCount = 0;

    data.forEach(content => {
        //빈칸인 경우에는 DB에 그 날짜의 행을 업로드하고 싶지 않다
         if (!content.content) {
            processedCount++;
            if (processedCount === totalCount) {
                alert("저장 성공!");
                 location.reload();
            }
            return;
        }



        // 체크하여 record content가 이미 존재하는지 확인
        $.ajax({
            type: "GET",
            url: "/check-record-content",
            data: {
                recordId: recordId,
                date: content.date
            },
            success: function(response) {
                var recordContentId = response;

                // record content가 이미 존재하면 업데이트, 그렇지 않으면 삽입
                var url = recordContentId != null ? "/update-record-content/" + recordContentId : "/add-record-content";
                var type = recordContentId != null ? "PUT" : "POST";

                $.ajax({
                    type: type,
                    url: url,
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({
                        recordId: recordId,
                        content: content.content,
                        date: content.date
                    }),
                    success: function() {
                        processedCount++;

                        if (processedCount === totalCount) {
                            alert("저장 성공!");
                            location.reload();
                        }
                    },
                    error: function(err) {
                        alert("저장 실패 : " + (err.responseJSON && err.responseJSON.message) || err.responseText || "알 수 없는 오류");
                    }
                });
            },
            error: function(err) {
                alert("체크 실패 : " + (err.responseJSON && err.responseJSON.message) || err.responseText || "알 수 없는 오류");
            }
        });
    });
}