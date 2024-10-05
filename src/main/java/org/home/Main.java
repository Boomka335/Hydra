package org.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Download> downloads = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> processedUrls = new HashSet<>();
        String baseUrl = "https://itorrents-igruha.org/new-games-pc/";
        String url1 = "https://itorrents-igruha.org";

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("d.M.yyyy")) // Для формата 6.05.2024
                .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy")) // Для формата 31.07.2024
                .toFormatter();


        try {
            for (int page = 1; page <= 299; page++){
                String url = page == 1 ? baseUrl : baseUrl + "page/" + page + "/";
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("a.short-img22");

                for (Element element : elements) {
                    String link = element.attr("href");
                    if (processedUrls.add(link)) {
                        Document document2 = Jsoup.connect(url1 + link).get();
                        Elements elements2 = document2.select("div.module-title h1");
                        Elements sizeElements = document2.select(":contains(Размер)");
                        String diskSpace = "";
                        Element timeElement = document2.select("time.published").first();

                        for (Element sizeElement : sizeElements) {
                            diskSpace = sizeElement.text();
                        }

                        for (Element element2 : elements2){
                            String title = element2.text() + " ";
                            String[] uris = { url1 + link };
                            String formattedDate = "";

                            if (timeElement != null){
                                String datetime = timeElement.attr("datetime"); // "13.06.2024"
                                LocalDate dateTime = LocalDate.parse(datetime, formatter);
                                formattedDate = dateTime.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
                            }

                            downloads.add(new Download(title, uris, formattedDate, diskSpace));
                        }
                    }
                    DownloadList downloadList = new DownloadList("Torrent Igruha", downloads);
                    objectMapper.writeValue(new File("downloads.json"), downloadList);
                }
                System.out.println("Создано " + page);
            }



        } catch (IOException e) {
            System.err.println("Ошибка при получении страницы: " + e.getMessage());
        }
    }
}