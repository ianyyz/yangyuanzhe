import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test (timeout =  1000)
    public void testisPalindrome() {
        assertTrue( palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("abda"));
        assertTrue(palindrome.isPalindrome("abba"));
        assertTrue(palindrome.isPalindrome("annna"));

    }

    @Test (timeout = 1000)
    public void testisPalindromeOffByOne(){
        CharacterComparator cc = new OffByOne();
        assertTrue((palindrome.isPalindrome("flake",cc)));
        assertFalse((palindrome.isPalindrome("fla",cc)));
        assertTrue((palindrome.isPalindrome("f",cc)));
        assertTrue((palindrome.isPalindrome("",cc)));
        assertTrue((palindrome.isPalindrome("ndem",cc)));
        assertFalse(palindrome.isPalindrome("adch",cc));

    }
}