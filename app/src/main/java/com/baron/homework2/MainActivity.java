package com.baron.homework2;
//Baron Hsieh 謝秉曄


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.aruco.Aruco;
import org.opencv.aruco.Dictionary;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    Scalar lineColor = new Scalar(255,0,0,255);
    int lineWidth = 3;
    Mat img = null;



    String decodeQRcode(Bitmap bitmap){
        Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, mat);
        QRCodeDetector qrCodeDetector = new QRCodeDetector();
        String result = qrCodeDetector.detectAndDecode(mat);
        Log.d("BB", "Coordinates" + result);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = findViewById(R.id.imageView);
        Button button= findViewById(R.id.button);
        final  Bitmap bm = ((BitmapDrawable) imgView.getDrawable()).getBitmap();

        if (!OpenCVLoader.initDebug()) {
           Log.d("run","unavailable");
        } else {
            Log.d("run","Normal");
        }



button.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view) {

        String string = decodeQRcode(bm);
        String[] output = string.split(";");
        for (String line : output) {
            Point[] points = {new Point(), new Point()};
            String[] cood = line.split("");
            int num = 0;


            for (String coods : cood) {

                String[] xy = coods.split(",");
                points[num] = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
                num++;

            }

            Imgproc.line(img, points[0], points[1], lineColor, lineWidth);
        }
    }
});






        }
    }












