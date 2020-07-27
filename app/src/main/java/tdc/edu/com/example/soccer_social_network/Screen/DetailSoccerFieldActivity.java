package tdc.edu.com.example.soccer_social_network.Screen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import tdc.edu.com.example.soccer_social_network.R;

public class DetailSoccerFieldActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    Animation in,out;
    private ArrayList<Integer> listImgViewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_san_layout);
        anhXa();
        slideViewFlipper();
    }

    public Bitmap loadLagreImage(int imageID, int targetHeight, int targetWidth){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),imageID,options);
        final int originalHeight = options.outHeight;
        final int originalWidth = options.outWidth;
        int inSampleSize = 1;
        while (originalHeight / (inSampleSize*2) > targetHeight && (originalWidth / (inSampleSize*2) > targetWidth)){
            inSampleSize *= 2;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(),imageID,options);
    }

    private void anhXa()
    {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
    }

    private void slideViewFlipper() {
        listImgViewFlipper = new ArrayList<Integer>();
        listImgViewFlipper.add(R.drawable.san1);
        listImgViewFlipper.add(R.drawable.san2);
        listImgViewFlipper.add(R.drawable.san3);
        for (int i = 0; i < listImgViewFlipper.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(loadLagreImage(listImgViewFlipper.get(i), 200, 200));
            viewFlipper.addView(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        in = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        out = AnimationUtils.loadAnimation(this,R.anim.fade_out);

        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }
}