package br.com.kauai.apifunctionaltest.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao(){
        WebDriver webDriver = new ChromeDriver();
        webDriver.navigate().to("http://localhost:8001/tasks");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return webDriver;
    }

    @Test
    public void deveriaSalvarTarefaComSucesso() {

        //Acessando o domínio
        WebDriver webDriver = acessarAplicacao();

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
    public void naoDeveriaSalvarTarefaSemDescricao() {
        WebDriver webDriver = acessarAplicacao();

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
    public void naoDeveriaSalvarTarefaSemData() {
        WebDriver webDriver = acessarAplicacao();

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
    public void naoDeveriaSalvarTarefaComDataPassada() {
        WebDriver webDriver = acessarAplicacao();

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
    public void naoDeveriaSalvarTarefaSemDescricaoEData() {
        WebDriver webDriver = acessarAplicacao();

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
