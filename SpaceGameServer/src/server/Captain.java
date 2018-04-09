package server;

public class Captain extends Player{

    public Captain(String name) {
        super(name);
    }

    @Override
    public  String toString(){
        return  super.toString() + " [Captain]";
    }

}
