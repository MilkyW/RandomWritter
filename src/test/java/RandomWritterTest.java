import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wxr.RandomWritter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class RandomWritterTest {

    /**
     * Method: main(String[] args)
     */
    @Test
    void testMain() {
        RandomWritter rm = new RandomWritter();
        String in = "-enable complete sentence\n" +
                "-enable complete sentence\n" +
                "tint.txt\n-disable complete sentence\n" +
                "hamlet.txt\n3\n1.5\n3\n10\n";
        String out = "";
        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes());
        System.setIn(is);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        rm.main();
        out = os.toString();
        String[] srm = rm.out.split(" ");
        assertEquals(10 + 2, srm.length);

        in = "-disable complete sentence\n" +
                "-enable complete sentence\n" +
                "hamlet.txt\n1.5\n1\n3\n100\n";
        is = new ByteArrayInputStream(in.getBytes());
        System.setIn(is);
        rm.main();
        out = os.toString();
        srm = rm.out.split(" ");
        assertTrue(100 + 2 <= srm.length);
        char c = rm.out.charAt(rm.out.length() -2);
        assertTrue(c == '?' || c == '.' || c == '!');
    }


    /**
     * Method: validNum(String num, int[] n)
     */
    @Test
    void testValidNum() {
        int[] n = new int[1];
        assertEquals(1,
                RandomWritter.validNum("9.123", n));
        assertEquals(2,
                RandomWritter.validNum("1", n));

        assertEquals(0,
                RandomWritter.validNum("123", n));
        assertEquals(123, n[0]);

    }

    @Test
    void testValidLen() {
        int[] n = new int[1];
        assertEquals(1,
                RandomWritter.validLen("9.123", n));
        assertEquals(2,
                RandomWritter.validLen("3", n));
        assertEquals(3,
                RandomWritter.validLen("0", n));

        assertEquals(0,
                RandomWritter.validLen("123", n));
        assertEquals(123, n[0]);
    }

} 
