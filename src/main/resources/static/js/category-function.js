function addTag(name, parent) {
    if(!name) {
        var tagName = $("#tag-name");

        tagName.addClass("is-invalid");
        tagName.keydown(function() {
            if (this.value) {
                tagName.removeClass("is-invalid");
                tagName.off("keydown");
            }
        });
        return;
    }

    if(parent) {
        parent = " <span class='badge badge-light'>" + parent + "</span>";
    } else {
        parent = "";
    }

    $("#tagField").prepend(
        "<div class='col-auto px-1 py-1'>" +
        "   <div class='bg-dark p-2 text-white'>" +
        "       <span>" + name + parent + "</span>" +
        "       <button type='button' class='close'  aria-label='Close' onclick='$(this).parent().parent().remove()'>" +
        "           <span aria-hidden='true'>&times;</span>" +
        "       </button>" +
        "   </div>" +
        "</div>");

    $("#addTagWindow").modal("toggle");
}