function createNewChart(configurationJson, requestDataOptions, refreshInterval, inputDiv, id) {
    var chart,
        intervalId,
        options = $.parseJSON(configurationJson),
        dataSource = $.parseJSON(requestDataOptions);

    //stop setInterval
    createEmptyChart(function () {
        if (refreshInterval == 0) {
            requestData(function () {
            });
        } else if (refreshInterval >= 10000) {
            requestData(function () {
                dataSource.rownum += 100;
            });
            intervalId = setInterval(function () {
                requestData(function () {
                    dataSource.rownum += 100;
                });
            }, refreshInterval);
        } else {
            requestLiveData(function () {
                dataSource.rownum++;
            });
            intervalId = setInterval(function () {
                requestLiveData(function () {
                    dataSource.rownum++;
                });
            }, refreshInterval);
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
                    console.log(chart.series[pos].data);
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
                console.log(dataSource);
                console.log(data);
                $.each(data, function (pos, series) {
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
        chart.destroy();
        $.each(Highcharts.charts, function (pos, chart) {
            if (Highcharts.charts[pos] == undefined) {
                Highcharts.charts.splice(pos, 1);
            }
        });
        gridster.remove_widget($(this).closest('li'));
    });
}