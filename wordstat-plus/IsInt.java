public class IsInt implements IsToken{

    @Override
    public boolean isToken(char x) {
        return Character.isDigit(x) || x == '-';
    }
}
