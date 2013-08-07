package com.li.learn.demo05.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.li.learn.demo05.R;
import com.li.learn.demo05.domain.LocationFinder;
import com.li.learn.demo05.domain.LocationView;
import com.li.learn.demo05.domain.PathItem;
import com.li.learn.demo05.domain.ReceiveLocationCallback;
import com.li.learn.demo05.framework.BeanContext;
import com.li.learn.demo05.framework.SerialPersistence;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PathInputActivity extends Activity implements ReceiveLocationCallback {
    private static final int CODE_TAKEN_PHOTO = 1013;
    private static final String ALBUM_NAME = "demo05";
    private static final String JPEG_FILE_PREFIX = "demo05_";
    private static final String JPEG_FILE_SUFFIX = ".jpeg";
    public static final String HISTORY_STATES_PREFERENCE = "history_state";

    private File albumDir;
    private Button startCameraBtn;
    private PathItem currentPathItem;
    private Button saveBtn;
    private LocationView locationView;
    private Spinner categorySpinner;
    private EditText titleTextView;
    private EditText busTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_input_layout);
        initUI();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initAlbumDir();
        initPathItem(savedInstanceState);
        initLocationFinder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SerialPersistence serialPersistence = BeanContext.getInstance().getBean(SerialPersistence.class);
        PathItem pathItem = serialPersistence.readSerialObject(HISTORY_STATES_PREFERENCE);
        if (pathItem != null) {
            currentPathItem = pathItem;
            restorePathItemToUI();
        }
    }

    private void initPathItem(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("path_item")) {
            currentPathItem = (PathItem) savedInstanceState.getSerializable("path_item");
            restorePathItemToUI();
            return;
        }
        currentPathItem = createPathItem();
    }

    private void restorePathItemToUI() {
        categorySpinner.setSelection(currentPathItem.getSelectedCategoryPosition());
        titleTextView.setText(currentPathItem.getTitle());
        busTextView.setText(currentPathItem.getBus());
        locationView.setAutoLocation(currentPathItem.getAutoLocation());
        locationView.setRevisedLocation(currentPathItem.getRevisedLocation());
    }

    private void initLocationFinder() {
        LocationFinder locationFinder = BeanContext.getInstance().getBean(LocationFinder.class);
        locationFinder.registerReceiveLocationCallback(this);
    }

    private void initUI() {
        categorySpinner = (Spinner) findViewById(R.id.spinner_path_item_category);
        titleTextView = (EditText) findViewById(R.id.edit_text_path_item_category_details);
        busTextView = (EditText) findViewById(R.id.path_item_bus);
        locationView = (LocationView) findViewById(R.id.loc_view);

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
    }

    private void deleteOldImage() {
        File fullImageFile = new File(currentPathItem.getFullImagePath());
        fullImageFile.deleteOnExit();
    }

    private void savePathItem() {
        fetchPathItemData();
        if (currentPathItem.save()) {
            showTip("保存成功");
        } else {
            showTip("保存失败");
        }
    }

    private void fetchPathItemData() {
        currentPathItem.setCategory((String) categorySpinner.getSelectedItem());
        currentPathItem.setSelectedCategoryPosition(categorySpinner.getSelectedItemPosition());
        currentPathItem.setTitle(titleTextView.getText().toString());
        currentPathItem.setBus(busTextView.getText().toString());
        currentPathItem.setAutoLocation(locationView.getAutoLocation());
        currentPathItem.setRevisedLocation(locationView.getRevisedLocation());
    }

    private void initAlbumDir() {
        albumDir = new File(Environment.getExternalStorageDirectory() + File.separator + ALBUM_NAME);
        if (!albumDir.exists()) {
            albumDir.mkdirs();
        }
    }

    private PathItem createPathItem() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp;
        File fullImage = new File(
                albumDir, imageFileName + JPEG_FILE_SUFFIX
        ), thumbnailImage = new File(
                albumDir, imageFileName + "_thumbnail_" + JPEG_FILE_SUFFIX
        );
        return new PathItem(fullImage.getAbsolutePath(), thumbnailImage.getAbsolutePath());
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
        locationFinder.destroy();
        super.onDestroy();
    }

    @Override
    public void fetchLocationError(String errorMsg) {
        showTip(errorMsg);
    }

    @Override
    public void receiveLocation(String location) {
        currentPathItem.setAutoLocation(location);
        locationView.setAutoLocation(location);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("path_item", currentPathItem);
    }

    @Override
    protected void onPause() {
        super.onPause();
        fetchPathItemData();
        SerialPersistence serialPersistence = BeanContext.getInstance().getBean(SerialPersistence.class);
        serialPersistence.saveSerialObject(HISTORY_STATES_PREFERENCE, currentPathItem);
    }
}
