package com.wonders.xlab.qrscanner;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * Created by hua on 16/8/8.
 * util to generator qrcode and barcode
 */
public class Encoder {
    private int width;
    private int height;

    public Encoder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @param s
     * @return
     * @throws Exception
     */
    public Bitmap qrcode(String s) throws Exception {
        //二维码QR_CODE
        BarcodeFormat fomt = BarcodeFormat.QR_CODE;
        //编码转换
        String a = new String(s.getBytes("utf-8"), "ISO-8859-1");
        BitMatrix matrix = new MultiFormatWriter().encode(a, fomt, width, height);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixel = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i))
                    pixel[i * width + j] = 0xff000000;
            }
        }
        Bitmap bmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmap.setPixels(pixel, 0, width, 0, 0, width, height);
        return bmap;
    }

    /**
     * 绘制条形码
     *
     * @param ss
     * @return
     * @throws Exception
     */
    public Bitmap barcode(String ss) throws Exception {
        //条形码CODE_128
        BarcodeFormat fomt = BarcodeFormat.CODE_128;
        BitMatrix matrix = new MultiFormatWriter().encode(ss, fomt, width, height);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixel = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i))
                    pixel[i * width + j] = 0xff000000;
            }
        }
        Bitmap bmapp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmapp.setPixels(pixel, 0, width, 0, 0, width, height);
        return bmapp;
    }
}

