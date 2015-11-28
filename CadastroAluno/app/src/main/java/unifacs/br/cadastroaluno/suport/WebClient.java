package unifacs.br.cadastroaluno.suport;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

/**
 * Created by jadson on 23/11/15.
 */
public class WebClient {

    private URI uri;
    public String url;

    public WebClient(String url){
        this.url = url;
    }

    public String post(String json){
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(json));
            post.setHeader("Content-type", "aplication/json");
            post.setHeader("Accept", "aplication/json");
            HttpClient cliente = new DefaultHttpClient();
            HttpResponse respota = cliente.execute(post);
            return EntityUtils.toString(respota.getEntity());
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }


}
