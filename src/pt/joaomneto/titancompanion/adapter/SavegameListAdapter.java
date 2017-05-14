package pt.joaomneto.titancompanion.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.joaomneto.titancompanion.LoadAdventureActivity;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.RCAdventure;
import pt.joaomneto.titancompanion.adventure.impl.fragments.rc.Robot;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class SavegameListAdapter extends ArrayAdapter<Savegame> implements View.OnCreateContextMenuListener {

    private final Context context;
    private final List<Savegame> values;
    private LoadAdventureActivity adv;


    private final Map<String, Integer> gamebookNameToNumber = new HashMap<>();

    private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public SavegameListAdapter(Context context, List<Savegame> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.adv = (LoadAdventureActivity) context;


        String[] gamebookNames = context.getResources().getStringArray(
                R.array.gamebook_list_names);

        int number = 0;

        for (String name: gamebookNames) {
            number++;

            String prefix = null;

            if(name.equals("Spectral Stalkers")){
                prefix = "spectral";
            }else if (name.equals("Tower of Destruction")){
                prefix = "tower";
            }else if (name.equals("Siege of Sardath")){
                prefix = "siege";
            }else if (name.equals("Moonrunner")){
                prefix = "moon";
            }else if (name.equals("Star Strider")){
                prefix = "star";
            }
            else {
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

        final TextView nameValue = (TextView) robotView.getRootView().findViewById(R.id.nameValue);
        final TextView gamebookValue = (TextView) robotView.getRootView().findViewById(R.id.gamebookValue);
        final TextView dateValue = (TextView) robotView.getRootView().findViewById(R.id.dateValue);
        final ImageView gamebookIcon = (ImageView) robotView.getRootView().findViewById(R.id.gamebookIcon);

        final String value = values.get(position).getFilename();

        String[] tokens = value.split("_");

        nameValue.setText(tokens[2]);

        int gamebookNameId = adv.getResources().getIdentifier(tokens[1].toLowerCase(), "string", adv.getApplicationContext().getPackageName());
        int gamebookCoverId = adv.getResources().getIdentifier("ff"+ gamebookNameToNumber.get(tokens[1].toLowerCase()), "drawable", adv.getApplicationContext().getPackageName());

        gamebookValue.setText(gamebookNameId);
        dateValue.setText(df.format(values.get(position).getLastUpdated()));
        gamebookIcon.setImageResource(gamebookCoverId);

        return robotView;
    }


    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        System.out.println();
    }

}
