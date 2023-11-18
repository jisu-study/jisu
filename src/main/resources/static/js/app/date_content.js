var tempData = {
    recordTitle: null,
    location: null,
    startDate: null,
    endDate: null,
};

function saveTempData() {
    tempData.recordTitle = $("#recordTitle").val();
    tempData.location = $("#location").val();
    tempData.startDate = $("#startDate").val();
    tempData.endDate = $("#endDate").val();
    alert("임시 저장 완료!");
}

function handleFormSubmit() {

   if (Object.values(tempData).some(item => item === null)) {
        alert("모든 필드를 채워주세요.");
        return;
    }

         let data = tempData;

        $.ajax({
            headers: {
                'X-CSRF-TOKEN': $('input[name="${_csrf.parameterName}"]').val()
            },
            type: "POST",
            url: "/add-record",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(response) {
                alert("성공적으로 추가되었습니다!");

                //date_content.js에 recordId 넘기는 용도
                recordId = response;
                alert("Received recordId from the server: " + response);
                window.location.href = `http://localhost:8080/view_detail_record?recordId=${recordId}`;



            },
            error: function(err) {
                alert("오류 발생: " + err.responseJSON.message);
            }
        });
    }

window.updateRecord = function(recordId) {
            let recordRow = $('tr[data-id="' + recordId + '"]');
            let fields = ['recordTitle', 'location', 'startDate', 'endDate'];

            fields.forEach(function(field) {
                let cell = recordRow.find('.' + field);
                let value = cell.text().trim();
                let inputType = (field === 'startDate' || field === 'endDate') ? 'date' : 'text';
                cell.html(`<input type="${inputType}" value="${value}">`);
            });

            let updateBtn = recordRow.find('button:contains("업데이트")');
            updateBtn.text('저장');
            updateBtn.attr('onclick', `saveRecord(${recordId})`);
        }

window.saveRecord = function(recordId) {
            let recordRow = $('tr[data-id="' + recordId + '"]');
            let recordTitleInput = recordRow.find('.recordTitle input');
            let locationInput = recordRow.find('.location input');
            let startDateInput = recordRow.find('.startDate input');
            let endDateInput = recordRow.find('.endDate input');

            let data = {
                recordTitle: recordTitleInput.val(),
                location: locationInput.val(),
                startDate: startDateInput.val(),
                endDate: endDateInput.val()
            };

            $.ajax({
                headers: {
                    'X-CSRF-TOKEN': $('input[name="${_csrf.parameterName}"]').val()
                },
                type: "PUT",
                url: "/update-record/" + recordId,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(response) {
                    alert(JSON.stringify(response));
                    alert("성공적으로 수정되었습니다!");
                    recordTitleInput.parent().text(data.recordTitle);
                    locationInput.parent().text(data.location);
                    startDateInput.parent().text(data.startDate);
                    endDateInput.parent().text(data.endDate);
                    let saveBtn = recordRow.find('button:contains("저장")');
                    saveBtn.text('업데이트');
                    saveBtn.attr('onclick', `updateRecord(${recordId})`);
                },
                error: function(err) {
                    alert("오류 발생: " + err.responseJSON.message);
                }
            });
        }

window.deleteRecord = function(recordId) {
            if (recordId === undefined || recordId === null || isNaN(recordId)) {
                alert("유효하지 않은 레코드 ID입니다.");
                return;
            }

            if(confirm("정말로 삭제하시겠습니까?")) {
                $.ajax({
                    url: "/delete-record/" + recordId,
                    type: 'DELETE',
                    success: function(result) {
                        location.reload();
                    }
                });
            }
        }

function generateContentsFields(recordId) {
    var startDate = tempData.startDate;
    var endDate = tempData.endDate;

    // 날짜 차이 계산
    var start = new Date(startDate);
    var end = new Date(endDate);
    var timeDiff = Math.abs(end.getTime() - start.getTime());
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); // 차이 일 수 계산

    // 텍스트 칸 동적 생성
    var templateContainer = document.createElement("div");
    for (var i = 0; i <= diffDays; i++) {
        var dateSpan = document.createElement("span");
        dateSpan.textContent = startDate;

        var textField = document.createElement("input");
        textField.setAttribute("type", "text");
        textField.setAttribute("placeholder", startDate);

        templateContainer.appendChild(dateSpan);
        templateContainer.appendChild(textField);
        templateContainer.appendChild(document.createElement("br")); // 줄 바꿈 추가
        startDate = getNextDay(startDate); // 다음 날짜 계산
    }

    var targetDiv = document.querySelector(".content-space");
    targetDiv.appendChild(templateContainer);
}


function getNextDay(dateString) {
    var date = new Date(dateString);
    date.setDate(date.getDate() + 1);
    return date.toISOString().split("T")[0];
}