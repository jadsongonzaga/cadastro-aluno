package unifacs.br.cadastroaluno.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import unifacs.br.cadastroaluno.converter.AlunoConverter;
import unifacs.br.cadastroaluno.model.Aluno;
import unifacs.br.cadastroaluno.model.dao.AlunoDAO;
import unifacs.br.cadastroaluno.suport.WebClient;

/**
 * Created by jadson on 23/11/15.
 */
public class EnviarContatosTaks extends AsyncTask<Object, Object, String> {


    Context ctx;

    ProgressDialog progressDialog;

    public  EnviarContatosTaks(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx, "Aguarde ....", "Eviando dados");
    }

    @Override
    protected String doInBackground(Object... params) {
        AlunoDAO dao = new AlunoDAO(ctx);

        List<Aluno> alunos = dao.getAlunos();
        String json = new AlunoConverter().toJSON(alunos);

        String media = new WebClient("http://...").post(json);

        return media;
    }

    @Override
    protected void onPostExecute(String media) {
        progressDialog.dismiss();
        Toast.makeText(ctx, media, Toast.LENGTH_LONG).show();
    }
    //sincronizar
    //new EnviarContatosTaks(this).execute();
    //new EnviarContatosTaks(this).execute();
}
