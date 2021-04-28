import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Process {

    public static void paintOrigin(int[][] imgdata, Graphics g, double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();

        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {
                int rgb = imgdata[i][j];
                Color color = new Color(rgb);
                buffg.setColor(color);
                buffg.fillRect( i,  j, 1, 1);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }

    public static void paintSketch(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();

        for (int i = 1; i < imgdata.length - 1; i++) {
            for (int j = 0; j < imgdata[i].length - 1; j++) {
                int rgb = imgdata[i][j];
                int red = rgb >> 16 & 0xFF;
                int green = rgb >> 8 & 0xFF;
                int blue = rgb >> 0 & 0xFF;
                int gray = (int) (red * 0.4 + green * 0.32 + blue * 0.28);

                int nrgb = imgdata[i + 1][j + 1];
                int nred = nrgb >> 16 & 0xFF;
                int ngreen = nrgb >> 8 & 0xFF;
                int nblue = nrgb >> 0 & 0xFF;
                int ngray = (int) (nred * 0.4 + ngreen * 0.32 + nblue * 0.28);

                if (Math.abs(ngray - gray) > 8) {
                    buffg.setColor(Color.black);
                } else {
                    buffg.setColor(Color.white);
                }
                buffg.fillRect( i,  j, 1, 1);
            }
        }
        g.drawImage(buffimg, 100, 100,(int)(imgdata.length*rate),(int)(imgdata[0].length*rate), null);
    }

    public static void paintBlackWhite(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {
                int rgb = imgdata[i][j];
                int red = rgb >> 16 & 0xFF;
                int green = rgb >> 8 & 0xFF;
                int blue = rgb >> 0 & 0xFF;
                int gray = (int) ((red + green + blue) / 3);
                Color color = new Color(gray, gray, gray);
                buffg.setColor(color);
                buffg.fillRect( i,  j, 1, 1);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }

    public static void paintBinarization(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {
                int rgb = imgdata[i][j];
                int red = rgb >> 16 & 0xFF;
                int green = rgb >> 8 & 0xFF;
                int blue = rgb >> 0 & 0xFF;
                int gray = (int) (red * 0.41 + green * 0.32 + blue * 0.27);
                if(gray>135){
                    buffg.setColor(Color.white);
                }else{
                    buffg.setColor(Color.black);
                }
                buffg.fillRect( i,  j, 1, 1);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }

    public static void paintOilPainting(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        Random rd = new Random();
        for (int i = 0; i < imgdata.length - 5; i+=2) {
            for (int j = 0; j < imgdata[i].length; j+=2) {
                int rgb = imgdata[i][j];
                Color color = new Color(rgb);
                buffg.setColor(color);
                buffg.fillOval(i, j, rd.nextInt(3) + 5, rd.nextInt(3) + 5);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }

    //var newR = (0.393 * r + 0.769 * g + 0.189 * b);
    //var newG = (0.349 * r + 0.686 * g + 0.168 * b);
    //var newB = (0.272 * r + 0.534 * g + 0.131 * b);

    public static void paintOld(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {
                int rgb = imgdata[i][j];
                int red = rgb >> 16 & 0xFF;
                int green = rgb >> 8 & 0xFF;
                int blue = rgb >> 0 & 0xFF;

                int newRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
                if (newRed > 255) {
                    newRed = 255;
                }
                if (newRed < 0) {
                    newRed = 0;
                }
                int newGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
                if (newGreen > 255) {
                    newGreen = 255;
                }
                if (newGreen < 0) {
                    newGreen = 0;
                }
                int newBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);
                if (newBlue > 255) {
                    newBlue = 255;
                }
                if (newBlue < 0) {
                    newBlue = 0;
                }
                Color color = new Color(newRed, newGreen, newBlue);
                buffg.setColor(color);
                buffg.fillRect(i, j, 1, 1);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }


    //var newR = Math.abs(g - b + g + r) * r / 256;
    //var newG = Math.abs(b -g + b + r) * r / 256;
    //var newB =  Math.abs(b -g + b + r) * g / 256;

    public static void paintComic(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {
                int rgb = imgdata[i][j];
                int red = rgb >> 16 & 0xFF;
                int green = rgb >> 8 & 0xFF;
                int blue = rgb >> 0 & 0xFF;

                int newRed = (int) (Math.abs(green - blue + green + red) * red / 256);
                int newGreen = (int) (Math.abs(blue -green + blue + red) * red / 256);
                int newBlue = (int) (Math.abs(blue -green + blue + red) * green / 256);
                if (newRed > 255) {
                    newRed = 255;
                }
                if (newRed < 0) {
                    newRed = 0;
                }
                if (newGreen > 255) {
                    newGreen = 255;
                }
                if (newGreen < 0) {
                    newGreen = 0;
                }
                if (newBlue > 255) {
                    newBlue = 255;
                }
                if (newBlue < 0) {
                    newBlue = 0;
                }
                Color color = new Color(newRed, newGreen, newBlue);
                buffg.setColor(color);
                buffg.fillRect( i,  j, 1, 1);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }

    public static void paintMosaic(int[][] imgdata, Graphics g,double rate) {
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();

        for (int i = 0; i < imgdata.length-10; i+=10) {
            for (int j = 0; j < imgdata[i].length-10; j+=10) {
                int rgb = imgdata[i][j];
                Color color = new Color(rgb);
                buffg.setColor(color);
                buffg.fillRect( i,  j, 10, 10);
            }
        }
        g.drawImage(buffimg, 100, 100, (int)(imgdata.length*rate),(int)(imgdata[0].length*rate),null);
    }

    public static void paintQRCode( Graphics g,String str) {
        boolean[][] binaryNum = StringBinary.StringToBinary(str);
        for (int i = 0; i < binaryNum.length; i++) {
            for (int j = 0; j < 16; j++) {
                if(binaryNum[i][j]==false) {
                    g.setColor(Color.WHITE);
                }else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(j*20+100,i*20+100, 20, 20);
            }
        }
    }
}
