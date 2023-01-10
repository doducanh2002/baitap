import java.util.ArrayList;

public class SearchNumber {

    // tạo list lưu các số tìm được vào list
    //kiểm tra các số từ 10 đến 200 theo điều kiện chia heets cho 7 và không là bội của 5
    //lưu vào list
    //in ra
        public static void main(String[] args) {
            System.out.println("Các số chia hết cho 7 là: ");

            ArrayList<Integer> list = new ArrayList<>();
            for (int i= 10;i<=200;i++) {//giới hạn các số trong khoảng từ 10 đến 200 tính cả 10 và 200
                if((i%7==0) && (i%5!=0)) { // tìm các số chia hết cho 7 và k là bội của 5
                   list.add(i);//lưu vào list
                }
            }

            for (int i = 0; i<list.size() - 1; i++ ){
                System.out.print(list.get(i)+",");//lấy ra các số theo yêu cầu
            }
        }
}