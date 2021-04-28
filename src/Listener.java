import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Listener implements ActionListener {

    int type;
    ArrayList<Integer> arrType = new ArrayList<>();
    ImageProcessing imgP;

    public Listener(){
        arrType.add(Params.TYPE_ORIGIN);
    }

    public void actionPerformed(ActionEvent e) {
        String txt = e.getActionCommand();
        switch (txt) {
            case "素描":
                type = Params.TYPE_SKETCH;
                arrType.add(type);
                imgP.repaint();
                break;
            case "原图":
                type = Params.TYPE_ORIGIN;
                arrType.add(type);
                imgP.repaint();
                break;
            case "油画":
                type = Params.TYPE_OIL_PAINTING;
                arrType.add(type);
                imgP.repaint();
                break;
            case "黑白":
                type = Params.TYPE_BLACK_WHITE;
                arrType.add(type);
                imgP.repaint();
                break;
            case "老照片":
                type = Params.TYPE_OLD;
                arrType.add(type);
                imgP.repaint();
                break;
            case "非主流":
                type = Params.TYPE_COMIC;
                arrType.add(type);
                imgP.repaint();
                break;
            case "板印":
                type = Params.TYPE_BINARIZATION;
                arrType.add(type);
                imgP.repaint();
                break;
            case "马赛克":
                type = Params.TYPE_MOSAIC;
                arrType.add(type);
                imgP.repaint();
                break;
            case "生成二维码":
                type = Params.TYPE_QRCODE;
                arrType.add(type);
                imgP.repaint();
                break;
            case "选择图片":
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "选择图片");
                File file = jfc.getSelectedFile();
                imgP.path = file.getAbsolutePath();
                imgP.repaint();
                //System.out.println(file.getAbsolutePath());
                break;
            case "回退":
                if (arrType.size()>1){
                    arrType.remove(arrType.size()-1);
                    imgP.repaint();
                }else{
                    JOptionPane.showMessageDialog(null, "最后一张！");
                }
                break;
            case "放大":
                imgP.rate*=1.2;
//                System.out.println(imgP.rate);
                imgP.repaint();
                break;
            case "缩小":
                imgP.rate*=0.8;
//                System.out.println(imgP.rate);
                imgP.repaint();
                break;
        }
    }
}
