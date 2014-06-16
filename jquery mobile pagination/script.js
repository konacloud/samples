var info = {
    cat : null,
    page : null,
    text: null
}

var tramiteSelected = null;
var tramiteSelectedName = null;

// $(document).on("mobileinit", function(){
//     $.mobile.buttonMarkup.hoverDelay = 0;
//     $.mobile.defaultPageTransition   = 'none';
//     $.mobile.defaultDialogTransition = 'none';
// });


$(document).on('vclick', '#movie-list li', function(){

    event.preventDefault();

    $.mobile.navigate( "#headline" );
    $.mobile.changePage( "#headline");

    $("#headlineTitle",$.mobile.activePage).text($(this).attr('data-name'));

    var idd = $(this).attr('data-id');

    info.cat = idd;
    info.page = 0;

    $('#movie-data',$.mobile.activePage).empty();

    fetchDataFromServer();
    return false;
});

$(document).on('vclick', '#movie-data li', function(){

    event.preventDefault();

    var idd = $(this).attr('data-id');
    tramiteSelected = idd;

    var name = $(this).attr('data-name');
    tramiteSelectedName = name;

    //$.mobile.changePage( "#tramiteDetail", { transition: "slide", changeHash: false });
    $.mobile.navigate( "#tramiteDetail" );

    //traemos los detalles del tramite del servidor

    $("#headerTramiteDetail").text(tramiteSelectedName);

    loading = true; //interlock to prevent multiple calls
    $.mobile.loading('show');

    var url = 'http://developer.konacloud.io/api/taio/TramitesUY/r_getTramiteById?id='+tramiteSelected;

    $.getJSON(url, function(data) {

            var tramite = data.data;

            $("#queEsId",$.mobile.activePage).text(tramite.queEs);

            if (tramite.dondeyCuando!=null)
                $("#dondeYCuandoId",$.mobile.activePage).text(tramite.dondeyCuando.otrosDatosUbicacion);
            else
                $("#dondeYCuandoId",$.mobile.activePage).text("N/A");

            $("#requisitos",$.mobile.activePage).text(tramite.requisitos);

            if (tramite.tenerEnCuenta!=null && tramite.tenerEnCuenta.costo!=null && tramite.tenerEnCuenta.otrosDatosDeInteres!=null && tramite.tenerEnCuenta.formaDeSolicitarlo!=null){
                var str = "<b>Costo:</b> " + tramite.tenerEnCuenta.costo + "<br>" +
                "<b>Otros datos:</b> " + tramite.tenerEnCuenta.otrosDatosDeInteres + "<br>" +
                "<b>Forma de solicitud:</b> " + tramite.tenerEnCuenta.formaDeSolicitarlo;

                $("#observacionesId",$.mobile.activePage).html(str);

                var depende = "<b> Area </b> : " + tramite.dependeDe.area + "<br>" +
                              "<b> Organismo </b> : " + tramite.dependeDe.organismo;

                $("#dependeDeId",$.mobile.activePage).html(depende);
            }
            loading = false;
            $.mobile.loading('hide');
    });

    return false;
});

var fetchDataFromServer = function(){
    $.getJSON('http://developer.konacloud.io/api/taio/TramitesUY/mr_tramiteCats?cat='+info.cat+"&p="+info.page , function(data) {


            var array = data.data;
            array.forEach(function(entry) {
                var tramTitle = entry.titulo;
                var li = $("<li/>");
                li.attr('data-id', entry.tramiteId);
                li.attr('data-name', entry.titulo);

                var title = $("<a/>").append(tramTitle);
                title.addClass( "ui-btn ui-btn-a ui-btn-icon-right ui-icon-carat-r" );
                li.append(title);


                li.appendTo("#movie-data");
            });

            info.page = info.page+1;
             //refresh listview
            $('#movie-data',$.mobile.activePage).listview('refresh');
            loading = false;
            $.mobile.loading('hide');
    });
};

