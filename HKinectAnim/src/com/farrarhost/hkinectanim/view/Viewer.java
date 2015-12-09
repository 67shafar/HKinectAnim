package com.farrarhost.hkinectanim.view;

import com.farrarhost.hkinectanim.model.SkeletonFrames;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import edu.ufl.digitalworlds.j4k.Skeleton;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.media.j3d.Appearance;
import javax.media.j3d.BadTransformException;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Group;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3f;

/**
 * Viewer class builds the view for the application.
 *
 * @author Shane Allen Farrar
 */
public class Viewer implements Observer {

    private final JButton startButton;
    private final JButton stopButton;
    private final JCheckBox[] checkBoxes;

    private final BranchGroup scene;
    private BranchGroup skeletonGroups;

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SkeletonFrames) {
            SkeletonFrames frames = (SkeletonFrames) o;
            try {
                BranchGroup temp = createSkeletonGroups(frames.pop());
                if (skeletonGroups != null) {
                    skeletonGroups.detach();
                    skeletonGroups.removeAllChildren();
                }
                skeletonGroups = temp;
                scene.addChild(temp);
            } catch (NullPointerException | IndexOutOfBoundsException e) {

            }
            System.out.println();
        }
    }

    private enum Actions {
        STARTKINECT,
        STOPKINECT,
        C1,
        C2,
        C3,
        C4,
        C5,
        C6
    }

    public Viewer(ActionListener appCtrl) {
        //Setup controls
        JPanel controlPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel checkBoxPanel = new JPanel(new FlowLayout());

        JPanel mainPanel = new JPanel(new FlowLayout());
        controlPanel.setSize(new Dimension(640, 100));
        buttonPanel.setSize(new Dimension(640, 50));
        checkBoxPanel.setSize(new Dimension(640, 50));
        checkBoxes = new JCheckBox[6];
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i] = new JCheckBox(String.valueOf(i));
            checkBoxes[i].setSelected(true);
            checkBoxPanel.add(checkBoxes[i]);
        }

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        controlPanel.add(buttonPanel);
        controlPanel.add(checkBoxPanel);

        //Setup JFrame
        JFrame frame = new JFrame();
        frame.setTitle("HKinectAnim - Shane Farrar");
        frame.setSize(640, 580);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the Canvas3D to the frame
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(gc);
        canvas.setDoubleBufferEnable(true);
        canvas.setSize(new Dimension(640, 480));
        mainPanel.add(canvas);
        mainPanel.add(controlPanel);
        frame.add(mainPanel);

        //Configure Universe
        SimpleUniverse universe = new SimpleUniverse(canvas);
        scene = createSceneGraph();

        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);

        //add the controller which is a listener
        addAppController(appCtrl);
        frame.setVisible(true);
    }

    private BranchGroup createSceneGraph() {
        //Create a branchgroup that can have its children edited.
        BranchGroup sceneGraph = new BranchGroup();
        sceneGraph.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        sceneGraph.setCapability(Group.ALLOW_CHILDREN_WRITE);

        return sceneGraph;
    }

    private BranchGroup createSkeletonGroups(Skeleton[] skeletonArray) {
        BranchGroup group = new BranchGroup();
        group.setCapability(BranchGroup.ALLOW_DETACH);

        for (int i = 0; i < skeletonArray.length; i++) {
            if (checkBoxes[i].isSelected() && skeletonArray[i] != null) {
                group.addChild(createSkeletonGroup(skeletonArray[i]));
            }
        }

        return group;
    }

    private BranchGroup createSkeletonGroup(Skeleton skeleton) {

        //setup geomerty and variables
        BranchGroup skeletonGroup = new BranchGroup();
        int length = skeleton.getJointPositions().length / 3;
        TransformGroup[] jointTransforms = new TransformGroup[length];
        Sphere[] jointGeom = new Sphere[length];

        //setup appearance of joints
        Material mat = new Material();
        mat.setEmissiveColor(0, 255, 0);
        mat.setLightingEnable(true);
        Appearance app = new Appearance();
        app.setMaterial(mat);

        double[] pos;
        Transform3D transform = new Transform3D();
        Vector3f translation;

        for (int i = 0; i < skeleton.getJointPositions().length / 3; i++) {
            pos = skeleton.get3DJoint(i);
            //adjust z-axis for viewing.
            translation
                    = new Vector3f((float) pos[0], (float) pos[1], -(float) pos[2]);

            //check to see if a finger of larger joint via joint_id < 21
            if (i < 21) {
                jointGeom[i] = new Sphere(.0235f);
            } else {
                jointGeom[i] = new Sphere(.0085f);
            }
            jointGeom[i].setAppearance(app);
            jointTransforms[i] = new TransformGroup();
            transform.setIdentity();
            try {
                transform.setTranslation(translation);
                jointTransforms[i].setTransform(transform);
            } catch (BadTransformException e) {

            }

            jointTransforms[i].addChild(jointGeom[i]);
            skeletonGroup.addChild(jointTransforms[i]);
        }

        //Draw spine
        for (int i = 0; i < 3; i++) {
            skeletonGroup.addChild(drawBone(skeleton.get3DJoint(i), skeleton.get3DJoint(i + 1)));
        }

        //Draw arms
        for (int i = 0; i < 3; i++) {
            skeletonGroup.addChild(drawBone(skeleton.get3DJoint(i + 4), skeleton.get3DJoint(i + 5)));
            skeletonGroup.addChild(drawBone(skeleton.get3DJoint(i + 8), skeleton.get3DJoint(i + 9)));
        }

        //Draw torso
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(8), skeleton.get3DJoint(20)));
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(20), skeleton.get3DJoint(4)));

        //Draw hips
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(16), skeleton.get3DJoint(0)));
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(0), skeleton.get3DJoint(12)));

        //Draw legs
        for (int i = 0; i < 3; i++) {
            skeletonGroup.addChild(drawBone(skeleton.get3DJoint(i + 16), skeleton.get3DJoint(i + 17)));
            skeletonGroup.addChild(drawBone(skeleton.get3DJoint(i + 12), skeleton.get3DJoint(i + 13)));
        }

        //hands and feet
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(23), skeleton.get3DJoint(11)));
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(24), skeleton.get3DJoint(11)));
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(21), skeleton.get3DJoint(7)));
        skeletonGroup.addChild(drawBone(skeleton.get3DJoint(22), skeleton.get3DJoint(7)));

        return skeletonGroup;
    }

    private TransformGroup drawBone(double[] pos1, double[] pos2) {
        //Setup looks of bones.
        Material mat = new Material();
        mat.setEmissiveColor(0, 255, 0);
        mat.setLightingEnable(true);
        Appearance app = new Appearance();
        app.setMaterial(mat);

        //Create Transform Group
        TransformGroup boneGroup = new TransformGroup();
        try {
            boneGroup.setTransform(boneTransform(pos1, pos2));
        } catch (BadTransformException e) {

        }

        Cylinder bone = new Cylinder(.01f, (float) vectorMagnitude(pos1, pos2));
        bone.setAppearance(app);

        boneGroup.addChild(bone);
        return boneGroup;
    }

    private Transform3D boneTransform(double[] pos1, double[] pos2) {

        //inital vectors
        Vector3f a = new Vector3f((float) pos1[0], (float) pos1[1], -(float) pos1[2]);
        Vector3f b = new Vector3f((float) pos2[0], (float) pos2[1], -(float) pos2[2]);
        Vector3f da = new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
        Vector3f crossP = new Vector3f();
        Vector3f YAXIS = new Vector3f(0, 1, 0);

        //Find axis of rotation
        da.normalize();
        crossP.cross(YAXIS, da);

        //Calculate angle
        AxisAngle4f angle = new AxisAngle4f();
        angle.set(crossP, (float) Math.acos(YAXIS.dot(da)));

        //Calcuate Translation
        Vector3f midpoint
                = new Vector3f((a.x + b.x) / 2, (a.y + b.y) / 2, (a.z + b.z) / 2);

        //Calculate transform
        Transform3D rotation = new Transform3D();
        rotation.set(angle);
        Transform3D translation = new Transform3D();
        translation.setIdentity();
        translation.setTranslation(midpoint);

        translation.mul(rotation);

        return translation;
    }

    private double vectorMagnitude(double[] pos1, double[] pos2) {
        double x, y, z;
        x = pos1[0] - pos2[0];
        y = pos1[1] - pos2[1];
        z = pos1[2] - pos2[2];
        Vector3f a = new Vector3f((float) x, (float) y, (float) z);

        return a.length();

    }

    private void addAppController(ActionListener appCtrl) {

        //Add appCtrl to listen for ActionCommands
        startButton.setActionCommand(Actions.STARTKINECT.name());
        startButton.addActionListener(appCtrl);

        stopButton.setActionCommand(Actions.STOPKINECT.name());
        stopButton.addActionListener(appCtrl);

    }
}
