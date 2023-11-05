import {extractData} from './viewPlan.js';
import {groupDatePlan} from './viewPlan.js';
import {newTable} from './manageTable.js';

var datePlans = extractData();
var groupedDatePlans = groupDatePlan(datePlans);
var container = $('#tables');
var cnt = 0;

for (var date in groupedDatePlans) {
    var tmp = groupedDatePlans[date];
    var tableE;

    if (cnt == 0) {
        tableE = $('#table1');
    }
    else {
        newTable(cnt+1);
        tableE = $('#table'+(cnt+1));
        console.log(tableE);
        //tableE = newTable(cnt+1);
        //container.append(tableE);
    }

    cnt += 1;

    var dateForm = tableE.find('input[name="dates"]');
    dateForm.eq(0).val(date);

    var idx = 0;
    var table_body = tableE.find('tbody[name="plan_table"]').eq(0);

    tmp.forEach(function(dp) {
        if (idx===0) {
            var tourSpotForm = table_body.find('input[name="tour_spots"]');
            var contentForm = table_body.find("input[name='contents']");
            var startTimeForm = table_body.find("input[name='start_times']");
            var endTimeForm = table_body.find("input[name='end_times']");
            var costForm = table_body.find("input[name='costs']");

            tourSpotForm.eq(0).val(dp["tour-spots"]);
            contentForm.eq(0).val(dp["contents"]);
            startTimeForm.eq(0).val(dp["start-times"]);
            endTimeForm.eq(0).val(dp["end-times"]);
            costForm.eq(0).val(dp["costs"]);
        }
        else {
            var first_tr = table_body.find('tr:first').eq(0);
            var new_tr = first_tr.clone();
            table_body.append(new_tr);

            for (var i=0; i<5; i++){
                switch (i) {
                    case 0:
                        var tmp = new_tr.find('input[name="tour_spots"]');
                        tmp.val(dp["tour-spots"]);
                    case 1:
                        var tmp = new_tr.find('input[name="contents"]');
                        tmp.val(dp["contents"]);
                    case 2:
                        var tmp = new_tr.find('input[name="start_times"]');
                        tmp.val(dp["start-times"]);
                    case 3:
                        var tmp = new_tr.find('input[name="end_times"]');
                        tmp.val(dp["end-times"]);
                    case 4:
                        var tmp = new_tr.find('input[name="costs"]');
                        tmp.val(dp["costs"]);
                    default:
                        break;
                }
            }

            $(new_tr).find('[name="btn-delete-row"]').on('click', function() {
                if($(this).closest('tbody').children().length == 1) {
                    alert('삭제할 수 없습니다.');
                }
                else {
                    var tableRow = $(this).closest('tr');
                    tableRow.remove();
                }
            });
        }

        idx += 1;
    });
}