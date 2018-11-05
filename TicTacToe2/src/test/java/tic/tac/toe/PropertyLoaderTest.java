package tic.tac.toe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import tic.tac.toe.interfaces.Player;
import tic.tac.toe.model.HumanPlayer;
import tic.tac.toe.model.Position;

@RunWith(PowerMockRunner.class)
@PrepareForTest
public class PropertyLoaderTest {

	private TicTacToeGame game = null;

	@Before
	public void prepareGame() {

	}

	
	@Test
	public void testLoadFromSamplePropertyFile() {
		URL url = this.getClass().getResource("");
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("testconfiguration.properties").getFile());
		
		PropertiesLoader loader = new PropertiesLoader(file.getAbsolutePath());
		
		assertEquals("Two players expected" , 3,loader.getPlayers().length);
		assertEquals("X player expected" , 'X',loader.getPlayers()[0].getPlayerCharacter());
		assertEquals("Y player expected" , 'Y',loader.getPlayers()[1].getPlayerCharacter());
		assertEquals("P player expected" , 'P',loader.getPlayers()[2].getPlayerCharacter());
		assertEquals("4 player expected" , 4,loader.getColumns());
		assertEquals("4 player expected" , 5,loader.getRows());
		
	}
}
