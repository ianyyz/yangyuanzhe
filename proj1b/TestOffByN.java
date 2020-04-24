import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    static CharacterComparator OffBy5 = new OffByN(5);

    @Test(timeout = 1000)
    public void TestEqualChars(){
        assertTrue(OffBy5.equalChars('a','f'));
        assertTrue(OffBy5.equalChars('f','a'));
        assertFalse(OffBy5.equalChars('h','f'));

    }
}
