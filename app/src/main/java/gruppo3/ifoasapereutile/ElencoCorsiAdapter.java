package gruppo3.ifoasapereutile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Luca on 30/03/2015.
 */
public class ElencoCorsiAdapter extends ArrayAdapter<ParseObject> {

    public ElencoCorsiAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = layoutInflater.inflate(R.layout.row_elenco_corsi, null);

        TextView nomeCorsoTextView = (TextView) rowLayout.findViewById(R.id.txtNomeCorso);
        TextView areaTematicaTextView = (TextView) rowLayout.findViewById(R.id.txtAreaTematica);

        ParseObject corso = getItem(position);
        ParseObject areaTematica = corso.getParseObject("areaTematica");
        try {
            nomeCorsoTextView.setText(corso.fetchIfNeeded().getString("nomeCorso"));
            areaTematicaTextView.setText(areaTematica.fetchIfNeeded().getString("nomeAreaTematica"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rowLayout;

    }
}
