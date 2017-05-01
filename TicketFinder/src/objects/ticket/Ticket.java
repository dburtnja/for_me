package objects.ticket;

import objects.Station;
import objects.search.Search;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Denys on 30.04.2017.
 */
public class Ticket {
    private Station from;
    private Station till;
    public Place place;
    public Date depDate;
    public Date tillDate;
    public String firstName;
    public String lastName;
    public Search search;
    private SimpleDateFormat writeFormat = new SimpleDateFormat("dd.MM.yyyyHH:mm");
    public SimpleDateFormat readData = new SimpleDateFormat("dd.MM.yyyy");
    public SimpleDateFormat readTime = new SimpleDateFormat("HH:mm");

    public Ticket(String from, String till,  JLabel serverResponse) {
        this.from = new Station(from, serverResponse);
        this.till = new Station(till, serverResponse);
    }

    public Station getFrom() {
        return from;
    }

    public Station getTill() {
        return till;
    }

    public void setDepDate(JLabel status, JTextField date, JTextField time) {
        try {
            this.depDate = writeFormat.parse(date.getText() + time.getText());
            date.setText(readData.format(this.depDate));
            time.setText(readTime.format(this.depDate));
        }
        catch (Exception e) {
            status.setText("Помилка дати");
            date.setText(readData.format(new Date()));
            time.setText(readTime.format(new Date()));
        }
    }

    public void setTillDate(JLabel status, JTextField date, JTextField time) {
        try {
            this.tillDate = writeFormat.parse(date.getText() + time.getText());
            date.setText(readData.format(this.tillDate));
            time.setText(readTime.format(this.tillDate));
        }
        catch (Exception e) {
            status.setText("Помилка дати");
            date.setText(readData.format(new Date()));
            time.setText(readTime.format(new Date()));
        }
    }
}
