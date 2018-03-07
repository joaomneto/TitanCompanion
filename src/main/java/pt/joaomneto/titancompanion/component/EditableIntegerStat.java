package pt.joaomneto.titancompanion.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import pt.joaomneto.titancompanion.R;

/**
 * Created by 962633 on 08-09-2017.
 */

public class EditableIntegerStat extends RelativeLayout {


    public EditableIntegerStat(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.component_integer_editable_stat, this, true);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.labelValue, 0, 0);

        TextView label = findViewById(R.id.textField);
        String labelText = array.getString(R.styleable.labelValue_label);
        if (labelText != null) {
            label.setText(labelText);
        }

        array.recycle();
    }
}
