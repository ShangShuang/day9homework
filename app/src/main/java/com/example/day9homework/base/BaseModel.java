package com.example.day9homework.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {
    private CompositeDisposable compositeDisposable = null;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            synchronized (BaseModel.class) {
                if (compositeDisposable == null) {
                    compositeDisposable = new CompositeDisposable();
                }
            }
        }
        compositeDisposable.add(disposable);
    }

    public void destroy() {
        compositeDisposable.dispose();
    }

    public void removeDisposable(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable);
        }
    }

}
