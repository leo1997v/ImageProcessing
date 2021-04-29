import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ImageProcessing extends JFrame {
    JButton btnSelect;

    JButton btnOrigin;
    JButton btnSketch;
    JButton btnOilPainting;
    JButton btnBlackWhite;
    JButton btnOld;
    JButton btnComic;
    JButton btnMosaic;
    JButton btnBinarization;

    JButton btnResize;
    JButton btnBack;

    JButton btnSharpen;
    JButton btnEmboss;


    ArrayList<BufferedImage> arrBuffImg = new ArrayList<>();

    JTextField str;
    JTextField inputRate;

    JButton btnQRCode;

    double rate;


    String path;

    Listener listen = new Listener();

    ImageProcessing(String path) {
        this.path = path;
        this.rate = 1;
        btnSketch = new JButton("素描");
        btnOilPainting = new JButton("油画");
        btnBlackWhite = new JButton("黑白");
        btnOld = new JButton("老照片");
        btnComic = new JButton("阴冷");
        btnOrigin = new JButton("原图");
        btnSelect = new JButton("选择图片");
        btnQRCode = new JButton("生成二维码");
        btnMosaic = new JButton("马赛克");
        btnBack = new JButton("回退");
        btnResize = new JButton("调整大小");
        inputRate = new JTextField("缩放倍率");

        btnBinarization = new JButton("板印");
        btnSharpen = new JButton("锐化");
        btnEmboss = new JButton("浮雕");
        str = new JTextField(25);
        str.setText("");


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
        btnResize.addActionListener(listen);
        btnBinarization.addActionListener(listen);
        btnSharpen.addActionListener(listen);
        btnEmboss.addActionListener(listen);


        setTitle("图像处理");
        setSize(1920, 1080);
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
        add(btnSharpen);
        add(btnEmboss);
        add(inputRate);
        add(btnResize);
        add(btnBack);
        arrBuffImg.add(getPix(path));
        setVisible(true);
    }


    public BufferedImage getPix(String path) {
        File file = new File(path);
        BufferedImage buffimg = null;

        try {
            buffimg = ImageIO.read(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return buffimg;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        draw(arrBuffImg.get(arrBuffImg.size()-1),g);

    }



    public void draw(BufferedImage buffimg,Graphics g){
        g.drawImage(buffimg, 100, 100, null);
    }


    public static void main(String args[]) {
        new ImageProcessing("./src/ufei.png");
    }

}
