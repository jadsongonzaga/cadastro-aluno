package unifacs.br.cadastroaluno.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import unifacs.br.cadastroaluno.model.Aluno;

/**
 * Created by jadson on 23/11/15.
 */
public class AlunoConverter {

    public String toJSON(List<Aluno> alunos){
        JSONStringer js = new JSONStringer();

        try{

            js.object().key("list").array();
            js.object().key("aluno").array();

            for(Aluno aluno:alunos){
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }

            js.endArray().endObject();
            js.endArray().endObject();

        }catch (JSONException e){
            e.printStackTrace();
        }
        return js.toString();
    }

}
