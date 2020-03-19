package generator;

import java.util.*;

public class Square implements Cloneable{
    private Set<Integer> candidates;
    private int entry;

    public Square(){
        this.entry = 0;
        this.candidates = new HashSet<>();
    }

    public Square(int value){
        this.entry = value;
        this.candidates = new HashSet<>();
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Square clone = (Square) super.clone();
        clone.candidates = new HashSet<>(this.candidates);
        return clone;
    }
}
