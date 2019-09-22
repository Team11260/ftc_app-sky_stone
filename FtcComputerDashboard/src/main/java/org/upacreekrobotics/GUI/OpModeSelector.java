package org.upacreekrobotics.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpModeSelector implements ActionListener{

    private String SelectedOpMode = null;

    public void setOpMode(String opModeName){
        SelectedOpMode = opModeName;
    }

    public String getSelectedOpMode(){
        return SelectedOpMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SelectedOpMode = e.getActionCommand();
    }

    public void clearSelected(){
        SelectedOpMode = null;
    }
}
