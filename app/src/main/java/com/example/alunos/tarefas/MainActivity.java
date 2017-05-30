package com.example.alunos.tarefas;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase banco;
    private SimpleCursorAdapter adapter;
    private long idSendoAlterado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BancoOpenHelper bancoOpenHelper = new BancoOpenHelper(this, "tarefas.db", null, 1);

        banco = bancoOpenHelper.getWritableDatabase();
        banco.query("tarefas", null, null, null, null, null, "descricao");
        /*
        SELECT * FROM tarefas ORDER BY descricao;
         */
        Cursor cursor = banco.query("tarefas", null, null, null, null, null, "descricao");
        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor, new String[]{"_id","descricao"},
                new int[] {android.R.id.text1, android.R.id.text2}, 0);

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //DELETE FROM tarefas WHERE _id = id;
                banco.delete("tarefas", "_id = " + id, null);
                atualizarListagem();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String tarefa = adapter.getCursor().getString(1);
                EditText editText = (EditText)findViewById(R.id.editText);
                editText.setText(tarefa);
                Button button = (Button)findViewById(R.id.button);
                button.setText("Alterar");
                idSendoAlterado = id;
                return true;
            }
        });
    }
    public void adicionar(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        String tarefa = editText.getText().toString();
        ContentValues valores = new ContentValues();
        valores.put("descricao", tarefa);

        if (idSendoAlterado > 0) {
            banco.update("tarefas", valores, "_id = " + idSendoAlterado, null);
            idSendoAlterado = -1;
            Button button = (Button)findViewById(R.id.button);
            button.setText("Adicionar");
        } else {

            // INSERT INTO tarefas (descricao) VALUES ("Tarefas no EditText");
            banco.insert("tarefas", null, valores);
        }
        atualizarListagem();
        editText.setText("");

    }
    private void atualizarListagem(){
        Cursor cursor = banco.query("tarefas", null, null, null, null, null, "descricao");
        adapter.changeCursor(cursor);
    }
}
