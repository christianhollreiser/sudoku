import java.util.*;

public class Square {
    private Set<Integer> candidates;
    private int entry;

    public Square(){
        this.entry = 0;
        this.candidates = new HashSet<Integer>();
    }

    public Square(int value){
        this.entry = value;
        this.candidates = new HashSet<Integer>();
    }

    public void addCandidate(int candidate){
        this.candidates.add(candidate);
    }

    public void removeCandidate(int candidate){
        this.candidates.remove(candidate);
    }

    public void removeAllCandidates(){
        this.candidates.clear();
    }

    public void changeEntry(int entry){
        this.entry = entry;
    }

    public int getEntry(){
        return this.entry;
    }

    public Set<Integer> getCandidates(){
        return this.candidates;
    }
}
