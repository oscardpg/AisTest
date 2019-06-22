import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.codeurjc.ais.tictactoe.*;


public class BoardTest{
	
	protected static TicTacToeGame partida;
	protected static Player j1;
	protected static Player j2;
	private Board tablero;
	
/*Before and After***********************************************/		
	
	@BeforeAll
	protected static void prepararTest(){		
		//inicializamos los jugadores antes de nada		
		j1 = new Player(10,"X", "Jugador 1");
		j2 = new Player(11,"O", "Jugador 2");
	}	
	
	@BeforeEach
	protected void empezarJuego(){		
		//Antes de cada test creamos el juego 
		//y agregamos los jugadores		
		partida = new TicTacToeGame();
		partida.addPlayer(j1);
		partida.addPlayer(j2);
	}
	
/*Test********************************************************************/		
/*CheckDraw*****************************************************/	
	@Test
	void testCheckDrawJugador1(){
		//Probamos CheckDraw() cuando gana el jugador 1
		marcarFichasTablero(1);
		tablero = partida.getBoard();
		// comprobamos el resultado
		System.out.println("Probamos CheckDrawJugador: Jugador 1 gana");
		assertTrue(tablero.checkDraw() == false);
		System.out.println("Jugador 1 gana");
	}
	
	@Test
	void testCheckDrawJugador2(){
		//Probamos CheckDraw() cuando gana el jugador 2
		marcarFichasTablero(2);
		tablero = partida.getBoard();
		//comprobamos el resultado
		System.out.println("Probamos CheckDrawJugador: Jugador 2 gana");
		assertTrue(tablero.checkDraw() == false);
		System.out.println("Jugador 2 gana");
	}
	
	@Test
	void testCheckDrawEmpate(){
		//Probamos CheckDraw() cuando empatan
		marcarFichasTablero(0);
		tablero = partida.getBoard();
		//comprobamos el resultado
		System.out.println("Probamos CheckDrawJugador: Empate");
		assertTrue(tablero.checkDraw());
		System.out.println("Han Empatado");
	}

/*GetCellsIfWinner***********************************************************/
	@Test
	void testGetCellsIfWinnerJugador1(){
		//Probamos GetCellsIfWinner() cuando gana el jugador 1
		marcarFichasTablero(1);
		tablero = partida.getBoard();
		// comprobamos el resultado
		System.out.println("Probamos GetCellsIfWinner: Jugador 1 gana");
		assertNotNull(tablero.getCellsIfWinner(j1.getLabel()));
		assertNull(tablero.getCellsIfWinner(j2.getLabel()));
		System.out.println("Jugador 1 gana");
	}
	
	@Test
	void testGetCellsIfWinnerJugador2(){
		//Probamos GetCellsIfWinner() cuando gana el jugador 2
		marcarFichasTablero(2);
		tablero = partida.getBoard();		
		//comprobamos el resultado
		System.out.println("Probamos GetCellsIfWinner: Jugador 2 gana");
		assertNotNull(tablero.getCellsIfWinner(j2.getLabel()));
		assertNull(tablero.getCellsIfWinner(j1.getLabel()));
		System.out.println("Jugador 2 gana");
	}
	
	@Test
	void testGetCellsIfWinnerEmpate(){
		//Probamos GetCellsIfWinner() cuando empatan
		marcarFichasTablero(0);
		tablero = partida.getBoard();
		//comprobamos el resultado
		System.out.println("Probamos GetCellsIfWinner: Empate");
		assertNull(tablero.getCellsIfWinner(j1.getLabel()));
		assertNull(tablero.getCellsIfWinner(j2.getLabel()));
		System.out.println("Han empatado");
	}
	
/*Métodos necesarios para el correcto funcinamiento************************************************/
	protected static void marcarFichasTablero(int caso){
		//Marcamos las fichas a nuestro antojo para probar los test
		int m1,m2;
		switch(caso){
			case 0:
					//X O X	 		
					//X X O
					//O X O
				for(int i = 0; i< 9; i++) {
					if(partida.checkTurn(10)){
						if(i == 0 || i==2|| i==4 || i== 7){
							partida.mark(i);
						}else {
							if(i==6){
								partida.mark(3);
							}else if(i==8){
								partida.mark(7);
							}
						}
					}else{
						if(i==1 || i==5){
							partida.mark(i);
						}else{
							if(i==3){
								partida.mark(6);
							}else if(i==7){
								partida.mark(8);
							}
						}
					}
				}break;
			case 1:
					//X X X
					//O O -
					//- - -
				m1=-1;
				m2=2;
				for(int i=0; i < 6; i++){
					if(partida.checkTurn(10)){
						m1 = m1+1;
						partida.mark(m1);
					}else{
						m2= m2+1;
						partida.mark(m2);
					}				
				}break;
			case 2:
					//O O O
					//- X -
					//X - X
				m1=10;
				m2=-1;
				for(int i=0; i < 6; i++){
					if(partida.checkTurn(11)){
						m2 = m2+1;
						partida.mark(m2);
					}else{
						m1= m1-2;
						partida.mark(m1);
					}					
				}break;
			case 3:
				//X O X
				//O X O
				//O X X
			for (int i = 0; i < 9; i++){ 
				if(i==6){ 
					partida.mark(7);
				}else if(i==7){
					partida.mark(6);
				}else{
					partida.mark(i);
				}
			}
			break;
		}
	}
}