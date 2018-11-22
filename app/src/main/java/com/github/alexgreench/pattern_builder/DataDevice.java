package com.github.alexgreench.pattern_builder;

public class DataDevice {
    private Integer type, color;
    private String name;

    private DataDevice(Integer type, Integer color, String name) {
        this.type = type;
        this.color = color;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public Integer getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    static class Builder {
        private Integer type, color;
        private String name;

        public Builder setType(final Integer type) {
            this.type = type;
            return this;
        }

        public Builder setColor(final Integer color) {
            this.color = color;
            return this;
        }

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public DataDevice create() {
            return new DataDevice(type, color, name);
        }
    }
}
