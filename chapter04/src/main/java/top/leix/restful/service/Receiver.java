package top.leix.restful.service;
import com.google.gson.Gson;
import top.leix.restful.model.City;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Name: Receiver
 * Description:
 * Author:leix
 * Time: 2017/3/28 14:13
 */
@Component
public class Receiver {
    public void process(String hello) {
        System.out.println("receiver:" + hello);
    }

    @RabbitListener(queues = "hello")
    public void process(City city) {
        System.out.println("city.name===[" + city.getCountry() + "]");
        System.out.println(new Gson().toJson(city));
    }
}
