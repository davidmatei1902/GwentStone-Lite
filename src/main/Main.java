package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import checker.CheckerConstants;
import fileio.DecksInput;
import fileio.Input;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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


        ////////////////////////////////////
        /// NU IMI INTRA AICI HELP        //
        ////////////////////////////////////
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

        //////////////////////////////////////////////////////////
        // objectNode asta de plimbat prin toate functiile////////
        // in asta punem chestiile citite/////////////////////////
        //////////////////////////////////////////////////////////
        ObjectNode objectNode = objectMapper.createObjectNode();

        // put method expects primitive tipes, like string, int ... not custom ones like getPlayerOneDecks
        // * objectNode.put("playerOneDeck", inputData.getPlayerOneDecks());


        ObjectNode playerOneNode = createDeckNode(objectMapper, inputData.getPlayerOneDecks());
        ObjectNode playerTwoNode = createDeckNode(objectMapper, inputData.getPlayerTwoDecks());

        objectNode.set("playerOneNode",playerOneNode);
        objectNode.set("playerTwoNode",playerTwoNode);

        output.add(objectNode);

        System.out.println(output.toPrettyString());

        // objectNode.put("playerOneDeck",playerOneDeck.getPlayerOneDeck());


        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         * ObjectMapper mapper = new ObjectMapper();
         *
         * ObjectNode objectNode = mapper.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = mapper.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }

    //////////////////////////////////////////////////////////////////////////
    //       Function that creates a deck node from a DecksInput class ///////
    //////////////////////////////////////////////////////////////////////////
    // momentan citim membrii simplii (int, float , etc)
    private static ObjectNode createDeckNode(ObjectMapper objectMapper, DecksInput deck)
    {
        ObjectNode deckNode = objectMapper.createObjectNode();
        deckNode.put("nrCardsInDeck",deck.getNrCardsInDeck());
        deckNode.put("nrDecks",deck.getNrDecks());
        return deckNode;
    }
}
