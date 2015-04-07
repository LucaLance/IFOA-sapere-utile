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

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class ElencoModuliActivity extends ActionBarActivity {

    List<ParseObject> insegna;
    ParseObject edizioneCorso;

    ModuloAdapter myAdapterModulo;

    ProgressBar progressBar;

    ListView listViewModuli;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_moduli);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listViewModuli = (ListView) findViewById(R.id.listElencoModuli);

        intent = getIntent();

        ElencoModuliTask task = new ElencoModuliTask();
        task.execute();

        listViewModuli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject insegnaSelected = (ParseObject) parent.getItemAtPosition(position);
                Intent i = new Intent(ElencoModuliActivity.this, DettaglioModuloActivity.class);
                i.putExtra("nomeModulo", insegnaSelected.getString("materia"));
                i.putExtra("idInsegna", insegnaSelected.getObjectId());
                startActivity(i);
            }
        });

    }

    private class ElencoModuliTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
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

                ParseQuery<ParseObject> queryInsegna = ParseQuery.getQuery("Insegna");
                queryInsegna.whereEqualTo("fkIdEdizioneCorso", edizioneCorso);
                try {
                    List<ParseObject> insegnaList = queryInsegna.find();
                    if (insegnaList.size() > 0 && insegnaList != null) {
                        insegna = insegnaList;
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                myAdapterModulo = new ModuloAdapter(ElencoModuliActivity.this, R.layout.row_materia, insegna);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);

            listViewModuli.setAdapter(myAdapterModulo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elenco_moduli, menu);
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
