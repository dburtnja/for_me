package objects.ticket;

import javax.swing.*;
import java.awt.event.ItemEvent;

/**
 * Created by Denys on 01.05.2017.
 */
public class Place {
    public boolean kPlace = false;
    public boolean pPlace = false;
    public boolean c1Place = false;
    public boolean c2Place = false;

    public Place(JRadioButton eny, JRadioButton kPlace, JRadioButton pPlace, JRadioButton c1Place, JRadioButton c2Place) {
        if (eny.isSelected()) {
            this.kPlace = true;
            this.pPlace = true;
            this.c1Place = true;
            this.c2Place = true;
        } else {
            if (kPlace.isSelected())
                this.kPlace = true;
            if (pPlace.isSelected())
                this.pPlace = true;
            if (c1Place.isSelected())
                this.c1Place = true;
            if (c2Place.isSelected())
                this.c2Place = true;
        }
    }

}
