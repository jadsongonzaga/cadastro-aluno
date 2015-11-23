package unifacs.br.cadastroaluno;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;

import unifacs.br.cadastroaluno.model.Aluno;
import unifacs.br.cadastroaluno.model.dao.AlunoDAO;

/**
 * Created by jadson on 24/08/15.
 */
public class CadastroAlunoActivity extends Activity {

    Button btnCadastrar;
    FormularioHelper helper;
    Aluno aluno;
    AlunoDAO alunoDAO;
    String caminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alunoDAO = new AlunoDAO(this);

        setContentView(R.layout.activity_cadastrar_aluno);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        helper = new FormularioHelper(this);

        aluno = (Aluno) getIntent().getSerializableExtra("AlunoAltera");
        if(aluno != null){
            btnCadastrar.setText("Alterar");
            helper.preencherFormulario(aluno);
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno novoAluno = helper.carregaAluno();
                if(aluno != null){
                    novoAluno.setId(aluno.getId());
                    alunoDAO.alterarAluno(novoAluno);
                }else {
                    alunoDAO.cadastrarAluno(novoAluno);
                }
                finish();
                //Toast.makeText(CadastroAlunoActivity.this, aluno.toString(), Toast.LENGTH_LONG).show();
            }
        });

        //trabalhando com foto

        ImageView foto = helper.getFoto();


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminho = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".png";
                File arquivo = new File(caminho);
                Uri local = Uri.fromFile(arquivo);
                //O retorno da camera deve ser salvo neste local.
                intent.putExtra(MediaStore.EXTRA_OUTPUT, local);
                //precisamos pegaar o retorno da intent
                startActivityForResult(intent, 123);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 123){
            if(resultCode == Activity.RESULT_OK){
                helper.carregaImagem(caminho);
            }
        }else{
            caminho = null;
        }

    }
}
