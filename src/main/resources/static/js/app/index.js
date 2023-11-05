import { calculateBudget } from './calculateBudget.js';
//import { uploadImages } from './handleImages.js';

var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            calculateBudget();
            _this.save();
            //_this.uploadImages()
        });
        $('[name="btn-new-row"]').on('click', function() {
            _this.new_row($(this));
        });
        $('[name="btn-delete-row"]').on('click', function() {
            _this.deleteRow($(this));
        });
        //수정 버튼
        $('#btn-update').on('click', function() {
            calculateBudget();
            _this.update();
        });
        //삭제 버튼
        $('[name="btn-delete"]').on('click', function() {
            _this.delete($(this));
        });
    },
    new_row : function(button) {
        var table_body = button.closest('table').find('tbody[name="plan_table"]').eq(0);
        var first_tr = table_body.find('tr:first').eq(0);
        var new_tr = first_tr.clone();

        var tmps = new_tr.find('input');
        for (var i=0; i<tmps.length; i++){
            var tmp = tmps.eq(i);
            tmp.val('');
        }

        table_body.append(new_tr);

        $(new_tr).find('[name="btn-delete-row"]').on('click', function() {
            main.deleteRow($(this));
        });
    },
    deleteRow : function(button) {
        if(button.closest('tbody').children().length == 1) {
            alert('삭제할 수 없습니다.');
        }
        else {
            var tableRow = button.closest('tr');
            tableRow.remove();
        }
    },
    save : function() {

        var plan = {
            title: $('#title').val(),
            location: $('#location').val(),
            startDate: $('#start_date').val(),
            endDate: $('#end_date').val(),
            tripState: $('select[name="state"]').val(),
            budget: parseFloat($('#budget').text())
        };

        var tableCounter = $('table').length - 1;
        var datePlans = [];
        for (var j=1; j<=tableCounter; j++){
            var table_name = '#table'+j+' ';

            var date = $(table_name+'input[name="dates"]');
            var tour_spots = $(table_name+'input[name="tour_spots"]');
            var contents = $(table_name+'input[name="contents"]');
            var start_times = $(table_name+'input[name="start_times"]');
            var end_times = $(table_name+'input[name="end_times"]');
            var costs = $(table_name+'input[name="costs"]');

            for (var i=0 ; i<tour_spots.length; i++){
                var datePlan = {
                    date: date.val(),
                    tourSpot: tour_spots.eq(i).val(),
                    content: contents.eq(i).val(),
                    startTime: start_times.eq(i).val()+":00",
                    endTime: end_times.eq(i).val()+":00",
                    cost: costs.eq(i).val()
                };
                datePlans.push(datePlan);
            }
        }

        var allData = {
            plan : plan,
            datePlans : datePlans
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/plans',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(allData)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/planboard';
        }).fail(function(error) {
            alert(JSON.stringify(allData));
        });
    },
    update: function() {
        var path = location.pathname.split('/');
        var planId = parseInt(path[3]);
        console.log(planId);

        var plan = {
            title: $('#title').val(),
            location: $('#location').val(),
            startDate: $('#start_date').val(),
            endDate: $('#end_date').val(),
            tripState: $('select[name="state"]').val(),
            budget: parseFloat($('#budget').text())
        };

        var tableCounter = $('table').length - 1;
        var datePlans = [];
        for (var j=1; j<=tableCounter; j++){
            var table_name = '#table'+j+' ';

            var date = $(table_name+'input[name="dates"]');
            var tour_spots = $(table_name+'input[name="tour_spots"]');
            var contents = $(table_name+'input[name="contents"]');
            var start_times = $(table_name+'input[name="start_times"]');
            var end_times = $(table_name+'input[name="end_times"]');
            var costs = $(table_name+'input[name="costs"]');

            for (var i=0 ; i<tour_spots.length; i++){
                var tmp1 = start_times.eq(i).val().split(":");
                var tmp2 = end_times.eq(i).val().split(":");

                var start_time;
                var end_time;

                if(tmp1.length > 2){
                    start_time = start_times.eq(i).val();
                }
                else{
                    start_time = start_times.eq(i).val()+":00";
                }

                if(tmp2.length > 2){
                    end_time = end_times.eq(i).val();
                }
                else{
                    end_time = end_times.eq(i).val()+":00";
                }

                var datePlan = {
                    date: date.val(),
                    tourSpot: tour_spots.eq(i).val(),
                    content: contents.eq(i).val(),
                    startTime: start_time,
                    endTime: end_time,
                    cost: costs.eq(i).val()
                };
                datePlans.push(datePlan);
            }
        }

        var allData = {
            plan : plan,
            datePlans : datePlans
        }

        $.ajax({
            type: 'PUT',
            url: '/api/v1/plans/'+planId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(allData)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/planboard';
        }).fail(function() {
            alert(JSON.stringify(error));
        });
    },
    delete: function(button) {
        var planId = parseInt(button.attr('data-plan-id'));

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/plans/'+planId,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/planboard';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();