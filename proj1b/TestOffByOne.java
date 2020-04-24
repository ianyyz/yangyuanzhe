import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test(timeout = 1000)
    public void testEqualChars(){
        assertFalse(offByOne.equalChars('a','a'));
        assertFalse(offByOne.equalChars('a','c'));
        assertFalse(offByOne.equalChars('a','B'));
        assertTrue(offByOne.equalChars('z','y'));
        assertTrue(offByOne.equalChars('&','%'));
    }
}