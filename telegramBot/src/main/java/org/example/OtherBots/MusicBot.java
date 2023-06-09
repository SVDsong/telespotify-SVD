package org.example.OtherBots;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


public class MusicBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "5833374309:AAE31hnDEJ4d7ocSOIyArVQiByOc5VxUhns";
    private static final String BOT_USERNAME = "kaziko0001_bot";
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
//    private final String deezerApiKey = "frgfXoeCfzOTQNy8d46F3AuWnksZo9u32HrrGw9t7c7ZvXjWu2H";
    private final String accessToken="frgfXoeCfzOTQNy8d46F3AuWnksZo9u32HrrGw9t7c7ZvXjWu2H";
    private final String deezerApiUrl = "https://api.deezer.com/track/";
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String trackName = message.getText();
            String chatId = message.getChatId().toString();
            String trackInfo = searchTrack(trackName);
            sendMessage(chatId, trackInfo);
        }
    }
    private void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String searchTrack(String trackName) {
        String url = deezerApiUrl + "278903" + "/files?access_token=" + accessToken;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String jsonString = response.body().string();
            JSONObject track = new JSONObject(jsonString);
            String trackUrl = track.getString("url");
            playTrackUrl(trackUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    private void playTrackUrl(String trackUrl) {
        System.out.println(trackUrl);
    }
}
