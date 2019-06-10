function getUrlParameter(sParam) {
    var sPageURL = window.location.search,
        sURLVariables = sPageURL.split('&'),
        sParameterName;


    for (var i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? null : decodeURIComponent(sParameterName[1]);
        }
    }
};