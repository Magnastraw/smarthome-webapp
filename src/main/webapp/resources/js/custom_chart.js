var intervalId = [];
function createNewChart(configurationJson, requestDataOptions, refreshInterval, inputDiv, id) {
    var chart,
        options = $.parseJSON(configurationJson),
        dataSource = $.parseJSON(requestDataOptions);

    Highcharts.setOptions({
        global : {
            timezone: moment.tz.guess()
        }
    });

    //stop setInterval
    createEmptyChart(function () {
        if (dataSource.chartInterval==='Live') {
            requestLiveData(function () {
                chart.reflow();
                chart.hideLoading();
            });
            intervalId.push( setInterval(function () {
                requestLiveData(function () {
                });
            }, refreshInterval));
        } else {
            requestData(function () {
                chart.reflow();
                chart.hideLoading();
            });
            intervalId.push ( setInterval(function () {
                requestData(function () {
                });
            }, refreshInterval));
        }
    });

    function requestLiveData(callback) {
        $.ajax({
            url: window.location+'jsonData',
            data: JSON.stringify(dataSource),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: 'POST',
            success: function (data) {
                $.each(data, function (pos, series) {
                    var shift = chart.series[pos].data.length > 15;
                    // console.log(chart.series[pos].data);
                    // console.log(moment.tz(series.data[0].x, moment.tz.guess()).format());
                    // console.log(moment.parseZone(moment.tz(series.data[0].x, moment.tz.guess()).format()).format());
                    // console.log(moment.tz(series.data[0].x, moment.tz.guess()).format());
                    // console.log(moment.tz(series.data[0].x, moment.tz.guess()).utcOffset());
                    // console.log(moment.tz(moment.tz.guess()).utcOffset());
                    // console.log(series.data[0].x);
                    console.log(series.data);

                    chart.series[pos].addPoint({
                        x: series.data[0].x,
                        y: series.data[0].y
                    }, false, shift);
                });
                chart.redraw();
                callback();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                clearInterval(intervalId);
                alert(xhr.status);
                alert(thrownError);
            },
            cache: false
        });
    }

    function requestData(callback) {
        $.ajax({
            url: window.location+'jsonData',
            data: JSON.stringify(dataSource),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: 'POST',
            success: function (data) {
                $.each(data, function (pos, series) {
                    // $.each(series.data, function (pos_1, series_1){
                    //     series_1.x=series_1.x+moment.tz(moment.tz.guess()).utcOffset()*60*1000;
                    // });
                        console.log(series.data);
                        chart.series[pos].setData(series.data);
                });
                chart.redraw();
                callback();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                clearInterval(intervalId);
                alert(xhr.status);
                alert(thrownError);
            },
            cache: false
        });
    }

    function createEmptyChart(callback) {
        fullscreenMode();
        chart = new Highcharts.Chart(options);
        chart.showLoading();
        chart.reflow();
        callback();
    }

    function fullscreenMode() {
        options.exporting = {
            buttons: {
                customButton: {
                    _titleKey: 'fullscreenTooltip',
                    x: -40,
                    symbol: 'symbolFullscreen'
                }
            }
        };
        /*custom fullscreen button*/
        options.exporting.buttons.customButton.onclick = function () {
            $('.ui-layout-center').toggleClass('ui-layout-center-visible');
            $('.ui-layout-center .ui-layout-unit-content').toggleClass('ui-layout-unit-content-visible');
            $('#li_' + id).toggleClass('li_overflow');
            $('#' + inputDiv).toggleClass('chart-container chart-border modal chart-in ');
            $('header').toggleClass("x-tree-icon-leaf");
            $("#centerForm\\:button_"+id).toggleClass("delete-chart-button");
            $("#center").toggleClass('z-index-centr');
            chart.reflow();
        };

        /*custom fullscreen icon*/
        $.extend(Highcharts.Renderer.prototype.symbols, {
            symbolFullscreen: function (x, y, w, h) {
                return [
                    'M', x, y + (3 * h) / 5,
                    'L', x, y + h,
                    'L', x + 2 * w / 5, y + h,
                    'M', x, y + h,
                    'L', x + w, y,
                    'M', x + (3 * w) / 5, y,
                    'L', x + w, y,
                    'L', x + w, y + (2 * h / 5)
                ]
            }
        });
    }


    $("#centerForm\\:button_"+id).click(function () {
        gridster.remove_widget($(this).closest('li'));
        chart.destroy();
        clearInterval(intervalId);
    });
}

function clearHighchartsContainer() {
    $.each(Highcharts.charts, function (pos, chart) {
        if(Highcharts.charts[pos]!=undefined) {
            Highcharts.charts[pos].destroy();
        }
    });
}

Highcharts.setOptions({
    global: {
        useUTC: false
    }
});