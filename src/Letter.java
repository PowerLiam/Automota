/**
 * Character wrapper to support a wildcard that isn't an actual Character, but represents any Character.
 */
public class Letter {
    // The char this is wrapping.
    public Character mychar;
    // Tells whether this Letter is a wildcard.
    public boolean isWildCard;

    /**
     * Creates a new Letter from a Character (which will never be a wild card).
     * @param c The Character to wrap.
     */
    public Letter(Character c) {
        this.mychar = c;
        this.isWildCard = false;
    }

    /**
     * Creates a new wildcard Letter.
     */
    public Letter() {
        this.isWildCard = true;
        this.mychar = null;
    }

    @Override
    public boolean equals(Object other) {
        Letter otherLetter;
        if (!(other instanceof Letter)) {
            return false;
        } else {
            otherLetter = (Letter)other;
        }
        if (other != null && otherLetter.mychar == mychar && otherLetter.isWildCard == isWildCard) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isWildCard) {
            return -1;
        }
        return mychar;
    }
}
