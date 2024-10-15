package fc;

import java.io.*;
import java.util.*;

public class Cookie {

    private List<String> cookies;

    public Cookie(String fileName) throws IOException {
        this.cookies = new ArrayList<>();
        loadCookiesFromFile(fileName);
    }

    private void loadCookiesFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            cookies.add(line);
        }
        reader.close();
    }

    public String getRandomCookie() {
        Random random = new Random();
        int index = random.nextInt(cookies.size());
        return cookies.get(index);
    }
}
