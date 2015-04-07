package gruppo3.ifoasapereutile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    RelativeLayout contentHome, contentContatti, contentSedi, contentProfilo;

    TextView txtUserName;

    Button btnInformatica, btnIfoa, btnCorsi, btnElencoCorsi;

    ParseUser user =ParseUser.getCurrentUser();

    ExpandableListView expListContatti;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_ifoa, null);
        TextView txtDialogIfoa = (TextView) dialogView.findViewById(R.id.txtDialogIfoa);
// Set other dialog properties
        final String dialogText = "Da quarant’anni IFOA si occupa di formazione. Trasmettere e accrescere le competenze professionali è la missione perseguita, con l’obiettivo di arricchire le persone nel loro sviluppo, di rafforzare il capitale umano e la competitività delle imprese e del sistema economico.\n" +
                "\n" +
                "IFOA opera in tante regioni in Italia ed anche su scala globale.\n" +
                "\n" +
                "Sviluppa attività di formazione in molteplici ambiti specialistici, servizi di inserimento al lavoro, servizi di assistenza tecnica e consulenza.\n" +
                "\n" +
                "IFOA è centro di formazione e servizi delle Camere di Commercio.\n" +
                "“Sapere utile” è il motto adottato per contrassegnarne le attività: portare beneficio all’utente e al cliente e renderne conto.\n" +
                "\n" +
                "Ti aspettiamo!";
        builder.setView(dialogView);
        txtDialogIfoa.setText(dialogText);
