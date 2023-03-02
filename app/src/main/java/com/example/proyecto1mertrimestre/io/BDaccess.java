package com.example.proyecto1mertrimestre.io;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.proyecto1mertrimestre.model.Duck;
import com.example.proyecto1mertrimestre.model.User;

import java.util.ArrayList;

public class BDaccess extends SQLiteOpenHelper {
    //Database name
    private static final String DB_NAME = "proyecto1trim";

    //Table name
    private static final String DB_TABLE_USER = "usuarios";
    private static final String DB_TABLE_DUCK = "ducks";

    //Database version must be >= 1
    private static final int DB_VERSION = 1;

    //user table Columns
    private static final String COLUMN_NOMBRE_USER = "nombre_user";
    private static final String COLUMN_CONTRASENIA = "contrasenia";

    //userDuck table Columns
    //we will use this column as a foreing key: COLUMN_NOMBRE_USER
    private static final String COLUMN_DUCK_USER = "code_duck";

    //Application Context
    private Context mContext;

    //COSTRUCTOR
    public BDaccess(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //borramos por si hubiera algun error
        String DropUserTable = "DROP TABLE IF EXISTS "+ DB_TABLE_USER;
        String DropDuckTable = "DROP TABLE IF EXISTS "+ DB_TABLE_DUCK;
        sqLiteDatabase.execSQL(DropUserTable);
        sqLiteDatabase.execSQL(DropDuckTable);

        //volvemos a crear bien hecha
        String CREATE_USER_TABLE = "CREATE TABLE " + DB_TABLE_USER + " ( "
                + COLUMN_NOMBRE_USER +" TEXT PRIMARY KEY, "
                + COLUMN_CONTRASENIA +" TEXT ) ";
        String CREATE_DUCK_TABLE = "CREATE TABLE " + DB_TABLE_DUCK + " ( "
                + COLUMN_NOMBRE_USER +" TEXT , "
                + COLUMN_DUCK_USER +" TEXT ,"
                + "PRIMARY KEY ("+COLUMN_NOMBRE_USER+" , "+COLUMN_DUCK_USER+")) ";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_DUCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //no hay actualizaciones de la base de datos en esta aplicacion, estamos en la primera version
    }

    //funcion para insertar un usuario
    public long insertUser(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        long res=-1;

        //es una especie de diccionario
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE_USER, name);
        values.put(COLUMN_CONTRASENIA, password);


        res = db.insert(DB_TABLE_USER,null,values);

        //Se cierra la conexión de la base de datos
        db.close();
        return res;
    }

    //funcion que comprieba si un usuario existe
    public boolean userExist(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean res=false;

        Cursor c = db.rawQuery("SELECT * FROM "+ DB_TABLE_USER + " WHERE "+ COLUMN_NOMBRE_USER+ " = '"+name+"'",null);

        if(c.moveToFirst()){
            res=true;
        }
        return res;
    }

    public String getPassword(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        String res="";

        //Un cursor es un tipo de dato que se mueve entre los registros devueltos por una consulta de una base de datos.
        Cursor c = db.rawQuery("SELECT "+COLUMN_CONTRASENIA+" FROM "+ DB_TABLE_USER + " WHERE "+ COLUMN_NOMBRE_USER+ " = '"+name+"'",null);

        if(c.moveToFirst()){
            res=c.getString(0);
        }
        return res;
    }

    public long insertUserDuck(User user, Duck duck){
        SQLiteDatabase db = this.getWritableDatabase();
        long res=-1;

        String nombre=user.getNombre();
        String duckCode=duck.getImagen();

        //es una especie de diccionario
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRE_USER, nombre);
        values.put(COLUMN_DUCK_USER, duckCode);


        res = db.insert(DB_TABLE_DUCK,null,values);

        //Se cierra la conexión de la base de datos
        db.close();
        return res;
    }

    public ArrayList<Duck> getAllDucks(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Duck> listaPatos=new ArrayList<Duck>();

        String[] cols = new String[]{ COLUMN_DUCK_USER };

        String selection = COLUMN_NOMBRE_USER+"=?";
        String[] args = new String[]{user.getNombre()};

        Cursor c = db.query(DB_TABLE_DUCK,cols,selection,args,null,null,null);

        if(c.moveToFirst()){
            do{
                Duck duck = new Duck(c.getString(0));

                listaPatos.add(duck);
            }while(c.moveToNext());
        }

        return listaPatos;
    }

    public long deleteDuck(Duck duck){
        SQLiteDatabase db = this.getWritableDatabase();
        long res=-1;

        String whereClause = COLUMN_DUCK_USER+"=?";
        String [] whereArgs = new String[]{duck.getImagen()};

        res = db.delete(DB_TABLE_DUCK,whereClause,whereArgs);

        //Se cierra la conexión de la base de datos
        db.close();
        return res;
    }
}
