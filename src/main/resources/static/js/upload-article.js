
function uploadArticle() {
    var articleName = $("#article-name").val();
    var articleAbbreviation = $("#article-abbreviation").val();
    var articleText = markdownEditor.value();

    if(fieldsIsEmpty(articleName, articleAbbreviation) || textAreaIsEmpty(articleText)) {
        return;
    }


}

function fieldsIsEmpty(name, abbreviation) {
    var hasEmptyField = false;
    if(!name) {
        var nameField = $("#article-name");

        nameField.addClass("is-invalid");
        nameField.keydown(function() {
            if (this.value) {
                nameField.removeClass("is-invalid");
                nameField.off("keydown");
            }
        });
        hasEmptyField = true;
    }

    if(!abbreviation) {
        var abbreviationField = $("#article-abbreviation");

        abbreviationField.addClass("is-invalid");
        abbreviationField.keydown(function() {
            if (this.value) {
                abbreviationField.removeClass("is-invalid");
                abbreviationField.off("keydown");
            }
        });

        hasEmptyField = true;
    }

    return hasEmptyField;
}

function textAreaIsEmpty(text) {
    if(!text) {
        alert("Article can't be empty");
        return true;
    }
    return false;
}

function getCategories() {

}