package com.li.learn.demo05.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PathItemDBOperator extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo05.db";
    private static final String PATH_ITEMS_TABLE_NAME = "path_items";
    private static final int DB_VERSION = 1;

    private static final String FULL_IMAGE_PATH_COLUMN = "full_image_path";
    private static final String THUMBNAIL_IMAGE_PATH_COLUMN = "thumbnail_image_path";
    private static final String CATEGORY_COLUMN = "category";
    private static final String TITLE_COLUMN = "title";
    private static final String BUS_COLUMN = "bus";
    private static final String AUTO_LOCATION_COLUMN = "auto_location";
    private static final String REVISED_LOCATION = "revised_location";


    public PathItemDBOperator(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase);
    }

    private void createTable(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("Create table ").append(PATH_ITEMS_TABLE_NAME).append("(")
                .append("id").append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(CATEGORY_COLUMN).append(" TEXT, ")
                .append(TITLE_COLUMN).append(" TEXT, ")
                .append(BUS_COLUMN).append(" TEXT, ")
                .append(AUTO_LOCATION_COLUMN).append(" TEXT, ")
                .append(REVISED_LOCATION).append(" TEXT, ")
                .append(FULL_IMAGE_PATH_COLUMN).append(" TEXT, ")
                .append(THUMBNAIL_IMAGE_PATH_COLUMN).append(" TEXT")
                .append(");");
        db.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public long savePathItem(PathItem pathItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN, pathItem.getCategory());
        contentValues.put(TITLE_COLUMN, pathItem.getTitle());
        contentValues.put(BUS_COLUMN, pathItem.getBus());
        contentValues.put(AUTO_LOCATION_COLUMN, pathItem.getAutoLocation());
        contentValues.put(REVISED_LOCATION, pathItem.getRevisedLocation());
        contentValues.put(FULL_IMAGE_PATH_COLUMN, pathItem.getFullImagePath());
        contentValues.put(THUMBNAIL_IMAGE_PATH_COLUMN, pathItem.getThumbnailImagePath());
        return getWritableDatabase().insert(PATH_ITEMS_TABLE_NAME, null, contentValues);
    }
}
