import java.net.*;
import java.util.*;
public class WebCrawler {
    public static void main(String [] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a URL: ");
        String urlInput = input.nextLine();
        crawler(urlInput);
    }

    public static void crawler( String startURL){
        ArrayList<String> pendingURLs = new ArrayList<>();
        ArrayList<String> traversedURLs = new ArrayList<>();
        int counter = 0;
        pendingURLs.add(startURL);
        while (!pendingURLs.isEmpty() && traversedURLs.size() <= 100){
            String urlString = pendingURLs.remove(0);
            if (!traversedURLs.contains(urlString)){
                traversedURLs.add(urlString);
                // --------------------- added this to find occurrences of a word in a URL -----------------------
                if (urlString.contains("developer")) {
                    counter++;
                    System.out.println("URL Containing Term: " + urlString);
                }
                else
                    System.out.println("URL Not Containing Term: " + urlString);
                // -----------------------------------------------------------------------------------------------
                for (String s : getSubURLs(urlString)) {
                    if (!traversedURLs.contains(s))
                        pendingURLs.add(s);
                }
                System.out.println(counter);
            }
        }
    }

    public static ArrayList<String> getSubURLs(String urlString) {
        ArrayList<String> list = new ArrayList<>();

        try {
            URL url = new URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            String word = "computer";
            while (input.hasNext()){
                String line = input.nextLine();
//                System.out.println("-"+line);
                current = line.indexOf("https:",current);
                while (current > 0){
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) {
                        list.add(line.substring(current,endIndex));
                        current = line.indexOf("https:", endIndex);
                    }
                    else {
                        current = -1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
