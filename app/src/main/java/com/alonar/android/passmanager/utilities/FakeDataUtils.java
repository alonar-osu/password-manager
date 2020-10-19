package com.alonar.android.passmanager.utilities;

import com.alonar.android.passmanager.PassEntry;
import com.alonar.android.passmanager.Type;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FakeDataUtils {

    public static ArrayList<PassEntry> generateFakePassEntry() {
        ArrayList<PassEntry> dataset = new ArrayList<PassEntry>();

        for (int i = 0; i < 100; i++) {
            String name = "entry " + i;
            Type type = Type.EMAIL;
            String password = "1234";
            Date date = Calendar.getInstance().getTime();

            PassEntry passEntry = new PassEntry(name, type, password, date);
            dataset.add(passEntry);
        }
        return dataset;
    }

}
