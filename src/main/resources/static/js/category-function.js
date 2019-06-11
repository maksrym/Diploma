function addCategory(name, id) {
    if(!name) {
        var categoryName = $("#category-name");

        categoryName.addClass("is-invalid");
        categoryName.keydown(function() {
            if (this.value) {
                categoryName.removeClass("is-invalid");
                categoryName.off("keydown");
            }
        });
        return;
    }

    $("#category-area").prepend(
        "<div class='col-auto px-1 py-1 category'>" +
        "   <div class='bg-dark p-2 text-white'>" +
        "       <span class='category-name' data-id=" + id + ">" + name + "</span>" +
        "       <button type='button' class='close'  aria-label='Close' onclick='$(this).parent().parent().remove()'>" +
        "           <span aria-hidden='true'>&times;</span>" +
        "       </button>" +
        "   </div>" +
        "</div>");

    $("#addCategoryWindow").modal("toggle");
}

function clearCategoryArea() {
    $(".category").remove();
};

function updateCategoryList() {
    var language = $("#language").val();

    $.get(
        "/category/getList",
        {
            language: language
        }
    ).done(function (data) {
        var select = $("#category-name");

        select.select2("destroy");
        select.html("<option hidden disabled selected value></option>");

        data.forEach(function (category) {
            var option = $("<option/>");
            option.val(category.id).text(category.name);

            select.append(option);
        })

        select.select2({
            dropdownParent: $('#addCategoryWindow'),
            theme: "bootstrap",
            width: "250px"
        });
    });
}

