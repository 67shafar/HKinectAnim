package com.farrarhost.hkinectanim;

import com.farrarhost.hkinectanim.controllers.AppController;

/**
 *
 * @author farrar
 */
public class Main {

    public static void main(String[] args) {
        //Setup applications with default parameters
        AppParameters params = new AppParameters();
        AppController appCtrl = new AppController(params);

    }
}
