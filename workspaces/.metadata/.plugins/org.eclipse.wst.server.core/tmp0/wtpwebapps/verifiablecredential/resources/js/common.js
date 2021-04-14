$(document).ready(function (){
    var winH;
    var topbarH;

    winH = $(document).height();
    topbarH = $("#topbar").outerHeight();

    var viewportH = $(document).height() - $("#topbar").outerHeight();

    $("#contents-area").css("max-height", viewportH + "px");
});
