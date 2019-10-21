package scanner;

class IsWord implements IsToken {
    public static final IsToken INSTANCE = new IsWord();

    @Override
    public boolean isToken(char x) {
        if (Character.getType(x) == Character.LOWERCASE_LETTER)
            return true;
        if (Character.getType(x) == Character.DASH_PUNCTUATION)
            return true;
        return x == '\'';
    }
}
