package enums;

public enum Language {
    ENGLISH("English"),
    UKRAINIAN("Українська"),
    RUSSIAN("Русский");

    private String title;

    Language(String title) {
        this.title = title;
    }

    public static Language getLanguageByTitle(String language) {
        switch (language) {
            case "English":
                return Language.ENGLISH;
            case "Українська":
                return Language.UKRAINIAN;
            case "Русский":
                return Language.RUSSIAN;
        }

        return null;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
