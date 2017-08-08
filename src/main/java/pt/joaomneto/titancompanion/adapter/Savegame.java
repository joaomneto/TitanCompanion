package pt.joaomneto.titancompanion.adapter;

import java.util.Date;

/**
 * Created by Joao Neto on 14/05/17.
 */

public class Savegame {

    String filename;
    Date lastUpdated;

    public Savegame(String filename, Date lastUpdated) {
        this.filename = filename;
        this.lastUpdated = lastUpdated;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
