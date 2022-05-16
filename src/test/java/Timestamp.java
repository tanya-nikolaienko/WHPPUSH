import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.io.*;
import java.time.format.*;

public class Timestamp {
    private String str1;
    @Test
    public void DateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println("variant1: " + formatter.format(date));

        String str1 = formatter.format(date);
        System.out.println("variant1/2: " + str1);
/*
        String strm = String.format(String.valueOf(date));
        System.out.printf("variant1/1: " + strm);
        System.out.println();
        System.out.println("variant2: " + date);

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd hh:mm:ss a zzz");

        System.out.println("variant3: " + formatForDateNow.format(dateNow));

        Date date1 = new Date();

        // Вывод текущей даты и времени с использованием toString()
        String str = String.format("Текущая дата и время: %tc", date1);

        System.out.printf("variant4: " + str);

 */
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }
}
