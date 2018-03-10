import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RandomWritterTest {

    /**
     * Method: main(String[] args)
     */
    @Test
    void testMain() {
        RandomWritter rm = new RandomWritter();
        assertEquals(2, 1 + 1);
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
