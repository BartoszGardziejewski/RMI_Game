package application;

import checkButton.GameCheckButton;
import javafx.scene.control.Label;
import verticalSlider.GameVerticalSlider;

public class ApplicationController {

    public GameVerticalSlider rearEngineThrust;
    public GameVerticalSlider leftEngineThrust;
    public GameVerticalSlider rightEngineThrust;

    public GameCheckButton rearEngineTurbo;
    public GameCheckButton leftEngineTurbo;
    public GameCheckButton rightEngineTurbo;

    private ApplicationManager manager;

    public Label MainLabel;

    public void setManager(ApplicationManager manager){
        this.manager = manager;

    }

    public void acknowledgeCaptain(){
        manager.acknowledgeCaptain();
    }
}
