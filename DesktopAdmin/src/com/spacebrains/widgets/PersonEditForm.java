package com.spacebrains.widgets;

import com.spacebrains.core.AppController;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.model.Person;
import com.spacebrains.widgets.base.BaseEditForm;

public class PersonEditForm extends BaseEditForm<Person> {

    public PersonEditForm(Person person) {
        this(person, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public PersonEditForm(Person person, int width, int height) {
        super(person, width, height);
    }

    @Override
    protected String save(Person person) {
        try {
            if (AppController.getInstance().setPerson(person) == true) {
                AppController.setLastChosenPerson(person);
                return RepoConstants.SUCCESS;
            } else return RepoConstants.NOT_ANSWERED;
        } catch (RuntimeException e) {
            return RepoConstants.NOT_ANSWERED;
        }
    }
}
