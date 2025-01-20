package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import gameplay.GamePlay;
import resources.MainGame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import fileio.Input;

import resources.Game;
import resources.Decks;


/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();



        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         */


        MainGame mainGame = new MainGame(inputData.getPlayerOneDecks(),
                inputData.getPlayerTwoDecks(), inputData.getGames());
        ArrayList<Game> games = mainGame.getGames();
        Decks p1Decks = mainGame.getPlayerOneDecks();
        Decks p2Decks = mainGame.getPlayerTwoDecks();
        GamePlay gamePlay = new GamePlay(games, p1Decks, p2Decks, output);
        gamePlay.initialSetup();


//        ArrayList<GameInput> games = inputData.getGames();
//        DecksInput p1Decks = inputData.getPlayerOneDecks();
//        DecksInput p2Decks = inputData.getPlayerTwoDecks();
//        GamePlay gamePlay = new GamePlay(games, p1Decks, p2Decks, output);
//        gamePlay.initial_setup();

//        ObjectMapper mapper2 = new ObjectMapper();
//        ObjectNode objectNode = mapper.createObjectNode();
//        objectNode.put("commandone", "COMMAND2");
//        objectNode.put("command", "COMMAND23");
//        ObjectNode objectNode2 = mapper2.createObjectNode();
//        objectNode2.put("commandsuca", "COMMAND23");
//        ArrayNode arrayNode = mapper.createArrayNode();
//        arrayNode.add(objectNode);
//
//        output.add(objectNode2);
//        output.add(objectNode);


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}