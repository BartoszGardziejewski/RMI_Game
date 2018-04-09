package server;

public class Defender extends Player {

    private double frontShield=0;
    private double leftShield=0;
    private double rightShield=0;

    public Defender(String name) {
        super(name);
    }

    public void setShield(double frontShield ,double leftShield ,double rightShield){
        this.frontShield = frontShield;
        this.leftShield = leftShield;
        this.rightShield = rightShield;
    }

    public double getRightShield() {
        return rightShield;
    }

    public void setRightShield(double rightShield) {
        this.rightShield = rightShield;
    }

    public double getLeftShield() {
        return leftShield;
    }

    public void setLeftShield(double leftShield) {
        this.leftShield = leftShield;
    }

    public double getFrontShield() {
        return frontShield;
    }

    public void setFrontShield(double frontShield) {
        this.frontShield = frontShield;
    }

    @Override
    public  String toString(){
        return  super.toString() + " [Defender]";
    }
}
