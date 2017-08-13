package pt.joaomneto.titancompanion.adventurecreation.impl;


import java.io.BufferedWriter;
import java.io.IOException;

public class TPOPAdventureCreation extends TFODAdventureCreation{


    @Override
    protected void storeAdventureSpecificValuesInFile(BufferedWriter bw)
            throws IOException {

        super.storeAdventureSpecificValuesInFile(bw);

        bw.write("copper=0\n");
    }



}
