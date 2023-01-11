import java.util.ArrayList;

public class SearchNumber {

    // tạo list lưu các số tìm được vào list
    //kiểm tra các số từ 10 đến 200 theo điều kiện chia heets cho 7 và không là bội của 5
    //lưu vào list
    //in ra
    public static void main(String[] args) {
        System.out.println("Các số chia hết cho 7 là: ");
        ArrayList<Integer> list = new ArrayList<>();
        searchNumber(list);
        display(list);
    }

    public static void searchNumber(ArrayList<Integer> list) {
        for (int index = 10; index <= 200; index++) {//giới hạn các số trong khoảng từ 10 đến 200 tính cả 10 và 200
            if ((index % 7 == 0) && (index % 5 != 0)) { // tìm các số chia hết cho 7 và k là bội của 5
                list.add(index);//lưu vào list
            }
        }
    }

    public static void display(ArrayList<Integer> list) {
        for (int index = 0; index < list.size() - 1; index++) {
            System.out.print(list.get(index) + ",");//lấy ra các số theo yêu cầu
        }
    }
}