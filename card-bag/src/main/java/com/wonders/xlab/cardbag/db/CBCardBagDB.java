package com.wonders.xlab.cardbag.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBContract.CardEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by hua on 16/9/2.
 */

public class CBCardBagDB {
    private CBDbHelper mCBDbHelper;

    private static CBCardBagDB instance = null;

    private CBCardBagDB() {
    }

    private CBCardBagDB(Context context) {
        mCBDbHelper = new CBDbHelper(context);
    }

    public static CBCardBagDB getInstance(Context context) {
        synchronized (CBCardBagDB.class) {
            if (instance == null) {
                instance = new CBCardBagDB(context);
            }
        }
        return instance;
    }

    /**
     * @param entity
     * @return the row ID of the newly inserted row OR <code>-1</code> when insert failed
     */
    public long insertOrReplace(CardEntity entity) {
        SQLiteDatabase db = mCBDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CardEntry._ID, entity.getId());
        values.put(CardEntry.COLUMN_NAME_NAME, entity.getCardName());
        values.put(CardEntry.COLUMN_NAME_BARCODE, entity.getBarCode());
        values.put(CardEntry.COLUMN_NAME_IMG_URL, entity.getImgUrl());
        values.put(CardEntry.COLUMN_NAME_IMG_FILE_PATH, entity.getImgFilePath());
        values.put(CardEntry.COLUMN_NAME_FRONT_IMG_FILE_PATH, entity.getFrontImgFilePath());
        values.put(CardEntry.COLUMN_NAME_FRONT_IMG_URL, entity.getFrontImgUrl());
        values.put(CardEntry.COLUMN_NAME_BACK_IMG_FILE_PATH, entity.getBackImgFilePath());
        values.put(CardEntry.COLUMN_NAME_BACK_IMG_URL, entity.getBackImgUrl());
        values.put(CardEntry.COLUMN_NAME_CREATE_DATE, entity.getCreateDate());
        long l = db.insertWithOnConflict(CardEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return l;
    }

    @Nullable
    public List<CardEntity> queryAllOrderByCreateDateDesc() throws IllegalArgumentException {
        return queryByIds(null);
    }

    @Nullable
    public List<CardEntity> queryByIds(HashSet<Long> ids) {
        String[] projection = {
                CardEntry._ID,
                CardEntry.COLUMN_NAME_NAME,
                CardEntry.COLUMN_NAME_BARCODE,
                CardEntry.COLUMN_NAME_IMG_URL,
                CardEntry.COLUMN_NAME_IMG_FILE_PATH,
                CardEntry.COLUMN_NAME_FRONT_IMG_FILE_PATH,
                CardEntry.COLUMN_NAME_FRONT_IMG_URL,
                CardEntry.COLUMN_NAME_BACK_IMG_FILE_PATH,
                CardEntry.COLUMN_NAME_BACK_IMG_URL,
                CardEntry.COLUMN_NAME_CREATE_DATE
        };
        String sortOrder =
                CardEntry.COLUMN_NAME_CREATE_DATE + " DESC";

        SQLiteDatabase db = mCBDbHelper.getReadableDatabase();

        String selection = null;
        if (ids != null && ids.size() > 0) {
            selection = assembleIdsToSelection(ids);
        }
        Cursor cursor = db.query(CardEntry.TABLE_NAME, projection, selection, null, null, null, sortOrder);
        List<CardEntity> cardEntities = null;
        try {
            if (cursor.moveToFirst()) {
                cardEntities = new ArrayList<>();
                do {
                    CardEntity entity = new CardEntity();
                    entity.setId(cursor.getLong(cursor.getColumnIndexOrThrow(CardEntry._ID)));
                    entity.setCardName(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_NAME)));
                    entity.setBarCode(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_BARCODE)));
                    entity.setImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_IMG_URL)));
                    entity.setImgFilePath(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_IMG_FILE_PATH)));
                    entity.setBackImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_BACK_IMG_URL)));
                    entity.setBackImgFilePath(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_BACK_IMG_FILE_PATH)));
                    entity.setFrontImgFilePath(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_FRONT_IMG_FILE_PATH)));
                    entity.setFrontImgUrl(cursor.getString(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_FRONT_IMG_URL)));
                    entity.setCreateDate(cursor.getLong(cursor.getColumnIndexOrThrow(CardEntry.COLUMN_NAME_CREATE_DATE)));
                    cardEntities.add(entity);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }
        return cardEntities;
    }

    public int deleteByIds(HashSet<Long> ids) {
        SQLiteDatabase db = mCBDbHelper.getWritableDatabase();

        String selection = assembleIdsToSelection(ids);
        int counts = db.delete(CardEntry.TABLE_NAME, selection, null);
        db.close();
        return counts;
    }

    @NonNull
    private String assembleIdsToSelection(HashSet<Long> ids) {
        StringBuilder inQuery = new StringBuilder();
        inQuery.append("(");
        boolean first = true;
        for (Long item : ids) {
            if (first) {
                first = false;
                inQuery.append("'").append(item).append("'");
            } else {
                inQuery.append(", '").append(item).append("'");
            }
        }
        inQuery.append(")");
        return CardEntry._ID + " in " + inQuery.toString();
    }
}
