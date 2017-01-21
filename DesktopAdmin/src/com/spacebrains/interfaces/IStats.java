package com.spacebrains.interfaces;

import com.spacebrains.model.Person;
import com.spacebrains.model.Site;

import java.util.Date;
import java.util.HashMap;

public interface IStats {
    HashMap<Person, Integer> getFullStats(Site site);
    HashMap<Person, Integer> getDailyStats(Site site, Person person, Date periodStart, Date periodEnd);
}
