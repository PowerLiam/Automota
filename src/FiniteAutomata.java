import java.util.List;

/**
 * Represents a finite automata.
 */
public interface FiniteAutomata extends Runnable{

    /**
     * Sets the input String to be ingested by this FiniteAutomata when compute() is called.
     * @param s The input to the Automata.
     */
    void setString(String s);

    /**
     * Simulates the ingestion of all input provided by setString.
     * @return A list of all the AcceptStates that were validly landed on (by non-dead Automata branches).  If there
     * are any states returned then this Automata accepted the input.
     */
    List<State> compute();
}
