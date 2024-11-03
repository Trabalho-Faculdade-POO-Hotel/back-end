package br.com.projeto.core.base;

import java.util.List;
import java.util.Objects;

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
        private List<Object> values;
        private String type;

        public enum FilterComparator {
            EQUALS("="),
            NOT_EQUALS("!="),
            GREATER_THAN(">"),
            LESS_THAN("<"),
            GREATER_EQUAL_THAN(">="),
            LESS_EQUAL_THAN("<="),
            IN("IN"),
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
            this.values = null;
            this.type = null;
        }

        public FilterEntry(String key, List<Object> values) {
            this(key, FilterComparator.IN, null);
            this.values = values;
        }

        public FilterEntry(String key, FilterComparator comparator, Object value, String type) {
            this(key, comparator, value);
            this.type = type;
        }

        public FilterEntry(String key, List<Object> values, String type) {
            this(key, values);
            this.type = type;
        }

        public boolean isSingleValue() {
            return this.values == null;
        }

        public String getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }

        public List<Object> getValues() {
            return this.values;
        }

        public String buildQuery() {
            if (this.comparator == FilterComparator.IN) {
                return String.format(
                        "%s IN (" + String.join(",",
                                List.of(
                                        "?".repeat(this.values.size())
                                                .split(""))
                                        .stream()
                                        .map(s -> s + (Objects.nonNull(this.type) ? "::" + this.type : "")).toList())
                                + ")",
                        this.key);
            }

            return String.format(
                    "%s %s ?" + (Objects.nonNull(this.type) ? "::" + this.type : ""),
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

    public abstract List<T> get(List<FilterEntry> filter) throws Exception;

    public abstract List<T> get() throws Exception;

    public abstract T create(T entity) throws Exception;

    public abstract T update(T updatedEntity) throws Exception;

    public abstract void delete(T entity) throws Exception;
}
