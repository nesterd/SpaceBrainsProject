package database;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by oldfox on 21.01.17.
 */
@Entity
@Table(name = "Persons", schema = "ratepersons", catalog = "")
public class PersonsEntity {
    private int id;
    private String name;
    private Collection<KeywordsEntity> keywordssById;
    private Collection<PersonPageRankEntity> personPageRanksById;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 1024)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonsEntity that = (PersonsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "personsByPersonId")
    public Collection<KeywordsEntity> getKeywordssById() {
        return keywordssById;
    }

    public void setKeywordssById(Collection<KeywordsEntity> keywordssById) {
        this.keywordssById = keywordssById;
    }

    @OneToMany(mappedBy = "personsByPersonId")
    public Collection<PersonPageRankEntity> getPersonPageRanksById() {
        return personPageRanksById;
    }

    public void setPersonPageRanksById(Collection<PersonPageRankEntity> personPageRanksById) {
        this.personPageRanksById = personPageRanksById;
    }
}
