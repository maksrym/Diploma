
function uploadArticle() {
    var articleTitle = $("#article-title").val();
    var articleAbbreviation = $("#article-abbreviation").val();
    var articleText = markdownEditor.value();
    var articleLanguage = $("#language").val();

    if(titleIsEmpty(articleTitle) || textAreaIsEmpty(articleText)) {
        return;
    }

    var categories = getCategories();

    var article = new Object();
    article.title = articleTitle;
    article.abbreviation = articleAbbreviation;
    article.text = articleText;
    article.language = articleLanguage;
    article.categories = categories;

    alert(JSON.stringify(article));
    $.ajax({
        url: "/article/add",
        method: "POST",
        contentType: "application/json",
        dataType : "json",
        data: JSON.stringify(article)
    })
}

function titleIsEmpty(title) {
    if(!title) {
        var titleField = $("#article-title");

        titleField.addClass("is-invalid");
        titleField.keydown(function() {
            if (this.value) {
                titleField.removeClass("is-invalid");
                titleField.off("keydown");
            }
        });
        return true;
    }
    return false;
}

function textAreaIsEmpty(text) {
    if(!text) {
        alert("Article can't be empty");
        return true;
    }
    return false;
}

function getCategories() {
    var categories = [];

    $("span.category-name").each(function () {
        var category = new Object();
        category.id = $(this).attr("data-id");
        categories.push(category);
    });

    return categories
}