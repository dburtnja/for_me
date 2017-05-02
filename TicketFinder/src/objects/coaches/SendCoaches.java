package objects.coaches;

import jdk.internal.org.objectweb.asm.tree.analysis.Interpreter;
import objects.ExeptionObj.ExceptionObj;
import objects.Post;
import objects.coach.Coach;
import objects.ticket.Ticket;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Denys on 02.05.2017.
 */
public class SendCoaches {

    private void selectCoach(Ticket ticket) {
        ticket.coach_num = ticket.coaches.getCoaches().get(0).getNum();
        ticket.coach_class = ticket.coaches.getCoaches().get(0).getCoachClass();
        ticket.coach_type_id = ticket.coaches.getCoaches().get(0).getCoachTypeId();
    }

    private void findPlace(Ticket ticket) {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<String> places = ticket.coach.getValue().getPlaces().getValue();
        for (String place : places) {
            if (Integer.parseInt(place) % 2 == 1) {
                ticket.palce_nbr = place;
                break;
            }
        }
        if (ticket.palce_nbr == null)
            ticket.palce_nbr = places.get(0);
    }

    public SendCoaches(Ticket ticket, Post post) {
        String coachesParam = "station_id_from=" + ticket.getFrom().value +
                "&station_id_till=" + ticket.getTill().value +
                "&train=" + URLEncoder.encode(ticket.train_nbr) +
                "&coach_type=" + URLEncoder.encode(ticket.coach_type) +
                "&model=0" +
                "&date_dep=" + (int)(ticket.tillDate.getTime() / 1000) +
                "&round_trip=0" +
                "&another_ec=0";
        ticket.coaches = (TestClass) post.sendPost("http://booking.uz.gov.ua/purchase/coaches/", coachesParam, TestClass.class);
        selectCoach(ticket);
        String coachParam = "station_id_from=" + ticket.getFrom().value +
                "&station_id_till=" + ticket.getTill().value +
                "&train=" + URLEncoder.encode(ticket.train_nbr) +
                "&coach_num=" + ticket.coach_num +
                "&coach_class=" + URLEncoder.encode(ticket.coach_class) +
                "&coach_type_id=" + ticket.coach_type_id +
                "&date_dep=" + (int)(ticket.tillDate.getTime() / 1000) +
                "&scheme_id=0";
        System.out.println("coachParam = " + coachParam);
        Object newCoach = (Object) post.sendPost("http://booking.uz.gov.ua/purchase/coach/", coachParam, Coach.class);
        try {
            ticket.coach = (Coach) newCoach;
            findPlace(ticket);
        } catch (Exception e) {
            ExceptionObj exceptionObj = (ExceptionObj) newCoach;
            System.out.println("Помилка SendCoaches:46");
        }
    }
}
//station_id_from=2200001&station_id_till=2218300&train=049%D0%9A&coach_num=1&coach_class=%D0%91&coach_type_id=3&date_dep=1493745120&scheme_id=0
//station_id_from=2200001&station_id_till=2218300&train=357%D0%9A&coach_num=1&coach_class=%D0%91&coach_type_id=%D0%9A&date_dep=1493758740&scheme_id=0