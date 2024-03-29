package br.com.victorcatao.amaki.ui.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the BaseView type that wants to be attached with.
 */
public interface BasePresenter<V extends BaseView> {

    void attachView(V mvpView);

    void detachView();
}
