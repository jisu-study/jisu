import { calculateBudget } from './calculateBudget.js';
import { saveImage } from './handleImages.js';

var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            calculateBudget();
            _this.save(saveImage());
        });
        $('#btn-update').on('click', function() {
            _this.update();
        });
        $('#btn-delete').on('click', function() {
            _this.delete();
        });
        $('[name="btn-new-row"]').on('click', function() {
            _this.new_row($(this));
        });
        $('[name="btn-delete-row"]').on('click', function() {
            _this.deleteRow($(this));
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
    save : function(formData) {

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

        /*var allData = {
            plan : plan,
            datePlans : datePlans
        }*/

        //새롭게 추가된 부분
        //formData.append('allData', JSON.stringify(allData));
        formData.append('plan', JSON.stringify(plan));
        formData.append('datePlans', JSON.stringify(datePlans));

        $.ajax({
            type: 'POST',
            url: '/api/v1/plans',
            data: formData,
            //챗지피티 말로는 밑 두 줄이 중요하다고 함
            processData: false,
            contentType: false,
            enctype: 'multipart/form-data'
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/planboard';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });

        /*
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
        });*/
    },
    update: function() {
        var data = {
           title: $('#title').val(),
           content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function() {
            alert(JSON.stringify(error));
        });
    },
    delete: function() {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function() {
            alert(JSON.stringify(error));
        });
    }
};

main.init();