import java.util.ArrayList;

public class SearchNumber {

    public static void searchNumber(ArrayList<Integer> list) {
        for (int index = 10; index <= 200; index++) {//giới hạn các số trong khoảng từ 10 đến 200 tính cả 10 và 200
            if ((index % 7 == 0) && (index % 5 != 0)) { // tìm các số chia hết cho 7 và k là bội của 5
                list.add(index);//lưu vào list
            }
        }
    }
}
