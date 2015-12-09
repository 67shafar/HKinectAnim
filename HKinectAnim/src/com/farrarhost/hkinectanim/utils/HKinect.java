package com.farrarhost.hkinectanim.utils;

import com.farrarhost.hkinectanim.AppParameters;
import com.farrarhost.hkinectanim.model.SkeletonFrames;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

/**
 * This is the class the enables the use of the kinect2 it extends an opensource
 * third party library. 
 * 
 * @author farrar
 */
public class HKinect extends J4KSDK {

    AppParameters params;
    SkeletonFrames frames;

    /**
     * Contructs the next available kinect not in use. 
     * takes applicication paramters for hints on how to operate.
     * Modifies skeleton frames retrived from the kinect. 
     * @param params
     * @param frames 
     */
    public HKinect(AppParameters params, SkeletonFrames frames) {
        this.params = params;
        this.frames = frames;
    }

    @Override
    public void onColorFrameEvent(byte[] color_frame) {
        //implement
    }

    @Override
    public void onSkeletonFrameEvent(boolean[] skeleton_tracked,
            float[] positions, float[] orientations, byte[] joint_status) {
        
        //Grabs the skeleton data. Roughtly once every 33 ms.
        Skeleton skeletons[] = this.getSkeletons();
        frames.add(skeletons);

    }

    @Override
    public void onDepthFrameEvent(short[] depth_frame, byte[] body_index,
            float[] xyz, float[] uv) {
        //implement
    }

}
