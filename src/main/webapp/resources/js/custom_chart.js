function createNewChart(configurationJson, dataSourceConfigJson, refreshInterval) {
    var chart,
        intervalId,
        rownum = 1,
        options = $.parseJSON(configurationJson),
        dataSource = $.parseJSON(dataSourceConfigJson);

    //stop setInterval!! ?
    createEmptyChart(function () {
        if (refreshInterval == 0) {
                requestData();
        } else if (refreshInterval >= 3600000) {
            intervalId=setInterval(function () {
                requestData(function () {
                    rownum+=100;
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
            url: 'http://localhost:8083/jsonDataLive/' + dataSource.homeId + '/' + dataSource.type + '/obj/' + dataSource.objectId + '/subObj/' + dataSource.subObjectId + '/metric/' + dataSource.metricSpecId + '/rownum/' + rownum,
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
            url: 'http://localhost:8083/jsonData/' + dataSource.homeId + '/' + dataSource.type + '/obj/' + dataSource.objectId + '/subObj/' + dataSource.subObjectId + '/metric/' + dataSource.metricSpecId+'/rownum/'+ rownum,
            dataType: 'json',
            success: function (data) {
                $.each(data, function (pos, series) {
                    $.each(series.data, function (pos, data) {
                        data.x = Date.parse(data.x);
                    });
                    switch (dataSource.type) {
                        case 'm':
                            chart.series[pos].setData(series.data);
                            break;
                        case 'o':
                            chart.series[pos].setData(series.data);

                            break;
                    }
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

    function createEmptyChart(callback) {
        $.ajax({
            url: 'http://localhost:8083/jsonDataConfig/' + dataSource.homeId + '/' + dataSource.type + '/obj/' + dataSource.objectId + '/subObj/' + dataSource.subObjectId + '/metric/' + dataSource.metricSpecId,
            dataType: 'json',
            type: 'POST',
            success: function (data) {
                var yAxis = [];
                var series = [];
                $.each(data, function (pos, dataObj) {
                    series.push(dataObj.series);
                    options.title = dataObj.title.title;
                    yAxis.push(dataObj.yAxis);

                });
                switch (dataSource.type) {
                    case 'm':
                        options.yAxis = yAxis[0];
                        break;
                    case 'o':
                        options.yAxis = yAxis;
                        break;
                }
                options.series = series;
                fullscreenMode();
                console.log(options);
                chart = new Highcharts.Chart(options);
                callback();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            },
            cache: false
        });

    }


    function fullscreenMode() {
        /*custom fullscreen button*/
        options.exporting.buttons.customButton.onclick = function () {
            $('.ui-layout-center').toggleClass('ui-layout-center-visible');
            $('.ui-layout-center .ui-layout-unit-content').toggleClass('ui-layout-unit-content-visible');
            $('.chart-container').toggleClass('modal');
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