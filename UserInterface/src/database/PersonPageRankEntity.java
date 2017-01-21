package database;

import javax.persistence.*;

/**
 * Created by oldfox on 21.01.17.
 */
@Entity
@Table(name = "PersonPageRank", schema = "ratepersons", catalog = "")
public class PersonPageRankEntity {
    private int rank;
    private int id;
    private PersonsEntity personsByPersonId;
    private PagesEntity pagesByPageId;
    private int personId;
    private int pageId;

    @Basic
    @Column(name = "Rank", nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonPageRankEntity that = (PersonPageRankEntity) o;

        if (rank != that.rank) return false;
        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rank;
        result = 31 * result + id;
        return result;
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

//    @Basic
//    @Column(name = "PersonID", nullable = false)
//    public int getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(int personId) {
//        this.personId = personId;
//    }
//
//    @Basic
//    @Column(name = "PageID", nullable = false)
//    public int getPageId() {
//        return pageId;
//    }
//
//    public void setPageId(int pageId) {
//        this.pageId = pageId;
//    }
}
