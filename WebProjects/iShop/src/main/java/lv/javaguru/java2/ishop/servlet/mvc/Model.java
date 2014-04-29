package lv.javaguru.java2.ishop.servlet.mvc;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class Model {

    private String view;
    private Object data;

    public Model(String view, Object data) {
        this.view = view;
        this.data = data;
    }

    public String getView() {
        return view;
    }

    public Object getData() {
        return data;
    }
}
