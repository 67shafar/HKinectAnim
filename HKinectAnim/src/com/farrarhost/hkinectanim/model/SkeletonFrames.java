/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farrarhost.hkinectanim.model;

import edu.ufl.digitalworlds.j4k.Skeleton;
import java.util.ArrayList;
import java.util.Observable;
import javax.vecmath.Vector3f;

/**
 *
 * @author farrar
 */
public class SkeletonFrames extends Observable {

    ArrayList<Skeleton[]> SkeletonFrames;

    public SkeletonFrames() {
        SkeletonFrames = new ArrayList<>();
    }

    public void add(Skeleton[] skeletons) {
        if (SkeletonFrames.size() < 60) {
            SkeletonFrames.add(skeletons);

            for (int i = 0; i < skeletons.length; i++) {

                double[] a = skeletons[i].get3DJoint(3);
                double[] b = skeletons[i].get3DJoint(0);

                Vector3f da = new Vector3f((float) (a[0] - b[0]),
                        (float) (a[1] - b[1]),
                        (float) (a[2] - b[2]));

                //90 frames at 30fps, of static motion will remove tracking.
                if (da.length() < .25) {
                    skeletons[i] = null;
                }
            }
            this.setChanged();
            this.notifyObservers();
        } else {
            System.err.println("WARNING: Buffer overflowing");
        }
    }

    public Skeleton[] pop() {
        Skeleton[] skeletons = SkeletonFrames.remove(0);
        this.setChanged();
        this.notifyObservers();
        return skeletons;
    }

    public int size() {
        return this.SkeletonFrames.size();
    }

}
