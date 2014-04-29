package lv.javaguru.java2.ishop.database.builders;

import lv.javaguru.java2.ishop.domain.Commodity;
import lv.javaguru.java2.ishop.domain.CommodityView;
import lv.javaguru.java2.ishop.domain.ImageType;

public class CommodityViewBuilder {
    private Long id;
    private Commodity commodity;
    private byte[] commodityPhoto;
    private ImageType commodityPhotoType;
    private Boolean galleryPhoto;

    public CommodityViewBuilder(){
    }

    public static CommodityViewBuilder createCommodityView(){
        return new CommodityViewBuilder();
    }

    public CommodityView build(){
        CommodityView view=new CommodityView();
        view.setId(id);
        view.setCommodity(commodity);
        view.setCommodityPhoto(commodityPhoto);
        view.setCommodityPhotoType(commodityPhotoType);
        view.setGalleryPhoto(galleryPhoto);
        return view;
    }

    public CommodityViewBuilder withId(Long id){
        this.id=id;
        return this;
    }
    public CommodityViewBuilder withPhoto(byte[] commodityPhoto){
        this.commodityPhoto=commodityPhoto;
        return this;
    }
    public CommodityViewBuilder withPhotoType(ImageType commodityPhotoType){
        this.commodityPhotoType=commodityPhotoType;
        return this;
    }
    public CommodityViewBuilder withGalleryPhoto(Boolean galleryPhoto){
        this.galleryPhoto=galleryPhoto;
        return this;
    }
    public CommodityViewBuilder with(CommodityBuilder commodityBuilder){
        this.commodity=commodityBuilder.build();
        return this;
    }
}
