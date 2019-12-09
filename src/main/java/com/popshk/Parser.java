package com.popshk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Parser extends Thread {
    private final Document indexPage = getPage();
    private final Elements content = indexPage.getElementsByAttributeValue("data-test-id","ProductTile");
    private CreateJson createJson = new CreateJson();
    private Products products = new Products();

    int countHttp = 1;
    int countProduct = 0;
    @Override
    public void run() {
        System.out.println("Parse...");
        while (! Thread.currentThread().isInterrupted()) {
            try {
                Pars(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("triggered HTTP request: "+countHttp +" , extracted products : "+countProduct);
            String os = System.getProperty("os.name").toLowerCase();
            if(os.indexOf("win")>=0) System.out.println("Windows - check result file in user directory");
            if(os.indexOf("nux")>=0 || os.indexOf("nux")>=0) System.out.println("Linux - check result file in home directory");
            interrupt();
        }
    }

    private Document getPage(){
        final String url ="https://www.aboutyou.de/maenner/bekleidung";
        Document page = null;
        try {
            page = Jsoup.connect(url).userAgent("Chrome/74.0.3729.169 Safari/537.36").referrer("http://www.google.com").maxBodySize(0).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        countHttp++;
        return page;
    }

    private Document getPage(String link){
        final String url ="https://www.aboutyou.de";
        Document page = null;
        try {
            page = Jsoup.connect(url + link).userAgent("Chrome/74.0.3729.169 Safari/537.36").referrer("http://www.google.com").maxBodySize(0).timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        countHttp++;
        return page;
    }

    private void otherVariants (Document page,String link) {
        Element bb = page.getElementsByAttributeValue("data-test-id", "BuyBox").first();
        Element otherColors = bb.getElementsByAttributeValue("data-test-id", "ThumbnailsList").first();
        Elements otherColorsHref = otherColors.getElementsByTag("a");
        for (Element e: otherColorsHref) {
            if (! e.attr("href").equals(link)){
                Pars(e);
            }else {
                continue;
            }
        }
    }

    public void Pars(Elements content) throws IOException {

        for (int i = 0; i < content.size(); i++) {
            products.reset();
            Element product = content.get(i);
            String link = product.attr("href");
            Document page = getPage(link);
            countProduct++;
            Element bb = page.getElementsByAttributeValue("data-test-id", "BuyBox").first();
            Element bl = bb.getElementsByAttributeValue("data-test-id", "BrandLogo").first();
            String brand = bl.attr("alt");

            Element name = bb.getElementsByAttributeValue("data-test-id", "ProductName").first();
            String productName = name.text();

            Element pr;
            String price;
            try {
                pr = bb.getElementsByAttributeValue("data-test-id", "ProductPriceFormattedBasePrice").first();
                price = pr.text();
            } catch (NullPointerException n) {
                pr = bb.getElementsByAttributeValue("data-test-id", "StrikeFormattedBasePrice").first();
                price = pr.text();
            }

            Element detail = page.getElementsByAttributeValue("data-test-id", "ProductDetails").first();
            Element bc = detail.getElementsByAttributeValue("data-test-id", "BrandNameProductNameColorName").first();
            String color = bc.text().replace(brand, "").replace(productName, "").replace("in ", "").trim();

            Element art = detail.getElementsByAttributeValue("data-test-id", "ArticleNumber").first();
            String articleID = art.text();
            products.add(new ProductData(articleID, productName, brand, price, color));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            otherVariants(page,link);
            createJson.writeToXml(products);
            //System.out.println("____________________________________________________________________________________________________");
        }
    }
    public void Pars(Element content) {
        countProduct++;
        String link = content.attr("href");
        Document page = getPage(link);

        Element bb = page.getElementsByAttributeValue("data-test-id", "BuyBox").first();
        Element bl = bb.getElementsByAttributeValue("data-test-id", "BrandLogo").first();
        String brand = bl.attr("alt");

        Element name = bb.getElementsByAttributeValue("data-test-id", "ProductName").first();
        String productName = name.text();

        Element pr;
        String price;
        try {
            pr = bb.getElementsByAttributeValue("data-test-id", "ProductPriceFormattedBasePrice").first();
            price = pr.text();
        } catch (NullPointerException n) {
            pr = bb.getElementsByAttributeValue("data-test-id", "StrikeFormattedBasePrice").first();
            price = pr.text();
        }

        Element detail = page.getElementsByAttributeValue("data-test-id", "ProductDetails").first();
        Element bc = detail.getElementsByAttributeValue("data-test-id", "BrandNameProductNameColorName").first();
        String color = bc.text().replace(brand, "").replace(productName, "").replace("in ", "").trim();

        Element art = detail.getElementsByAttributeValue("data-test-id", "ArticleNumber").first();
        String articleID = art.text();
        products.add(new ProductData(articleID, productName, brand, price, color));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
