import java.awt.*;
import java.awt.image.BufferedImage;

public class BilinearInterpolation {
    /**
     * 防止下标越界
     * @param x
     * @param length
     * @return
     */
    public static double checkArrBoundary(double x, double length) {
        if (x > length) {
            return length;
        } else if (x < 0) {
            return 0;
        } else {
            return x;
        }
    }

    /**
     * 原理：双线性插值 矩阵映射
     * 假设一个原图为n*n大小的图像resize为一个m*m大小的图像
     * 求新图像上某一点(i,j)的像素过程：
     *      先计算这个点映射到原图的坐标 (i*m/n,j*m/n)可能为小数
     *      通过向上向下取整得到最近的四个顶点 upLeft,upRight,bottomLeft,bottomRight
     *      通过四个顶点的像素RGB值，用二维线性插值的方法计算(i*m/n,j*m/n)的像素RGB值
     *      回填到新图像的(i,j)
     * @param buffimg 原图像
     * @param rate resize的倍率
     * @return 处理完成后的图像
     */
    public static BufferedImage resize(BufferedImage buffimg, double rate) {
        int[][] imgData = Process.getMatrix(buffimg);
        int Height = imgData[0].length;
        int Width = imgData.length;
        int newHeight = (int) (Height * rate);
        int newWidth = (int) (Width * rate);
        BufferedImage newimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = newimg.getGraphics();
        for (double i = 0; i < newWidth; i++) {
            for (double j = 0; j < newHeight; j++) {

                double mapi = i / rate;
                double mapj = j / rate;
                //System.out.println(mapi+" "+mapj);

                double up = checkArrBoundary(Math.floor(mapj), (double) imgData[0].length - 1);
                double bottom = checkArrBoundary(Math.ceil(mapj), (double) imgData[0].length - 1);
                double right = checkArrBoundary(Math.ceil(mapi), (double) imgData.length - 1);
                double left = checkArrBoundary(Math.floor(mapi), (double) imgData.length - 1);


                //System.out.println(up+" "+bottom+" "+right+" "left);

                double upLeft = imgData[(int) left][(int) up];
                double upRight = imgData[(int) right][(int) up];
                double bottomLeft = imgData[(int) left][(int) bottom];
                double bottomRight = imgData[(int) right][(int) bottom];

                double upLeftR = (int) (upLeft) >> 16 & 0xFF;
                double upLeftG = (int) (upLeft) >> 8 & 0xFF;
                double upLeftB = (int) (upLeft) & 0xFF;
                double upRightR = (int) (upRight) >> 16 & 0xFF;
                double upRightG = (int) (upRight) >> 8 & 0xFF;
                double upRightB = (int) (upRight) & 0xFF;
                double bottomLeftR = (int) (bottomLeft) >> 16 & 0xFF;
                double bottomLeftG = (int) (bottomLeft) >> 8 & 0xFF;
                double bottomLeftB = (int) (bottomLeft) & 0xFF;
                double bottomRightR = (int) (bottomRight) >> 16 & 0xFF;
                double bottomRightG = (int) (bottomRight) >> 8 & 0xFF;
                double bottomRightB = (int) (bottomRight) & 0xFF;

                //System.out.println(upLeftR+" "+upLeftG+" "+upLeftB);
                //防止出现分母为0的情况！！！ 不会报错,但会导致数据异常 显示为NaN(not a number)
                double bottomR, bottomG, bottomB;
                if (left == right) {
                    bottomR = bottomLeftR;
                    bottomG = bottomLeftG;
                    bottomB = bottomLeftB;
                } else {
                    bottomR = (bottomRightR - bottomLeftR) * (mapi - left) / (right - left) + bottomLeftR;
                    bottomG = (bottomRightG - bottomLeftG) * (mapi - left) / (right - left) + bottomLeftG;
                    bottomB = (bottomRightB - bottomLeftB) * (mapi - left) / (right - left) + bottomLeftB;
                }


                double upR, upG, upB;
                if (left == right) {
                    upR = upLeftR;
                    upG = upLeftG;
                    upB = upLeftB;
                } else {
                    upR = (upRightR - upLeftR) * (mapi - left) / (right - left) + upLeftR;
                    upG = (upRightG - upLeftG) * (mapi - left) / (right - left) + upLeftG;
                    upB = (upRightB - upLeftB) * (mapi - left) / (right - left) + upLeftB;
                }
                //System.out.println(upLeft+" "+upLeft+" "+upLeft);
                //未考虑分母为0得错误写法
                /*double upR = (upRightR-upLeftR)*(mapi-left)/(right-left)+upLeftR;
                double upG = (upRightG-upLeftG)*(mapi-left)/(right-left)+upLeftG;
                double upB = (upRightB-upLeftB)*(mapi-left)/(right-left)+upLeftB;*/

                double newR, newG, newB;
                if (up == bottom) {
                    newR = bottomR;
                    newG = bottomG;
                    newB = bottomB;
                } else {
                    newR = (bottomR - upR) * (mapj - up) / (bottom - up) + upR;
                    newG = (bottomG - upG) * (mapj - up) / (bottom - up) + upG;
                    newB = (bottomB - upB) * (mapj - up) / (bottom - up) + upB;
                }


                //System.out.println(newR+" "+newG+" "+newB);

                Color color = new Color((int) newR, (int) newG, (int) newB);
                g.setColor(color);
                g.fillRect((int) i, (int) j, 1, 1);
            }
        }
        return newimg;
    }
}
