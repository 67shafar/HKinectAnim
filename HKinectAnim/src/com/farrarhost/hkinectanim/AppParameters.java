/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farrarhost.hkinectanim;

/**
 *
 * @author farrar
 */
public class AppParameters {

    int numberOfSkeletonsToTrack;

    /**
     * Sets the application parameters to the default values.
     * numberOfSkeletonsToTrack = 1
     *
     */
    AppParameters() {
        numberOfSkeletonsToTrack = 1;
    }

    /**
     * Sets the applications parameters to the given values.
     *
     * @param numberOfSkeletonsToTrack max number of skeletons to track
     */
    AppParameters(int numberOfSkeletonsToTrack) {
        this.numberOfSkeletonsToTrack = numberOfSkeletonsToTrack;
    }

    /**
     * Get the maximum number of skeletons the application is set to track.
     *
     * @return
     */
    public int getNumberOfSkeletonsToTrack() {
        return numberOfSkeletonsToTrack;
    }

    /**
     * Set the maximum number of skeletons to track between [1,4] Maximum number
     * of skeletons to track is 4.
     *
     * @param numberOfSkeletonsToTrack
     */
    public void setNumberOfSkeletonsToTrack(int numberOfSkeletonsToTrack) {
        if (numberOfSkeletonsToTrack > 4) {
            numberOfSkeletonsToTrack = 4;
        }
        this.numberOfSkeletonsToTrack = numberOfSkeletonsToTrack;
    }

}
