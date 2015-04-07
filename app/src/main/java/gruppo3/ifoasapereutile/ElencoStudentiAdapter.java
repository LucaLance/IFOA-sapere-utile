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
public class ElencoStudentiAdapter extends ArrayAdapter<ParseObject> {

    public ElencoStudentiAdapter(Context context, int resource, List<ParseObject> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = layoutInflater.inflate(R.layout.row_studente, null);

        TextView nomeStudenteTextView = (TextView) rowLayout.findViewById(R.id.txtNomeStudente);

        ParseObject user = getItem(position);

        try {
            nomeStudenteTextView.setText(user.fetchIfNeeded().getString("firstName") + " " + user.fetchIfNeeded().getString("lastName"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rowLayout;

    }
}
