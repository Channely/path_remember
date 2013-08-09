package com.li.learn.path.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PathItemDBOperator extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo05.db";
    private static final String PATH_ITEMS_TABLE_NAME = "path_items";
    private static final int DB_VERSION = 1;

    public static final String FULL_IMAGE_PATH_COLUMN = "full_image_path";
    public static final String THUMBNAIL_IMAGE_PATH_COLUMN = "thumbnail_image_path";
    public static final String CATEGORY_COLUMN = "category";
    public static final String TITLE_COLUMN = "title";
    public static final String BUS_COLUMN = "bus";
    public static final String AUTO_LOCATION_COLUMN = "auto_location";
    public static final String REVISED_LOCATION = "revised_location";
    public static final String CREATED_DATE_COLUMN = "created_at";
    public static final String ID_COLUMN = "_id";


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
                .append(ID_COLUMN).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(CATEGORY_COLUMN).append(" TEXT, ")
                .append(TITLE_COLUMN).append(" TEXT, ")
                .append(BUS_COLUMN).append(" TEXT, ")
                .append(AUTO_LOCATION_COLUMN).append(" TEXT, ")
                .append(REVISED_LOCATION).append(" TEXT, ")
                .append(FULL_IMAGE_PATH_COLUMN).append(" TEXT, ")
                .append(THUMBNAIL_IMAGE_PATH_COLUMN).append(" TEXT, ")
                .append(CREATED_DATE_COLUMN).append(" DATE")
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
        contentValues.put(CREATED_DATE_COLUMN, System.currentTimeMillis());
        return getWritableDatabase().insert(PATH_ITEMS_TABLE_NAME, null, contentValues);
    }

    public Cursor getQueryCursor() {
        StringBuilder querySql = new StringBuilder()
                .append("SELECT ")
                .append("*").append(" FROM ")
                .append(PATH_ITEMS_TABLE_NAME)
                .append(" ORDER BY ").append(CREATED_DATE_COLUMN).append(" ASC");
        return getReadableDatabase().rawQuery(querySql.toString(), new String[]{});
    }
}
