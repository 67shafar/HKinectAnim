package com.farrarhost.hkinectanim;

import com.farrarhost.hkinectanim.controllers.AppController;

/**
 * This is the main class and function. Glues the MVC structure together. 
 * Creates the default parameters.
 * @author farrar
 */
public class Main {

    public static void main(String[] args) {
        //Setup applications with default parameters
        AppParameters params = new AppParameters();
        AppController appCtrl = new AppController(params);

    }
}
