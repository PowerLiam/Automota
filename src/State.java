
import java.util.List;
import java.util.Map;

public interface State {

    /**
     * Returns the name of this State.
     * @return The name of this State.
     */
    String getName();

    /**
     * Returns the Transitions associated with this state that are followed based the the provided char.
     * @param c The char to determine if a Transition is followed.
     * @return A map representing the Transitions to follow and whether they were followed because of an empty
     * (wildcard)
     */
    Map<Transition, Boolean> getTransitions(char c);

    /**
     * Gets all the Transitions.
     * @return The Transitions.
     */
    List<Transition> getTransitions();

    /**
     * Adds a Transition to this State.
     * @param t The Transition to add.
     */
    void addTransition(Transition t);

    @Override
    boolean equals(Object other);

    @Override
    int hashCode();
}
