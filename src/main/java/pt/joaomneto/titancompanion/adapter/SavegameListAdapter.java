package pt.joaomneto.titancompanion.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pt.joaomneto.titancompanion.LoadAdventureActivity;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.consts.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SavegameListAdapter extends ArrayAdapter<Savegame> implements View.OnCreateContextMenuListener {

    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final Context context;
    private final List<Savegame> values;
    private final Map<String, Integer> gamebookNameToNumber = new HashMap<>();
    private LoadAdventureActivity adv;

    public SavegameListAdapter(Context context, List<Savegame> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.adv = (LoadAdventureActivity) context;


        Resources enResources = Constants.getLocalizedResources(context, new Locale("en"));
        String[] gamebookNames = enResources.getStringArray(
                R.array.gamebook_list_names);

        int number = 0;


        //TODO refactor this
        for (String name : gamebookNames) {
            number++;

            String prefix = null;

            if (name.equals(enResources.getString(R.string.spectral))) {
                prefix = "spectral";
            } else if (name.equals(enResources.getString(R.string.tower))) {
                prefix = "tower";
            } else if (name.equals(enResources.getString(R.string.siege))) {
                prefix = "siege";
            } else if (name.equals(enResources.getString(R.string.moon))) {
                prefix = "moon";
            } else if (name.equals(enResources.getString(R.string.strider))) {
                prefix = "strider";
            } else {
                String[] tokens = name.split("\\ ");

                prefix = "";

                for (String string : tokens) {
                    prefix += string.charAt(0);
                }
            }

            gamebookNameToNumber.put(prefix.toLowerCase(), number);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View robotView = inflater.inflate(R.layout.component_load_dventure, parent, false);

        final TextView nameValue = robotView.getRootView().findViewById(R.id.nameValue);
        final TextView gamebookValue = robotView.getRootView().findViewById(R.id.gamebookValue);
        final TextView dateValue = robotView.getRootView().findViewById(R.id.dateValue);
        final ImageView gamebookIcon = robotView.getRootView().findViewById(R.id.gamebookIcon);

        final String value = values.get(position).getFilename();

        String[] tokens = value.split("_");

        nameValue.setText(tokens[2]);

        int gamebookNameId = adv.getResources().getIdentifier(tokens[1].toLowerCase(), "string", adv.getApplicationContext().getPackageName());
        int gamebookCoverId = adv.getResources().getIdentifier("ff" + gamebookNameToNumber.get(tokens[1].toLowerCase()), "drawable", adv.getApplicationContext().getPackageName());

        gamebookValue.setText(gamebookNameId);
        dateValue.setText(df.format(values.get(position).getLastUpdated()));
        gamebookIcon.setImageResource(gamebookCoverId);

        return robotView;
    }


    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        System.out.println();
    }

}
