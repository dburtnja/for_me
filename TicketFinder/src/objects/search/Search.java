package objects.search;

import objects.Ticket;

import java.net.URLEncoder;

public class Search {
    public String toString(Ticket ticket) {
        return  "station_id_from=" + ticket.getFrom().value +
                "&station_id_till=" + ticket.getTill().value +
                "&station_from=" +
                "&station_till=" +
                "&date_dep=" + ticket.readData.format(ticket.depDate) +
                "&time_dep=" + URLEncoder.encode(ticket.readTime.format(ticket.depDate)) +
                "&time_dep_till=&another_ec=0&search=";
    }
}