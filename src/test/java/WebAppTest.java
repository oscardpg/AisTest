import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.codeurjc.ais.tictactoe.WebApp;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebAppTest{

	protected WebDriver wd1;
	protected WebDriver wd2;

	
/*Before and After***********************************************/	
	
	@BeforeClass
	public static void setupClass(){		
		//Lo primero que se ejecuta
		//System.setProperty("webdriver.gecko.driver","\\tic-tac-toe-enunciado");
		WebDriverManager.firefoxdriver().setup();		
		WebApp.start();
	}	
	
	@Before
	public void setup(){
		//Antes de cada test
		//Creamos los WebDriver
		wd1 = new FirefoxDriver();
		wd2 = new FirefoxDriver();
		
		WebDriverWait wdw1 = new WebDriverWait(wd1, 50);
		WebDriverWait wdw2 = new WebDriverWait(wd2, 50);
		
		wd1.get("http://localhost:8081");
		wd2.get("http://localhost:8081");		
		
		wdw1.until(ExpectedConditions.elementToBeClickable(By.id("nickname")));
		wdw2.until(ExpectedConditions.elementToBeClickable(By.id("nickname")));
		wdw1.until(ExpectedConditions.elementToBeClickable(By.id("startBtn")));
		wdw2.until(ExpectedConditions.elementToBeClickable(By.id("startBtn")));

		wd1.findElement(By.id("nickname")).sendKeys("JugadorUno");
		wd1.findElement(By.id("startBtn")).click();

		wd2.findElement(By.id("nickname")).sendKeys("JugadorDos");
		wd2.findElement(By.id("startBtn")).click();		
	}	
	
	@After
	public void teardown(){
		//Despues de cada test
		//Cerramos las WebDriver
		if(wd1 != null){
			wd1.quit();
		}
		if(wd2 != null){
			wd2.quit();
		}
	}
	
	@AfterClass
	public static void teardownClass(){
		//Al final del todo
		WebApp.stop();
	}

/*Test*******************************************************/
	
	@Test
	public void testWinnerJugador1() throws InterruptedException{
		//Marcamos las casillas para simular el juego 		
		wd1.findElement(By.id("cell-0")).click();
		wd2.findElement(By.id("cell-3")).click();
		wd1.findElement(By.id("cell-1")).click();
		wd2.findElement(By.id("cell-4")).click();
		wd1.findElement(By.id("cell-2")).click();
		//Comprobamos los mensajes para verificar el resultado del test
		assertEquals(wd1.switchTo().alert().getText(), "JugadorUno wins! JugadorDos looses.");
		assertEquals(wd2.switchTo().alert().getText(), "JugadorUno wins! JugadorDos looses.");
	}
	
	@Test
	public void testWinnerJugador2() throws InterruptedException{
		//Marcamos  las casillas para simular el juego
		wd1.findElement(By.id("cell-0")).click();
		wd2.findElement(By.id("cell-3")).click();
		wd1.findElement(By.id("cell-1")).click();
		wd2.findElement(By.id("cell-4")).click();
		wd1.findElement(By.id("cell-6")).click();
		wd2.findElement(By.id("cell-5")).click();
		//Comprobamos los mensajes para verificar el resultado del test
		assertEquals(wd2.switchTo().alert().getText(), "JugadorDos wins! JugadorUno looses.");
		assertEquals(wd1.switchTo().alert().getText(), "JugadorDos wins! JugadorUno looses.");
	}
	
	@Test
	public void testDraw() throws InterruptedException{
		//Marcamos  las casillas para simular el juego		
		wd1.findElement(By.id("cell-0")).click();
		wd2.findElement(By.id("cell-3")).click();
		wd1.findElement(By.id("cell-1")).click();
		wd2.findElement(By.id("cell-4")).click();
		wd1.findElement(By.id("cell-5")).click();
		wd2.findElement(By.id("cell-2")).click();
		wd1.findElement(By.id("cell-6")).click();
		wd2.findElement(By.id("cell-7")).click();
		wd1.findElement(By.id("cell-8")).click();
		//Comprobamos los mensajes para verificar el resultado del test
		assertEquals(wd1.switchTo().alert().getText(), "Draw!");
		assertEquals(wd2.switchTo().alert().getText(), "Draw!");
	}
}