var main = {
    init : function() {
        var _this = this;
        $('#btn-new-row').on('click', function() {
            _this.new_row();
        });
        $('#btn-save').on('click', function() {
            _this.save();
        });
        $('#btn-update').on('click', function() {
            _this.update();a
        });
        $('#btn-delete').on('click', function() {
            _this.delete();
        })
    },
    new_row : function() {
        var table_body = document.getElementsByName("plan_table")[0];
        var first_tr = table_body.firstElementChild;
        var new_tr = first_tr.cloneNode(true);

        var tmps = new_tr.children;
        for (var i=0; i<(tmps.length-1); i++){
            var tmp = tmps[i].firstElementChild;
            tmp.reset();
        }

        table_body.appendChild(new_tr);
    },
    save : function() {

        var plan = {
            title: $('#title').val(),
            location: $('#location').val(),
            startDate: $('#start_date').val(),
            endDate: $('#end_date').val(),
            tripState: $('select[name="state"]').val()
        };

        var table_name = '#table1 '
        var datePlans = [];
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
            window.location.href = '/plans/save';
        }).fail(function(error) {
            alert(JSON.stringify(allData));
        });
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