// Create the AlertDialog
        final AlertDialog dialogIfoa = builder.create();

        contentHome = (RelativeLayout) findViewById(R.id.contentHome);
        contentContatti = (RelativeLayout) findViewById(R.id.contentContatti);
        contentSedi = (RelativeLayout) findViewById(R.id.contentSedi);
        contentProfilo = (RelativeLayout) findViewById(R.id.contentProfilo);

        txtUserName = (TextView) findViewById(R.id.txtUserName);

        btnIfoa = (Button) findViewById(R.id.btnIfoa);
        btnInformatica = (Button) findViewById(R.id.btnInformatica);
        btnCorsi = (Button) findViewById(R.id.btnCorsi);
        btnElencoCorsi = (Button) findViewById(R.id.btnElencoCorsi);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        btnInformatica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InformaticaActivity.class);
                startActivity(i);
            }
        });

        btnIfoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogIfoa.show();
                dialogIfoa.getWindow().setLayout(725, 900);
            }
        });

        btnCorsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MieiCorsiActivity.class);
                startActivity(i);
            }
        });

        btnElencoCorsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ElencoCorsiActivity.class);
                startActivity(i);
            }
        });

        txtDialogIfoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogIfoa.cancel();
            }
        });

        if (user != null){
            txtUserName.setText("Ciao, " + user.get("firstName").toString());
            contentHome.setVisibility(View.GONE);
            contentContatti.setVisibility(View.GONE);
            contentSedi.setVisibility(View.GONE);
            contentProfilo.setVisibility(View.VISIBLE);
            mTitle = "Il mio profilo";
        }

        Intent returnIntent = getIntent();
        int section = returnIntent.getIntExtra("codice", -1);
        if (section == 0){
            contentHome.setVisibility(View.GONE);
            contentContatti.setVisibility(View.GONE);
            contentSedi.setVisibility(View.GONE);
            contentProfilo.setVisibility(View.VISIBLE);
            mTitle = "Il mio profilo";
        }
        if (section != 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(section-1))
                    .commit();
        }

        // get the listview
        expListContatti = (ExpandableListView) findViewById(R.id.expListContatti);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // preparing header
        View header = getLayoutInflater().inflate(R.layout.list_header, null);
        TextView txtNomeCorso = (TextView) header.findViewById(R.id.txtTitle);
        TextView txtDescrizione = (TextView) header.findViewById(R.id.txtDescrizione);
        txtNomeCorso.setText("Contatti");
        txtDescrizione.setText("");
        txtDescrizione.setVisibility(View.GONE);
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
        expListContatti.addHeaderView(header);

        // setting list adapter
        expListContatti.setAdapter(listAdapter);

        FrameLayout frameSedi = (FrameLayout) findViewById(R.id.frameSedi);

        frameSedi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                ImageView imageView = (ImageView) v.findViewById(R.id.image);

                Intent i = new Intent(MainActivity.this, DettaglioSedeActivity.class);

                final int action = ev.getAction();
                // (1)
                final int evX = (int) ev.getX();
                final int evY = (int) ev.getY();

                int touchColor = 0;
                if (evY >= 0){
                    touchColor = getHotspotColor(R.id.image_areas, evX, evY);
                }
                int tolerance = 25;
                int nextImage = 0;
                switch (action) {
                    case MotionEvent.ACTION_DOWN :
                        boolean clickable = false;
                        if (closeMatch (Color.RED, touchColor, tolerance)) {
                            // Do the action associated with the RED region
                            nextImage = R.drawable.mantova_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.GREEN, touchColor, tolerance)) {
                            nextImage = R.drawable.milano_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.BLUE, touchColor, tolerance)) {
                            nextImage = R.drawable.padova_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.YELLOW, touchColor, tolerance)) {
                            nextImage = R.drawable.bologna_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.CYAN, touchColor, tolerance)) {
                            nextImage = R.drawable.modena_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.parseColor("#800080"), touchColor, tolerance)) {
                            nextImage = R.drawable.parma_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.parseColor("#FFA500"), touchColor, tolerance)) {
                            nextImage = R.drawable.reggioemilia_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.parseColor("#FFC0CB"), touchColor, tolerance)) {
                            nextImage = R.drawable.firenze_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.WHITE, touchColor, tolerance)) {
                            nextImage = R.drawable.napoli_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.parseColor("#008000"), touchColor, tolerance)) {
                            nextImage = R.drawable.bari_selected;
                            clickable = true;
                        }
                        if (closeMatch (Color.GRAY, touchColor, tolerance)) {
                            nextImage = R.drawable.cagliari_selected;
                            clickable = true;
                        }
                        if (!clickable){
                            nextImage = R.drawable.sedi_ifoa_2015;
                        }
                        break;
                    case MotionEvent.ACTION_UP :

                        if (closeMatch (Color.RED, touchColor, tolerance)) {
                            // Do the action associated with the RED region
                            i.putExtra("sede", "mantova");
                            startActivity(i);
                        }
                        if (closeMatch (Color.GREEN, touchColor, tolerance)) {
                            i.putExtra("sede", "milano");
                            startActivity(i);
                        }
                        if (closeMatch (Color.BLUE, touchColor, tolerance)) {
                            i.putExtra("sede", "padova");
                            startActivity(i);
                        }
                        if (closeMatch (Color.YELLOW, touchColor, tolerance)) {
                            i.putExtra("sede", "bologna");
                            startActivity(i);
                        }
                        if (closeMatch (Color.CYAN, touchColor, tolerance)) {
                            i.putExtra("sede", "modena");
                            startActivity(i);
                        }
                        if (closeMatch (Color.parseColor("#800080"), touchColor, tolerance)) {
                            i.putExtra("sede", "parma");
                            startActivity(i);
                        }
                        if (closeMatch (Color.parseColor("#FFA500"), touchColor, tolerance)) {
                            i.putExtra("sede", "reggioemilia");
                            startActivity(i);
                        }
                        if (closeMatch (Color.parseColor("#FFC0CB"), touchColor, tolerance)) {
                            i.putExtra("sede", "firenze");
                            startActivity(i);
                        }
                        if (closeMatch (Color.WHITE, touchColor, tolerance)) {
                            i.putExtra("sede", "napoli");
                            startActivity(i);
                        }
                        if (closeMatch (Color.parseColor("#008000"), touchColor, tolerance)) {
                            i.putExtra("sede", "bari");
                            startActivity(i);
                        }
                        if (closeMatch (Color.GRAY, touchColor, tolerance)) {
                            i.putExtra("sede", "cagliari");
                            startActivity(i);
                        }
                        nextImage = R.drawable.sedi_ifoa_2015;
                        break;
                } // end switch
                if (nextImage > 0) {
                    imageView.setImageResource (nextImage);
                    imageView.setTag (nextImage);
                }
                return true;
            }
        });

    }

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    public boolean closeMatch (int color1, int color2, int tolerance) {
        if ((int) Math.abs (Color.red (color1) - Color.red (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerance )
            return false;
        if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance )
            return false;
        return true;
    } // end match

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Presidente");
        listDataHeader.add("Direzione");
        listDataHeader.add("Servizi Centrali e di Staff");
        listDataHeader.add("Linee e Prodotti");
        listDataHeader.add("Aree Tematiche");
        listDataHeader.add("Progetti di Sistema e Internazionali");
        listDataHeader.add("Sedi Ifoa e Territori");

        // Adding child data
        List<String> presidente = new ArrayList<String>();
        String sourceString = "<strong>Lorenzo Giberti</strong><br>\n" +
                "<i>Presidente</i><br>\n" +
                "email: fieni@ifoa.it<br>\n" +
                "tel. 0522-329.111\t <br>\n" +
                "Nato nel 1948 a Carpineti, è residente a Cadelbosco Sopra, Comune dove ha ricoperto il ruolo di sindaco per 12 anni.<br>\n" +
                "<br>\n" +
                "Dal 1989 ricopre la responsabilità delle “Relazioni Industriali e politiche del lavoro” per conto della Legacoop di Reggio Emilia. E’ membro della Commissione Nazionale Relazioni Industriali di Legacoop e ha partecipato alla discussione e alla stipula di diversi CCNL sia a livello nazionale che a livello territoriale.<br>\n" +
                "<br>\n" +
                "E’ membro di Enti Bilaterali (Cassa Edile, Scuola Edile, Associazione Edile per la Sicurezza) e dell’Osservatorio per la Cooperazione presso la Direzione Provinciale del Lavoro di Reggio Emilia";
        presidente.add(sourceString);

        List<String> direzione = new ArrayList<String>();
        sourceString = "<strong>Umberto Lonardoni</strong><br>\n" +
                "<i>Direttore generale</i><br>\n" +
                "email: fieni@ifoa.it<br>\n" +
                "tel. 0522-329.111\t<br>\n" +
                "Veronese di origine, ma reggiano di adozione, 45 anni, laureato in Matematica, è sposato e padre di cinque figli.<br>\n" +
                "<br>\n" +
                "In IFOA dal 1994 ha operato con diversi compiti e crescenti responsabilità, anche nella presenza di IFOA nelle aree Veneto e Lombardia.<br>\n" +
                "<br>\n" +
                "Oltre all'esperienza professionale in IFOA, è stato membro del Consiglio di Amministrazione dell'Università di Modena, Presidente di una cooperativa editoriale e fondatore e presidente di una società per azioni attiva nel settore della progettazione e fornitura di arredo per locali commerciali.";
        direzione.add(sourceString);

        List<String> servizi = new ArrayList<String>();
        sourceString = "<strong>Stefano Soldati</strong><br>\n" +
                "<i>Servizi centrali, Sistema Informativo, Rendicontazione</i><br>\n" +
                "email: soldati@ifoa.it<br>\n" +
                "tel. 0522–329.262<br>\n" +
                "<br>\n" +
                "<strong>Umberto Lonardoni</strong><br>\n" +
                "<i>Risorse Umane</i><br>\n" +
                "email: info@ifoa.it<br>\n" +
                "tel. 0522–329.111<br>\n" +
                "<br>\n" +
                "<strong>Donatella Davolio</strong><br>\n" +
                "<i>Amministrazione del Personale</i><br>\n" +
                "email: davolio@ifoa.it<br>\n" +
                "tel. 0522-329.226<br>\n" +
                "<br>\n" +
                "<strong>Francesca Campani</strong><br>\n" +
                "<i>Amministrazione, Contabilità generale</i><br>\n" +
                "email: campani@ifoa.it<br>\n" +
                "tel. 0522–329.269<br>\n" +
                "<br>\n" +
                "<strong>Paola Magelli</strong><br>\n" +
                "<i>Acquisti, Sicurezza, Servizi generali e Logistica</i><br>\n" +
                "email: magelli@ifoa.it<br>\n" +
                "tel. 0522–329.235<br>\n" +
                "<br>\n" +
                "<strong>Giacomo Torlai</strong><br>\n" +
                "<i>Pianificazione, Controllo e Finanza</i><br>\n" +
                "email: info@ifoa.it<br>\n" +
                "tel. 0522–329.111<br>\n" +
                "<br>\n" +
                "<strong>Marina Soncini</strong><br>\n" +
                "<i>Tesoreri</i>a<br>\n" +
                "email: soncini@ifoa.it<br>\n" +
                "tel. 0522-329.268<br>\n" +
                "<br>\n" +
                "<strong>Stefania Cocorullo</strong><br>\n" +
                "<i>Ufficio Informazioni, INFOPoint e Selezioni</i><br>\n" +
                "email: cocorullo@ifoa.it<br>\n" +
                "tel. 0522–329.224<br>\n" +
                "<br>\n" +
                "<strong>Cosetta Soragni</strong><br>\n" +
                "<i>Ufficio Marketing</i><br>\n" +
                "email: soragni@ifoa.it<br>\n" +
                "tel. 0522–329.305<br>\n" +
                "<br>\n" +
                "<strong>Barbara Bianchi</strong><br>\n" +
                "<i>Ufficio Comunicazione</i><br>\n" +
                "email: bianchi@ifoa.it<br>\n" +
                "tel. 0522–329.258<br>\n" +
                "<br>\n" +
                "<strong>Elio Perrone</strong><br>\n" +
                "<i>Web e Social Media Manager</i><br>\n" +
                "email: perrone@ifoa.it<br>\n" +
                "tel. 0522–329.280";
        servizi.add(sourceString);

        List<String> prodotti = new ArrayList<String>();
        sourceString = "<strong>Ornella Trombino</strong><br>\n" +
                "<i>Linea Corsi a Catalogo</i><br>\n" +
                "email: trombino@ifoa.it<br>\n" +
                "tel. 0522–329.240<br>\n" +
                "<br>\n" +
                "<strong>Serenella Musu</strong><br>\n" +
                "<i>Linea Tirocini Formativi</i><br>\n" +
                "email: musu@ifoa.it<br>\n" +
                "tel. 0522–329.234<br>\n" +
                "<br>\n" +
                "<strong>Francesca Lusenti</strong><br>\n" +
                "<i>Linea Apprendistato</i><br>\n" +
                "email: lusenti@ifoa.it<br>\n" +
                "tel. 0522–329.128<br>\n" +
                "<br>\n" +
                "<strong>Fabiana Biccirè</strong><br>\n" +
                "<i>Linea Sicurezza sui luoghi di lavoro</i><br>\n" +
                "email: biccire@ifoa.it<br>\n" +
                "tel. 0522–329.251<br>\n" +
                "<br>\n" +
                "<strong>Samantha Ballerini</strong><br>\n" +
                "<i>Linea Piani Formativi Aziendali e Fondi Interprofessionali</i><br>\n" +
                "email: ballerini@ifoa.it<br>\n" +
                "tel. 0522–329.303<br>\n" +
                "<br>\n" +
                "<strong>Francesco Buzzoni</strong><br>\n" +
                "<i>Linea Informatica e ICT per le Aziende</i><br>\n" +
                "email: buzzoni@ifoa.it<br>\n" +
                "tel. 0522–329.379<br>\n" +
                "<br>\n" +
                "<strong>Cosetta Soragni</strong><br>\n" +
                "<i>Linea Master</i><br>\n" +
                "email: soragni@ifoa.it<br>\n" +
                "tel. 0522–329.305<br>\n" +
                "<br>\n" +
                "<strong>Elisa Braghiroli</strong><br>\n" +
                "<i>Linea Post Diploma</i><br>\n" +
                "email: braghiroli@ifoa.it<br>\n" +
                "tel. 0522–329.247";
        prodotti.add(sourceString);

        List<String> tematiche = new ArrayList<String>();
        sourceString = "<strong>Alessandra Nironi</strong><br>\n" +
                "<i>Nucleo E-Learning</i><br>\n" +
                "email: nironi@ifoa.it<br>\n" +
                "tel. 0522–329.287<br>\n" +
                "<br>\n" +
                "<strong>Stefania Cocorullo</strong><br>\n" +
                "<i>Nucleo Servizi per il Lavoro – Ricerca e Selezione</i><br>\n" +
                "email: cocorullo@ifoa.it<br>\n" +
                "tel. 0522–329.224<br>\n" +
                "<br>\n" +
                "<strong>Ornella Trombino</strong><br>\n" +
                "<i>Nucleo Operations, Organizzazione e Processi Aziendali, Marketing, Vendite e Commercio Internazionale, Informatica e ICT</i><br>\n" +
                "email: trombino@ifoa.it<br>\n" +
                "tel. 0522–329.240<br>\n" +
                "<br>\n" +
                "<strong>Fabiana Biccirè</strong><br>\n" +
                "<i>Nucleo Agro-Alimentare, GDO, Ambiente, Energia e Sicurezza sul lavoro</i><br>\n" +
                "email: biccire@ifoa.it<br>\n" +
                "tel. 0522–329.251<br>\n" +
                "<br>\n" +
                "<strong>Vito Brugnola</strong><br>\n" +
                "<i>Nucleo Turismo e Ristorazione</i><br>\n" +
                "email: brugnola@ifoa.it<br>\n" +
                "tel. 080-5966511";
        tematiche.add(sourceString);

        List<String> progetti = new ArrayList<String>();
        sourceString = "<strong>Luca Boetti</strong><br>\n" +
                "<i>Progetti Europei</i><br>\n" +
                "email: boetti@ifoa.it<br>\n" +
                "tel. 0522–329.273<br>\n" +
                "<br>\n" +
                "<strong>Giuseppina Scardaci</strong><br>\n" +
                "<i>Progetti Internazionali</i><br>\n" +
                "email: scardaci@ifoa.it<br>\n" +
                "tel. 0522–329.333<br>\n" +
                "<br>\n" +
                "<strong>Alessandra Nironi</strong><br>\n" +
                "<i>Progetti direzionali</i><br>\n" +
                "email: nironi@ifoa.it<br>\n" +
                "tel. 0522–329.287<br>\n" +
                "<br>\n" +
                "<strong>Narciso Coloretti</strong><br>\n" +
                "<i>Progetti Sviluppo Nuove Aree Paesi terzi</i><br>\n" +
                "email: n.coloretti@ifoa.it<br>\n" +
                "tel. 0522–329.237";
        progetti.add(sourceString);

        List<String> sedi = new ArrayList<String>();
        sourceString = "<strong>Ornella Trombino</strong><br>\n" +
                "<i>Area Reggio Emilia, Parma, Modena</i><br>\n" +
                "email: trombino@ifoa.it<br>\n" +
                "tel. 0522–329.240<br>\n" +
                "<br>\n" +
                "<strong>Nadia Nannini</strong><br>\n" +
                "<i>Area Bologna</i><br>\n" +
                "email: nannini@ifoa.it<br>\n" +
                "tel. 051-368652<br>\n" +
                "<br>\n" +
                "<strong>Francesco Buzzoni</strong><br>\n" +
                "<i>Area Lombardia</i><br>\n" +
                "email: buzzoni@ifoa.it<br>\n" +
                "tel. 02-26705056<br>\n" +
                "<br>\n" +
                "<strong>Monia Farinazzo</strong><br>\n" +
                "<i>Sede di Mantova</i><br>\n" +
                "email: farinazzo@ifoa.it<br>\n" +
                "<br>\n" +
                "<strong>Marco Correggi</strong><br>\n" +
                "<i>Area Veneto</i><br>\n" +
                "email: correggi@ifoa.it<br>\n" +
                "tel. 049-8698662<br>\n" +
                "<br>\n" +
                "<strong>Rita Menichetti</strong><br>\n" +
                "<i>Area Toscana</i><br>\n" +
                "email: menichetti@ifoa.it<br>\n" +
                "tel. 055-3245306<br>\n" +
                "<br>\n" +
                "<strong>Vito Brugnola</strong><br>\n" +
                "<i>Area Puglia e Basilicata</i><br>\n" +
                "email: brugnola@ifoa.it<br>\n" +
                "tel. 080-5966511";
        sedi.add(sourceString);
        listDataChild.put(listDataHeader.get(0), presidente); // Header, Child data
        listDataChild.put(listDataHeader.get(1), direzione);
        listDataChild.put(listDataHeader.get(2), servizi);
        listDataChild.put(listDataHeader.get(3), prodotti);
        listDataChild.put(listDataHeader.get(4), tematiche);
        listDataChild.put(listDataHeader.get(5), progetti);
        listDataChild.put(listDataHeader.get(6), sedi);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        if (user == null){
            position ++;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.home_section);
                contentHome.setVisibility(View.VISIBLE);
                contentContatti.setVisibility(View.GONE);
                contentSedi.setVisibility(View.GONE);
                contentProfilo.setVisibility(View.GONE);
                break;
            case 2:
                mTitle = getString(R.string.contatti_section);
                contentHome.setVisibility(View.GONE);
                contentContatti.setVisibility(View.VISIBLE);
                contentSedi.setVisibility(View.GONE);
                contentProfilo.setVisibility(View.GONE);
                break;
            case 3:
                mTitle = getString(R.string.sedi_section);
                contentHome.setVisibility(View.GONE);
                contentContatti.setVisibility(View.GONE);
                contentSedi.setVisibility(View.VISIBLE);
                contentProfilo.setVisibility(View.GONE);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        if (mNavigationDrawerFragment.getmTitle() != ""){
            actionBar.setTitle(mNavigationDrawerFragment.getmTitle());
            mNavigationDrawerFragment.setmTitle("");
        }else{
            actionBar.setTitle(mTitle);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemLogin = menu.findItem(R.id.action_login);
        MenuItem itemLogout = menu.findItem(R.id.action_logout);

        ParseUser user = ParseUser.getCurrentUser();

        if(user==null){
            itemLogin.setVisible(true);
            itemLogout.setVisible(false);
        }else{
            itemLogin.setVisible(false);
            itemLogout.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }

        if (item.getItemId() == R.id.action_logout){
            DialogFragment logoutDialogFragment = new LogoutDialogFragment();
            logoutDialogFragment.show(getFragmentManager(), "logout");
            return true;
        }

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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
