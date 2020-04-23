public class ArrayDeque <T>{
    private T[] items;
    private int size;
    private int next_first;
    private int next_last;
    private static int start_posit = 4;
    private double usage_ratio;

    public ArrayDeque (){
        items = (T[]) new Object[8];
        size = 0;
        next_first = 4;
        next_last = 4;
    }


    public ArrayDeque(ArrayDeque other){
        items = (T[]) new Object[8];
        size = 0;
        other.reorder(other.items.length);
        for(int i= 0; i<other.size; i += 1){
            this.addLast((T)other.items[i]);
        }

    }

    private void reorder(int num){
        T[] a = (T[]) new Object[num];
        if(next_first == items.length-1){
            System.arraycopy(items,0,a,0,size);
        }else{
            System.arraycopy(items,next_first+1,a,0,items.length-next_first-1);
            System.arraycopy(items,0,a,items.length-next_first-1,next_last);
        }
        items = a;
        next_last = size;
        next_first = items.length - 1;
    }

    private void resize(int cap){
        T[] a = (T[]) new Object[cap];
        if(next_first == items.length-1){
            System.arraycopy(items,0,a,0,size);
        }else{
            System.arraycopy(items,next_last,a,0,items.length-next_last);
            System.arraycopy(items,0,a,items.length-next_last,next_last);
        }
        items = a;
        next_last = size;
        next_first = items.length - 1;
    }


    private void contract(){
        if (items.length < 10){
            return;
        }
        T[] a = (T[]) new Object[items.length/2];
        if ((items[items.length-1] == null )&& (next_first != items.length-1)){
            System.arraycopy(items,next_first+1,a,0,size);
            items = a;
            next_last = size;
            next_first = items.length-1;
        }else if((items[items.length-1] == null )&& (next_first == items.length-1)){
            System.arraycopy(items,0,a,0,size);
            items = a;
            next_first = items.length - 1;
        }else{
            System.arraycopy(items,0,a,0,next_last);
            System.arraycopy(items,next_first+1,a,next_first+1-items.length/2,items.length-next_first);
            items = a;
            next_first = next_first - items.length/2;
        }

    }



    public void addFirst(T i){
        if (size == items.length){
            this.resize(size*2);
        }
        if (size == 0){
            next_last = start_posit + 1;
        }
        items[next_first] = i;
        if(next_first == 0) {
            next_first = items.length-1;
        }else{
            next_first = next_first -1;
        }
        size += 1;
    }

    public void addLast(T i){
        if(size == items.length){
            this.resize(size*2);
        }
        if (size == 0){
            next_first = start_posit - 1;
        }
        items[next_last] = i;
        if(next_last == items.length-1){
            next_last = 0;
        }else{
            next_last = next_last + 1;
        }
        size += 1;

    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else{
            return false;
        }
    }

    public int size(){
        return size;
    }


    public void printDeque() {
        int idx = next_first+1;
        if(next_first<next_last) {
            while (idx < next_last) {
                System.out.print(items[idx] + " ");
                idx += 1;
            }
        } else{
            while (idx < items.length) {
                System.out.print(items[idx]+ " ");
                idx += 1;
            }
            idx = 0;
            while (idx < next_last) {
                System.out.print(items[idx]+ " ");
                idx += 1;
            }

        }

        System.out.println(System.lineSeparator());
    }

    public T removeLast(){
        double len = items.length;
        usage_ratio = size/ len;
        if (usage_ratio < 0.25){
            this.contract();
        }
        if (size == 0){
            return null;
        }
        T return_item;
        if (next_last == 0){
            return_item = items[items.length - 1];
            items[items.length-1] = null;
            next_last = items.length - 1;
        }else{
            return_item = items[next_last - 1];
            items[next_last-1] = null;
            next_last = next_last-1;
        }
        size -= 1;
        if (size == 0){
            next_first = start_posit;
            next_last = start_posit;
        }
        return return_item;

    }

    public T removeFirst(){
        double len = items.length;
        usage_ratio = size/ len;
        if (usage_ratio < 0.25 ){
            this.contract();
        }
        if (size == 0){
            return null;
        }
        T return_item;
        if (next_first == items.length - 1){
            return_item = items[0];
            items[0] = null;
            next_first = 0;
        }else{
            return_item = items[next_first + 1];
            items[next_first + 1] = null;
            next_first = next_first + 1;
        }
        size -= 1;
        if (size == 0){
            next_first = start_posit;
            next_last = start_posit;
        }
        return return_item;
    }

    public T get(int index){
        if (size == 0){
            return null;
        }
        if (index >= size){
            return null;
        }
        if(next_first < next_last){
            return items[next_first+index+1];
        }else if ( next_first == items.length-1) {
            return items[index];
        }
            return items[index-(items.length-next_first-1)];
    }

    /*
    public static void main(String[] args){
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        for (int i =0; i<10; i += 1){
            a.addLast(i);
        }
        for (int i = 10; i<15; i += 1){
            a.addFirst(i);
        }

        for (int i = 0; i < 10; i+=1){
            a.removeFirst();
            a.removeLast();
        }

        System.out.println(a.get(0));

        ArrayDeque<Integer> new_copy = new ArrayDeque<>(a);

        new_copy.addFirst(2);
        for (int i =0; i<15; i += 1){
            new_copy.addLast(i);
        }


        a.printDeque();


    }

     */
}
