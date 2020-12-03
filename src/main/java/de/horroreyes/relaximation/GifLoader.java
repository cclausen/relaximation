package de.horroreyes.relaximation;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GifLoader {

    private static final Logger log = Logger.getInstance(GifLoader.class);

    private static final String API_KEY = "IWBW9Q86UTOG";

    private final AtomicInteger currentGif = new AtomicInteger();
    private final AtomicInteger currentGifTotal = new AtomicInteger();

    private final AtomicReference<String> nextGifs = new AtomicReference<>();

    private final AtomicReference<JSONObject> gifs = new AtomicReference<>();
    private final AtomicReference<String> searchString = new AtomicReference<>();
    private final Random random = new Random();

    private final RelaximationSettingsState settings = ServiceManager.getService(RelaximationSettingsState.class);

    private final Runnable thread = () -> {
        JSONObject searchResult = getSearchResults(10);
        gifs.set(searchResult);
    };

    public URL getNextGif() throws MalformedURLException, InterruptedException {
        if (this.gifs.get() == null || this.currentGifTotal.get() >= settings.loopSize) {
            this.gifs.set(null);
            this.currentGif.set(0);
            this.currentGifTotal.set(0);
            this.searchString.set(this.getNextSearchString());
            this.nextGifs.set(null);
            new Thread(thread).start();
        }
        if (this.gifs.get() == null || this.gifs.get().getJSONArray("results").length() <= this.currentGif.get()) {
            this.getGifs();
            this.currentGif.set(0);
            this.nextGifs.set(this.gifs.get().getString("next"));
        }
        JSONObject gif = this.gifs.get().getJSONArray("results").getJSONObject(this.currentGif.get());
        this.currentGif.set(this.currentGif.get() + 1);
        currentGifTotal.set(this.currentGifTotal.get() + 1);
        return new URL(gif.getJSONArray("media").getJSONObject(0).getJSONObject(
                "gif").getString("url"));

    }

    private String getNextSearchString() {
        if (settings.combination.equals("Alle Keywords kombinieren")) {
            return settings.searchString;
        }
        if (settings.combination.equals("Alle Keywords zufällig kombinieren")) {
            return getRandomSearchString();
        }
        if (settings.combination.equals("Alle Keywords einzeln nacheinander")) {
            return getSequentialSearchString();
        }
        return "error";
    }

    private String getSequentialSearchString() {
        String[] strings = settings.searchString.split(" ");
        if (searchString.get() == null) {
            return strings[0];
        } else {
            boolean next = false;
            for (String string : strings) {
                if (next) {
                    return string;
                }
                if (string.equals(searchString.get())) {
                    next = true;
                }
            }
        }
        return "error";
    }

    @NotNull
    private String getRandomSearchString() {
        List<String> list = new ArrayList<>(Arrays.asList(settings.searchString.split(" ")));
        int howMany = random.nextInt(list.size()) + 1;
        ArrayList<String> result = new ArrayList<>();
        while (result.size() < howMany) {
            int index = random.nextInt(list.size());
            result.add(list.get(index));
            list.remove(index);
        }
        return String.join(" ", result);
    }

    public void getGifs() throws InterruptedException {
        while (gifs.get() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Thread dead...", e);
                throw e;
            }
        }
    }

    /**
     * Get Search Result GIFs
     */
    public JSONObject getSearchResults(int limit) {

        // make search request - using default locale of EN_US

        final String url;
        try {
            if (nextGifs.get() == null) {
                url = String.format("https://api.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s",
                        URLEncoder.encode(searchString.get(), StandardCharsets.UTF_8.toString()), API_KEY,
                        limit);
            } else {
                url = String.format("https://api.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s&pos=%4$s",
                        URLEncoder.encode(searchString.get(), StandardCharsets.UTF_8.toString()), API_KEY,
                        limit, nextGifs.get());
            }
            log.warn(url);
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
