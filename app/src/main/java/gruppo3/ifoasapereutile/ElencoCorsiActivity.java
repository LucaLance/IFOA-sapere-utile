package gruppo3.ifoasapereutile;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gruppo3.ifoasapereutile.Model.Corso;
import gruppo3.ifoasapereutile.Model.KeyValue;


public class ElencoCorsiActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    RelativeLayout layoutRicerca;
    ListView listElencoCorsi;
    List<ParseObject> elencoCorsi;
    List<ParseObject> elencoCorsiRicerca;
    ElencoCorsiAdapter myAdapter;
    Spinner spinner;
    List<KeyValue> spinnerList;
    TextView txtNoCorsi;
    SpinnerAdapter adapter;
    ProgressBar progressBar;
    EditText txtCampoRicerca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_corsi);

        listElencoCorsi = (ListView) findViewById(R.id.listElencoCorsi);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtNoCorsi = (TextView) findViewById(R.id.txtNoCorsi);

        elencoCorsi = new ArrayList<>();
        spinnerList = new ArrayList<>();

        ElencoCorsiTask task = new ElencoCorsiTask();
        task.execute();

        listElencoCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject corsoSelected = (ParseObject) parent.getItemAtPosition(position);
                Corso corso = new Corso();
                try {
                    corso.setObjectId(corsoSelected.fetchIfNeeded().getObjectId());
                    corso.setNomeCorso(corsoSelected.fetchIfNeeded().getString("nomeCorso"));
                    corso.setDescrizione(corsoSelected.fetchIfNeeded().getString("descrizione"));
                    corso.setPremessa(corsoSelected.fetchIfNeeded().getString("premessa"));
                    corso.setObiettivi(corsoSelected.fetchIfNeeded().getString("obiettivi"));
                    corso.setDestinatari(corsoSelected.fetchIfNeeded().getString("destinatari"));
                    corso.setMetodologiaDidattica(corsoSelected.fetchIfNeeded().getString("metodologiaDidattica"));
                    corso.setDurata(corsoSelected.fetchIfNeeded().getString("durata"));
                    corso.setDescrizioneDurata(corsoSelected.fetchIfNeeded().getString("descrizioneDurata"));
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(ElencoCorsiActivity.this, DettaglioElencoCorsoActivity.class);
                i.putExtra("dettaglioCorso", corso);
                startActivity(i);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ElencoCorsiRicercaTask elencoCorsiRicercaTask = new ElencoCorsiRicercaTask();
        elencoCorsiRicercaTask.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private class ElencoCorsiTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> queryElencoCorsi = ParseQuery.getQuery("Corso");
            queryElencoCorsi.include("areaTematica");
            try {
                List<ParseObject> elencoCorsiList = queryElencoCorsi.find();
                if (elencoCorsiList.size()>0 && elencoCorsiList!=null){
                    elencoCorsi = elencoCorsiList;
                    for (int i=0; i<elencoCorsiList.size()-1; i++){
                        for (int j=i+1; j<elencoCorsiList.size(); j++){
                            int compare = elencoCorsi.get(i).getParseObject("areaTematica").getString("nomeAreaTematica").compareTo(
                                    elencoCorsi.get(j).getParseObject("areaTematica").getString("nomeAreaTematica"));
                            if (compare >= 0){
                                ParseObject scambio = elencoCorsi.get(i);
                                elencoCorsi.set(i,elencoCorsi.get(j));
                                elencoCorsi.set(j, scambio);
                            }
                        }
                    }
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            myAdapter = new ElencoCorsiAdapter(ElencoCorsiActivity.this, R.layout.row_elenco_corsi, elencoCorsi);

            spinnerList.add(new KeyValue("default","Scegli l'area Tematica"));
            ParseQuery<ParseObject> queryAreaTematica = ParseQuery.getQuery("AreaTematica");
            try {
                List<ParseObject> areaTematicaList = queryAreaTematica.find();
                if (areaTematicaList.size()>0 && areaTematicaList!=null){
                    for (int i = 0; i<areaTematicaList.size(); i++){
                        KeyValue keyValue = new KeyValue(areaTematicaList.get(i).getObjectId(), areaTematicaList.get(i).getString("nomeAreaTematica"));
                        spinnerList.add(keyValue);
                    }
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            // Create an ArrayAdapter using the string array and a default spinner layout
            adapter = new SpinnerAdapter(ElencoCorsiActivity.this,
                    android.R.layout.simple_spinner_item, spinnerList);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            View header = getLayoutInflater().inflate(R.layout.header_elenco_corsi, null);
            header.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            break;
                        }
                        case MotionEvent.ACTION_CANCEL: {
                            break;
                        }
                    }
                    return true;
                    }
            });
            listElencoCorsi.addHeaderView(header);
            layoutRicerca = (RelativeLayout) header.findViewById(R.id.layoutRicerca);
            // Apply the adapter to the spinner
            spinner = (Spinner) header.findViewById(R.id.spinnerAreaTematica);
            spinner.setOnItemSelectedListener(ElencoCorsiActivity.this);
            txtCampoRicerca = (EditText) header.findViewById(R.id.txtCampoRicerca);
            txtCampoRicerca.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        //check if the right key was pressed
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(txtCampoRicerca.getWindowToken(), 0);
                            ElencoCorsiRicercaTask elencoCorsiRicercaTask = new ElencoCorsiRicercaTask();
                            elencoCorsiRicercaTask.execute();
                            return true;
                        }
                    }
                    return false;
                }
            });
            spinner.setAdapter(adapter);
            listElencoCorsi.setAdapter(myAdapter);
        }
    }

    private class ElencoCorsiRicercaTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseObject areaTematica = null;
            KeyValue itemSelected = (KeyValue) spinner.getSelectedItem();
            String campoRicerca = txtCampoRicerca.getText().toString();

            elencoCorsiRicerca = new ArrayList<>(Arrays.asList(elencoCorsi.toArray(new ParseObject[elencoCorsi.size()]).clone()));

            if (itemSelected.getKey()!="default"){
                ParseQuery<ParseObject> queryAreaTematica = ParseQuery.getQuery("AreaTematica");
                queryAreaTematica.whereEqualTo("objectId", itemSelected.getKey());
                try {
                    List<ParseObject> areaTematicaList = queryAreaTematica.find();
                    if (areaTematicaList.size()>0 && areaTematicaList!=null){
                        areaTematica = areaTematicaList.get(0);
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < elencoCorsiRicerca.size(); i++){
                    if (elencoCorsiRicerca.get(i).getParseObject("areaTematica")!=areaTematica){
                        elencoCorsiRicerca.remove(i);
                        i = i-1;
                    }
                }
            }

            if (!campoRicerca.equals("")){
                List<ParseObject> elencoFiltrato = new ArrayList<>();
                boolean trovato = false;
                for (int i=0; i<elencoCorsiRicerca.size();i++){
                    ParseObject corso = elencoCorsiRicerca.get(i);
                    if (corso.getString("nomeCorso").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("descrizione").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("premessa").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("obiettivi").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("destinatari").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("metodologiaDidattica").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("durata").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (corso.getString("descrizioneDurata").toLowerCase().contains(campoRicerca.toLowerCase())){
                        trovato = true;
                    }
                    if (!trovato) {
                        elencoCorsiRicerca.remove(i);
                        i--;
                    }
                    trovato = false;
                }
            }

            myAdapter = new ElencoCorsiAdapter(ElencoCorsiActivity.this, R.layout.row_elenco_corsi, elencoCorsiRicerca);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (elencoCorsiRicerca.size()>0){
                txtNoCorsi.setVisibility(View.GONE);
            }else {
                txtNoCorsi.setVisibility(View.VISIBLE);
            }
            listElencoCorsi.setAdapter(myAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elenco_corsi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ricerca) {
            if (layoutRicerca.getVisibility() == View.VISIBLE){
                layoutRicerca.setVisibility(View.GONE);
            }else{
                layoutRicerca.setVisibility(View.VISIBLE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
