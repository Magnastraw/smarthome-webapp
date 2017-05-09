function updateContent(url,remoteName){
    intervalId.push(setInterval(function () {
        requestUpdate(url,remoteName);
    },10000));
}
function requestUpdate(url,remoteName){
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            if(data){
                window[remoteName]();
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            clearInterval(intervalId);
            alert(xhr.status);
            alert(thrownError);
        },
        cache: false
    });
}