package database;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by oldfox on 11.02.17.
 */
@Entity
@Table(name = "personpagerank", schema = "ratepersons", catalog = "")
public class PersonpagerankEntity implements Serializable {


    @EmbeddedId
    private CompositeKeyPersonspagerank personPageRank;

    private int personId;
    private int pageId;
    private int rank;

    @Basic
    @Column(name = "PersonID")
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "PageID")
    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Basic
    @Column(name = "Rank")
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonpagerankEntity that = (PersonpagerankEntity) o;

        if (personId != that.personId) return false;
        if (pageId != that.pageId) return false;
        if (rank != that.rank) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + pageId;
        result = 31 * result + rank;
        return result;
    }
}
