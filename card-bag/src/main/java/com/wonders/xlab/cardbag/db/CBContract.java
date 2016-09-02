package com.wonders.xlab.cardbag.db;

import android.provider.BaseColumns;

/**
 * Created by hua on 16/9/2.
 */

public final class CBContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static class CardEntry implements BaseColumns{
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + CardEntry.TABLE_NAME + " (" +
                        CardEntry._ID + " TEXT PRIMARY KEY," +
                        CardEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_IMG_URL + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_IMG_FILE_PATH + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_FRONT_IMG_URL + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_FRONT_IMG_FILE_PATH + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_BACK_IMG_FILE_PATH + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_BACK_IMG_URL + TEXT_TYPE + COMMA_SEP +
                        CardEntry.COLUMN_NAME_CREATE_DATE + TEXT_TYPE+ " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + CardEntry.TABLE_NAME;

        public static final String TABLE_NAME = "card";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_IMG_URL = "imgUrl";
        public static final String COLUMN_NAME_IMG_FILE_PATH = "imgFilePath";
        public static final String COLUMN_NAME_FRONT_IMG_URL = "frontImgUrl";
        public static final String COLUMN_NAME_FRONT_IMG_FILE_PATH = "frontImgFilePath";
        public static final String COLUMN_NAME_BACK_IMG_FILE_PATH = "backImgFilePath";
        public static final String COLUMN_NAME_BACK_IMG_URL = "backImgUrl";
        public static final String COLUMN_NAME_CREATE_DATE = "createDate";
    }
}
