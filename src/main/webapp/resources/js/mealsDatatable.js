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
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        oTable_datatable.fnClearTable();
        $.each(data, function (key, item) {
            oTable_datatable.fnAddData(item);
        });
        oTable_datatable.fnDraw();
    });
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function (data) {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
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
        return '<a class="btn btn-xs btn-info" onclick="updateRow(' + row.id + ')">Update</a>';
    return data;
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display')
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ')">Delete</a>';
    return data;
}

function renderDate(date, type, row) {
    if (type == 'display') {
        return '<span>' + date + '</span>';
    }
    return date;
}
