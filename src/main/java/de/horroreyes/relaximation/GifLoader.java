package de.horroreyes.relaximation;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GifLoader {

    private static final Logger log = Logger.getInstance(GifLoader.class);

    private static final String API_KEY = "IWBW9Q86UTOG";

    private final AtomicInteger currentGif = new AtomicInteger();

    AtomicReference<JSONObject> gifs = new AtomicReference<>();

    RelaximationSettingsState settings = ServiceManager.getService(RelaximationSettingsState.class);
    Runnable thread = () -> {
        JSONObject searchResult = getSearchResults(10);
        gifs.set(searchResult);
    };

    public GifLoader() {
        currentGif.set(0);
        new Thread(thread).start();
    }

    public URL getNextGif() throws MalformedURLException {
        log.warn("getting new");
        if (this.gifs.get() == null || this.gifs.get().getJSONArray("results").length() <= this.currentGif.get()) {
            this.getGifs();
            this.currentGif.set(0);
        }
        JSONObject gif = this.gifs.get().getJSONArray("results").getJSONObject(this.currentGif.get());
        this.currentGif.set(this.currentGif.get() + 1);
        new Thread(thread).start();
        log.warn("The NEW");
        return new URL(gif.getJSONArray("media").getJSONObject(0).getJSONObject(
                "gif").getString("url"));

    }

    public JSONObject getGifs() {
        while (gifs.get() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return gifs.get();
    }

    /**
     * Get Search Result GIFs
     */
    public JSONObject getSearchResults(int limit) {

        // make search request - using default locale of EN_US

        final String url;
        try {
            url = String.format("https://api.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s",
                    URLEncoder.encode(settings.searchString, StandardCharsets.UTF_8.toString()), API_KEY, limit);
            return get(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Construct and run a GET request
     */
    private JSONObject get(String url) {
        HttpURLConnection connection = null;
        try {
            // Get request
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Handle failure
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK && statusCode != HttpURLConnection.HTTP_CREATED) {
                String error = String.format("HTTP Code: '%1$s' from '%2$s'", statusCode, url);
                throw new ConnectException(error);
            }

            // Parse response
            return parser(connection);
        } catch (Exception e) {
            log.error("Something happened: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new JSONObject("");
    }

    /**
     * Parse the response into JSONObject
     */
    private JSONObject parser(HttpURLConnection connection) {
        char[] buffer = new char[1024 * 4];
        int n;
        try (InputStream stream = new BufferedInputStream(connection.getInputStream())) {
            InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            StringWriter writer = new StringWriter();
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            return new JSONObject(writer.toString());
        } catch (IOException e) {
            log.error("Something happened: ", e);
        }
        return new JSONObject("");
    }

}
