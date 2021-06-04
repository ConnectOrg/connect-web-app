package com.example.chattinfunction;

import androidx.annotation.Nullable;

import com.example.chattinfunction.commons.models.IMessage;
import com.example.chattinfunction.commons.models.IUser;
import com.example.chattinfunction.commons.models.MessageContentType;
import com.example.chattinfunction.messages.MessageInput;

import java.util.Date;

public class ModelOFMessage implements IMessage, MessageContentType.Image {
    String id;
    String text;
    ModelOFUser user;
    Date createdAt;
    Image image;

    public ModelOFMessage(String id,ModelOFUser user,String text){
        this(id,user,text,new Date());
    }
    public ModelOFMessage(String id,ModelOFUser user,String text,Date createdAt){
        this.id = id;
        this.user = user;
        this.text=text;
        this.createdAt= createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public ModelOFUser getUser() {
        return this.user;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Nullable
    @Override
    public String getImageUrl() {
        return image == null ? null : image.url ;
    }

    public String getStatus() {
        return "sent";
    }

    public static class Image{
        protected String url;
        public Image(String url){
            this.url =url;
        }
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt=createdAt;
    }
}
