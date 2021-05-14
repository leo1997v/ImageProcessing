import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.WebSocket;

public class CameraListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        String txt = e.getActionCommand();
        switch (txt) {
            case "Origin":
                CameraThread.type = Params.TYPE_ORIGIN;
                break;
            case "Sketch":
                CameraThread.type = Params.TYPE_SKETCH;
                break;
            case "Grey":
                CameraThread.type = Params.TYPE_BLACK_WHITE;
                break;
            case "Vintage":
                CameraThread.type = Params.TYPE_OLD;
                break;
            case "Mosaic":
                CameraThread.type = Params.TYPE_MOSAIC;
                break;
            case "Plate Printing":
                CameraThread.type = Params.TYPE_BINARIZATION;
                break;

        }
    }
}
