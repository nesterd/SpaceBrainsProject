package database;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by oldfox on 21.01.17.
 */
@Entity
@Table(name = "Pages", schema = "ratepersons", catalog = "")
public class PagesEntity {
    private int id;
    private String url;
    private Timestamp foundDateTime;
    private Timestamp lastScanDate;
    private SitesEntity sitesById;
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
    @Column(name = "Url", nullable = false, length = 2048)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "FoundDateTime", nullable = true)
    public Timestamp getFoundDateTime() {
        return foundDateTime;
    }

    public void setFoundDateTime(Timestamp foundDateTime) {
        this.foundDateTime = foundDateTime;
    }

    @Basic
    @Column(name = "LastScanDate", nullable = true)
    public Timestamp getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Timestamp lastScanDate) {
        this.lastScanDate = lastScanDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagesEntity that = (PagesEntity) o;

        if (id != that.id) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (foundDateTime != null ? !foundDateTime.equals(that.foundDateTime) : that.foundDateTime != null)
            return false;
        if (lastScanDate != null ? !lastScanDate.equals(that.lastScanDate) : that.lastScanDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (foundDateTime != null ? foundDateTime.hashCode() : 0);
        result = 31 * result + (lastScanDate != null ? lastScanDate.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "ID", nullable = false)
    public SitesEntity getSitesById() {
        return sitesById;
    }

    public void setSitesById(SitesEntity sitesById) {
        this.sitesById = sitesById;
    }

    @OneToMany(mappedBy = "pagesByPageId")
    public Collection<PersonPageRankEntity> getPersonPageRanksById() {
        return personPageRanksById;
    }

    public void setPersonPageRanksById(Collection<PersonPageRankEntity> personPageRanksById) {
        this.personPageRanksById = personPageRanksById;
    }
}
