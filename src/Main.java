import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a filename to load your finite automata.\n");
        System.out.print("> ");
        File input = new File(in.nextLine());
        FiniteAutomata machine = readInput(input);
        System.out.print("Enter an input string.\n");
        System.out.print("> ");
        String str = in.nextLine();

        // Provide the FiniteAutomata with the input.
        machine.setString(str);
        // Computer (determine if the input is accepted)
        List<State> accepted = machine.compute();
        // Interpret the results (any returned states mean that the input was accepted).
        if(accepted.size() != 0) {
            System.out.println("Accepted the input string!");
            System.out.println("Accept states were:");
            for (State s : accepted) {
                System.out.println(s.getName());
            }
        } else {
            System.out.println("Rejected Input String");
        }
    }

    /**
     * Reads in a .fa file format and creates a FiniteAutomata from it.
     *
     *
     * Syntax of a .fa file:
     * -----------------------------------------------------------------------------------------------------------
     * To specify a State:
     *   s [name] [type]
     *      [name] The name of this State.
     *      [type]: One of {start, normal, accept}
     *         start:  A unique State for your Automaton to start from.
     *         normal: A generic State.
     *         accept: A "final" State, which signifies that the input String was accepted if the Automaton ends on a
     *                  State of this type.
     *
     * To specify a Transition:
     *   t [source] [destination] {letters, ...};
     *      [source]:        The name of the source state for the transition.
     *      [destination]:   The name of the destination state for the transition.
     *      {letters, ...}:  A list of letters that if read in the input while on this Transitions's source state
     *                        will cause this transition to occur.  Specify an empty letter (or wildcard, matches
     *                        emptiness) by using the string |e| as a letter.
     *
     * All lines must end in a Semicolon (;)
     *
     * @param input The file to read from.
     * @return A FiniteAutomata constructed from the data in the file.
     */
    private static FiniteAutomata readInput(File input) {
        FiniteAutomata ret;
        Scanner s = null;
        try {
            s = new Scanner(input);
        } catch (FileNotFoundException e) {
            System.err.println("Failed to load file.");
            e.printStackTrace();
        }

        List<State> states = new ArrayList<>();

        int statebuild = 0;
        int transitionbuild = 0;

        State tempS = null;
        String tempSName = null;

        Transition tempT = null;
        State tempSourceState = null;
        State tempDestState = null;
        List<Letter> tempLetters = new ArrayList<>();

        while (s.hasNext()) {
            String cur = s.next();
            // Continue parsing that has already begun.
            if (statebuild != 0) {
                switch (statebuild) {
                    case 1:
                        tempSName = cur;
                        statebuild++;
                        break;
                    case 2:
                        if (cur.equals("start;")) {
                            tempS = new StartState(tempSName);
                        } else if (cur.equals("normal;")) {
                            tempS = new NormalState(tempSName);
                        } else if (cur.equals("accept;")) {
                            tempS = new AcceptState(tempSName);
                        } else {
                            System.err.println("Error while parsing State info.  Found: " + cur );
                            System.exit(0);
                        }
                        states.add(tempS);
                        statebuild = 0;
                        break;
                }
            } else if (transitionbuild != 0) {
                switch ((transitionbuild)) {
                    case 1:
                        for (State state : states) {
                            if (state.getName().equals(cur)) {
                                tempSourceState = state;
                            }
                        }
                        if (tempSourceState == null) {
                            System.err.println("Error while parsing Transition info, source State does not exist.");
                            System.exit(0);
                        }
                        transitionbuild++;
                        break;
                    case 2:
                        for (State state : states) {
                            if (state.getName().equals(cur)) {
                                tempDestState = state;
                            }
                        }
                        if (tempDestState == null) {
                            System.err.println("Error while parsing Transition info, destination State does not exist.");
                            System.exit(0);
                        }
                        transitionbuild++;
                        break;
                    default:
                        if (cur.contains(";")) {
                            if (tempDestState == null || tempSourceState == null) {
                                System.err.println("Error while parsing Transition info, Transition ended (from ;) before all data received");
                                System.exit(0);
                            }

                            Letter last = null;
                            if (cur.length() == 4 && cur.substring(0, 3).equals("|e|")) {
                                last = new Letter();
                            } else if (cur.length() == 2) {
                                last = new Letter(cur.charAt(0));
                            } else {
                                System.err.println("Error while parsing Transition info, last letter is more than 1 character (and not a wildcard).");
                                System.exit(0);
                            }
                            tempLetters.add(last);

                            Transition t = new TransitionImpl(tempDestState, tempSourceState, tempLetters);
                            tempSourceState.addTransition(t);

                            tempDestState = null;
                            tempSourceState = null;
                            tempLetters = new ArrayList<>();
                            transitionbuild = 0;
                        } else {
                            Letter temp;
                            if (cur.equals("|e|")) {
                                temp = new Letter();
                            } else {
                                if (cur.length() != 1) {
                                    System.err.println("Error while parsing Transition info, letter is more than 1 character (and not a wildcard).");
                                    System.exit(0);
                                }
                                temp = new Letter(cur.charAt(0));
                            }
                            tempLetters.add(temp);
                        }
                }
            } else {
                // Initialize one type of parsing
                if (cur.equals("s")) {
                    statebuild++;
                } else if (cur.equals("t")) {
                    transitionbuild++;
                } else {
                    System.err.println("Unknown command found: " + cur);
                    System.exit(0);
                }
            }
        }
        ret = new FiniteAutomataImpl(states, 0);

        return ret;
    }
}
