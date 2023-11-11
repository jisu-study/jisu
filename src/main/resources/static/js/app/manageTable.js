import { calculateBudget } from './calculateBudget.js';

var tableCounter = 0;

$(document).ready(function() {
    var lastTableId =$('#tables').children().eq(-1).attr('id');
    var regex = /[^0-9]/g;
    var result = lastTableId.replace(regex, "");
    tableCounter = parseInt(result); // 새 테이블의 카운터 초기값

    // 새 테이블 추가 버튼 클릭 이벤트 처리
    $('#btn-add-table').on('click', function() {
        // 테이블 복사 및 추가 함수 호출
        console.log(tableCounter);
        newTable(tableCounter+1);
        tableCounter++; // 테이블 카운터 증가
    });

    $('#btn-budget').on('click', function() {calculateBudget();});
});

// 테이블 복사 및 추가하는 함수
function newTable(tableId) {
    // 기존의 table1을 복사
    var clonedTable = $('#table1').clone(true);
    var newTableId = 'table' + tableId;

    // 새로운 테이블의 ID 설정
    clonedTable.attr('id', newTableId);

    // 기존 테이블의 추가 행 제거
    clonedTable.find('tbody[name="plan_table"] tr:not(:first)').remove();

    var tmps = clonedTable.find('input');
    for (var i=0; i<tmps.length; i++){
        var tmp = tmps.eq(i);
        tmp.val('');
    }

    // 삭제 버튼 추가
    var deleteButton = $('<button>', {
        text: '테이블 삭제',
        class: 'btn btn-danger d-inline-flex align-items-center',
        click: function() {
            // 삭제 버튼 클릭 시 해당 테이블 삭제
            $('#' + newTableId).remove();
            $(this).remove();
            tableCounter--;
        }
    });

    // 복제한 테이블과 삭제 버튼을 컨테이너에 추가
    $('#table'+(tableId-1)).after(deleteButton, clonedTable);
}

export {newTable};