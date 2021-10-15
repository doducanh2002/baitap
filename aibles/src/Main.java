public class Main {
    public static void main(String[] args) {
        Animal snake =new Snake(5,"a","b");
        Animal bird = new Bird(10,"sparrow","yellow");
        Animal dog = new Dog(50,"alaska","brown");
        System.out.print("Dog'action is: ");
        dog.introduce();
        dog.bark();
        System.out.print("Bird's action is: ");
        bird.introduce();
        bird.fly();
        System.out.print("Snake's action is: ");
        snake.introduce();
        snake.crawl();

    }
}