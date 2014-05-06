package lv.javaguru.java2.ishop.domain;

import javax.persistence.*;

/**
 * Created by Andrej on 14.22.3.
 */
@Entity
@Table(name="WISHEDCOMMODITY")
public class WishedCommodity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IDWishedCommodity", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="IDCommodity", nullable = false)
    private Commodity commodity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="IDCustomer", nullable = false)
    private Customer customer;

    public WishedCommodity() {}

    public WishedCommodity(Commodity commodity, Customer customer) {
        this.commodity = commodity;
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WishedCommodity that = (WishedCommodity) o;

        if (commodity != null ? !commodity.equals(that.commodity) : that.commodity != null) return false;
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (commodity != null ? commodity.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
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
}
