import java.util.List;

public class TransitionImpl implements Transition {
    private final State SOURCE;
    private final State DEST;
    private List<Letter> CHARS;

    public TransitionImpl(State dest, State source, List<Letter> CHARS) {
        this.SOURCE = source;
        this.DEST = dest;
        this.CHARS = CHARS;
    }

    @Override
    public List<Letter> getLetters() {
        return CHARS;
    }

    @Override
    public State getDest() {
        return DEST;
    }

    @Override
    public State getSource() {
        return SOURCE;
    }

    @Override
    public Transition clone() {
        return new TransitionImpl(DEST, SOURCE, CHARS);
    }


    @Override
   public boolean equals(Object other) {
        Transition otherTransition;
        if (!(other instanceof TransitionImpl)) {
            return false;
        } else {
            otherTransition = (Transition) other;
        }
        if (getDest() == otherTransition.getDest() && getSource() == otherTransition.getSource()){
            boolean sameLetters = true;
            for (Letter l : otherTransition.getLetters()) {
                sameLetters &= getLetters().contains(l);
            }
            if (sameLetters) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getDest().getName().length() + getSource().getName().length() + getLetters().size();
    }
}
