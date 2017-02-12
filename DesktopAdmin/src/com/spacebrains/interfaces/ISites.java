package com.spacebrains.interfaces;

import com.spacebrains.model.Site;

import java.util.ArrayList;

public interface ISites extends IRest<Site>{
    ArrayList<Site> getSites();
}
