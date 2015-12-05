package com.farrarhost.hkinectanim.controllers;

import com.farrarhost.hkinectanim.AppParameters;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

/**
 * This is the class which contains the main function. it
 *
 * @author farrar
 */
public class HKinect extends J4KSDK {

    AppParameters params;
    
    public HKinect(AppParameters params){
        this.params = params;
    }
    
    @Override
    public void onColorFrameEvent(byte[] color_frame) {
        //implement
    }

    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {

        Skeleton skeletons[] = new Skeleton[getMaxNumberOfSkeletons()];
        for (int i = 0; i < getMaxNumberOfSkeletons(); i++) {
            skeletons[i] = Skeleton.getSkeleton(i, skeleton_tracked, positions,
                    orientations, joint_status, this);
        }

    }

    @Override
    public void onDepthFrameEvent(short[] depth_frame, byte[] body_index, float[] xyz, float[] uv) {
        //implement
    }

}
