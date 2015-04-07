package gruppo3.ifoasapereutile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DettaglioCorsoActivity extends ActionBarActivity {

    ParseUser user = ParseUser.getCurrentUser();

    ParseObject userStudente;
    ParseObject userDocente;
    List<ParseObject> studentePartecipa;
    List<ParseObject> docenteInsegna;
    ParseObject edizioneCorso;

    ProgressBar progressBar;
    TextView txtNomeCorso;
    TextView txtDataInizio, txtDataFine;
    ListView listViewMaterie;
    Button btnElencoStudenti, btnModuli, btnCalendario;

    MateriaAdapter myAdapterMateria;

    String idEdizioneCorso;

    RelativeLayout layoutStudente, layoutDocente;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_corso);

        txtNomeCorso = (TextView) findViewById(R.id.txtNomeCorso);
        listViewMaterie = (ListView) findViewById(R.id.listMaterie);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtDataInizio = (TextView) findViewById(R.id.txtDataInizio);
        txtDataFine = (TextView) findViewById(R.id.txtDataFine);
        btnElencoStudenti = (Button) findViewById(R.id.btnElencoStudenti);
        btnModuli = (Button) findViewById(R.id.btnModuli);
        btnCalendario = (Button) findViewById(R.id.btnCalendario);

        layoutDocente = (RelativeLayout) findViewById(R.id.layoutDocente);
        layoutStudente = (RelativeLayout) findViewById(R.id.layoutStudente);

        intent = getIntent();

        idEdizioneCorso = intent.getStringExtra("idEdizioneCorso");

        txtNomeCorso.setText(intent.getStringExtra("nomeCorso"));

        DettaglioCorsoTask task = new DettaglioCorsoTask();
        task.execute();

        btnElencoStudenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DettaglioCorsoActivity.this, StudentiActivity.class);
                i.putExtra("idEdizioneCorso", edizioneCorso.getObjectId());
                startActivity(i);
            }
        });

        btnModuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DettaglioCorsoActivity.this, ElencoModuliActivity.class);
                i.putExtra("idEdizioneCorso", edizioneCorso.getObjectId());
                startActivity(i);
            }
        });

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DettaglioCorsoActivity.this, CalendarioActivity.class);
                if (userDocente != null){
                    i.putExtra("Docente", user.getString("firstName") + " " + user.getString("lastName"));
                }
                i.putExtra("Calendario", edizioneCorso.getString("calendario"));
                startActivity(i);
            }
        });

    }

    private class DettaglioCorsoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

            btnCalendario.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (intent.getStringExtra("tipoUtente").equals("Docente")) {

                ParseQuery<ParseObject> queryDocente = ParseQuery.getQuery("Docente");
                queryDocente.whereEqualTo("fkIdUser", user);
                try {
                    List<ParseObject> docenteList = queryDocente.find();
                    if (docenteList.size() > 0 && docenteList != null) {
                        userDocente = docenteList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                ParseQuery<ParseObject> queryEdizioneCorso = ParseQuery.getQuery("EdizioneCorso");
                queryEdizioneCorso.whereEqualTo("objectId", idEdizioneCorso);
                try {
                    List<ParseObject> edizioneCorsoList = queryEdizioneCorso.find();
                    if (edizioneCorsoList.size() > 0 && edizioneCorsoList != null) {
                        edizioneCorso = edizioneCorsoList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                ParseQuery<ParseObject> queryInsegna = ParseQuery.getQuery("Insegna");
                queryInsegna.whereEqualTo("fkIdDocente", userDocente);
                queryInsegna.whereEqualTo("fkIdEdizioneCorso", edizioneCorso);
                try {
                    List<ParseObject> insegnaList = queryInsegna.find();
                    if (insegnaList.size() > 0 && insegnaList != null) {
                        docenteInsegna = insegnaList;
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                myAdapterMateria = new MateriaAdapter(DettaglioCorsoActivity.this, R.layout.row_materia, docenteInsegna);

            }else{
                ParseQuery<ParseObject> queryStudente = ParseQuery.getQuery("Studente");
                queryStudente.whereEqualTo("fkIdUser", user);
                try {
                    List<ParseObject> studenteList = queryStudente.find();
                    if (studenteList.size() > 0 && studenteList != null) {
                        userStudente = studenteList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                ParseQuery<ParseObject> queryEdizioneCorso = ParseQuery.getQuery("EdizioneCorso");
                queryEdizioneCorso.whereEqualTo("objectId", idEdizioneCorso);
                try {
                    List<ParseObject> edizioneCorsoList = queryEdizioneCorso.find();
                    if (edizioneCorsoList.size() > 0 && edizioneCorsoList != null) {
                        edizioneCorso = edizioneCorsoList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            btnCalendario.setVisibility(View.VISIBLE);
            txtDataInizio.setText("Data Inizio: " + new SimpleDateFormat("dd/MM/yyyy").format(edizioneCorso.getDate("dataInizio")));
            txtDataFine.setText("Data Fine: " + new SimpleDateFormat("dd/MM/yyyy").format(edizioneCorso.getDate("dataFine")));
            if (intent.getStringExtra("tipoUtente").equals("Studente")){
                layoutStudente.setVisibility(View.VISIBLE);
                layoutDocente.setVisibility(View.GONE);
            }else{
                layoutStudente.setVisibility(View.GONE);
                layoutDocente.setVisibility(View.VISIBLE);
                listViewMaterie.setAdapter(myAdapterMateria);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dettaglio_corsi, menu);
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
