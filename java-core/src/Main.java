import java.util.ArrayList;

public class Main {

    // tạo list lưu các số tìm được vào list
    //kiểm tra các số từ 10 đến 200 theo điều kiện chia heets cho 7 và không là bội của 5
    //lưu vào list
    //in ra
    public static void main(String[] args) {
        System.out.println("Các số chia hết cho 7 là: ");
        ArrayList<Integer> list = new ArrayList<>();
        Search.searchNumber(list);
        Display.display1(list);
        System.out.println();
        Display.display2(list);
    }

}