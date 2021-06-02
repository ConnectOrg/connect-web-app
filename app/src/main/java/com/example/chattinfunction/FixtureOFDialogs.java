package com.example.chattinfunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FixtureOFDialogs extends FixtureDatas {
    private FixtureOFDialogs(){
        throw new AssertionError();
    }
    public static ArrayList<ModelOFDialog> getDialogs() {
        ArrayList<ModelOFDialog> chats = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -(i * i));
            calendar.add(Calendar.MINUTE, -(i * i));

            chats.add(getDialog(i, calendar.getTime()));
        }

        return chats;
    }

    private static ModelOFDialog getDialog(int i, Date lastMessageCreatedAt) {
        ArrayList<ModelOFUser> users = getUsers();
        return new ModelOFDialog(
                getRandomId(),
                users.size() > 1 ? groupChatTitles.get(users.size() - 2) : users.get(0).getName(),
                users.size() > 1 ? groupChatImages.get(users.size() - 2) : getRandomAvatar(),
                users,
                getMessage(lastMessageCreatedAt),
                i < 3 ? 3 - i : 0);
    }

    private static ArrayList<ModelOFUser> getUsers() {
        ArrayList<ModelOFUser> users = new ArrayList<>();
        int usersCount = 1 + rnd.nextInt(4);

        for (int i = 0; i < usersCount; i++) {
            users.add(getUser());
        }

        return users;
    }

    private static ModelOFUser getUser() {
        return new ModelOFUser(
                getRandomId(),
                getRandomName(),
                getRandomAvatar(),
                getRandomBoolean()
        );
    }

    private static ModelOFMessage getMessage(final Date date) {
        return new ModelOFMessage(
                getRandomId(),
                getUser(),
                getRandomMessage(),
                date);
    }
}


