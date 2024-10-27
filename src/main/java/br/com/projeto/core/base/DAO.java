package br.com.projeto.core.base;

import java.util.HashMap;
import java.util.List;

public abstract class DAO<T, E> {
    private E context;

    @SuppressWarnings("unchecked")
    public static <T, E> DAO<T, E> createFromClass(Class<? extends DAO<T, E>> daoClass, E context) {
        try {
            return (DAO<T, E>) daoClass.getConstructors()[0].newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public DAO(E context) {
        this.context = context;
    }

    protected E getContext() {
        return this.context;
    }

    public abstract List<T> get(HashMap<String, Object> filter);

    public abstract List<T> get();

    public abstract T create(T entity);

    public abstract T update(T updatedEntity);

    public abstract void delete(T entity);
}
