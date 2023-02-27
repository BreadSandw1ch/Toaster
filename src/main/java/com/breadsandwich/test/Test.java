package com.breadsandwich.test;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class Test extends ListenerAdapter {

    Boolean spam = true;

    Integer[] tempTime = new Integer[]{null};

    Boolean error = false;

    String prefix = "%";
    String[] pollEmotes = new String[]{"U+1F1E6", "U+1F1E7", "U+1F1E8", "U+1F1E9", "U+1F1EA", "U+1F1EB", "U+1F1EC",
            "U+1F1ED", "U+1F1EE", "U+1F1EF", "U+1F1F0", "U+1F1F1", "U+1F1F2", "U+1F1F3", "U+1F1F4", "U+1F1F5",
            "U+1F1F6", "U+1F1F7", "U+1F1F8", "U+1F1F9"};

    String[] twentyBall = new String[]{"As I see it, yes.", "Ask again later.", "Better not tell you now.", "Cannot predict now.",
    "Concentrate and ask again.", "Don’t count on it.", "It is certain.", "It is decidedly so.", "Most likely.", "My reply is no.",
    "My sources say no.", "Outlook not so good.", "Outlook good.", "Reply hazy, try again.", "Signs point to yes.", "Very doubtful.",
    "Without a doubt.", "Yes.", "Yes – definitely.", " You may rely on it."};
    public void sendPrivateEmbed (User user, MessageEmbed content) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    public void sendPrivateMessage (User user, String content) {
        user.openPrivateChannel().queue((channel) ->
                channel.sendMessage(content).queue());
    }

    public void convert (Integer[] tempTime) {
        while ((tempTime[0] < 0) || ((tempTime[1] < 0) || (tempTime[1] >= 60)) || ((tempTime[2] < 0) || (tempTime[2] >= 60))) {
            if (tempTime[2] >= 60) {
                tempTime[1] += 1;
                tempTime[2] -= 60;
            }
            if (tempTime[2] < 0) {
                tempTime[1] -= 1;
                tempTime[2] += 60;
            }
            if (tempTime[1] >= 60) {
                tempTime[0] += 1;
                tempTime[1] -= 60;
            }
            if (tempTime[1] < 0) {
                tempTime[0] -= 1;
                tempTime[1] += 60;
            }

            //System.out.println("convert to hms:" + tempTime[0] + ":" + tempTime[1] + ":" + tempTime[2]);
            if ((tempTime[0] < 0) && (tempTime[1] < 60) && (tempTime[2] < 60)) {
                error = true;
                break;
            }
        }
    }
    public void convertToSeconds(Integer[] tempTime) {
        //System.out.println("before convert to s:" + tempTime[0] + ":" + tempTime[1] + ":" + tempTime[2]);
        if(!tempTime[0].equals(0)) {
            tempTime[1] = tempTime[0] * 60 + tempTime[1];
            tempTime[0] = 0;
        }
        if(!tempTime[1].equals(0)) {
            tempTime[2] = tempTime[1] * 60 + tempTime[2];
            tempTime[1] = 0;
        }
        //System.out.println("after convert to s:" + tempTime[0] + ":" + tempTime[1] + ":" + tempTime[2]);
        if (tempTime[2] < 0) {
            error = true;
        }
    }

    @Override


    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        Member member = event.getMember();
        String user = event.getAuthor().getAsMention();
        String userID = event.getAuthor().getAsTag();
        String userName = event.getAuthor().getName();
        long userIDlong = event.getAuthor().getIdLong();
        MessageChannel channel = event.getChannel();
        String messageId = event.getMessageId();
        String[] args = content.split(" ");



        Random random = new Random();
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();

        EmbedBuilder eb = new EmbedBuilder();

        switch(args[0].toLowerCase()) {
            case "%toast":
                String toasted;
                int seconds;
                switch (args.length) {
                    case 3:
                        toasted = args[2];
                        String[] tempTimeA = args[1].split(":");
                        int hour = 0;
                        int minute = 0;
                        int second = 0;

                        switch (tempTimeA.length) {
                            case 3:
                                hour = Integer.parseInt(tempTimeA[0]);
                                minute = Integer.parseInt(tempTimeA[1]);
                                second = Integer.parseInt(tempTimeA[2]);
                                break;
                            case 2:
                                minute = Integer.parseInt(tempTimeA[0]);
                                second = Integer.parseInt(tempTimeA[1]);
                                break;
                            case 1:
                                second = Integer.parseInt(tempTimeA[0]);
                                break;
                        }
                        tempTime = new Integer[]{hour, minute, second};
                        convert(tempTime);
                        if (error.equals(true)) {
                            tempTime = new Integer[]{0, 0, 0};
                            channel.sendMessage("SYSTEM ERROR TOASTER IMPLODED").queue();
                            error = false;
                            break;
                        }
                        Integer[] DisplayTime = new Integer[]{tempTime[0], tempTime[1], tempTime[2]};
                        convertToSeconds(tempTime);
                        seconds = tempTime[2];
                        StringBuilder toastyMessage = new StringBuilder();
                        if (DisplayTime[0] > 0) {
                            if (DisplayTime[0] >= 2) {
                                toastyMessage.append(DisplayTime[0]).append(" hours");
                            } else {
                                toastyMessage.append(DisplayTime[0]).append(" hour");
                            }
                            if ((DisplayTime[1] > 0) || (DisplayTime[2] > 0)) {
                                toastyMessage.append(", ");
                            }
                        }
                        if (DisplayTime[1] > 0) {
                            if (DisplayTime[1] >= 2) {
                                toastyMessage.append(DisplayTime[1]).append(" minutes");
                            } else {
                                toastyMessage.append(DisplayTime[1]).append(" minute");
                            }
                            if (DisplayTime[2] > 0) {
                                toastyMessage.append(", ");
                            }
                        }
                        if (DisplayTime[2] > 0) {
                            if (DisplayTime[2] >= 2) {
                                toastyMessage.append(DisplayTime[2]).append(" seconds");
                            } else {
                                toastyMessage.append(DisplayTime[2]).append(" second");
                            }
                        }

                        if (seconds > 0) {
                            if (seconds > 3599) {
                                channel.sendMessage(user + ": :fire: You just started a fire after toasting " + toasted + " for " + toastyMessage.toString() + ". How do you feel?").queueAfter(seconds, TimeUnit.SECONDS);
                            } else if (seconds > 599) {
                            channel.sendMessage(user + ": You just burned " + toasted + ". Good job.").queueAfter(seconds, TimeUnit.SECONDS);
                            } else if (seconds > 29) {
                                channel.sendMessage(user + ": Perfectly toasted " + toasted + ". Yum :)").queueAfter(seconds, TimeUnit.SECONDS);
                            } else {
                                channel.sendMessage(user + ": :bread: You barely toasted " + toasted + "... -_-").queueAfter(seconds, TimeUnit.SECONDS);
                            }
                        } else {
                            channel.sendMessage(user + ": :fire: `SYSTEM ERROR TOASTER IMPLODED`").queue();
                        }
                        break;
                    case 2:
                        tempTimeA = args[1].split(":");
                        hour = 0;
                        minute = 0;
                        second = 0;

                        switch (tempTimeA.length) {
                            case 3:
                                hour = Integer.parseInt(tempTimeA[0]);
                                minute = Integer.parseInt(tempTimeA[1]);
                                second = Integer.parseInt(tempTimeA[2]);
                                break;
                            case 2:
                                minute = Integer.parseInt(tempTimeA[0]);
                                second = Integer.parseInt(tempTimeA[1]);
                                break;
                            case 1:
                                second = Integer.parseInt(tempTimeA[0]);
                                break;
                        }
                        tempTime = new Integer[]{hour, minute, second};
                        convert(tempTime);
                        if (error.equals(true)) {
                            tempTime = new Integer[]{0, 0, 0};
                            channel.sendMessage("SYSTEM ERROR TOASTER IMPLODED").queue();
                            error = false;
                            break;
                        }
                        DisplayTime = new Integer[]{tempTime[0], tempTime[1], tempTime[2]};
                        convertToSeconds(tempTime);
                        seconds = tempTime[2];
                        toastyMessage = new StringBuilder();
                        if (DisplayTime[0] > 0) {
                            if (DisplayTime[0] >= 2) {
                                toastyMessage.append(DisplayTime[0]).append(" hours");
                            } else {
                                toastyMessage.append(DisplayTime[0]).append(" hour");
                            }
                            if ((DisplayTime[1] > 0) || (DisplayTime[2] > 0)) {
                                toastyMessage.append(", ");
                            }
                        }
                        if (DisplayTime[1] > 0) {
                            if (DisplayTime[1] >= 2) {
                                toastyMessage.append(DisplayTime[1]).append(" minutes");
                            } else {
                                toastyMessage.append(DisplayTime[1]).append(" minute");
                            }
                            if (DisplayTime[2] > 0) {
                                toastyMessage.append(", ");
                            }
                        }
                        if (DisplayTime[2] > 0) {
                            if (DisplayTime[2] >= 2) {
                                toastyMessage.append(DisplayTime[2]).append(" seconds");
                            } else {
                                toastyMessage.append(DisplayTime[2]).append(" second");
                            }
                        }

                        channel.sendMessage("**" + userName + "**: Toasting... (Please wait approximately " + toastyMessage + ")").queue();
                        if (seconds > 0) {
                            if (seconds > 3599) {
                                channel.sendMessage(user + ": :fire: You just started a fire. How do you feel?").queueAfter(seconds, TimeUnit.SECONDS);
                            } else if (seconds > 599) {
                                channel.sendMessage(user + ": B̷̞̟̎̽͊͘Ȗ̷̩͓͋̀R̴̜̭͉̪͕̫̆̎̕N̸͍̋͆̀́̑͌̂͘̚͠͠T̸͙͚̥̣̙̍̅̈́̓̈́͋̿͗͒͗̅̚ ̵̻̀̂͜͝T̸̛̥̻̱̜̜̼̟͈̱̳̔̌̐̄̅͒̚͝ͅṎ̵̡̮̤̺̝̗͈̪̭̮̤̝͓̱̝́̔͒͑̀̌̚Ą̶̢͔̺̼͍͓̼̜͇̩̼̰̎̔͗ͅŜ̴̢͖̮͇̼͎̩̤̗͇͕͖̱̇͒͛̏͊̇͜T̴̺̞̙͍̞́̐̚͘").queueAfter(seconds, TimeUnit.SECONDS);
                            } else if (seconds > 29) {
                                channel.sendMessage(user + ": :bread: Your toast is ready :)").queueAfter(seconds, TimeUnit.SECONDS);
                            } else {
                                channel.sendMessage(user + ": :bread: The bread is barely toasted... -_-").queueAfter(seconds, TimeUnit.SECONDS);
                            }
                        } else {
                            channel.sendMessage(user + ": :fire: `SYSTEM ERROR TOASTER IMPLODED`").queue();
                        }
                        break;
                    default:
                        int time = (random.nextInt(61) + 30);
                        channel.sendMessage("**" + userName + "**: Toasting... (Please wait approximately " + time + " seconds)").queue();
                        channel.sendMessage(user + ": :bread: Your toast is ready :)").queueAfter(time, TimeUnit.SECONDS);
                        break;
                }break;
                /*
            case "%bready":
                eb.setImage("https://cdn.discordapp.com/attachments/777922476793266256/777976703405260831/FullSizeR-1.jpg");
                channel.sendMessage(eb.build()).queue();
                break;

            case "%nou":
                eb.setImage("https://cdn.discordapp.com/attachments/777922476793266256/777976703649579028/deepfry-3.png");
                channel.sendMessage(eb.build()).queue();
                break;

            case "%nsfw":
                channel.sendMessage("Calling the FBI...").queue();
                break;

            case "%ban":
                eb.setImage("https://cdn.discordapp.com/attachments/777922476793266256/779438791533396018/ban.png");
                channel.sendMessage(eb.build()).queue();
                break;
*/

            case "%help":
                if (args.length > 1) {
                    eb.setColor(0xeeac41);
                    switch (args[1].toLowerCase()) {
                        case "toast":
                            eb.setTitle("Help - Toast");
                            eb.setDescription("Usage: %toast {time} {~~person~~ thing to toast}");
                            eb.addField("What it does:", "Toasts an object for a certain amount of time", false);
                            eb.addField("Notes:", "Time can be given in the format hours:minutes:seconds, minutes:seconds, or seconds \n" +
                                    "The object can be anything, including the toaster in case you feel like toasting a toaster using a toaster for" +
                                    "some odd reason\n" +
                                    "Time and thing to toast are optional arguments", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "roll":
                            eb.setTitle("Help - Roll");
                            eb.setDescription("Usage: %roll [integer]");
                            eb.addField("What it does:", "Rolls a value between 1 and [integer]", false);
                            eb.addField("Notes:", "The integer value must be above one, or else the command will not work", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "spam":
                            eb.setTitle("Help - Spam");
                            eb.setDescription("Usage: %spam");
                            eb.addField("What it does:", "Enables or disables spam (will be updated sooner or later", false);
                            eb.addField("Notes:", "You need to be an admin or my creator to use this command", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "poll":
                            eb.setTitle("Help - Poll");
                            eb.setDescription("Usage: %poll [message ID] [Number of options]");
                            eb.addField("What it does:", "Provides a quick and easy way to set up a poll", false);
                            eb.addField("Notes:", "The message ID and the number of options are __both__ required\n" +
                                    "There must be between 2 and 20 options for the poll. Otherwise, it won't work", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "choose":
                            eb.setTitle("Help - Choose");
                            eb.setDescription("Usage: %choose [option 1] | [option 2] | {option 3} | {option 4} | ...");
                            eb.addField("What it does:", "Chooses a random option from a set of options", false);
                            eb.addField("Notes:", "At least 2 options are required. Any options after the first two are optional\n" +
                                    "Be sure to separate your options with \" | \". The spaces are required\n" +
                                    "Warning: Unless your life is entirely dictated by what some entity on a messaging site says," +
                                    " do not actually listen to what this bot says.", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "help":
                            eb.setTitle("Help - Help");
                            eb.setDescription("Usage: %help");
                            eb.addField("What it does:", "Sends a general overview of the commands", false);
                            eb.addField("Notes:", "Did you really do %help help? did you really need to find" +
                                    " how to use %help? Man, you're helpless...", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "20ball":
                            eb.setTitle("Help - 20Ball");
                            eb.setDescription("Usage: %20ball [question or whatever you want to say here, but at least something]");
                            eb.addField("What it does:", "Gives a random response to a question", false);
                            eb.addField("Notes:", "A question, or something anyway, after %20ball is required for this command to work\n" +
                                    "Warning: Unless your life is entirely dictated by what some entity on a messaging site says," +
                                    " do not actually listen to what this bot says.", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        case "coinflip":
                            eb.setTitle("Help - Coinflip");
                            eb.setDescription("Usage: %coinflip {heads/tails/side/[insert your guess here]}");
                            eb.addField("What it does:", "\"flips\" a coin", false);
                            eb.addField("Notes:", "Could literally be done by any of the other commands, but eh", false);
                            channel.sendMessage(eb.build()).queue();
                            break;
                        default:
                            eb.setTitle("Toaster Command List");
                            eb.setDescription("If you want to learn more about a command, do `%help [command]`");
                            eb.addField("Chance Commands", "%20ball, %choose, %roll, %coinflip", false);
                            eb.addField("Misc Commands", "%toast, %poll", false);
                            eb.addField("System Commands", "%spam, %help", false);
                            channel.sendMessage(eb.build()).queue();
                            channel.addReactionById(messageId, "U+1F35E").complete();
                            break;
                    }
                } else {
                    eb.setTitle("Toaster Command List");
                    eb.setDescription("If you want to learn more about a command, do `%help [command]`");
                    eb.addField("Chance Commands", "%20ball, %choose, %roll, %coinflip", false);
                    eb.addField("Misc Commands", "%toast, %poll", false);
                    eb.addField("System Commands", "%spam, %help", false);
                    channel.sendMessage(eb.build()).queue();
                    channel.addReactionById(messageId, "U+1F35E").complete();
                }
                break;
            case "%roll":
                int roll = Integer.parseInt(args[1]);
                if (roll > 1) {
                    int result = (random.nextInt(roll) + 1);
                    channel.sendMessage("**" + userName + "**'s result: " + result).queue();
                } else {
                    if (roll == 1) {
                        channel.sendMessage("**" + userName + "**, do you really expect me to roll a value between 1 and 1?").queue();
                    } else {
                        channel.sendMessage("`SYSTEM ERROR HOW TO ROLL BELOW 1`").queue();
                    }
                }
                break;
            case "%spam":
                assert member != null;
                if ((member.hasPermission(Permission.ADMINISTRATOR)) || userIDlong ==390246916308336640L) {
                    if (spam.equals(true)) {
                        channel.sendMessage("Spam has been disabled.").queue();
                        spam = false;
                    } else {
                        channel.sendMessage("Spam has been enabled.").queue();
                        spam = true;
                    }
                } else {
                    channel.sendMessage("I wouldn't give you *that* much power, would I? (you are missing the __**Administrator**__ permission)").queue();
                }
                break;
            case "%rickroll":
                channel.deleteMessageById(messageId).queue();
                if (userIDlong == 390246916308336640L) {
                    channel.sendMessage("https://tenor.com/view/dance-moves-dancing-singer-groovy-gif-17029825").queue();
                }
                break;
            case "%poll":
                long poll = Long.parseLong(args[1]);
                int x = Integer.parseInt(args[2]) - 1;
                if (x > 19 || x < 1) {
                    channel.addReactionById(messageId, "U+274C").complete();
                    channel.sendMessage("<:no_u:667112124597927947>").queue();
                } else {
                    for (int i = 0; i <= x; i++) {
                        channel.addReactionById(poll, pollEmotes[i]).complete();
                    }
                    channel.addReactionById(messageId,"U+2705").complete();
                }
                break;
            case "%cpoll":
            case "custompoll":
                long cpoll = Long.parseLong(args[1]);
                String[] reactions = content.substring(args[0].length() + args[1].length() + 2).split(" ");
                System.out.println(Arrays.toString(reactions));

                if (reactions.length > 20 || reactions.length < 2) {
                    channel.addReactionById(messageId, "U+274C").complete();
                    channel.sendMessage("<:no_u:667112124597927947>").queue();
                } else {
                    for (int i = 0; i <= reactions.length-1; i++) {
                        String emoteAdded = reactions[i];
                            channel.addReactionById(cpoll, emoteAdded).complete();
                    }
                    channel.addReactionById(messageId,"U+2705").complete();
                }

                break;
            case "%choose":
                String[] choices = content.substring(args[0].length() + 1).split(" \\| ");
                if (choices.length > 1) {
                    int result = random.nextInt(choices.length);
                    String result1 = choices[result];
                    int result2 = result + 1;
                    //System.out.println(result1.length());
                    if (result1.length() > 1935) {
                        channel.sendMessage("**" + userName + "**, you really expect me to type all of that out? (option " + result2 + " btw)").queue();
                    } else {
                        channel.sendMessage("**" + userName + "**, I choose: **" + result1 + "**! (option " + result2 + ")").queue();
                    }
                } else {
                    channel.sendMessage("**" + userName + "**, what am I supposed to choose from?").queue();
                }
                break;
            case "%20ball":
                if (args.length >= 2) {
                    int result = random.nextInt(20);
                    channel.sendMessage("**" + userName + "**: " + twentyBall[result]).queue();
                } else {
                    channel.sendMessage("**" + userName + "**: What are you asking?").queue();
                }
                break;
            case "%coinflip":
                int side = random.nextInt(12000);
                StringBuilder coinflip = new StringBuilder();
                coinflip.append("**").append(userName).append("**'s ");
                if ((side > 0) && (side < 6000)) {
                    coinflip.append("Result: Heads ");
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("heads")) {
                            coinflip.append("(You got it correct. Congrats)");
                        } else {
                            coinflip.append("(You got it incorrect. rip)");
                        }
                    }
                } else if ((side > 5999) && (side < 11999)) {
                    coinflip.append("Result: Tails ");
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("tails")) {
                            coinflip.append("(You got it correct. Congrats)");
                        } else {
                            coinflip.append("(You got it incorrect. rip)");
                        }
                    }
                } else {
                    coinflip.append("Result: Side ");
                    if (args.length >= 2) {
                        if ((args[1].equalsIgnoreCase("side")) || (args[1].equalsIgnoreCase("sides"))) {
                            coinflip.append("(You got it correct. Wait... HOW?!)");
                        } else {
                            coinflip.append("(You got it incorrect. What did you expect?)");
                        }
                    }
                }
                System.out.println(side);
                channel.sendMessage(coinflip).queue();
                break;
            case "🍞":
                message.addReaction("U+1F35E").complete();
                channel.sendMessage("🍞").queue(message1 -> message1.addReaction("U+1F35E").queue());
                break;


        }

switch (content.toLowerCase()) {
    //case "h":
    //case "yes":
    /*
    case "🍞":
        if (!spam.equals(true)) return;
        else {
            channel.sendMessage(content).queue();
        }
        break;

     */
    case "f":
        if ((userIDlong == 390246916308336640L) && (spam.equals(true))) {
            channel.deleteMessageById(messageId).queue();
            channel.sendMessage("<:f_:653852766392942602>").queue();
        }
        break;
}


/*
        if (content.equals("&inspire")) {
            HttpUrl inspire = HttpUrl.get("https://www.inspirobot.me/");
            channel.sendMessage("Here's your inspirational quote: " + inspire).queue();

        }
 */

//        if (content.equals("&wholesome")) {
//            OkHttpClient http = channel.getJDA().getHttpClient();
//            Request request = new Request.Builder().url("https://giphy.com/explore/wholesome").build();
//            Response response = null;
//            try {
//                response = http.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                InputStream body = response.body().byteStream();
//                eb.setImage("attachment://image.png");
//                Response finalResponse = response;
//                channel.sendMessage(eb.build())
//                        .addFile(body, "image.png)")
//                        .queue(m -> finalResponse.close(), error -> {
//                            finalResponse.close();
//                            RestAction.getDefaultFailure().accept(error);
//                        });
//            } catch (Throwable ex) {
//                response.close();
//                if (ex instanceof Error) throw (Error) ex;
//                else throw (RuntimeException) ex;
//            }
//            }

        }
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        //String emote = event.getReactionEmote().getAsCodepoints();
        //System.out.println(emote);
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        Member a = event.getMember();
        System.out.println(a);
        System.out.println("a");
    }
}

