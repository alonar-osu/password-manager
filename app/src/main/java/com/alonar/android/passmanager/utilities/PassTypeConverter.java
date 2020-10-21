package com.alonar.android.passmanager.utilities;

import com.alonar.android.passmanager.Type;

import androidx.room.TypeConverter;

public class PassTypeConverter {

    @TypeConverter
    public static int toInteger(Type passType) {
        return passType.getValue();
    }

    @TypeConverter
    public static Type toPassType(int typeIntValue) {
        Type passType = null;

        switch(typeIntValue) {
            case 0: passType = Type.EMAIL;
            break;
            case 1: passType = Type.APP;
            break;
            case 2: passType = Type.WEBSITE;
            break;
            case 3: passType = Type.BANK;
            break;
            case 4: passType = Type.UTILITY;
            break;
            case 5: passType = Type.OTHER;
        }
        return passType;
    }

}
