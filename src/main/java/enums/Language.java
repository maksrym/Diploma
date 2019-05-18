package enums;

public enum Language {
    UKRAINIAN("Українська"),
    ENGLISH("English"),
    RUSSIAN("Русский");

    private String title;

    Language(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
