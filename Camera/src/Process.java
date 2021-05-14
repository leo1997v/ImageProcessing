import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图像滤镜处理的方法
 */
public class Process {
    /**
     * @param buffimg
     * @return buffimg的RGB矩阵
     */
    public static int[][] getMatrix(BufferedImage buffimg){
        int[][] imgdata = new int[buffimg.getWidth()][buffimg.getHeight()];
//		alt+/   enter
        for (int i = 0; i < imgdata.length; i++) {
            for (int j = 0; j < imgdata[i].length; j++) {

                imgdata[i][j] = buffimg.getRGB(i, j);
            }
        }
        return imgdata;
    }

    /*public static void paintOrigin(int[][] imgdata, Graphics g, double rate) {
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
    }*/

    /**
     * 原理：先转成灰度图，取当前像素的下一个像素，当当前像素与下一像素灰度值差超过某一阈值才将当前像素涂黑
     * @param originImg
     * @return 素描处理完成后的图像buffimg
     */
    public static BufferedImage paintSketch(BufferedImage originImg) {
        int[][] imgdata = getMatrix(originImg);
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
        return buffimg;
    }
    /**
     * 原理：转为灰度图
     * @param originImg
     * @return 黑白处理完成后的图像buffimg
     */
    public static BufferedImage paintBlackWhite(BufferedImage originImg) {
        int[][] imgdata = getMatrix(originImg);
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
        return buffimg;
    }

    /**
     * 原理：二值化，取当前像素的灰度，当灰度超过某一阈值才涂黑
     * @param originImg
     * @return 板印处理完成后的图像buffimg
     */
    public static BufferedImage paintBinarization(BufferedImage originImg) {
        int[][] imgdata = getMatrix(originImg);
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
        return buffimg;
    }

    /**
     * 原理：将填充像素点改为填充大小随机的圆
     * @param originImg
     * @return 油画处理完成后的图像buffimg
     */
    public static BufferedImage paintOilPainting(BufferedImage originImg) {
        int[][] imgdata = getMatrix(originImg);
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
        return buffimg;
    }



    /**
     * 原理：处理公式如下
     *  var newR = (0.393 * r + 0.769 * g + 0.189 * b);
     *  var newG = (0.349 * r + 0.686 * g + 0.168 * b);
     *  var newB = (0.272 * r + 0.534 * g + 0.131 * b);
     * @param originImg
     * @return 老照片处理完成后的图像buffimg
     */
    public static BufferedImage paintOld(BufferedImage originImg) {
        int[][] imgdata = getMatrix(originImg);
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
        return buffimg;
    }

    /**
     * 原理：处理公式如下
     *  var newR = Math.abs(g - b + g + r) * r / 256;
     *  var newG = Math.abs(b -g + b + r) * r / 256;
     *  var newB =  Math.abs(b -g + b + r) * g / 256;
     * @param originImg
     * @return 非主流处理完成后的图像buffimg
     */
    public static BufferedImage paintComic(BufferedImage originImg) {
        int[][] imgdata= getMatrix(originImg);
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
        return buffimg;
    }
    /**
     * 原理：将填充像素点改为填充大小为10*10的区域
     * @param originImg
     * @return 马赛克处理完成后的图像buffimg
     */
    public static BufferedImage paintMosaic(BufferedImage originImg) {
        int[][] imgdata = getMatrix(originImg);
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();

        for (int i = 0; i < imgdata.length; i+=10) {
            for (int j = 0; j < imgdata[i].length; j+=10) {
                int rgb = imgdata[i][j];
                Color color = new Color(rgb);
                buffg.setColor(color);
                buffg.fillRect( i,  j, 10, 10);
            }
        }
        return buffimg;
    }
    /**
     * 原理：将str转化成一个矩阵再绘出
     * @param  str 要转化成二维码的字符串
     * @return 马赛克处理完成后的图像 buffimg
     */
    /*public static BufferedImage paintQRCode( String str) {
        boolean[][] binaryNum = StringBinary.StringToBinary(str);

        BufferedImage buffimg = new BufferedImage(binaryNum[0].length*20, binaryNum.length*20, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffimg.getGraphics();

        for (int i = 0; i < binaryNum.length; i++) {
            for (int j = 0; j < 16; j++) {
                if(binaryNum[i][j]==false) {
                    g.setColor(Color.WHITE);
                }else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(j*20,i*20, 20, 20);
            }
        }
        return buffimg;
    }*/
}
