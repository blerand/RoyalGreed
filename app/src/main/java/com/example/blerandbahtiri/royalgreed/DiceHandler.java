package com.example.blerandbahtiri.royalgreed;


import java.util.Collections;
import java.util.List;

/**
 * ROYAL DICE . BLERAND BAHTIRI
 * Created by blerandbahtiri on 16-02-06.
 */
public class DiceHandler {

    private final static int mNrOfAllDice = 6;

    //GAME SCORE DIFFERENT RULES
    private final int[] mThrees = { 1000, 200, 300, 400, 500, 600 };
    private final int[] mSingleScores = { 100, 0, 0, 0, 50, 0 };

    public DiceHandler(){}

    /**
     * Function caulculates totalpoints and current throw
     * points.
     * @param dices Data type that represents the dices
     * @param typeofPoints Takes in and int that represents the type of points.
     * @return Will return the total of a throw or the total of all the points
     * on the typeface input.
     */
    public int countPoints(List<Integer> dices, int typeofPoints){
        typeofPoints += TripleOrAllSingles(dices, 1)
                + TripleOrAllSingles(dices, 2)
                + TripleOrAllSingles(dices, 3)
                + TripleOrAllSingles(dices, 4)
                + TripleOrAllSingles(dices, 5)
                + TripleOrAllSingles(dices, 6);

        typeofPoints += ladderDices(dices);
        return typeofPoints;
    }

    /**
     * Function calculates doubles or tripples
     * @param dices Data type that represents the dices
     * @param typeOfFace Takes in and int that represents the type of singles.
     * @return Score value of this throw.
     */
    public int TripleOrAllSingles(List<Integer> dices, int typeOfFace){
        int score = 0;
        int occurrences = Collections.frequency(dices, typeOfFace);
        switch (occurrences){
            case 3:
                score = mThrees[typeOfFace - 1];
            break;
            case 4:
                score = mThrees[typeOfFace - 1];
                break;
            case 5:
                score = mThrees[typeOfFace - 1];
                break;
            case mNrOfAllDice:
                score = mSingleScores[typeOfFace - 1];
                break;
        }
        return score;
    }
    /**
     * Function will return the score for a ladder
     * or 0 if not a ladder.
     * @param dices Data type that represents the dices
     * @return A score if a ladder, else 0.
     */
    public int ladderDices(List<Integer> dices){
        if((dices.contains(1)) &&
                (dices.contains(2)) &&
                (dices.contains(3)) &&
                (dices.contains(4)) &&
                (dices.contains(5)) &&
                (dices.contains(6))){
            return 1000;
        }else return 0;
    }

}