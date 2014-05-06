package lv.javaguru.java2.ishop.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lilya on 19.03.14.
 */

@Entity
@Table(name="Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDComment", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="IDCommodity")
    private Commodity commodity;

    @ManyToOne
    @JoinColumn(name="IDCustomer")
    private Customer customer;

    @Column(name ="Comment", nullable = false)
    private String comment;

    @Column(name ="Date", nullable = false)
    @Type(type = "timestamp")
    private Date date;

    @Column(name ="Name", nullable = false)
    private String name;

    @Column(name ="IsLoggedIn", nullable = false)
    private boolean isLoggedIn;

    public Comment() {

    }

    public Comment(Commodity commodity, Customer customer, String comment, Date date, boolean isLoggedIn) {
        this.commodity = commodity;
        this.customer = customer;
        this.comment = comment;
        this.date = date;
        this.name = customer.getName()+" "+customer.getSurname();
        this.isLoggedIn = isLoggedIn;
    }

    public Comment(Commodity commodity, String comment, Date date, boolean isLoggedIn) {
        this.commodity = commodity;
        this.customer = null;
        this.comment = comment;
        this.date = date;
        this.name = "Guest";
        this.isLoggedIn = isLoggedIn;
    }

    public Long getIdCommodity() {
        return commodity.getId();
    }

    public Long getIdCustomer() {
        return customer.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment c = (Comment) o;

        if (name != null ? !name.equals(c.name) : c.name != null) return false;
        if (commodity != null ? !commodity.equals(c.commodity) : c.commodity != null) return false;
        if (customer != null ? !customer.equals(c.customer) : c.customer != null)
            return false;
        if (id != null ? !id.equals(c.id) : c.id != null) return false;
        if (comment != null ? !comment.equals(c.comment) : c.comment != null) return false;
        if (date != null ? !date.equals(c.date) : c.date != null) return false;
        if (name != null ? !name.equals(c.name) : c.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (commodity != null ? commodity.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);

        return result;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", idCommodity=" + commodity.getId() +
                ", idCustomer=" + customer.getId() +
                ", name=" + name +
                ", comment=" + comment +
                ", date='" + date +
                '}';
    }


}
