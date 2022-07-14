package example1;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

        ArrayList<String> arrayList = new ArrayList<String>();
        //thêm các phần tử vào array List
        arrayList.add("DucAnh");
        arrayList.add("Dat");
        arrayList.add("Luat");
        arrayList.add("Nam");
        arrayList.add("Khang");
        //hiển thị các phần tử
        System.out.println("Các phần tử có trong list là: ");
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
    }
}
