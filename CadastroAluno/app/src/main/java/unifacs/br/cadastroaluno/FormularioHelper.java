package unifacs.br.cadastroaluno;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import unifacs.br.cadastroaluno.model.Aluno;

/**
 * Created by jadson on 24/08/15.
 */
public class FormularioHelper {

    private Aluno aluno;
    private EditText edtNome;
    private EditText edtSite;
    private EditText edtEndereco;
    private EditText edtTelefone;
    private RatingBar rbNota;
    ImageView foto;

    public FormularioHelper(Activity activity) {
        aluno = new Aluno();
        edtNome = (EditText) activity.findViewById(R.id.edtNome);
        edtSite = (EditText) activity.findViewById(R.id.edtSite);
        edtEndereco = (EditText) activity.findViewById(R.id.edtEndereco);
        edtTelefone = (EditText) activity.findViewById(R.id.edtTelefone);
        rbNota = (RatingBar) activity.findViewById(R.id.rbNota);
        foto = (ImageView) activity.findViewById(R.id.foto);
    }

    public Aluno carregaAluno() {

        aluno.setNome(edtNome.getText().toString());
        aluno.setEndereco(edtEndereco.getText().toString());
        aluno.setSite(edtSite.getText().toString());
        aluno.setTelefone(edtTelefone.getText().toString());
        aluno.setNota(rbNota.getRating());

        try {
            aluno.setCaminhoFoto(foto.getTag().toString());
        }catch (Exception e){
            Log.d("log", e.getMessage());
        }
        return aluno;
    }


    public void preencherFormulario(Aluno aluno){
        edtNome.setText(aluno.getNome());
        edtSite.setText(aluno.getSite());
        edtEndereco.setText(aluno.getEndereco());
        edtTelefone.setText(aluno.getTelefone());
        rbNota.setRating(aluno.getNota());


        if(aluno.getCaminhoFoto()!=null) {
            carregaImagem(aluno.getCaminhoFoto());
            Log.d("log", aluno.getCaminhoFoto());
        }




    }

    public ImageView getFoto(){
        return foto;
    }

    public void carregaImagem(String caminho){

        aluno.setCaminhoFoto(caminho);
        //carrega na inteface
        Bitmap imagem  = BitmapFactory.decodeFile(caminho);
        //reduz imagem e add filtro
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, 100, 100, true);
        foto.setImageBitmap(imagemReduzida);
    }
}
