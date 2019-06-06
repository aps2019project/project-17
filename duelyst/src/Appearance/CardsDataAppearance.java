package Appearance;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CardsDataAppearance {
    private Text nameView;
    private Text priceView;
    private Text mpView;
    private Text apView;
    private Text hpView;
    private Text typeView;
    private Text idView;
    private Text inCollectionView;

    public CardsDataAppearance(String nameView, String priceView, String mpView, String apView, String hpView) {
        this.nameView = new Text(nameView);
        this.priceView = new Text(priceView);
        this.mpView = new Text(mpView);
        this.apView = new Text(apView);
        this.hpView = new Text(hpView);
        setFonts();
        dark();
        fill();
    }

    public CardsDataAppearance(String nameView, String priceView, String mpView) {
        this.nameView = new Text(nameView);
        this.priceView = new Text(priceView);
        this.mpView = new Text(mpView);
        setFonts();
        this.apView = null;
        this.hpView = null;
        dark();
        fill();
    }

    public CardsDataAppearance(Text typeView, Text idView, Text inCollectionView) {
        this.typeView = typeView;
        this.idView = idView;
        this.inCollectionView = inCollectionView;
        setSearchFonts();
    }

    private void setFonts() {
        this.nameView.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
        this.priceView.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
        this.mpView.setFont(FontAppearance.FONT_SHOW_CARD_DATA);
        if (apView != null) {
            this.apView.setFont(FontAppearance.FONT_SHOW_AP);
            this.hpView.setFont(FontAppearance.FONT_SHOW_AP);
        }
    }

    private void setSearchFonts(){
        this.idView.setFont(FontAppearance.FONT_NOT_FOUND);
        this.typeView.setFont(FontAppearance.FONT_NOT_FOUND);
        this.inCollectionView.setFont(FontAppearance.FONT_NOT_FOUND);
    }

    private void changeLight(double value) {
        nameView.setOpacity(value);
        priceView.setOpacity(value);
        mpView.setOpacity(value);
        if (apView != null) {
            apView.setOpacity(value);
            hpView.setOpacity(value);
        }
    }

    private void fill() {
        nameView.setFill(Color.WHITE);
        priceView.setFill(Color.WHITE);
        if (apView != null) {
            apView.setFill(Color.WHITE);
            hpView.setFill(Color.WHITE);
        }
    }

    public void light() {
        changeLight(1);
    }

    public void dark() {
        changeLight(0.7);
    }

    public Text getNameView() {
        return nameView;
    }

    public void setNameView(String nameView) {
        this.nameView.setText(nameView);
    }

    public Text getPriceView() {
        return priceView;
    }

    public void setPriceView(String priceView) {
        this.priceView.setText(priceView);
    }

    public Text getMpView() {
        return mpView;
    }

    public void setMpView(String mpView) {
        this.mpView.setText(mpView);
    }

    public Text getApView() {
        return apView;
    }

    public void setApView(String apView) {
        this.apView.setText(apView);
    }

    public Text getHpView() {
        return hpView;
    }

    public void setHpView(String hpView) {
        this.hpView.setText(hpView);
    }

    public void addAll(Group group) {
        group.getChildren().addAll(nameView, mpView, priceView);
        if (apView != null)
            group.getChildren().add(apView);
        if (hpView != null)
            group.getChildren().add(hpView);
    }

    public void removeAll(Group group) {
        group.getChildren().removeAll(nameView, mpView, priceView, apView, hpView);
    }
}
