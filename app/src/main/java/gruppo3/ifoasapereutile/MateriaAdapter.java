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
public class MateriaAdapter extends ArrayAdapter<ParseObject> {

    public MateriaAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = layoutInflater.inflate(R.layout.row_materia, null);

        TextView nomeMateriaTextView = (TextView) rowLayout.findViewById(R.id.txtNomeMateria);
        TextView oreMateriaTextView = (TextView) rowLayout.findViewById(R.id.txtOreMateria);

        ParseObject docenteInsegna = getItem(position);
        try {
            nomeMateriaTextView.setText("Materia: " + docenteInsegna.fetchIfNeeded().getString("materia"));
            oreMateriaTextView.setText("Ore Totali: " + docenteInsegna.fetchIfNeeded().getInt("numeroOre"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rowLayout;

    }
}
