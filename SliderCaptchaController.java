package com.fmj43.fanyoutong.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sliderCaptcha")
public class SliderCaptchaController {


    @PostMapping("/isVerify")
    public boolean isVerify(@RequestParam(value = "datas") String datazz) {
        List<Integer> datas=new ArrayList<>();
        for (int i = 0; i < datazz.length(); i++) {
            char c = datazz.charAt(i);
            if (c >= '0' && c <= '9') {
                datas.add(Integer.valueOf(String.valueOf(c)));
            }
        }
        int sum = 0;
        for (Integer data : datas) {
            sum += data;
        }
        double avg = sum * 1.0 / datas.size();
        double sum2 = 0.0;
        for (Integer data : datas) {
            sum2 += Math.pow(data - avg, 2);
        }
        double stddev = sum2 / datas.size();
        System.out.println("验证码判断"+(stddev != 0));
        return stddev != 0;
    }

}
