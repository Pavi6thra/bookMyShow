package com.microService.bookmyshow.bookMyShow;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class bookContro {

    @Autowired
    RestTemplate restTemplate;


    @HystrixCommand(groupKey = "MS", commandKey = "MS", fallbackMethod = "fallBackResponse")
    @GetMapping("/bookNowWithHystrix")
    public String bookShow(){
        String emailResponse=  restTemplate.getForObject("http://localhost:8082/emailService/mail",String.class);
        String paymentResponse=  restTemplate.getForObject("http://localhost:8081/paymentService/pay",String.class);
        return emailResponse + "\n" + paymentResponse;
    }


    @HystrixCommand(groupKey = "MS1", commandKey = "MS1", fallbackMethod = "fallBackResponse1")
    @GetMapping("/bookNowWithHystrix1")
    public String bookShow1(){
        String emailResponse=  restTemplate.getForObject("http://localhost:8082/emailService/mail",String.class);
        String paymentResponse=  restTemplate.getForObject("http://localhost:8081/paymentService/pay",String.class);
        return emailResponse + "\n" + paymentResponse;
    }


    /*@GetMapping("/bookNowWithoutHystrix")
    public String bookShowWithoutHystrix(){
        String emailResponse=  restTemplate.getForObject("http://localhost:8080/emailService/mail",String.class);
        String paymentResponse=  restTemplate.getForObject("http://localhost:8081/paymentService/pay",String.class);
        return emailResponse + "\n" + paymentResponse;
    }*/


    public String fallBackResponse(){
        return "from fallBack";
    }
    public String fallBackResponse1(){
        return "from fallBack1";
    }

    @Bean
    public RestTemplate template(){
        return new RestTemplate();

    }
}
