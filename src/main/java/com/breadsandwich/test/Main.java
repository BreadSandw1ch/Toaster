package com.breadsandwich.test;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;



public class Main extends Test {
    public static void main(String[] args) throws Exception {
        JDABuilder api = JDABuilder.createDefault("NzczMjc1Mzg4Mjk3NDc4MTU0.X6G20w.iGVaVr2JVKT97Wqs0Z2PAXjX70M")
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
