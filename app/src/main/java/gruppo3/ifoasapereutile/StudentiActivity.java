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

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class StudentiActivity extends ActionBarActivity {

    ParseObject edizioneCorso;
    List<ParseObject> studentePartecipa = new ArrayList<>();
    List<ParseObject> listStudenti = new ArrayList<>();
    List<ParseObject> listUtenti = new ArrayList<>();

    ListView listViewElencoStudenti;
    TextView txtNoStudenti;
    ProgressBar progressBar;

    ElencoStudentiAdapter myAdapterStudente;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studenti);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listViewElencoStudenti = (ListView) findViewById(R.id.listStudenti);
        txtNoStudenti = (TextView) findViewById(R.id.txtNoStudenti);

        intent = getIntent();

        StudentiTask task = new StudentiTask();
        task.execute();

        listViewElencoStudenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(StudentiActivity.this, DettaglioStudenteActivity.class);
                ParseObject userSelected = (ParseObject) parent.getItemAtPosition(position);
                i.putExtra("idUser", userSelected.getObjectId());
                startActivity(i);
            }
        });

    }

    private class StudentiTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            txtNoStudenti.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            ParseQuery<ParseObject> queryEdizioneCorso = ParseQuery.getQuery("EdizioneCorso");
            queryEdizioneCorso.whereEqualTo("objectId", intent.getStringExtra("idEdizioneCorso"));
            try {
                List<ParseObject> edizioneCorsoList = queryEdizioneCorso.find();
                if (edizioneCorsoList.size() > 0 && edizioneCorsoList != null) {
                    edizioneCorso = edizioneCorsoList.get(0);
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject> queryPartecipa = ParseQuery.getQuery("Partecipa");
            queryPartecipa.include("fkIdStudente");
            queryPartecipa.whereEqualTo("fkIdEdizioneCorso", edizioneCorso);
                try {
                    List<ParseObject> partecipaList = queryPartecipa.find();
                    if (partecipaList.size() > 0 && partecipaList != null) {
                        studentePartecipa = partecipaList;
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                if (studentePartecipa.size()>0 && studentePartecipa!=null){
                    for (int i = 0; i<studentePartecipa.size(); i++){
                        try {
                            listStudenti.add(studentePartecipa.get(i).fetchIfNeeded().getParseObject("fkIdStudente"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            if (listStudenti.size()>0 && listStudenti!=null){
                for (int i = 0; i<listStudenti.size(); i++){
                    listUtenti.add(listStudenti.get(i).getParseObject("fkIdUser"));
                }
            }

            myAdapterStudente = new ElencoStudentiAdapter(StudentiActivity.this, R.layout.row_studente, listUtenti);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (listUtenti.size()>0 && listUtenti!=null){
                listViewElencoStudenti.setAdapter(myAdapterStudente);
            }else{
                txtNoStudenti.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_studenti, menu);
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
