package org.example.Spotify;

import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;

import java.io.IOException;

public class Spotify {
    private static final String SPOTIFY_CLIENT_ID = "113481984889495695877b84a0396c90";
    private static final String SPOTIFY_CLIENT_SECRET = "af1048233fb446c7acd01ea32064db55";

    public static String AccessToken() throws IOException, ParseException, SpotifyWebApiException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(SPOTIFY_CLIENT_ID)
                .setClientSecret(SPOTIFY_CLIENT_SECRET)
                .build();
        try {
            final ClientCredentials clientCredentials = spotifyApi.clientCredentials().build().execute();
            return clientCredentials.getAccessToken();
        } catch (SpotifyWebApiException e) {
            return "null";
        }
    }
}
