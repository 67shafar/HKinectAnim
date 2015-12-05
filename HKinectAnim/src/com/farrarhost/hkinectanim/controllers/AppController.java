/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farrarhost.hkinectanim.controllers;

import com.farrarhost.hkinectanim.AppParameters;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author farrar
 */
public class AppController implements ActionListener{

    AppParameters params;
    HKinect kinect;
    
    public AppController(AppParameters params) {
        this.params = params;
        this.kinect = new HKinect(params);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
