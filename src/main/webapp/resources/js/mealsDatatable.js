/**
 * Created by eugene on 26.04.15.
 */

var failedNote;

var form;

function makeEditable(ajaxUrl) {

    form = $('#addUpdateForm');

    $('#add').click(function () {
        form.find(":input").each(function () {
            $(this).val("");
        });
        $('#id').val(0);
        $('#editRow').modal();
    });

    form.submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });

    init();
}

function init() {
    $('#filter').submit(function () {
        filterTable();
        return false;
    });

    $('.date-picker').datetimepicker({
        timepicker: false,
        format: 'Y-m-d'
    });

    $('.time-picker').datetimepicker({
        datepicker: false,
        format: 'H:i'
    });

    $('.datetime-picker').datetimepicker({
        format: 'Y-m-d H:i'
    });
    coloredTable();
}

function filterTable() {
    var frm = $('#filter');
    $.ajax({
        type: "POST",
        url: frm.attr('action'),
        data: frm.serialize(),
        success: function(data){
            updateByData(data);
            coloredTable();
        }
    });
}

function coloredTable() {
    $('td.limitExceeded').each(function () {
        if ($(this).text() === 'true') {
            $(this).parent().css("color", "red");
        } else {
            $(this).parent().css("color", "green");
        }
    });
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        oTable_datatable.fnClearTable();
        $.each(data, function (key, item) {
            oTable_datatable.fnAddData(item);
        });
        oTable_datatable.fnDraw();
        coloredTable();
    });
}

function updateByData(data) {
    oTable_datatable.fnClearTable();
    $.each(data, function (key, item) {
        oTable_datatable.fnAddData(item);
    });
    oTable_datatable.fnDraw();
}

function updateRow(id) {
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    $.ajax({
        type: "DELETE",
        url: ajaxUrl + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(data) {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function closeNote() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNote();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNote();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}

function renderUpdateBtn(data, type, row) {
    if (type == 'display')
        return '<a class="btn btn-xs btn-info" onclick="updateRow(' + row.id + ')">' + updateButton + '</a>';
    return data;
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display')
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ')">' + deleteButton + '</a>';
    return data;
}

function renderDate(date, type, row) {
    if (type == 'display') {
        return '<span>' + date + '</span>';
    }
    return date;
}
