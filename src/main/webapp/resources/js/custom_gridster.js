var gridster;
function savePosition() {
    document.getElementById("centerForm:widgetPos").value = JSON.stringify(gridster.serialize());
    remoteSave();
}
function resizeChart() {
    $.each(Highcharts.charts, function (pos, chart) {
        if (Highcharts.charts[pos] != undefined) {
            Highcharts.charts[pos].reflow();
        }
    });
}
$(function () {
    gridster = $(".gridster  ul").gridster({
        widget_base_dimensions: [$(window).width() / 10, $(window).height() / 10],
        widget_margins: [8, 8],
        minSizeY: 6,
        minSizeX: 6,
        helper: 'clone',
        max_cols: ($(window).width() / 12) * 10,
        draggable: {
            handle: 'header',
            stop: function (e, ui, $widget) {
                savePosition();
            }
        },
        resize: {
            enabled: true,
            max_size: [8, 8],
            min_size: [3, 3],
            resize: function (e, ui, $widget) {
                resizeChart();
            },
            stop: function (e, ui, $widget) {
                resizeChart();
                savePosition();
                console.log(new Date());
            }
        }
    }).data('gridster');
});
