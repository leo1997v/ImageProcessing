import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

public class Listener implements ActionListener {


    ImageProcessing imgP;


    public void actionPerformed(ActionEvent e) {
        String txt = e.getActionCommand();
        BufferedImage img = null;
        switch (txt) {
            case "素描":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintSketch(img));
                imgP.repaint();
                break;
            case "原图":
                imgP.arrBuffImg.add(imgP.getPix(imgP.path));
                imgP.repaint();
                break;
            case "油画":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintOilPainting(img));
                imgP.repaint();
                break;
            case "黑白":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintBlackWhite(img));
                imgP.repaint();
                break;
            case "老照片":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintOld(img));
                imgP.repaint();
                break;
            case "阴冷":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintComic(img));
                imgP.repaint();
                break;
            case "板印":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintBinarization(img));
                imgP.repaint();
                break;
            case "马赛克":
                img =BilinearInterpolation.resize(imgP.getPix(imgP.path),imgP.rate);
                imgP.arrBuffImg.add(Process.paintMosaic(img));
                imgP.repaint();
                break;
            case "生成二维码":
                if(imgP.str.getText().length()==0){
                    JOptionPane.showMessageDialog(null,"输入为空");
                }else{
                    imgP.arrBuffImg.add(Process.paintQRCode(imgP.str.getText()));
                    imgP.repaint();
                }
                break;
            case "锐化":
                imgP.arrBuffImg.add(Convolution.sharpen(imgP.arrBuffImg.get(imgP.arrBuffImg.size()-1)));
                imgP.repaint();
                break;
            case "浮雕":
                imgP.arrBuffImg.add(Convolution.emboss(imgP.arrBuffImg.get(imgP.arrBuffImg.size()-1)));
                imgP.repaint();
                break;
            case "选择图片":
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "选择图片");
                File file = jfc.getSelectedFile();
                imgP.path = file.getAbsolutePath();
                //重置图片序列
                imgP.arrBuffImg = new ArrayList<>();
                imgP.arrBuffImg.add(imgP.getPix(imgP.path));
                imgP.rate = 1;
                imgP.repaint();
                //System.out.println(file.getAbsolutePath());
                break;
            case "回退":
                if (imgP.arrBuffImg.size()>1){
                    imgP.arrBuffImg.remove(imgP.arrBuffImg.size()-1);
                    imgP.repaint();
                }else{
                    JOptionPane.showMessageDialog(null, "最后一张！");
                }
                break;
            case "调整大小":
                //正则表达式，仅匹配数字，可带小数点
                String pattern ="^\\d+(\\.\\d+)?";
                boolean isMatch = Pattern.matches(pattern, imgP.inputRate.getText());
                if (isMatch){
                    imgP.rate = Double.parseDouble(imgP.inputRate.getText());
                    imgP.arrBuffImg.add(BilinearInterpolation.resize(imgP.arrBuffImg.get(imgP.arrBuffImg.size()-1),imgP.rate));
                    imgP.repaint();
                }else{
                    JOptionPane.showMessageDialog(null,"请输入一个数字");
                }
                break;

        }
    }
}
