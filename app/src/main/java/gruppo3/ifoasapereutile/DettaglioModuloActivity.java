package gruppo3.ifoasapereutile;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.List;


public class DettaglioModuloActivity extends ActionBarActivity {

    ParseObject insegna;
    ParseObject docente;
    ParseUser userDocente;

    Intent intent;

    TextView txtNomeModulo, txtDocente, txtTitleDocente, txtNumeroOre, txtTitleOre, txtArgomenti, txtTitleArgomenti;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_modulo);

        txtNomeModulo = (TextView) findViewById(R.id.txtNomeModulo);
        txtTitleDocente = (TextView) findViewById(R.id.txtTitleDocente);
        txtNumeroOre = (TextView) findViewById(R.id.txtOre);
        txtTitleOre = (TextView) findViewById(R.id.txtTitleOre);
        txtArgomenti = (TextView) findViewById(R.id.txtArgomenti);
        txtTitleArgomenti = (TextView) findViewById(R.id.txtTitleArgomenti);
        txtDocente = (TextView) findViewById(R.id.txtDocente);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        intent = getIntent();

        txtNomeModulo.setText(intent.getStringExtra("nomeModulo"));

        DettaglioModuloTask task = new DettaglioModuloTask();
        task.execute();

        txtDocente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        txtDocente.setTextColor(Color.RED);
                        break;
                    case MotionEvent.ACTION_UP:
                        txtDocente.setTextColor(Color.BLACK);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        txtDocente.setTextColor(Color.BLACK);
                        break;
                }
                return true;
            }
        });

    }

    private class DettaglioModuloTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

                ParseQuery<ParseObject> queryInsegna = ParseQuery.getQuery("Insegna");
                queryInsegna.include("_User");
                queryInsegna.whereEqualTo("objectId", intent.getStringExtra("idInsegna"));
                try {
                    List<ParseObject> insegnaList = queryInsegna.find();
                    if (insegnaList.size() > 0 && insegnaList != null) {
                        insegna = insegnaList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                docente = insegna.getParseObject("fkIdDocente");

                ParseQuery<ParseObject> queryDocente = ParseQuery.getQuery("Docente");
                queryDocente.include("fkIdUser");
                queryDocente.whereEqualTo("objectId", docente.getObjectId());
                try {
                    List<ParseObject> docenteList = queryDocente.find();
                    if (docenteList.size() > 0 && docenteList != null) {
                        docente = docenteList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

            userDocente = docente.getParseUser("fkIdUser");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            txtDocente.setText(userDocente.getString("firstName") + " " + userDocente.getString("lastName"));
            txtTitleDocente.setText("Docente");
            txtNumeroOre.setText("" + insegna.getInt("numeroOre"));
            txtTitleOre.setText("Ore Totali");
            txtArgomenti.setText("" + insegna.getString("argomentiMateria"));
            txtTitleArgomenti.setText("Argomenti");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dettaglio_materia, menu);
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
