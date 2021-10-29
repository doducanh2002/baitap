import java.util.Scanner;

public class array {
    public static int total(int[] arr){
        int total =0;
        for (int i = 0; i < 10; i++) {
            total = total +arr[i];
        }
    return total;
    }

    public static void findNumber(int[] arr){
        System.out.println("Số đầu tiên nhỏ hơn 0 là :");
        for (int i = 0; i < 10; i++) {
            if (arr[i]<0){
                System.out.println(arr[i]);
                System.exit(i);
            }
        }
    }

    public static void findEvenNumber(int[] arr) {
        System.out.println("Các số chẵn là :");
        for (int i = 0; i < 10; i++) {
            if (arr[i] % 2 == 0) {
                System.out.println(arr[i] + " ");
            }
        }
    }

    public static void newArray(int[] arr) {
        System.out.println("Các Số trong mảng mới là : ");
        for (int i = 0; i < 10; i++) {
            arr[i] = arr[i] * 2;
            System.out.println(arr[i] + " ");
        }
    }


    public static void show(int[] arr) {
        System.out.println("Các Số trong mảng là : ");
        for (int i = 0; i < 10; i++) {
            System.out.println(arr[i] + " ");
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[10];
        System.out.print("Nhập các phần tử của mảng: ");
        for (int i = 0; i < 10; i++) {
            System.out.printf("a[%d] = ", i);
            arr[i] = scanner.nextInt();
        }
        show(arr);
        System.out.println("Tổng các số trong mảng là : "+total(arr));
        findNumber(arr);
        findEvenNumber(arr);
        newArray(arr);
    }
}