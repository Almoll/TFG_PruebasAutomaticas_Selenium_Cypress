package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ProxyProvider {

    private static final String API_URL =
            "https://api.proxyscrape.com/v2/?request=getproxies&protocol=http&timeout=5000&country=es";

    public static String getRandomProxy() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            List<String> proxies = new ArrayList<>();

            String line;
            while ((line = in.readLine()) != null) {
                if (!line.isEmpty()) proxies.add(line.trim());
            }
            in.close();

            if (proxies.isEmpty()) {
                System.out.println("⚠ No se encontraron proxies en ProxyScrape");
                return null;
            }

            Random random = new Random();
            String chosen = proxies.get(random.nextInt(proxies.size()));

            System.out.println("🔄 Proxy seleccionado: " + chosen);
            return chosen;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
