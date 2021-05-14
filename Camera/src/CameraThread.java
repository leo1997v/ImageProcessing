import com.github.sarxos.webcam.Webcam;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CameraThread implements Runnable {
    public Graphics g;
    public Webcam webcam;
    static public int type;

    public CameraThread(Graphics g, Webcam webcam) {
        this.g = g;
        this.webcam = webcam;
        this.type = Params.TYPE_ORIGIN;
    }

    @Override
    public void run() {

        while (true) {
            BufferedImage img = webcam.getImage();
            switch(type){
                case Params.TYPE_ORIGIN:
                    break;
                case Params.TYPE_SKETCH:
                    img = Process.paintSketch(img);
                    break;
                case Params.TYPE_BINARIZATION:
                    img = Process.paintBinarization(img);
                    break;
                case Params.TYPE_BLACK_WHITE:
                    img = Process.paintBlackWhite(img);
                    break;
                case Params.TYPE_OLD:
                    img = Process.paintOld(img);
                    break;
                case Params.TYPE_MOSAIC:
                    img = Process.paintMosaic(img);
                    break;
            }
            int fps = (int)webcam.getFPS();
            g.drawImage(img, 100, 100, null);
            g.setColor(Color.white);
            g.fillRect(100,75,300,20);
            g.setColor(Color.black);
            g.drawString("FPS:" + fps, 100, 90);
        }

    }
}
