package com.example.dburtnja.androidticketfinder10.TicketInfo;

import android.widget.CheckBox;

import com.example.dburtnja.androidticketfinder10.MainActivity;
import com.example.dburtnja.androidticketfinder10.R;

/**
 * Created by dburtnja on 06.07.17.
 * Coach type object
 */

public class Train {
    private boolean      C1;
    private boolean      C2;
    private boolean      P;
    private boolean      K;

    public Train() {
        C1 = true;
        C2 = true;
        P = true;
        K = true;
    }

    public void changeCoach(CheckBox[] checkBoxes){
        for (CheckBox checkBox : checkBoxes){
            switch (checkBox.getId()){
                case R.id.checkC1:
                    C1 = true;
                    break;
                case R.id.checkC2:
                    C2 = true;
                    break;
                case R.id.checkP:
                    P = true;
                    break;
                case R.id.checkK:
                    K = true;
            }
        }
    }

    public boolean coachIsSet(MainActivity activity){
        if (C1 || C2 || P || K)
            return true;
        else {
            activity.toast("Не вказаний тип вагону", true);
            return false;
        }
    }
}
