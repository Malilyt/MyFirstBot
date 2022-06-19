package bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class Rate {
    String eur;
    String usd;


    String output() throws IOException {
        parserUSD();
        parserEUR();

        return "1$ = " + usd +"\n1€ = " + eur;
    }

    void parserEUR() throws IOException {
        Document doc = Jsoup.connect("https://cbr.ru").get();
        Elements USD = doc.getElementsByAttributeValue("class", "col-md-2 col-xs-9 _right mono-num");
        String ratEUR = USD.get(3).text();

        eur = ratEUR;


    }


    void  parserUSD() throws IOException {
        Document doc = Jsoup.connect("https://cbr.ru").get();
        Elements USD = doc.getElementsByAttributeValue("class", "col-md-2 col-xs-9 _right mono-num");
        String ratUSD = USD.get(1).text();


        usd = ratUSD;


    }
}
