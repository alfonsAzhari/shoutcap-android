package co.shoutnet.shoutcap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adam MB on 10/21/2015.
 */
public class FAQAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ModelQuestionFAQ> groups;

    public FAQAdapter(Context context, ArrayList<ModelQuestionFAQ> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ModelAnswerFAQ> chList = groups.get(groupPosition)
                .getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ModelAnswerFAQ child = (ModelAnswerFAQ) getChild(groupPosition,
                childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_answer_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.txt_answer);
        tv.setText(child.getAnswer().toString());

        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ModelAnswerFAQ> chList = groups.get(groupPosition)
                .getItems();

        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ModelQuestionFAQ group = (ModelQuestionFAQ) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.faq_question_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.txt_question);
        tv.setText(group.getQuestion());
        ImageView image;
        image = (ImageView) convertView.findViewById(R.id.expand_icon);
        if (groupPosition >= 0) {
            if (isExpanded) {
                image.setImageResource(R.mipmap.ic_arrow_up);
            } else {
                image.setImageResource(R.mipmap.ic_arrow_down);
            }
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
