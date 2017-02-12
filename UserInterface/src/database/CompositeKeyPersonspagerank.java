package database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by oldfox on 11.02.17.
 */

@Embeddable
public class CompositeKeyPersonspagerank implements Serializable {

    @Basic
    @Column(name="PersonID")
    private int personId;

    @Basic
    @Column(name="PageID")
    private int pageId;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}
