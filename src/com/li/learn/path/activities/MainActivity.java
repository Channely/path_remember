package com.li.learn.path.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import com.li.learn.path.R;
import com.li.learn.path.animations.ZoomViewAnimation;
import com.li.learn.path.components.PathItemDetailsView;
import com.li.learn.path.components.PathItemList;
import com.li.learn.path.components.PathItemSelectedListener;
import com.li.learn.path.domain.PathItem;
import com.li.learn.path.utils.Constants;

public class MainActivity extends Activity {

    private PathItemList pathItemList;
    private PathItemDetailsView pathItemDetailsView;

    private ZoomViewAnimation zoomViewAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initUI();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initAnimation();
    }


    private void initAnimation() {
        zoomViewAnimation = new ZoomViewAnimation(pathItemDetailsView, 300);
    }

    private void initUI() {
        pathItemList = (PathItemList) findViewById(R.id.path_item_list);
        pathItemDetailsView = (PathItemDetailsView) findViewById(R.id.path_item_details);
        pathItemList.registerPathItemSelectedListener(new PathItemSelectedListener() {

            @Override
            public void onSelect(PathItem pathItem) {
                if (!pathItem.hasTakenImage()) return;
                pathItemDetailsView.setImage(pathItem.getFullImagePath());
                zoomViewAnimation.toFullScreen();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                onSearchRequested();
                return true;
            case R.id.menu_add_path_item:
                startActivityForResult(new Intent(this, PathInputActivity.class), Constants.CREATE_PATH_ITEM_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CREATE_PATH_ITEM_CODE && resultCode == RESULT_OK) {
            pathItemList.refresh();

        }
    }

    private View getRootView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }
}
