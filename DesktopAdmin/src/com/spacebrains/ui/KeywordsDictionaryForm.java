package com.spacebrains.ui;

import com.spacebrains.core.AppController;
import com.spacebrains.core.util.BaseParams;
import com.spacebrains.model.Keyword;
import com.spacebrains.model.Person;
import com.spacebrains.widgets.base.BaseTable;
import com.spacebrains.widgets.base.BaseWindow;
import com.spacebrains.widgets.KeywordEditForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static com.spacebrains.core.util.BaseParams.*;

/**
 * @author Tatyana Vorobeva
 */
public class KeywordsDictionaryForm extends BaseWindow {

    private static Person currentPerson;

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

        editDialog = new KeywordEditForm(new Keyword(""));

        currentPerson = (Person) personChooser.getSelectedItem();
        table = new BaseTable(AppController.getInstance().getKeywordsByPerson(currentPerson));
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editDialog = new KeywordEditForm(new Keyword("", (Person) personChooser.getSelectedItem()));
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getKeywordsByPerson((Person) personChooser.getSelectedItem()));
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Keyword keyword = (Keyword) table.getSelectedItem();
                if (keyword == null) keyword = new Keyword("");
                keyword.setPerson((Person) personChooser.getSelectedItem());
                editDialog = new KeywordEditForm(keyword);
                editDialog.setVisible(true);
                table.updateValues(AppController.getInstance().getKeywordsByPerson((Person) personChooser.getSelectedItem()));
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedItem() != null) {
                    int userChoice = getDeleteConfirmation(currentFrame, table.getSelectedItem().getName());

                    if (userChoice == JOptionPane.YES_OPTION) {
                        System.out.println("Delete: " + table.getSelectedItem());
                        AppController.getInstance().deleteKeyword((Keyword) table.getSelectedItem());
                        table.updateValues(AppController.getInstance().getKeywordsByPerson((Person) personChooser.getSelectedItem()));
                    }
                }
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
        ArrayList<Person> personList = AppController.getInstance().getPersons();

        if (personChooser == null) {
            personChooser = new JComboBox<>();
            currentPerson = null;
        } else {
            personChooser.removeAllItems();
        }

        for (Person person : personList){
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

        personChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPerson = (Person) personChooser.getSelectedItem();
                AppController.setLastChosenPerson(currentPerson);
                if (table != null) table.updateValues(AppController.getInstance().getKeywordsByPerson(currentPerson));
            }
        });

        personChooser.updateUI();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);

        if (AppController.getInstance().getLastChosenPerson() != null && wasAlreadyOpenedBefore) {
            for (int i = 0; i <= personChooser.getItemCount(); i++) {
                if (personChooser.getItemAt(i).getName().equals(AppController.getInstance().getLastChosenPerson().getName())) {
                    personChooser.setSelectedItem(personChooser.getItemAt(i));
                    return;
                }
            }
        }

        currentPerson = (Person) personChooser.getSelectedItem();
        if (table != null && wasAlreadyOpenedBefore) table.updateValues(AppController.getInstance().getKeywordsByPerson(currentPerson));
        wasAlreadyOpenedBefore = true;
    }
}
