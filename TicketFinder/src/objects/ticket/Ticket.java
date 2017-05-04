package objects.ticket;

import com.sun.deploy.util.SystemUtils;
import objects.Station;
import objects.coach.Coach;
import objects.coaches.Coaches;
import objects.coaches.TestClass;
import objects.search.Search;


import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private Station from;
    private Station till;
    public Place place;
    public Date depDate;
    public Date tillDate;
    public String firstName;
    public String lastName;
    public Search search;
    public TestClass coaches;
    public Coach coach;
    private SimpleDateFormat writeFormat = new SimpleDateFormat("dd.MM.yyyyHH:mm");
    public SimpleDateFormat readData = new SimpleDateFormat("dd.MM.yyyy");
    public SimpleDateFormat readTime = new SimpleDateFormat("HH:mm");
    public String train_nbr;
    public String coach_type;
    public int coach_type_id;
    public int coach_num;
    public String coach_class;
    public String palce_nbr = null;
    public JLabel cookieStore;
    public String osName;
    public JLabel instruction1;
    public JLabel instruction2;

    public JTextField test1;
    public JTextField test2;

    public Ticket(String from, String till,  JLabel serverResponse) {
        this.from = new Station(from, serverResponse);
        this.till = new Station(till, serverResponse);
        osName = System.getProperty("os.name").toLowerCase();
        serverResponse.setText(osName);
    }

    public Station getFrom() {
        return from;
    }

    public Station getTill() {
        return till;
    }

    public boolean setName(JTextField firstName, JTextField lastName, JLabel status) {
        if (firstName.getText().isEmpty()) {
            status.setText("Не вказане ім'я");
            return false;
        }
        if (lastName.getText().isEmpty()) {
            status.setText("Не вказане прізвище");
            return false;
        }
        this.firstName = firstName.getText();
        this.lastName = lastName.getText();
        return true;
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
