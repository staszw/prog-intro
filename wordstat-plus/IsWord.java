public class IsWord implements IsToken {
    @Override
    public boolean isToken(char x) {
        if (Character.getType(x) == Character.LOWERCASE_LETTER)
            return true;
        if (Character.getType(x) == Character.DASH_PUNCTUATION)
            return true;
        return x == '\'';
    }
}
