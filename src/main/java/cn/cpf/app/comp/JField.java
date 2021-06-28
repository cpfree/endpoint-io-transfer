package cn.cpf.app.comp;

import com.github.cosycode.common.base.IValGetter;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * <b>Description : </b>
 * <p>
 * <b>created in </b> 2021/6/28
 *
 * @author CPF
 * @since 1.1
 **/
public class JField extends JPanel implements IValGetter<String> {

    @Getter
    private JLabel jLabel;

    @Getter JTextField jTextField;

    public JField(String label) {
        initComponents(label);
    }

    public JField(String label, boolean readOnly) {
        initComponents(label);
        if (readOnly) {
            setEditable(false);
            getJTextField().setBorder(null);
        }
    }

    private void initComponents(String label) {
        this.setLayout(new GridLayout(1,3,5,3));
        jLabel = new JLabel(label);
        jTextField = new JTextField();
        jLabel.setLabelFor(jTextField);
        jLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        add(jLabel);
        add(jTextField);
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
