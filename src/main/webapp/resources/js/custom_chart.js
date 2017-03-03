function createNewChart(configurationJson, requestDataOptions, refreshInterval, inputDiv) {
    var chart,
        intervalId,
        rownum = 1,
        options = $.parseJSON(configurationJson),
        dataSource =  $.parseJSON(requestDataOptions);

    //stop setInterval
    createEmptyChart(function () {
        console.log(options.chart.renderTo);
        if (refreshInterval == 0) {
            requestData(function () {
                
            });
        } else if (refreshInterval >= 3600000) {
            intervalId = setInterval(function () {
                requestData(function () {
                    rownum += 100;
                });
            }, 5000);
        } else {
            intervalId = setInterval(function () {
                requestLiveData(function () {
                    rownum++;
                });
            }, refreshInterval);
        }
    });

    function requestLiveData(callback) {
        $.ajax({
            url: 'http://localhost:8083/jsonDataLive',
            data: {
                type: dataSource.type,
                homeId: dataSource.homeId,
                objectId: dataSource.objectId,
                subObjectId: dataSource.subObjectId,
                metricSpecId: dataSource.metricSpecId,
                rownum: rownum
            },
            dataType: 'json',
            type: 'POST',
            success: function (data) {
                $.each(data, function (pos, series) {
                    $.each(series.data, function (pos, data) {
                        data.x = Date.parse(data.x);
                    });
                    var shift = chart.series[pos].data.length > 15;
                    chart.series[pos].addPoint({x: series.data[0].x, y: series.data[0].y}, false, shift);
                    console.log(chart.series[pos].data);
                });
                chart.redraw();
                callback();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
                alert("no new data");
                clearInterval(intervalId);
            },
            cache: false
        });
    }

    function requestData(callback) {
        $.ajax({
            url: 'http://localhost:8083/jsonData',
            data: JSON.stringify(dataSource),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: 'POST',
            success: function (data) {
                $.each(data, function (pos, series) {
                    $.each(series.data, function (pos, data) {
                        data.x = Date.parse(data.x);
                    });
                    chart.series[pos].setData(series.data);
                });
                chart.redraw();
                callback();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
                clearInterval(intervalId);
            },
            cache: false
        });
    }

    function createEmptyChart(callback) {
        fullscreenMode();
        console.log(options);
        chart = new Highcharts.Chart(options);
        callback();


    }

    function fullscreenMode() {
        options.exporting={
            buttons:{
                customButton:{
                    _titleKey:'fullscreenTooltip',
                    x : -40,
                    symbol:'symbolFullscreen'
                }
            }
        };
        /*custom fullscreen button*/
        options.exporting.buttons.customButton.onclick = function () {
            $('.ui-layout-center').toggleClass('ui-layout-center-visible');
            $('.ui-layout-center .ui-layout-unit-content').toggleClass('ui-layout-unit-content-visible');
            $('#'+inputDiv).toggleClass('modal');
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
}