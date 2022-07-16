package example1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class main {
    public static void main(String[] args) {
        //khởi tạo list thứ nhất
        ArrayList<String> arrayList = new ArrayList<String>();
        //khởi tạo list thứ 2
        ArrayList<String> arrayList2 = new ArrayList<String>();
        //thêm các phần tử vào array List

        arrayList.add("DucAnh");
        arrayList.add("Dat");
        arrayList.add("Luat");
        arrayList.add("Nam");
        arrayList.add("Khang");

        arrayList2.add("8");
        arrayList2.add("7");
        arrayList2.add("2");
        arrayList2.add("5");


        //hiển thị các phần tử
        System.out.println("Các phần tử có trong list là: ");
        System.out.println(arrayList);
        arrayList.addAll(arrayList2);
        System.out.println("In ra list mới sau khi gộp list 2 vào list 1: ");
        System.out.println(arrayList);
        //hiển thị
        for (int i=0;i<arrayList.size();i++){
            System.out.println(arrayList.get(i));
        }

        System.out.println("in ra số phần tử có trong list");
        //in ra số phần tử có trong list
        System.out.println(arrayList.size());

        System.out.println("tìm thông tin theo chỉ số");
        //tìm thông tin theo chỉ số
        System.out.println(arrayList.get(2));

        System.out.println("sửa giá trị trong list");
        //sửa giá trị trong list
        arrayList.set(2,"aaaaa");

        System.out.println("list mới: "+ arrayList);
        //xoa
        arrayList.remove(1);
        System.out.println("Xóa ở vị trí 1 ta có list mới: "+ arrayList );
        System.out.println("-------------");

        //hoán đổi vị trí ngẫu nhiên
        Collections.shuffle(arrayList);
        System.out.println("Hoán đổi vị trí trong list"+ arrayList);

        //In ra các giá trị từ vị trí 2 đến 4
        System.out.println("các giá trị từ vị trí 2 đến 4" + arrayList.subList(2,5));

        //Đảo ngược danh sách
        Collections.reverse(arrayList);
        System.out.println("Dach sách sau khi đảo ngược là: "+arrayList);

        //Sắp xếp theo ký tự đầu
        Collections.sort(arrayList2);
        System.out.println("Sắp xếp list 2 theo ký tự đầu: "+arrayList2);

        //Xoay chiều mảng với
        System.out.println(arrayList);
        Collections.rotate(arrayList,4);
        System.out.println("Xoay chiều mảng với chuỗi có vị trí 4 đứng đầu: "+ arrayList);

        Collections.swap(arrayList2,1,3);
        System.out.println("Đổi chuỗi ở vị trí 3 cho chuỗi ở vị trí 5:" + arrayList2);

        Collections.fill(arrayList2,"3");
        System.out.println("Ghi đè toàn bộ giá trị trong chuỗi thành 3: "+ arrayList2);
    }
}
