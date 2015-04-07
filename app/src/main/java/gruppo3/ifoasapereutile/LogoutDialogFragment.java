package gruppo3.ifoasapereutile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;

/**
 * Created by Luca on 12/03/2015.
 */
public class LogoutDialogFragment extends DialogFragment {

    Activity activity;
    Toast toast;
    LogoutTask task;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        activity = getActivity();
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        task = new LogoutTask();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Sei Sicuro?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Logout
                        task.execute();
                    }
                })
                .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private class LogoutTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            toast.setText("Utente sloggato");
            toast.show();
            activity.invalidateOptionsMenu();
            Intent i = new Intent(activity, RefreshNavigationDrawerActivity.class);
            startActivity(i);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseUser.logOut();
            return null;
        }
    }

}

