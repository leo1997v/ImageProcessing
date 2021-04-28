import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;

public class ImageProcessing extends JFrame {
    JButton btnOrigin;
    JButton btnSketch;
    JButton btnOilPainting;
    JButton btnBlackWhite;
    JButton btnOld;
    JButton btnComic;
    JButton btnSelect;
    JButton btnQRCode;
    JButton btnMosaic;
    JButton btnBack;
    JButton btnEnlarge;
    JButton btnShrink;
    JButton btnBinarization;

    JTextField str;
    double rate;


    String path;

    Listener listen = new Listener();

    ImageProcessing(String path) {
        this.path = path;
        this.rate = 1;
        listen.type = Params.TYPE_ORIGIN;
        btnSketch = new JButton("素描");
        btnOilPainting = new JButton("油画");
        btnBlackWhite = new JButton("黑白");
        btnOld = new JButton("老照片");
        btnComic = new JButton("非主流");
        btnOrigin = new JButton("原图");
        btnSelect = new JButton("选择图片");
        btnQRCode = new JButton("生成二维码");
        btnMosaic = new JButton("马赛克");
        btnBack = new JButton("回退");
        btnEnlarge = new JButton("放大");
        btnShrink = new JButton("缩小");
        btnBinarization = new JButton("板印");
        str = new JTextField(25);
        str.setText("霏霏真好看");


        listen.imgP = this;

        btnSelect.addActionListener(listen);
        btnOrigin.addActionListener(listen);
        btnSketch.addActionListener(listen);
        btnBlackWhite.addActionListener(listen);
        btnOilPainting.addActionListener(listen);
        btnOld.addActionListener(listen);
        btnComic.addActionListener(listen);
        btnMosaic.addActionListener(listen);
        btnQRCode.addActionListener(listen);
        btnBack.addActionListener(listen);
        btnEnlarge.addActionListener(listen);
        btnShrink.addActionListener(listen);
        btnBinarization.addActionListener(listen);


        setTitle("图像处理");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        add(btnSelect);
        add(btnOrigin);
        add(btnSketch);
        add(btnOilPainting);
        add(btnBlackWhite);
        add(btnOld);
        add(btnComic);
        add(btnMosaic);
        add(btnBinarization);
        add(btnQRCode);
        add(str);
        add(btnEnlarge);
        add(btnShrink);
        add(btnBack);
        setVisible(true);
    }


    public int[][] getPix(String path) {
        File file = new File(path);
        BufferedImage buffimg = null;

        try {
            buffimg = ImageIO.read(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int[][] imgdata = new int[buffimg.getWidth()][buffimg.getHeight()];
//		alt+/   enter
        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {

                imgdata[i][j] = buffimg.getRGB(i, j);
            }
        }
        return imgdata;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int[][] imgdata = getPix(path);
//        System.out.println(rate);

        switch (listen.arrType.get(listen.arrType.size() - 1)) {
            case Params.TYPE_ORIGIN:
                Process.paintOrigin(imgdata, g,rate);
                break;
            case Params.TYPE_SKETCH:
                Process.paintSketch(imgdata, g,rate);
                break;
            case Params.TYPE_BLACK_WHITE:
                Process.paintBlackWhite(imgdata, g,rate);
                break;
            case Params.TYPE_OIL_PAINTING:
                Process.paintOilPainting(imgdata, g,rate);
                break;
            case Params.TYPE_OLD:
                Process.paintOld(imgdata, g,rate);
                break;
            case Params.TYPE_COMIC:
                Process.paintComic(imgdata, g,rate);
                break;
            case Params.TYPE_MOSAIC:
                Process.paintMosaic(imgdata, g,rate);
                break;
            case Params.TYPE_QRCODE:
                Process.paintQRCode(g, str.getText());
                break;
            case Params.TYPE_BINARIZATION:
                Process.paintBinarization(imgdata, g,rate);
                break;
        }

    }


    public static void main(String args[]) {
        new ImageProcessing("./src/ufeii.jpg");
    }

}
