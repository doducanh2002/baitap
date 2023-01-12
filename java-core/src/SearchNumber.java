import java.util.ArrayList;
import java.util.ListIterator;

public class SearchNumber {

    // tạo list lưu các số tìm được vào list
    //kiểm tra các số từ 10 đến 200 theo điều kiện chia heets cho 7 và không là bội của 5
    //lưu vào list
    //in ra
    public static void main(String[] args) {
        System.out.println("Các số chia hết cho 7 là: ");
        ArrayList<Integer> list = new ArrayList<>();
        searchNumber(list);
        display1(list);
    }

    public static void searchNumber(ArrayList<Integer> list) {
        for (int index = 10; index <= 200; index++) {//giới hạn các số trong khoảng từ 10 đến 200 tính cả 10 và 200
            if ((index % 7 == 0) && (index % 5 != 0)) { // tìm các số chia hết cho 7 và k là bội của 5
                list.add(index);//lưu vào list
            }
        }
    }

    //cách 1:
    public static void display1(ArrayList<Integer> list) {
        System.out.print(list);//lấy ra các số theo yêu cầu
    }

    //cách 2
    public static void display2(ArrayList<Integer> list) {
        ListIterator<Integer> iterator = list.listIterator();
        System.out.println("Các phần tử có trong list là: ");
        while (iterator.hasNext()) {
            System.out.print((Integer) iterator.next() + ",");
        }
    }


}