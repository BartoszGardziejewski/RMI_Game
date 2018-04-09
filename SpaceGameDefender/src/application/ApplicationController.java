package application;

import checkButton.GameCheckButton;
import javafx.scene.control.Label;
import verticalSlider.GameVerticalSlider;

public class ApplicationController {

    public GameVerticalSlider frontShieldPower;
    public GameVerticalSlider leftShieldPower;
    public GameVerticalSlider rightShieldPower;

    private ApplicationManager manager;

    public Label MainLabel;

    public void setManager(ApplicationManager manager){
        this.manager = manager;

    }

    public void acknowledgeCaptain(){
        manager.acknowledgeCaptain();
    }
}
