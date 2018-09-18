import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractState implements State{
    private final String NAME;
    private List<Transition> TRANSITIONS;

    /**
     * Construct a named State with no initial Transitions.
     * @param name The name of this State.
     */
    public AbstractState(String name) {
        this.NAME = name;
        this.TRANSITIONS = new ArrayList<>();
    }

    public void addTransition(Transition t) {
        TRANSITIONS.add(t);
    }

    public String getName() {
        return NAME;
    }

    @Override
    public Map<Transition, Boolean> getTransitions(char c) {
        Map<Transition, Boolean> ret = new HashMap<>();
        for (Transition t : TRANSITIONS) {
            // See if my letter is in this transition
            for (Letter l : t.getLetters()) {
                if (!l.isWildCard && l.mychar == c) {
                    ret.put(t.clone(), false);
                }
            }
            // Check for wild cards, and add the transition again.
            for (Letter l : t.getLetters()) {
                if (l.isWildCard) {
                    ret.put(t.clone(), true);
                }
            }
        }
        return ret;
    }

    @Override
    public List<Transition> getTransitions() {
        List<Transition> ret = new ArrayList<>();
        for (Transition t : TRANSITIONS) {
            ret.add(t.clone());
        }
        return ret;
    }

    @Override
    public boolean equals(Object other) {
        AbstractState otherState;
        if (!(other instanceof AbstractState)) {
            return false;
        } else {
            otherState = (AbstractState) other;
        }
        // Since there can never be duplicate names, this is enough even if it isn't strictly equal.
        if (otherState.getName().equals(getName())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return NAME.hashCode();
    }
}
