package com.baron.homework2alternative;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //ethan
    ImageView imgView;
    TextView txtView;
    String decodeQRcode(Bitmap bitmap) {
        Mat mat = new Mat(bitmap.getWidth(), bitmap.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, mat);
        QRCodeDetector qrCodeDetector = new QRCodeDetector();
        String result = qrCodeDetector.detectAndDecode(mat);
        Log.d("BB",""+result);
        txtView.setText(result);
        return result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        if (!OpenCVLoader.initDebug()) {
            Log.d("BB", "Unavailable");
        } else {
            Log.d("BB", "Normal");
        }
        imgView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.textView);
        final Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = decodeQRcode(bitmap);
                String[] result = string.split(";");
                String[] deletespace = string.split(" ");
                Mat img = null;
                int lineWidth = 3;
                Scalar lineColor = new Scalar(255,0,0,255);
                Point point1 = new Point(100,0);
                Point point2 = new Point(160, 180);
                Point point3 = new Point(10,60);
                Point point4 = new Point(190, 60);
                Point point5 = new Point(40, 180);
                try{
                    img = Utils.loadResource(MainActivity.this,R.drawable.first, CvType.CV_8UC4);
                    Imgproc.line(img, point1, point2, lineColor, lineWidth);
                    Imgproc.line(img, point2, point3, lineColor, lineWidth);
                    Imgproc.line(img, point3, point4, lineColor, lineWidth);
                    Imgproc.line(img, point4, point5, lineColor, lineWidth);
                    Imgproc.line(img, point5, point1, lineColor, lineWidth);
                    Bitmap bitmap = Bitmap.createBitmap(img.width(), img.height(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(img, bitmap);
                    ImageView imgView = findViewById(R.id.imageView);
                    imgView.setImageBitmap(bitmap);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }


}