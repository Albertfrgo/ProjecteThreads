package com.example.projectethreads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String error = "";
    String dadesURL;

    ArrayList<String> liniesURL=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        Button botoEnviar=findViewById(R.id.botoEnviar);
        /*TextView vistaResultat=findViewById(R.id.resultatVista);*/
        ListView vistaResultat=findViewById(R.id.resultatVista);

        /*Per probar que la list View mostra linies*/
        liniesURL.add("Linia de mostra1");
        liniesURL.add("Linia de mostra2");
        liniesURL.add("mostra de Linia 3");
        liniesURL.add("La quart4 fil4");
        liniesURL.add("Linia de mostra5");
        liniesURL.add("Linia de mostra6");
        liniesURL.add("mostra de Linia 7");
        liniesURL.add("La vuitena fila");
        liniesURL.add("Linia de mostra9");
        liniesURL.add("Linia de mostra10");
        liniesURL.add("mostra de Linia 11");
        liniesURL.add("La dotzena fila");

        botoEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("INFO", "S'ha apretat el boto d'enviar");
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.getMainLooper());

                executor.execute(new Runnable() {
                    public void run() {
                        //handler.post(new Runnable() {
                            //public void run() {
                        dadesURL=getDataFromUrl("https://ieti.cat");

                        ArrayAdapter<String> adaptador=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, liniesURL);
                        vistaResultat.setAdapter(adaptador);
                        /*String linia="";
                        for(int i=0;i<dadesURL.length();i++){
                            if (String.valueOf(dadesURL.charAt(i)).equals("/"))
                            linia=linia+String.valueOf(dadesURL.charAt(i));
                            Log.i("INFO", String.valueOf(dadesURL.charAt(i)));
                        }
                        for(String f:liniesURL){
                            Log.i("INFO", f);
                        }*/
                                /*vistaResultat.setText(dadesURL);*/

                            //}
                        //});
                    }
                });
            }
        });
    }

    private String getDataFromUrl(String demoIdUrl) {
        String result = null;
        int resCode;
        InputStream in;
        try {
            URL url = new URL(demoIdUrl);
            URLConnection urlConn = url.openConnection();

            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConn;
            httpsConn.setAllowUserInteraction(false);
            httpsConn.setInstanceFollowRedirects(true);
            httpsConn.setRequestMethod("GET");
            httpsConn.connect();
            resCode = httpsConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpsConn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        in, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                in.close();
                result = sb.toString();
            } else {
                error += resCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}