package com.li.learn.demo05.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.li.learn.demo05.R;
import com.li.learn.demo05.domain.LocationFinder;
import com.li.learn.demo05.domain.LocationView;
import com.li.learn.demo05.domain.PathItem;
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
    private Button startCameraBtn;
    private PathItem currentPathItem;
    private Button saveBtn;
    private LocationView locationView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_input_layout);
        initUI();
        initAlbumDir();
        initCurrentPathItem();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        locationView.restore(currentPathItem.getAutoLocation());

    }

    private void initUI() {
        startCameraBtn = (Button) findViewById(R.id.btn_start_camera);
        startCameraBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteOldImage();
                try {
                    startCamera();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        saveBtn = (Button) findViewById(R.id.btn_save_path);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                savePathItem();
            }
        });
        locationView = (LocationView) findViewById(R.id.loc_view);
    }

    private void deleteOldImage() {
        File fullImageFile = new File(currentPathItem.getFullImagePath());
        fullImageFile.deleteOnExit();
    }

    private void savePathItem() {
        if (currentPathItem.save()) {
            showTip("保存成功");
        } else {
            showTip("保存失败");
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
            handleImageTaken();
        }
    }

    private void handleImageTaken() {
        if (!imageTakenSuccess()) {
            showTip("拍照没有成功！");
            return;
        }
        showTip("拍照成功！");
    }

    private void showTip(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean imageTakenSuccess() {
        return new File(currentPathItem.getFullImagePath()).exists();
    }


    @Override
    protected void onDestroy() {
        LocationFinder locationFinder = BeanContext.getInstance().getBean(LocationFinder.class);
        locationFinder.stop();
        super.onDestroy();
    }
}
