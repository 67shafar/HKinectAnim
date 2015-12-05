package com.farrarhost.hkinectanim.view;

import com.farrarhost.hkinectanim.controllers.AppController;
import java.awt.event.ActionListener;

/**
 * Viewer class builds the view for the application.
 * @author Shane Allen Farrar
 */
public class Viewer {
    
    private enum Actions {
        
    }
    
    public Viewer(AppController appCtrl){
        addAppController(appCtrl);
    }

    private void addAppController(ActionListener appCtrl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
