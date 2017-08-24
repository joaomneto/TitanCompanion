package pt.joaomneto.titancompanion.adventure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import pt.joaomneto.titancompanion.BaseFragmentActivity;
import pt.joaomneto.titancompanion.LoadAdventureActivity;
import pt.joaomneto.titancompanion.R;
import pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment;
import pt.joaomneto.titancompanion.util.DiceRoller;

public abstract class Adventure extends BaseFragmentActivity {

    protected static final int FRAGMENT_VITAL_STATS = 0;
    protected static final int FRAGMENT_COMBAT = 1;
    protected static final int FRAGMENT_EQUIPMENT = 2;
    protected static final int FRAGMENT_NOTES = 3;

    protected static SparseArray<Adventure.AdventureFragmentRunner> fragmentConfiguration = new SparseArray<Adventure.AdventureFragmentRunner>();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    StandardSectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    Integer initialSkill = -1;
    Integer initialLuck = -1;
    Integer initialStamina = -1;
    Integer currentSkill = -1;
    Integer currentLuck = -1;
    Integer currentStamina = -1;
    List<String> equipment = new ArrayList<String>();
    List<String> notes = new ArrayList<String>();
    Integer currentReference = -1;
    // Common values
    Integer standardPotion = -1;
    Integer gold = 0;
    Integer provisions = -1;
    Integer provisionsValue = -1;
    Integer standardPotionValue = -1;
    File dir = null;
    int gamebook = -1;
    String name = null;
    Properties savedGame;
    Map<Integer, Fragment> fragments = new HashMap<Integer, Fragment>();
    Toast lastToast = null;

