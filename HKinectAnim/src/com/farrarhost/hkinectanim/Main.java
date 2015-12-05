/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farrarhost.hkinectanim;

import com.farrarhost.hkinectanim.controllers.AppController;
import com.farrarhost.hkinectanim.view.Viewer;

/**
 *
 * @author farrar
 */
public class Main {

    public static void main(String[] args) {
        //Setup applications with default parameters
        AppParameters params = new AppParameters();
        AppController appCtrl = new AppController(params);
        Viewer view = new Viewer(appCtrl);

    }
}
