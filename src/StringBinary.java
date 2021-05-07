public class StringBinary {


    /**
     * 将每个字符取出并补码填入一个boolean[][]矩阵，每行表示一个字符
     * @param str 要转换的字符串
     * @return 二维矩阵，每行表示一个字符
     */
    public static boolean[][] StringToBinary(String str){
        boolean[][] binaruNum = new boolean[str.length()][16];
        char[] chars = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            chars[i]=str.charAt(i);
            //System.out.println(chars[i]);
            String binaryChar = Integer.toBinaryString(chars[i]);
            //System.out.println(binaryChar);
            int bsize = binaryChar.length();
            while(bsize<16){
                binaryChar = 0 + binaryChar;
                bsize++;
            }
            //System.out.println(binaryChar+" "+binaryChar.length());
            for (int j = 0; j <16 ; j++) {
                if (binaryChar.charAt(j)=='0'){
                    binaruNum[i][j]=false;
                }else{
                    binaruNum[i][j]=true;
                }
               // System.out.println(binaruNum[i][j]);
            }
        }
        return binaruNum;

    }
}
