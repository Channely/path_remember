package com.li.learn.path.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.li.learn.path.utils.Constants;

public class PathItemDBOperator extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo05.db";
    private static final String PATH_ITEMS_TABLE_NAME = "path_items";
    private static final int DB_VERSION = 1;

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
                .append(Constants.ID_COLUMN).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(Constants.CATEGORY_COLUMN).append(" TEXT, ")
                .append(Constants.TITLE_COLUMN).append(" TEXT, ")
                .append(Constants.BUS_COLUMN).append(" TEXT, ")
                .append(Constants.AUTO_LOCATION_COLUMN).append(" TEXT, ")
                .append(Constants.REVISED_LOCATION_COLUMN).append(" TEXT, ")
                .append(Constants.FULL_IMAGE_PATH_COLUMN).append(" TEXT, ")
                .append(Constants.THUMBNAIL_IMAGE_PATH_COLUMN).append(" TEXT, ")
                .append(Constants.CREATED_DATE_COLUMN).append(" DATE")
                .append(");");
        db.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public long savePathItem(PathItem pathItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.CATEGORY_COLUMN, pathItem.getCategory());
        contentValues.put(Constants.TITLE_COLUMN, pathItem.getTitle());
        contentValues.put(Constants.BUS_COLUMN, pathItem.getBus());
        contentValues.put(Constants.AUTO_LOCATION_COLUMN, pathItem.getAutoLocation());
        contentValues.put(Constants.REVISED_LOCATION_COLUMN, pathItem.getRevisedLocation());
        contentValues.put(Constants.FULL_IMAGE_PATH_COLUMN, pathItem.getFullImagePath());
        contentValues.put(Constants.THUMBNAIL_IMAGE_PATH_COLUMN, pathItem.getThumbnailImagePath());
        contentValues.put(Constants.CREATED_DATE_COLUMN, System.currentTimeMillis());
        return getWritableDatabase().insert(PATH_ITEMS_TABLE_NAME, null, contentValues);
    }

    public Cursor getQueryCursor() {
        StringBuilder querySql = new StringBuilder()
                .append("SELECT ")
                .append("*").append(" FROM ")
                .append(PATH_ITEMS_TABLE_NAME)
                .append(" ORDER BY ").append(Constants.CREATED_DATE_COLUMN).append(" ASC");
        return getReadableDatabase().rawQuery(querySql.toString(), new String[]{});
    }
}
