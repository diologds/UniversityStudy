package lv.rtu.domain;

import java.io.Serializable;
import java.util.Arrays;

public class ObjectFile implements Serializable {

    private String command;
    private String subCommand;
    private String message;
    private String data;
    private byte[] fileBytes;
    private User user;
    private String accessToken;

    public ObjectFile(String command) {
        this.message = message;
    }

    public ObjectFile(String command, String subCommand, String message, String data, byte[] fileBytes, String accessToken, User user) {
        this.command = command;
        this.subCommand = subCommand;
        this.message = message;
        this.data = data;
        this.fileBytes = fileBytes;
        this.accessToken = accessToken;
        this.user = user;
    }

    public ObjectFile(String command,String subCommand, String message, User user , String accessToken) {
        this.command = command;
        this.subCommand = subCommand;
        this.message = message;
        this.user = user;
        this.accessToken = accessToken;
    }

    public ObjectFile(String command,String subCommand, User user , String accessToken) {
        this.command = command;
        this.subCommand = subCommand;
        this.user = user;
        this.accessToken = accessToken;
    }

    public ObjectFile(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public ObjectFile(String command, String subCommand) {
        this.command = command;
        this.subCommand = subCommand;
    }

    public ObjectFile(String command, String subCommand, String accessToken) {
        this.command = command;
        this.subCommand = subCommand;
        this.accessToken = accessToken;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSubCommand() {
        return subCommand;
    }

    public void setSubCommand(String subCommand) {
        this.subCommand = subCommand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectFile)) return false;

        ObjectFile that = (ObjectFile) o;

        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) return false;
        if (command != null ? !command.equals(that.command) : that.command != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (!Arrays.equals(fileBytes, that.fileBytes)) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (subCommand != null ? !subCommand.equals(that.subCommand) : that.subCommand != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (subCommand != null ? subCommand.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (fileBytes != null ? Arrays.hashCode(fileBytes) : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ObjectFile{" +
                "command='" + command + '\'' +
                ", subCommand='" + subCommand + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", fileBytes=" + Arrays.toString(fileBytes) +
                ", user=" + user +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
