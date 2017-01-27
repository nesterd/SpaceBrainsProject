package database;

import javax.persistence.*;

/**
 * Created by oldfox on 21.01.17.
 */
@Entity
@Table(name = "Sites", schema = "ratepersons", catalog = "")
public class SitesEntity {
    private int id;
    private String name;
    private PagesEntity pagesById;

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

        SitesEntity that = (SitesEntity) o;

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

    @OneToOne(mappedBy = "sitesById")
    public PagesEntity getPagesById() {
        return pagesById;
    }

    public void setPagesById(PagesEntity pagesById) {
        this.pagesById = pagesById;
    }
}
