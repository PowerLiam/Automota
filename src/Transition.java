import java.util.List;

public interface Transition {

    /**
     * Gets the letters that will cause this Transition to occur.
     * @return A List of the letters that will cause this Transition to occur.
     */
    List<Letter> getLetters();

    /**
     * Gets the destination State of this Transition.
     * @return The destination state of this Transition.
     */
    State getDest();

    /**
     * Gets the source State of this Transition.
     * @return
     */
    State getSource();

    /**
     * Creates a new Transition with identical attributes to this one.
     * @return A new identical Transition.
     */
    Transition clone();

    @Override
    boolean equals(Object other);

    @Override
    int hashCode();
}
