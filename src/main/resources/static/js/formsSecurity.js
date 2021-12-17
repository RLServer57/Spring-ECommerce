$('form').on('submit', function (e) {
    e.preventDefault();
    $('button').prop('disabled', true);
    this.submit();
});