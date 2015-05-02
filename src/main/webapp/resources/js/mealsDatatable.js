/**
 * Created by eugene on 26.04.15.
 */

var failedNote;

var form;

function makeEditable(ajaxUrl) {

    form = $('#addUpdateForm');

    $('#add').click(function () {
        $('#item_id').val(0);
        $('#editRow').modal();
    });

    $('.update').click(function () {
        $('#item_id').val($(this).attr('id'));
        updateRow($(this).attr('id'));
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).attr('id'));
    });

    $('#addUpdateForm').submit(function () {
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
