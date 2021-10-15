public class Bird extends Animal {

    public Bird(int size ,String species, String color ) {
        super(size, species,color);
    }

    @Override
    public void introduce() {
        System.out.println("Bird's size is " + size +" species "+species+ " color "+ color);
    }

    @Override
    public void fly() {
        System.out.println("bay");
    }

}