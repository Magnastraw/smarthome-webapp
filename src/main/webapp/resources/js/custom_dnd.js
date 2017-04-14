function initDND() {
    $('#centerForm\\:chartSettings\\:objectTree').find('.ui-treenode').draggable({
        helper: 'clone',
        scope: 'treetotable',
        zIndex: ++PrimeFaces.zindex
    });

    $('#centerForm\\:chartSettings\\:selectedObjects').droppable({
        activeClass: 'ui-state-active',
        hoverClass: 'ui-state-highlight',
        tolerance: 'pointer',
        scope: 'treetotable',
        drop: function(event, ui) {
            var data_rowkey = ui.draggable.attr('data-rowkey');
            treeToTable([
                {name: 'data_rowkey', value:  data_rowkey}
            ]);
        }
    });
    $('#centerForm\\:chartSettings\\:metricTree').find('.ui-treenode').draggable({
        helper: 'clone',
        scope: 'treetometric',
        zIndex: ++PrimeFaces.zindex
    });

    $('#centerForm\\:chartSettings\\:selectedMetrics').droppable({
        activeClass: 'ui-state-active',
        hoverClass: 'ui-state-highlight',
        tolerance: 'pointer',
        scope: 'treetometric',
        drop: function(event, ui) {
            var data_rowkey = ui.draggable.attr('data-rowkey');
            treeToMetric([
                {name: 'data_rowkey', value:  data_rowkey}
            ]);
        }
    });


}

$(function() {
    initDND();
});
