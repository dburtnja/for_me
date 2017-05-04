package objects.ticket;


import javax.swing.*;
import java.awt.event.ItemEvent;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;


/**
 * Created by Denys on 01.05.2017.
 */
public class Place {
    private boolean kPlace = false;
    private boolean pPlace = false;
    private boolean c1Place = false;
    private boolean c2Place = false;

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



    public boolean isSuitable(String id, Ticket ticket) {
        if (this.pPlace && id.matches("П")) {
            ticket.coach_type = "П";
            return true;
        } else if (this.kPlace && id.matches("К")) {
            ticket.coach_type = "К";
            return true;
        } else if (this.c1Place && id.matches("С1")) {
            ticket.coach_type = "С1";
            return true;
        } else if (this.c2Place && id.matches("С2")) {
            ticket.coach_type = "С2";
            return true;
        }
        return false;
    }
}
