package unifacs.br.cadastroaluno.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import unifacs.br.cadastroaluno.model.Aluno;

/**
 * Created by jadson on 31/08/15.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    private static final String DATABASE = "escola";
    private static final int VERSAO = 1;
    private static  final String TABELA = "aluno";

    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =    "CREATE TABLE " + TABELA +
                        " ( " +
                            "id          INTEGER NOT NULL PRIMARY KEY, " +
                            "nome        TEXT UNIQUE NOT NULL, " +
                            "site        TEXT, " +
                            "endereco    TEXT, " +
                            "telefone    TEXT," +
                            "nota        REAL," +
                            "caminhoFoto TEXT " +
                        ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void cadastrarAluno(Aluno aluno){
        ContentValues valores = new ContentValues();
        valores.put("nome", aluno.getNome());
        valores.put("telefone", aluno.getTelefone());
        valores.put("site", aluno.getSite());
        valores.put("endereco", aluno.getEndereco());
        valores.put("nota", aluno.getNota());

        valores.put("caminhoFoto", aluno.getCaminhoFoto());

        getWritableDatabase().insert(TABELA,null, valores);

    }

    public void alterarAluno(Aluno aluno){

        ContentValues valores = new ContentValues();
        valores.put("nome", aluno.getNome());
        valores.put("telefone", aluno.getTelefone());
        valores.put("site", aluno.getSite());
        valores.put("endereco", aluno.getEndereco());
        valores.put("nota", aluno.getNota());

        valores.put("caminhoFoto", aluno.getCaminhoFoto());

        String args[] = {""+aluno.getId()};

        getWritableDatabase().update(TABELA, valores, "id=?", args);
        Log.d("alterar", "passou");
    }

    public List<Aluno> getAlunos(){

        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA + ";";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        while (cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setNota(cursor.getFloat(cursor.getColumnIndex("nota")));

            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }

        return alunos;
    }

    public void deletar(Aluno aluno){
        String [] args = {""+aluno.getId()};
        getWritableDatabase().delete(TABELA, "id=?",args);
    }

    public boolean ehAluno(String telefone){

        boolean retorno = false;
        String sql = "SELECT * FROM " + TABELA + " WHERE telefone = ? ";

        String arg []  = {telefone};

        Cursor cursor = getReadableDatabase().rawQuery(sql, arg);



        if(cursor.moveToNext()){
            retorno = true;
        }

        cursor.close();

        return retorno;
    }

}
