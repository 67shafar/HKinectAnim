package com.farrarhost.hkinectanim.model;

import edu.ufl.digitalworlds.j4k.Skeleton;
import java.util.ArrayList;
import java.util.Observable;
import javax.vecmath.Vector3f;

/**
 * This class is the model for skeleton frames. It contains the 'raw' data
 * from the kinect stream. 
 * @author farrar
 */
public class SkeletonFrames extends Observable {

    ArrayList<Skeleton[]> SkeletonFrames;

    /**
     * Constructs the SkeletonFrame Object
     */
    public SkeletonFrames() {
        SkeletonFrames = new ArrayList<>();
    }

    /**
     * Adds an array of 6 skeletons. The maximum amount the kinect2 can track.
     * It also removes blank skeletons by setting them to null. This helps
     * to prevent extraneous joints from being saved ( and later rendered ).
     * If the buffer gets full it will print a watrning.
     * @param skeletons 
     */
    public void add(Skeleton[] skeletons) {
        if (SkeletonFrames.size() < 60) {
            SkeletonFrames.add(skeletons);

            for (int i = 0; i < skeletons.length; i++) {

                //Get length of torso
                double[] a = skeletons[i].get3DJoint(3);
                double[] b = skeletons[i].get3DJoint(0);

                Vector3f da = new Vector3f((float) (a[0] - b[0]),
                        (float) (a[1] - b[1]),
                        (float) (a[2] - b[2]));

                //torso must be alteast 25cm to render
                if (da.length() < .25) {
                    skeletons[i] = null;
                }
            }
            //notify observers of changes.
            this.setChanged();
            this.notifyObservers();
        } else {
            System.err.println("WARNING: Buffer overflowing");
        }
    }

    /**
     * Removes and returns a skeleton array
     * @return 
     */
    public Skeleton[] pop() {
        Skeleton[] skeletons = SkeletonFrames.remove(0);
        this.setChanged();
        this.notifyObservers();
        return skeletons;
    }

    /**
     * returns the number of frames saved.
     * @return 
     */
    public int size() {
        return this.SkeletonFrames.size();
    }

}
