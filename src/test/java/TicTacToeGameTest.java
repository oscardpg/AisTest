import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import es.codeurjc.ais.tictactoe.*;

@DisplayName("Pruebas de dobles de la clase TicTacToeGame")
public class TicTacToeGameTest{
	
	private static TicTacToeGame partida;
	private static Player j1;
	private static Player j2;	
	private static Connection c1;
	private static Connection c2;

/*Before and After***********************************************/		
	
	@BeforeAll
	protected static void setUp(){
		//Antes de todo
		//Aprovechamos el metodo que hemos creado en la clase BoardTest
		//para preparar el juego
		BoardTest.prepararTest();
		j1 = BoardTest.j1;
		j2 = BoardTest.j2;
		c1 = mock(Connection.class);	
		c2 = mock(Connection.class);	
	}
	
	@BeforeEach
	private void startGame(){
		//Antes de cada test
		//Creamos un juego para restablecer la partida
		partida = new TicTacToeGame();
		BoardTest.partida = partida;
		
		//agregamos las conexiones de Mockito
		partida.addConnection(c1);
		partida.addConnection(c2);
		
		//agragamos los players
		partida.addPlayer(j1);
		partida.addPlayer(j2);
	}
	
	@AfterEach
	private void endConnection(){
		//Despues de cada test
		//Reseteamos las conexiones de Mockito
		reset(c1);
		reset(c2);
	}

/*Test*******************************************************/		
	
	@Test
	@DisplayName("Prueba de conexion")
	void testConnection(){	
		System.out.println("Probamos la conexion de los jugadores");		
		System.out.println("\tProbamos la conexion 1");
		
		//capturar evento
		ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
		verify(c1,times(2)).sendEvent(eq(EventType.JOIN_GAME),argument.capture());
		System.out.println("\t\tJOIN_GAME capturado correctamente");
		
		//obtener valor
		List<Player> lJugadores = argument.getAllValues();
		List<Player> lPlayers = new LinkedList<>();
		lPlayers.add(j1);
		lPlayers.add(j2);
		
		//comprobar valor
		Assertions.assertEquals(lJugadores.get(0),lPlayers);
		Assertions.assertEquals(lJugadores.get(1),lPlayers);
		
		System.out.println("\t\tObtenemos los jugadores correctamente");
		System.out.println("\tProbamos la conexion 2");
		
		//capturar evento 
		verify(c2,times(2)).sendEvent(eq(EventType.JOIN_GAME),argument.capture());
		System.out.println("\t\tJOIN_GAME capturado correctamente");
		
		//obtener valor
		lJugadores = argument.getAllValues();
		
		//comprobar valor
		Assertions.assertEquals(lJugadores.get(0),lPlayers);
		Assertions.assertEquals(lJugadores.get(1),lPlayers);
		System.out.println("\t\tObtenemos los dos Jugadores correctamente");
		System.out.println("\tLa prueba de conexion ha salido bien");
	}
	
	@Test
	@DisplayName("Prueba de CheckTurn")
	void checkTurn(){
		System.out.println("Probamos el CheckTurn");
		
		//capturar evento
		ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
		BoardTest.marcarFichasTablero(3);
		verify(c1,times(9)).sendEvent(eq(EventType.SET_TURN), argument.capture());
		System.out.println("\tSET_TURN capturado correctamente");
		
		//obtener datos
		List<Player> lJugadores = argument.getAllValues();
		Player player;

		//comprobar
		for(int i = 0; i < lJugadores.size(); i++){
			if(i % 2 == 0){
				player = j1;
			}else{
				player = j2;
			}
			Assertions.assertEquals(lJugadores.get(i),player);
		}
		System.out.println("\tObtenemos los jugadores bien");
		
		//capturar evento
		ArgumentCaptor<WinnerValue> arg = ArgumentCaptor.forClass(WinnerValue.class);
		verify(c1,times(1)).sendEvent(eq(EventType.GAME_OVER),arg.capture());
		System.out.println("\tGAME_OVER obtenido correctamente");
		
		//obtener datos
		List<WinnerValue> res = arg.getAllValues();
		
		//comprobar
		Assertions.assertEquals(res.get(0).player,j1);
		System.out.println("\tRecibimos bien el ganador");
		System.out.println("\tLa prueba de CheckTurn ha finalizado correctamente");
	}
	
	@Test
	@DisplayName("Prueba de Mark")
	void testMark(){
		
		System.out.println("Probamos el Mark");

		//capturar evento
		
		ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
		BoardTest.marcarFichasTablero(1);
		verify(c1,times(5)).sendEvent(eq(EventType.MARK), argument.capture());
		System.out.println("\tJugador 1 gana, MArk recibido correctamente");
		
		reset(c1);
		startGame();
		BoardTest.marcarFichasTablero(0);
		verify(c1,times(9)).sendEvent(eq(EventType.MARK), argument.capture());
		System.out.println("\tHan empatado, Mark recibido correctamente");
		
		reset(c1);
		startGame();
		BoardTest.marcarFichasTablero(2);
		verify(c1,times(6)).sendEvent(eq(EventType.MARK), argument.capture());
		System.out.println("\tJugador 2 gana, MArk recibido correctamente");

		System.out.println("\tEl test de Mark ha resultado correcto");
	}
	
	@Test
	@DisplayName("Prueba de CheckWinnerMessage")
	void testCheckWinnerMessage(){
		System.out.println("Probamos CheckWinnerMessage");
		BoardTest.marcarFichasTablero(1);
		
		//capturar evento
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(c1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		System.out.println("\tGAME_OVER capturador correctamente");
		
		//obtener los valores
		List<WinnerValue> res = argument.getAllValues();
		Assertions.assertTrue(res.get(0).player.equals(j1));
		System.out.println("\tObtenemos el ganador de forma correcta");
		
		//comprobar
		Assertions.assertFalse(res.get(0).player.equals(j2));
		System.out.println("\tLa prueba de CheckWinnerMessage ha salido satisfactoria");
	}
	
	
	@Test
	@DisplayName("Prueba de CheckDrawMessage")
	void testCheckDrawMessage(){
		System.out.println("Empezando prueba del empate");
		BoardTest.marcarFichasTablero(0);
		
		//capturar evento
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(c1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		System.out.println("\tGAME_OVER capturador correctamente");
		
		//obtener los valores
		List<WinnerValue> res = argument.getAllValues();
		Assertions.assertNull(res.get(0));
		System.out.println("\tExito en la obtención del empate");
		
		//comprobar
		System.out.println("\tPrueba del empate superada");
	}
	
	@Test
	@DisplayName("Prueba de CheckLoserMessage")
	void testCheckLoserMessage(){
		System.out.println("Empezando prueba de la derrota");
		BoardTest.marcarFichasTablero(2);
		
		//capturar evento
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(c1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		System.out.println("\tGAME_OVER capturador correctamente");
		
		//obtener los valores
		List<WinnerValue> res = argument.getAllValues();
		Assertions.assertTrue(res.get(0).player.equals(j2));
		System.out.println("\tExito en la obtención del ganador");
		
		//comprobar
		Assertions.assertFalse(res.get(0).player.equals(j1));
		System.out.println("\tPrueba de derrota superada");
	}
}