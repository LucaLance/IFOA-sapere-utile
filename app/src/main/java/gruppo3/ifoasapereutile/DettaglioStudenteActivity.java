package gruppo3.ifoasapereutile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DettaglioStudenteActivity extends ActionBarActivity {

    ParseUser user;

    ProgressBar progressBar;

    TextView txtNomeStudente, txtCognomeStudente, txtEmailStudente;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_studente);

        txtNomeStudente = (TextView) findViewById(R.id.txtNomeStudente);
        txtCognomeStudente = (TextView) findViewById(R.id.txtCognomeStudente);
        txtEmailStudente = (TextView) findViewById(R.id.txtEmailStudente);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        intent = getIntent();

        DettaglioStudenteTask task = new DettaglioStudenteTask();
        task.execute();

    }

    private class DettaglioStudenteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
  //          progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            ParseQuery<ParseUser> queryUtente = ParseQuery.getQuery("_User");
            queryUtente.whereEqualTo("objectId", intent.getStringExtra("idUser"));
            try {
                List<ParseUser> userList = queryUtente.find();
                if (userList.size()>0 && userList!=null){
                    user = userList.get(0);
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
     //       progressBar.setVisibility(View.GONE);

            txtNomeStudente.setText("Nome: " + user.getString("firstName"));
            txtCognomeStudente.setText("Cognome: " + user.getString("lastName"));
            txtEmailStudente.setText("email: " + user.getEmail());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dettaglio_studente, menu);
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
