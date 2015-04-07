package gruppo3.ifoasapereutile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Luca on 17/03/2015.
 */
public class EdizioneCorsoAdapter extends ArrayAdapter<ParseObject> {

    public EdizioneCorsoAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = layoutInflater.inflate(R.layout.row_corso, null);

        TextView nomeCorsoTextView = (TextView) rowLayout.findViewById(R.id.txtNomeCorso);
        ImageView imgStato = (ImageView) rowLayout.findViewById(R.id.imgStato);

        ParseObject edizioneCorso = getItem(position);
        ParseObject corso = null;
        if (edizioneCorso.getDate("dataInizio").after(new Date())){
            imgStato.setImageResource(R.drawable.yellow_cerchio);
        }else if (edizioneCorso.getDate("dataFine").after(new Date())){
            imgStato.setImageResource(R.drawable.green_cerchio);
        }else if (edizioneCorso.getDate("dataFine").before(new Date())){
            imgStato.setImageResource(R.drawable.red_cerchio);
        }

        try {
            corso = edizioneCorso.fetchIfNeeded().getParseObject("fkIdCorso");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            nomeCorsoTextView.setText(corso.fetchIfNeeded().getString("nomeCorso"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rowLayout;

    }
}
