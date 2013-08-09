package com.li.learn.path.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.li.learn.path.R;
import com.li.learn.path.components.PathItemList;
import com.li.learn.path.utils.Constants;

public class MainActivity extends Activity {

    private PathItemList pathItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initUI();
    }

    private void initUI() {
        pathItemList = (PathItemList) findViewById(R.id.path_item_list);
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
}
