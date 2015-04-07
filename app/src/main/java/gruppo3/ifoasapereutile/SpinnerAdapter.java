package gruppo3.ifoasapereutile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.HashMap;
import java.util.List;

import gruppo3.ifoasapereutile.Model.KeyValue;

/**
 * Created by Luca on 02/04/2015.
 */
public class SpinnerAdapter extends ArrayAdapter<KeyValue> {

    public SpinnerAdapter(Context context, int resource, List<KeyValue> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = layoutInflater.inflate(R.layout.row_area_tematica, null);

        TextView nomeAreaTematicaTextView = (TextView) rowLayout.findViewById(R.id.txtAreaTematica);

        KeyValue areaTematica = getItem(position);
        nomeAreaTematicaTextView.setText(areaTematica.getValue());

        return rowLayout;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = layoutInflater.inflate(R.layout.row_area_tematica, null);

        TextView nomeAreaTematicaTextView = (TextView) rowLayout.findViewById(R.id.txtAreaTematica);

        KeyValue areaTematica = getItem(position);
        nomeAreaTematicaTextView.setText(areaTematica.getValue());

        return rowLayout;

    }

}
