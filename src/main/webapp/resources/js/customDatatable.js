
function makeEditable(ajaxUrl) {
    form = $('#detailsForm')

    $('#add').click(function () {
        form.find(":input").each(function () {
            $(this).val("");
        });
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('.update').click(function () {
        console.log('update click');
        updateRow($(this).attr("id"));
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

function enable(id, chkbox) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var enabled = chkbox.is(":checked");
    chkbox.parent().parent().css("text-decoration", enabled ? "none" : "line-through");
    $.ajax({
        url: ajaxUrl + id + '/enable',
        type: 'POST',
        data: 'enabled=' + enabled,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function () {
            successNoty(enabled ? 'Enabled' : 'Disabled');
        }
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNote();
    var errorInfo = $.parseJSON(jqXHR.responseText);

    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>" + errorInfo.cause + "<br>" + errorInfo.detail,
        type: 'error',
        layout: 'bottomRight'
    });
}

function renderDate(date, type, row) {
    if (type == 'display') {
        var dateObject = new Date(date);
        return '<span>' + dateObject.toISOString().substring(0, 10) + '</span>';
    }
    return date;
}

function renderEmail(data, type, row) {
    if (type == 'display') {
        return '<a href="mailto:' + data + '">' + data + '</a>';
    }
    return data;
}

function renderUpdateBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-primary" onclick="updateRow(' + row.id + ')">Update</a>';
    }
    return data;
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ')">Delete</a>';
    }
    return data;
}

function renderCheckbox(data, type, row) {
    if (type == 'display') {
        return '<input type="checkbox"' + (data ? ' checked ' : ' ') + 'onclick="enable(' + row.id + ',$(this))"/>';
    }
    return data;
}
