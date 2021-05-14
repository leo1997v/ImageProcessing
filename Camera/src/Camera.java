import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {

    public static void main(String args[]) {
        new Camera().initialize();
    }

    public void initialize() {
        JFrame jf = new JFrame("Camera");
        jf.setSize(800, 800);
        jf.setLayout(new FlowLayout());
        JButton btnOrigin =new JButton("Origin");
        JButton btnSketch = new JButton("Sketch");
        JButton btnBlackWhite = new JButton("Grey");
        JButton btnOld = new JButton("Vintage");
        JButton btnMosaic = new JButton("Mosaic");
        JButton btnBinarization = new JButton("Plate Printing");

        CameraListener cameraListener = new CameraListener();
        btnOrigin.addActionListener(cameraListener);
        btnSketch.addActionListener(cameraListener);
        btnBlackWhite.addActionListener(cameraListener);
        btnOld.addActionListener(cameraListener);
        btnMosaic.addActionListener(cameraListener);
        btnBinarization.addActionListener(cameraListener);

        jf.add(btnOrigin);
        jf.add(btnSketch);
        jf.add(btnBlackWhite);
        jf.add(btnOld);
        jf.add(btnMosaic);
        jf.add(btnBinarization);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setVisible(true);
        Graphics g = jf.getGraphics();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        g.setColor(Color.black);
        g.setFont(new java.awt.Font("", Font.BOLD, 64));
        g.drawString("Loading....." , 200, 300);
        g.setFont(new java.awt.Font("", Font.BOLD, 16));



        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        //webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        CameraThread cameraThread = new CameraThread(g,webcam);
        Thread ct = new Thread(cameraThread);
        ct.start();


    }
}
