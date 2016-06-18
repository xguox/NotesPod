package me.xguox.notespod;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by xguox on 6/18/16.
 */
public class DbAdapter {
    private static final String DATABASE_NAME = "notespod.db";
    private static final int DATABASE_VERSION = 1;

    public static final String NOTE_TABLE = "notes";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String CATEGORY = "category";
    public static final String CREATED_AT = "created_at";

    private String[] allColumns = { ID, TITLE, MESSAGE, CATEGORY, CREATED_AT };
    public static final String DATABASE_CREATE = "CREATE TABLE " + NOTE_TABLE + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT NOT NULL, "
            + MESSAGE + " TEXT NOT NULL, "
            + CATEGORY + " INTEGER NOT NULL, "
            + CREATED_AT + ");";

    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private DbHelper mDbHelper;

    public DbAdapter(Context context) {
        mContext = context;
    }

    public DbAdapter open() throws android.database.SQLException {
        mDbHelper = new DbHelper(mContext);
        mSQLiteDatabase = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Note createNote(String title, String message, Note.Category category) {
        ContentValues values = new ContentValues();

        values.put(TITLE, title);
        values.put(MESSAGE, message);
        values.put(CATEGORY, category.name());
        values.put(CREATED_AT, Calendar.getInstance().getTimeInMillis() + "");

        long insertId = mSQLiteDatabase.insert(NOTE_TABLE, null, values);

        Cursor cursor = mSQLiteDatabase.query(NOTE_TABLE, allColumns, ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Note newNote = cursorToNote(cursor);
        cursor.close();

        return newNote;
    }

    public long updateNote(long idToUpdate, String newTitle, String newMessage, Note.Category newCategory) {
        ContentValues values = new ContentValues();

        values.put(TITLE, newTitle);
        values.put(MESSAGE, newMessage);
        values.put(CATEGORY, newCategory.name());

        return mSQLiteDatabase.update(NOTE_TABLE, values, ID + " = " + idToUpdate, null);
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();

        Cursor cursor = mSQLiteDatabase.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Note note = cursorToNote(cursor);
            notes.add(note);
        }
        cursor.close();

        return notes;
    }

    public Note cursorToNote(Cursor cursor) {
        Note newNote = new Note(cursor.getString(1), cursor.getString(2), Note.Category.valueOf(cursor.getString(3)), cursor.getLong(0), cursor.getLong(4));
        return newNote;
    }

    private static class DbHelper extends SQLiteOpenHelper {

        DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DbHelper.class.getName(), "Upgrading database from version" + oldVersion + " to "
            + newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS " + "NOTE_TABLE");
            onCreate(db);
        }
    }
}
