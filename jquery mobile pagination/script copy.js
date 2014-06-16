$(document).bind('pageinit', function () {
    $.mobile.buttonMarkup.hoverDelay = 0;
    $.mobile.defaultPageTransition   = 'none';
    $.mobile.defaultDialogTransition = 'none';
    $.event.special.swipe.scrollSupressionThreshold = 100;
});

var info = {
    cat : null,
    page : null,
    text: null
}

$.fx.off = true;

var tramiteSelected = null;
var tramiteSelectedName = null;

var mg = {

    cache: {},
    init: function(){

        console.log("init start " + (new Date()).getTime());
        this.cache.headlineTitle = $("#headlineTitle");
        this.cache.movie_data = $("#movie-data");
        this.cache.tramiteDetail = $("#tramiteDetail");
        this.cache.headerTramiteDetail = $("#headerTramiteDetail");
        this.cache.queEsId = $("#queEsId");
        this.cache.dondeYCuandoId = $("#dondeYCuandoId");
        this.cache.requisitos = $("#requisitos");
        this.cache.observacionesId = $("#observacionesId");
        this.cache.dependeDeId = $("#dependeDeId");
        this.cache.search_page_list_view = $("#search-page-list-view");
        this.cache.search_page_list_view = $("#search-page-list-view");
        this.cache.btnBuscar = $("#btnBuscar");
        this.cache.searchPageTitle = $("#searchPageTitle");
        this.cache.search_page_list_view = $("#search-page-list-view");
        this.cache.searchTramite = $("#searchTramite");
        console.log("init end "+(new Date()).getTime());
    }
}

//inicializamos la cache
mg.init();



$(document).on('vclick', '#movie-list li', function(){

    event.preventDefault();

    $.mobile.navigate( "#headline" );
    $.mobile.changePage( "#headline");

    mg.cache.headlineTitle.text($(this).attr('data-name'));

    var idd = $(this).attr('data-id');

    info.cat = idd;
    info.page = 0;

    mg.cache.movie_data.empty();

    fetchDataFromServer();
    return false;
});

$(document).on('vclick', '#movie-data li', function(){

    event.preventDefault();

    var idd = $(this).attr('data-id');
    tramiteSelected = idd;

    var name = $(this).attr('data-name');
    tramiteSelectedName = name;

    $.mobile.navigate( "#tramiteDetail" );

    mg.cache.headerTramiteDetail.text(tramiteSelectedName);

    loading = true;
    $.mobile.loading('show');

    var url = 'http://developer.konacloud.io/api/taio/TramitesUY/r_getTramiteById?id='+tramiteSelected;

    $.getJSON(url, function(data) {

            var tramite = data.data;

            mg.cache.queEsId.text(tramite.queEs);

            if (tramite.dondeyCuando!=null)
                mg.cache.dondeYCuandoId.text(tramite.dondeyCuando.otrosDatosUbicacion);
            else
                mg.cache.dondeYCuandoId.text("N/A");

            mg.cache.requisitos.text(tramite.requisitos);

            if (tramite.tenerEnCuenta!=null && tramite.tenerEnCuenta.costo!=null && tramite.tenerEnCuenta.otrosDatosDeInteres!=null && tramite.tenerEnCuenta.formaDeSolicitarlo!=null){
                var str = "<b>Costo:</b> " + tramite.tenerEnCuenta.costo + "<br>" +
                "<b>Otros datos:</b> " + tramite.tenerEnCuenta.otrosDatosDeInteres + "<br>" +
                "<b>Forma de solicitud:</b> " + tramite.tenerEnCuenta.formaDeSolicitarlo;

                mg.cache.observacionesId.html(str);

                var depende = "<b> Area </b> : " + tramite.dependeDe.area + "<br>" +
                              "<b> Organismo </b> : " + tramite.dependeDe.organismo;

                mg.cache.dependeDeId.html(depende);
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
            mg.cache.movie_data.listview('refresh');
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
            mg.cache.search_page_list_view.listview('refresh');
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

    event.preventDefault();
    mg.cache.btnBuscar.click(function() {

      event.preventDefault();

      //console.log($("#searchTramite"))
      var name = $("#searchTramite").val();
      //console.log(name);

      $.mobile.navigate( "#searchPage" );
      $.mobile.changePage( "#searchPage");

      mg.cache.searchPageTitle.text("Resultados para " + name);

      info.text = name;
      info.page = 0;

      mg.cache.search_page_list_view.empty();
      fetchDataFromServerRegExp(name);
      return false;
  });
  return false;

});


$(document).on('vclick', '#search-page-list-view li', function(){

    event.preventDefault();

    var idd = $(this).attr('data-id');
    tramiteSelected = idd;

    var name = $(this).attr('data-name');
    tramiteSelectedName = name;

    $.mobile.navigate( "#tramiteDetail" );

    //traemos los detalles del tramite del servidor
    mg.cache.headerTramiteDetail.text(tramiteSelectedName);

    loading = true; //interlock to prevent multiple calls
    $.mobile.loading('show');

    var url = 'http://developer.konacloud.io/api/taio/TramitesUY/r_getTramiteById?id='+tramiteSelected;

    $.getJSON(url, function(data) {

            var tramite = data.data;

            mg.cache.queEsId.text(tramite.queEs);

            if (tramite.dondeyCuando!=null)
                    mg.cache.dondeYCuandoId.text(tramite.dondeyCuando.otrosDatosUbicacion);
            else
                    mg.cache.dondeYCuandoId.text("N/A");

            mg.cache.requisitos.text(tramite.requisitos);

            if (tramite.tenerEnCuenta!=null && tramite.tenerEnCuenta.costo!=null && tramite.tenerEnCuenta.otrosDatosDeInteres!=null && tramite.tenerEnCuenta.formaDeSolicitarlo!=null){
                var str = "<b>Costo:</b> " + tramite.tenerEnCuenta.costo + "<br>" +
                "<b>Otros datos:</b> " + tramite.tenerEnCuenta.otrosDatosDeInteres + "<br>" +
                "<b>Forma de solicitud:</b> " + tramite.tenerEnCuenta.formaDeSolicitarlo;

                $("#observacionesId",$.mobile.activePage).html(str);

                var depende = "<b> Area </b> : " + tramite.dependeDe.area + "<br>" +
                              "<b> Organismo </b> : " + tramite.dependeDe.organismo;

                mg.cache.dependeDeId.html(depende);
            }
            loading = false;
            $.mobile.loading('hide');
    });
    return false;
});
