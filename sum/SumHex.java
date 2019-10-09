public class SumHex {

    public static int myParseInt(String arg) {
        if (arg.length() > 2 && Character.toLowerCase(arg.charAt(1)) == 'x') {
            return Integer.parseUnsignedInt(arg.substring(2), 16);
        } else {
            return Integer.parseInt(arg);
        }
    }
    public static void main(String[] args) {
        int result = 0;
        for (String arg : args) {
            arg += " ";
            int lastDigit = 0;
            for (int position = 0; position < arg.length(); position++){
                if (Character.isWhitespace(arg.charAt(position))){
                    if (position > lastDigit) {
                        result += myParseInt(arg.substring(lastDigit, position));
                    }
                    lastDigit = position + 1;
                }
            }
        }
        System.out.println(result);
    }
}
