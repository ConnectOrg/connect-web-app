package com.example.chattinfunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FixtureofMessages extends FixtureDatas {
    private FixtureofMessages(){
        throw new AssertionError();
    }
    public static ModelOFMessage getImageMessage() {
        ModelOFMessage message = new ModelOFMessage(getRandomId(), getUser(), null);
        message.setImage(new ModelOFMessage.Image ( getRandomImage()));
        return message;
    }



    public static ModelOFMessage getTextMessage() {
        return getTextMessage(getRandomMessage());
    }

    public static ModelOFMessage getTextMessage(String text) {
        return new ModelOFMessage(getRandomId(), getUser(), text);
    }

    public static ArrayList<ModelOFMessage> getMessages(Date startDate) {
        ArrayList<ModelOFMessage> messages = new ArrayList<>();
        for (int i = 0; i < 10/*days count*/; i++) {
            int countPerDay = rnd.nextInt(5) + 1;

            for (int j = 0; j < countPerDay; j++) {
                ModelOFMessage message;
                if (i % 2 == 0 && j % 3 == 0) {
                    message = getImageMessage();
                } else {
                    message = getTextMessage();
                }

                Calendar calendar = Calendar.getInstance();
                if (startDate != null) calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1));

                message.setCreatedAt(calendar.getTime());
                messages.add(message);
            }
        }
        return messages;
    }

    private static ModelOFUser getUser() {
        boolean even = rnd.nextBoolean();
        return new ModelOFUser(
                even ? "0" : "1",
                even ? names.get(0) : names.get(1),
                even ? avatars.get(0) : avatars.get(1),
                true);
    }
}
