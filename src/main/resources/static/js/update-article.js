function updateArticle(element) {
    $(element).prop("disabled", true);

    var articleTitle = $("#article-title").val();
    var articleAbbreviation = $("#article-abbreviation").val();
    var articleText = markdownEditor.value();
    var articleLanguage = $("#language").val();

    if(textAreaIsEmpty(articleText)) {
        return;
    }

    var categories = getCategories();

    var article = new Object();
    article.title = articleTitle;
    article.abbreviation = articleAbbreviation;
    article.text = articleText;
    article.language = articleLanguage;
    article.categories = categories;
    article.pageId = getUrlParameter("page");

    $.ajax({
        url: decodeURI("/article/edit/" + articleTitle),
        method: "POST",
        contentType: "application/json",
        dataType : "json",
        data: JSON.stringify(article)
    }).always(function () {
        window.location.href = "/article/" + encodeURI(articleTitle);
    });
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