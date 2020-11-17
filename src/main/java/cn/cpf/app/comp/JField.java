package cn.cpf.app.comp;

import com.github.cpfniliu.common.base.IValGetter;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/12 15:44
 */
public class JField extends JPanel implements IValGetter<String> {

    @Getter
    private JLabel jLabel;

    @Getter JTextField jTextField;

    public JField(String label) {
        GridLayout mgr = new GridLayout(1, 3, 10, 5);
        setLayout(mgr);
        jLabel = new JLabel(label);
        jTextField = new JTextField();
        jLabel.setLabelFor(jTextField);
        add(jLabel);
        mgr.setColumns(2);
        add(jTextField);
    }

    public JField(String label, boolean readOnly) {
        this(label);
        if (readOnly) {
            setEditable(false);
            getJTextField().setBorder(null);
        }
    }

    public void setText(String text) {
        jTextField.setText(text);
    }

    public void setEditable(boolean readOnly) {
        jTextField.setEditable(readOnly);
    }

    @Override
    public String getVal() {
        return jTextField.getText();
    }
}
