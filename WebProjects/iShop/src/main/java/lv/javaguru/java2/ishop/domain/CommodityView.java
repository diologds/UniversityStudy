package lv.javaguru.java2.ishop.domain;

import javax.persistence.*;
import java.util.Arrays;

//Don't work , fixing

@Entity
@Table(name = "COMMODITYVIEW")
public class CommodityView {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "IDCommodityView", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="IDCommodity", nullable = false)
    private Commodity commodity;

    //@Column(name = "IDCommodity", nullable = false)
    transient  private Long idCommodity;

    @Column(name = "CommodityPhoto", nullable = false)
    private byte[] commodityPhoto;

    @Enumerated(EnumType.STRING)
    @Column(name = "CommodityPhotoType", nullable = false)
    private ImageType commodityPhotoType;

    @Column(name = "GalleryPhoto", nullable = false)
    private Boolean galleryPhoto;

    public CommodityView(){};

    public CommodityView(Long id , Long idCommodity , byte[] commodityPhoto , ImageType commodityPhotoType , Boolean galleryPhoto){

        this.id = id;
        this.idCommodity = idCommodity;
        this.commodityPhoto = commodityPhoto;
        this.commodityPhotoType = commodityPhotoType;
        this.galleryPhoto = galleryPhoto;
    }

    public CommodityView( byte[] commodityPhoto , ImageType commodityPhotoType , Boolean galleryPhoto){

        this.commodityPhoto = commodityPhoto;
        this.commodityPhotoType = commodityPhotoType;
        this.galleryPhoto = galleryPhoto;
    }

    public CommodityView(Long idCommodity, byte[] commodityPhoto , ImageType commodityPhotoType , Boolean galleryPhoto){

        this.idCommodity = idCommodity;
        this.commodityPhoto = commodityPhoto;
        this.commodityPhotoType = commodityPhotoType;
        this.galleryPhoto = galleryPhoto;
    }

    public CommodityView(Commodity commodity, byte[] commodityPhoto , ImageType commodityPhotoType , Boolean galleryPhoto){

        this.commodity = commodity;
        this.commodityPhoto = commodityPhoto;
        this.commodityPhotoType = commodityPhotoType;
        this.galleryPhoto = galleryPhoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCommodity() {
        return idCommodity;
    }

    public void setIdCommodity(Long idCommodity) {
        this.idCommodity = idCommodity;
    }

    public byte[] getCommodityPhoto() {
        return commodityPhoto;
    }

    public void setCommodityPhoto(byte[] commodityPhoto) {
        this.commodityPhoto = commodityPhoto;
    }

    public ImageType getCommodityPhotoType() {
        return commodityPhotoType;
    }

    public void setCommodityPhotoType(ImageType commodityPhotoType) {
        this.commodityPhotoType = commodityPhotoType;
    }

    public boolean isGalleryPhoto() {
        return galleryPhoto;
    }

    public void setGalleryPhoto(boolean galleryPhoto) {
        this.galleryPhoto = galleryPhoto;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommodityView commodityView = (CommodityView) o;

        if (id != null ? !id.equals(commodityView.id) : commodityView.id != null) {
            return false;
        }
        if (idCommodity != null ? !idCommodity.equals(commodityView.idCommodity) : commodityView.idCommodity != null) {
            return false;
        }
        if (commodityPhoto != null ? !Arrays.equals(commodityPhoto,commodityView.commodityPhoto ) : commodityView.commodityPhoto != null) {
            return false;
        }
        if (commodityPhotoType != null ? !commodityPhotoType.equals(commodityView.commodityPhotoType) : commodityView.commodityPhotoType != null) {
            return false;
        }
        if (galleryPhoto != null ? !galleryPhoto.equals(commodityView.galleryPhoto) : commodityView.galleryPhoto != null) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "CommodityView{" +
                "id=" + id +
                ", idCommodity=" + idCommodity +
                ", CommodityPhoto lengths =" + commodityPhoto.length +
                ",CommodityPhotoType = " + commodityPhotoType.toString()+ '\'' +
                ", GalleryPhoto='" + galleryPhoto + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idCommodity != null ? idCommodity.hashCode() : 0);
        result = 31 * result + (commodityPhoto != null ? commodityPhoto.hashCode() : 0);
        result = 31 * result + (commodityPhotoType != null ? commodityPhotoType.hashCode() : 0);
        result = 31 * result + (galleryPhoto != null ? galleryPhoto.hashCode() : 0);
        return result;
    }
}
