public class Snake extends Animal{

    public Snake(int size ,String species, String color ) {
        super(size, species,color);
    }

    @Override
    public void introduce() {
       System.out.println("snake's size is "  + size +", species "+species+ ", color "+ color);
    }

    @Override
    public void crawl() {
        System.out.println("bò");
    }
    }

