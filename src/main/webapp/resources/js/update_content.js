var intervalId = [];
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

$(document).ready(function() {
    setTimezoneCookie();
});

function setTimezoneCookie() {
    if (null == getCookie("timezoneCookie")) {
        document.cookie = "timezoneCookie="+moment.tz.guess();
    }
}

function getCookie(cookieName) {
    var cookieValue = document.cookie;
    var cookieStart = cookieValue.indexOf(" " + cookieName + "=");
    if (cookieStart == -1) {
        cookieStart = cookieValue.indexOf(cookieName + "=");
    }
    if (cookieStart == -1) {
        cookieValue = null;
    } else {
        cookieStart = cookieValue.indexOf("=", cookieStart) + 1;
        var cookieEnd = cookieValue.indexOf(";", cookieStart);
        if (cookieEnd == -1) {
            cookieEnd = cookieValue.length;
        }
        cookieValue = unescape(cookieValue.substring(cookieStart, cookieEnd));
    }
    return cookieValue;
}