package co.shoutnet.shoutcap.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.adapter.RackAdapter;
import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelAdapterRack;

/**
 * Created by mikqi on 10/29/15.
 */
public class DBCapsHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "shoutcap";
    private static final String TB_NAME = "caps";
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_MODEL = "model";
    private static final String KEY_TYPE = "type";
    private static final String KEY_SIZE = "size";
    private static final String KEY_FONT = "font";
    private static final String KEY_COLOR = "color";
    private static final String KEY_PRICE = "price";
    private static final String KEY_URI = "baseImage";

    private static final String[] COLUMNS = {KEY_ID, KEY_TEXT, KEY_MODEL, KEY_TYPE, KEY_SIZE, KEY_FONT, KEY_COLOR, KEY_PRICE, KEY_URI};

    public DBCapsHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TB_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "text TEXT," +
                "model INTEGER," +
                "type INTEGER," +
                "size INTEGER," +
                "font TEXT," +
                "color TEXT," +
                "price INTEGER," +
                "baseImage TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TB_NAME);
        this.onCreate(db);
    }

    public void addCap(CapsModel capsModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, capsModel.getText());
        values.put(KEY_MODEL, capsModel.getModel());
        values.put(KEY_TYPE, capsModel.getType());
        values.put(KEY_SIZE, capsModel.getSize());
        values.put(KEY_FONT, capsModel.getColor());
        values.put(KEY_COLOR, capsModel.getColor());
        values.put(KEY_PRICE, capsModel.getPrice());
        values.put(KEY_URI, capsModel.getBaseImage());

        db.insert(TB_NAME, null, values);
        db.close();
    }

    public List<CapsModel> getAllData() {
        List<CapsModel> caps = new ArrayList<>();

        String query = "SELECT * FROM " + TB_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        CapsModel capsModel;
        if (cursor.moveToFirst()) {
            do {
                capsModel = new CapsModel();
                capsModel.setId(cursor.getString(0));
                capsModel.setText(cursor.getString(1));
                capsModel.setModel(Integer.parseInt(cursor.getString(2)));
                capsModel.setType(Integer.parseInt(cursor.getString(3)));
                capsModel.setSize(Integer.parseInt(cursor.getString(4)));
                capsModel.setFont(cursor.getString(5));
                capsModel.setColor(cursor.getString(6));
                capsModel.setPrice(Integer.parseInt(cursor.getString(7)));
                capsModel.setBaseImage(cursor.getString(8));

                caps.add(capsModel);
            } while (cursor.moveToNext());
        }
        return caps;
    }

    public void updateUri(int id, String uri){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("baseImage",uri);

        db.update(TB_NAME,values,"id = "+id,null);
        db.close();
    }

    public int getLatestId(){
        String query="SELECT id FROM "+TB_NAME+" ORDER BY id DESC LIMIT 1";

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public List<ModelAdapterRack> getRackData(){
        List<ModelAdapterRack> data = new ArrayList<>();

        String query = "SELECT baseImage FROM "+TB_NAME;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        ModelAdapterRack rack;
        if (cursor.moveToFirst()){
            do {
                rack=new ModelAdapterRack();
                rack.setImgRack(Uri.parse(cursor.getString(0)));

                data.add(rack);
            }while (cursor.moveToNext());
        }
        db.close();
        return data;
    }
}
