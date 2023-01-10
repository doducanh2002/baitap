public class SearchNumber {
        public static void main(String[] args) {
            System.out.println("Cac so chia het cho 7 la: ");

            for (int i= 10;i<=200;i++) {//giới hạn các số trong khoảng từ 10 đến 200 tính cả 10 và 200
                if((i%7==0) && (i%5!=0)) { // tìm các số chia hết cho 7 và k là bội của 5
                    System.out.print(i+" ; "); // in ra
                }
            }
        }
}