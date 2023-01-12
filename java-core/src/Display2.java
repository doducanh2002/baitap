import java.util.ArrayList;
import java.util.ListIterator;

public class Display2 {

    public static void display2(ArrayList<Integer> list) {
        ListIterator<Integer> iterator = list.listIterator();
        System.out.println("Các phần tử có trong list là: ");
        while (iterator.hasNext()) {
            System.out.print((Integer) iterator.next() + ",");
        }
    }
}
