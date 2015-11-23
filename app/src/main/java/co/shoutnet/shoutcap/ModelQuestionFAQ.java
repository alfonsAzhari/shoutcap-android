package co.shoutnet.shoutcap;

import java.util.ArrayList;

/**
 * Created by Adam MB on 10/22/2015.
 */
public class ModelQuestionFAQ {

    private String Question;
    private ArrayList<ModelAnswerFAQ> Items;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String name) {
        this.Question = name;
    }

    public ArrayList<ModelAnswerFAQ> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ModelAnswerFAQ> Items) {
        this.Items = Items;
    }
}
