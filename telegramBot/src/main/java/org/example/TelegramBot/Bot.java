package org.example.TelegramBot;

import org.apache.hc.core5.http.ParseException;
import org.example.Spotify.Spotify;
import org.example.User.User;
import org.example.User.UserStatus;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Objects;

public class Bot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "5916098046:AAExH7ZMfYF7T2z501JTXQ-4ZAzC0yuER6o";
    private static final String BOT_USERNAME = "SearchMusicByNameUz_bot";
    private static final String SPOTIFY_CLIENT_ID = "113481984889495695877b84a0396c90";
    private static final String SPOTIFY_CLIENT_SECRET = "cc09852165394ed287aec9c49691459d";
    String url = "http://localhost:8888/callback";
    LinkedList<User> users = new LinkedList<>();

    public User checkUser(String chatId) {
        for (User user : users) {
            if (user.getId().equals(chatId)) {
                return user;
            }
        }
        User user = new User();
        user.setId(chatId);
        users.add(user);
        return user;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    private User UserSetStatus(String chatId, Message message) {
        User currentUser = checkUser(chatId);
        if (Objects.equals(message.getText(), "/start")) {
            currentUser.setStatus(UserStatus.START);
            currentUser.setFirstname(message.getFrom().getFirstName());
            currentUser.setLastname(message.getFrom().getLastName());
        } else {
            currentUser.setStatus(UserStatus.SEARCH_MUSIC);
        }
        return currentUser;
    }


    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User currentUser = UserSetStatus(chatId, message);
            if (currentUser.getStatus().equals(UserStatus.START)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("enter music name");
                sendMessage.setChatId(chatId);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (currentUser.getStatus().equals(UserStatus.SEARCH_MUSIC)) {
                SpotifyApi spotifyApi = null;
                try {
                    spotifyApi = new SpotifyApi.Builder()
                            .setClientId(SPOTIFY_CLIENT_ID)
                            .setClientSecret(SPOTIFY_CLIENT_SECRET)
                            .setAccessToken(Spotify.AccessToken())
                            .setRedirectUri(URI.create(url))
                            .build();
                } catch (IOException | ParseException | SpotifyWebApiException e) {
                    throw new RuntimeException(e);
                }
                SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(message.getText()).build();
                Paging<Track> tracks = null;
                try {
                    tracks = searchTracksRequest.execute();
                    for (int i = 0; i < tracks.getItems().length; i++) {
                        if (i == 5) {
                            break;
                        }
                        InputFile audio = new InputFile();
                        String filename = tracks.getItems()[i].getArtists()[0].getName() + "-" + tracks.getItems()[i].getName();
                        try {
                            audio = new InputFile(new URL(tracks.getItems()[i].getPreviewUrl()).openStream(), filename);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SendAudio sendAudio = new SendAudio();
                        sendAudio.setChatId(chatId);
                        sendAudio.setAudio(audio);
                        try {
                            execute(sendAudio);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                    }
                } catch (IOException | SpotifyWebApiException | ParseException e) {
                    throw new RuntimeException(e);
                }

            }

        }
        System.out.println(users.toString());
    }
}


