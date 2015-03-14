package gruppo3.ifoasapereutile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;

import java.text.ParseException;

/**
 * Created by Luca on 12/03/2015.
 */
public class LoginDialogFragment extends DialogFragment {

    EditText txtEmail, txtPassword;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View loginDialogView = inflater.inflate(R.layout.login_dialog, null);

        txtEmail = (EditText) loginDialogView.findViewById(R.id.txtEmail);
        txtPassword = (EditText) loginDialogView.findViewById(R.id.txtPassword);
        final Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        final Activity activity = getActivity();

        builder.setView(loginDialogView)
                .setPositiveButton("Accedi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String email = txtEmail.getText().toString();
                        final String password = txtPassword.getText().toString();

                        ParseUser.logInInBackground(email, password, new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, com.parse.ParseException e) {
                                if (e == null) {
                                    toast.setText("login riuscito");
                                    toast.show();
                                    activity.invalidateOptionsMenu();
                                } else {
                                    toast.setText("Unable to login: " + e.getMessage());
                                    toast.show();
                                }
                            }
                        });
                        return;
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
}
