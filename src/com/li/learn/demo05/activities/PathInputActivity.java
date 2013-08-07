package com.li.learn.demo05.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.li.learn.demo05.R;
import com.li.learn.demo05.framework.ImageUtils;
import com.li.learn.demo05.domain.PathItem;
import com.li.learn.demo05.domain.PathItemDBOperator;
import com.li.learn.demo05.framework.BeanContext;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathInputActivity extends Activity {
    private static final int CODE_TAKEN_PHOTO = 1013;
    private static final String ALBUM_NAME = "demo05";
    private static final String JPEG_FILE_PREFIX = "demo05_";
    private static final String JPEG_FILE_SUFFIX = ".jpeg";

    private File albumDir;
    private ImageView imageView;
    private Button startCameraBtn;
    private PathItem currentPathItem;
    private EditText whereEditText;
    private Button saveBtn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_input_layout);
        initUI();
        initAlbumDir();
        initCurrentPathItem();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BeanContext beanContext = BeanContext.getInstance();
        beanContext.putBean(PathItemDBOperator.class, new PathItemDBOperator(getApplicationContext()));
    }

    private void initUI() {
        startCameraBtn = (Button) findViewById(R.id.demo06_camera_start_btn);
        startCameraBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    if (currentPathItem != null) {
                        startCamera();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        saveBtn = (Button) findViewById(R.id.demo06_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currentPathItem != null) {
                    savePathItem();
                }
            }
        });
        imageView = (ImageView) findViewById(R.id.demo06_image_view);
        whereEditText = (EditText) findViewById(R.id.demo06_where);
    }

    private void savePathItem() {
        currentPathItem.setDescription(whereEditText.getText().toString());
        if (currentPathItem.save()) {
            Toast.makeText(this, "save success", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(this, "save fail", Toast.LENGTH_SHORT);
        }
    }

    private void initAlbumDir() {
        albumDir = new File(Environment.getExternalStorageDirectory() + File.separator + ALBUM_NAME);
        if (!albumDir.exists()) {
            albumDir.mkdirs();
        }
    }

    private void initCurrentPathItem() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = JPEG_FILE_PREFIX + timeStamp;
            File fullImage = new File(
                    albumDir, imageFileName + JPEG_FILE_SUFFIX
            );
            File thumbnailImage = new File(
                    albumDir, imageFileName + "_thumbnail_" + JPEG_FILE_SUFFIX
            );
            currentPathItem = new PathItem(fullImage.getAbsolutePath(), thumbnailImage.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startCamera() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                new File(currentPathItem.getFullImagePath())));
        startActivityForResult(intent, CODE_TAKEN_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == CODE_TAKEN_PHOTO) {
            Bitmap thumbnailBitmap = ImageUtils.decodeBitmapFromFile(currentPathItem.getFullImagePath(), 250, 250);
            ImageUtils.saveBitmap(currentPathItem.getThumbnailImagePath(), thumbnailBitmap);
            imageView.setImageBitmap(thumbnailBitmap);
        }
    }

}
