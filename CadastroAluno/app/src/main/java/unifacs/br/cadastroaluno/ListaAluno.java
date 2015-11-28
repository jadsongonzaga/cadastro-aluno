package unifacs.br.cadastroaluno;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import unifacs.br.cadastroaluno.model.Aluno;
import unifacs.br.cadastroaluno.model.dao.AlunoDAO;
import unifacs.br.cadastroaluno.task.EnviarContatosTaks;

public class ListaAluno extends AppCompatActivity {

    private ListView lista;
    private List<Aluno> alunos;
    AlunoDAO alunoDAO;
    Aluno aluno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);
        alunoDAO = new AlunoDAO(this);


        lista = (ListView) findViewById(R.id.lista);
        registerForContextMenu(lista);

        //String [] alunos = {"Ricardo", "Marcos", "Juvenal", "Paulo", "Maria", "Carlos"};
        /*alunos = alunoDAO.getAlunos();
        lista = (ListView) findViewById(R.id.lista);
        ArrayAdapter <Aluno> adapte = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        lista.setAdapter(adapte);*/


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //obter do adaper e a posição para obter o item selecionada
                aluno = (Aluno) parent.getItemAtPosition(position);

                return false;
            }
        });

        //Registrar o menu de contexto a lista
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Aluno aluno = (Aluno) adapter.getItemAtPosition(position);
                Intent formularioAlterar = new Intent(ListaAluno.this, CadastroAlunoActivity.class);
                formularioAlterar.putExtra("AlunoAltera", aluno);
                startActivity(formularioAlterar);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();

    }

    public void carregaLista(){
        alunoDAO = new AlunoDAO(this);
        //String [] alunos = {"Ricardo", "Marcos", "Juvenal", "Paulo", "Maria", "Carlos"};
        alunos = alunoDAO.getAlunos();
        lista = (ListView) findViewById(R.id.lista);
        AlunoAdapter adapte = new AlunoAdapter(alunos, this);
        //final ArrayAdapter <Aluno> adapte = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);
        lista.setAdapter(adapte);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_aluno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menu_novo:
                Intent intent = new Intent(this, CadastroAlunoActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_telefone:
                Intent ligar = new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:9999999999");
                ligar.setData(uri);
                startActivity(ligar);
                break;
            case R.id.menu_sincronizar:
                new EnviarContatosTaks(this).execute();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    /**
     * Criar o menu de contexto na activite
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem excluir = menu.add("Excluir");
        MenuItem ligar = menu.add("Ligar");
        MenuItem verSite = menu.add("Ver site");

        super.onCreateContextMenu(menu, v, menuInfo);

        excluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO alunoDAO = new AlunoDAO(ListaAluno.this);
                alunoDAO.deletar(aluno);
                alunoDAO.close();
                carregaLista();
                return false;
            }
        });

        //ligar - abrir o app para ligar
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //itent exlicita
                Intent fazerChamada = new Intent(Intent.ACTION_CALL);
                Uri uriNumero = Uri.parse("tel:"+aluno.getTelefone().trim());
                fazerChamada.setData(uriNumero);
                startActivity(fazerChamada);
                return false;
            }
        });

        verSite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent abrirSite = new Intent(Intent.ACTION_VIEW);
                Uri uriSite = Uri.parse("http://" + aluno.getSite());

                abrirSite.setData(uriSite);
                startActivity(abrirSite);

                return false;
            }
        });

    }


}
