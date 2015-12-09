/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farrarhost.hkinectanim.controllers;

import com.farrarhost.hkinectanim.utils.HKinect;
import com.farrarhost.hkinectanim.AppParameters;
import com.farrarhost.hkinectanim.model.SkeletonFrames;
import com.farrarhost.hkinectanim.view.Viewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author farrar
 */
public class AppController implements ActionListener {

    AppParameters params;
    HKinect kinect;
    SkeletonFrames frames;

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

    private static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar,
                JOptionPane.INFORMATION_MESSAGE);
    }

}
