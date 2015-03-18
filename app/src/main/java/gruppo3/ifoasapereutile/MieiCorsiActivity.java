package gruppo3.ifoasapereutile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import gruppo3.ifoasapereutile.model.Corso;
import gruppo3.ifoasapereutile.model.Docente;
import gruppo3.ifoasapereutile.model.EdizioneCorso;
import gruppo3.ifoasapereutile.model.Insegna;
import gruppo3.ifoasapereutile.model.Partecipa;
import gruppo3.ifoasapereutile.model.Studente;


public class MieiCorsiActivity extends ActionBarActivity {

    ParseUser user = ParseUser.getCurrentUser();

    ParseObject userStudente;
    ParseObject userDocente;
    List<ParseObject> studentePartecipa;
    List<ParseObject> docenteInsegna;
    List<ParseObject> edizioneCorso = new ArrayList<>();
    List<ParseObject> corso = new ArrayList<>();

    EdizioneCorsoAdapter myAdapter;

    ProgressBar progressBar;
    TextView txtNoCorsi;

    ListView listViewCorsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miei_corsi);

        txtNoCorsi = (TextView) findViewById(R.id.txtNoCorsi);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listViewCorsi = (ListView) findViewById(R.id.listCorsi);

        MieiCorsiTask task = new MieiCorsiTask();
        task.execute();

        listViewCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MieiCorsiActivity.this, DettaglioCorsoActivity.class);
                ParseObject edizioneCorsoSelected = (ParseObject) parent.getItemAtPosition(position);
                ParseObject corsoselected = null;
                try {
                    corsoselected = edizioneCorsoSelected.fetchIfNeeded().getParseObject("fkIdCorso");
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
                String nomeCorso = "";
                try {
                    nomeCorso = corsoselected.fetchIfNeeded().getString("nomeCorso");
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
                i.putExtra("nomeCorso", nomeCorso);
                if (userStudente!=null){
                    i.putExtra("tipoUtente", userStudente.getClassName());
                }else{
                    i.putExtra("tipoUtente", userDocente.getClassName());
                    i.putExtra("idEdizioneCorso", edizioneCorsoSelected.getObjectId());
                }
                startActivity(i);
            }
        });

    }

    private class MieiCorsiTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            txtNoCorsi.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> queryStudente = ParseQuery.getQuery("Studente");
            queryStudente.whereEqualTo("fkIdUser", user);
            try {
                List<ParseObject> studenteList = queryStudente.find();
                if (studenteList.size()>0 && studenteList!=null){
                    userStudente = studenteList.get(0);
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            if (userStudente == null) {
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
            }

            if (userStudente != null) {
                ParseQuery<ParseObject> queryPartecipa = ParseQuery.getQuery("Partecipa");
                queryPartecipa.whereEqualTo("fkIdStudente", userStudente);
                try {
                    List<ParseObject> partecipaList = queryPartecipa.find();
                    if (partecipaList.size()>0 && partecipaList!=null){
                        studentePartecipa = partecipaList;
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            } else {
                ParseQuery<ParseObject> queryInsegna = ParseQuery.getQuery("Insegna");
                queryInsegna.whereEqualTo("fkIdDocente", userDocente);
                try {
                    List<ParseObject> insegnaList = queryInsegna.find();
                    if (insegnaList.size()>0 && insegnaList!=null){
                        docenteInsegna = insegnaList;
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }

            if (studentePartecipa != null){
                for (int i = 0; i<studentePartecipa.size(); i++){
                    if (!edizioneCorso.contains(studentePartecipa.get(i).getParseObject("fkIdEdizioneCorso"))) {
                        edizioneCorso.add(studentePartecipa.get(i).getParseObject("fkIdEdizioneCorso"));
                    }
                }
            }else if (docenteInsegna != null){
                for (int i = 0; i<docenteInsegna.size(); i++){
                    if (!edizioneCorso.contains(docenteInsegna.get(i).getParseObject("fkIdEdizioneCorso"))) {
                        edizioneCorso.add(docenteInsegna.get(i).getParseObject("fkIdEdizioneCorso"));
                    }
                }
            }

            if (edizioneCorso != null){
                for(int i = 0; i<edizioneCorso.size()-1; i++){
                    for (int j = i+1; j<edizioneCorso.size(); j++){
                        ParseObject scambio;
                        try {
                            if (!edizioneCorso.get(i).fetchIfNeeded().getDate("dataFine").after(edizioneCorso.get(j).fetchIfNeeded().getDate("dataFine"))){
                                scambio = edizioneCorso.get(i);
                                edizioneCorso.set(i, edizioneCorso.get(j));
                                edizioneCorso.set(j, scambio);
                            }
                        } catch (com.parse.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        if (edizioneCorso!=null){
            for (int i = 0; i<edizioneCorso.size(); i++){
                try {
                    corso.add(edizioneCorso.get(i).fetchIfNeeded().getParseObject("fkIdCorso"));
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }
        }

            myAdapter = new EdizioneCorsoAdapter(MieiCorsiActivity.this, R.layout.row_corso, edizioneCorso);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (edizioneCorso.size()==0){
                txtNoCorsi.setVisibility(View.VISIBLE);
            }
            listViewCorsi.setAdapter(myAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_miei_corsi, menu);
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
