package objects.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import objects.ExeptionObj.ExceptionObj;
import objects.Post;
import objects.coaches.SendCoaches;
import objects.ticket.Ticket;

import javax.swing.*;
import java.awt.*;
import java.net.URLEncoder;
import java.util.List;

public class Search {

    @SerializedName("value")
    @Expose
    public List<Value> value = null;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("captcha")
    @Expose
    private Object captcha;

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Object captcha) {
        this.captcha = captcha;
    }

    public boolean sendPost(Ticket ticket, JLabel status, JLabel serverResponse, JLabel findPlace) {
        status.setText("Веду пошук");
        String param = "station_id_from=" + ticket.getFrom().value +
                "&station_id_till=" + ticket.getTill().value +
                "&station_from=" +
                "&station_till=" +
                "&date_dep=" + ticket.readData.format(ticket.depDate) +
                "&time_dep=" + URLEncoder.encode(ticket.readTime.format(ticket.depDate)) +
                "&time_dep_till=&another_ec=0&search=";
        Post post = new Post();
        Object search = post.sendPost("http://booking.uz.gov.ua/purchase/search/", param, Search.class);
        try {
            ticket.search = (Search)search;
            long trainData;
            int myData;
            for (int i = 0; i < ticket.search.value.size(); i++) {
                trainData = ticket.search.value.get(i).getFrom().getDate().longValue();
                myData = (int)(ticket.tillDate.getTime() / 1000);
                if (myData >= trainData){
                    for (int j = 0; j < ticket.search.value.get(i).getTypes().size(); j++) {
                        if (ticket.place.isSuitable(ticket.search.value.get(i).getTypes().get(j).getId(), ticket)) {
                            findPlace.setText("Знайдено: " + ticket.coach_type);
                            findPlace.setForeground(Color.red);
                            if (ticket.firstName != null) {
                                ticket.train_nbr = ticket.search.value.get(i).getNum();
                                new SendCoaches(ticket, post);
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            ExceptionObj exceptionObj = (ExceptionObj) search;
            if (exceptionObj.value.compareTo("Введена невірна дата") == 0) { //змінити на "по заданому напрямку місць не має.."
                status.setForeground(Color.red);
                status.setText(exceptionObj.value);
                serverResponse.setText(exceptionObj.value);
                return true;
            } else {
                serverResponse.setText(exceptionObj.value);
                return false;
            }
        }
    }
}