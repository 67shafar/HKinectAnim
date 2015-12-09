package com.farrarhost.hkinectanim.controllers;

import com.farrarhost.hkinectanim.utils.HKinect;
import com.farrarhost.hkinectanim.AppParameters;
import com.farrarhost.hkinectanim.model.SkeletonFrames;
import com.farrarhost.hkinectanim.view.Viewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * This class is the controller for the project. It is an action listener
 * Which checks the action commands of the buttons in the View and 
 * runs the appropraite actions. 
 * @author farrar
 */
public class AppController implements ActionListener {

    AppParameters params;
    HKinect kinect;
    SkeletonFrames frames;

    /**
     * Takes application parameters
     * @param params 
     */
    public AppController(AppParameters params) {

        Viewer view = new Viewer(this);
        this.frames = new SkeletonFrames();
        this.frames.addObserver(view);
        this.params = params;
        this.kinect = new HKinect(params, frames);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "STARTKINECT":
                //Only grab skeleton data at this juncture.
                kinect.start(HKinect.SKELETON);
                System.out.println("Starting Kinect");
                break;
            case "STOPKINECT":
                kinect.stop();
                System.out.println("Stoping Kinect");
                break;
            default:
                infoBox("No Such Action Command", "ERROR: AppController");
        }
    }

    /**
     * Create a message dialog for errors or to be implemented functions.
     * @param infoMessage
     * @param titleBar 
     */
    private static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar,
                JOptionPane.INFORMATION_MESSAGE);
    }

}
