package enums;

public enum Language {
    UKRAINIAN("Українська"),
    ENGLISH("English"),
    RUSSIAN("Русский");

    private String title;

    Language(String title) {
        this.title = title;
    }

    public static Language getLanguageByTitle(String language) {
        switch (language) {
            case "Українська":
                return Language.UKRAINIAN;
            case "English":
                return Language.ENGLISH;
            case "Русский":
                return Language.RUSSIAN;
        }

        return null;
    }

    @Override
    public String toString() {
        return title;
    }
}
