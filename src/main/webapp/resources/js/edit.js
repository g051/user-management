$(function () {
    $('.updateData').click(function () {
        console.log('updateData')
        var id = $(this).attr('id').substring(7);
        $('#email_' + id).hide();
        $('#text_email_' + id).show();
        $('#text_email_' + id).focus();
        $(this).hide();
        $('#save_' + id).show();

    });

});