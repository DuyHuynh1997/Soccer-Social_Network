package tdc.edu.com.example.soccer_social_network.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "databasesql1";
    private static int DB_VERSION = 1;



    public SQLiteHelper(Context context) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL( sql );
    }

    public void insertData(String tendoi, String diachi, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO TEAM VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString( 1, tendoi);
        statement.bindString( 2, diachi);
        statement.bindBlob( 3, image);

        statement.executeInsert();
    }

    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery( sql,null );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
