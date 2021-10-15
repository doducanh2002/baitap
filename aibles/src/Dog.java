public class Dog extends Animal{

    public Dog(int size ,String species, String color ) {
        super(size, species,color);
    }

    @Override
    public void introduce() {
        System.out.println("Dog's size is "  + size +", species "+species+ ", color "+ color);
    }

    @Override
    public void bark() {
        System.out.println("Gru Gru");
    }

}