package br.com.projeto.core.base;

import java.util.List;
import java.util.Map;

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

    public static class FilterEntry {
        private String key;
        private FilterComparator comparator;
        private Object value;

        public enum FilterComparator {
            EQUALS("="),
            NOT_EQUALS("!="),
            GREATER_THAN(">"),
            LESS_THAN("<"),
            GREATER_EQUAL_THAN(">="),
            LESS_EQUAL_THAN("<="),
            LIKE("LIKE");

            private String filterValue;

            private FilterComparator(String filterValue) {
                this.filterValue = filterValue;
            }

            @Override
            public String toString() {
                return this.filterValue;
            }
        }

        public FilterEntry(String key, FilterComparator comparator, Object value) {
            this.key = key;
            this.comparator = comparator;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }

        public String buildQuery() {
            return String.format(
                    "%s %s ?",
                    this.key,
                    this.comparator.toString());
        }
    }

    public DAO(E context) {
        this.context = context;
    }

    protected E getContext() {
        return this.context;
    }

    public abstract List<T> get(List<FilterEntry> filter);

    public abstract List<T> get();

    public abstract T create(T entity);

    public abstract T update(T updatedEntity);

    public abstract void delete(T entity);
}
