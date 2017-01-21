package DataBase;

import javax.persistence.*;

/**
 * Created by oldfox on 21.01.17.
 */
@Entity
@Table(name = "PersonPageRank", schema = "ratepersons", catalog = "")
public class PersonPageRankEntity {
    private int rank;
    private PersonsEntity personsByPersonId;
    private PagesEntity pagesByPageId;

    @Basic
    @Column(name = "Rank", nullable = false)
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

        PersonPageRankEntity that = (PersonPageRankEntity) o;

        if (rank != that.rank) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rank;
    }

    @ManyToOne
    @JoinColumn(name = "PersonID", referencedColumnName = "ID", nullable = false)
    public PersonsEntity getPersonsByPersonId() {
        return personsByPersonId;
    }

    public void setPersonsByPersonId(PersonsEntity personsByPersonId) {
        this.personsByPersonId = personsByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "PageID", referencedColumnName = "ID", nullable = false)
    public PagesEntity getPagesByPageId() {
        return pagesByPageId;
    }

    public void setPagesByPageId(PagesEntity pagesByPageId) {
        this.pagesByPageId = pagesByPageId;
    }
}
