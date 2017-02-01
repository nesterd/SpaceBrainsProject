package com.spacebrains.widgets;

import com.spacebrains.core.AppController;
import com.spacebrains.core.RepoConstants;
import com.spacebrains.model.Person;

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
            return AppController.getInstance().setPerson(person)
                    ? RepoConstants.SUCCESS
                    : RepoConstants.NOT_ANSWERED;
        } catch (RuntimeException e) {
            return RepoConstants.NOT_ANSWERED;
        }
    }
}
