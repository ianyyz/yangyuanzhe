public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> someDeque = new LinkedListDeque<Character>();
        for (int idx = 0; idx < word.length(); idx+=1){
            someDeque.addLast(word.charAt(idx));
        }
        return someDeque;
    }

    private Deque recursive(Deque d){
        if (d.size() == 0 || d.size() == 1){
            return d;
        }else {
            if (d.removeLast() == d.removeFirst()){
                recursive(d);
            }else{
                d.addLast(null);
                d.addFirst(null);
            }
        }
        return d;

    }
    public boolean isPalindrome(String word) {
        Deque<Character> someDeque = wordToDeque(word);
        someDeque = recursive(someDeque);
        if (someDeque.size() == 0 || someDeque.size() == 1){
            return true;
        }else{
            return false;
        }

    }

    public Deque recursive(Deque d,CharacterComparator cc){
        if (d.size() == 0 || d.size() == 1){
            return d;
        }else {
            if (cc.equalChars(d.removeFirst().toString().charAt(0),d.removeLast().toString().charAt(0))){
                recursive(d,cc);
            }else{
                d.addLast(null);
                d.addFirst(null);
            }
        }
        return d;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> someDeque = wordToDeque(word);
        someDeque = recursive(someDeque,cc);
        if (someDeque.size() == 0 || someDeque.size() == 1){
            return true;
        }else{
            return false;
        }

    }

    public static void main(String[] args){
        Palindrome palindrome = new Palindrome();
        CharacterComparator cc = new OffByOne();
        boolean result = palindrome.isPalindrome("abcd",cc);
    }




}
