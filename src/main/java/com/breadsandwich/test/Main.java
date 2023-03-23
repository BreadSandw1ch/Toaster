package com.breadsandwich.test;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;

import java.io.File;
import java.util.Scanner;


public class Main extends Test {
    public static void main(String[] args) throws Exception {
        File textFile = new File("input/tokentoaster.txt");
        Scanner scanner = new Scanner(textFile);
        String token = scanner.nextLine();
        System.out.println("token acquired");
        JDABuilder api = JDABuilder.createDefault(token)
                .addEventListeners(new Main())
                .setStatus(OnlineStatus.ONLINE);
        api.setActivity(Activity.watching("toast | try %help")).build();
        /*
        for (int i = 0; i < 16; i++) {
            String status = "toast | try %help | id " + i;
            api.useSharding(i, 16)
            .setActivity(Activity.watching(status))
                    .build();
        }

         */

    }
}
