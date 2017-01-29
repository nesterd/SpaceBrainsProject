package com.spacebrains.ui;

import com.spacebrains.core.rest.KeywordsRestMock;
import com.spacebrains.interfaces.IKeywords;
import com.spacebrains.interfaces.IPersons;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;
import com.spacebrains.core.rest.PersonsRestMock;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static com.spacebrains.core.util.BaseParams.APP_NAME;
import static com.spacebrains.core.util.BaseParams.KEYWORDS_DICT;
import static com.spacebrains.core.util.BaseParams.TABLE_WIDTH;

/**
 * @author Tatyana Vorobeva
 */
public class KeywordsDictionaryForm extends BaseWindow {

    private static Person currentPerson;

    IPersons personRest = PersonsRestMock.getInstance();
    IKeywords rest = KeywordsRestMock.getInstance();
    //IKeywords rest = KeywordsRest.getInstance();

    private JComboBox<Person> personChooser;
    BaseTable table = null;

    public KeywordsDictionaryForm() {
        super();
        windowTitle = APP_NAME + ": " + KEYWORDS_DICT;
        JFrame currentFrame = this;

        JLabel label = new JLabel("Справочник \"" + KEYWORDS_DICT + "\"");
        label.setFont(BaseParams.BASE_LABEL_FONT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        initPersonChooser();

        editDialog = new BaseEditForm<>(rest, new Keyword(""));

        currentPerson = (Person) personChooser.getSelectedItem();
        table = new BaseTable(rest.getKeywords(currentPerson));
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new BaseEditForm<>(rest, new Keyword("", (Person) personChooser.getSelectedItem()));
                editDialog.setVisible(true);
                table.updateValues(rest.getKeywords((Person) personChooser.getSelectedItem()));
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Keyword keyword = (Keyword) table.getSelectedItem();
                if (keyword == null) keyword = new Keyword("");
                keyword.setPerson((Person) personChooser.getSelectedItem());
                editDialog = new BaseEditForm<>(rest, keyword);
                editDialog.setVisible(true);
                table.updateValues(rest.getKeywords((Person) personChooser.getSelectedItem()));
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        rest.delete((Keyword) table.getSelectedItem());
                        table.updateValues(rest.getKeywords((Person) personChooser.getSelectedItem()));
                    }
                }
            }
        });

        personChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPerson = (Person) personChooser.getSelectedItem();
                table.updateValues(rest.getKeywords(currentPerson));
            }
        });

        JLabel personLabel = new JLabel("Персона: ");
        personLabel.setFont(BaseParams.BASE_TABLE_FONT);
        personLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        Box personChooserBox = Box.createHorizontalBox();
        personChooserBox.add(personLabel);
        personChooserBox.add(personChooser);
        personChooserBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(new JLabel(" "));
        content.add(label);
        content.add(new JLabel(" "));
        content.add(personChooserBox);
        content.add(table);

        setVisible(true);
    }

    private void initPersonChooser() {
        ArrayList<Person> personList = personRest.getPersons();

        if (personChooser == null) {
            personChooser = new JComboBox<>();
            currentPerson = null;
        } else {
            personChooser.removeAllItems();
        }

        for(Person person : personRest.getPersons()){
            personChooser.addItem(person);
        }

        if (personList.size() > 0) {
            if (currentPerson != null) personChooser.setSelectedItem(currentPerson);
            else personChooser.setSelectedIndex(0);
        }
        personChooser.setMaximumSize(new Dimension(TABLE_WIDTH - 67, 30));

        personChooser.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Person){
                    setText(((Person) value).getName());
                }
                return this;
            }
        } );
        personChooser.updateUI();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);
        if (table != null) table.updateValues(rest.getKeywords((Person) personChooser.getSelectedItem()));
    }
}
