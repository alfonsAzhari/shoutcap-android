package co.shoutnet.shoutcap.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelAdapterCart;
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
    private static final String KEY_SIZE = "size";
    private static final String KEY_FONT = "font";
    private static final String KEY_COLOR = "color";
    private static final String KEY_FONT_SIZE = "fontsize";
    private static final String KEY_LINE = "line";
    private static final String KEY_PRICE = "price";
    private static final String KEY_URI = "baseImage";
    private static final String KEY_STATUS = "status";

    private static final String[] COLUMNS = {KEY_ID, KEY_TEXT, KEY_MODEL, KEY_SIZE, KEY_FONT, KEY_COLOR, KEY_FONT_SIZE, KEY_LINE, KEY_PRICE, KEY_URI, KEY_STATUS};

    public DBCapsHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TB_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "text TEXT," +
                "model INTEGER," +
                "size INTEGER," +
                "font TEXT," +
                "color INTEGER," +
                "fontsize INTEGER," +
                "line INTEGER," +
                "price INTEGER," +
                "baseImage TEXT," +
                "status TEXT)";
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
        values.put(KEY_ID, capsModel.getId());
        values.put(KEY_TEXT, capsModel.getText());
        values.put(KEY_MODEL, capsModel.getModel());
        values.put(KEY_SIZE, capsModel.getSize());
        values.put(KEY_FONT, capsModel.getColor());
        values.put(KEY_COLOR, capsModel.getColor());
        values.put(KEY_FONT_SIZE, capsModel.getFontsize());
        values.put(KEY_LINE, capsModel.getLine());
        values.put(KEY_PRICE, capsModel.getPrice());
        values.put(KEY_URI, capsModel.getBaseImage());
        Log.i("image", capsModel.getBaseImage());
        values.put(KEY_STATUS, capsModel.getStatus());

        db.insert(TB_NAME, null, values);
        db.close();
    }

//    public List<CapsModel> getAllData() {
//        List<CapsModel> caps = new ArrayList<>();
//
//        String query = "SELECT * FROM " + TB_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        CapsModel capsModel;
//        if (cursor.moveToFirst()) {
//            do {
//                capsModel = new CapsModel();
//                capsModel.setId(cursor.getString(0));
//                capsModel.setText(cursor.getString(1));
//                capsModel.setModel(Integer.parseInt(cursor.getString(2)));
//                capsModel.setType(Integer.parseInt(cursor.getString(3)));
//                capsModel.setSize(Integer.parseInt(cursor.getString(4)));
//                capsModel.setFont(cursor.getString(5));
//                capsModel.setColor(cursor.getString(6));
//                capsModel.setPrice(Integer.parseInt(cursor.getString(7)));
//                capsModel.setBaseImage(cursor.getString(8));
//
//                caps.add(capsModel);
//            } while (cursor.moveToNext());
//        }
//        return caps;
//    }

    public void updateUri(int id, String uri) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("baseImage", uri);

        db.update(TB_NAME, values, "id = " + id, null);
        db.close();
    }

    public int getLatestId() {
        String query = "SELECT id FROM " + TB_NAME + " ORDER BY id DESC LIMIT 1";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor.getInt(0);
    }

    public ArrayList<ModelAdapterRack> getRackData() {
        ArrayList<ModelAdapterRack> data = new ArrayList<>();

        String query = "SELECT id,baseImage FROM " + TB_NAME + " WHERE status = 'rack' OR status = 'both'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ModelAdapterRack rack;
        if (cursor.moveToFirst()) {
            do {
                rack = new ModelAdapterRack();
                rack.setId(cursor.getString(0));
                rack.setImgRack(cursor.getString(1));

                data.add(rack);
            } while (cursor.moveToNext());
        }
        db.close();
        return data;
    }

    public List<ModelAdapterCart> getCartData() {
        List<ModelAdapterCart> data = new ArrayList<>();
        ModelAdapterCart modelAdapterCart;
        String query = "SELECT id, baseImage, text, price FROM " + TB_NAME + " WHERE status = 'cart' OR status = 'both'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Log.i("image", cursor.getString(0));
                modelAdapterCart = new ModelAdapterCart();
                modelAdapterCart.setId(cursor.getInt(0));
                modelAdapterCart.setImage(cursor.getString(1));
                modelAdapterCart.setName(cursor.getString(2));
                modelAdapterCart.setPrice(cursor.getInt(3));
                modelAdapterCart.setSubTotal(cursor.getInt(3));
                modelAdapterCart.setQty(1);
                data.add(modelAdapterCart);
            } while (cursor.moveToNext());
        }
        db.close();
        return data;
    }

    public List<String> getCart() {
        List<String> data = new ArrayList<>();
        String query = "SELECT id FROM " + TB_NAME + " WHERE status = 'cart' OR status = 'both'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return data;
    }

    public void updateStatus(String status, String id) {
        SQLiteDatabase db=getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_STATUS,status);

        db.update(TB_NAME, values, "id = " + id, null);
        db.close();
    }

    public void deleteCartData(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TB_NAME, "id = " + id, null);
        db.close();
    }
}
