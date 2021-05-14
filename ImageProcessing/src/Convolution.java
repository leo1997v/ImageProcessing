import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 通过卷积进行图像处理的方法
 */
public class Convolution {

    public static int checkRGBBoundary(int x){
        if(x>255){
            return 255;
        }else if(x<0){
            return 0;
        }else{
            return x;
        }
    }

    /**
     * 计算每个像素卷积后的颜色值
     * @param imgdata 原图像
     * @param i 待卷积像素坐标i
     * @param j 待卷积像素坐标j
     * @param core 一个3*3的卷积核
     * @return 该像素卷积后的颜色对象
     */
    public static Color calculate(int[][] imgdata, int i, int j, int[][] core) {

        int rgb00 = imgdata[i - 1][j - 1];
        int red00 = rgb00 >> 16 & 0xFF;
        int green00 = rgb00 >> 8 & 0xFF;
        int blue00 = rgb00 & 0xFF;

        int rgb01 = imgdata[i][j - 1];
        int red01 = rgb01 >> 16 & 0xFF;
        int green01 = rgb01 >> 8 & 0xFF;
        int blue01 = rgb01 & 0xFF;

        int rgb02 = imgdata[i+1][j - 1];
        int red02 = rgb02 >> 16 & 0xFF;
        int green02 = rgb02 >> 8 & 0xFF;
        int blue02 = rgb02 & 0xFF;

        int rgb10 = imgdata[i - 1][j];
        int red10 = rgb10 >> 16 & 0xFF;
        int green10 = rgb10 >> 8 & 0xFF;
        int blue10 = rgb10 & 0xFF;

        int rgb11 = imgdata[i][j];
        int red11 = rgb11 >> 16 & 0xFF;
        int green11 = rgb11 >> 8 & 0xFF;
        int blue11 = rgb11 & 0xFF;

        int rgb12 = imgdata[i+1][j];
        int red12 = rgb12 >> 16 & 0xFF;
        int green12 = rgb12 >> 8 & 0xFF;
        int blue12 = rgb12 & 0xFF;

        int rgb20 = imgdata[i-1][j+1];
        int red20 = rgb20 >> 16 & 0xFF;
        int green20 = rgb20 >> 8 & 0xFF;
        int blue20 = rgb20 & 0xFF;

        int rgb21 = imgdata[i][j+1];
        int red21 = rgb21 >> 16 & 0xFF;
        int green21 = rgb21 >> 8 & 0xFF;
        int blue21 = rgb21 & 0xFF;

        int rgb22 = imgdata[i+1][j+1];
        int red22 = rgb22 >> 16 & 0xFF;
        int green22 = rgb22 >> 8 & 0xFF;
        int blue22 = rgb22 & 0xFF;
        
        int newR = red00*core[0][0]+red01*core[0][1]+red02*core[0][2]
                + red10*core[1][0]+red11*core[1][1]+red12*core[1][2]
                + red20*core[2][0]+red21*core[2][1]+red22*core[2][2];

        int newG = green00*core[0][0]+green01*core[0][1]+green02*core[0][2]
                + green10*core[1][0]+green11*core[1][1]+green12*core[1][2]
                + green20*core[2][0]+green21*core[2][1]+green22*core[2][2];

        int newB = blue00*core[0][0]+blue01*core[0][1]+blue02*core[0][2]
                + blue10*core[1][0]+blue11*core[1][1]+blue12*core[1][2]
                + blue20*core[2][0]+blue21*core[2][1]+blue22*core[2][2];

        Color color =new Color(checkRGBBoundary(newR),checkRGBBoundary(newG),checkRGBBoundary(newB));
        return color;
    }


    /**
     * 锐化处理 卷积核{{-1,-1,-1},{-1,9,-1},{-1,-1,-1}}
     * @param originImg
     * @return
     */
    public static BufferedImage sharpen(BufferedImage originImg) {
        int[][] imgdata = Process.getMatrix(originImg);
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        int[][] core = new int[][]{{-1,-1,-1},{-1,9,-1},{-1,-1,-1}};
        for (int i = 1; i < imgdata.length - 1; i++) {
            for (int j = 1; j < imgdata[i].length - 1; j++) {
                buffg.setColor(calculate(imgdata,i,j,core));
                buffg.fillRect(i,j,1,1);
            }
        }
        return buffimg;
    }

    /**
     * 浮雕 卷积核{{-1,-1,0},{-1,0,1},{0,1,1}}
     * @param originImg
     * @return
     */
    public static BufferedImage emboss(BufferedImage originImg) {
        int[][] imgdata = Process.getMatrix(originImg);
        BufferedImage buffimg = new BufferedImage(imgdata.length, imgdata[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics buffg = buffimg.getGraphics();
        int[][] core = new int[][]{{-1,-1,0},{-1,0,1},{0,1,1}};
        for (int i = 1; i < imgdata.length - 1; i++) {
            for (int j = 1; j < imgdata[i].length - 1; j++) {
                buffg.setColor(calculate(imgdata,i,j,core));
                buffg.fillRect(i,j,1,1);
            }
        }
        return buffimg;
    }
}
