package gruppo3.ifoasapereutile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;


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

    RelativeLayout contentHome, contentChiSiamo, contentSedi;

    Button btnInformatica, btnIfoa;

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
        contentChiSiamo = (RelativeLayout) findViewById(R.id.contentChiSiamo);
        contentSedi = (RelativeLayout) findViewById(R.id.contentSedi);

        btnIfoa = (Button) findViewById(R.id.btnIfoa);
        btnInformatica = (Button) findViewById(R.id.btnInformatica);

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

        txtDialogIfoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogIfoa.cancel();
            }
        });

        Intent returnIntent = getIntent();
        int section = returnIntent.getIntExtra("codice", 0);
        if (section != 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(section))
                    .commit();
        }


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
        switch (number) {
            case 1:
                mTitle = getString(R.string.home_section);
                contentHome.setVisibility(View.VISIBLE);
                contentChiSiamo.setVisibility(View.GONE);
                contentSedi.setVisibility(View.GONE);
                break;
            case 2:
                mTitle = getString(R.string.chi_siamo_section);
                contentHome.setVisibility(View.GONE);
                contentChiSiamo.setVisibility(View.VISIBLE);
                contentSedi.setVisibility(View.GONE);
                break;
            case 3:
                mTitle = getString(R.string.sedi_section);
                contentHome.setVisibility(View.GONE);
                contentChiSiamo.setVisibility(View.GONE);
                contentSedi.setVisibility(View.VISIBLE);
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
            DialogFragment loginDialogFragment = new LoginDialogFragment();
            loginDialogFragment.show(getFragmentManager(), "login");
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
