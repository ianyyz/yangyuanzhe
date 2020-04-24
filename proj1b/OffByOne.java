import static org.junit.Assert.assertTrue;

public class OffByOne implements CharacterComparator{


    @Override
    public boolean equalChars(char x, char y) {
        int char1 = x;
        int char2 = y;
        if (Math.abs(char1-char2)==1){
            return true;
        }else{
            return false;
        }
    }



    /*
    public static void main(String[] args){
        char x = 'v';
        char y = 'b';
        int total = 0;
        for (char alphabet = x; alphabet <= y; alphabet ++){
            total += 1;
        }
        System.out.println(total);

    }

     */
}
