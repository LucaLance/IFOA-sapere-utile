package gruppo3.ifoasapereutile;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CalendarioActivity extends ActionBarActivity {

    ProgressBar bar;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        bar = (ProgressBar) findViewById(R.id.progressBar);

        intent = getIntent();
        String idcal = intent.getStringExtra("Calendario");
        String docente = intent.getStringExtra("Docente");

        WebView webview = new WebView(this);
        setContentView(webview);

        webview.getSettings().setJavaScriptEnabled(true);
        if (docente != null) {
            webview.loadUrl("http://calendarioifoa.byethost10.com/Calendario/www/index.html?idcal=" + idcal + "&docente=" + docente);
        }else {
            webview.loadUrl("http://calendarioifoa.byethost10.com/Calendario/www/index.html?idcal=" + idcal);
        }

    /*    // controllo se la rete è disponibile
        if (isNetworkAvailable() == true) {
            CalendarioReaderTask task = new CalendarioReaderTask();
            task.execute();
        } else {
            Toast.makeText(CalendarioActivity.this, "Rete non disponibile", Toast.LENGTH_SHORT).show();
        }*/

    }

    private class CalendarioReaderTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            String url = "https://www.googleapis.com/calendar/v3/calendars/fll2540vd8t1f30dseh2rq1ouk%40group.calendar.google.com/events?singleEvents=true&orderBy=startTime&key=AIzaSyC4k8WMylaShebC0Jw5XJsZ-NzJRmJQ-x4";
            // leggo il contenuto del file remoto e lo restituisco
            // diventerà il parametro della onPostExecute
            String response = readRemoteFile(url);
            return response;

        }

        @Override
        protected void onPostExecute(String result) {

            bar.setVisibility(View.INVISIBLE);

            // trasformo la stringa in oggetto json
            // per poter accedere facilmente ai campi
            try {
                JSONObject json = new JSONObject(result);
                JSONArray arrayEventi = json.getJSONArray("items");

    /*            String city = json.getString("name");
                double temp = json.getJSONObject("main").getDouble("temp");
                String description = json.getJSONArray("weather")
                        .getJSONObject(0).getString("description");*/

                String a = "ihocd";

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // metodo privato che effettua la connessione http
        // legge il file e restituisce una stringa
        // richiede il permesso: INTERNET

        private String readRemoteFile(String url) {

            StringBuilder builder = new StringBuilder();

            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            try {

                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();

                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } else {
                    Log.e("Network", "Failed to download file");
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return builder.toString();
        }

    }

    // richiede il permesso: ACCESS_NETWORK_STATE
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clandario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
