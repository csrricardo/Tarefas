package com.example.alunos.tarefas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoOpenHelper extends SQLiteOpenHelper{

    public BancoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tarefas (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+ "descricao TEXT)";
        db.execSQL(sql);

        sql = "INSERT INTO tarefas (descricao) VALUES ('Estudar Android')";
        db.execSQL(sql);

        sql = "INSERT INTO tarefas (descricao) VALUES ('Pagar contas')";
        db.execSQL(sql);

        sql = "INSERT INTO tarefas (descricao) VALUES ('Lavar o carro')";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
