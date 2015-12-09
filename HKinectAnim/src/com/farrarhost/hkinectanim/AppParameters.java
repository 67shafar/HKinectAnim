package com.farrarhost.hkinectanim;

/**
 * Contains parameters for the application. Not heavily used yet, but will
 * expand here in the future. 
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
        numberOfSkeletonsToTrack = 6;
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
        if (numberOfSkeletonsToTrack > 6) {
            numberOfSkeletonsToTrack = 6;
        }
        this.numberOfSkeletonsToTrack = numberOfSkeletonsToTrack;
    }

}
