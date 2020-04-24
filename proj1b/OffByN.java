public class OffByN implements CharacterComparator{
    public int difference;
    @Override
    public boolean equalChars (char x, char y){
        int char1 = x;
        int char2 = y;
        if (Math.abs(char1-char2)==difference){
            return true;
        }else{
            return false;
        }
    }

    public OffByN(int x){
        difference = x;
    }
}
