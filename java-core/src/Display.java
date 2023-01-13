import java.util.ArrayList;
import java.util.ListIterator;

public class Display {

    public static void display1(ArrayList<Integer> list) {
        System.out.print(list);//lấy ra các số theo yêu cầu
    }

    public static void display2(ArrayList<Integer> list) {
        String str = list.toString();
        System.out.println(str.substring(0, str.length()));
    }
}
