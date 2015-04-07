package gruppo3.ifoasapereutile;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gruppo3.ifoasapereutile.Model.Corso;


public class DettaglioElencoCorsoActivity extends ActionBarActivity {

    Intent intent;
    Corso corso;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_elenco_corso);

        intent = getIntent();

        corso = intent.getParcelableExtra("dettaglioCorso");

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // preparing header
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        TextView txtNomeCorso = (TextView) header.findViewById(R.id.txtTitle);
        TextView txtDescrizione = (TextView) header.findViewById(R.id.txtDescrizione);
        txtNomeCorso.setText(corso.getNomeCorso());
        txtDescrizione.setText(corso.getDescrizione());
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
        expListView.addHeaderView(header);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Premessa");
        listDataHeader.add("Obiettivi");
        listDataHeader.add("Destinatari");
        listDataHeader.add("Metodologia Didattica");
        listDataHeader.add("Durata");
        listDataHeader.add("Descrizione Durata");

        // Adding child data
        List<String> premessa = new ArrayList<String>();
        premessa.add(corso.getPremessa());

        List<String> obiettivi = new ArrayList<String>();
        obiettivi.add(corso.getObiettivi());

        List<String> destinatari = new ArrayList<String>();
        destinatari.add(corso.getDestinatari());

        List<String> metodologiaDidattica = new ArrayList<String>();
        metodologiaDidattica.add(corso.getMetodologiaDidattica());

        List<String> durata = new ArrayList<String>();
        durata.add(corso.getDurata());

        List<String> descrizioneDurata = new ArrayList<String>();
        descrizioneDurata.add(corso.getDescrizioneDurata());

        listDataChild.put(listDataHeader.get(0), premessa); // Header, Child data
        listDataChild.put(listDataHeader.get(1), obiettivi);
        listDataChild.put(listDataHeader.get(2), destinatari);
        listDataChild.put(listDataHeader.get(3), metodologiaDidattica);
        listDataChild.put(listDataHeader.get(4), durata);
        listDataChild.put(listDataHeader.get(5), descrizioneDurata);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dettaglio_elenco_corso, menu);
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
