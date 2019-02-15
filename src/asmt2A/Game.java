package asmt2A;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Game extends Application {

	/**
	 * Global width and height parameters Global height parameter
	 */
	public static final int GLOBAL_HORI = 30;
	public static final int GLOBAL_VERT = 30;

	/**
	 * creates a world
	 */
	private World world;

	/**
	 * declares a 2D array of buttons for the world
	 */
	private Button[][] buttons;

	/**
	 * declares a gridpane root, and a stage
	 */
	static GridPane root;
	Stage stage;

	/**
	 * instantiates a world with global parameters and a complementary 2d array of
	 * buttons
	 */
	public Game() {
		world = new World(new Point(GLOBAL_HORI, GLOBAL_VERT));
		buttons = new Button[world.worldBounds.x][world.worldBounds.y];
	}

	/**
	 * launches the GUI
	 */
	public void play() {
		launch();
	}

	/**
	 * calls a turn in the World class
	 */
	public void turn() {
		world.worldTurn();
		colorizeButtons();
	}

	/**
	 * the GUI stuff --> creates an array of buttons coloured according to what is
	 * in the cells of the cell array, now drawing the colours in lifeforms, and a
	 * default colour for cells with null lifeforms
	 */
	public static void colorize(Button button, World.Cell cell) {
		if (cell.life == null)
			button.setStyle("-fx-background-color: SIENNA");
		else {
			String colourString = "" + cell.life.getColour();
			button.setStyle("-fx-background-color: " + colourString);
		}
	}

	/**
	 * a function to re-colorize buttons after a turn is processed
	 */
	public void colorizeButtons() {
		for (int x = 0; x < world.worldBounds.x; x++)
			for (int y = 0; y < world.worldBounds.y; y++)
				colorize(buttons[x][y], world.cell[x][y]);
	}

	/**
	 * the event handler for a button press to advance a turn
	 */
	public void processButtonPress(ActionEvent lifeClick) {
		turn();
	}
	
	/**
	 * processes a 'L' click which loads a game file 
	 * @param loadClick is the name of the button press event
	 */
	public void processLPress(ActionEvent loadClick) {
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Load Game File");
	    fileChooser.getExtensionFilters().add(new ExtensionFilter("BoardGame Files", "*.ser"));
	    File file = fileChooser.showOpenDialog(stage);
	    if(file != null) {
	        loadGame(file);
	    }
		colorizeButtons();
	}

	/**
	 * processes a 'S' click which loads a game file 
	 * @param saveClick is the name of the button press event
	 */
	public void processSPress(ActionEvent saveClick) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Game File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("BoardGame Files", "*.ser"));
		File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			saveGame(file);
		}
	}

	/**
	 * function that takes in a file to save the game to
	 * @param file is the file being passed in
	 */
	public void saveGame(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutput out = new ObjectOutputStream(fileOut);
			out.writeObject(world);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function that takes in a file to load into a game
	 * @param file is the file being opened
	 */
	public void loadGame(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file.getPath());
			ObjectInputStream in = new ObjectInputStream(fileIn);
			world = (World) in.readObject();
			in.close();
			colorizeButtons();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * starts the gridpane, creates buttons and populates the 2D array
	 */
	public void start(Stage stage) {
		stage.setTitle("Game of Life");
		Button load = new Button("L");
		load.setOnAction(this::processLPress);
		Button save = new Button("S");
		save.setOnAction(this::processSPress);
		root = new GridPane();
		root.add(load, GLOBAL_HORI / 2 + 1, 0);
		root.add(save, GLOBAL_HORI / 2 - 2, 0);
		int x, y;
		for (x = 0; x < world.worldBounds.x; x++) {
			for (y = 0; y < world.worldBounds.y; y++) {
				buttons[x][y] = new Button("  ");
				root.add(buttons[x][y], x, y + 1);
				colorize(buttons[x][y], world.cell[x][y]);
				buttons[x][y].setOnAction(this::processButtonPress);
			}
		}
		root.setMinSize(1, 1);
		Scene scene = new Scene(root);
		stage.setScene(scene);

		stage.show();
	}
}