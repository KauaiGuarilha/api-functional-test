package br.com.kauai.apifunctionaltest.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver startApplication() throws MalformedURLException {
        //WebDriver webDriver = new ChromeDriver();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        WebDriver webDriver = new RemoteWebDriver(new URL("http://192.168.0.111:4444/wd/hub"), capabilities);
        webDriver.navigate().to("http://192.168.0.111:8001/tasks");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return webDriver;
    }

    @Test
    public void shouldReturnTasksSuccessfully() throws MalformedURLException {

        //Acessando o domínio
        WebDriver webDriver = startApplication();

        try {
            //Clicar em Add Todo
            webDriver.findElement(By.id("addTodo")).click();

            //Escrever descrição
            webDriver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever a data
            webDriver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            //Salvar cadastro
            webDriver.findElement(By.id("saveButton")).click();

            //Receber/Validar mensagem de sucesso
            String message = webDriver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

            //Fechar o browser
            webDriver.quit();
        } finally {

            //Fechar o browser
            webDriver.quit();
        }
    }

    @Test
    public void shouldntAddTasksWithoutTasks() throws MalformedURLException {
        WebDriver webDriver = startApplication();

        try {
            webDriver.findElement(By.id("addTodo")).click();
            webDriver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            webDriver.findElement(By.id("saveButton")).click();

            String message = webDriver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);

            webDriver.quit();
        } finally {

            webDriver.quit();
        }
    }

    @Test
    public void shouldNotAddWithDueDateNull() throws MalformedURLException {
        WebDriver webDriver = startApplication();

        try {
            webDriver.findElement(By.id("addTodo")).click();
            webDriver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            webDriver.findElement(By.id("saveButton")).click();

            String message = webDriver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);

            webDriver.quit();
        } finally {

            webDriver.quit();
        }
    }

    @Test
    public void shouldNotReturnTasksWithAnInvalidDate() throws MalformedURLException {
        WebDriver webDriver = startApplication();

        try {
            webDriver.findElement(By.id("addTodo")).click();
            webDriver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            webDriver.findElement(By.id("dueDate")).sendKeys("10/10/1996");
            webDriver.findElement(By.id("saveButton")).click();

            String message = webDriver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);

            webDriver.quit();
        } finally {

            webDriver.quit();
        }
    }

    @Test
    public void shouldNotAddWithTasksAndDateNull() throws MalformedURLException {
        WebDriver webDriver = startApplication();

        try {
            webDriver.findElement(By.id("addTodo")).click();
            webDriver.findElement(By.id("saveButton")).click();

            String message = webDriver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);

            webDriver.quit();
        } finally {

            webDriver.quit();
        }
    }
}
