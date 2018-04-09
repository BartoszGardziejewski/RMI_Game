package server;

public class Engineer extends Player {

    private double rearEngineThrust=0;
    private double leftEngineThrust=0;
    private double rightEngineThrust=0;

    private boolean rearEngineTurbo=false;
    private boolean leftEngineTurbo=false;
    private boolean rightEngineTurbo=false;

    public Engineer(String name){
        super(name);
    }

    public void setThrusts(double rearEngineThrust ,double leftEngineThrust ,double rightEngineThrust){
        this.rearEngineThrust = rearEngineThrust;
        this.leftEngineThrust = leftEngineThrust;
        this.rightEngineThrust = rightEngineThrust;
    }

    public void setTurbo(boolean rearEngineTurbo ,boolean leftEngineTurbo ,boolean rightEngineTurbo){
        this.rearEngineTurbo = rearEngineTurbo;
        this.leftEngineTurbo = leftEngineTurbo;
        this.rightEngineTurbo = rightEngineTurbo;
    }

    public double getRearEngineThrust(){
        return rearEngineThrust;
    }

    public double getLeftEngineThrust() {
        return leftEngineThrust;
    }

    public double getRightEngineThrust() {
        return rightEngineThrust;
    }

    public boolean isRightEngineTurbo() {
        return rightEngineTurbo;
    }

    public boolean isLeftEngineTurbo() {
        return leftEngineTurbo;
    }

    public boolean isRearEngineTurbo() {
        return rearEngineTurbo;
    }

    public boolean compare(Engineer engineer){
        return
               this.rearEngineThrust == engineer.rearEngineThrust && this.leftEngineThrust == engineer.leftEngineThrust && this.rightEngineThrust == engineer.rightEngineThrust &&
               this.rearEngineTurbo == engineer.rearEngineTurbo && this.leftEngineTurbo == engineer.leftEngineTurbo && this.rightEngineTurbo == engineer.rightEngineTurbo;
    }

    @Override
    public  String toString(){
        return  super.toString() + " [Engineer]";
    }
}
