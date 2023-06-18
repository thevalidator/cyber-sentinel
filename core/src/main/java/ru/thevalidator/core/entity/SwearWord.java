/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.core.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Entity
@Table(name = "word")
public class SwearWord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "text_value")
    private String textValue;
    private int category;

    public SwearWord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.textValue);
        hash = 79 * hash + this.category;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SwearWord other = (SwearWord) obj;
        if (this.category != other.category) {
            return false;
        }
        return Objects.equals(this.textValue, other.textValue);
    }

    @Override
    public String toString() {
        return "SwearWord{" + "id=" + id + ", textValue=" + textValue + ", category=" + category + '}';
    }
    
}
