package scanner;

class IsInt implements IsToken{
    public static final IsToken INSTANCE = new IsInt();

    @Override
    public boolean isToken(char x) {
        return x != ' ';
    }
}
