package amata1219.walkure.spigot.data.processor;

import java.util.regex.Pattern;

public class StringMeasurer {

    private static final Pattern DOUBLE_BYTE_CHARACTER_CHECKER = Pattern.compile("^[^!-~｡-ﾟ]+$");
    private static final char[] halfSizeChars = ".,:;()!'| `[]".toCharArray();

    public static int measure(String s) {
        float length = 0;
        for(char c : s.toCharArray()) length += length(c);
        System.out.println(s.getBytes().length + " : " + length);
        return (int) length;
    }

    private static float length(char c) {
        for (char halfSizeChar : halfSizeChars)
            if (c == halfSizeChar) return 0.5f;

        return DOUBLE_BYTE_CHARACTER_CHECKER.matcher(String.valueOf(c)).matches() ? 2.25f : 1.0f;
    }

}
