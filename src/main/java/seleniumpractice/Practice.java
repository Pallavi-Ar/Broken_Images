package seleniumpractice;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.List;

public class Practice {
    //creating a static variable for number of invalid images
    public static int invalidImages = 0;

    //function to check for broken images
    public static void checkImage(WebElement img) {
        try {
            //creating a get request using HttpClient
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet req = new HttpGet(img.getAttribute("src"));
            //executing the get request
            CloseableHttpResponse response = client.execute(req);
            //if the status code of the image is not equal to 200, it means that the image is broken
            if(response.getCode()!=200) {
                //increasing the count of invalid images
                invalidImages++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //setting the properties and WebDriver as ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pallavi.Arora\\Downloads\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //maximizing the window
        driver.manage().window().maximize();

        //opening the url
        driver.get("http://the-internet.herokuapp.com/broken_images");

        //waiting for 10 seconds for the page to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            //creating a list of WebElements by the tagname 'img'
            List<WebElement> images = driver.findElements(By.tagName("img"));
            //printing the total number of images on the url
            System.out.println("Total number of images : " + images.size());
            //traversing the list of images to find broken images
            for(WebElement img : images) {
                if(img!=null) {
                    checkImage(img);
                }
            }
            //printing the number of broken images
            System.out.println("Number of broken images : " + invalidImages);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //closing the instance
        driver.quit();
    }
}
