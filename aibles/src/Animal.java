public abstract class Animal implements  Action{

    public int size;
    public String species;
    public String color;

    public Animal() {
    }

    public Animal(int size,  String species, String color) {
        this.size = size;
        this.species = species;
        this.color = color;

    }
    public void introduce(){
    }

    @Override
    public void fly() {
    }

    @Override
    public void crawl() {
    }

    @Override
    public void bark(){
    }




    }
