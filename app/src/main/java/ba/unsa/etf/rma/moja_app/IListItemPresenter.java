package ba.unsa.etf.rma.moja_app;

public interface IListItemPresenter {
    boolean validateTitle(String title);
    boolean validateType(String type);
    boolean validateDescription(String description, String type);
    boolean validateInterval(int interval, String type);
    //validate date
    //validate end date

}
