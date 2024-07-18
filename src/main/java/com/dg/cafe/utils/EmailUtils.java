package com.dg.cafe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMail(String to, String subject, String text, List<String> list)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mylearningd2021@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if(list!=null && list.size()>0)
            message.setCc(getCCArray(list));

        javaMailSender.send(message);


    }

    private String[] getCCArray(List<String> list) {
        Integer size = list.size();
        String[] ccArray = new String[size];
        for (int i=0; i<size; i++){
            ccArray[i] = list.get(i);
        }
        return ccArray;
    }

}