var fetchDataFromServerRegExp = function(name){

    $.getJSON('http://developer.konacloud.io/api/taio/TramitesUY/r_searchTramitesByTitulo?name='+info.text+'&p='+info.page , function(data) {

            console.log(data);

            var array = data.data;
            array.forEach(function(entry) {
                var tramTitle = entry.titulo;
                var li = $("<li/>");
                li.attr('data-id', entry.tramiteId);
                li.attr('data-name', entry.titulo);

                var title = $("<a/>").append(tramTitle);
                title.addClass( "ui-btn ui-btn-a ui-btn-icon-right ui-icon-carat-r" );
                li.append(title);


                li.appendTo("#search-page-list-view");
            });

            info.page = info.page+1;
             //refresh listview
            $('#search-page-list-view',$.mobile.activePage).listview('refresh');
            loading = false;
            $.mobile.loading('hide');
    });
};

$(window).bind('scrollstart', function () {

  //event.preventDefault();

  var activePage = $.mobile.activePage.attr("id");

  if (activePage!="headline" && activePage!="searchPage")
    return;


  if (isAtBottom()){
    loading = true; //interlock to prevent multiple calls
    $.mobile.loading('show');

    if (activePage=="headline")
        fetchDataFromServer();
    else
        fetchDataFromServerRegExp(info.text);
    }
    //return false;
});

$(window).bind('scrollstop', function () {
  //event.preventDefault();
  //return false;
});

function isAtBottom(){
   return ($(window).scrollTop() >= $(document).height() - $(window).height() - 100)
}


$(document).ready(function(){
  $("#btnBuscar").click(function() {

    event.preventDefault();

    var name = $("#searchTramite",$.mobile.activePage).val();

    $.mobile.navigate( "#searchPage" );
    $.mobile.changePage( "#searchPage");

    $("#searchPageTitle",$.mobile.activePage).text("Resultados para " + name);

    info.text = name;
    info.page = 0;

    $('#search-page-list-view',$.mobile.activePage).empty();
    fetchDataFromServerRegExp(name);
    return false;
  });

});


$(document).on('vclick', '#search-page-list-view li', function(){

    event.preventDefault();

    var idd = $(this).attr('data-id');
    tramiteSelected = idd;

    var name = $(this).attr('data-name');
    tramiteSelectedName = name;

    $.mobile.navigate( "#tramiteDetail" );

    //traemos los detalles del tramite del servidor
    $("#headerTramiteDetail",$.mobile.activePage).text(tramiteSelectedName);

    loading = true; //interlock to prevent multiple calls
    $.mobile.loading('show');

    var url = 'http://developer.konacloud.io/api/taio/TramitesUY/r_getTramiteById?id='+tramiteSelected;

    $.getJSON(url, function(data) {

            var tramite = data.data;

            $("#queEsId",$.mobile.activePage).text(tramite.queEs);

            if (tramite.dondeyCuando!=null)
                $("#dondeYCuandoId",$.mobile.activePage).text(tramite.dondeyCuando.otrosDatosUbicacion);
            else
                $("#dondeYCuandoId",$.mobile.activePage).text("N/A");

            $("#requisitos",$.mobile.activePage).text(tramite.requisitos);

            if (tramite.tenerEnCuenta!=null && tramite.tenerEnCuenta.costo!=null && tramite.tenerEnCuenta.otrosDatosDeInteres!=null && tramite.tenerEnCuenta.formaDeSolicitarlo!=null){
                var str = "<b>Costo:</b> " + tramite.tenerEnCuenta.costo + "<br>" +
                "<b>Otros datos:</b> " + tramite.tenerEnCuenta.otrosDatosDeInteres + "<br>" +
                "<b>Forma de solicitud:</b> " + tramite.tenerEnCuenta.formaDeSolicitarlo;

                $("#observacionesId",$.mobile.activePage).html(str);

                var depende = "<b> Area </b> : " + tramite.dependeDe.area + "<br>" +
                              "<b> Organismo </b> : " + tramite.dependeDe.organismo;

                $("#dependeDeId",$.mobile.activePage).html(depende);
            }
            loading = false;
            $.mobile.loading('hide');
    });
    return false;
});
