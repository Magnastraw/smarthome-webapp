function createNewChart(configurationJson, dataSourceConfigJson, refreshInterval) {
    var chart;
    var options = $.parseJSON(configurationJson);
    var dataSource = $.parseJSON(dataSourceConfigJson);

    fullscreenMode();

    chart = new Highcharts.Chart(options);

    if(refreshInterval==0){
        requestData();
    } else {
        setInterval( requestData, refreshInterval);
    }

    function requestData() {
        $.ajax({
            url: 'http://localhost:8083/jsonData/' + dataSource.to + '/' + dataSource.id,
            dataType: 'json',
            success: function (data) {
                $.each(data, function (pos, series) {
                    $.each(series.data, function (pos, data) {
                        data.x = Date.parse(data.x);

                    });
                    chart.series[pos].setData(series.data) ;
                    console.log(series);
                    chart.redraw();

                });
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