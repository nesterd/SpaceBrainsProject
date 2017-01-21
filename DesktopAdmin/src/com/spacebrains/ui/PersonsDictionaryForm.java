package com.spacebrains.ui;

import com.spacebrains.model.Person;
import com.spacebrains.widgets.BaseEditForm;
import com.spacebrains.widgets.BaseTable;
import com.spacebrains.widgets.BaseWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PersonsDictionaryForm extends BaseWindow {

    public PersonsDictionaryForm() {
        super();

        editDialog = new BaseEditForm<>(new Person(""));

        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "Путин"));
        persons.add(new Person(2, "Медведев"));
        persons.add(new Person(3, "Шойгу"));
        persons.add(new Person(4, "Захарова"));
        persons.add(new Person(5, "Жириновский"));
        persons.add(new Person(6, "Зюганов"));
        persons.add(new Person(7, "Чуркин"));
        persons.add(new Person(8, "Рогозин"));
        persons.add(new Person(9, "Яровая"));
        persons.add(new Person(10, "Миронов"));

        BaseTable table = new BaseTable(persons);
        table.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add new person");
                editDialog = new BaseEditForm<>(new Person(""));
                editDialog.setVisible(true);
            }
        });
        table.getEditBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit: " + table.getSelectedItem());
                editDialog = new BaseEditForm<>(table.getSelectedItem());
                editDialog.setVisible(true);
            }
        });
        table.getDeleteBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete: " + table.getSelectedItem());
            }
        });

        content.add(table);

        setVisible(true);
    }
}
