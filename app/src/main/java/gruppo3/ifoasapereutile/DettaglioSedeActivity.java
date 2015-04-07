package gruppo3.ifoasapereutile;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class DettaglioSedeActivity extends ActionBarActivity {

    TextView txtNomeSede, txtDescrizione, txtTelefono, txtEmail;
    Button btnChiama, btnMail;

    String[] mail;
    String telefono;

    GoogleMap mapSede;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_sede);

        txtNomeSede = (TextView) findViewById(R.id.txtTitle);
        txtDescrizione = (TextView) findViewById(R.id.txtDescrizione);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        btnChiama = (Button) findViewById(R.id.btnChiama);
        btnMail = (Button) findViewById(R.id.btnMail);

        mapSede = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapSede)).getMap();

        intent = getIntent();

        switch (intent.getStringExtra("sede")){
            case "mantova":
                txtNomeSede.setText("Mantova");
                txtDescrizione.setText("IFOA ha riaperto una propria sede a Mantova nel 2010.\n" +
                        "\n" +
                        "Per stare più vicino alle aziende ed alle persone che da sempre serviamo, magari con progetti ad hoc o presso le nostre sedi emiliane.\n" +
                        "\n" +
                        "Presso IFOA Mantova si possono utilizzare la Dote lavoro e la Dote ricollocazione e riqualificazione della Regione o trovare altre opportunità formative finanziate.\n" +
                        "\n" +
                        "Indipendentemente dai finanziamenti, la sede IFOA di Mantova offre corsi a catalogo, la migliore occasione per stare al passo con le conoscenze professionali necessarie e sempre aggiornate.\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di MANTOVA\n" +
                        "\n" +
                        "Indirizzo Via Imre Nagy, 21 - Borgochiesanuova – 46100 Mantova\n" +
                        "Fax +39 0376 263501");
                txtTelefono.setText("Telefono +39 0376 263650");
                txtEmail.setText("Email ifoamantova@ifoa.it");
                mail = new String[]{"ifoamantova@ifoa.it"};
                telefono = "tel:+39 0376 263650";
                if (mapSede!=null){
                    LatLng latLng = new LatLng(45.1506727, 10.7685477);
                    Marker mrkSede = mapSede.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("IFOA")
                            .snippet("Sede IFOA")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_sede)));
                    mapSede.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                break;
            case "milano":
                txtNomeSede.setText("Milano");
                txtDescrizione.setText("IFOA è presente con una sede a Milano dal 1997. La sede è in centro città, in via Olmetto a pochi passi da piazza Missori (MM 3 - Linea Gialla) e via Torino e poco più in là c’è il Duomo.\n" +
                        "\n" +
                        "La sede IFOA di Milano ha come principale obiettivo quello di lavorare con e per le aziende del territorio, al fine di sostenerne ed aumentarne la competitività sui mercati nazionali ed internazionali, supportandone le direzioni Human Resources in ogni loro necessità.\n" +
                        "\n" +
                        "Da sempre la sede IFOA di Milano è anche a servizio dei cittadini che vogliono aggiornarsi e riqualificarsi: formiamo persone e proponiamo percorsi di inserimento in azienda. \n" +
                        "\n" +
                        "Offriamo opportunità ai neo diplomati e neo laureati in cerca della prima occupazione, come a coloro che cercano un lavoro o che rischiano di perderlo:\n" +
                        "\n" +
                        "La forza di IFOA è coniugare i finanziamenti pubblici (es. Dote lavoro, Dote ricollocazione e riqualificazione, Legge 236...) ed aziendali (Fondi Interprofessionali) con le necessità dei nostri clienti...insomma quando possibile i nostri servizi sono gratuiti o quasi per il cliente.\n" +
                        "\n" +
                        "Alcune delle nostre aree di eccellenza sono: formazione in campo ICT & Management, formazione obbligatoria per gli apprendisti, ricerca e selezione ed attivazione di tirocini formativi presso le imprese.\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di MILANO\n" +
                        "\n" +
                        "Indirizzo Via Olmetto 5 – 20123 Milano\n" +
                        "Fax +39 0226705060");
                txtTelefono.setText("Telefono +39 0226705056");
                txtEmail.setText("Email ifoalombardia@ifoa.it");
                mail = new String[]{"ifoalombardia@ifoa.it"};
                telefono = "tel:+39 0226705056";
                break;
            case "padova":
                txtNomeSede.setText("Padova");
                txtDescrizione.setText("IFOA area Veneto è presente con una sede a Padova dal 2003 ed ha come principale obiettivo quello di lavorare con le aziende del Veneto ed in particolare del Nord-Est, su percorsi di formazione e consulenza, affiancando e supportando le direzioni Human Resources al fine di aumentarne la competitività delle imprese sia sui mercati nazionali che internazionali.\n" +
                        "\n" +
                        "IFOA area Veneto gestisce anche un punto di presenza a Trento in via Brennero, 248 con l’obiettivo di servire il territorio del Trentino-Alto Adige.\n" +
                        "\n" +
                        "IFOA area Veneto è anche al servizio dei cittadini che vogliono aggiornarsi e riqualificarsi: formiamo persone e proponiamo percorsi di inserimento in azienda. Offriamo opportunità di crescita professionale sia a neo diplomati che laureati in cerca della prima occupazione e a tutti coloro che cercano un lavoro o che rischiano di perderlo.\n" +
                        "\n" +
                        "IFOA Veneto è specializzata nell’utilizzo di finanziamenti pubblici (Fondi Interprofessionali, corsi FSE, Voucher Regionali,…) per erogare formazione a cittadini e lavoratori dipendenti a costi agevolati e talvolta gratuiti.\n" +
                        "\n" +
                        "Alcune delle aree di eccellenza di IFOA Veneto sono la formazione in ambito Organizzazione Aziendale, Qualità, Sicurezza, Ambiente, ICT, Marketing e vendite, Gestione risorse umane.\n" +
                        "\n" +
                        "A questo si aggiunge una nostra elevata specializzazione nella formazione “on-the-job” per gli apprendisti, soprattutto per le aziende della Grande Distribuzione Organizzata, e un’esperienza consolidata nella ricerca e selezione e nella attivazione di tirocini formativi presso le imprese.\n" +
                        "\n" +
                        "Formazione Continua, Formazione Superiore e Orientamento - IFOA è organismo accreditato dalla Regione Veneto (nr. A0176 - L.R. 19/2002)\n" +
                        "\n" +
                        "Servizi al Lavoro - IFOA è Agenzia accreditata dalla Regione Veneto (nr. L070)\n" +
                        "\n" +
                        "IFOA è autorizzata ad operare in qualità di Agenzia per il lavoro autorizzata ex Art. 6 D.Lgs 276/2003 (come integrato dall'art. 29 L. 111/2011 - Cod. Intermediario H223S001239 Albo informatico - www.clicklavoro.gov.it)\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di PADOVA\n" +
                        "\n" +
                        "Indirizzo Via Giovanni Savelli, 8 – 35129 Padova\n" +
                        "Fax +39 049 8252064");
                txtTelefono.setText("Telefono +39 049 8698662");
                txtEmail.setText("Email ifoaveneto@ifoa.it");
                mail = new String[]{"ifoaveneto@ifoa.it"};
                telefono = "tel:+39 049 8698662";
                break;
            case "bologna":
                txtNomeSede.setText("Bologna");
                txtDescrizione.setText("La sede operativa di Bologna è attiva dal 1979.\n" +
                        " \n" +
                        "Nel tempo ha realizzato moltissime attività che vanno dalla più tradizionale formazione post diploma e post laurea, fino alla formazione dedicata alle aziende ed agli adulti.\n" +
                        "Le azioni formative sono state principalmente focalizzate sui temi dell’innovazione tecnico/informatica, della sicurezza, del marketing e della GDO.\n" +
                        "La sede IFOA di Bologna è Local Academy di Cisco dal 2000.\n" +
                        " \n" +
                        "All’attività formativa, dal 2005 si è aggiunto il sevizio rivolto alle aziende di attivazione tirocini formativi e dal 2007 la formazione dedicata agli apprendisti.\n" +
                        " \n" +
                        "La sede organizza inoltre dal 2008, in collaborazione con UnionCamere Regionale, il Piano Formativo dedicato alle nove CCIAA della Regione Emilia Romagna.\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di BOLOGNA\n" +
                        "\n" +
                        "Indirizzo Via A. Calzoni 6/d – 40128 Bologna\n" +
                        "Fax +39 051 362608");
                txtTelefono.setText("Telefono +39 051 368652");
                txtEmail.setText("Email ifoabologna@ifoa.it");
                mail = new String[]{"ifoabologna@ifoa.it"};
                telefono = "tel:+39 051 368652";
                break;
            case "parma":
                txtNomeSede.setText("Parma");
                txtDescrizione.setText("La presenza stabile di IFOA sul territorio di Parma è datata 1996.\n" +
                        "\n" +
                        "L'iniziale specializzazione della sede era centrata sui contenuti e sulle collaborazioni specialistiche in ambito Agro-alimentare.\n" +
                        "\n" +
                        "Negli ultimi anni la sede IFOA di Parma afferisce alla responsabilità del Nucleo delle Competenze - Area Reggio Emilia Modena Parma,  territori distinti ma per i quali sono state sviluppate sinergie di linea e di prodotto che veicolano le migliori pratiche e le specializzazioni core di IFOA, affiancando a quelle di base, interventi e profili specialistici ICT, Gestionali, Commerciali, Industriali, in linea con le esigenze del territorio.\n" +
                        "\n" +
                        "In particolare si posizionano sul territorio di Parma, le seguenti linee:\n" +
                        "\n" +
                        "- Sviluppo di piani formativi aziendali\n" +
                        "- Tirocini formativi\n" +
                        "- Apprendistato\n" +
                        "- Formazione continua\n" +
                        "- Formazione superiore\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di PARMA\n" +
                        "\n" +
                        "Indirizzo Via dei Mercati, 9/B – 43126 Parma\n" +
                        "Fax +39 0521 945014");
                txtTelefono.setText("Telefono +39 0521 942800");
                txtEmail.setText("Email ifoaparma@ifoa.it");
                mail = new String[]{"ifoaparma@ifoa.it"};
                telefono = "tel:+39 0521 942800";
                break;
            case "reggioemilia":
                txtNomeSede.setText("Reggio Emilia");
                txtDescrizione.setText("IFOA nasce a Reggio Emilia circa quaranta anni or sono come emanazione della Camera di Commercio e a Reggio Emilia IFOA ha tuttora il suo quartier generale e la principale delle sue oltre 10 sedi.\n" +
                        "\n" +
                        "Attore riconosciuto e qualificato di un’offerta formativa molto articolata, alle aziende e alle persone, IFOA offre molteplici opportunità:\n" +
                        "dalla tradizionale capacità di proporre corsi post diploma e post laurea per i giovani, fornendo le competenze utili ad accedere ad un posto di lavoro qualificato; dai corsi per gli operatori aziendali in tutte le modalità (mediante il sostegno del Fondo Sociale Europeo, con i fondi interprofessionali o con modalità strettamente commerciale) in area ICT, Sicurezza, e più in generale in tutte le aree manageriali, fino alla qualificata gestione di vere e proprie scuole aziendali.\n" +
                        "\n" +
                        "I servizi per l’accesso al lavoro mediante tirocini e apprendistato ed i progetti internazionali a beneficio degli attori locali vengono a completare il complesso dell’offerta IFOA a Reggio Emilia.\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di REGGIO EMILIA (sede legale)\n" +
                        "\n" +
                        "Indirizzo Via Gianna Giglioli Valle, 11 – 42124 Reggio Emilia\n" +
                        "Fax +39 0522 284708");
                txtTelefono.setText("Telefono +39 0522 329111");
                txtEmail.setText("Email ifoa@ifoa.it");
                mail = new String[]{"ifoa@ifoa.it"};
                telefono = "tel:+39 0522 329111";
                if (mapSede!=null){
                    LatLng latLng = new LatLng(44.71380, 10.62258);
                    Marker mrkSede = mapSede.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("IFOA")
                            .snippet("Sede IFOA")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_sede)));
                    mapSede.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                break;
            case "modena":
                txtNomeSede.setText("Modena");
                txtDescrizione.setText("La presenza stabile di IFOA sul territorio di Modena è datata 1999, anche se già da alcuni anni prima IFOA aveva iniziato a sviluppare azioni indirizzate ad imprese e ad utenti modenesi, utilizzando punti di presenza o strutture delle provincie attigue.\n" +
                        "\n" +
                        "Negli ultimi anni la sede IFOA di Modena afferisce alla responsabilità del Nucleo delle Competenze - Area Reggio Emilia Modena Parma,  territori distinti ma per i quali sono state sviluppate sinergie di linea e di prodotto che veicolano le migliori pratiche e le specializzazioni core di IFOA.\n" +
                        "\n" +
                        "In particolare si posizionano sul territorio modenese, le seguenti linee:\n" +
                        "\n" +
                        "- Sviluppo di piani formativi aziendali\n" +
                        "- Tirocini formativi\n" +
                        "- Apprendistato\n" +
                        "- Formazione continua\n" +
                        "- Formazione superiore.\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di MODENA\n" +
                        "\n" +
                        "Indirizzo Strada Saliceto Panaro, 5 – 41122 Modena\n" +
                        "Fax +39 059 2923658");
                txtTelefono.setText("Telefono +39 059 346616");
                txtEmail.setText("Email ifoamodena@ifoa.it");
                mail = new String[]{"ifoamodena@ifoa.it"};
                telefono = "tel:+39 059 346616";
                break;
            case "firenze":
                txtNomeSede.setText("Firenze");
                txtDescrizione.setText("La sede Toscana di IFOA nasce a Firenze nella primavera 2002 con l'obiettivo di implementare sul territorio del Centro Italia le progettualità e le competenze sviluppate nei decenni dalla sede centrale.\n" +
                        "\n" +
                        "Nel corso degli anni la vocazione di  business school a supporto dello sviluppo strategico delle Risorse Umane all'interno del sistema produttivo ed imprenditoriale ha consentito ad IFOA Toscana di mettere a punto una gamma di servizi altamente qualificati e personalizzati per le aziende di medio/grandi dimensioni.\n" +
                        "\n" +
                        "Le aree tematiche di maggior specializzazione di IFOA Toscana sono: Ambiente, energia – Sicurezza – GDO – Informatica e ICT – Turismo – Manifatturiero.\n" +
                        "\n" +
                        "Il posizionamento di IFOA Toscana sul territorio si caratterizza per la gestione di piani formativi complessi che coinvolgono tutti i livelli aziendali ed una serie di servizi a completamento, funzionali alla formazione ed all'inserimento di nuove risorse all'interno delle imprese.\n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di FIRENZE\n" +
                        "\n" +
                        "Indirizzo Via Leoncavallo, 15 int.2/3 – 50127 Firenze\n" +
                        "Fax +39 055331583");
                txtTelefono.setText("Telefono +39 0553245306");
                txtEmail.setText("Email ifoatoscana@ifoa.it");
                mail = new String[]{"ifoatoscana@ifoa.it"};
                telefono = "tel:+39 0553245306";
                break;
            case "napoli":
                txtNomeSede.setText("Napoli");
                txtDescrizione.setText("Linea Tirocini\n" +
                        "Rif. Serenella Musu\n" +
                        "Email ifoanet.na@ifoa.it\n" +
                        "\n" +
                        "Linea Apprendistato\n" +
                        "Rif. Daniela Cospito \n" +
                        "Email consulenza.apprendidtato@ifoa.it\n");
                txtTelefono.setText("Numero Verde 800 915108");
                telefono = "tel:800 915108";
                btnMail.setVisibility(View.GONE);
                break;
            case "bari":
                txtNomeSede.setText("Bari");
                txtDescrizione.setText("In Puglia dal 1994, IFOA si occupa di “Sapere Utile”, di innovazione delle imprese e inserimento professionale di giovani diplomati e laureati.\n" +
                        "\n" +
                        "IFOA Puglia opera da sempre a fianco di imprese sane che crescono, e con loro costruisce un concreto rapporto di partnership basato sul comune interesse per la crescita del Capitale Umano. \n" +
                        "Per questo motivo le azioni formative IFOA rivolte ai giovani si concludono con alte percentuali di inserimenti occupazionali.\n" +
                        "IFOA lavora assecondando le tradizionali vocazioni produttive del territorio e lo sviluppo competitivo nel settore del futuro, l’Information Technology. \n" +
                        "\n" +
                        "IFOA Puglia offre corsi medio lunghi di preparazione tecnica ed alta formazione per giovani, piani formativi aziendali, percorsi di general management per imprenditori ed operatori aziendali, ricerca e selezione del personale, tirocini formativi, corsi per apprendisti. \n" +
                        "Inoltre opera con i Fondi interprofessionali, finanziamenti regionali e provinciali e su commessa. \n" +
                        "\n" +
                        "In seguito all’accordo di solidarietà espansiva (previsto dall’art. 2 della legge 863/1984) firmato da Ifoa il 18 febbraio scorso, che prevede una riduzione dell’orario di lavoro del personale dipendente, il venerdì pomeriggio la nostra attività termina alle ore 16.\n" +
                        "\n" +
                        "Sede di BARI\n" +
                        "\n" +
                        "Indirizzo Via Vitantonio De Bellis, 7 – 70126 Bari\n" +
                        "Fax +39 0805966516");
                txtTelefono.setText("Telefono +39 0805966511");
                txtEmail.setText("Email ifoapuglia@ifoa.it");
                mail = new String[]{"ifoapuglia@ifoa.it"};
                telefono = "tel:+39 0805966511";
                break;
            case "cagliari":
                txtNomeSede.setText("Cagliari");
                txtDescrizione.setText("");
                txtTelefono.setText("Numero Verde 800 915108\n");
                txtEmail.setText("Email ifoasardegna@ifoa.it");
                mail = new String[]{"ifoasardegna@ifoa.it"};
                telefono = "tel:800 915108";
                break;
        }

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, mail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Informazioni");
                startActivity(intent);
            }
        });

        btnChiama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(telefono));
                startActivity(callIntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dettaglio_sede, menu);
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
