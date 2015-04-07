package gruppo3.ifoasapereutile;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InformaticaActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    static final int CODE_HOME = 1;  // The request code HOME
    static final int CODE_SECTION2 = 2;  // The request code SECTION 2
    static final int CODE_SECTION3 = 3;  // The request code SECTION 3

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatica);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // preparing header
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        TextView txtNomeCorso = (TextView) header.findViewById(R.id.txtTitle);
        TextView txtDescrizione = (TextView) header.findViewById(R.id.txtDescrizione);
        txtNomeCorso.setText("INFORMATICA e ICT");
        txtDescrizione.setText("Le ICT (Information and Communication Technologies) sono oggi strumenti fondamentali per il funzionamento e la competitività delle organizzazioni pubbliche e private: possono essere quindi considerate a tutti gli effetti degli assets critici da proteggere e gestire nel miglior modo possibile. Esse richiedono quindi personale qualificato, sia per il loro corretto utilizzo, che per lo sviluppo dei sistemi e delle procedure. Per queste persone occorre dunque scegliere la migliore formazione. E per alcuni di loro di arrivare anche ad una certificazione delle competenze possedute…\n" +
                "\n" +
                "L’ICT – che raggruppa Informatica e Telecomunicazioni - è da sempre una delle core competences di IFOA.\n" +
                "\n" +
                "In linea con la propria mission, IFOA svolge un’ampia attività al servizio delle imprese e dei giovani nell’area delle più attuali ICT, offrendo i programmi di formazione e certificazione delle competenze più diffusi e riconosciuti a livello internazionale nel mondo.\n" +
                "\n" +
                "Nell’attività formativa rivolta alle imprese, IFOA accompagna gli IT Managers (CIO) nella qualificazione del proprio personale e degli utenti in azienda. L’esperienza consolidata ci permette di comprendere le esigenze aziendali e di individuare insieme alle imprese le competenze da rafforzare e gli strumenti più opportuni per farlo.\n" +
                "\n" +
                "Negli anni IFOA ha formato intere generazioni di tecnici informatici ed ha sviluppato un’offerta formativa ICT flessibile, in grado di adattarsi alle esigenze dei giovani, delle persone, dei professionisti, delle aziende acquirenti e di quelle fornitrici di servizi ICT, di ogni settore economico e dimensione.");
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

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Servizi ICT alle imprese");
        listDataHeader.add("ICT Competence Community e Eventi");

        // Adding child data
        List<String> servizi = new ArrayList<String>();
        String sourceString = "Il know how IFOA si concretizza anche in una serie di Servizi dedicati alle imprese:<br>\n" +
                "<strong>Analizziamo i fabbisogni</strong><br>\n" +
                "La nostra esperienza ci permette di comprendere le esigenze e di individuare insieme all’azienda le competenze da rafforzare e gli strumenti più opportuni per farlo.<br>\n" +
                "<strong>La nostra formazione e i nostri servizi</strong><br>\n" +
                "Assistiamo gli IT Managers e i Responsabili Risorse Umane nelle loro principali attività (formazione e mappatura delle competenze, coaching e affiancamento, recruiting e consulenza).<br>\n" +
                "<br>\n" +
                "Offriamo alle imprese:<br>\n" +
                "Piani formativi e corsi “personalizzati” per gli addetti di aziende con team ICT consistenti<br>\n" +
                "Corsi formativi interaziendali “a catalogo” per aziende con team ICT di ridotte dimensioni<br>\n" +
                "Percorsi di alta specializzazione informatica rivolti a managers e tecnici<br>\n" +
                "Percorsi di certificazione professionale<br>\n" +
                "<strong>Misuriamo l’efficacia</strong><br>\n" +
                "Abbiamo messo a punto uno strumento di valutazione delle competenze appositamente sviluppato sulle figure ICT, basato su un modello condiviso a livello europeo, in grado di misurare l’efficacia delle azioni di formazione e servizi messe in campo. Lo strumento aiuta gli IT Managers e i Responsabili Risorse Umane anche nell’individuazione dei fabbisogni formativi e dei migliori percorsi per soddisfarli.<br>\n" +
                "<strong>La nostra struttura didattica</strong><br>\n" +
                "Con IFOA le aziende possono contare su:<br>\n" +
                "Staff dedicato di specialisti della formazione<br>\n" +
                "Ampia faculty composta da istruttori e docenti certificati, tutti professionisti del settore tecnologico dotati di un background specifico nella formazione<br>\n" +
                "Materiali didattici ufficiali<br>\n" +
                "Laboratori completi e attrezzature di ultima generazione<br>\n" +
                "A fianco della formazione tradizionale, IFOA mette a disposizione delle imprese il proprio know how nella formazione a distanza. Le aziende interessate possono accedere alla nostra piattaforma di E-Learning per un’erogazione dei corsi in modalità mixed, in cui le ore d’aula possono essere integrate con attività didattica ed esercitazione on line.<br>\n" +
                "<strong>I nostri numeri</strong><br>\n" +
                "<strong>776</strong> le persone formate sui temi ICT nell’ultimo anno<br>\n" +
                "<strong>4.000</strong> le persone formate sui temi ICT negli ultimi 5 anni<br>\n" +
                "<strong>6.738.000</strong> Euro gestiti in attività ICT negli ultimi 5 anni<br>\n" +
                "<strong>257</strong> PC ad uso didattico<br>\n" +
                "<strong>7</strong> Laboratori Cisco: oltre 50 tra Switch, Router e Firewall sia fissi che wireless";
        servizi.add(sourceString);

        List<String> competence = new ArrayList<String>();
        sourceString = "IFOA per accrescere il sistema delle competenze distintive degli specialisti di funzione ICT ha dato vita da qualche anno al Progetto ICT Competence Community: un network che coinvolge i responsabili aziendali della funzione ICT mettendo a loro disposizione servizi e strumenti.<br><br>\n" +
                "<br>\n" +
                "Il progetto è focalizzato specificamente sul tema delle competenze informatiche nell’impresa e dello sviluppo delle risorse umane ICT.<br>\n" +
                "<br>\n" +
                "I membri della Community possono:<br>\n" +
                "&#8226; discutere attorno a dei temi di vivo interesse sia in presenza che via internet<br>\n" +
                "&#8226; essere in contatto con altri CIO<br>\n" +
                "&#8226; ricevere dalla community informazioni di loro interesse, informazioni su eventi e progetti nel settore, news dal settore ICT;<br>\n" +
                "&#8226; scoprire opportunità di formazione individuale o aziendale;<br>\n" +
                "&#8226; collaudare o sperimentare un sistema di mappatura delle figure professionali e delle competenze;<br>\n" +
                "&#8226; utilizzare software di valutazione delle competenze;<br>\n" +
                "&#8226; accedere a modelli e tool per creare un piano formativo aziendale;<br>\n" +
                "&#8226; approfondire il benchmarking retributivo delle figure ICT;<br>\n" +
                "&#8226; conoscere modalità e sistemi per il recruiting e la gestione dei contratti in outsourcing";
        competence.add(sourceString);
        listDataChild.put(listDataHeader.get(0), servizi); // Header, Child data
        listDataChild.put(listDataHeader.get(1), competence);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Intent i = new Intent(this, MainActivity.class);
        switch (number) {
            case 1:
                i.putExtra("codice", CODE_HOME);
                startActivity(i);
                break;
            case 2:
                i.putExtra("codice", CODE_SECTION2);
                startActivity(i);
                break;
            case 3:
                i.putExtra("codice", CODE_SECTION3);
                startActivity(i);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_informatica, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

   /*     @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }*/

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((InformaticaActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}