    public Adventure() {
        super();
        fragmentConfiguration.put(FRAGMENT_VITAL_STATS, new AdventureFragmentRunner(R.string.vitalStats,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureVitalStatsFragment"));
        fragmentConfiguration.put(FRAGMENT_COMBAT, new AdventureFragmentRunner(R.string.fights,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureCombatFragment"));
        fragmentConfiguration.put(FRAGMENT_EQUIPMENT, new AdventureFragmentRunner(R.string.goldEquipment,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureEquipmentFragment"));
        fragmentConfiguration.put(FRAGMENT_NOTES, new AdventureFragmentRunner(R.string.notes,
                "pt.joaomneto.titancompanion.adventure.impl.fragments.AdventureNotesFragment"));

    }

    public static void showAlert(int title, int message, Context context) {
        showAlert(title, context.getString(message), context);
    }

    public static void showAlert(int title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title > 0 ? title : R.string.result).setMessage(message).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlert(View view, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.result).setView(view).setCancelable(false).setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String arrayToString(Collection<? extends Object> elements) {
        String _string = "";

        if (!elements.isEmpty()) {
            for (Object value : elements) {
                _string += value.toString() + "#";
            }
            _string = _string.substring(0, _string.length() - 1);
        }
        return _string;
    }

    public static void showAlert(int message, Context context) {
        showAlert(-1, message, context);
    }

    public static void showAlert(String message, Context context) {
        showAlert(-1, message, context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            // Create the adapter that will return a fragment for each of the
            // three
            // primary sections of the app.
            setContentView(R.layout.activity_adventure);
            mSectionsPagerAdapter = new StandardSectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = findViewById(R.id.pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            String fileName = getIntent().getStringExtra(LoadAdventureActivity.ADVENTURE_FILE);
            String relDir = getIntent().getStringExtra(LoadAdventureActivity.ADVENTURE_DIR);
            name = relDir;
            dir = new File(Environment.getExternalStorageDirectory().getPath() + "/ffgbutil/" + relDir);

            loadGameFromFile(dir, fileName);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create adventure", e);
        }
    }

    private void loadGameFromFile(File dir, String fileName) throws IOException {
        savedGame = new Properties();
        savedGame.load(new InputStreamReader(new FileInputStream(new File(dir, fileName)), "UTF-8"));

        gamebook = Integer.valueOf(savedGame.getProperty("gamebook"));
        initialSkill = Integer.valueOf(savedGame.getProperty("initialSkill"));
        initialLuck = Integer.valueOf(savedGame.getProperty("initialLuck"));
        initialStamina = Integer.valueOf(savedGame.getProperty("initialStamina"));
        currentSkill = Integer.valueOf(savedGame.getProperty("currentSkill"));
        currentLuck = Integer.valueOf(savedGame.getProperty("currentLuck"));
        currentStamina = Integer.valueOf(savedGame.getProperty("currentStamina"));

        String equipmentS = new String(savedGame.getProperty("equipment").getBytes(java.nio.charset.Charset.forName("UTF-8")));
        String notesS = new String(savedGame.getProperty("notes").getBytes(java.nio.charset.Charset.forName("UTF-8")));
        currentReference = Integer.valueOf(savedGame.getProperty("currentReference"));

        if (equipmentS != null) {
            equipment = new ArrayList<String>();
            List<String> list = Arrays.asList(equipmentS.split("#"));
            for (String string : list) {
                if (!string.isEmpty())
                    equipment.add(string);
            }
        }

        if (notesS != null) {
            notes = new ArrayList<String>();
            List<String> list = Arrays.asList(notesS.split("#"));
            for (String string : list) {
                if (!string.isEmpty())
                    notes.add(string);
            }
        }

        String provisionsS = getSavedGame().getProperty("provisions");
        provisions = provisionsS != null && !provisionsS.equals("null") ? Integer.valueOf(provisionsS) : null;
        String provisionsValueS = getSavedGame().getProperty("provisionsValue");
        provisionsValue = provisionsValueS != null && !provisionsValueS.equals("null") ? Integer.valueOf(provisionsValueS) : null;

        loadAdventureSpecificValuesFromFile();
    }

    protected abstract void loadAdventureSpecificValuesFromFile();

    private AdventureVitalStatsFragment getVitalStatsFragment() {
        AdventureVitalStatsFragment adventureVitalStatsFragment = (AdventureVitalStatsFragment) getFragments().get(
                FRAGMENT_VITAL_STATS);

        return adventureVitalStatsFragment;
    }

    public void testSkill(View v) {

        boolean result = DiceRoller.roll2D6().getSum() < currentSkill;

        int message = result ? R.string.success : R.string.failed;
        showAlert(message, this);
    }

    public void testLuck(View v) {

        boolean result = testLuckInternal();

        int message = result ? R.string.success : R.string.failed;
        showAlert(message, this);
    }

    public boolean testLuckInternal() {
        boolean result = DiceRoller.roll2D6().getSum() < currentLuck;

        setCurrentLuck(--currentLuck);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.adventure, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rolld6: {
                return displayRollXD6(1);
            }
            case R.id.roll2d6: {
                return displayRollXD6(2);
            }
            case R.id.roll3d6: {
                return displayRollXD6(3);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean displayRollXD6(int diceNumber) {

        Integer d1 = DiceRoller.rollD6();
        Integer d2 = 0;
        Integer d3 = 0;
        if (diceNumber > 1) {
            d2 = DiceRoller.rollD6();
        }
        if (diceNumber > 2) {
            d3 = DiceRoller.rollD6();
        }

        View toastView = getLayoutInflater().inflate(R.layout.d3toast, findViewById(R.id.d3ToastLayout));
        ImageView imageViewD1 = toastView.findViewById(R.id.d1);
        ImageView imageViewD2 = null;
        ImageView imageViewD3 = null;

        if (diceNumber > 1) {
            imageViewD2 = toastView.findViewById(R.id.d2);
        }

        if (diceNumber > 2) {
            imageViewD3 = toastView.findViewById(R.id.d3);
        }

        TextView resultView = toastView.findViewById(R.id.diceResult);

        Integer d1Id = getResources().getIdentifier("d6" + d1, "drawable", this.getPackageName());

        Integer d2Id = null;
        Integer d3Id = null;

        if (diceNumber > 1) {
            d2Id = getResources().getIdentifier("d6" + d2, "drawable", this.getPackageName());
        }

        if (diceNumber > 2) {
            d3Id = getResources().getIdentifier("d6" + d3, "drawable", this.getPackageName());
        }

        imageViewD1.setImageResource(d1Id);

        if (diceNumber > 1) {
            imageViewD2.setImageResource(d2Id);
        }

        if (diceNumber > 2) {
            imageViewD3.setImageResource(d3Id);
        }

        resultView.setText(" = " + (d1 + d2 + d3));

        showAlert(toastView, this);

        return true;
    }

    public void savepoint(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.currentReference);

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.requestFocus();
        alert.setView(input);

        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                try {
                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    String ref = input.getText().toString();
                    File file = new File(dir, ref + ".xml");

                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

                    storeValuesInFile(ref, bw);
                    storeNotesForRestart(dir);

                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });

        alert.show();
    }

    private String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        reader.close();
        return stringBuilder.toString();
    }

    private void storeNotesForRestart(File dir) throws IOException {

        String notesS = "";

        if (!notes.isEmpty()) {
            for (String note : notes) {
                notesS += note + "#";
            }
            notesS = notesS.substring(0, notesS.length() - 1);
        }

        String initialContent = readFile(new File(dir, "initial.xml"));
        initialContent = initialContent.replace("notes=", "notes=" + notesS);

        FileWriter fileWriter = new FileWriter(new File(dir, "initial_full_notes.xml"));
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(initialContent);

        bw.close();
        fileWriter.close();

    }

    public void storeValuesInFile(String ref, BufferedWriter bw) throws IOException {
        String equipmentS = "";
        String notesS = "";

        if (!notes.isEmpty()) {
            for (String note : notes) {
                notesS += note + "#";
            }
            notesS = notesS.substring(0, notesS.length() - 1);
        }

        if (!equipment.isEmpty()) {
            for (String eq : equipment) {
                equipmentS += eq + "#";
            }
            equipmentS = equipmentS.substring(0, equipmentS.length() - 1);
        }
        bw.write("gamebook=" + gamebook + "\n");
        bw.write("name=" + name + "\n");
        bw.write("initialSkill=" + initialSkill + "\n");
        bw.write("initialLuck=" + initialSkill + "\n");
        bw.write("initialStamina=" + initialStamina + "\n");
        bw.write("currentSkill=" + currentSkill + "\n");
        bw.write("currentLuck=" + currentLuck + "\n");
        bw.write("currentStamina=" + currentStamina + "\n");
        bw.write("currentReference=" + ref + "\n");
        bw.write("equipment=" + equipmentS + "\n");
        bw.write("notes=" + notesS + "\n");
        bw.write("provisions=" + getProvisions() + "\n");
        bw.write("provisionsValue=" + getProvisionsValue() + "\n");
        storeAdventureSpecificValuesInFile(bw);
    }

    public abstract void storeAdventureSpecificValuesInFile(BufferedWriter bw) throws IOException;

    public void consumePotion(View view) {
        int message = -1;
        switch (standardPotion) {
            case 0:
                message = R.string.potionSkillReplenish;
                setCurrentSkill(getInitialSkill());
                break;
            case 1:
                message = R.string.potionStaminaReplenish;
                setCurrentStamina(getInitialStamina());
                break;
            case 2:
                message = R.string.potionLuckReplenish;
                int maxLuck = getInitialLuck() + 1;
                setCurrentLuck(maxLuck);
                setInitialLuck(maxLuck);
                break;
        }
        setStandardPotionValue(getStandardPotionValue() - 1);
        showAlert(message, this);
        refreshScreens();
    }

    public void consumeProvision(View view) {
        if (provisions == 0) {
            showAlert(R.string.noProvisionsLeft, this);
        } else if (getCurrentStamina() == getInitialStamina()) {
            showAlert(R.string.provisionsMaxStamina, this);
        } else {
            AdventureVitalStatsFragment vitalstats = getVitalStatsFragment();
            vitalstats.setProvisionsValue(--provisions);
            setCurrentStamina(getCurrentStamina() + provisionsValue);
            if (getCurrentStamina() > getInitialStamina())
                setCurrentStamina(getInitialStamina());
            showAlert(getResources().getString(R.string.provisionsStaminaGain, provisionsValue), this);
        }
    }

    public Integer getInitialSkill() {
        return initialSkill;
    }

    public void setInitialSkill(Integer initialSkill) {
        this.initialSkill = initialSkill;
    }

    public Integer getInitialLuck() {
        return initialLuck;
    }

    public void setInitialLuck(Integer initialLuck) {
        this.initialLuck = initialLuck;
    }

    public Integer getInitialStamina() {
        return initialStamina;
    }

    public void setInitialStamina(Integer initialStamina) {
        this.initialStamina = initialStamina;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public Integer getStandardPotion() {
        return standardPotion;
    }

    public void setStandardPotion(Integer standardPotion) {
        this.standardPotion = standardPotion;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getProvisions() {
        return provisions;
    }

    public void setProvisions(Integer provisions) {
        this.provisions = provisions;
    }

    public Integer getStandardPotionValue() {
        return standardPotionValue;
    }

    public void setStandardPotionValue(Integer standardPotionValue) {
        this.standardPotionValue = standardPotionValue;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    public int getGamebook() {
        return gamebook;
    }

    public void setGamebook(int gamebook) {
        this.gamebook = gamebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getSavedGame() {
        return savedGame;
    }

    public void setSavedGame(Properties savedGame) {
        this.savedGame = savedGame;
    }

    public Integer getCurrentSkill() {
        return currentSkill;
    }

    public void setCurrentSkill(Integer currentSkill) {
        AdventureVitalStatsFragment adventureVitalStatsFragment = getVitalStatsFragment();
        this.currentSkill = currentSkill;
        adventureVitalStatsFragment.refreshScreensFromResume();
    }

    public Integer getCurrentLuck() {
        return currentLuck;
    }

    public void setCurrentLuck(Integer currentLuck) {
        AdventureVitalStatsFragment adventureVitalStatsFragment = getVitalStatsFragment();
        this.currentLuck = currentLuck;
        adventureVitalStatsFragment.refreshScreensFromResume();
    }

    public Integer getCurrentStamina() {
        return currentStamina;
    }

    public void setCurrentStamina(Integer currentStamina) {
        AdventureVitalStatsFragment adventureVitalStatsFragment = getVitalStatsFragment();
        this.currentStamina = currentStamina;
        adventureVitalStatsFragment.refreshScreensFromResume();
    }

    public Integer getCurrentReference() {
        return currentReference;
    }

    public void setCurrentReference(Integer currentReference) {
        this.currentReference = currentReference;
    }

    public synchronized Integer getProvisionsValue() {
        return provisionsValue;
    }

    public synchronized void setProvisionsValue(Integer provisionsValue) {
        this.provisionsValue = provisionsValue;
    }

    @Override
    protected void onPause() {
        super.onPause();

        pause();
    }

    private void pause() {
        try {
            File file = new File(dir, "temp" + ".xml");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

            storeValuesInFile("-1", bw);

            bw.close();
        } catch (IOException e) {
            try {
                FileWriter fw = new FileWriter(new File(dir, "exception_" + new Date() + ".txt"), true);
                PrintWriter pw = new PrintWriter(fw);
                e.printStackTrace(pw);
                pw.close();
                fw.close();
            } catch (IOException e1) {
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    private void resume() {
        try {

            File f = new File(dir, "temp.xml");

            if (!f.exists())
                return;

            loadGameFromFile(dir, "temp.xml");

            refreshScreens();

        } catch (Exception e) {
            try {
                FileWriter fw = new FileWriter(new File(dir, "exception_" + new Date() + ".txt"), true);
                PrintWriter pw = new PrintWriter(fw);
                e.printStackTrace(pw);
                pw.close();
                fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void testResume(View v) {
        resume();
    }

    public void testPause(View v) {
        pause();
    }

    public void refreshScreens() {

        Collection<Fragment> afList = getFragments().values();

        for (Fragment fragment : afList) {
            AdventureFragment af = (AdventureFragment) fragment;
            af.refreshScreensFromResume();
        }
    }

    protected List<String> stringToArray(String _string) {

        List<String> elements = new ArrayList<String>();

        if (_string != null) {
            List<String> list = Arrays.asList(_string.split("#"));
            for (String string : list) {
                if (!string.isEmpty())
                    elements.add(string);
            }
        }

        return elements;
    }

    protected Set<String> stringToSet(String _string) {

        Set<String> elements = new HashSet<String>();

        if (_string != null) {
            List<String> list = Arrays.asList(_string.split("#"));
            for (String string : list) {
                if (!string.isEmpty())
                    elements.add(string);
            }
        }

        return elements;
    }

    public void changeStamina(int i) {
        setCurrentStamina(i > 0 ? Math.min(getInitialStamina(), getCurrentStamina() + i) : Math.max(0, getCurrentStamina() + i));
    }

    public String getConsumeProvisionText() {
        return getResources().getString(R.string.consumeProvisions);
    }

    public String getCurrencyName() {
        return getResources().getString(R.string.gold);
    }

    public Integer getCombatSkillValue() {
        return getCurrentSkill();
    }

    public void fullRefresh() {
        for (Fragment fragment : getFragments().values()) {
            AdventureFragment frag = (AdventureFragment) fragment;
            frag.refreshScreensFromResume();
        }

    }

    public Map<Integer, Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(Map<Integer, Fragment> fragments) {
        this.fragments = fragments;
    }

    public void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    public static class AdventureFragmentRunner {
        int titleId;
        String className;

        public AdventureFragmentRunner(int titleId, String className) {
            super();
            this.titleId = titleId;
            this.className = className;
        }

        public int getTitleId() {
            return titleId;
        }

        public String getClassName() {
            return className;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class StandardSectionsPagerAdapter extends FragmentPagerAdapter {

        public StandardSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            try {
                Fragment o = (Fragment) Class.forName(fragmentConfiguration.get(position).getClassName()).newInstance();
                fragments.put(position, o);
                return o;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragmentConfiguration.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            return getString(fragmentConfiguration.get(position).getTitleId()).toUpperCase(l);
        }

    }


}
