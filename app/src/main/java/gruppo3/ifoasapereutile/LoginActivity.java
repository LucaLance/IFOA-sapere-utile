package gruppo3.ifoasapereutile;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends ActionBarActivity {

    EditText txtEmail, txtPassword;
    Button btnLogin;

    LoginTask task;

    AlertDialog dialog;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        task = new LoginTask();

        ProgressBar progressBar = new ProgressBar(getApplicationContext());
        final LinearLayout layout = new LinearLayout(getApplicationContext());
        progressBar.setPadding(0, 30, 0, 30);
        layout.setHorizontalGravity(1);
        layout.addView(progressBar);
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Login in corso");
        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                if (task.getStatus() == AsyncTask.Status.RUNNING) {
                    task.cancel(true);
                    task = new LoginTask();
                }
                if (ParseUser.getCurrentUser()!=null){
                    ParseUser.logOut();
                }
            }
        });
        builder.setView(layout);
        dialog = builder.create();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = txtEmail.getText().toString();
                final String password = txtPassword.getText().toString();

         //       if (isEmailValid(email)) {
                    task.execute(email, password);
         /*       }else{
                    toast.setText("Formato mail non corretto");
                    toast.show();
                }*/

            }
        });

    }

    private class LoginTask extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String email = params[0];
            String password = params[1];

                try {
                    Thread.sleep(1000);
                    ParseUser.logIn(email, password);
                } catch (ParseException e) {
                    toast.setText("Combinazione email password non valida");
                    toast.show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.cancel();
            if (ParseUser.getCurrentUser()!=null) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("codice", 0);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }

    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
 /*       if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

}
