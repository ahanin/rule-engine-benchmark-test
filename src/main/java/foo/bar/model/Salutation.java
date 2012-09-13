package foo.bar.model;

public enum Salutation {

    MR("Mr."),
    MS("Ms."),
    MRS("Mrs.");

    private final String literal;

    private Salutation(final String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

}
