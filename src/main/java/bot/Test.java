package bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.io.IOException;

public class Test {

    public static void main(String args[]) throws IOException {

        Document doc = Jsoup.connect("https://cbr.ru").get();
        Elements USD = doc.getElementsByAttributeValue("class", "col-md-2 col-xs-9 _right mono-num");
        System.out.println(USD.get(1).text());



//        USD.forEach(USd -> System.out.println(USd));

    }
}
