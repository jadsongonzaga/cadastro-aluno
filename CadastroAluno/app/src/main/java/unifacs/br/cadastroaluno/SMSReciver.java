package unifacs.br.cadastroaluno;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

import unifacs.br.cadastroaluno.model.dao.AlunoDAO;

/**
 * Classe que escutará as mensagens que chegará
 * Created by jadson on 23/11/15.
 */
public class SMSReciver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Object [] mensagens = (Objects[]) intent.getExtras().get("pdus");
        byte[] mensagem = (byte[]) mensagens[0];//Pega a última mensagem que chegou...

        SmsMessage sms = SmsMessage.createFromPdu(mensagem);
        String telefone = sms.getOriginatingAddress();

        //Verificando de quem é a mensagem
        boolean smsEhDeAluno = new AlunoDAO(context).ehAluno(telefone);

        Log.d("sms", "Telefone" + telefone);

        if(smsEhDeAluno){
            MediaPlayer musica = MediaPlayer.create(context, R.raw.sms);

        }


    }


}
